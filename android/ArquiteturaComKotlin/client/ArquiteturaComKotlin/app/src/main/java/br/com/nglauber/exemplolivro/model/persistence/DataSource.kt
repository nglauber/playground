package br.com.nglauber.exemplolivro.model.persistence

import br.com.nglauber.exemplolivro.model.data.Post

interface PostDataSource {

    fun loadPosts(callback: (List<Post>)->Unit)

    fun loadPost(postId : Long, callback: (Post?)->Unit)

    fun savePost(post: Post, callback : (Boolean)->Unit)

    fun deletePost(post: Post, callback: (Boolean) -> Unit)
}
