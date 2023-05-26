package com.greenmate.greenmate.ui.addGreenMate

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.repository.GreenMateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddGreenMateViewModel @Inject constructor(
    private val repository: GreenMateRepository,
) : ViewModel() {
    private val _isModuleFirst = MutableStateFlow(true)
    val isModuleFirst: StateFlow<Boolean> get() = _isModuleFirst

    private val _isSavedSuccess = MutableStateFlow(false)
    val isSavedSuccess: StateFlow<Boolean> get() = _isSavedSuccess

    private val _plantTypes = MutableStateFlow(listOf("몬스테라", "홍콩야자", "테이블야자", "로즈마리", "커피나무"))
    private val _currentPlantTypes = MutableStateFlow(_plantTypes.value)
    val currentPlantTypes: StateFlow<List<String>> get() = _currentPlantTypes

    /* info */
    private val _currentPlantIndex = MutableStateFlow(0)
    val currentPlantIndex: StateFlow<Int> get() = _currentPlantIndex

    private val _greenMateImage = MutableStateFlow(R.drawable.plant1)
    val greenMateImage: StateFlow<Int> get() = _greenMateImage

    val serialNumber = MutableStateFlow("")
    val name = MutableStateFlow("")

    fun setModuleAdded(isModuleAdded: Boolean) {
        _isModuleFirst.value = isModuleAdded
    }

    fun isModuleAdded() = _isModuleFirst.value

    fun setCurrentPlantType(index: Int) {
        _currentPlantIndex.value = index
    }

    fun search(text: String) {
        if (text.isEmpty()) {
            _currentPlantTypes.value = _plantTypes.value
        } else {
            _currentPlantTypes.value = _plantTypes.value.filter { it.startsWith(text) }
        }
    }

    fun onTextChanged() {

    }

    fun saveGreenMate() {
        val newGreenMate = GreenMate(
            serialNumber.value,
            name.value,
            _plantTypes.value[_currentPlantIndex.value],
            "키우기 시작한 지 1일",
            "좋음",
            "좋음",
            "좋음",
            _greenMateImage.value
        )
        val response = repository.addGreenMate(newGreenMate)
        _isSavedSuccess.value = response
    }
}