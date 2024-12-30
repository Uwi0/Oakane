package com.kakapo.oakane.model.system

enum class Theme {
    System, Light, Dark
}

fun Int.asTheme(): Theme {
    return Theme.entries.find { it.ordinal == this } ?: Theme.System
}