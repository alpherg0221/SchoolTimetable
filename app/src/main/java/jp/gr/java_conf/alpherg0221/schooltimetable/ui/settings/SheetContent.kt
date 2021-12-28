package jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.gr.java_conf.alpherg0221.compose.material.BottomSheetLayout
import jp.gr.java_conf.alpherg0221.schooltimetable.model.AppTheme
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toLongString

@Composable
fun SelectDayOfWeekSheet(
    list: List<DayOfWeekType>,
    onCheckedChange: (DayOfWeekType) -> Unit,
) {
    BottomSheetLayout(title = stringResource(id = R.string.day_of_week)) {
        DayOfWeekType.values().forEach { dayOfWeekType ->
            CheckboxItem(
                label = dayOfWeekType.toLongString(),
                checked = list.contains(dayOfWeekType),
                onChecked = { onCheckedChange(dayOfWeekType) },
            )
        }
    }
}

@Composable
fun SelectPeriodSheet(
    list: List<PeriodType>,
    onCheckedChange: (PeriodType) -> Unit,
) {
    BottomSheetLayout(title = stringResource(id = R.string.period)) {
        PeriodType.values().forEach { periodType ->
            CheckboxItem(
                label = periodType.toLongString(),
                checked = list.contains(periodType),
                onChecked = { onCheckedChange(periodType) },
            )
        }
    }
}

@Composable
fun SelectThemeSheet(
    currentTheme: AppTheme,
    onChecked: (AppTheme) -> Unit,
) {
    BottomSheetLayout(title = stringResource(id = R.string.theme)) {
        AppTheme.values().forEach { appTheme ->
            RadioButtonItem(
                label = appTheme.toLongString(),
                selected = currentTheme == appTheme,
                onChecked = { onChecked(appTheme) },
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckboxItem(
    label: String,
    checked: Boolean,
    onChecked: () -> Unit = {},
) {
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChecked() }
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { onChecked() },
            )
            Text(
                text = label,
                modifier = Modifier.padding(start = 18.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RadioButtonItem(
    label: String,
    selected: Boolean,
    onChecked: () -> Unit = {},
) {
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChecked() }
                .padding(horizontal = 24.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onChecked,
            )
            Text(
                text = label,
                modifier = Modifier.padding(start = 18.dp)
            )
        }
    }
}