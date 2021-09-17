package jp.gr.java_conf.alpherg0221.schooltimetable.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.TableWithClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.data.settings.SettingsRepository
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HomeUiState(
    val bottomSheet: TableWithClassInfo? = null,
    val loading: Boolean = false
)

class HomeViewModel(
    private val settingsRepository: SettingsRepository,
    private val timetableRepository: TimetableRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    val dayOfWeek = settingsRepository.observeDayOfWeek().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val period = settingsRepository.observePeriods().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val classTimeList = settingsRepository.observeAllClassTime().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList(),
    )

    val tableWithClassInfoList = timetableRepository.observeTableWithClassInfo().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList(),
    )

    init {
        refreshAll()
    }

    fun setBottomSheet(tableWithClassInfo: TableWithClassInfo?) {
        _uiState.update { it.copy(bottomSheet = tableWithClassInfo) }
    }

    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            timetableRepository.initDatabase()
            _uiState.update { it.copy(loading = false) }
        }
    }

    companion object {
        fun provideFactory(
            settingsRepository: SettingsRepository,
            timetableRepository: TimetableRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    settingsRepository = settingsRepository,
                    timetableRepository = timetableRepository,
                ) as T
            }
        }
    }
}