package jp.gr.java_conf.alpherg0221.schooltimetable.ui.components

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppDivider() {
    Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
}