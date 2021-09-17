package jp.gr.java_conf.alpherg0221.schooltimetable.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TimetableDao {
    // initialize
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun initTimetable(timetable: Timetable)

    // select
    @Query("SELECT * FROM class_info")
    abstract fun getAllClassInfo(): Flow<List<ClassInfo>>

    @Query("SELECT * FROM class_info WHERE subject = :subject")
    abstract fun getClassInfoBySubject(subject: String): ClassInfo?

    @Query(
        "SELECT dayOfWeek, period, subject, classroom, teacher, memo, color " +
                "FROM timetable LEFT OUTER JOIN class_info " +
                "ON class_info.subject = timetable.name"
    )
    abstract fun getAllTimetable(): Flow<List<TableWithClassInfo>>

    @Query("SELECT * FROM timetable WHERE dayOfWeek = :dayOfWeek AND period = :period")
    abstract fun getTimetableByDayOfWeekAndPeriod(
        dayOfWeek: String,
        period: String
    ): Flow<Timetable>


    // update
    @Query("UPDATE timetable SET name = :newSubject WHERE name = :oldSubject")
    abstract suspend fun updateClassInfoFromTimetable(newSubject: String, oldSubject: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertClassInfo(classInfo: ClassInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTimetable(timetable: Timetable)

    @Transaction
    open suspend fun updateClassInfoAndTimetable(classInfo: ClassInfo, oldSubject: String) {
        updateClassInfoFromTimetable(classInfo.subject, oldSubject)
        deleteClassInfoBySubject(oldSubject)
        insertClassInfo(classInfo)
    }

    // delete

    @Query("DELETE FROM class_info WHERE subject = :subject")
    abstract suspend fun deleteClassInfoBySubject(subject: String)

    @Query("UPDATE timetable SET name = null WHERE name = :subject")
    abstract suspend fun deleteClassInfoFromTimetable(subject: String)

    @Delete
    abstract suspend fun deleteClassInfo(classInfo: ClassInfo)

    @Transaction
    open suspend fun deleteClassInfoAndTimetable(subject: String) {
        deleteClassInfoFromTimetable(subject)
        deleteClassInfoBySubject(subject)
    }
}