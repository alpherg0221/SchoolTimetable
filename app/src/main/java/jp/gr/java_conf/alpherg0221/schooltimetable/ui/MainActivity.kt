package jp.gr.java_conf.alpherg0221.schooltimetable.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.WindowCompat
import jp.gr.java_conf.alpherg0221.schooltimetable.SchoolTimetableApplication
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.Timetable
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val appContainer = (application as SchoolTimetableApplication).container

        setContent {
            SchoolTimetableApp(appContainer)
        }
    }
}