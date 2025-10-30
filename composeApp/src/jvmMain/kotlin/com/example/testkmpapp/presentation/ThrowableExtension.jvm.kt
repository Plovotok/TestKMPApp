package com.example.testkmpapp.presentation

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

actual fun Throwable?.isInternetError(): Boolean =
    this is UnknownHostException || this is ConnectTimeoutException || this is HttpRequestTimeoutException || this is SocketTimeoutException || this is UnresolvedAddressException