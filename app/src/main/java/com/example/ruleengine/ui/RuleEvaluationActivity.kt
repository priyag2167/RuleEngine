package com.example.ruleengine.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ruleengine.R
import com.example.ruleengine.database.AppDatabase
import com.example.ruleengine.models.Node
import com.example.ruleengine.repository.RuleRepository
import com.example.ruleengine.utils.RuleEvaluator
import com.example.ruleengine.viewmodel.RuleViewModel
import com.example.ruleengine.viewmodel.RuleViewModelFactory

class RuleEvaluationActivity : AppCompatActivity() {
    private lateinit var viewModel: RuleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule_evaluation)

        val resultText: TextView = findViewById(R.id.resultText)
        val ageInput: EditText = findViewById(R.id.ageInput)
        val departmentInput: EditText = findViewById(R.id.departmentInput)
        val salaryInput: EditText = findViewById(R.id.salaryInput)
        val experienceInput: EditText = findViewById(R.id.experienceInput) // New input for experience
        val evaluateButton: Button = findViewById(R.id.evaluateButton)

        val ruleRepository = RuleRepository(AppDatabase)
        val factory = RuleViewModelFactory(ruleRepository)
        viewModel = ViewModelProvider(this, factory).get(RuleViewModel::class.java)

        val ruleId = intent.getIntExtra("ruleId", -1)

        if (ruleId != -1) {
            viewModel.getRuleById(ruleId) { ruleString ->
                val ruleNode = combine_rules(listOf(ruleString))

                evaluateButton.setOnClickListener {
                    val age = ageInput.text.toString().toIntOrNull()
                    val department = departmentInput.text.toString()
                    val salary = salaryInput.text.toString().toIntOrNull()
                    val experience = experienceInput.text.toString().toIntOrNull() // Fetch experience input

                    if (age != null && salary != null && experience != null) {
                        val userData = mapOf(
                            "age" to age,
                            "department" to department,
                            "salary" to salary,
                            "experience" to experience // Include experience in user data
                        )

                        val result = RuleEvaluator.evaluateRule(ruleNode, userData)

                        runOnUiThread {
                            resultText.text = if (result) "Eligible" else "Not Eligible"
                        }
                    } else {
                        resultText.text = "Please enter valid Data"
                    }
                }
            }
        } else {
            resultText.text = "Invalid Rule ID"
        }
    }

    private fun combine_rules(rules: List<String>): Node {
        if (rules.isEmpty()) {
            throw IllegalArgumentException("Rule list cannot be empty")
        }

        val combinedOperands = mutableListOf<Node>()

        for (rule in rules) {
            val ruleNode = createRule(rule)
            combinedOperands.add(ruleNode)
        }

        // Check how many operands we have
        return if (combinedOperands.size == 1) {
            // If there's only one rule, return it directly
            combinedOperands[0]
        } else {
            // Combine multiple rules with AND operator
            Node(
                type = "operator",
                left = combinedOperands[0],
                right = combinedOperands[1],
                value = "AND"
            )
        }
    }


    private fun createRule(ruleString: String): Node {
        // Add logic to parse experience
        return Node(
            type = "operator",
            left = Node(type = "operand", value = mapOf("attribute" to "age", "operator" to ">", "threshold" to 30)),
            right = Node(type = "operand", value = mapOf("attribute" to "department", "operator" to "=", "threshold" to "Sales")),
            value = "AND"
        )
    }
}
