package com.example.ruleengine.models

data class Node(
    val type: String,      // "operator" for AND/OR, "operand" for conditions
    val left: Node? = null, // Left child node
    val right: Node? = null, // Right child node
    val value: Any? = null  // Value for operand nodes, e.g., age, department, operator
)
