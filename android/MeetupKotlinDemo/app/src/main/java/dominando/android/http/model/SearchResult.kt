package dominando.android.http.model

data class SearchResult(
    val totalItems: Int,
    val items: List<Volume>
)