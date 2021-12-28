package jp.gr.java_conf.alpherg0221.schooltimetable.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import jp.gr.java_conf.alpherg0221.compose.material.AppDivider
import jp.gr.java_conf.alpherg0221.compose.material.DrawerButton
import jp.gr.java_conf.alpherg0221.schooltimetable.R

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToClassListEdit: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAppInfo: () -> Unit,
    closeDrawer: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(24.dp))
        SchoolTimetableLogo(Modifier.padding(20.dp))
        AppDivider()
        DrawerButton(
            icon = Icons.Rounded.Home,
            label = stringResource(R.string.home),
            isSelected = currentRoute == MainDestinations.HOME_ROUTE,
            action = {
                navigateToHome()
                closeDrawer()
            }
        )

        DrawerButton(
            icon = Icons.Rounded.FormatListBulleted,
            label = stringResource(R.string.class_info_list),
            isSelected = currentRoute == MainDestinations.CLASS_LIST_EDIT_ROUTE,
            action = {
                navigateToClassListEdit()
                closeDrawer()
            }
        )

        DrawerButton(
            icon = Icons.Rounded.Settings,
            label = stringResource(R.string.settings),
            isSelected = currentRoute == MainDestinations.SETTINGS_ROUTE,
            action = {
                navigateToSettings()
                closeDrawer()
            }
        )

        DrawerButton(
            icon = Icons.Rounded.Info,
            label = stringResource(R.string.info),
            isSelected = currentRoute == MainDestinations.APP_INFO_ROUTE,
            action = {
                navigateToAppInfo()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun SchoolTimetableLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier.background(shape = MaterialTheme.shapes.small, color = Color.White),
            imageVector = Icons.Rounded.DateRange,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color(0xFF05A2E2))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Monospace
        )
    }
}