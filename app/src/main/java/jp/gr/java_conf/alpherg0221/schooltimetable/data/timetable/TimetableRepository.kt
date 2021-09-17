package jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable

import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.TableWithClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.Timetable
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {

    suspend fun initDatabase()

    fun observeAllClassInfo(): Flow<List<ClassInfo>>

    fun observeTableWithClassInfo(): Flow<List<TableWithClassInfo>>

    fun observeTimetable(dayOfWeek: String, period: String): Flow<Timetable>

    suspend fun getClassInfoBySubject(subject: String): ClassInfo?

    suspend fun setClassInfo(classInfo: ClassInfo, oldSubject: String)

    suspend fun setTimetable(timetable: Timetable)

    suspend fun deleteClassInfoAndTimetable(subject: String)
}