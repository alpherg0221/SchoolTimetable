package jp.gr.java_conf.alpherg0221.schooltimetable.data.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class_info")
data class ClassInfo(
    @PrimaryKey val subject: String,
    val classroom: String?,
    val teacher: String?,
    val memo: String?,
    val color: String?,
)

@Entity(
    tableName = "timetable",
    primaryKeys = ["dayOfWeek", "period"]
)
data class Timetable(
    val dayOfWeek: String,
    val period: String,
    val name: String?,
)

data class TableWithClassInfo(
    val dayOfWeek: String,
    val period: String,
    val subject: String?,
    val classroom: String?,
    val teacher: String?,
    val memo: String?,
    val color: String?,
)