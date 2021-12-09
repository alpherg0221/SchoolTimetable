package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classtime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.InsetAwareTopAppBar
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toLongString

@Composable
fun ClassTimeContent(
    onBack: () -> Unit,
    period: PeriodType,
    startH: String = "",
    startM: String = "",
    endH: String = "",
    endM: String = "",
    loading: Boolean = false,
    onValueChange: (TimeType, String) -> Unit,
    onSave: () -> Unit,
) {
    val focus = LocalFocusManager.current
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                title = { Text(text = period.toLongString()) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = stringResource(id = R.string.start_time))
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TimeInputTextField(
                        value = startH,
                        onValueChange = { onValueChange(TimeType.StartH, it) },
                    )
                    Text(
                        text = "  :  ",
                        fontSize = 24.sp,
                    )
                    TimeInputTextField(
                        value = startM,
                        onValueChange = { onValueChange(TimeType.StartM, it) },
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                Text(text = stringResource(id = R.string.end_time))
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TimeInputTextField(
                        value = endH,
                        onValueChange = { onValueChange(TimeType.EndH, it) },
                    )
                    Text(
                        text = "  :  ",
                        fontSize = 24.sp,
                    )
                    TimeInputTextField(
                        value = endM,
                        imeAction = ImeAction.Done,
                        onDone = { focus.clearFocus() },
                        onValueChange = { onValueChange(TimeType.EndM, it) },
                    )
                }
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
    )
}

@Composable
fun TimeInputTextField(
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    onDone: KeyboardActionScope.() -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.width(150.dp),
        value = value,
        singleLine = true,
        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 24.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        onValueChange = onValueChange,
    )
}