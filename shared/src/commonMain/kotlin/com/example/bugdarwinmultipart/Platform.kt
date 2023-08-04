package com.example.bugdarwinmultipart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform