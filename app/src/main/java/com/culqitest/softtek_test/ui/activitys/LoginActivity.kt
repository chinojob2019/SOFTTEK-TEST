package com.culqitest.softtek_test.ui.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.culqitest.softtek_test.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.etlogin.setText("Admin")
        binding.etcontrasena.setText("Password*123")
        binding.btnlogin.setOnClickListener {
            if (verificaCampos()) {
                val intent: Intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
                ContextCompat.startActivity(this, intent, null)
                this.finish()
            }
        }


    }


    private fun verificaCampos(): Boolean {
        val login = binding.etlogin.getText().toString()
        val contraseña = binding.etcontrasena.getText().toString()
        binding.tillogin.setError("")
        binding.tilcontrasena.setError("")
        if (login.isEmpty()) {
            binding.tillogin.setError("Introduce un Login")
        } else if (contraseña.isEmpty()) {
            binding.tilcontrasena.setError("Introduce una contraseña")
        } else {
            if (login.equals("Admin") && contraseña.equals("Password*123")) {
                return true
            } else {
                binding.tillogin.setError("Verifique login")
                binding.tilcontrasena.setError("Verifique contraseña")
            }

        }
        return false
    }

}