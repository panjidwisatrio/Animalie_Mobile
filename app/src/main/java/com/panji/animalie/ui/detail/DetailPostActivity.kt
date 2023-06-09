package com.panji.animalie.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.ActivityDetailPostBinding
import com.panji.animalie.model.response.DetailPostResponse
import com.panji.animalie.util.Constanta
import com.panji.animalie.util.Constanta.EXTRA_POST
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class DetailPostActivity : AppCompatActivity(), ViewStateCallback<DetailPostResponse> {

    private lateinit var binding: ActivityDetailPostBinding
    private val viewModel by viewModels<ViewModelDetailPost>()
    private val sessionManager by lazy { SessionManager(this) }

    private var postId: String? = null
    private val token by lazy { sessionManager.fetchToken() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Post"

        getPost()
    }

    private fun getPost() {
        val slug = intent.getStringExtra(EXTRA_POST)
        val userId = sessionManager.fetchId()

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

    override fun onSuccess(data: DetailPostResponse) {
        postId = data.post.id.toString()

        val timeAgo = getTimeAgo(data.post.created_at)
        val stringBuilder = data.post.User.avatar?.let { StringBuilder(it) }
        val fixString = stringBuilder?.replace(11, 12, "/").toString()

        binding.apply {
            post.visibility = visible
            errorText.visibility = invisible
            progressBar.visibility = invisible

            fullName.text = data.post.User.name
            username.text = data.post.User.username
            date.text = timeAgo
            postTitle.text = data.post.title

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

            Log.d("DetailPostActivity", "onSuccess: ${data.liked}")

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

            bookmark.setOnClickListener {
                bookmarkPost()
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

    override fun onLoading() {
        binding.apply {
            progressBar.visibility = visible

            errorText.visibility = invisible
            post.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            errorText.text = message
            errorText.visibility = visible

            progressBar.visibility = invisible
            post.visibility = invisible
        }
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