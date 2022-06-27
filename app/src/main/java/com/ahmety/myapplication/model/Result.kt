package com.ahmety.myapplication.model

data class Result(
    val page: Long,
    val results: List<Data>,
    val totalPages: Long,
    val totalResults: Long
)
