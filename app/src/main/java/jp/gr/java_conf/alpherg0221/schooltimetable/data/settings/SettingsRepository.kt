package jp.gr.java_conf.alpherg0221.schooltimetable.data.settings

import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.model.AppTheme
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun observeDayOfWeek(): Flow<List<DayOfWeekType>>

    fun observePeriods(): Flow<List<PeriodType>>

    fun observeAllClassTime(): Flow<List<ClassTime>>

    fun observeTheme(): Flow<AppTheme>

    suspend fun getClassTimeByPeriod(period: String): ClassTime?

    suspend fun setDayOfWeek(set: Set<DayOfWeekType>)

    suspend fun setPeriod(set: Set<PeriodType>)

    suspend fun setClassTime(classTime: ClassTime)

    suspend fun setTheme(theme: AppTheme)
}