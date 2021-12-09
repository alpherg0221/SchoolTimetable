package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.edit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.AppDivider
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.InsetAwareTopAppBar
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.PreferencesItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClassListEditContent(
    onBack: () -> Unit = {},
    classInfoList: List<ClassInfo>,
    onListClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                title = { Text(text = stringResource(id = R.string.class_info_list)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn() {
            items(classInfoList) { classInfo ->
                PreferencesItem(
                    title = classInfo.subject,
                    subtitle = "${classInfo.classroom ?: ""} / ${classInfo.teacher ?: ""}",
                    onClick = { onListClick(classInfo.subject) },
                    icon = Icons.Rounded.Circle,
                    color = classInfo.color?.let { Color(it.toLong()) } ?: Color.Transparent,
                )
                AppDivider()
            }
        }
    }
}