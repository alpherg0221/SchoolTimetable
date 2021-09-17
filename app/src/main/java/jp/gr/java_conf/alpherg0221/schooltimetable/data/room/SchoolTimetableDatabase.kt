package jp.gr.java_conf.alpherg0221.schooltimetable.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ClassTime::class, ClassInfo::class, Timetable::class],
    version = 2,
    exportSchema = false
)
abstract class SchoolTimetableDatabase : RoomDatabase() {

    abstract fun classTimeDao(): ClassTimeDao
    abstract fun timetableDao(): TimetableDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolTimetableDatabase? = null

        fun getDatabase(context: Context): SchoolTimetableDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SchoolTimetableDatabase::class.java,
                    "school_timetable_database"
                ).addMigrations(
                    MIGRATION_1_2,
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}