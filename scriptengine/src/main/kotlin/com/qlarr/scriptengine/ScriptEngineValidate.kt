package com.qlarr.scriptengine

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.qlarr.expressionmanager.model.jacksonKtMapper
import com.qlarr.expressionmanager.usecase.ScriptValidationInput
import com.qlarr.expressionmanager.usecase.ScriptValidationOutput
import com.qlarr.expressionmanager.usecase.ValidationScriptError
import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.ResourceLimits
import org.json.JSONArray
import org.json.JSONObject
import javax.script.Bindings
import javax.script.Compilable
import javax.script.CompiledScript
import javax.script.ScriptEngine


class ScriptEngineValidation {
    private val compiledScript: CompiledScript

    init {
        val classLoader = javaClass.classLoader
        val script = classLoader.getResourceAsStream("my-library.min.js")!!.reader().readText()
        compiledScript = (engine as Compilable).compile("$script;" +
                "const QlarrScript = typeof globalThis !== 'undefined' ? globalThis.QlarrScript : this.QlarrScript;" +
                "QlarrScript.validateCode(instructionList);")
    }

    companion object {
        var engine: ScriptEngine = GraalJSScriptEngine.create(null,
            Context.newBuilder("js")
                .allowHostAccess(HostAccess.NONE)
                .allowHostClassLookup { false }
                .resourceLimits(
                    ResourceLimits.newBuilder()
                        .statementLimit(1000000, null)
                        .build()
                ) // Set resource limits
                .allowIO(false)
                .option("js.ecmascript-version", "2021"))

    }

    fun validate(input: List<ScriptValidationInput>): List<ScriptValidationOutput> {
        val scriptParams: Bindings = engine.createBindings()
        val items = JSONArray()
        input.forEach {
            val item = JSONObject()
            item.put("script", it.componentInstruction.instruction.text)
            item.put("allowedVariables", JSONArray(it.dependencies))
            items.put(item)
        }
        scriptParams["instructionList"] = items.toString()
        val result = compiledScript.eval(scriptParams).toString()
        val processed:List<List<ValidationScriptError>> = jacksonKtMapper.readValue(result, jacksonTypeRef<List<List<ValidationScriptError>>>())
            ?: listOf()
        return input.mapIndexed { index, scriptValidationInput ->
            ScriptValidationOutput(scriptValidationInput.componentInstruction, processed[index])
        }

    }

}