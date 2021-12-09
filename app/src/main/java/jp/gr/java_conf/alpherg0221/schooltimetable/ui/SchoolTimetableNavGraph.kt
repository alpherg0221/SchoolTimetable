package jp.gr.java_conf.alpherg0221.schooltimetable.ui

import androidx.compose.animation.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import jp.gr.java_conf.alpherg0221.schooltimetable.data.AppContainer
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoActionMode
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoViewModel
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.edit.ClassListEditScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.edit.ClassListEditViewModel
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.select.ClassListSelectScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.select.ClassListSelectViewModel
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classtime.ClassTimeScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classtime.ClassTimeViewModel
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.home.HomeScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.home.HomeViewModel
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings.SettingsScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings.SettingsViewModel
import kotlinx.coroutines.launch

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val SETTINGS_ROUTE = "settings"
    const val CLASS_TIME_ROUTE = "class_time"
    const val CLASS_INFO_LIST_SELECT_ROUTE = "class_info_list_select"
    const val CLASS_INFO_LIST_EDIT_ROUTE = "class_info_list_edit"
    const val CLASS_INFO_ROUTE = "class_info"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SchoolTimetableNavGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberAnimatedNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    startDestination: String = MainDestinations.HOME_ROUTE,
) {
    val actions = remember(navController) { MainActions(navController) }
    val coroutineScope = rememberCoroutineScope()
    val openDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(MainDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(
                    settingsRepository = appContainer.settingsRepository,
                    timetableRepository = appContainer.timetableRepository
                )
            )
            HomeScreen(
                homeViewModel = homeViewModel,
                openDrawer = openDrawer,
                navigateToClassInfoListSelect = { dayOfWeek, period ->
                    navController.navigate(
                        "${MainDestinations.CLASS_INFO_LIST_SELECT_ROUTE}/${dayOfWeek.name}/${period.name}"
                    )
                },
                navigateToClassInfo = { type, subject ->
                    navController.navigate("${MainDestinations.CLASS_INFO_ROUTE}/${type.name}/$subject")
                },
            )
        }

        composable(MainDestinations.SETTINGS_ROUTE) {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModel.provideFactory(
                    settingsRepository = appContainer.settingsRepository
                )
            )
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                onBack = actions.upPress,
                navigateToClassTime = { period ->
                    navController.navigate("${MainDestinations.CLASS_TIME_ROUTE}/${period.name}")
                }
            )
        }

        composable("${MainDestinations.CLASS_TIME_ROUTE}/{period}") { backStackEntry ->
            val classTimeViewModel: ClassTimeViewModel = viewModel(
                factory = ClassTimeViewModel.provideFactory(
                    settingsRepository = appContainer.settingsRepository,
                    period = PeriodType.valueOf(backStackEntry.arguments?.getString("period")!!),
                )
            )
            ClassTimeScreen(
                classTimeViewModel = classTimeViewModel,
                onBack = actions.upPress,
            )
        }

        composable(
            "${MainDestinations.CLASS_INFO_LIST_SELECT_ROUTE}/{dayOfWeek}/{period}"
        ) { backStackEntry ->
            val classListSelectViewModel: ClassListSelectViewModel = viewModel(
                factory = ClassListSelectViewModel.provideFactory(
                    timetableRepository = appContainer.timetableRepository,
                    dayOfWeek = DayOfWeekType.valueOf(backStackEntry.arguments?.getString("dayOfWeek")!!),
                    period = PeriodType.valueOf(backStackEntry.arguments?.getString("period")!!),
                )
            )
            ClassListSelectScreen(
                classListSelectViewModel = classListSelectViewModel,
                onBack = actions.upPress,
                navigateToClassInfo = { type, subject ->
                    navController.navigate("${MainDestinations.CLASS_INFO_ROUTE}/${type.name}/$subject")
                },
            )
        }

        composable(MainDestinations.CLASS_INFO_LIST_EDIT_ROUTE) {
            val classListEditViewModel: ClassListEditViewModel = viewModel(
                factory = ClassListEditViewModel.provideFactory(
                    timetableRepository = appContainer.timetableRepository,
                )
            )
            ClassListEditScreen(
                classListEditViewModel = classListEditViewModel,
                onBack = actions.upPress,
                navigateToClassInfo = { type, subject ->
                    navController.navigate("${MainDestinations.CLASS_INFO_ROUTE}/${type.name}/$subject")
                },
            )
        }

        composable("${MainDestinations.CLASS_INFO_ROUTE}/{mode}/{subject}") { backStackEntry ->
            val classInfoViewModel: ClassInfoViewModel = viewModel(
                factory = ClassInfoViewModel.provideFactory(
                    timetableRepository = appContainer.timetableRepository,
                    mode = ClassInfoActionMode.valueOf(backStackEntry.arguments?.getString("mode")!!),
                    subject = backStackEntry.arguments?.getString("subject")!!,
                )
            )
            ClassInfoScreen(
                classInfoViewModel = classInfoViewModel,
                onBack = actions.upPress,
            )
        }
    }
}

class MainActions(navController: NavHostController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
