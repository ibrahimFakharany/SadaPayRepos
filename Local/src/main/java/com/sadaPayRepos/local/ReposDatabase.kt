package com.sadaPayRepos.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sadaPayRepos.model.GithubRepo
import com.sadaPayRepos.model.GithubReposRemoteKeys

@Database(
    entities = [GithubRepo::class, GithubReposRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class ReposDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): ReposDatabase {
            val databaseBuilder =
                Room.databaseBuilder(context, ReposDatabase::class.java, "repos_database.db")
            return databaseBuilder.build()
        }
    }

    abstract fun getReposDao(): GithubRepos
    abstract fun getKeysDao(): RemoteKeysDao
}