package com.techsultan.jobber.db

import android.content.Context
import androidx.room.*
import com.techsultan.jobber.models.FavoriteJob


@Database(entities = [FavoriteJob::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase() {

    abstract fun getJobDao(): FavoriteJobDao

    companion object {

        @Volatile
        private var INSTANCE : JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase {
            return  INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JobDatabase::class.java,
                    "article_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}