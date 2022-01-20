package com.app.weatherapp.data.network.base

data class BaseResponse<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        COMPLETE
    }

    companion object {
        fun <T> success(data: T?): BaseResponse<T> {
            return BaseResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String): BaseResponse<T> {
            return BaseResponse(Status.ERROR, null, message)
        }

        fun <T> loading(): BaseResponse<T> {
            return BaseResponse(Status.LOADING, null, null)
        }

        fun <T> complete(): BaseResponse<T> {
            return BaseResponse(Status.COMPLETE, null, null)
        }
    }
}
