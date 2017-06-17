package br.com.nglauber.starwarskoltinrx.model

data class Movie (val title : String, val episodeId : Int, val characters : MutableList<Character>)

data class Character(val name : String, val gender : String) {

    override fun toString(): String {
        return "${name} - ${gender}"
    }
}