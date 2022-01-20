package com.app.weatherapp.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.weatherapp.R
import com.app.weatherapp.data.network.base.BaseResponse
import com.app.weatherapp.data.network.consts.ApiConstants
import com.app.weatherapp.data.network.exceptions.ApiException
import com.app.weatherapp.data.network.exceptions.NoInternetException
import com.app.weatherapp.data.repository.HomeRepository
import com.app.weatherapp.models.currentTempModels.CurrentTempResponse
import com.app.weatherapp.utils.Coroutines
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _currentWeatherResponse: MutableLiveData<BaseResponse<CurrentTempResponse>> =
        MutableLiveData()
    val currentWeatherResponse: LiveData<BaseResponse<CurrentTempResponse>>
        get() = _currentWeatherResponse

    fun getCurrentWeatherData() {
        _currentWeatherResponse.value = BaseResponse.loading()
        Coroutines.main {
            try {
                val mResponse = homeRepository.getCurrentWeatherData()
                val data =
                    Gson().fromJson(Gson().toJson(mResponse), CurrentTempResponse::class.java)
                if (data.cod == ApiConstants.StatusCode.SERVER_SUCCESS_CODE) {
                    _currentWeatherResponse.value = BaseResponse.success(data)
                    return@main
                }
                _currentWeatherResponse.value = BaseResponse.error(context.getString(R.string.msg))
            } catch (e: ApiException) {
                _currentWeatherResponse.value = BaseResponse.error(e.message!!)
            } catch (e: NoInternetException) {
                _currentWeatherResponse.value = BaseResponse.error(e.message!!)
            } catch (e: Exception) {
                _currentWeatherResponse.value =
                    BaseResponse.error(context.getString(R.string.something_went_wrong))
            }
        }

    }

}