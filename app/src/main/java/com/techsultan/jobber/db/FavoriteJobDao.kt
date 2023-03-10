package com.techsultan.jobber.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.techsultan.jobber.models.FavoriteJob
import com.techsultan.jobber.models.Job


@Dao
interface FavoriteJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavJob(job: FavoriteJob)

    @Query("SELECT * FROM fav_job ORDER BY id DESC")
    fun getAllJob(): LiveData<FavoriteJob>

    @Delete
    suspend fun deleteJob(job: FavoriteJob)
}