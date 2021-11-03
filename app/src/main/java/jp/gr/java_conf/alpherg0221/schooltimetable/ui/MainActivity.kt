package jp.gr.java_conf.alpherg0221.schooltimetable.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import jp.gr.java_conf.alpherg0221.schooltimetable.SchoolTimetableApplication

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