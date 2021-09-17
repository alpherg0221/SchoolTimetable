package jp.gr.java_conf.alpherg0221.schooltimetable.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorSurface(
    color: Color,
    contentColor: Color = Color.Transparent,
) {
    Surface(
        modifier = Modifier
            .padding(start = 18.dp)
            .size(24.dp),
        shape = CircleShape,
        color = color,
        border = BorderStroke(
            width = 1.dp,
            color = if (contentColor != Color.Transparent) {
                contentColor.copy(alpha = .5f)
            } else {
                contentColor
            }
        ),
        content = {
        }
    )
}