package org.example.specifikacija

import java.io.File
import java.sql.ResultSet
import java.sql.ResultSetMetaData

interface IzvestajInterface {

    abstract val implementacija: String
    abstract val formating: Boolean

    fun generisiIzvestaj(podaci: MutableMap<String, MutableList<String>>, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null, fajl: File)

    fun generisiIzvestaj(podaci: ResultSet, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null, fajl: File){
        val pripremljeniPodaci = pripremiPodatke(podaci)
        generisiIzvestaj(pripremljeniPodaci, destinacija, header, naslov, summary, fajl)
    }

    private fun pripremiPodatke(resultSet: ResultSet): MutableMap<String, MutableList<String>> {
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



}