package com.panji.animalie.ui.createpost

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.databinding.ActivityCreatePostBinding
import com.panji.animalie.model.response.CreatePostResponse
import com.panji.animalie.util.Constanta.READ_STORAGE_PERMISSION_REQUEST_CODE
import com.panji.animalie.util.DialogHelper
import com.panji.animalie.util.EditorTextHelper
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Locale


class CreatePostActivity : AppCompatActivity(), ViewStateCallback<CreatePostResponse> {

    private lateinit var binding: ActivityCreatePostBinding
    private val viewModel by viewModels<ViewModelCreatePost>()
    private val sessionManager: SessionManager by lazy { SessionManager(this) }

    private var categoryMap: Map<String, String> = emptyMap()
    private var tagMap: MutableMap<String, Int> = mutableMapOf()
    private var tags: ArrayList<String> = arrayListOf()
    private var removedChip: MutableMap<String, Int> = mutableMapOf()

    private var selectedImageUri: Uri? = null
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
        getImage()
        setWYSIWYG()
        setAllTextField()
    }

    private fun getImage() {
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    selectedImageUri = result.data?.data
                    uploadFile(selectedImageUri!!)
                }
            }

        binding.actionInsertImage.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Insert Image")
                .setMessage("Do you want to insert image from gallery or camera?")
                .setPositiveButton("Gallery") { _, _ ->
                    val readExternalStoragePermission =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Manifest.permission.READ_MEDIA_IMAGES
                        } else {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        }

                    // Jika izin belum diberikan, minta izin kepada pengguna
                    if (ContextCompat.checkSelfPermission(
                            this,
                            readExternalStoragePermission,
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(readExternalStoragePermission),
                            READ_STORAGE_PERMISSION_REQUEST_CODE
                        )
                    }

                    // Jika izin telah diberikan, lanjutkan dengan mengunggah file
                    else {
                        resultLauncher.launch(
                            Intent(Intent.ACTION_PICK).also {
                                it.type = "image/*"
                                val mimeType = arrayOf("image/jpeg", "image/png")
                                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                            }
                        )
                    }
                }
                .setNegativeButton("Camera") { _, _ ->
                    resultLauncher.launch(
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                            it.type = "image/*"
                            it.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri)
                        }
                    )
                }
                .show()
        }
    }

    private fun uploadFile(fileUri: Uri) {
        // Buat objek File dari Uri
        val file = File(getRealPathFromUri(fileUri))

        // Buat instance RequestBody
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        // Buat instance MultipartBody.Part menggunakan nama file asli
        val filePart = MultipartBody.Part.createFormData("upload", file.name, requestBody)

        // Lakukan pemanggilan asinkron ke server melalui view model
        CoroutineScope(Dispatchers.Main).launch {
            DialogHelper.setUpDialog(this@CreatePostActivity)

            viewModel.uploadImage(sessionManager.fetchToken()!!, filePart)
                .observe(this@CreatePostActivity) { data ->
                    when (data) {
                        is Resource.Error -> {
                            DialogHelper.showLoadingDialog("Uploading image...").dismiss()
                            DialogHelper.showErrorDialog("Failed to upload image").show()
                        }

                        is Resource.Loading -> {
                            DialogHelper.showLoadingDialog("Uploading image...").show()
                        }

                        is Resource.Success -> {
                            DialogHelper.showLoadingDialog("Uploading image...").dismiss()
                            DialogHelper.showSuccessDialog("Image uploaded successfully").show()

                            binding.editor.insertImage(data.data?.url.toString(), "Image")
                        }
                    }
                }
        }
    }

    private fun getRealPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            val filePath = it.getString(columnIndex)
            it.close()
            return filePath
        }
        return ""
    }

    private fun setWYSIWYG() {
        EditorTextHelper.setEditorWasabeef(
            binding.editor,
            binding.actionUndo,
            binding.actionRedo,
            binding.actionBold,
            binding.actionItalic,
            binding.actionUnderline,
            binding.actionStrikethrough,
            binding.actionHeading1,
            binding.actionHeading2,
            binding.actionHeading3,
            binding.actionHeading4,
            binding.actionIndent,
            binding.actionOutdent,
            binding.actionAlignLeft,
            binding.actionAlignCenter,
            binding.actionAlignRight,
            binding.actionBlockquote,
            binding.actionInsertBullets,
            binding.actionInsertNumbers,
            binding.actionInsertLink,
            binding.actionBgColor,
            binding.actionTxtColor,
            this
        )
    }

    private fun getCategoryAndTag() {
        val token = sessionManager.fetchToken()

        CoroutineScope(Dispatchers.Main).launch {
            token?.let {
                viewModel.getCategoriesAndTags(it).observe(this@CreatePostActivity) { data ->
                    when (data) {
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
            editor.setOnTextChangeListener {
                contents = editor.html
            }

            tagLayout.setEndIconOnClickListener {
                val tagName = tagLayout.editText?.text.toString()
                if (tagName.isNotEmpty()) {
                    addNewTag(tagName, tagLayout.editText)
                }
            }
        }
    }

    private fun addNewTag(tagName: String, editText: EditText? = null) {
        CoroutineScope(Dispatchers.Main).launch {
            val token = sessionManager.fetchToken()
            token?.let {
                viewModel.storeTag(it, tagName).observe(this@CreatePostActivity) { data ->
                    when (data) {
                        is Resource.Error -> onFailed(data.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> {
                            data.data?.let { it1 ->
                                removedChip[tagName] = it1.tags.id
                                tagMap[tagName]?.let { it2 -> tags.add(it2.toString()) }
                                addChipToGroup(tagName)
                                editText?.text?.clear()
                            }

                            binding.apply {
                                title.isEnabled = true
                                category.isEnabled = true
                                tag.isEnabled = true
                                editor.isEnabled = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addChipToGroup(tagName: String) {
        val chip = Chip(this)
        chip.text = tagName
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chip.isCheckable = false
        binding.chipGroup.addView(chip as View)

        chip.setOnCloseIconClickListener {
            tagMap[tagName] = removedChip[tagName]!!
            removedChip.remove(tagName)

            binding.chipGroup.removeView(chip as View)
            tags.remove(tagMap[tagName].toString())
            setTagDropdown()
            Toast.makeText(this, "${chip.text} has been removed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createSlug(titles: String): String {
        return titles
            .replace(" ", "-")
            .replace("[^a-zA-Z0-9\\-]".toRegex(), "")
            .lowercase(Locale.getDefault())
    }

    private fun setTagDropdown() {
        val tagAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, tagMap.keys.toList())
        (binding.tag as? AutoCompleteTextView)?.setAdapter(tagAdapter)

        binding.tag.apply {
            setOnClickListener {
                showDropDown()
            }

            setOnItemClickListener { _, _, _, _ ->
                val nameTag = binding.tag.text.toString()
                val selectedTag = tagMap[nameTag]
                tags.add(selectedTag.toString())
                selectedTag?.let { addChipToGroup(nameTag) }

                if (selectedTag != null) {
                    removedChip[nameTag] = selectedTag
                }
                tagMap.remove(nameTag)

                binding.tag.setText("")
                setTagDropdown()
            }
        }
    }

    private fun setCategoryDropdown() {
        val categoryAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryMap.keys.toList())
        binding.category.setAdapter(categoryAdapter)
        binding.category.setOnItemClickListener { _, _, position, _ ->
            categoryIds = categoryMap[categoryMap.keys.toList()[position]].toString()
        }
    }

    override fun onSuccess(data: CreatePostResponse) {
        categoryMap = data.categories.associate { it.category to it.id.toString() }
        tagMap = data.tags.associate { it.name_tag to it.id }.toMutableMap()

        binding.apply {
            title.isEnabled = true
            category.isEnabled = true
            tag.isEnabled = true
            editor.isEnabled = true
        }

        setCategoryDropdown()
        setTagDropdown()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_create_post, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }

            R.id.create_post -> {
                binding.apply {
                    titles = title.text.toString()
                    slugs = createSlug(titles)
                }

                if (titles.isEmpty() || slugs.isEmpty() || categoryIds.isEmpty() || contents.isEmpty()) {
                    MaterialAlertDialogBuilder(this@CreatePostActivity)
                        .setTitle("Error")
                        .setMessage("Please fill all the fields")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                } else {
                    val token = sessionManager.fetchToken()
                    CoroutineScope(Dispatchers.Main).launch {
                        token?.let {
                            viewModel.createPost(
                                it,
                                titles,
                                slugs,
                                categoryIds,
                                contents,
                                tags.toList(),
                            ).observe(this@CreatePostActivity) { data ->
                                when (data) {
                                    is Resource.Error -> onFailed(data.message)
                                    is Resource.Loading -> onLoading()
                                    is Resource.Success -> {
                                        data.data?.let {
                                            Toast.makeText(
                                                this@CreatePostActivity,
                                                "Post has been created",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            finish()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
