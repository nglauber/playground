package br.com.nglauber.exemplolivro.view.binding

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.net.Uri
import br.com.nglauber.exemplolivro.BR
import br.com.nglauber.exemplolivro.model.data.Post
import java.text.SimpleDateFormat
import java.util.*

class PostBinding(val post: Post = Post()) : BaseObservable() {

    var id: Long
        @Bindable
        get() = post.id
        set(id) {
            this.post.id = id
            notifyPropertyChanged(BR.id)
        }

    var text: String
        @Bindable
        get() = post.text
        set(text) {
            this.post.text = text
            notifyPropertyChanged(BR.text)
        }

    var date: Date?
        @Bindable
        get() = this.post.date
        set(date) {
            this.post.date = date!!
            notifyPropertyChanged(BR.date)
            notifyPropertyChanged(BR.dateAsString)
        }

    val dateAsString: String
        @Bindable
        get() {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            return format.format(this.post.date)
        }

    var photoUrl: String?
        @Bindable
        get() = this.post.photoUrl
        set(photoUrl) {
            this.post.photoUrl = photoUrl
            notifyPropertyChanged(BR.photoUrl)
            notifyPropertyChanged(BR.itemImageUrl)
        }

    var latitude: Double
        @Bindable
        get() = this.post.latitude
        set(latitude) {
            this.post.latitude = latitude
            notifyPropertyChanged(BR.latitude)
            notifyPropertyChanged(BR.locationUrl)
            notifyPropertyChanged(BR.itemImageUrl)
        }

    var longitude: Double
        @Bindable
        get() = this.post.longitude
        set(longitute) {
            this.post.longitude = longitute
            notifyPropertyChanged(BR.longitude)
            notifyPropertyChanged(BR.locationUrl)
            notifyPropertyChanged(BR.itemImageUrl)
        }

    val locationUrl: String?
        @Bindable
        get() {
            if (latitude != 0.0 && longitude != 0.0) {
                val builder = Uri.Builder()
                builder.scheme("https")
                        .authority("maps.googleapis.com")
                        .appendPath("maps")
                        .appendPath("api")
                        .appendPath("staticmap")
                        .appendQueryParameter("center", String.format("%f,%f", latitude, longitude))
                        .appendQueryParameter("size", "500x400")
                        .appendQueryParameter("zoom", "12")
                        .appendQueryParameter("format", "jpg")
                        .appendQueryParameter("language", Locale.getDefault().language)
                        .appendQueryParameter("markers", String.format("|%f,%f", latitude, longitude))
                        .fragment("section-name")
                return builder.build().toString()
            }
            return null
        }

    val itemImageUrl : String?
        @Bindable
        get() {
            return if (photoUrl.isNullOrEmpty()) locationUrl else photoUrl
        }
}
