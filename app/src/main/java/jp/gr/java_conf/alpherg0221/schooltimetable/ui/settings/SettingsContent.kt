package jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Brightness4
import androidx.compose.material.icons.rounded.Brightness7
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.navigationBarsPadding
import jp.gr.java_conf.alpherg0221.schooltimetable.model.AppTheme
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.*
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toLongString

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsContent(
    onBack: () -> Unit,
    onCloseBottomSheet: () -> Unit = {},
    sheetState: ModalBottomSheetState,
    onClickItem: (SettingsContent) -> Unit = {},
    onClickClassTimeItem: (PeriodType) -> Unit = {},
    dayOfWeek: String = "",
    period: String = "",
    classTimeList: List<ClassTime>,
    theme: AppTheme,
    sheetContent: @Composable ColumnScope.() -> Unit
) {
    BackHandler(enabled = sheetState.isVisible, onBack = onCloseBottomSheet)
    ModalBottomSheetLayout(sheetState = sheetState, sheetContent = sheetContent) {
        Scaffold(
            topBar = {
                InsetAwareTopAppBar(
                    title = { Text(text = stringResource(id = R.string.settings)) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            LazyColumn(modifier = Modifier.navigationBarsPadding()) {
                item {
                    PreferencesTitle(title = stringResource(id = R.string.timetable))
                    PreferencesItem(
                        title = stringResource(id = R.string.day_of_week),
                        subtitle = dayOfWeek,
                        onClick = { onClickItem(SettingsContent.DayOfWeek) },
                    )
                    PreferencesItem(
                        title = stringResource(id = R.string.period),
                        subtitle = period,
                        onClick = { onClickItem(SettingsContent.Period) },
                    )
                    AppDivider()
                    PreferencesTitle(title = stringResource(id = R.string.class_time))
                }

                items(classTimeList) { classTime ->
                    PreferencesItem(
                        title = classTime.periodType().toLongString(),
                        subtitle = "${classTime.start()} ~ ${classTime.end()}",
                        onClick = { onClickClassTimeItem(classTime.periodType()) }
                    )
                }

                item {
                    AppDivider()
                    PreferencesTitle(title = stringResource(id = R.string.general))
                    PreferencesItem(
                        title = stringResource(id = R.string.theme),
                        subtitle = theme.toLongString(),
                        onClick = { onClickItem(SettingsContent.Theme) },
                        icon = when (theme) {
                            AppTheme.Dark -> Icons.Rounded.Brightness4
                            AppTheme.Light -> Icons.Rounded.Brightness7
                            AppTheme.Default ->
                                when (LocalContext.current.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                    Configuration.UI_MODE_NIGHT_YES -> Icons.Rounded.Brightness4
                                    Configuration.UI_MODE_NIGHT_NO -> Icons.Rounded.Brightness7
                                    else -> Icons.Rounded.Error
                                }
                        },
                    )
                }
            }
        }
    }
}