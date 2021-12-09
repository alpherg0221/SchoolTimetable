package jp.gr.java_conf.alpherg0221.schooltimetable.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class InfoUiState(
    val loading: Boolean = false,
)

class InfoViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InfoUiState(loading = true))
    val infoUiState: StateFlow<InfoUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    private fun refreshAll() {
        _uiState.update { it.copy(loading = false) }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return InfoViewModel() as T
            }
        }
    }
}