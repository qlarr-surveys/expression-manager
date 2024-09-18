package com.qlarr.expressionmanager.context.assemble

import com.qlarr.expressionmanager.model.*
import org.junit.Test


class RuntimeScriptKtTest {

    @Test
    fun kabak() {
        val list = listOf(
            ChildlessComponent(
                code = "Q2",
                parentCode = "G2",
                surveyElementType = SurveyElementType.QUESTION,
                instructionList = listOf(
                    Instruction.SimpleState(
                        "kabaka",
                        ReservedCode.ValidationRule("validation_gt")
                    ),
                    Instruction.SimpleState(
                        "kabaka",
                        ReservedCode.ValidationRule("validation_gte")
                    ),
                    Instruction.SimpleState(
                        "!Q2.validation_gte && !Q2.validation_gt",
                        ReservedCode.Validity
                    )
                )
            )
        )
        val kabaka = list.runtimeScript(
            mapOf(
                Dependent("Q2", "validity") to listOf(
                    Dependency("Q2", ReservedCode.ValidationRule("validation_gt")),
                    Dependency("Q2", ReservedCode.ValidationRule("validation_gte"))
                )
            )
        )
        println(kabaka)
    }

}