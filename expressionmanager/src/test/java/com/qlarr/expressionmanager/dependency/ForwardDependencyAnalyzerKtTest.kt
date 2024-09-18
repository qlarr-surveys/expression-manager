package com.qlarr.expressionmanager.dependency

import com.qlarr.expressionmanager.common.buildScriptEngine
import com.qlarr.expressionmanager.model.ReservedCode
import com.qlarr.expressionmanager.context.assemble.ContextBuilder
import com.qlarr.expressionmanager.model.Group
import com.qlarr.expressionmanager.model.Instruction
import com.qlarr.expressionmanager.model.Question
import org.junit.Test

class ForwardDependencyAnalyzerKtTest {

    @Test
    fun `bindComponent loads survey elements and evaluates its instructions`() {
        val QUESTION_ONE = Question(
            "Q1", listOf(
                Instruction.SimpleState("Q2.relevance", ReservedCode.ConditionalRelevance)
            )
        )

        val QUESTION_TWO = Question(
            "Q2", listOf(
                Instruction.SimpleState("Q1.conditional_relevance", ReservedCode.ConditionalRelevance)
            )
        )

        val QUESTION_THREE = Question(
            "Q3", listOf(
                Instruction.SimpleState("Q2.relevance", ReservedCode.ConditionalRelevance)
            )
        )

        val group = Group("G1", listOf(), listOf(QUESTION_ONE, QUESTION_TWO, QUESTION_THREE))
        val contextManager = ContextBuilder(mutableListOf(group), buildScriptEngine())
        contextManager.validate()
        contextManager.components[0]
    }

}