package jp.gr.java_conf.alpherg0221.schooltimetable.ui

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
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
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.info.InfoScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.info.InfoViewModel
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings.SettingsScreen
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings.SettingsViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SchoolTimetableNavGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberAnimatedNavController(),
    openDrawer: () -> Unit = {},
    onBack: () -> Unit = {},
    navigationActions: MainActions,
    startDestination: String = MainDestinations.HOME_ROUTE,
) {
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
                navigateToClassListSelect = navigationActions.navigateToClassListSelect,
                navigateToClassInfo = navigationActions.navigateToClassInfo,
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
                onBack = onBack,
                navigateToClassTime = navigationActions.navigateToClassTime,
            )
        }

        composable(MainDestinations.APP_INFO_ROUTE) {
            val infoViewModel: InfoViewModel = viewModel(
                factory = InfoViewModel.provideFactory()
            )
            InfoScreen(
                infoViewModel = infoViewModel,
                navigateToOSS = navigationActions.navigateToOSS,
                navigateToPrivacyPolicy = navigationActions.navigateToPrivacyPolicy,
                onBack = onBack,
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
                onBack = onBack,
            )
        }

        composable(
            "${MainDestinations.CLASS_LIST_SELECT_ROUTE}/{dayOfWeek}/{period}"
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
                onBack = onBack,
                navigateToClassInfo = navigationActions.navigateToClassInfo,
            )
        }

        composable(MainDestinations.CLASS_LIST_EDIT_ROUTE) {
            val classListEditViewModel: ClassListEditViewModel = viewModel(
                factory = ClassListEditViewModel.provideFactory(
                    timetableRepository = appContainer.timetableRepository,
                )
            )
            ClassListEditScreen(
                classListEditViewModel = classListEditViewModel,
                onBack = onBack,
                navigateToClassInfo = navigationActions.navigateToClassInfo,
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
                onBack = onBack,
            )
        }
    }
}
