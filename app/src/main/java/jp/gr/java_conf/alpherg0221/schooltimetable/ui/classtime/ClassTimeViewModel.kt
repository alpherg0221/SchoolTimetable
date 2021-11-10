package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classtime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassTime
import jp.gr.java_conf.alpherg0221.schooltimetable.data.settings.SettingsRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class TimeType { StartH, StartM, EndH, EndM, }

data class ClassTimeUiState(
    val period: PeriodType,
    val startH: String = "",
    val startM: String = "",
    val endH: String = "",
    val endM: String = "",
    val loading: Boolean = false,
)

class ClassTimeViewModel(
    private val settingsRepository: SettingsRepository,
    private val period: PeriodType,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassTimeUiState(period = period, loading = true))
    val uiState: StateFlow<ClassTimeUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    fun setTime(type: TimeType, value: String) {
        if (value.length > 2) return
        _uiState.update {
            when (type) {
                TimeType.StartH -> it.copy(startH = value)
                TimeType.StartM -> it.copy(startM = value)
                TimeType.EndH -> it.copy(endH = value)
                TimeType.EndM -> it.copy(endM = value)
            }
        }
    }

    suspend fun save() {
        val state = uiState.value
        val startH =
            when (state.startH.length) {
                2 -> state.startH
                1 -> "0${state.startH}"
                else -> null
            }
        val startM =
            if (startH == null) null
            else
                when (state.startM.length) {
                    2 -> state.startM
                    1 -> "0${state.startM}"
                    0 -> "00"
                    else -> null
                }
        val endH =
            when (state.endH.length) {
                2 -> state.endH
                1 -> "0${state.endH}"
                else -> null
            }
        val endM =
            if (endH == null) null
            else
                when (state.endM.length) {
                    2 -> state.endM
                    1 -> "0${state.endM}"
                    0 -> "00"
                    else -> null
                }
        settingsRepository.setClassTime(
            ClassTime(
                period = period.name,
                startH = startH,
                startM = startM,
                endH = endH,
                endM = endM,
            )
        )
    }

    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val classTime = settingsRepository.getClassTimeByPeriod(period.name)
            _uiState.update {
                it.copy(
                    startH = classTime?.startH ?: "",
                    startM = classTime?.startM ?: "",
                    endH = classTime?.endH ?: "",
                    endM = classTime?.endM ?: "",
                    loading = false,
                )
            }
        }
    }

    companion object {
        fun provideFactory(
            settingsRepository: SettingsRepository,
            period: PeriodType,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ClassTimeViewModel(
                    settingsRepository = settingsRepository,
                    period = period,
                ) as T
            }
        }
    }
}