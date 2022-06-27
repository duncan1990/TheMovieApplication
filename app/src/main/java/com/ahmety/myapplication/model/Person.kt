package com.ahmety.myapplication.model

data class Person(
    val adult: Boolean,
    val biography: String,
    val id: Long,
    val known_for_department: String,
    val name: String,
    val place_of_birth: String,
    val popularity: Double,
    val profile_path: String
)
