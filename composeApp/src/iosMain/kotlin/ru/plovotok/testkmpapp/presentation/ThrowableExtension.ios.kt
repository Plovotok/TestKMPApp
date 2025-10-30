package ru.plovotok.testkmpapp.presentation

import io.ktor.client.engine.darwin.DarwinHttpRequestException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorNetworkConnectionLost
import platform.Foundation.NSURLErrorNotConnectedToInternet

@OptIn(ExperimentalForeignApi::class)
actual fun Throwable?.isInternetError(): Boolean {
    return this is DarwinHttpRequestException
            && this.origin.domain == NSURLErrorDomain
            && this.origin.code in listOf(NSURLErrorNetworkConnectionLost, NSURLErrorNotConnectedToInternet)
            || this is ConnectTimeoutException
            || this is SocketTimeoutException
}