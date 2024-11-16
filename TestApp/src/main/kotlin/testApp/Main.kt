package testApp

import org.example.specifikacija.IzvestajInterface
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.ServiceLoader


fun main(){

    val jdbcURL = "jdbc:mysql://mysqlvasamisa.mysql.database.azure.com/softkomp?autoReconnect=true"
    println("Username za MySQL DB: ")
    var username = readLine()
    println("Lozinka: ")
    var password = readLine()
    var connection: Connection? = null
    try{
        connection = DriverManager.getConnection(jdbcURL, username, password)
//        println("SQL upit: ")
//        val sql = readLine()
//        val query = connection.prepareStatement(sql)
//        val resultSet = query.executeQuery()




    }catch (e: SQLException){
        e.printStackTrace()
        if (connection != null) {
            connection.close()
        }
    }catch (e: Exception){
        e.printStackTrace()
        if (connection != null) {
            connection.close()
        }
    }

    val serviceLoader = ServiceLoader.load(IzvestajInterface::class.java)

    val exporterServices = mutableMapOf<String, IzvestajInterface> ()
    println(exporterServices.values.toList())

    while(true) {
        serviceLoader.forEach { service ->
            exporterServices[service.implementacija] = service
        }

        println("Dostupni formati: " + exporterServices.keys)

        val format = readLine()?.trim()?.uppercase()
        if(format == "EXIT"){
            println("Gasenje programa")
            break
        }

        val izvestajInterface = exporterServices[format]

        println("SQL upit: ")
        val sql = readLine()
        val query = connection?.prepareStatement(sql)
        val resultSet = query?.executeQuery()

        var fajl = File("C:\\Users\\ASUS Tuf Gaming\\Desktop\\SoftKomp\\TestApp\\src\\main\\resources\\testSK.json")
        println(fajl)
        if (resultSet != null) {
            exporterServices["TXT"]?.generisiIzvestaj(resultSet, "C:\\Users\\ASUS Tuf Gaming\\Desktop\\SoftKomp\\TestApp\\src\\main\\resources\\testKalk.txt", false, "Test", null, fajl )
        }
    }
//header: Boolean, naslov: String? = null, summary: String? = null, fajl: File
    if (connection != null) {
        connection.close()
    }
}