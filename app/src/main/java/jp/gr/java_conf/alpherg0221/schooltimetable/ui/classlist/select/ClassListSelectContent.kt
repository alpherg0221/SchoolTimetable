package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.select

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
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
fun ClassListSelectContent(
    onBack: () -> Unit = {},
    classInfoList: List<ClassInfo>,
    dayOfWeek: String,
    period: String,
    selectedSubject: String = "",
    onListClick: (String) -> Unit,
    addSubject: () -> Unit = {},
    deleteSubject: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                title = { Text(text = "$dayOfWeek $period") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addSubject) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn() {
            stickyHeader {
                PreferencesItem(
                    title = stringResource(id = R.string.delete),
                    onClick = deleteSubject,
                    icon = Icons.Rounded.Delete,
                )
                AppDivider()
            }
            items(classInfoList) { classInfo ->
                PreferencesItem(
                    title = classInfo.subject,
                    subtitle = "${classInfo.classroom ?: ""} / ${classInfo.teacher ?: ""}",
                    onClick = { onListClick(classInfo.subject) },
                    icon = if (selectedSubject == classInfo.subject) {
                        Icons.Rounded.CheckCircleOutline
                    } else {
                        Icons.Rounded.Circle
                    },
                    color = classInfo.color?.let { Color(it.toLong()) } ?: Color.Transparent,
                )
                AppDivider()
            }
        }
    }
}