package com.example.ruleengine.utils

import com.example.ruleengine.models.Node

object RuleEvaluator {

    fun evaluateRule(node: Node, data: Map<String, Any>): Boolean {
        return when (node.type) {
            "operator" -> {
                val leftResult = node.left?.let { evaluateRule(it, data) } ?: false
                val rightResult = node.right?.let { evaluateRule(it, data) } ?: false
                when (node.value) {
                    "AND" -> leftResult && rightResult
                    "OR" -> leftResult || rightResult
                    else -> false
                }
            }
            "operand" -> {
                val valueMap = node.value as? Map<String, Any> ?: return false
                val attribute = valueMap["attribute"]
                val operator = valueMap["operator"]
                val threshold = valueMap["threshold"]

                return when (operator) {
                    ">" -> (data[attribute] as? Int ?: 0) > (threshold as Int)
                    "=" -> data[attribute] == threshold
                    else -> false
                }
            }
            else -> false
        }
    }
}
