package jp.gr.java_conf.alpherg0221.schooltimetable.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.model.AppTheme
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.theme.ColorValueList
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.theme.OnColorValueList

@Composable
fun DayOfWeekType.toShortString(): String {
    return when (this) {
        DayOfWeekType.Sun -> stringResource(id = R.string.sun)
        DayOfWeekType.Mon -> stringResource(id = R.string.mon)
        DayOfWeekType.Tue -> stringResource(id = R.string.tue)
        DayOfWeekType.Wed -> stringResource(id = R.string.wed)
        DayOfWeekType.Thu -> stringResource(id = R.string.thu)
        DayOfWeekType.Fri -> stringResource(id = R.string.fri)
        DayOfWeekType.Sat -> stringResource(id = R.string.sat)
    }
}

@Composable
fun DayOfWeekType.toLongString(): String {
    return when (this) {
        DayOfWeekType.Sun -> stringResource(id = R.string.sunday)
        DayOfWeekType.Mon -> stringResource(id = R.string.monday)
        DayOfWeekType.Tue -> stringResource(id = R.string.tuesday)
        DayOfWeekType.Wed -> stringResource(id = R.string.wednesday)
        DayOfWeekType.Thu -> stringResource(id = R.string.thursday)
        DayOfWeekType.Fri -> stringResource(id = R.string.friday)
        DayOfWeekType.Sat -> stringResource(id = R.string.saturday)
    }
}

@Composable
fun PeriodType.toLongString(): String {
    return when (this) {
        PeriodType.First -> stringResource(id = R.string.first)
        PeriodType.Second -> stringResource(id = R.string.second)
        PeriodType.Third -> stringResource(id = R.string.third)
        PeriodType.Fourth -> stringResource(id = R.string.forth)
        PeriodType.Fifth -> stringResource(id = R.string.fifth)
        PeriodType.Sixth -> stringResource(id = R.string.sixth)
        PeriodType.Seventh -> stringResource(id = R.string.seventh)
    }
}

@Composable
fun AppTheme.toLongString(): String {
    return when (this) {
        AppTheme.Dark -> stringResource(id = R.string.dark_theme)
        AppTheme.Light -> stringResource(id = R.string.light_theme)
        AppTheme.Default -> stringResource(id = R.string.default_theme)
    }
}

@Composable
fun Collection<DayOfWeekType>.toDayOfWeekString(): String {
    val str = StringBuilder("")
    if (this.contains(DayOfWeekType.Sun)) str.append("${stringResource(id = R.string.sun)}, ")
    if (this.contains(DayOfWeekType.Mon)) str.append("${stringResource(id = R.string.mon)}, ")
    if (this.contains(DayOfWeekType.Tue)) str.append("${stringResource(id = R.string.tue)}, ")
    if (this.contains(DayOfWeekType.Wed)) str.append("${stringResource(id = R.string.wed)}, ")
    if (this.contains(DayOfWeekType.Thu)) str.append("${stringResource(id = R.string.thu)}, ")
    if (this.contains(DayOfWeekType.Fri)) str.append("${stringResource(id = R.string.fri)}, ")
    if (this.contains(DayOfWeekType.Sat)) str.append("${stringResource(id = R.string.sat)}, ")
    if (str.endsWith(", ")) str.delete(str.lastIndex - 1, str.lastIndex)
    return str.toString()
}

@Composable
fun Collection<PeriodType>.toPeriodString(): String {
    val str = StringBuilder("")
    if (this.contains(PeriodType.First)) str.append("1, ")
    if (this.contains(PeriodType.Second)) str.append("2, ")
    if (this.contains(PeriodType.Third)) str.append("3, ")
    if (this.contains(PeriodType.Fourth)) str.append("4, ")
    if (this.contains(PeriodType.Fifth)) str.append("5, ")
    if (this.contains(PeriodType.Sixth)) str.append("6, ")
    if (this.contains(PeriodType.Seventh)) str.append("7, ")
    if (str.endsWith(", ")) str.delete(str.lastIndex - 1, str.lastIndex)
    return str.toString()
}

fun Collection<ClassTime>.toComplement(): List<ClassTime> {
    val pairList = mutableListOf<ClassTime>()

    PeriodType.values().forEach { periodType ->
        pairList.add(
            this.find { it.periodType() == periodType } ?: ClassTime(
                period = periodType.name,
                startH = null,
                startM = null,
                endH = null,
                endM = null,
            )
        )
    }
    return pairList
}

fun localContentColorFor(color: Long): Color {
    return try {
        Color(OnColorValueList[ColorValueList.indexOf(color)])
    } catch (e: Throwable) {
        Color.Transparent
    }
}
