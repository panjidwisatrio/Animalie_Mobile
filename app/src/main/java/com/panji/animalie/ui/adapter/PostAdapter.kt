package com.panji.animalie.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.R
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.PostBinding
import com.panji.animalie.model.Post
import com.panji.animalie.ui.createpost.CreatePostActivity
import com.panji.animalie.ui.createpost.ViewModelCreatePost
import com.panji.animalie.ui.detail.DetailPostActivity
import com.panji.animalie.ui.detail.ViewModelDetailPost
import com.panji.animalie.ui.myprofile.MyProfileActivity
import com.panji.animalie.ui.myprofile.OtherProfileActivity
import com.panji.animalie.util.Constanta.BASE_URL
import com.panji.animalie.util.Constanta.EXTRA_CATEGORY
import com.panji.animalie.util.Constanta.EXTRA_CONTENT
import com.panji.animalie.util.Constanta.EXTRA_POST
import com.panji.animalie.util.Constanta.EXTRA_SLUG
import com.panji.animalie.util.Constanta.EXTRA_TAG
import com.panji.animalie.util.Constanta.EXTRA_TITLE
import com.panji.animalie.util.Constanta.EXTRA_USER
import com.panji.animalie.util.Constanta.URL_IMAGE
import com.panji.animalie.util.Constanta.URL_WEB
import com.panji.animalie.util.TimeStampHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostAdapter(
    val context: Context?,
    val type: String? = null,
    private val viewModel: ViewModelDetailPost? = null,
    private val viewModelCreatePost: ViewModelCreatePost? = null,
    private val lifecycleOwner: LifecycleOwner? = null,
    private val refreshData: Function0<Unit>? = null
): ListAdapter<Post, PostAdapter.ViewHolder>(DIFF_UTIL) {

    private val sessionManager: SessionManager by lazy {
        SessionManager(context!!)
    }

    private val token = sessionManager.fetchToken()
    private val userId = sessionManager.fetchId()

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
            val timeAgo = TimeStampHelper.getTimeAgo(post.created_at)
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

                date.text = timeAgo

                // show more button only when activity is my profile
                if (type == "my_post" && post.User.id.toString() == sessionManager.fetchId()) {
                    moreButton.visibility = ViewGroup.VISIBLE
                    date.visibility = ViewGroup.GONE

                    moreButton.setOnClickListener {
                        showOptionMenu(moreButton, post)
                    }
                } else {
                    moreButton.visibility = ViewGroup.GONE
                    date.visibility = ViewGroup.VISIBLE
                }

                if (post.like.isNotEmpty()) {
                    likeCounter.text = post.like.size.toString()
                } else {
                    likeCounter.text = "0"
                }

                var isLiked = false
                for (like in post.like) {
                    if (like.user_id == userId) {
                        isLiked = true
                        break
                    }
                }

                if (isLiked) {
                    likeButton.setImageResource(R.drawable.ic_unlike)
                } else {
                    likeButton.setImageResource(R.drawable.ic_like)
                }

                var isBookmarked = false
                for (bookmark in post.bookmarked_by) {
                    if (bookmark.id.toString() == userId) {
                        isBookmarked = true
                        break
                    }
                }

                if (isBookmarked) {
                    bookmark.setImageResource(R.drawable.ic_unbookmark)
                } else {
                    bookmark.setImageResource(R.drawable.ic_bookmark)
                }

                if (post.Comments.isNotEmpty()) {
                    commentCounter.text = post.Comments.size.toString()
                } else {
                    commentCounter.text = "0"
                }

                if (post.User.id.toString() == userId) {
                    bookmark.visibility = ViewGroup.GONE

                    biodata.setOnClickListener {
                        biodata.context?.startActivity(
                            Intent(biodata.context, MyProfileActivity::class.java)
                        )
                    }
                } else {
                    bookmark.visibility = ViewGroup.VISIBLE

                    biodata.setOnClickListener {
                        biodata.context?.startActivity(
                            Intent(biodata.context, OtherProfileActivity::class.java)
                                .putExtra(EXTRA_USER, post.User.username)
                        )
                    }

                    bookmark.setOnClickListener {
                        bookmarkPost(
                            postId = post.id.toString(),
                            bookmarkButton = bookmark
                        )
                    }
                }

                shareButton.setOnClickListener {
                    val message = "${URL_WEB}post-detail/${post.slug}"

                    context?.startActivity(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, post.title)
                            putExtra(Intent.EXTRA_TEXT, message)
                        }
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

    fun updateData(newData: List<Post>) {
        submitList(newData)
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

    private fun deletePost(view: View, post: Post) {
        CoroutineScope(Dispatchers.Main).launch {
            post.slug.let { slug ->
                token?.let { token ->
                    lifecycleOwner?.let { lifecycleOwner ->
                        viewModelCreatePost?.deletePost(token, slug)
                            ?.observe(lifecycleOwner) { post ->
                                when (post) {
                                    is Resource.Success -> {
                                        Toast.makeText(
                                            view.context,
                                            "Post Deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        refreshData?.invoke()
                                    }

                                    is Resource.Loading -> {}

                                    is Resource.Error -> {
                                        Toast.makeText(
                                            view.context,
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

    private fun showOptionMenu(
        view: View,
        post: Post
    ) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.post_menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_post -> {
                    view.context.startActivity(
                        Intent(view.context, CreatePostActivity::class.java)
                            .putExtra(EXTRA_TITLE, post.title)
                            .putExtra(EXTRA_SLUG, post.slug)
                            .putExtra(EXTRA_CONTENT, post.content)
                            .putExtra(EXTRA_CATEGORY, post.Category.category)
                            .putExtra("TYPE", "edit")
                            .putStringArrayListExtra(EXTRA_TAG, post.Tag.map { it.name_tag } as ArrayList<String>)
                    )
                }

                R.id.delete_post -> {
                    MaterialAlertDialogBuilder(view.context)
                        .setTitle("Delete Post")
                        .setMessage("Are you sure want to delete this post?")
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Delete") { _, _ ->
                            deletePost(view, post)
                        }
                        .show()
                }
            }

            true
        }

        popupMenu.show()
    }
}