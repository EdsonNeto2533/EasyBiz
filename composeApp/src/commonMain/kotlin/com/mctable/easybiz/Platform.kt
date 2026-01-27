package com.mctable.easybiz

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform