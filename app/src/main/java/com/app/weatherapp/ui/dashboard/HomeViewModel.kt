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
import com.app.weatherapp.models.forcastDataModels.ForecastDataResponse
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


    private val _forecastDataResponse: MutableLiveData<BaseResponse<ForecastDataResponse>> =
        MutableLiveData()
    val forecastDataResponse: LiveData<BaseResponse<ForecastDataResponse>>
        get() = _forecastDataResponse

    fun getCurrentWeatherData() {
        _currentWeatherResponse.postValue(BaseResponse.loading())
        Coroutines.main {
            try {
                val mResponse = homeRepository.getCurrentWeatherData()
                val data =
                    Gson().fromJson(Gson().toJson(mResponse), CurrentTempResponse::class.java)
                if (data.cod == ApiConstants.StatusCode.SERVER_SUCCESS_CODE) {
                    _currentWeatherResponse.postValue(BaseResponse.success(data))
                    return@main
                }
                _currentWeatherResponse.postValue(BaseResponse.error(context.getString(R.string.msg)))
            } catch (e: ApiException) {
                _currentWeatherResponse.postValue(BaseResponse.error(e.message!!))
            } catch (e: NoInternetException) {
                _currentWeatherResponse.postValue(BaseResponse.error(e.message!!))
            } catch (e: Exception) {
                _currentWeatherResponse.postValue(BaseResponse.error(context.getString(R.string.something_went_wrong)))

            }
        }

    }

    fun getForecastData() {
        _forecastDataResponse.postValue(BaseResponse.loading())
        Coroutines.main {
            try {
                val mResponse = homeRepository.getForecastData()
                val data =
                    Gson().fromJson(Gson().toJson(mResponse), ForecastDataResponse::class.java)
                if (data.cod == ApiConstants.StatusCode.SERVER_SUCCESS_CODE.toString()) {
                    _forecastDataResponse.postValue(BaseResponse.success(data))
                    return@main
                }
                _forecastDataResponse.postValue(BaseResponse.error(context.getString(R.string.msg)))
            } catch (e: ApiException) {
                _forecastDataResponse.postValue(BaseResponse.error(e.message!!))
            } catch (e: NoInternetException) {
                _forecastDataResponse.postValue(BaseResponse.error(e.message!!))
            } catch (e: Exception) {
                _forecastDataResponse.postValue(BaseResponse.error(context.getString(R.string.something_went_wrong)))
            }
        }

    }

}