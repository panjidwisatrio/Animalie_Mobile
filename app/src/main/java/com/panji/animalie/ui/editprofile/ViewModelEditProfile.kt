package com.panji.animalie.ui.editprofile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.panji.animalie.data.repo.UserRepository

class ViewModelEditProfile(application: Application) : AndroidViewModel(application) {

    val repository = UserRepository(application)

    suspend fun sendEditedProfile(
        token: String,
        name: String,
        username: String,
        work_place: String? = null,
        job_position: String? = null,
        email: String,
        avatar: String? = null
    ) = repository.sendEditedProfile(
        token,
        name,
        username,
        work_place,
        job_position,
        email,
        avatar
    )
}