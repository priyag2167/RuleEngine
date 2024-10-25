package com.example.ruleengine.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RuleDao {
    @Insert
    suspend fun insertRule(rule: RuleEntity)

    @Query("SELECT * FROM rules WHERE id = :ruleId")
    suspend fun getRule(ruleId: Int): RuleEntity
}