package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.model.repository.GreenMateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DetailEditViewModel @Inject constructor(
    private val repository: GreenMateRepository,
) : ViewModel() {


}