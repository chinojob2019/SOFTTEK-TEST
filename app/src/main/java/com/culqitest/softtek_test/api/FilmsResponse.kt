package com.culqitest.softtek_test.api

import com.culqitest.softtek_test.model.FilmModel
import com.google.gson.annotations.SerializedName

data class FilmsResponse(

	@field:SerializedName("dates")
	val dates: Dates? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<FilmModel> = emptyList(),

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class Dates(

	@field:SerializedName("maximum")
	val maximum: String? = null,

	@field:SerializedName("minimum")
	val minimum: String? = null
)
