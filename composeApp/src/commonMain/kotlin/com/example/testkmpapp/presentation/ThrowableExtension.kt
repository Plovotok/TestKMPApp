package com.example.testkmpapp.presentation

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode

expect fun Throwable?.isInternetError(): Boolean

fun Throwable?.shortDescription(): String? = when  {
    isNotFoundError() -> {
        "Not found"
    }
    this is ResponseException -> {
        if (this.response.status.value == 402) {
            "Your daily points limit of 50 has been reached"
        } else {
            this.message
        }
    }
    else -> "Something went wrong..."
}

fun Throwable?.description(): String? = when {
    isNotFoundError() -> {
        "Not found"
    }
    this is ResponseException -> {
        if (this.response.status.value == 402) {
            "Your daily points limit of 50 has been reached. Please upgrade your plan to continue using the API."
        } else {
            this.message
        }
    }
    else -> "Something went wrong..."
}

fun Throwable?.isNotFoundError(): Boolean = (this is ClientRequestException) && (this.response.status == HttpStatusCode.NotFound)