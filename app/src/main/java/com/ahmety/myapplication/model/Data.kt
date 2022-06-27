package com.ahmety.myapplication.model

import java.io.Serializable

data class Data(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Long>? = null,
    val id: Long,
    val media_type: String,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Long? = null,
    val first_air_date: String? = null,
    val name: String? = null,
    val origin_country: List<String>? = null,
    val original_name: String? = null,
    val gender: Long? = null,
    val known_for: List<Data>? = null,
    val known_for_department: String? = null,
    val profile_path: String? = null
) : Serializable

enum class MediaType(val mediaType: String) {
    Movie("movie"),
    Person("person"),
    Tv("tv")
}