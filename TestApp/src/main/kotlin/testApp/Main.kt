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

    println("ovo su servisi: " + exporterServices.keys)
}