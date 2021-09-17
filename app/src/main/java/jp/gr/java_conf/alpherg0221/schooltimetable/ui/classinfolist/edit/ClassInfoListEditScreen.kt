package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfolist.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo.ClassInfoActionMode

@Composable
fun ClassInfoListEditScreen(
    classInfoListEditViewModel: ClassInfoListEditViewModel,
    onBack: () -> Unit = {},
    navigateToClassInfo: (ClassInfoActionMode, String) -> Unit,
) {
    val classInfoList by classInfoListEditViewModel.classInfoList.collectAsState()

    ClassInfoListEditContent(
        onBack = onBack,
        classInfoList = classInfoList,
        onListClick = { subject ->
            navigateToClassInfo(ClassInfoActionMode.Edit, subject)
        },
        onAddClick = { navigateToClassInfo(ClassInfoActionMode.Add, " ") }
    )
}