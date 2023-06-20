package com.dicoding.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.local.entity.FavoriteUser
import com.dicoding.githubuser.database.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getFavoriteUser()
}