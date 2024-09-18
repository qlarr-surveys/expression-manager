package com.qlarr.expressionmanager.usecase

import com.qlarr.expressionmanager.model.ResponseField
import com.qlarr.expressionmanager.context.assemble.ContextBuilder
import com.qlarr.expressionmanager.context.assemble.NotSkippedInstructionManifesto
import com.qlarr.expressionmanager.context.assemble.getSchema
import com.qlarr.expressionmanager.context.assemble.runtimeScript
import com.qlarr.expressionmanager.dependency.DependencyMapper
import com.qlarr.expressionmanager.model.ComponentIndex
import com.qlarr.expressionmanager.model.StringImpactMap
import com.qlarr.expressionmanager.model.Survey
import com.qlarr.expressionmanager.model.toStringImpactMap

interface ValidationUseCase {
    fun validate(validateSpecialTypeGroups: Boolean = true): ValidationOutput
}

class ValidationUseCaseImpl(scriptEngine: ScriptEngineValidate, survey: Survey) : ValidationUseCase {
    private val contextManager = ContextBuilder(listOf(survey).toMutableList(), scriptEngine)
    override fun validate(validateSpecialTypeGroups: Boolean): ValidationOutput {

        contextManager.validate(validateSpecialTypeGroups)
        val sanitisedComponents = contextManager.sanitizedNestedComponents
        val dependencyMapper = DependencyMapper(sanitisedComponents)
        return ValidationOutput(
            contextManager.components[0] as Survey,
            dependencyMapper.impactMap.toStringImpactMap(),
            contextManager.components.getSchema(),
            script = sanitisedComponents.runtimeScript(dependencyMapper.dependencyMap),
            componentIndexList = contextManager.componentIndexList,
            skipMap = contextManager.skipMap
        )
    }

}

data class ValidationOutput(
    val survey: Survey = Survey(),
    val impactMap: StringImpactMap = mapOf(),
    val schema: List<ResponseField> = listOf(),
    val script: String,
    val componentIndexList: List<ComponentIndex>,
    val skipMap: Map<String, List<NotSkippedInstructionManifesto>>
)