package com.panji.animalie.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.R
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.CommentListBinding
import com.panji.animalie.model.Comment
import com.panji.animalie.ui.detail.ViewModelDetailPost
import com.panji.animalie.ui.myprofile.MyProfileActivity
import com.panji.animalie.ui.myprofile.OtherProfileActivity
import com.panji.animalie.util.Constanta.EXTRA_USER
import com.panji.animalie.util.Constanta.URL_IMAGE
import com.panji.animalie.util.TimeStampHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentAdapter(
    val context: Context?,
    private val viewModel: ViewModelDetailPost? = null,
    private val lifecycleOwner: LifecycleOwner? = null,
    private val refreshData: Function0<Unit>? = null
) : ListAdapter<Comment, CommentAdapter.ViewHolder>(DIFF_UTIL) {

    private val sessionManager: SessionManager by lazy {
        SessionManager(context!!)
    }

    private val token = sessionManager.fetchToken()
    private val userId = sessionManager.fetchId()

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(private val binding: CommentListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {
            val timeAgo = TimeStampHelper.getTimeAgo(comment.created_at)
            val stringBuilder = comment.user.avatar?.let { StringBuilder(it) }
            val fixString = stringBuilder?.replace(11, 12, "/").toString()

            binding.apply {
                fullNameCommenter.text = comment.user.name
                usernameCommenter.text = comment.user.username
                dateCommenter.text = timeAgo

                if (comment.user.avatar == null) {
                    profilePhotoCommenter.setImageResource(R.mipmap.profile_photo_round)
                } else {
                    Glide.with(itemView.context)
                        .load(URL_IMAGE + fixString)
                        .into(profilePhotoCommenter)
                }

                commentContent.text = comment.comment

                if (comment.user_id.toString() == userId) {
                    dateCommenter.visibility = ViewGroup.GONE
                    deleteButton.visibility = ViewGroup.VISIBLE

                    deleteButton.setOnClickListener {
                        context?.let { it1 ->
                            MaterialAlertDialogBuilder(it1)
                                .setTitle("Delete Comment")
                                .setMessage("Are you sure want to delete this comment?")
                                .setPositiveButton("Yes") { _, _ ->
                                    deleteComment(comment.id.toString())
                                }
                                .setNegativeButton("No") { _, _ ->

                                }
                                .show()
                        }
                    }

                    biodata.setOnClickListener {
                        biodata.context.startActivity(
                            Intent(
                                biodata.context,
                                MyProfileActivity::class.java
                            )
                        )
                    }
                } else {
                    dateCommenter.visibility = ViewGroup.VISIBLE
                    deleteButton.visibility = ViewGroup.GONE

                    biodata.setOnClickListener {
                        biodata.context.startActivity(
                            Intent(
                                biodata.context,
                                OtherProfileActivity::class.java
                            ).putExtra(EXTRA_USER, comment.user.username)
                        )
                    }
                }

//                likeButton.setOnClickListener {
//                    likeComment(
//                        comment.id.toString(),
//                        likeButton,
//                        likeCounter
//                    )
//                }
            }
        }
    }

    fun updateData(newData: List<Comment>) {
        submitList(newData)
    }

    private fun deleteComment(commentId: String? = null) {
        CoroutineScope(Dispatchers.Main).launch {
            commentId?.let { commentId ->
                token?.let { token ->
                    lifecycleOwner?.let { lifecycleOwner ->
                        viewModel?.deleteComment(token, commentId)
                            ?.observe(lifecycleOwner) { post ->
                                when (post) {
                                    is Resource.Success -> {
                                        Toast.makeText(
                                            context,
                                            post.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        refreshData?.invoke()
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

//    private fun likeComment(
//        commentId: String? = null,
//        likeButton: ImageButton? = null,
//        likeCounter: TextView? = null
//    ) {
//        CoroutineScope(Dispatchers.Main).launch {
//            commentId?.let { commentId ->
//                token?.let { token ->
//                    lifecycleOwner?.let { lifecycleOwner ->
//                        viewModel?.likeComment(token, commentId)?.observe(lifecycleOwner) { comment ->
//                            when (comment) {
//                                is Resource.Success -> {
//                                    if (comment.data?.liked == true) {
//                                        likeButton?.setImageResource(R.drawable.ic_unlike)
//                                    } else {
//                                        likeButton?.setImageResource(R.drawable.ic_like)
//                                    }
//
//                                    likeCounter?.text = comment.data?.likeCount.toString()
//                                }
//
//                                is Resource.Loading -> {
//
//                                }
//
//                                is Resource.Error -> {
//                                    Toast.makeText(
//                                        context,
//                                        comment.message,
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        return ViewHolder(
            CommentListBinding.inflate(
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