package jp.gr.java_conf.alpherg0221.schooltimetable.ui.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.TableWithClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    loading: Boolean = false,
    openDrawer: () -> Unit = {},
    onCloseBottomSheet: () -> Unit = {},
    sheetState: ModalBottomSheetState,
    dayOfWeekList: List<DayOfWeekType>,
    periodList: List<PeriodType>,
    classTimeList: List<ClassTime>,
    tableWithClassInfoList: List<TableWithClassInfo> = listOf(),
    onItemClick: (TableWithClassInfo?) -> Unit = {},
    sheetContent: @Composable ColumnScope.() -> Unit,
) {
    BackHandler(enabled = sheetState.isVisible, onBack = onCloseBottomSheet)
    ModalBottomSheetLayout(sheetState = sheetState, sheetContent = sheetContent) {
        Scaffold(
            topBar = {
                InsetAwareTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = openDrawer) {
                            Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
                        }
                    },
                    elevation = 4.dp
                )
            },
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                elevation = 4.dp,
            ) {
                if (!loading) {
                    BoxWithConstraints {
                        TimetableLayout(
                            itemHeight = (maxHeight - 30.dp) / periodList.size,
                            itemWidth = (maxWidth - 40.dp) / dayOfWeekList.size,
                            dayOfWeekList = dayOfWeekList,
                            periodList = periodList,
                            classTimeList = classTimeList,
                        ) {
                            TimetableContent(
                                itemHeight = (maxHeight - 30.dp) / periodList.size,
                                itemWidth = (maxWidth - 40.dp) / dayOfWeekList.size,
                                dayOfWeekList = dayOfWeekList,
                                periodList = periodList,
                                tableWithClassInfoList = tableWithClassInfoList,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                } else {
                    FullScreenProgressIndicator()
                }
            }
        }
    }
}