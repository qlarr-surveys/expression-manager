package com.qlarr.expressionmanager.common

import com.qlarr.expressionmanager.model.SurveyComponent
import com.qlarr.expressionmanager.usecase.ScriptEngineValidate
import com.qlarr.expressionmanager.usecase.ScriptValidationInput
import com.qlarr.expressionmanager.usecase.ScriptValidationOutput
import com.qlarr.scriptengine.ScriptEngineValidation

fun SurveyComponent.getErrorsCount(): Int {
    var returnResult = errors.size
    instructionList.forEach { instruction ->
        returnResult += instruction.errors.size
    }
    children.forEach { component ->
        returnResult += component.getErrorsCount()
    }

    return returnResult
}

fun buildScriptEngine(): ScriptEngineValidate {
    val scriptEngineWrapper = ScriptEngineValidation()
    return object : ScriptEngineValidate {
        override fun validate(input: List<ScriptValidationInput>): List<ScriptValidationOutput> {
            return scriptEngineWrapper.validate(input)
        }


    }
}
