package com.panji.animalie.ui.fragments.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.PostBinding
import com.panji.animalie.model.Post
import com.panji.animalie.ui.detail.DetailPostActivity
import com.panji.animalie.ui.detail.ViewModelDetailPost
import com.panji.animalie.ui.myprofile.MyProfileActivity
import com.panji.animalie.ui.myprofile.OtherProfileActivity
import com.panji.animalie.util.Constanta.EXTRA_POST
import com.panji.animalie.util.Constanta.EXTRA_USER
import com.panji.animalie.util.Constanta.URL_IMAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class PostAdapter(
    val context: Context?,
    val type: String? = null,
    val viewModel: ViewModelDetailPost? = null,
    private val lifecycleOwner: LifecycleOwner? = null
) :
    ListAdapter<Post, PostAdapter.ViewHolder>(DIFF_UTIL) {

    private val sessionManager: SessionManager by lazy {
        SessionManager(context!!)
    }

    private val token = sessionManager.fetchToken()

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

                if (SDK_INT >= Build.VERSION_CODES.N) {
                    postContent.text = Html.fromHtml(post.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                } else if (SDK_INT < Build.VERSION_CODES.N) {
                    postContent.text = Html.fromHtml(post.content)
                }

                // show more button only when activity is my profile
                date.text = timeAgo
                if (type == "my_post" && post.User.id.toString() == sessionManager.fetchId()) {
                    moreButton.visibility = ViewGroup.VISIBLE
                    date.visibility = ViewGroup.GONE
                } else {
                    moreButton.visibility = ViewGroup.GONE
                    date.visibility = ViewGroup.VISIBLE
                }

                if (post.like.isNotEmpty()) {
                    likeCounter.text = post.like.size.toString()
                } else {
                    likeCounter.text = "0"
                }

                // show like filled icon if user already like the post
                for (like in post.like) {
                    if (like.user_id == sessionManager.fetchId()) {
                        likeButton.setImageResource(R.drawable.ic_unlike)
                        break
                    } else {
                        likeButton.setImageResource(R.drawable.ic_like)
                    }
                }

                for (bookmarked in post.bookmarked_by) {
                    if (bookmarked.id.toString() == sessionManager.fetchId()) {
                        bookmark.setImageResource(R.drawable.ic_unbookmark)
                        break
                    } else {
                        bookmark.setImageResource(R.drawable.ic_bookmark)
                    }
                }

                if (post.Comments.isNotEmpty()) {
                    commentCounter.text = post.Comments.size.toString()
                } else {
                    commentCounter.text = "0"
                }

                if (post.User.id.toString() == sessionManager.fetchId()) {
                    biodata.setOnClickListener {
                        biodata.context?.startActivity(
                            Intent(biodata.context, MyProfileActivity::class.java)
                        )
                    }
                } else {
                    biodata.setOnClickListener {
                        biodata.context?.startActivity(
                            Intent(biodata.context, OtherProfileActivity::class.java)
                                .putExtra(EXTRA_USER, post.User.username)
                        )
                    }
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

                bookmark.setOnClickListener {
                    // TODO: 1. Buatlah sebuah fungsi untuk melakukan bookmark post
                    bookmarkPost(
                        postId = post.id.toString(),
                        bookmarkButton = bookmark
                    )
                }

                likeButton.setOnClickListener {
                    likePost(
                        postId = post.id.toString(),
                        likeButton = likeButton,
                        likeCounter = likeCounter
                    )
                }

                content.setOnClickListener {
                    content.context?.startActivity(
                        Intent(content.context, DetailPostActivity::class.java)
                            .putExtra(EXTRA_POST, post.slug)
                    )
                }
            }
        }
    }

    private fun bookmarkPost(
        postId: String? = null,
        bookmarkButton: ImageButton? = null
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            postId?.let { postId ->
                token?.let { token ->
                    lifecycleOwner?.let { lifecycleOwner ->
                        viewModel?.bookmarkPost(token, postId)?.observe(lifecycleOwner) { post ->
                            when (post) {
                                is Resource.Success -> {
                                    if (post.data?.bookmarked == true) {
                                        bookmarkButton?.setImageResource(R.drawable.ic_unbookmark)
                                    } else {
                                        bookmarkButton?.setImageResource(R.drawable.ic_bookmark)
                                    }
                                }

                                is Resource.Loading -> {

                                }

                                is Resource.Error -> {
                                    Toast.makeText(
                                        context,
                                        post.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun likePost(
        postId: String? = null,
        likeButton: ImageButton? = null,
        likeCounter: TextView? = null
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            postId?.let { postId ->
                token?.let { token ->
                    lifecycleOwner?.let { lifecycleOwner ->
                        viewModel?.likePost(token, postId)?.observe(lifecycleOwner) { post ->
                            when (post) {
                                is Resource.Success -> {
                                    if (post.data?.liked == true) {
                                        likeButton?.setImageResource(R.drawable.ic_unlike)
                                    } else {
                                        likeButton?.setImageResource(R.drawable.ic_like)
                                    }

                                    likeCounter?.text = post.data?.likeCount.toString()
                                }

                                is Resource.Loading -> {

                                }

                                is Resource.Error -> {
                                    Toast.makeText(
                                        context,
                                        post.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
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