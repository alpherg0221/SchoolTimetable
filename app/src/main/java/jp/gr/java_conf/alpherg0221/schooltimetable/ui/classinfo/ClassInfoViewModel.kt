package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.gr.java_conf.alpherg0221.schooltimetable.data.room.ClassInfo
import jp.gr.java_conf.alpherg0221.schooltimetable.data.timetable.TimetableRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class ClassInfoType { Subject, Classroom, Teacher, Memo, Color }

enum class ClassInfoActionMode { Add, Edit }

data class ClassInfoUiState(
    val subject: String,
    val classroom: String? = null,
    val teacher: String? = null,
    val memo: String? = null,
    val color: String = (0xFFD9D9D9).toString(),
    val showDialog: Boolean = false,
    val loading: Boolean = false,
)

class ClassInfoViewModel(
    private val timetableRepository: TimetableRepository,
    val mode: ClassInfoActionMode,
    private val subject: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassInfoUiState(subject = ""))
    val uiState: StateFlow<ClassInfoUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    fun setClassInfo(classInfoType: ClassInfoType, value: String) {
        viewModelScope.launch {
            _uiState.update {
                when (classInfoType) {
                    ClassInfoType.Subject -> it.copy(subject = value)
                    ClassInfoType.Classroom -> it.copy(classroom = value)
                    ClassInfoType.Teacher -> it.copy(teacher = value)
                    ClassInfoType.Memo -> it.copy(memo = value)
                    ClassInfoType.Color -> it.copy(color = value)
                }
            }
        }
    }

    fun save() {
        if (uiState.value.subject == "") return
        viewModelScope.launch {
            timetableRepository.setClassInfo(
                ClassInfo(
                    subject = uiState.value.subject,
                    classroom = uiState.value.classroom,
                    teacher = uiState.value.teacher,
                    memo = uiState.value.memo,
                    color = uiState.value.color,
                ),
                subject,
            )
        }
    }

    fun delete() {
        viewModelScope.launch {
            timetableRepository.deleteClassInfoAndTimetable(subject)
        }
    }

    fun setShowDialog(showDialog: Boolean) {
        _uiState.update { it.copy(showDialog = showDialog) }
    }

    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }
        when (mode) {
            ClassInfoActionMode.Edit -> viewModelScope.launch {
                val classInfo = timetableRepository.getClassInfoBySubject(subject)
                if (classInfo != null) {
                    _uiState.update {
                        it.copy(
                            subject = classInfo.subject,
                            classroom = classInfo.classroom,
                            teacher = classInfo.teacher,
                            memo = classInfo.memo,
                            color = classInfo.color ?: (0xFFD9D9D9).toString(),
                            loading = false,
                        )
                    }
                } else {
                    _uiState.update { it.copy(loading = false) }
                }
            }
            ClassInfoActionMode.Add -> _uiState.update { it.copy(loading = false) }
        }
    }

    companion object {
        fun provideFactory(
            timetableRepository: TimetableRepository,
            mode: ClassInfoActionMode,
            subject: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ClassInfoViewModel(
                    timetableRepository = timetableRepository,
                    mode = mode,
                    subject = subject,
                ) as T
            }
        }
    }
}