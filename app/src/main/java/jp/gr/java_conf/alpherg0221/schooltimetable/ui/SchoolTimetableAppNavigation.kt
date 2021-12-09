package jp.gr.java_conf.alpherg0221.schooltimetable.ui

import android.content.Intent
import android.net.Uri
import androidx.navigation.NavHostController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoActionMode


object MainDestinations {
    const val HOME_ROUTE = "home"
    const val SETTINGS_ROUTE = "settings"
    const val APP_INFO_ROUTE = "app_info"
    const val CLASS_TIME_ROUTE = "class_time"
    const val CLASS_LIST_SELECT_ROUTE = "class_list_select"
    const val CLASS_LIST_EDIT_ROUTE = "class_list_edit"
    const val CLASS_INFO_ROUTE = "class_info"
}

class MainActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(MainDestinations.HOME_ROUTE)
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(MainDestinations.SETTINGS_ROUTE)
    }
    val navigateToAppInfo: () -> Unit = {
        navController.navigate(MainDestinations.APP_INFO_ROUTE)
    }
    val navigateToClassTime: (PeriodType) -> Unit = { period ->
        navController.navigate("${MainDestinations.CLASS_TIME_ROUTE}/${period.name}")
    }
    val navigateToClassListSelect: (DayOfWeekType, PeriodType) -> Unit = { dayOfWeek, period ->
        navController.navigate("${MainDestinations.CLASS_LIST_SELECT_ROUTE}/${dayOfWeek.name}/${period.name}")
    }
    val navigateToClassInfo: (ClassInfoActionMode, String) -> Unit = { type, subject ->
        navController.navigate("${MainDestinations.CLASS_INFO_ROUTE}/${type.name}/$subject")
    }
    val navigateToClassListEdit: () -> Unit = {
        navController.navigate(MainDestinations.CLASS_LIST_EDIT_ROUTE)
    }
    val navigateToOSS: () -> Unit = {
        val context = navController.context
        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
    }
    val navigateToPrivacyPolicy: () -> Unit = {
        val context = navController.context
        context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://alpherg0221.github.io/SchoolTimetable/")))
    }
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
}