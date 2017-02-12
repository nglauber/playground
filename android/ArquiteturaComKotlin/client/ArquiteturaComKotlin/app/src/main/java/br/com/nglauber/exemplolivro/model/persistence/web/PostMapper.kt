package br.com.nglauber.exemplolivro.model.persistence.web

import android.text.TextUtils
import br.com.nglauber.exemplolivro.model.data.Post
import java.text.SimpleDateFormat
import java.util.*

data class PostMapper(
    var id: Long,
    var username: String,
    var text: String,
    var date: String,
    var photourl: String? = null,
    var latitude: Double,
    var longitude: Double
) {
    companion object {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    }

    constructor(post : Post, username: String) : this(
            post.id,
            username,
            post.text,
            formatter.format(post.date),
            post.photoUrl ?: "",
            post.latitude,
            post.longitude)

    fun toDomain() : Post {
        val photo = if (TextUtils.isEmpty(photourl)) null else PostWeb.SERVER_PATH + photourl
        return Post(id, text, formatter.parse(date), photo, latitude, longitude)
    }
}

data class IdResult(var id : Long)