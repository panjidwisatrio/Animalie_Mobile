package com.panji.animalie.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.ui.createpost.ViewModelCreatePost
import jp.wasabeef.richeditor.RichEditor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object ImageManager {
    fun getImage(
        context: Context,
        activity: Activity,
        actionInsertImage: ImageButton,
        selectedImageUri: Uri,
        resultLauncher: ActivityResultLauncher<Intent>,
    ) {
        actionInsertImage.setOnClickListener {
            MaterialAlertDialogBuilder(context)
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
                            context,
                            readExternalStoragePermission,
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(readExternalStoragePermission),
                            Constanta.READ_STORAGE_PERMISSION_REQUEST_CODE
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

    fun uploadFile(
        context: Context,
        fileUri: Uri,
        viewModel: ViewModelCreatePost,
        sessionManager: SessionManager,
        lifecycleOwner: LifecycleOwner,
        editor: RichEditor,
    ) {
        val file = File(getRealPathFromUri(context, fileUri))
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("upload", file.name, requestBody)

        CoroutineScope(Dispatchers.Main).launch {
            DialogHelper.setUpDialog(context)

            viewModel.uploadImage(sessionManager.fetchToken()!!, filePart)
                .observe(lifecycleOwner) { data ->
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

                            editor.insertImage(data.data?.url.toString(), "Image")
                        }
                    }
                }
        }
    }

    private fun getRealPathFromUri(
        context: Context,
        uri: Uri,
    ): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            val filePath = it.getString(columnIndex)
            it.close()
            return filePath
        }
        return ""
    }
}