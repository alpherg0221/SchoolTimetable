package jp.gr.java_conf.alpherg0221.schooltimetable.data.settings.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTimeDao
import jp.gr.java_conf.alpherg0221.schooltimetable.model.AppTheme
import jp.gr.java_conf.alpherg0221.schooltimetable.data.settings.SettingsRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toComplement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    private val applicationContext: Context,
    private val classTimeDao: ClassTimeDao,
) : SettingsRepository {

    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dayOfWeekKey = stringSetPreferencesKey("day_of_week")
    private val periodKey = stringSetPreferencesKey("period")
    private val themeKey = stringPreferencesKey("theme")

    override fun observeDayOfWeek(): Flow<List<DayOfWeekType>> =
        applicationContext.settingsDataStore.data.map { timetable ->
            try {
                timetable[dayOfWeekKey]?.map(DayOfWeekType::valueOf)?.sortedBy { it.ordinal }
                    ?: emptyList()
            } catch (e: Throwable) {
                emptyList()
            }
        }

    override fun observePeriods(): Flow<List<PeriodType>> =
        applicationContext.settingsDataStore.data.map { timetable ->
            try {
                timetable[periodKey]?.map(PeriodType::valueOf)?.sortedBy { it.ordinal }
                    ?: emptyList()
            } catch (e: Throwable) {
                emptyList()
            }
        }

    override fun observeAllClassTime(): Flow<List<ClassTime>> =
        classTimeDao.getAllClassTime().map { it.toComplement() }

    override fun observeTheme(): Flow<AppTheme> =
        applicationContext.settingsDataStore.data.map { settings ->
            try {
                settings[themeKey]?.let(AppTheme::valueOf) ?: AppTheme.Default
            } catch (e: Throwable) {
                AppTheme.Default
            }
        }

    override suspend fun getClassTimeByPeriod(period: String): ClassTime? =
        withContext(Dispatchers.IO) {
            classTimeDao.getClassTimeByPeriod(period)
        }

    override suspend fun setDayOfWeek(set: Set<DayOfWeekType>) {
        val sSet = set.map { it.name }.toSet()
        applicationContext.settingsDataStore.edit { timetable ->
            timetable[dayOfWeekKey] = sSet
        }
    }

    override suspend fun setPeriod(set: Set<PeriodType>) {
        val sSet = set.map { it.name }.toSet()
        applicationContext.settingsDataStore.edit { timetable ->
            timetable[periodKey] = sSet
        }
    }

    override suspend fun setClassTime(classTime: ClassTime) {
        withContext(Dispatchers.IO) {
            classTimeDao.insert(classTime)
        }
    }

    override suspend fun setTheme(theme: AppTheme) {
        applicationContext.settingsDataStore.edit { settings ->
            settings[themeKey] = theme.name
        }
    }
}