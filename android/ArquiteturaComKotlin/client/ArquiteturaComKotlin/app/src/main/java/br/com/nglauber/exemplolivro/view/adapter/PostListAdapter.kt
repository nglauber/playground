package br.com.nglauber.exemplolivro.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.databinding.ItemPostBinding
import br.com.nglauber.exemplolivro.view.binding.PostBinding

class PostListAdapter(var posts: List<PostBinding>, val itemClick: (PostBinding)->Unit) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PostViewHolder {
        val binding : ItemPostBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_post,
                parent,
                false)
        return PostViewHolder(binding, itemClick)
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

    class PostViewHolder(val binding: ItemPostBinding, val itemClick: (PostBinding) -> Unit) : RecyclerView.ViewHolder(binding.root)
}