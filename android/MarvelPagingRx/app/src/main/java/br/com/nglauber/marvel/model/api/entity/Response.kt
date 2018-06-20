package br.com.nglauber.marvel.model.api.entity

data class Response(
        val code: Int,
        val etag: String,
        val data: Data
)