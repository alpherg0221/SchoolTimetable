package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classlist.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ClassListEditViewModel(
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
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ClassListEditViewModel(
                    timetableRepository = timetableRepository,
                ) as T
            }
        }
    }
}