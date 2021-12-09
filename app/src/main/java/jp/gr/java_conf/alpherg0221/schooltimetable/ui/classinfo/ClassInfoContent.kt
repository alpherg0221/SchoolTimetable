package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.BackHandler
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.ColorSurface
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.InsetAwareTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClassInfoContent(
    onBack: () -> Unit,
    mode: ClassInfoActionMode,
    showDialog: Boolean = false,
    dialogContent: @Composable () -> Unit,
    onOpenBottomSheet: () -> Unit = {},
    onCloseBottomSheet: () -> Unit = {},
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable ColumnScope.() -> Unit,
    uiState: ClassInfoUiState,
    onValueChange: (ClassInfoType, String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
) {
    val focus = LocalFocusManager.current

    BackHandler(enabled = sheetState.isVisible, onBack = onCloseBottomSheet)
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = sheetContent,
    ) {
        Scaffold(
            topBar = {
                InsetAwareTopAppBar(
                    title = {
                        Text(
                            text = when (mode) {
                                ClassInfoActionMode.Edit -> stringResource(id = R.string.edit)
                                ClassInfoActionMode.Add -> stringResource(id = R.string.add)
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        if (mode == ClassInfoActionMode.Edit) {
                            IconButton(onClick = onDelete) {
                                Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
                            }
                        }
                    }
                )
            }
        ) {
            if (showDialog) dialogContent()

            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    ClassInfoInputField(
                        icon = Icons.Rounded.Class,
                        label = stringResource(id = R.string.subject),
                        value = uiState.subject,
                        onValueChange = { onValueChange(ClassInfoType.Subject, it) },
                    )
                    ClassInfoInputField(
                        icon = Icons.Rounded.Room,
                        label = stringResource(id = R.string.classroom),
                        value = uiState.classroom ?: "",
                        onValueChange = { onValueChange(ClassInfoType.Classroom, it) },
                    )
                    ClassInfoInputField(
                        icon = Icons.Rounded.Person,
                        label = stringResource(id = R.string.teacher),
                        value = uiState.teacher ?: "",
                        onValueChange = { onValueChange(ClassInfoType.Teacher, it) },
                    )
                    ClassInfoInputField(
                        icon = Icons.Rounded.Description,
                        label = stringResource(id = R.string.memo),
                        value = uiState.memo ?: "",
                        onValueChange = { onValueChange(ClassInfoType.Memo, it) },
                    )
                    ClassInfoColorInputField(
                        icon = Icons.Rounded.Palette,
                        label = stringResource(id = R.string.color),
                        color = uiState.color.toLong(),
                        onClick = {
                            focus.clearFocus()
                            onOpenBottomSheet()
                        },
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        onClick = {
                            focus.clearFocus()
                            onSave()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,)
@Composable
fun ClassInfoInputField(
    icon: ImageVector,
    label: String,
    value: String,
    onDone: KeyboardActionScope.() -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            modifier = Modifier.width(48.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent {
                    if (it.isFocused) {
                        scope.launch { bringIntoViewRequester.bringIntoView() }
                    }
                },
            value = value,
            maxLines = 5,
            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
            keyboardActions = KeyboardActions(onDone = onDone),
            onValueChange = onValueChange,
        )
    }
}

@Composable
fun ClassInfoColorInputField(
    icon: ImageVector,
    label: String,
    color: Long,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            modifier = Modifier.width(48.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        ColorSurface(color = Color(color))
    }
}