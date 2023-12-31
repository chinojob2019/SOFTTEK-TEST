package com.culqitest.softtek_test.ui


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.culqitest.softtek_test.R
import com.culqitest.softtek_test.model.FilmModel


/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private var repo: FilmModel? = null

    init {
        view.setOnClickListener {
           /* repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }*/
        }
    }

    fun bind(repo: FilmModel?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = "Cargando..."
            description.visibility = View.GONE
            language.visibility = View.GONE
         //   stars.text = resources.getString(R.string.unknown)
          //  forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: FilmModel) {
        this.repo = repo
        name.text = repo.title

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.originalTitle != null) {
            description.text = repo.originalTitle
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

       // stars.text = repo.stars.toString()
      //  forks.text = repo.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
       /* if (!repo.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, repo.language)
            languageVisibility = View.VISIBLE
        }*/
        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}
