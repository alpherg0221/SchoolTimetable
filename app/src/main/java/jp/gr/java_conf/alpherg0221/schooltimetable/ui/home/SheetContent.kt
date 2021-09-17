package jp.gr.java_conf.alpherg0221.schooltimetable.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.TableWithClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.BottomSheetLayout
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.components.ColorSurface
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toLongString

@Composable
fun ClassInfoSheetContent(
    tableWithClassInfo: TableWithClassInfo?,
    onEditSubject: () -> Unit = {},
    onEdit: () -> Unit = {},
) {
    BottomSheetLayout(
        title = tableWithClassInfo?.let {
            DayOfWeekType.valueOf(tableWithClassInfo.dayOfWeek).toLongString() + " " +
                    PeriodType.valueOf(tableWithClassInfo.period).toLongString()
        } ?: "",
        actions = {
            IconButton(onClick = onEditSubject) {
                Icon(
                    imageVector = Icons.Rounded.EditNote,
                    contentDescription = null,
                )
            }
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                )
            }
        }
    ) {
        ClassInfoContent(
            info = tableWithClassInfo?.subject ?: "",
            icon = Icons.Rounded.Class,
        )
        ClassInfoContent(
            info = tableWithClassInfo?.classroom ?: "",
            icon = Icons.Rounded.Room,
        )
        ClassInfoContent(
            info = tableWithClassInfo?.teacher ?: "",
            icon = Icons.Rounded.Person,
        )
        ClassInfoContent(
            info = tableWithClassInfo?.memo ?: "",
            icon = Icons.Rounded.Description,
        )
        ClassColorInfoContent(
            color = try {
                tableWithClassInfo?.color?.toLong()?.let { Color(it) } ?: Color.Transparent
            } catch (e: Throwable) {
                Color.Transparent
            },
            icon = Icons.Rounded.Palette,
        )
    }
}

@Composable
fun ClassInfoContent(
    info: String = "",
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Text(
            text = info,
            modifier = Modifier.padding(start = 18.dp),
        )
    }
}

@Composable
fun ClassColorInfoContent(
    color: Color = Color.Transparent,
    contentColor: Color = Color.Transparent,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = icon, contentDescription = null)
        ColorSurface(color = color, contentColor = contentColor)
    }
}