package jp.gr.java_conf.alpherg0221.schooltimetable.ui.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoActionMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    openDrawer: () -> Unit,
    navigateToClassInfoListSelect: (DayOfWeekType, PeriodType) -> Unit,
    navigateToClassInfo: (ClassInfoActionMode, String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val uiState by homeViewModel.uiState.collectAsState()
    val dayOfWeekList by homeViewModel.dayOfWeek.collectAsState()
    val periodList by homeViewModel.period.collectAsState()
    val classTimeList by homeViewModel.classTimeList.collectAsState()
    val tableWithClassInfoList by homeViewModel.tableWithClassInfoList.collectAsState()

    HomeContent(
        loading = uiState.loading,
        openDrawer = openDrawer,
        onCloseBottomSheet = { scope.launch { sheetState.hide() } },
        sheetState = sheetState,
        dayOfWeekList = dayOfWeekList,
        periodList = periodList,
        classTimeList = classTimeList,
        tableWithClassInfoList = tableWithClassInfoList,
        onItemClick = { tableWithClassInfo ->
            homeViewModel.setBottomSheet(tableWithClassInfo)
            scope.launch { sheetState.show() }
        },
        sheetContent = {
            ClassInfoSheetContent(
                tableWithClassInfo = uiState.bottomSheet,
                onEditSubject = {
                    scope.launch { sheetState.hide() }
                    uiState.bottomSheet?.subject?.let {
                        navigateToClassInfo(ClassInfoActionMode.Edit, it)
                    }
                },
                onEdit = {
                    scope.launch { sheetState.hide() }
                    uiState.bottomSheet?.let {
                        navigateToClassInfoListSelect(
                            DayOfWeekType.valueOf(it.dayOfWeek),
                            PeriodType.valueOf(it.period),
                        )
                    }
                }
            )
        },
    )
}