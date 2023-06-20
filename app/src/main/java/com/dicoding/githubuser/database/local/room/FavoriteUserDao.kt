package com.dicoding.githubuser.database.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuser.database.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT * FROM FavoriteUser ORDER BY username ASC")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>
}