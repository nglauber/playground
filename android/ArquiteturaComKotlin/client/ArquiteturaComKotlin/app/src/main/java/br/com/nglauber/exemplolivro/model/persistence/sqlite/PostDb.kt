package br.com.nglauber.exemplolivro.model.persistence.sqlite

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import br.com.nglauber.exemplolivro.App
import br.com.nglauber.exemplolivro.model.data.Post
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.model.persistence.file.Media
import org.jetbrains.anko.db.*
import rx.Observable
import java.io.File
import java.util.*

class PostDb(val dbHelper : DbHelper = DbHelper(App.instance)) : PostDataSource {

    override fun loadPosts() : Observable<List<Post>> {
        val posts = dbHelper.use {
            select(PostTable.TABLE_NAME).parseList(object : MapRowParser<Post> {
                override fun parseRow(columns : Map<String, Any?>) : Post {
                    return PostMapper(HashMap(columns)).toDomain()
                }
            })
        }
        return Observable.just(posts)
    }

    override fun loadPost(postId: Long) : Observable<Post> {
        val post = dbHelper.use {
            select(PostTable.TABLE_NAME)
                    .whereSimple("${PostTable.ID} = ?", postId.toString())
                    .parseOpt(object : MapRowParser<Post> {
                        override fun parseRow(columns: Map<String, Any?>): Post {
                            return PostMapper(HashMap(columns)).toDomain();
                        }
                    })
        }
        return Observable.just(post)
    }

    override fun savePost(post: Post) : Observable<Long> {
        return Observable.create {
            subscriber ->

            try {
                val context = dbHelper.ctx

                if (post.id == 0L) {
                    insertPost(context, post)

                } else {
                    updatePost(context, post)
                }

                subscriber.onNext(post.id)

            } catch (e : Throwable) {
                e.printStackTrace()

                subscriber.onError(e)
            }
        }
    }

    override fun deletePost(post: Post) : Observable<Boolean> {
        return Observable.create {
            subscriber ->

            dbHelper.use {
                try {
                    beginTransaction()
                    val result = delete(PostTable.TABLE_NAME, "${PostTable.ID} = {postId}", "postId" to post.id) > 0

                    if (result) {

                        val file = File(dbHelper.ctx.getExternalFilesDir(null), "${post.id}.jpg")
                        if (file.exists() && !file.delete()) {
                            subscriber.onError(RuntimeException("Fail to delete file ${file.absolutePath}"))

                        } else {
                            setTransactionSuccessful()
                            subscriber.onNext(true)
                        }

                    } else {
                        subscriber.onError(RuntimeException("Fail to delete post"))
                    }

                } catch (e : Throwable) {
                    subscriber.onError(e)

                } finally {
                    endTransaction()
                }
            }
        }
    }

    private fun insertPost(context: Context, post: Post) : Boolean = dbHelper.use {
        try {
            beginTransaction()
            val postMapper = PostMapper(post)
            postMapper.map.remove(PostTable.ID)
            val insertedId = insert(PostTable.TABLE_NAME, *postMapper.map.map({ it.key to it.value }).toTypedArray())

            if (insertedId != -1L) {
                post.id = insertedId

                if (TextUtils.isEmpty(postMapper.photoUrl)) {
                    setTransactionSuccessful()
                    true

                } else {
                    val file = File(context.getExternalFilesDir(null), "${insertedId}.jpg")
                    val fileUrl = "file://${file.absolutePath}"
                    if (Media.saveImageFromUri(context, Uri.parse(postMapper.photoUrl), file)) {
                        val result = update(PostTable.TABLE_NAME, PostTable.PHOTO_URL to fileUrl)
                                .where("${PostTable.ID} = {postId}", "postId" to insertedId)
                                .exec()
                        if (result == 1) {
                            post.photoUrl = fileUrl
                            setTransactionSuccessful()
                            true

                        } else false

                    } else false
                }

            } else {
                throw RuntimeException("Fail to insert post")
            }

        } finally {
            endTransaction()
        }
    }

    private fun updatePost(context: Context, post: Post) : Boolean = dbHelper.use {
        try {
            beginTransaction()
            val postMapper = PostMapper(post)
            val result = update(PostTable.TABLE_NAME, *postMapper.map.map({ it.key to it.value }).toTypedArray())
                    .where("${PostTable.ID} = {postId}", "postId" to postMapper._id)
                    .exec()

            if (result == 1) {
                val file = File(context.getExternalFilesDir(null), "${postMapper._id}.jpg")

                if (TextUtils.isEmpty(postMapper.photoUrl)) {
                    setTransactionSuccessful()
                    if (file.exists()) {
                        file.delete()
                    }
                    true

                } else {
                    if (Media.saveImageFromUri(context, Uri.parse(postMapper.photoUrl), file)) {
                        setTransactionSuccessful()
                        true

                    } else false
                }

            } else {
                throw RuntimeException("Fail to update post")
            }

        } finally {
            endTransaction()
        }
    }
}
