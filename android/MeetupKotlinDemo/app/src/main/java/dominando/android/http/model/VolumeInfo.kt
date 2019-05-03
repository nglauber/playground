package dominando.android.http.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VolumeInfo(
    val title: String,
    val subtitle: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String,
    val description: String?,
    val pageCount: Int,
    val imageLinks: ImageLinks?
): Parcelable