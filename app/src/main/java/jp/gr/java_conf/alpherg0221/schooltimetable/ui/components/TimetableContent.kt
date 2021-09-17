package jp.gr.java_conf.alpherg0221.schooltimetable.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.TableWithClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.localContentColorFor

@Composable
fun TimetableContent(
    itemHeight: Dp,
    itemWidth: Dp,
    dayOfWeekList: List<DayOfWeekType>,
    periodList: List<PeriodType>,
    tableWithClassInfoList: List<TableWithClassInfo>,
    onItemClick: (TableWithClassInfo?) -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        dayOfWeekList.forEach { dayOfWeek ->
            DayOfWeekColumn(
                modifier = Modifier
                    .width(itemWidth)
                    .fillMaxHeight(),
                itemHeight = itemHeight,
                periodList = periodList,
                tableWithClassInfoList = tableWithClassInfoList
                    .filter { it.dayOfWeek == dayOfWeek.name }
                    .sortedBy { it.period },
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
fun DayOfWeekColumn(
    modifier: Modifier = Modifier,
    itemHeight: Dp,
    periodList: List<PeriodType>,
    tableWithClassInfoList: List<TableWithClassInfo>,
    onItemClick: (TableWithClassInfo?) -> Unit = {},
) {
    Column(modifier = modifier) {
        periodList.forEach { period ->
            ClassItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight),
                tableWithClassInfo = tableWithClassInfoList.find { it.period == period.name },
                onCLick = onItemClick,
            )
        }
    }
}

@Composable
fun ClassItem(
    modifier: Modifier = Modifier,
    tableWithClassInfo: TableWithClassInfo?,
    onCLick: (TableWithClassInfo?) -> Unit = {},
) {
    Surface(
        modifier = modifier.clickable { onCLick(tableWithClassInfo) },
        color = tableWithClassInfo?.color?.let { Color(it.toLong()) } ?: Color.Transparent,
        contentColor = tableWithClassInfo?.color?.let { localContentColorFor(it.toLong()) }
            ?: Color.Transparent,
        border = BorderStroke(
            width = (0.5).dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = .5f)
        ),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 1.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tableWithClassInfo?.subject ?: "",
                fontSize = 12.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = tableWithClassInfo?.classroom ?: "",
                fontSize = 10.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}