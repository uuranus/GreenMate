package com.greenmate.greenmate.ui.addGreenMate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.repository.GreenMateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
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

    private val _isCameraNeedToStart = MutableStateFlow(true)
    val isCameraNeedToStart: StateFlow<Boolean> get() = _isCameraNeedToStart

    private val _plantTypes = MutableStateFlow(listOf("몬스테라", "홍콩야자", "테이블야자", "로즈마리", "커피나무"))
    private val _currentPlantTypes = MutableStateFlow(_plantTypes.value)
    val currentPlantTypes: StateFlow<List<String>> get() = _currentPlantTypes

    /* info */
    private val _currentPlantName = MutableStateFlow("")
    val currentPlantName: StateFlow<String> get() = _currentPlantName

    private val _greenMateImage = MutableStateFlow("")
    val greenMateImage: StateFlow<String> get() = _greenMateImage

    val serialNumber = MutableStateFlow("")
    val name = MutableStateFlow("")

    fun setModuleAdded(isModuleAdded: Boolean) {
        _isModuleFirst.value = isModuleAdded
    }

    fun isModuleAdded() = _isModuleFirst.value

    fun getModuleId() = serialNumber.value

    fun setCurrentPlantType(name: String) {
        _currentPlantName.value = name
    }

    fun saveImage(imageUrl: String) {
        _greenMateImage.value = imageUrl
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
            val response = repository.isSerialNumberExist(serialNumber.value)
            if (response.isSuccess) {
                response.getOrNull()?.let {
                    if (it) {
                        _isSerialNumberFound.value = true
                    } else {
                        _snackBarMessage.value = "시리얼 넘버와 일치하는 모듈이 없습니다"
                    }
                }
            } else {
                _snackBarMessage.value = "시리얼 넘버가 일치하지 않습니다"
            }
        }
    }

    fun saveGreenMate() {
        viewModelScope.launch {

            val newGreenMate = GreenMate(
                serialNumber.value,
                name.value,
                _currentPlantName.value,
                "키우기 시작한 지 1일",
                "좋음",
                "좋음",
                "좋음",
                "좋음",
                _greenMateImage.value
            )

            val response = repository.addGreenMate(newGreenMate)
            println("Response $response")
            _isSavedSuccess.value = response.isSuccess
            if (response.isSuccess.not()) {
                _snackBarMessage.value = "그린메이트를 추가하는데 실패했습니다"
            }
        }
    }
}