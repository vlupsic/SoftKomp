package org.example.specifikacija

import model.mrtviJson
import model.pripremiPodatke
import java.io.File
import java.sql.ResultSet
import java.sql.ResultSetMetaData

interface IzvestajInterface {

    abstract val implementacija: String
    abstract val formating: Boolean

    /**
     * Generise izvestaj na osnovu proslednjenih podataka, sa mogucnoscu da bude ukrasen i da sadrzi rezime.
     *
     * @param podaci Mapa sa kljucevima tipa String i vrednostima tipa List<String>.
     *               Pogodna za rad sa podacima.
     * @param destinacija Adresa tipa String na koju ce fajl biti izvezen.
     *                    Koristiti Absolute Path za najlaksi rad.
     * @param header Polje tipa Boolean koje definise da li ce krajnji proizvod imati header odeljak.
     * @param naslov Opciono polje tipa String za davanje naslova izvestaju.
     *               Rezervisano za implementacije sa formatiranjem.
     * @param summary Opciono polje tipa String koje prima rezime za ispis sa krajnjim proizvodom.
     *                Rezervisano za implementacije sa formatiranjem.
     * @param formatiranje Opcioni File koji sadrzi JSON parametre na osnovu kojih se vrsi formatiranje.
     *
     */
    fun generisiIzvestaj(podaci: MutableMap<String, MutableList<String>>, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null, formatiranje: File? = null)

    /**
     * Alternativna verzija metode koja za ulaz prima ResultSet koji ce biti prebacen u Mapu osnovne implementacije.
     *
     * @param podaci ResultSet koji ce biti prebacen u MutableMap<String, MutableList<String>>
     *
     */
    fun generisiIzvestaj(podaci: ResultSet, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null, formatiranje: File? = null){
        val pripremljeniPodaci = pripremiPodatke(podaci)
        generisiIzvestaj(pripremljeniPodaci, destinacija, header, naslov, summary, formatiranje)
    }

    /**
     * Alternativna verzija metode koja za ulaz prima JSON File koji ce biti prebacen u Mapu osnovne implementacije.
     *
     * @param podaci JSON File koji ce biti parsiran u MutableMap<String, MutableList<String>>
     *
     */
    fun generisiIzvestaj(podaci: File, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null, formatiranje: File? = null){
        val pripremljeniPodaci = pripremiPodatke(podaci)
        generisiIzvestaj(pripremljeniPodaci, destinacija, header, naslov, summary, formatiranje)
    }

    /**
     * Alternativna verzija metode koja za ulaz prima List<List<String>> koji ce biti prebacen u Mapu osnovne implementacije.
     *
     * @param podaci Lista listi koja ce biti prebacena u MutableMap<String, MutableList<String>>
     *
     */
    fun generisiIzvestaj(podaci: List<List<String>>, destinacija: String, header: Boolean, naslov: String? = null, summary: String? = null, formatiranje: File? = null){
        val pripremljeniPodaci = pripremiPodatke(podaci)
        generisiIzvestaj(pripremljeniPodaci, destinacija, header, naslov, summary, formatiranje)
    }

    /**
     * Alternativna verzija metode koja za ulaz prima ResultSet i File koji obradjuje kalkulacije koji ce biti prebacen u Mapu osnovne implementacije.
     *
     * @param podaci ResultSet koji ce biti prebacen u MutableMap<String, MutableList<String>>.
     *
     * @param fajl JSON File koji sadrzi parametre za obradu kalkulacija.
     *
     */
    fun generisiIzvestaj(podaci: ResultSet, destinacija: String, header: Boolean, naslov: String? = null, fajl: File, formatiranje: File? = null){
        val tabela = pripremiPodatke(podaci)
        var summary = ""
        summary = mrtviJson(tabela, fajl)
        generisiIzvestaj(tabela, destinacija, header, naslov, summary, formatiranje)
    }






}