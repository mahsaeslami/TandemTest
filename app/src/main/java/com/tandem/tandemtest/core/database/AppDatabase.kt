package com.tandem.tandemtest.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tandem.tandemtest.data.community.local.datasource.CommunityDao
import com.tandem.tandemtest.data.community.local.model.CommunityTable

@Database(entities = [CommunityTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun communityDao(): CommunityDao

    companion object {
        private const val DATABASE_NAME = "tandem.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(mContext: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabaseInstance(mContext).also {
                    instance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
