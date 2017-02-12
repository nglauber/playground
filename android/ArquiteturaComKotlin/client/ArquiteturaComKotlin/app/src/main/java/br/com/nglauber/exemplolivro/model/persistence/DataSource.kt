package br.com.nglauber.exemplolivro.model.persistence

import br.com.nglauber.exemplolivro.model.data.Post

interface PostDataSource {

    fun loadPosts() : List<Post>

    fun loadPost(postId : Long) : Post?

    fun savePost(post: Post) : Boolean

    fun deletePost(post: Post) : Boolean
}
