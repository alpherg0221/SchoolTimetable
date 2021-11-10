package jp.gr.java_conf.alpherg0221.schooltimetable.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.gr.java_conf.alpherg0221.schooltimetable.model.AppTheme
import jp.gr.java_conf.alpherg0221.schooltimetable.data.settings.SettingsRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.model.DayOfWeekType
import jp.gr.java_conf.alpherg0221.schooltimetable.model.PeriodType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class SettingsContent { DayOfWeek, Period, Theme }

data class SettingsUiState(
    val bottomSheet: SettingsContent = SettingsContent.Theme,
    val loading: Boolean = false
)

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    val dayOfWeekList = settingsRepository.observeDayOfWeek().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList(),
    )

    val periodList = settingsRepository.observePeriods().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList(),
    )

    val classTimeList = settingsRepository.observeAllClassTime().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList(),
    )

    val theme = settingsRepository.observeTheme().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        AppTheme.Default,
    )

    fun setBottomSheet(settingsContent: SettingsContent) {
        _uiState.update { it.copy(bottomSheet = settingsContent) }
    }

    fun updateDayOfWeek(dayOfWeek: DayOfWeekType) {
        viewModelScope.launch {
            val mSet = dayOfWeekList.value.toMutableSet()
            if (mSet.contains(dayOfWeek)) mSet.remove(dayOfWeek) else mSet.add(dayOfWeek)
            settingsRepository.setDayOfWeek(mSet)
        }
    }

    fun updatePeriod(period: PeriodType) {
        viewModelScope.launch {
            val mSet = periodList.value.toMutableSet()
            if (mSet.contains(period)) mSet.remove(period) else mSet.add(period)
            settingsRepository.setPeriod(mSet)
        }
    }

    fun updateTheme(theme: AppTheme) {
        viewModelScope.launch {
            settingsRepository.setTheme(theme)
        }
    }

    companion object {
        fun provideFactory(
            settingsRepository: SettingsRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(
                    settingsRepository = settingsRepository
                ) as T
            }
        }
    }
}