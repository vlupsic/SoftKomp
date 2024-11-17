package model

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.FileNotFoundException
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import kotlin.system.exitProcess

internal fun mrtviJson(podaci: MutableMap<String, MutableList<String>>, toParse: File): String{
    try {
        val mapper = jacksonObjectMapper()
        val fajl = mapper.readValue<Kalkulacije>(toParse)
        val KalkInterface = KalkImpl();

        for (kalkulacijeKolone in fajl.kolone) {


            val naslovKolone = kalkulacijeKolone.naslov;
            var koloneZaObradu: List<List<Double?>>? = null
            try {
                koloneZaObradu = kalkulacijeKolone.sadrzaj.mapNotNull { podaci[it]?.map { it.toDoubleOrNull() } }
            } catch (e: Exception) {
                println("Nema trazene kolone!")
                e.printStackTrace()
                exitProcess(1)
            }

            val koef = kalkulacijeKolone.koeficijent?.toIntOrNull();

            val novaKolona = when (kalkulacijeKolone.operacija) {
                "saberi" -> KalkInterface.zbir(naslovKolone, koloneZaObradu, koef);
                "oduzmi" -> KalkInterface.razlika(naslovKolone, koloneZaObradu[0], koloneZaObradu[1], koef);
                "podeli" -> KalkInterface.deljenje(naslovKolone, koloneZaObradu[0], koloneZaObradu[1], koef);
                "pomnozi" -> KalkInterface.mnozenje(naslovKolone, koloneZaObradu, koef);
                else -> {
                    null
                }
            }
            if (novaKolona != null) {
                podaci[naslovKolone.toString()] = novaKolona.map { it.toString() }.toMutableList()
            }
        }
        val bilde = StringBuilder()

        for (kalkulacijeEpilog in fajl.epilog) {

            val labela = kalkulacijeEpilog.naslov
            val operacija = kalkulacijeEpilog.operacija
            val kolona = podaci[kalkulacijeEpilog.kolona]
            val uslov = kalkulacijeEpilog.uslov
            val sadrzajUslova = kalkulacijeEpilog.uslovValue
            val unos = kalkulacijeEpilog.sadrzaj
            val koef = kalkulacijeEpilog.koeficijent

            val sadrzaj = when (operacija) {
                "sumiraj" -> KalkInterface.sum(kolona, uslov, sadrzajUslova, koef?.toIntOrNull());
                "prosek" -> KalkInterface.average(kolona, uslov, sadrzajUslova, koef?.toIntOrNull());
                "prebroj" -> KalkInterface.count(kolona, uslov, sadrzajUslova);
                else -> {
                    null
                }
            }

            bilde.append(labela + ": " + sadrzaj + " - " + unos + "\n")
        }
        return bilde.toString()
    }catch (e : FileNotFoundException){
        println("Nema fajl")
        return "";
    }

}

internal fun pripremiPodatke(resultSet: ResultSet): MutableMap<String, MutableList<String>> {
    val podaci = mutableMapOf<String, MutableList<String>>()

    val metaPodaci: ResultSetMetaData = resultSet.metaData
    val brojKolona = metaPodaci.columnCount

    for (i in 1..brojKolona){
        val imeKolone = metaPodaci.getColumnName(i)
        podaci[imeKolone] = mutableListOf()
    }



    while (resultSet.next()){
        for (i in 1..brojKolona){
            val imeKolone = metaPodaci.getColumnName(i)
            podaci[imeKolone]!!.add(resultSet.getString(i))
        }
    }

    return podaci
}

internal fun pripremiPodatke(listaListi: List<List<String>>): MutableMap<String, MutableList<String>> {
    val podaci = mutableMapOf<String, MutableList<String>>()

    for (i in listaListi.indices){
        val privremenaLista = mutableListOf<String>()
        for (j in listaListi[i].indices){
            if (j==0) continue
            privremenaLista.add(listaListi[i][j])
        }
        podaci[listaListi[i][0]] = privremenaLista
    }
    return podaci
}

internal fun pripremiPodatke(fajl: File): MutableMap<String, MutableList<String>> {
    val podaci = mutableMapOf<String, MutableList<String>>()
    val mapper = jacksonObjectMapper()
    val podacici = mapper.readValue<ListaPodataka>(fajl)

    for (i in podacici.kolone){
        for (j in i.sadrzaj){
            podaci[i.id]?.add(j)
        }
    }

    return podaci
}

