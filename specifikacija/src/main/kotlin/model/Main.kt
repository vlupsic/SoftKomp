package model

import java.sql.DriverManager
import java.sql.SQLException


internal fun connect() /*: MutableMap<String , MutableList<String>>*/ {
    val jdbcURL = "jdbc:mysql://mysqlvasamisa.mysql.database.azure.com/softkomp?autoReconnect=true"
    println("Username za MySQL DB: ")
    var username = readLine()
    println("Lozinka: ")
    var password = readLine()

    try{
        val connection = DriverManager.getConnection(jdbcURL, username, password)
        println("SQL upit: ")
        val sql = readLine()
        val query = connection.prepareStatement(sql)
        val resultSet = query.executeQuery()


        connection.close()
    }catch (e: SQLException){
        e.printStackTrace()
    }catch (e: Exception){
        e.printStackTrace()
    }
}

fun main() {
    connect()
}