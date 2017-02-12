package br.com.nglauber.exemplolivro.model.persistence.web

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostAPI {
    @GET("webservice.php")
    fun list(@Query("username") user: String): Call<List<PostMapper>>

    @GET("webservice.php/{postId}")
    fun loadPost(@Path("postId") id : Long, @Query("username") user: String): Call<PostMapper?>

    @POST("webservice.php/postservice")
    fun insert(@Body post: PostMapper) : Call<IdResult>

    @PUT("webservice.php/postservice/{postId}")
    fun update(@Path("postId") id : Long, @Body post: PostMapper) : Call<IdResult>

    @DELETE("webservice.php/postservice/{postId}")
    fun delete(@Path("postId") id : Long) : Call<IdResult>

    @Multipart
    @POST("upload.php")
    fun uploaPhoto(
            @Part("id") postId: RequestBody,
            @Part file: MultipartBody.Part
    ): Call<IdResult>
}