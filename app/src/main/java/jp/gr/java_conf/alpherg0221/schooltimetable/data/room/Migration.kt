package jp.gr.java_conf.alpherg0221.schooltimetable.data.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE `class_info` (" +
                    "`subject` TEXT PRIMARY KEY NOT NULL, " +
                    "`classroom` TEXT, " +
                    "`teacher` TEXT, " +
                    "`memo` TEXT, " +
                    "`color` TEXT);"
        )
        database.execSQL(
            "CREATE TABLE `timetable` (" +
                    "`dayOfWeek` TEXT NOT NULL, " +
                    "`period` TEXT NOT NULL, " +
                    "`name` TEXT," +
                    "PRIMARY KEY(`dayOfWeek`, `period`));"
        )
    }
}