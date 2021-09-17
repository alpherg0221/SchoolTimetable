package jp.gr.java_conf.alpherg0221.schooltimetable.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.utils.toShortString

@Composable
fun TimetableLayout(
    itemHeight: Dp,
    itemWidth: Dp,
    dayOfWeekList: List<DayOfWeekType>,
    periodList: List<PeriodType>,
    classTimeList: List<ClassTime>,
    content: @Composable () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (space, row, column, contents) = createRefs()

        BorderSurface(
            modifier = Modifier
                .size(width = 40.dp, height = 40.dp)
                .constrainAs(space) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            width = 1.dp,
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .constrainAs(column) { top.linkTo(space.bottom) }
        ) {
            periodList.forEach { periodType ->
                PeriodItem(
                    modifier = Modifier.size(width = 40.dp, height = itemHeight),
                    start = classTimeList.find { it.periodType() == periodType }?.start() ?: "",
                    end = classTimeList.find { it.periodType() == periodType }?.end() ?: "",
                    period = periodType.ordinal + 1,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .constrainAs(row) { start.linkTo(space.end) }
        ) {
            dayOfWeekList.forEach { dayOfWeek ->
                DayOfWeekItem(
                    modifier = Modifier.size(width = itemWidth, height = 40.dp),
                    dayOfWeek = dayOfWeek.toShortString(),
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(contents) {
                    start.linkTo(column.end)
                    top.linkTo(row.bottom)
                },
            content = content
        )
    }
}

@Composable
fun DayOfWeekItem(
    modifier: Modifier = Modifier,
    dayOfWeek: String
) {
    BorderSurface(modifier = modifier, width = (0.5).dp) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = dayOfWeek)
        }
    }
}

@Composable
fun PeriodItem(
    modifier: Modifier = Modifier,
    start: String = "",
    end: String = "",
    period: Int,
) {
    BorderSurface(modifier = modifier, width = (0.5).dp) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeCard(time = start)
            Text(text = period.toString(), fontSize = 24.sp)
            TimeCard(time = end)
        }
    }
}

@Composable
fun BorderSurface(
    modifier: Modifier,
    width: Dp,
    content: @Composable () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(
            width = width,
            color = MaterialTheme.colors.onSurface.copy(alpha = .5f)
        ),
        content = content,
    )
}

@Composable
fun TimeCard(time: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.size(width = 32.dp, height = 18.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = time, fontSize = 12.sp)
        }
    }
}