package jp.gr.java_conf.alpherg0221.schooltimetable.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType

@Entity(tableName = "class_time")
data class ClassTime(
    @PrimaryKey val period: String,
    val startH: String?,
    val startM: String?,
    val endH: String?,
    val endM: String?,
) {
    fun periodType() = PeriodType.valueOf(period)
    fun start() = if (startH == null || startM == null) "" else "$startH:$startM"
    fun end() = if (endH == null || endM == null) "" else "$endH:$endM"
}