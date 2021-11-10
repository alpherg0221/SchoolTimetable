package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfolist.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.Timetable
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClassInfoListSelectViewModel(
    private val timetableRepository: TimetableRepository,
    val dayOfWeek: DayOfWeekType,
    val period: PeriodType,
) : ViewModel() {

    val classInfoList = timetableRepository.observeAllClassInfo().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        listOf(),
    )

    val selectedSubject = timetableRepository.observeTimetable(dayOfWeek.name, period.name).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        Timetable(dayOfWeek.name, period.name, "")
    )

    fun setTimetable(subject: String?) {
        viewModelScope.launch {
            timetableRepository.setTimetable(Timetable(dayOfWeek.name, period.name, subject))
        }
    }

    companion object {
        fun provideFactory(
            timetableRepository: TimetableRepository,
            dayOfWeek: DayOfWeekType,
            period: PeriodType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ClassInfoListSelectViewModel(
                    timetableRepository = timetableRepository,
                    dayOfWeek = dayOfWeek,
                    period = period,
                ) as T
            }
        }
    }
}