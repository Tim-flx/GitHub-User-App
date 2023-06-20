package com.dicoding.githubuser.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.database.local.entity.FavoriteUser
import com.dicoding.githubuser.database.local.room.FavoriteUserDao
import com.dicoding.githubuser.database.local.room.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String) : LiveData<FavoriteUser> = mFavoriteUserDao.getFavoriteUserByUsername(username)

    fun getFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteUserDao.getFavoriteUser()
}