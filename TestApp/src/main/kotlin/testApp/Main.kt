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
   // println(exporterServices.values.toList())

    while(true) {
        serviceLoader.forEach { service ->
            exporterServices[service.implementacija] = service
        }

        println("Dostupni formati: " + exporterServices.keys)
        var format: String? = null
        while (format == null) {
            println("\n Izaberite format: ")
            format = readLine()?.trim()?.uppercase()
        }
        if(format == "EXIT"){
            println("Gasenje programa")
            break
        }

        //println("\n Izaberite format: ")
        //val format = readLine()?.trim()?.uppercase()

        println("SQL upit: ")
        val sql = readLine()


        var putanja: String? = null
        while (putanja == null) {
            println("\n Navedite putanju za izvoz: ")
            putanja = readLine()?.trim()?.uppercase()
        }

        var heder: Boolean? = null
        var unos: String? = null
        while (unos == null) {
            println("\n Da li zelite header (Y/N): ")
            unos = readLine()?.trim()?.uppercase()
            if (unos.equals("Y")){
                heder = true
            }else if (unos.equals("N")){
                heder = false
            }else {
                println("Unos nije validan, probajte opet <3")
                unos = null
            }
        }

        var naslov: String? = null
        while (naslov == null) {
            println("\n Naslov fajla: ")
            naslov = readlnOrNull()?.trim()
            println(naslov)
        }

        var kalkulacije: String? = null
        while (kalkulacije == null){
            println("\n Ukoliko imate kalkulacije, sada je vreme da ih prilozite:")
            kalkulacije = readLine()?.trim()?.uppercase()
        }

        var formatiranje: String? = null
        while (formatiranje == null){
            println("\n Ukoliko imate zelje za formatiranje, sada je vreme da ih prilozite:")
            formatiranje = readLine()?.trim()?.uppercase()
        }

        val query = connection?.prepareStatement(sql)
        val resultSet = query?.executeQuery()
        //C:\Users\ASUS Tuf Gaming\Desktop\SoftKomp\TestApp\src\main\resources\testKalk.txt
        //C:\Users\ASUS Tuf Gaming\Desktop\SoftKomp\TestApp\src\main\resources\supertest.txt
        //C:\Users\ASUS Tuf Gaming\Desktop\SoftKomp\TestApp\src\main\resources\testpdf.pdf
        //C:\Users\ASUS Tuf Gaming\Desktop\SoftKomp\TestApp\src\main\resources\testexel55.XLSX
        //C:\Users\ASUS Tuf Gaming\Desktop\SoftKomp\TestApp\src\main\resources\TESTSK.JSON
        //C:\Users\ASUS Tuf Gaming\Desktop\SoftKomp\TestApp\src\main\resources\testFormatPDF.json
        //C:\Users\ASUS Tuf Gaming\Desktop\SoftKomp\TestApp\src\main\resources\testFormatExcel.json
        val fajl = File(kalkulacije.toString())
        val formating = File(formatiranje.toString())
        println(formating)
        if (resultSet != null) {
            exporterServices[format]?.generisiIzvestaj(resultSet, putanja.toString(), heder?:false, naslov?:"", fajl, formating)
        }
    }
    if (connection != null) {
        connection.close()
    }
}