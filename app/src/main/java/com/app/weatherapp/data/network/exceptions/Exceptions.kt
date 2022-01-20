package com.app.weatherapp.data.network.exceptions

import java.io.IOException

class ApiException(message: String) : IOException(message)
class UnAuthorizedException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)