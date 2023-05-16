package com.panji.animalie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.R
import com.panji.animalie.databinding.PostBinding
import com.panji.animalie.model.Post

class PostAdapter(val context: Context?) : ListAdapter<Post, PostAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(private val binding: PostBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            val minutes = post.created_at.split("T")[1].split(":")[1]

            binding.apply {
                fullName.text = post.User.name
                username.text = post.User.username
                postTitle.text = post.title
                postContent.text = post.content
                // TODO: 10/10/2021 Menampilkan waktu posting belum sempurna
                date.text = context?.getString(R.string.posted_at, minutes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        bind(getItem(position))
    }
}