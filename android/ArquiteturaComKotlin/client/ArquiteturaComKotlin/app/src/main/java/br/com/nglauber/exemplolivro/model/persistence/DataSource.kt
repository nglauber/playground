package br.com.nglauber.exemplolivro.model.persistence

import br.com.nglauber.exemplolivro.model.data.Post
import rx.Observable

interface PostDataSource {

    fun loadPosts() : Observable<List<Post>>

    fun loadPost(postId : Long) : Observable<Post>

    fun savePost(post: Post) : Observable<Long>

    fun deletePost(post: Post) : Observable<Boolean>
}
