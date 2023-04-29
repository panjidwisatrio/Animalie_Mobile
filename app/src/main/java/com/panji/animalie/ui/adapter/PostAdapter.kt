package com.panji.animalie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.databinding.HomePageBinding
import com.panji.animalie.databinding.PostBinding
import com.panji.animalie.model.Post

class PostAdapter(private val data: List<Post>) :
RecyclerView.Adapter<PostAdapter.Viewholder>()
{

    class Viewholder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
         fun bind(post: Post) = with(binding) {
             postTitle.text = post.title
             postContent.text = post.content
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return Viewholder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(data[position])
    }
}