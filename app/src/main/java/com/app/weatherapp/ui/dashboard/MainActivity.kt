package com.app.weatherapp.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.base.BaseActivity
import com.app.weatherapp.consts.Constants
import com.app.weatherapp.data.network.base.BaseResponse
import com.app.weatherapp.databinding.ActivityMainBinding
import com.app.weatherapp.models.currentTempModels.CurrentTempResponse
import com.app.weatherapp.models.forcastDataModels.Data
import com.app.weatherapp.models.forcastDataModels.ForecastDataResponse
import com.app.weatherapp.models.showForecastModel.ForecastModel
import com.app.weatherapp.utils.Utils
import com.app.weatherapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: HomeViewModel by viewModels()
    private var mAdapter: ForecastAdapter? = null

    private var mCurrentTempData: CurrentTempResponse? = null
    private var mForecastTempData: ForecastDataResponse? = null
    private var mForecastList = ArrayList<ForecastModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        observers()
        clickListeners()
        initCurrentApiCalls()
    }

    private fun clickListeners() {
        mBinding.btnRetry.setOnClickListener {
            hideAllLayouts()
            initCurrentApiCalls()
        }
    }

    private fun initCurrentApiCalls() {
        mViewModel.getCurrentWeatherData()
    }

    private fun initForecastApiCall() {
        mViewModel.getForecastData()
    }

    private fun observers() {
        mViewModel.currentWeatherResponse.observe(this, Observer {
            if (it != null) {
                Log.i(">>>>", "observers: currentWeatherResponse: ${it.message}")
                when (it.status) {
                    BaseResponse.Status.LOADING -> {
                        //---show progress
                        showProgress()
                    }
                    BaseResponse.Status.ERROR -> {
                        showErrorScreen()
                    }
                    BaseResponse.Status.SUCCESS -> {
                        mCurrentTempData = it.data
                        initForecastApiCall()
                    }
                    BaseResponse.Status.COMPLETE -> {
                    }
                }
            }
        })

        mViewModel.forecastDataResponse.observe(this, Observer {
            if (it != null) {
                Log.i(">>>>", "observers: forecastDataResponse: ${it.message}")
                when (it.status) {
                    BaseResponse.Status.LOADING -> {
                        //---show progress
                        showProgress()
                    }
                    BaseResponse.Status.ERROR -> {
                        //---hide progress
                        showErrorScreen()
                    }
                    BaseResponse.Status.SUCCESS -> {
                        //---hide progress
                        mForecastTempData = it.data
                        showSuccessScreen()
                    }
                    BaseResponse.Status.COMPLETE -> {
                    }
                }
            }
        })
    }

    private fun setForeCastRecyclerView(dataList: ArrayList<ForecastModel>) {
        mAdapter = ForecastAdapter(
            this,
            dataList,
            object : ForecastAdapter.OnItemSelected {
                override fun onSelect(position: Int, data: ForecastModel) {

                }
            }
        )

        mBinding.forecastRV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentDayTemp() {
        if (mCurrentTempData != null) {
            mBinding.currentTempTv.text = "${
                Utils.convertFromKelvinToCelsius(mCurrentTempData?.main?.temp!!).toInt()
            }${Constants.Common.sDegreeSymbol}"
            mBinding.currentCityTv.text = mCurrentTempData?.name
        }
    }

    private fun getForCastModel(): ArrayList<ForecastModel> {
        var tempDate = ""
        var totalTempInCelsius = 0.0
        var indexCount = 0
        for (data: Data in mForecastTempData?.list!!) {
            val dateStr = getDate(data.dt_txt!!)
            if (tempDate == "") {
                tempDate = dateStr
            }
            if (tempDate == dateStr) {
                //--- change from kalvin to celsius and add
                totalTempInCelsius += Utils.convertFromKelvinToCelsius(data.main!!.temp!!)
                //--- increment the counter
                indexCount += 1
            } else {
                //--- average of all same day temp
                totalTempInCelsius /= indexCount
                //--- add to model list
                mForecastList.add(
                    ForecastModel(
                        Utils.getWeekDay(tempDate), totalTempInCelsius.toInt().toString()
                    )
                )
                //--- change tempDate
                tempDate = dateStr
                //--- make totalTempInCelsius to 0
                totalTempInCelsius = 0.0
                //--- set current date temp
                totalTempInCelsius = Utils.convertFromKelvinToCelsius(data.main!!.temp!!)
                //--- change index count to 1
                indexCount = 1
            }
        }
        if (mForecastList.size > 4) {
            mForecastList.removeAt(0)
        }

        return mForecastList
    }

    private fun getDate(dateTimeStr: String): String {
        return dateTimeStr.split(" ")[0]
    }

    private fun showSuccessScreen() {
        hideProgress()
        mBinding.successLay.visible(true)
        setCurrentDayTemp()
        setForeCastRecyclerView(getForCastModel())
    }

    private fun showErrorScreen() {
        hideProgress()
        mBinding.errorLay.visible(true)
    }

    private fun hideAllLayouts() {
        hideProgress()
        mBinding.successLay.visible(false)
        mBinding.errorLay.visible(false)
    }

    private fun showProgress() {
        mBinding.progressBar.visible(true)
    }

    private fun hideProgress() {
        mBinding.progressBar.visible(false)
    }
}