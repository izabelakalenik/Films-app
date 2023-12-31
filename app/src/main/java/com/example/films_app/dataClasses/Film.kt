package com.example.films_app.dataClasses

import com.example.films_app.R

data class Film(
    val title: String,
    val imageRes: Int,
    val releaseDate: String,
    val director: String,
    val duration: String,
    val productionCountry: String,
    val description: String,
    val scenesList : List<Scene>,
    val trailersList : List<Trailer>,
    val castList : List<CastMember>
)
val films = listOf(
    Film(
        "Captain America: The Winter Soldier",
        R.drawable.p1_winter_soldier,
        "March 13, 2014",
        "Anthony Russo",
        "2h 16m",
        "USA",
        "As Steve Rogers struggles to embrace his role in the modern world, he teams up with a fellow Avenger and S.H.I.E.L.D agent, Black Widow, to battle a new threat from history: an assassin known as the Winter Soldier.",
        scenes1,
        trailers1,
        cast1
    ),
    Film(
        "Captain America: Civil War",
        R.drawable.p2_civil_war,
        "April 12, 2016",
        "Anthony Russo, Joe Russo",
        "2h 27m",
        "USA",
        "Political involvement in the Avengers' affairs causes a rift between Captain America and Iron Man. It leads to the civil war between two superhero fractions.",
        scenes2,
        trailers2,
        cast2
    ),
    Film(
        "Doctor Strange in the Multiverse of Madness",
        R.drawable.p3_dr_strange,
        "May 6, 2022",
        "Sam Raimi",
        "2h 06m",
        "USA",
        "Doctor Strange teams up with a mysterious teenage girl from his dreams who can travel across multiverses, to battle multiple threats, including other-universe versions of himself, which threaten to wipe out millions across the multiverse. They seek help from Wanda the Scarlet Witch, Wong and others.",
        scenes3,
        trailers3,
        cast3
    )
)
