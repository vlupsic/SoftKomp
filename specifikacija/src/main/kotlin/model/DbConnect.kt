package model


import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

class DbConnect {

    fun connect(){
        val jdbcURL = "mysqlvasamisa.mysql.database.azure.com"
        val connectionProps = Properties()
        println("Username za MySQL DB: ")
        var username = readLine()
        println("Lozinka: ")
        var password = readLine()
        connectionProps.put("user", username)
        connectionProps.put("password", password)

        try{
            val connection = DriverManager.getConnection(jdbcURL, connectionProps)
            println("SQL upit: ")
            val sql = readLine()
            val query = connection.prepareStatement(sql)
            val result = query.executeQuery()
            val pesme = mutableListOf<Pesma>()
            while(result.next()){
                val id = result.getInt("idpesme")
                val name = result.getString("naziv")
                val autor = result.getString("autor")
                val date = result.getDate("datum")
                val album = result.getString("album")
                val ocena = result.getInt("ocena")
                val streams = result.getInt("streams")
                val price = result.getInt("cena")
                pesme.add(Pesma(id, name, autor, date, album, ocena, streams, price))
                print(pesme)
            }


        }catch (e: SQLException){
            e.printStackTrace()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }



}