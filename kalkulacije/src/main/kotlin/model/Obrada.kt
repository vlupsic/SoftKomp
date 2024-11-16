package model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

internal fun mrtviJson(podaci: MutableMap<String, MutableList<String>>, toParse: File): String{
    val mapper =  jacksonObjectMapper()
    val fajl = mapper.readValue<Kalkulacije>(toParse)
    val KalkInterface = KalkImpl();

    for (kalkulacijeKolone in fajl.kolone) {


        val naslovKolone = kalkulacijeKolone.naslov;
        val koloneZaObradu = kalkulacijeKolone.sadrzaj.mapNotNull { podaci[it]?.map {it.toDoubleOrNull()} }
        val koef = kalkulacijeKolone.koeficijent?.toIntOrNull();

        val novaKolona = when (kalkulacijeKolone.operacija){
            "saberi" -> KalkInterface.zbir(naslovKolone, koloneZaObradu, koef);
            "oduzmi" -> KalkInterface.razlika(naslovKolone, koloneZaObradu[0], koloneZaObradu[1], koef);
            "podeli" -> KalkInterface.deljenje(naslovKolone, koloneZaObradu[0], koloneZaObradu[1], koef);
            "pomnozi" -> KalkInterface.mnozenje(naslovKolone, koloneZaObradu, koef);
            else -> {null}
        }
        if (novaKolona != null) {
            podaci[novaKolona.keys.toString()] = novaKolona.values.map{ it.toString() }.toMutableList()
        }
    }
        val bilde = StringBuilder()

    for (kalkulacijeEpilog in fajl.epilog){

        val labela = kalkulacijeEpilog.naslov
        val operacija = kalkulacijeEpilog.operacija
        val kolona = podaci[kalkulacijeEpilog.kolona]
        val uslov = kalkulacijeEpilog.uslov
        val sadrzajUslova = kalkulacijeEpilog.uslovValue
        val unos = kalkulacijeEpilog.sadrzaj
        val koef = kalkulacijeEpilog.koeficijent

        val sadrzaj = when(operacija){
            "sumiraj" -> KalkInterface.sum(kolona, uslov, sadrzajUslova, koef?.toIntOrNull());
            "prosek" -> KalkInterface.average(kolona, uslov, sadrzajUslova, koef?.toIntOrNull());
            "prebroj" -> KalkInterface.count(kolona, uslov, sadrzajUslova);
            else -> {null}
        }

        bilde.append(labela + ": " + sadrzaj + " - " + unos + "\n")
    }
    return bilde.toString()


}