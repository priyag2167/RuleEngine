package com.example.ruleengine.repository

import com.example.ruleengine.database.AppDatabase
import com.example.ruleengine.database.RuleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RuleRepository(private val database: AppDatabase) {

    suspend fun insertRule(ruleString: String) {
        val ruleEntity = RuleEntity(ruleString = ruleString)
        withContext(Dispatchers.IO) {
            database.insertRule(ruleEntity)
        }
    }

    suspend fun getRuleById(ruleId: Int): RuleEntity? {
        return withContext(Dispatchers.IO) {
            database.getRuleById(ruleId)
        }
    }

}
