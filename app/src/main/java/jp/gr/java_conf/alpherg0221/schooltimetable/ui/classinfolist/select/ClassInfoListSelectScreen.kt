package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfolist.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoActionMode
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toLongString

@Composable
fun ClassInfoListSelectScreen(
    classInfoListSelectViewModel: ClassInfoListSelectViewModel,
    onBack: () -> Unit = {},
    navigateToClassInfo: (ClassInfoActionMode, String) -> Unit,
) {
    val classInfoList by classInfoListSelectViewModel.classInfoList.collectAsState()
    val selectedSubject by classInfoListSelectViewModel.selectedSubject.collectAsState()

    ClassInfoListSelectContent(
        onBack = onBack,
        classInfoList = classInfoList,
        dayOfWeek = classInfoListSelectViewModel.dayOfWeek.toLongString(),
        period = classInfoListSelectViewModel.period.toLongString(),
        selectedSubject = selectedSubject.name ?: "",
        onListClick = { subject ->
            classInfoListSelectViewModel.setTimetable(subject)
        },
        addSubject = { navigateToClassInfo(ClassInfoActionMode.Add, " ") },
        deleteSubject = { classInfoListSelectViewModel.setTimetable(null) }
    )
}