package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfolist.select

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.navigationBarsPadding
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.AppDivider
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.InsetAwareTopAppBar
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.PreferencesItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClassInfoListSelectContent(
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
        }
    ) {
        LazyColumn(modifier = Modifier.navigationBarsPadding()) {
            stickyHeader {
                PreferencesItem(
                    title = stringResource(id = R.string.add),
                    onClick = addSubject,
                    icon = Icons.Rounded.Add
                )
                AppDivider()
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