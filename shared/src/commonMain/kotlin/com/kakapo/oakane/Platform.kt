package com.kakapo.oakane

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform