package org.example.specifikacija

import java.sql.ResultSet
import java.sql.ResultSetMetaData

interface IzvestajInterface {

    abstract val implementacija: String
    abstract val formating: Boolean

    fun generisiIzvestaj(podaci: Map<String, List<String>>, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null)

    fun generisiIzvestaj(podaci: ResultSet, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null){
        val pripremljeniPodaci = pripremiPodatke(podaci)
        generisiIzvestaj(pripremljeniPodaci, destinacija, header, naslov, summary)
    }

    private fun pripremiPodatke(resultSet: ResultSet): Map<String, List<String>> {
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