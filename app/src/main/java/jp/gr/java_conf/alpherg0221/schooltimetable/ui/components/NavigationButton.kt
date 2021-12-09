package jp.gr.java_conf.alpherg0221.schooltimetable.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavRailButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    isSelected: Boolean,
    action: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        if (isSelected) {
            MaterialTheme.colors.primary.copy(alpha = 0.12f)
        } else {
            Color.Transparent
        }
    )

    Surface(
        color = backgroundColor,
        onClick = action,
        shape = CircleShape,
        role = Role.Tab,
        modifier = modifier.size(48.dp)
    ) {
        NavigationIcon(
            modifier = Modifier.size(32.dp),
            icon = icon,
            isSelected = isSelected,
            contentDescription = contentDescription,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
) {
    val colors = MaterialTheme.colors
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        onClick = action,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(ButtonDefaults.ContentPadding).fillMaxWidth()
        ) {
            NavigationIcon(
                icon = icon,
                isSelected = isSelected,
                contentDescription = null, // decorative
                tintColor = textIconColor
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                fontFamily = FontFamily.Monospace,
                color = textIconColor
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    isSelected: Boolean,
    contentDescription: String? = null,
    tintColor: Color? = null,
) {
    val imageAlpha = if (isSelected) 1f else 0.6f

    val iconTintColor = tintColor ?: if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    Image(
        modifier = modifier,
        imageVector = icon,
        contentDescription = contentDescription,
        contentScale = ContentScale.Inside,
        colorFilter = ColorFilter.tint(iconTintColor),
        alpha = imageAlpha
    )
}