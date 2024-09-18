package com.qlarr.expressionmanager.model



data class NavigationUseCaseInput(
    val values: Map<String, Any> = mapOf(),
    val lang: String? = null,
    val navigationInfo: NavigationInfo = NavigationInfo()
)