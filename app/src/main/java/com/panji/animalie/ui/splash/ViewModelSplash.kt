package com.panji.animalie.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.panji.animalie.data.repo.UserRepository
import kotlinx.coroutines.launch

class ViewModelSplash(application: Application): AndroidViewModel(application) {
    private val repository = UserRepository(application)

    fun saveThemeSetting(isDarkModeActive: Boolean) = viewModelScope.launch {
        repository.saveThemeSetting(isDarkModeActive)
    }

    fun getThemeSetting() = repository.getThemeSetting().asLiveData()
}