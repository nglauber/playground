package dominando.android.http.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
): Parcelable