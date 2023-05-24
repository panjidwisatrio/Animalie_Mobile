package com.panji.animalie.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.databinding.PostBinding
import com.panji.animalie.model.Post
import com.panji.animalie.util.Constanta.URL_IMAGE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

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

    inner class ViewHolder(private val binding: PostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            val timeAgo = getTimeAgo(post.created_at)
            val stringBuilder = post.User.avatar?.let { StringBuilder(it) }
            val fixString = stringBuilder?.replace(11, 12, "/").toString()

            binding.apply {
                if (post.User.avatar == null) {
                    profilePhoto.setImageResource(R.mipmap.profile_photo_round)
                } else {
                    Glide.with(itemView.context)
                        .load(URL_IMAGE + fixString)
                        .into(profilePhoto)
                }

                fullName.text = post.User.name
                username.text = post.User.username
                postTitle.text = post.title

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    postContent.text = Html.fromHtml(post.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    postContent.text = Html.fromHtml(post.content)
                }

                date.text = timeAgo

                if (post.like != null) {
                    likeCounter.text = post.like.count.toString()
                } else {
                    likeCounter.text = "0"
                }

                if (post.Comments.isEmpty()) {
                    commentCounter.text = "0"
                } else {
                    commentCounter.text = post.Comments.size.toString()
                }

                shareButton.setOnClickListener {
                    context?.startActivity(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, post.title)
                            putExtra(Intent.EXTRA_TEXT, "${post.title}\n\n${post.content}")
                        }
                    )
                }

                // TODO: 1. Buatlah sebuah fungsi untuk melakukan bookmark post
                // TODO: 2. Buatlah sebuah fungsi untuk melakukan like post
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

    private fun getTimeAgo(timestamp: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val date = sdf.parse(timestamp)
        val now = Date()
        val diff = now.time - date!!.time

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> {
                val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
                "$seconds detik yang lalu"
            }
            diff < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
                "$minutes menit yang lalu"
            }
            diff < TimeUnit.DAYS.toMillis(1) -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diff)
                "$hours jam yang lalu"
            }
            diff < TimeUnit.DAYS.toMillis(7) -> {
                val days = TimeUnit.MILLISECONDS.toDays(diff)
                "$days hari yang lalu"
            }
            diff < TimeUnit.DAYS.toMillis(30) -> {
                val weeks = TimeUnit.MILLISECONDS.toDays(diff) / 7
                "$weeks minggu yang lalu"
            }
            diff < TimeUnit.DAYS.toMillis(365) -> {
                val months = TimeUnit.MILLISECONDS.toDays(diff) / 30
                "$months bulan yang lalu"
            }
            else -> {
                val years = TimeUnit.MILLISECONDS.toDays(diff) / 365
                "$years tahun yang lalu"
            }
        }
    }
}