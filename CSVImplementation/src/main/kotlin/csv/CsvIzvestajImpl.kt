package csv

import org.example.specifikacija.IzvestajInterface

class CsvIzvestajImpl() : IzvestajInterface {

    override val implementacija: String = "CSV"
    override val formating: Boolean = false

    override fun generisiIzvestaj(
        podaci: Map<String, List<String>>,
        destinacija: String,
        header: Boolean,
        naslov: String?,
        summary: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun generisiSummary(podaci: Map<String, List<String>>, destinacija: String, naslov: String?) {
        TODO("Not yet implemented")
    }

}
