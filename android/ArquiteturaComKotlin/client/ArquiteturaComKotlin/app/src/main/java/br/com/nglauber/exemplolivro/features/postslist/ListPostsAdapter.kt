package br.com.nglauber.exemplolivro.features.postslist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.databinding.ItemPostBinding
import br.com.nglauber.exemplolivro.shared.binding.PostBinding

class ListPostsAdapter(var posts: List<PostBinding>,
                       val itemClick: (PostBinding)->Unit) : RecyclerView.Adapter<ListPostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PostViewHolder {
        val binding : ItemPostBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_post,
                parent,
                false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.post = post
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener { itemClick(post) }
    }

    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)
}