package com.greenmate.greenmate.ui.addGreenMate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.repository.GreenMateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGreenMateViewModel @Inject constructor(
    private val repository: GreenMateRepository,
) : ViewModel() {
    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage: StateFlow<String> get() = _snackBarMessage

    private val _isModuleFirst = MutableStateFlow(true)
    val isModuleFirst: StateFlow<Boolean> get() = _isModuleFirst

    private val _isSerialNumberFound = MutableStateFlow(false)
    val isSerialNumberFound: StateFlow<Boolean> get() = _isSerialNumberFound

    private val _isSavedSuccess = MutableStateFlow(false)
    val isSavedSuccess: StateFlow<Boolean> get() = _isSavedSuccess

    private val _plantTypes = MutableStateFlow(listOf("몬스테라", "홍콩야자", "테이블야자", "로즈마리", "커피나무"))
    private val _currentPlantTypes = MutableStateFlow(_plantTypes.value)
    val currentPlantTypes: StateFlow<List<String>> get() = _currentPlantTypes

    /* info */
    private val _currentPlantName = MutableStateFlow("")
    val currentPlantName: StateFlow<String> get() = _currentPlantName

    private val _greenMateImage = MutableStateFlow(R.drawable.plant1)
    val greenMateImage: StateFlow<Int> get() = _greenMateImage

    val serialNumber = MutableStateFlow("")
    val name = MutableStateFlow("")

    fun setModuleAdded(isModuleAdded: Boolean) {
        _isModuleFirst.value = isModuleAdded
    }

    fun isModuleAdded() = _isModuleFirst.value

    fun setCurrentPlantType(name: String) {
        _currentPlantName.value = name
    }

    fun search(text: String) {
        if (text.isEmpty()) {
            _currentPlantTypes.value = _plantTypes.value
        } else {
            _currentPlantTypes.value = _plantTypes.value.filter { it.startsWith(text) }
        }
    }

    /** network **/
    fun findSerialNumber() {
        if (serialNumber.value.isEmpty()) {
            _snackBarMessage.value = "시리얼 넘버를 입력해주세요"
            return
        }

        viewModelScope.launch {
            val response = repository.findSerialNumber(serialNumber.value)
            if (response.isSuccess) {
                response.getOrNull()?.let{
                    if(it){
                        _isSerialNumberFound.value = true
                    }
                    else{
                        _snackBarMessage.value = "시리얼 넘버와 일치하는 모듈이 없습니다"
                    }
                }
            } else {
                _snackBarMessage.value = "시리얼 넘버가 일치하지 않습니다"
            }
        }
    }

    fun saveGreenMate() {
        val newGreenMate = GreenMate(
            serialNumber.value,
            name.value,
            _currentPlantName.value,
            "키우기 시작한 지 1일",
            "좋음",
            "좋음",
            "좋음",
            _greenMateImage.value
        )
        viewModelScope.launch {
            val response = repository.addGreenMate(newGreenMate)
            _isSavedSuccess.value = response.isSuccess
        }
    }
}