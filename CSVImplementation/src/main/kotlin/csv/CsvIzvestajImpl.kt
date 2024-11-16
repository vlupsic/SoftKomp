package csv

import org.example.specifikacija.IzvestajInterface
import java.io.File

class CsvIzvestajImpl : IzvestajInterface {

    override val implementacija: String = "CSV"
    override val formating: Boolean = false

    override fun generisiIzvestaj(
        podaci: MutableMap<String, MutableList<String>>,
        destinacija: String,
        header: Boolean,
        naslov: String?,
        summary: String?,
        fajl: File
    ) {
        val kolone = podaci.keys.toList()
        val vrste = podaci.values.first().size

        File(destinacija).printWriter().use{ writer ->
            if (header)
                writer.println(kolone.joinToString(","))
            for(i in 0 until vrste) {
                val vrsta = kolone.map{
                    kolona -> podaci[kolona]?.get(i) ?: ""
                }
                writer.println(vrsta.joinToString(","))
            }
        }
    }


}
