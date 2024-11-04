package txt

import org.example.specifikacija.IzvestajInterface

class TxtReportImplementacija(override val implementacija: String = "TXT", override val formating: Boolean = false) : IzvestajInterface {

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