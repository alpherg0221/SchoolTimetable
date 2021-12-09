package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoActionMode
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toLongString

@Composable
fun ClassListSelectScreen(
    classListSelectViewModel: ClassListSelectViewModel,
    onBack: () -> Unit = {},
    navigateToClassInfo: (ClassInfoActionMode, String) -> Unit,
) {
    val classInfoList by classListSelectViewModel.classInfoList.collectAsState()
    val selectedSubject by classListSelectViewModel.selectedSubject.collectAsState()

    ClassListSelectContent(
        onBack = onBack,
        classInfoList = classInfoList,
        dayOfWeek = classListSelectViewModel.dayOfWeek.toLongString(),
        period = classListSelectViewModel.period.toLongString(),
        selectedSubject = selectedSubject.name ?: "",
        onListClick = { subject ->
            classListSelectViewModel.setTimetable(subject)
        },
        addSubject = { navigateToClassInfo(ClassInfoActionMode.Add, " ") },
        deleteSubject = { classListSelectViewModel.setTimetable(null) }
    )
}