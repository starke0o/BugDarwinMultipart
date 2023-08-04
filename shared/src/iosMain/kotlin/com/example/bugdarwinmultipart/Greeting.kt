package com.example.bugdarwinmultipart

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun clientEngine(): HttpClientEngine = Darwin.create()