package com.app.weatherapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.weatherapp.base.BaseActivity
import com.app.weatherapp.data.network.base.BaseResponse
import com.app.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initCalls()
        observers()

    }

    private fun initCalls() {
        mViewModel.getCurrentWeatherData()
    }

    private val TAG = ">>>>MA"

    private fun observers() {
        mViewModel.currentWeatherResponse.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    BaseResponse.Status.LOADING -> {
                        //---show progress
                        Log.i(TAG, "observers: LOADING")
                    }
                    BaseResponse.Status.ERROR -> {
                        //---hide progress
                        Log.i(TAG, "observers: ERROR")
                    }
                    BaseResponse.Status.SUCCESS -> {
                        //---hide progress
                        Log.i(TAG, "observers: SUCCESS")
                        Log.i(TAG, "observers: ${it.data.toString()}")

                    }
                    BaseResponse.Status.COMPLETE -> {
                    }
                }
            }
        })
    }
}