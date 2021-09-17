package jp.gr.java_conf.alpherg0221.schooltimetable.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassTimeDao {

    @Query("SELECT * FROM class_time")
    fun getAllClassTime(): Flow<List<ClassTime>>

    @Query("SELECT * FROM class_time WHERE period = :period")
    fun getClassTimeByPeriod(period: String): ClassTime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(classTime: ClassTime)

    @Delete
    suspend fun delete(classTime: ClassTime)
}