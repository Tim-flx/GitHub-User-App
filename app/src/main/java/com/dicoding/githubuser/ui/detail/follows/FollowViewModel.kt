package com.dicoding.githubuser.ui.detail.follows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.ItemsItem
import com.dicoding.githubuser.database.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _listFollow = MutableLiveData<List<ItemsItem>>()
    val listFollow: LiveData<List<ItemsItem>> = _listFollow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun findFollow(query: String, apiFunc: (String) -> Call<List<ItemsItem>>) {
        _isLoading.value = true
        apiFunc(query).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollow.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchFollower(query: String) {
        findFollow(query) { ApiConfig.getApiService().getFollowers(query) }
    }

    fun searchFollowing(query: String) {
        findFollow(query) { ApiConfig.getApiService().getFollowing(query) }
    }

    companion object{
        private const val TAG = "FollowViewModel"
    }
}
