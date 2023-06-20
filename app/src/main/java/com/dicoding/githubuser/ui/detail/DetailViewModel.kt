package com.dicoding.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.local.entity.FavoriteUser
import com.dicoding.githubuser.database.remote.api.ApiConfig
import com.dicoding.githubuser.database.remote.response.DetailUserResponse
import com.dicoding.githubuser.database.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _dataUser = MutableLiveData<DetailUserResponse>()
    val dataUser: LiveData<DetailUserResponse> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> = _isFavorited

    private fun findUserData(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dataUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String) {
        val favoriteUserLiveData : LiveData<FavoriteUser> = mFavoriteUserRepository.getFavoriteUserByUsername(username)
        favoriteUserLiveData.observeForever { favoriteUser ->
            _isFavorited.value = favoriteUser != null
        }
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun searchUser(query: String) {
        findUserData(query)
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}
