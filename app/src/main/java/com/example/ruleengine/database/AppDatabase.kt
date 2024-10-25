package com.example.ruleengine.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

object AppDatabase {  // It's already an object (singleton)
    private const val IP = "10.0.2.2"  // Adjust for emulator or real device IP
    private const val PORT = "1433"
    private const val DATABASE_NAME = "AppEngine"
    private const val USERNAME = "sa"
    private const val PASSWORD = "12345678"
    private const val URL = "jdbc:jtds:sqlserver://$IP:$PORT/$DATABASE_NAME;user=$USERNAME;password=$PASSWORD;encrypt=true;trustServerCertificate=true"

    init {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection {
        return DriverManager.getConnection(URL)
    }

    fun insertRule(rule: RuleEntity) {
        val conn = getConnection()
        var statement: PreparedStatement? = null
        try {
            statement = conn.prepareStatement("INSERT INTO rules (rule_string) VALUES (?)")
            statement.setString(1, rule.ruleString)
            statement.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            statement?.close()
            conn.close()
        }
    }

    fun getRuleById(ruleId: Int): RuleEntity? {
        val conn = getConnection()
        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null
        return try {
            statement = conn.prepareStatement("SELECT * FROM rules WHERE id = ?")
            statement.setInt(1, ruleId)
            resultSet = statement.executeQuery()

            if (resultSet.next()) {
                RuleEntity(resultSet.getInt("id"), resultSet.getString("rule_string"))
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            resultSet?.close()
            statement?.close()
            conn.close()
        }
    }
}
