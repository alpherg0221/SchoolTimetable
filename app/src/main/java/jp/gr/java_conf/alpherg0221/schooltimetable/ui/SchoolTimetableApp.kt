package jp.gr.java_conf.alpherg0221.schooltimetable.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import jp.gr.java_conf.alpherg0221.schooltimetable.model.AppTheme
import jp.gr.java_conf.alpherg0221.schooltimetable.data.AppContainer
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.BackHandler
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.theme.SchoolTimetableTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SchoolTimetableApp(
    appContainer: AppContainer
) {
    val theme by appContainer.settingsRepository.observeTheme().collectAsState(AppTheme.Default)

    SchoolTimetableTheme(
        darkTheme = when (theme) {
            AppTheme.Dark -> true
            AppTheme.Light -> false
            AppTheme.Default -> isSystemInDarkTheme()
        }
    ) {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setStatusBarColor(Color.Transparent, darkIcons = darkIcons)
            }

            val navController = rememberAnimatedNavController()
            val scope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.HOME_ROUTE

            BackHandler(
                enabled = scaffoldState.drawerState.isOpen,
                onBack = { scope.launch { scaffoldState.drawerState.close() } }
            )

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                scaffoldState = scaffoldState,
                drawerContent = {
                    AppDrawer(
                        currentRoute = currentRoute,
                        navigateToHome = {
                            navController.navigate(MainDestinations.HOME_ROUTE)
                        },
                        navigateToClassInfoListEdit = {
                            navController.navigate(MainDestinations.CLASS_INFO_LIST_EDIT_ROUTE)
                        },
                        navigateToSettings = {
                            navController.navigate(MainDestinations.SETTINGS_ROUTE)
                        },
                        closeDrawer = { scope.launch { scaffoldState.drawerState.close() } }
                    )
                },
                drawerGesturesEnabled = currentRoute == MainDestinations.HOME_ROUTE

            ) {
                SchoolTimetableNavGraph(
                    appContainer = appContainer,
                    navController = navController,
                    scaffoldState = scaffoldState,
                )
            }
        }
    }
}