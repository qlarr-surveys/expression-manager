package com.qlarr.expressionmanager.usecase

import com.qlarr.expressionmanager.model.BindingErrors
import com.qlarr.expressionmanager.model.ComponentInstruction

interface ScriptEngineValidate {
    fun validate(input: List<ScriptValidationInput>): List<ScriptValidationOutput>
}

interface ScriptEngineNavigate {
    fun navigate(script: String): String
}

data class ScriptValidationInput(
    val componentInstruction: ComponentInstruction,
    val dependencies: List<String>
)

data class ScriptValidationOutput(
    val componentInstruction: ComponentInstruction,
    val result: List<ValidationScriptError>
)

data class ValidationScriptError(
    val message: String,
    val start: Int,
    val end: Int
){
    fun toBindingError() = BindingErrors.ScriptError(
        message, start, end
    )
}
