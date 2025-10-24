package com.example.testkmpapp.data.ext

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path

suspend inline fun <reified T> HttpClient.makeRequest(
    path: String,
    params: Map<String, String> = mapOf(),
    method: HttpMethod,
    body: Any? = null,
): T {
    val response = this.dowRawRequest(path, params, method, body)
    return response.body()
}

suspend fun HttpClient.dowRawRequest(
    path: String,
    params: Map<String, String> = mapOf(),
    method: HttpMethod,
    body: Any? = null,
) = request {
    expectSuccess = true
    this.method = method
    url {
        protocol = URLProtocol.HTTPS
        host = "api.bigbookapi.com"
        path(path)
        parameters.append(params)
        contentType(ContentType.Application.Json)
    }
    body?.let {
        setBody(it)
    }
}

private fun ParametersBuilder.append(params: Map<String, String>) {
    params.forEach { (key, value) ->
        append(key, value)
    }
}