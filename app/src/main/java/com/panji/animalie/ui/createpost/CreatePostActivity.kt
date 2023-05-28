package com.panji.animalie.ui.createpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.data.Resource
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.databinding.ActivityCreatePostBinding
import com.panji.animalie.model.response.CreatePostResponse
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class CreatePostActivity : AppCompatActivity(), ViewStateCallback<CreatePostResponse> {

    private lateinit var binding: ActivityCreatePostBinding
    private val viewModel by viewModels<ViewModelCreatePost>()
    private val sessionManager: SessionManager by lazy {
        SessionManager(this)
    }

    private var categoryMap: Map<String, String> = emptyMap()
    private var tagMap: Map<String, String> = emptyMap()

    private var titles = ""
    private var slugs = ""
    private var categoryIds = ""
    private var contents = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.createPostToolbar.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Create Post"

        getCategoryAndTag()
        setWYSIWYG()
        setAllTextField()
    }

    private fun setWYSIWYG() {
        binding.editor.apply {
            setEditorHeight(200)
            setPadding(16, 10, 16, 10)
            setPlaceholder("Content...")
        }
    }

    private fun getCategoryAndTag() {
        val token = sessionManager.fetchToken()

        CoroutineScope(Dispatchers.Main).launch {
            token?.let {
                viewModel.getCategoriesAndTags(it).observe(this@CreatePostActivity) { data ->
                    when(data) {
                        is Resource.Error -> onFailed(data.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> data.data?.let { it1 -> onSuccess(it1) }
                    }
                }
            }
        }
    }

    private fun setAllTextField() {
        binding.apply {
            titles = title.text.toString()
            slugs = createSlug(titles)
        }
    }

    private fun createSlug(titles: String): String {
        return titles
            .replace(" ", "-")
            .replace("[^a-zA-Z0-9\\-]".toRegex(), "")
            .lowercase(Locale.getDefault())
    }

    override fun onSuccess(data: CreatePostResponse) {
        categoryMap = data.categories.associate { it.category to it.id.toString() }
        tagMap = data.tags.associate { it.name_tag to it.id.toString() }

        binding.apply {
            title.isEnabled = true
            category.isEnabled = true
            tag.isEnabled = true
            editor.isEnabled = true
        }

        setCategoryDropdown()
    }

    private fun setCategoryDropdown() {
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryMap.keys.toList())
        binding.category.setAdapter(categoryAdapter)
        binding.category.setOnItemClickListener { _, _, position, _ ->
            categoryIds = categoryMap[categoryMap.keys.toList()[position]].toString()
        }
    }

    override fun onLoading() {
        binding.apply {
            title.isEnabled = false
            category.isEnabled = false
            tag.isEnabled = false
            editor.isEnabled = false
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            title.isEnabled = false
            category.isEnabled = false
            tag.isEnabled = false
            editor.isEnabled = false
        }

        message?.let {
            MaterialAlertDialogBuilder(this@CreatePostActivity)
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
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