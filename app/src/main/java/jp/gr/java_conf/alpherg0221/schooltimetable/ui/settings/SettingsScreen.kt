package jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toDayOfWeekString
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toPeriodString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onBack: () -> Unit,
    navigateToClassTime: (PeriodType) -> Unit,
) {
    val uiState by settingsViewModel.uiState.collectAsState()
    val dayOfWeekList by settingsViewModel.dayOfWeekList.collectAsState()
    val periodList by settingsViewModel.periodList.collectAsState()
    val classTimeList by settingsViewModel.classTimeList.collectAsState()
    val theme by settingsViewModel.theme.collectAsState()
    val bottomSheetContent = uiState.bottomSheet

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    SettingsContent(
        onBack = onBack,
        onCloseBottomSheet = { scope.launch { sheetState.hide() } },
        sheetState = sheetState,
        dayOfWeek = dayOfWeekList.toDayOfWeekString(),
        period = periodList.toPeriodString(),
        classTimeList = classTimeList,
        theme = theme,
        onClickItem = { content ->
            settingsViewModel.setBottomSheet(content)
            scope.launch { sheetState.show() }
        },
        onClickClassTimeItem = navigateToClassTime,
        sheetContent = {
            when (bottomSheetContent) {
                SettingsContent.DayOfWeek -> SelectDayOfWeekSheet(
                    list = dayOfWeekList,
                    onCheckedChange = { dayOfWeek ->
                        scope.launch { settingsViewModel.updateDayOfWeek(dayOfWeek) }
                    },
                )
                SettingsContent.Period -> SelectPeriodSheet(
                    list = periodList,
                    onCheckedChange = { period ->
                        scope.launch { settingsViewModel.updatePeriod(period) }
                    },
                )
                SettingsContent.Theme -> SelectThemeSheet(
                    currentTheme = theme,
                    onChecked = { theme ->
                        scope.launch { sheetState.hide() }
                        settingsViewModel.updateTheme(theme)
                    },
                )
            }
        },
    )
}
