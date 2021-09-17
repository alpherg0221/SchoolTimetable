package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfolist.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.Timetable
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClassInfoListEditViewModel(
    private val timetableRepository: TimetableRepository,
) : ViewModel() {

    val classInfoList = timetableRepository.observeAllClassInfo().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        listOf(),
    )

    companion object {
        fun provideFactory(
            timetableRepository: TimetableRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ClassInfoListEditViewModel(
                    timetableRepository = timetableRepository,
                ) as T
            }
        }
    }
}