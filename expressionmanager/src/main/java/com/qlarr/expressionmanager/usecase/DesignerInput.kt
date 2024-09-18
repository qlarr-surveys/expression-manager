package com.qlarr.expressionmanager.usecase

import com.fasterxml.jackson.databind.node.ObjectNode
import com.qlarr.expressionmanager.model.ComponentIndex

data class DesignerInput(
    val state: ObjectNode,
    val componentIndexList: List<ComponentIndex>
)