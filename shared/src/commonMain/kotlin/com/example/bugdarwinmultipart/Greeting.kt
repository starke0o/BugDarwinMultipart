package com.example.bugdarwinmultipart

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

expect fun clientEngine(): HttpClientEngine

class Greeting {
    private val platform: Platform = getPlatform()

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        Napier.base(DebugAntilog())
    }

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun startSampleRequest() {
        scope.launch {
            sampleRequest()
        }
    }

    private suspend fun sampleRequest() {
        try {
            val content = "Text Text Text".repeat(20_000).encodeToByteArray()

            Napier.i("Start sample request")

            val client = HttpClient(clientEngine())

            val response = client.post("https://httpbin.org/post") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                "file",
                                content,
                                Headers.build {
                                    append(HttpHeaders.ContentType, "application/pdf")
                                    append(HttpHeaders.ContentDisposition, "filename=\"file.pdf\"")
                                }
                            )
                        }
                    )
                )
            }

            Napier.i("Sample request finished with response: $response")
        } catch (e: Exception) {
            Napier.i("Sample request failed with error: $e")
            Napier.d(e.stackTraceToString())
        }
    }
}
