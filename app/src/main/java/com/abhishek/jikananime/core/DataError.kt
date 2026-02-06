package com.abhishek.jikananime.core

import java.lang.Exception

sealed class DataError: Exception() {
    data class NetworkError(val code: Int, override val message: String? = null): DataError()
    data object EmptyBody: DataError()
    data object LimitReached: DataError()
    data class UnknownError(val exception: Throwable): DataError()
}