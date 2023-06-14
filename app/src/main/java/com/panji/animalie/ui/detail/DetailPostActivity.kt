package com.panji.animalie.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.panji.animalie.R
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.ActivityDetailPostBinding
import com.panji.animalie.model.Comment
import com.panji.animalie.model.response.DetailPostResponse
import com.panji.animalie.ui.adapter.CommentAdapter
import com.panji.animalie.util.Constanta
import com.panji.animalie.util.Constanta.EXTRA_POST
import com.panji.animalie.util.TimeStampHelper
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPostActivity : AppCompatActivity(), ViewStateCallback<DetailPostResponse> {

    private lateinit var binding: ActivityDetailPostBinding
    private val viewModel by viewModels<ViewModelDetailPost>()
    private lateinit var adapterComment: CommentAdapter
    private val sessionManager by lazy { SessionManager(this) }

    private var postId: String? = null
    private var comment: List<Comment>? = null
    private val token by lazy { sessionManager.fetchToken() }
    private val userId by lazy { sessionManager.fetchId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Post"

        adapterComment = CommentAdapter(
            this,
            viewModel = viewModel,
            lifecycleOwner = this,
            refreshData = {
                updateComment()
            }
        )

        getPost()
        sendComment()
        setSwipeRefresh()
        showRecyclerView()
    }

    private fun sendComment() {
        binding.sendCommentButton.setOnClickListener {
            val comment = binding.comment.text.toString()

            if (comment.isEmpty()) {
                Snackbar.make(binding.root, "Comment can't be empty", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                postId?.let { postId ->
                    token?.let { token ->
                        viewModel.sendComment(token, postId, comment)
                            .observe(this@DetailPostActivity) { comment ->
                                when (comment) {
                                    is Resource.Success -> {
                                        Snackbar.make(
                                            binding.root,
                                            "Comment sent",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                        binding.comment.text.clear()
                                        updateComment()
                                    }

                                    is Resource.Loading -> {
                                        //
                                    }

                                    is Resource.Error -> {
                                        Snackbar.make(
                                            binding.root,
                                            comment.message.toString(),
                                            Snackbar.LENGTH_SHORT
                                        ).setAction(
                                            "Retry"
                                        ) {
                                            sendComment()
                                        }.show()
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    private fun setSwipeRefresh() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                getPost()
            }
        }
    }

    private fun showRecyclerView() {
        binding.recyclerView.apply {
            adapter = adapterComment
            isNestedScrollingEnabled = false

            layoutManager =
                LinearLayoutManager(this@DetailPostActivity, LinearLayoutManager.VERTICAL, false)
            (layoutManager as LinearLayoutManager).scrollToPosition(0)
        }
    }

    private fun getPost() {
        val slug = intent.getStringExtra(EXTRA_POST)

        CoroutineScope(Dispatchers.Main).launch {
            slug?.let {
                userId?.let { it1 ->
                    viewModel.getDetailPost(it, it1).observe(this@DetailPostActivity) { post ->
                        when (post) {
                            is Resource.Success -> post.data?.let { it1 -> onSuccess(it1) }
                            is Resource.Loading -> onLoading()
                            is Resource.Error -> onFailed(post.message)
                        }
                    }
                }
            }
        }
    }

    private fun likePost() {
        CoroutineScope(Dispatchers.Main).launch {
            postId?.let {
                token?.let { it1 ->
                    viewModel.likePost(it1, it).observe(this@DetailPostActivity) { post ->
                        when (post) {
                            is Resource.Success -> {
                                binding.apply {
                                    if (post.data?.liked == true) {
                                        likeButton.setImageResource(R.drawable.ic_unlike)
                                    } else {
                                        likeButton.setImageResource(R.drawable.ic_like)
                                    }

                                    likeCounter.text = post.data?.likeCount.toString()
                                }
                            }

                            is Resource.Loading -> {

                            }

                            is Resource.Error -> {
                                Toast.makeText(
                                    this@DetailPostActivity,
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

    private fun updateComment() {
        val slug = intent.getStringExtra(EXTRA_POST)

        CoroutineScope(Dispatchers.Main).launch {
            slug?.let {
                userId?.let { it1 ->
                    viewModel.getDetailPost(it, it1).observe(this@DetailPostActivity) { post ->
                        when (post) {
                            is Resource.Success -> {
                                comment = post.data?.post?.Comments
                                comment?.let { adapterComment.updateData(it) }
                            }
                            is Resource.Loading -> {}
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        }
    }

    override fun onSuccess(data: DetailPostResponse) {
        comment = data.post.Comments
        comment?.let { adapterComment.submitList(it) }

        postId = data.post.id.toString()

        val timeAgo = TimeStampHelper.getTimeAgo(data.post.created_at)
        val stringBuilder = data.post.User.avatar?.let { StringBuilder(it) }
        val fixString = stringBuilder?.replace(11, 12, "/").toString()

        binding.apply {
            recyclerView.visibility = visible
            post.visibility = visible
            errorText.visibility = invisible
            progressBar.visibility = invisible

            fullName.text = data.post.User.name
            username.text = data.post.User.username
            date.text = timeAgo
            postTitle.text = data.post.title

            if (data.post.User.id.toString() == userId) {
                bookmark.visibility = gone
            } else {
                bookmark.visibility = visible

                bookmark.setOnClickListener {
                    bookmarkPost()
                }
            }

            if (data.post.User.avatar == null) {
                profilePhoto.setImageResource(R.mipmap.profile_photo_round)
            } else {
                Glide.with(this@DetailPostActivity)
                    .load(Constanta.URL_IMAGE + fixString)
                    .into(profilePhoto)
            }

            if (SDK_INT >= Build.VERSION_CODES.N) {
                postContent.text =
                    Html.fromHtml(data.post.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else if (SDK_INT < Build.VERSION_CODES.N) {
                postContent.text = Html.fromHtml(data.post.content)
            }

            if (data.post.like.isNotEmpty()) {
                likeCounter.text = data.post.like.size.toString()
            } else {
                likeCounter.text = "0"
            }

            if (data.liked) {
                likeButton.setImageResource(R.drawable.ic_unlike)
            } else {
                likeButton.setImageResource(R.drawable.ic_like)
            }

            if (data.bookmarked) {
                bookmark.setImageResource(R.drawable.ic_unbookmark)
            } else {
                bookmark.setImageResource(R.drawable.ic_bookmark)
            }

            if (data.post.Comments.isEmpty()) {
                commentCounter.text = "0"
            } else {
                commentCounter.text = data.post.Comments.size.toString()
            }

            shareButton.setOnClickListener {
                startActivity(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, data.post.title)
                        putExtra(Intent.EXTRA_TEXT, "${data.post.title}\n\n${data.post.content}")
                    }
                )
            }

            likeButton.setOnClickListener {
                likePost()
            }

        }
    }

    private fun bookmarkPost() {
        CoroutineScope(Dispatchers.Main).launch {
            postId?.let {
                token?.let { it1 ->
                    viewModel.bookmarkPost(it1, it).observe(this@DetailPostActivity) { post ->
                        when (post) {
                            is Resource.Success -> {
                                binding.apply {
                                    progressBar.visibility = invisible

                                    if (post.data?.bookmarked == true) {
                                        bookmark.setImageResource(R.drawable.ic_unbookmark)
                                    } else {
                                        bookmark.setImageResource(R.drawable.ic_bookmark)
                                    }
                                }
                            }

                            is Resource.Loading -> {}

                            is Resource.Error -> {
                                Toast.makeText(
                                    this@DetailPostActivity,
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

    override fun onLoading() {
        binding.apply {
            progressBar.visibility = visible

            recyclerView.visibility = invisible
            errorText.visibility = invisible
            post.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            errorText.text = message
            errorText.visibility = visible

            recyclerView.visibility = invisible
            progressBar.visibility = invisible
            post.visibility = invisible
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}