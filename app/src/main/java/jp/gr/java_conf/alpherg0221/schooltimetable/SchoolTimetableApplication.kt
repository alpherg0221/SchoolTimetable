package jp.gr.java_conf.alpherg0221.schooltimetable

import android.app.Application
import jp.gr.java_conf.alpherg0221.schooltimetable.data.AppContainer
import jp.gr.java_conf.alpherg0221.schooltimetable.data.AppContainerImpl

class SchoolTimetableApplication : Application() {

    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}