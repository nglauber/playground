package br.com.nglauber.exemplolivro.model.data

import java.util.*

data class Post (
    var id: Long = 0,
    var text: String = "",
    var date: Date = Date(),
    var photoUrl: String? = null,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)
