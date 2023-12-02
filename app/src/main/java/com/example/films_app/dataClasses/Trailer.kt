package com.example.films_app.dataClasses

import com.example.films_app.R

data class Trailer(val trailerID: Int)

val trailers1 = listOf(
    Trailer(R.raw.winter_soldier_trailer_1),
    Trailer(R.raw.winter_soldier_trailer_2)
)

val trailers2 = listOf(
    Trailer(R.raw.civil_war_trailer_2),
    Trailer(R.raw.civil_war_trailer_1)
)

val trailers3 = listOf(
    Trailer(R.raw.dr_strange_trailer_1),
    Trailer(R.raw.dr_strange_trailer_2)
)