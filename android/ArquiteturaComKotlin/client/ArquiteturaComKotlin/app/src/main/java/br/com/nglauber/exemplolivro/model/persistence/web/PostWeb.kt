package br.com.nglauber.exemplolivro.model.persistence.web

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import br.com.nglauber.exemplolivro.model.data.Post
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.model.persistence.file.Media
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.io.File

class PostWeb(private val username : String?,
              private val context : Context) : PostDataSource {

    val service : PostAPI

    companion object {
        val SERVER_PATH = "http://192.168.25.29/postservice/"
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<PostAPI>(PostAPI::class.java)
    }

    override fun loadPosts() : Observable<Post> {
        if (username == null) throw IllegalStateException("User not authenticated")

        return service.list(username)
                .map { post -> post.map { it.toDomain() } }
                .flatMap { posts -> Observable.from(posts) }
    }

    override fun loadPost(postId: Long) : Observable<Post> {
        if (username == null) throw IllegalStateException("User not authenticated")

        val postMapper = service.loadPost(postId, username)
        return postMapper.map{ post-> post?.toDomain() }
    }

    override fun savePost(post: Post): Observable<Long> {
        if (username == null) throw IllegalStateException("User not authenticated")

        val apiResult = if (post.id == 0L) {
            service.insert(PostMapper(post, username))

        } else {
            service.update(post.id, PostMapper(post, username))

        }.flatMap { idPost ->
            if (idPost.id != 0L){
                post.id = idPost.id
                if (!TextUtils.isEmpty(post.photoUrl) && post.photoUrl?.startsWith("http") == false) {
                    if (uploadFile(post)){
                        Observable.just(idPost.id)
                    } else {
                        Observable.error(RuntimeException("Fail to upload post image"))
                    }
                } else {
                    Observable.just(idPost.id)
                }
            } else {
                Observable.error(RuntimeException("Fail to save post"))
            }
        }
        return apiResult
    }

    override fun deletePost(post: Post): Observable<Boolean> {
        return service.delete(post.id)
                .flatMap { idResult ->
                    if (idResult.id != 0L) Observable.just(true)
                    else Observable.error(RuntimeException("Fail to delete post"))
                }
    }

    private fun uploadFile(post : Post) : Boolean {

        val file = File(context.getExternalFilesDir(null), "${post.id}.jpg")
        if (file.exists() &&  !file.delete()) return false

        if (Media.saveImageFromUri(context, Uri.parse(post.photoUrl), file)) {
            val requestFile = RequestBody.create(
                    MediaType.parse(context.contentResolver.getType(Uri.parse(post.photoUrl))),
                    file
            )

            val body = MultipartBody.Part.createFormData("arquivo", file.getName(), requestFile)
            val description = RequestBody.create(MultipartBody.FORM, post.id.toString())
            val response = service.uploadPhoto(description, body).execute()
            return response.isSuccessful
        }
        return false
    }
}