package pdf

import org.example.specifikacija.IzvestajInterface

class PdfIzvestajImpl() : IzvestajInterface{

    override val implementacija: String = "PDF"
    override val formating: Boolean = true

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