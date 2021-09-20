package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.CheckDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClassInfoScreen(
    classInfoViewModel: ClassInfoViewModel,
    onBack: () -> Unit,
) {
    val uiState by classInfoViewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val context = LocalContext.current

    ClassInfoContent(
        onBack = onBack,
        mode = classInfoViewModel.mode,
        showDialog = uiState.showDialog,
        dialogContent = {
            CheckDialog(
                title = stringResource(id = R.string.delete_class_info_title),
                text = stringResource(id = R.string.delete_class_info_text),
                confirmText = stringResource(id = R.string.delete),
                onDismissRequest = { classInfoViewModel.setShowDialog(false) },
                onConfirm = {
                    classInfoViewModel.delete()
                    classInfoViewModel.setShowDialog(false)
                    onBack()
                },
                onDismiss = { classInfoViewModel.setShowDialog(false) },
            )
        },
        onOpenBottomSheet = { scope.launch { sheetState.show() } },
        onCloseBottomSheet = { scope.launch { sheetState.hide() } },
        sheetContent = {
            ColorPicker(
                selected = uiState.color.toLong(),
                onSelected = { color ->
                    scope.launch { sheetState.hide() }
                    Toast.makeText(context, color, Toast.LENGTH_SHORT).show()
                    classInfoViewModel.setClassInfo(ClassInfoType.Color, color)
                }
            )
        },
        sheetState = sheetState,
        uiState = uiState,
        onValueChange = { type, value -> classInfoViewModel.setClassInfo(type, value) },
        onSave = {
            classInfoViewModel.save()
            onBack()
        },
        onDelete = {
            classInfoViewModel.setShowDialog(true)
        },
    )
}