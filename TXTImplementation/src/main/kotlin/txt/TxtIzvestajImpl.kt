package txt

import org.example.specifikacija.IzvestajInterface
import java.io.File


class TxtIzvestajImpl() : IzvestajInterface {

    override val implementacija: String = "TXT"
    override val formating: Boolean = false

    override fun generisiIzvestaj(
        podaci: Map<String, List<String>>,
        destinacija: String,
        header: Boolean,
        naslov: String?,
        summary: String?
    ) {
        val kolone = podaci.keys.toList()
        val redovi = podaci.values.first().size

        val sirineKolona = kolone.map{ kolona->
            val maxSirina = podaci[kolona]?.maxOfOrNull { it.length } ?: 0
            maxOf(kolona.length, maxSirina)
        }

        File(destinacija).printWriter().use { writer ->
            naslov?.let{
                writer.println(it)
                writer.println()
            }

            kolone.forEachIndexed { indeks, kolona ->
                writer.print(kolona.padEnd(sirineKolona[indeks] + 2))
            }
            writer.println()

            sirineKolona.forEach{ sirina ->
                writer.print("-".repeat(sirina+2))
            }
            writer.println()

            for (i in 0 until redovi){
                kolone.forEachIndexed { indeks, kolona ->
                    val celija = podaci[kolona]?.get(i) ?: ""
                    writer.print(celija.padEnd(sirineKolona[indeks] + 2))
                }
                writer.println()
            }

            summary?.let {
                writer.println()
                writer.println(it)
            }

        }



    }

}