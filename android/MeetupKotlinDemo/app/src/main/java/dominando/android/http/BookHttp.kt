package dominando.android.http

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import dominando.android.http.model.SearchResult
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object BookHttp {

    val API_KEY = "AIzaSyCqHb5zz6W6pdzg4xsFQSNR2_j4mupNIys"

    private val BOOK_JSON_URL = "https://www.googleapis.com/books/v1/volumes?q=%s&key$API_KEY"

    fun hasConnection(ctx: Context): Boolean {
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }

    fun searchBook(q: String): SearchResult? {
        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(String.format(BOOK_JSON_URL, q))
            .build()
        try {
            val response = client.newCall(request).execute()
            val json = response.body()?.string()
            val result = Gson().fromJson<SearchResult>(json, SearchResult::class.java)
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
