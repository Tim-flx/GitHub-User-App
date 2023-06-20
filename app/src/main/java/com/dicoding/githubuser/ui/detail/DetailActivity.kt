package com.dicoding.githubuser.ui.detail

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.githubuser.*
import com.dicoding.githubuser.database.local.entity.FavoriteUser
import com.dicoding.githubuser.database.remote.response.DetailUserResponse
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.dicoding.githubuser.ui.ViewModelFactory
import com.dicoding.githubuser.ui.detail.follows.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(application)
    }
    private var myMenuItem: MenuItem? = null
    private var mDataUser : ItemsItem? = null
    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        title = "Detail Github User"

        val dataUsers = intent.getParcelableExtra<ItemsItem>(KEY_USER)
        mDataUser = dataUsers

        detailViewModel.dataUser.observe(this) { dataUser -> setUserData(dataUser) }
        detailViewModel.isLoading.observe(this) { showLoading(it) }
        detailViewModel.isFavorited.observe(this) { updateIcon(it) }

        dataUsers?.login?.let { detailViewModel.searchUser(it) }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = dataUsers?.login.toString()
        binding?.viewPager?.adapter = sectionsPagerAdapter
        binding?.tabs?.let { tabs ->
            binding?.viewPager?.let { viewPager ->
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        myMenuItem = menu.findItem(R.id.favorite)
        mDataUser?.login?.let { detailViewModel.getFavoriteUserByUsername(it) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                mDataUser?.login?.let { nama ->
                    if (detailViewModel.isFavorited.value == false) {
                        detailViewModel.insert(FavoriteUser(username = nama, avatarUrl = mDataUser?.avatarUrl))

                    } else if (detailViewModel.isFavorited.value == true) {
                        detailViewModel.delete(FavoriteUser(username = nama, avatarUrl = mDataUser?.avatarUrl))
                    }
                    detailViewModel.getFavoriteUserByUsername(nama)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }

    private fun setUserData(userData: DetailUserResponse) {
        binding?.tvName?.text = userData.name
        binding?.tvUsername?.text = userData.login
        binding?.tvFollowers?.text = getString(R.string.followers, userData.followers)
        binding?.tvFollowing?.text = getString(R.string.following, userData.following)
        Picasso.get().load(userData.avatarUrl).into(binding?.ivProfilePicture)
    }

    private fun updateIcon(isFavorited: Boolean){
        if (isFavorited) {
            myMenuItem?.setIcon(R.drawable.ic_baseline_favorite_fill)
        } else {
            myMenuItem?.setIcon(R.drawable.ic_baseline_favorite_border)
        }
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val KEY_USER = "key_user"
    }
}