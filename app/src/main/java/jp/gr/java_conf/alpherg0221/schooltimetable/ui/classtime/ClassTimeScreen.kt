package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ClassTimeScreen(
    classTimeViewModel: ClassTimeViewModel,
    onBack: () -> Unit,
) {
    val uiState by classTimeViewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()

    ClassTimeContent(
        onBack = onBack,
        period = uiState.period,
        startH = uiState.startH,
        startM = uiState.startM,
        endH = uiState.endH,
        endM = uiState.endM,
        loading = uiState.loading,
        onValueChange = { type, value ->
            classTimeViewModel.setTime(type, value)
        },
        onSave = {
            scope.launch { classTimeViewModel.save() }
            onBack()
        }
    )
}