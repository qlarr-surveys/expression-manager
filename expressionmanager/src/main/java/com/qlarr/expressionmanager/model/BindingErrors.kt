package com.qlarr.expressionmanager.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "name",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(BindingErrors.ForwardDependency::class, name = "ForwardDependency"),
    JsonSubTypes.Type(BindingErrors.ScriptError::class, name = "ScriptError"),
    JsonSubTypes.Type(BindingErrors.InvalidSkipReference::class, name = "InvalidSkipReference"),
    JsonSubTypes.Type(BindingErrors.InvalidReference::class, name = "InvalidReference"),
    JsonSubTypes.Type(BindingErrors.InvalidChildReferences::class, name = "InvalidChildReferences"),
    JsonSubTypes.Type(BindingErrors.DuplicateRandomGroupItems::class, name = "DuplicateRandomGroupItems"),
    JsonSubTypes.Type(BindingErrors.DuplicatePriorityGroupItems::class, name = "DuplicatePriorityGroupItems"),
    JsonSubTypes.Type(BindingErrors.PriorityLimitMismatch::class, name = "PriorityLimitMismatch"),
    JsonSubTypes.Type(BindingErrors.PriorityGroupItemNotChild::class, name = "PriorityGroupItemNotChild"),
    JsonSubTypes.Type(BindingErrors.RandomGroupItemNotChild::class, name = "RandomGroupItemNotChild"),
    JsonSubTypes.Type(BindingErrors.InvalidRandomItem::class, name = "InvalidRandomItem"),
    JsonSubTypes.Type(BindingErrors.InvalidPriorityItem::class, name = "InvalidPriorityItem"),
    JsonSubTypes.Type(BindingErrors.InvalidInstructionInEndGroup::class, name = "InvalidInstructionInEndGroup"),
    JsonSubTypes.Type(BindingErrors.SkipToEndOfEndGroup::class, name = "SkipToEndOfEndGroup"),
    JsonSubTypes.Type(BindingErrors.DuplicateInstructionCode::class, name = "DuplicateInstructionCode")
)
sealed class BindingErrors(val name: String = "") {

    data class ForwardDependency(val dependency: Dependency) : BindingErrors("ForwardDependency")
    data class ScriptError(
        val message: String,
        val start: Int,
        val end: Int
    ): BindingErrors("ScriptError")
    data class InvalidSkipReference(val component: String) : BindingErrors("InvalidSkipReference")
    object SkipToEndOfEndGroup : BindingErrors("SkipToEndOfEndGroup")
    data class InvalidReference(val reference: String, val invalidComponent: Boolean) :
        BindingErrors("InvalidReference")

    data class InvalidChildReferences(val children: List<String>) : BindingErrors("InvalidChildReferences")
    object PriorityLimitMismatch : BindingErrors("DuplicateLimitMismatch")
    data class DuplicatePriorityGroupItems(val items: List<String>) : BindingErrors("DuplicatePriorityGroupItems")
    data class PriorityGroupItemNotChild(val items: List<String>) : BindingErrors("PriorityGroupItemNotChild")
    data class InvalidPriorityItem(val items: List<String>) : BindingErrors("InvalidPriorityItem")
    data class InvalidRandomItem(val items: List<String>) : BindingErrors("InvalidRandomItem")
    data class DuplicateRandomGroupItems(val items: List<String>) : BindingErrors("DuplicateRandomGroupItems")
    data class RandomGroupItemNotChild(val items: List<String>) : BindingErrors("RandomGroupItemNotChild")
    object DuplicateInstructionCode : BindingErrors("DuplicateInstructionCode")
    object InvalidInstructionInEndGroup : BindingErrors("InvalidInstructionInEndGroup")

}
