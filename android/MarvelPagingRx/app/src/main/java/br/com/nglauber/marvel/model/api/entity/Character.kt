package br.com.nglauber.marvel.model.api.entity

data class Character(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: Thumbnail
)