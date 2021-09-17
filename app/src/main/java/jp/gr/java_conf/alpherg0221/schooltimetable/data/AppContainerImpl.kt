package jp.gr.java_conf.alpherg0221.schooltimetable.data

import android.content.Context
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.SchoolTimetableDatabase
import jp.gr.java_conf.alpherg0221.schooltimetable.data.settings.SettingsRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.data.settings.impl.SettingsRepositoryImpl
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.impl.TimetableRepositoryImpl

interface AppContainer {
    val database: SchoolTimetableDatabase
    val timetableRepository: TimetableRepository
    val settingsRepository: SettingsRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val database: SchoolTimetableDatabase =
        SchoolTimetableDatabase.getDatabase(applicationContext)

    override val timetableRepository: TimetableRepository by lazy {
        TimetableRepositoryImpl(database.timetableDao())
    }

    override val settingsRepository: SettingsRepository by lazy {
        SettingsRepositoryImpl(
            applicationContext,
            database.classTimeDao(),
        )
    }

}