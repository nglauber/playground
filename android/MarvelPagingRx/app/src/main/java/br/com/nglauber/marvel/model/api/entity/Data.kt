package br.com.nglauber.marvel.model.api.entity

data class Data(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Character>
)