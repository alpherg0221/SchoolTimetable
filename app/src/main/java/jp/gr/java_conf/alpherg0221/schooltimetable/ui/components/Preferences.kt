package jp.gr.java_conf.alpherg0221.schooltimetable.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreferencesItem(
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {},
    icon: ImageVector? = null,
    color: Color? = null,
    checked: Boolean? = null,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(6.dp, 8.dp),
            ) {
                Icon(
                    imageVector = icon ?: Icons.Rounded.DateRange,
                    contentDescription = null,
                    tint = if (icon != null) color ?: MaterialTheme.colors.onSurface else Color.Transparent
                )
                Spacer(Modifier.width(36.dp))
                Column {
                    Text(
                        text = title,
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 16.sp,
                        maxLines = 1,
                        style = MaterialTheme.typography.caption,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            color = MaterialTheme.colors.onSurface.copy(alpha = .6f),
                            fontSize = 14.sp,
                            maxLines = 1,
                            style = MaterialTheme.typography.body2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            if (checked != null) {
                Switch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.padding(6.dp, 8.dp),
                )
            }
        }
    }
}

@Composable
fun PreferencesTitle(
    title: String
) {
    Surface(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text(
            text = title,
            maxLines = 1,
            fontSize = 14.sp,
        )
    }
}