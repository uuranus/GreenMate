package com.greenmate.greenmate.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.User
import com.greenmate.greenmate.model.repository.GreenMateRepository
import com.greenmate.greenmate.util.setUerId
import com.greenmate.greenmate.util.setUserPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GreenMateRepository,
) : ViewModel() {

    private val _userInfo = MutableStateFlow(User("PEPL", "", "1111", "20020304"))
    val userInfo: StateFlow<User> get() = _userInfo

    private val _isDataLoaded = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> get() = _isDataLoaded

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> get() = _isSuccess

    private val _greenMates = MutableStateFlow<List<GreenMate>>(
        emptyList()
    )
    val greenMates: StateFlow<List<GreenMate>> get() = _greenMates

    private val _mainGreenMate =
        MutableStateFlow(GreenMate(name = "", type = "", image = ""))
    val mainGreenMate: StateFlow<GreenMate> get() = _mainGreenMate

    fun isDataLoaded() = _isDataLoaded.value
    fun setIsDataLoaded(isDataLoaded: Boolean) {
        _isDataLoaded.value = isDataLoaded
    }

    fun isGreenMateEmpty() = _greenMates.value.isEmpty()
    fun isMainGreenMateEmpty() = _mainGreenMate.value.name.isEmpty()

    fun setMainGreenMateByFirst() {
        if (_greenMates.value.isEmpty()) return
        _mainGreenMate.value = _greenMates.value[0].copy()
    }

    fun setMainGreenMateCurrent() {
        setMainGreenMateByFirst()
        if (_greenMates.value.find { it.id == _mainGreenMate.value.id } == null) {

        }
//        _mainGreenMate.value = _mainGreenMate.value.copy()
    }

    fun setMainGreenMate(greenMate: GreenMate) {
        _mainGreenMate.value = greenMate
    }

    fun getSelectedGreenMate(): GreenMate {
        return _mainGreenMate.value
    }


    /* network */
    fun login(id: String, password: String) {
//        if (_userInfo.value.id.isNotEmpty()) {
//            _isDataLoaded.value = true
//            return
//        }

        viewModelScope.launch {
            val result = repository.login(id, password)
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    _userInfo.value = it
                    setUerId(it.id)
                    setUserPassword(it.password)
                    getAllGreenMates()
                }
            }
        }
    }

    fun getAllGreenMates() {
        viewModelScope.launch {
            val response = repository.getAllGreenMates()
            response.getOrNull()?.let {
                _userInfo.value = it.userData
                _greenMates.value = it.greenMates
                _isDataLoaded.value = true
            }
        }
    }
}