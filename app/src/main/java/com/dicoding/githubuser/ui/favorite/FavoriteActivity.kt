package com.dicoding.githubuser.ui.favorite

import com.dicoding.githubuser.ListUserAdapter

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.EmptyAdapter
import com.dicoding.githubuser.ItemsItem
import com.dicoding.githubuser.ui.ViewModelFactory
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private val favoriteViewModel by viewModels<FavoriteViewModel>{
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Favorite Github User"

        favoriteViewModel.getFavoriteUser().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            binding.rvFavorite.adapter = ListUserAdapter(items)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val emptyAdapter = EmptyAdapter()
        binding.rvFavorite.adapter = emptyAdapter
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}