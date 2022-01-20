package com.app.weatherapp.ui.dashboard

import androidx.lifecycle.ViewModel
import com.app.weatherapp.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: HomeRepository
) : ViewModel() {

}