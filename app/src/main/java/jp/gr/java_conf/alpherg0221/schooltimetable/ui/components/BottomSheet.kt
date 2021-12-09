package jp.gr.java_conf.alpherg0221.schooltimetable.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetLayout(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.selectableGroup()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(16.dp)
            )
            Row(content = actions)
        }
        AppDivider()
        Column(modifier = modifier, content = content)
    }
}