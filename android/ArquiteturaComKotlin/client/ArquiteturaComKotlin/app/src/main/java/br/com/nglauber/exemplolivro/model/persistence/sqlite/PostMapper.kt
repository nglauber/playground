package br.com.nglauber.exemplolivro.model.persistence.sqlite

import br.com.nglauber.exemplolivro.model.data.Post
import java.util.*

class PostMapper(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var text: String by map
    var date: Long by map
    var photoUrl: String? by map
    var latitude: Double by map
    var longitude: Double by map

    constructor(post : Post) : this(HashMap()) {
        this._id = post.id
        this.text = post.text
        this.date = post.date.time
        this.photoUrl = post.photoUrl
        this.latitude = post.latitude
        this.longitude = post.longitude
    }

    fun toDomain(): Post = Post(_id, text, Date(date), photoUrl, latitude, longitude)
}
