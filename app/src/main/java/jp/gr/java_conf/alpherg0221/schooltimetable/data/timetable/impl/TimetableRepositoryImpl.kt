package jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.impl

import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.*
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TimetableRepositoryImpl(
    private val timetableDao: TimetableDao,
) : TimetableRepository {
    override suspend fun initDatabase() {
        withContext(Dispatchers.IO) {
            DayOfWeekType.values().forEach { dayOfWeek ->
                PeriodType.values().forEach { period ->
                    try {
                        timetableDao.initTimetable(
                            Timetable(
                                dayOfWeek = dayOfWeek.name,
                                period = period.name,
                                name = null,
                            )
                        )
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    override fun observeAllClassInfo(): Flow<List<ClassInfo>> =
        timetableDao.getAllClassInfo().map { it.sortedBy { classInfo -> classInfo.subject } }

    override fun observeTableWithClassInfo(): Flow<List<TableWithClassInfo>> =
        timetableDao.getAllTimetable()


    override fun observeTimetable(dayOfWeek: String, period: String): Flow<Timetable> =
        timetableDao.getTimetableByDayOfWeekAndPeriod(dayOfWeek, period)

    override suspend fun getClassInfoBySubject(subject: String): ClassInfo? =
        withContext(Dispatchers.IO) {
            timetableDao.getClassInfoBySubject(subject)
        }

    override suspend fun setClassInfo(classInfo: ClassInfo, oldSubject: String) {
        withContext(Dispatchers.IO) {
            if (classInfo.subject != oldSubject) {
                timetableDao.updateClassInfoAndTimetable(classInfo, oldSubject)
            } else if (classInfo.subject == oldSubject) {
                timetableDao.insertClassInfo(classInfo)
            }
        }
    }

    override suspend fun setTimetable(timetable: Timetable) {
        withContext(Dispatchers.IO) {
            timetableDao.insertTimetable(timetable)
        }
    }

    override suspend fun deleteClassInfoAndTimetable(subject: String) {
        withContext(Dispatchers.IO) {
            timetableDao.deleteClassInfoAndTimetable(subject)
        }
    }
}