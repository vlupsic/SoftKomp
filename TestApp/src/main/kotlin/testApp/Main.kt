package testApp

import org.example.specifikacija.IzvestajInterface
import java.util.ServiceLoader

fun main(){
    val serviceLoader = ServiceLoader.load(IzvestajInterface::class.java)

    val exporterServices = mutableMapOf<String, IzvestajInterface> ()


    serviceLoader.forEach{
            service ->
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

    }

    if (connection != null) {
        connection.close()
    }
}