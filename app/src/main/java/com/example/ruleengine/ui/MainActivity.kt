package com.example.ruleengine.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ruleengine.R
import com.example.ruleengine.database.AppDatabase
import com.example.ruleengine.repository.RuleRepository
import com.example.ruleengine.viewmodel.RuleViewModel
import com.example.ruleengine.viewmodel.RuleViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RuleViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ruleInput = findViewById<EditText>(R.id.ruleInput)
        val ruleIdInput = findViewById<EditText>(R.id.ruleIdInput)
        val createRuleButton = findViewById<Button>(R.id.createRuleButton)
        val evaluateRuleButton = findViewById<Button>(R.id.evaluateRuleButton)
        progressBar = findViewById(R.id.progressBar)

        // Use AppDatabase directly since it's a singleton object
        val ruleRepository = RuleRepository(AppDatabase)

        val factory = RuleViewModelFactory(ruleRepository)
        viewModel = ViewModelProvider(this, factory).get(RuleViewModel::class.java)

        // Rule Creation Logic
        createRuleButton.setOnClickListener {
            val ruleString = ruleInput.text.toString()
            if (ruleString.isNotEmpty()) {
                showLoader() // Show the loader
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertRule(ruleString)
                    withContext(Dispatchers.Main) {
                        hideLoader() // Hide the loader
                        Toast.makeText(this@MainActivity, "Rule Add Successfully", Toast.LENGTH_SHORT).show()
                        refreshActivity() // Refresh the activity
                    }
                }
            }
        }

        // Rule Evaluation Logic
        evaluateRuleButton.setOnClickListener {
            val ruleId = ruleIdInput.text.toString().toIntOrNull()
            if (ruleId != null) {
                // Navigate to RuleEvaluationActivity and pass the rule ID
                val intent = Intent(this, RuleEvaluationActivity::class.java)
                intent.putExtra("ruleId", ruleId)
                startActivity(intent)
            }
        }
    }

    private fun showLoader() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun hideLoader() {
        progressBar.visibility = ProgressBar.GONE
    }

    private fun refreshActivity() {
        val intent = intent
        finish() // Close current activity
        startActivity(intent) // Restart activity
    }
}
