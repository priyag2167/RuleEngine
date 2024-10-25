package com.example.ruleengine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ruleengine.repository.RuleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RuleViewModel(private val repository: RuleRepository) : ViewModel() {

    fun insertRule(ruleString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRule(ruleString)
        }
    }

    fun getRuleById(ruleId: Int, callback: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val rule = repository.getRuleById(ruleId)
            rule?.let {
                callback(it.ruleString)
            }
        }
    }

}
