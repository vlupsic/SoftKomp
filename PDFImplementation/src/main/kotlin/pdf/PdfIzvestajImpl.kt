package pdf

import com.lowagie.text.*
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import org.example.specifikacija.IzvestajInterface
import java.io.FileOutputStream

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
        val document = Document()
        PdfWriter.getInstance(document, FileOutputStream(destinacija))
        document.open()
        val headerFont = Font(Font.HELVETICA, 12f, Font.BOLD)
        val bodyFont = Font(Font.HELVETICA, 10f, Font.NORMAL)
        if (naslov != null) {
            val title = Paragraph(naslov, headerFont)
            title.alignment = Element.ALIGN_CENTER
            document.add(title)
            document.add(Chunk.NEWLINE)
        }

        val table = PdfPTable(podaci.size)
        table.widthPercentage = 100f
        if(header){
            for(headerColumn in podaci.keys){
                val headerCell = PdfPCell(Phrase(headerColumn,headerFont))
                headerCell.horizontalAlignment = Element.ALIGN_CENTER
                table.addCell(headerCell)
            }
        }
        val rowCnt = podaci.values.first().size
        for(i in 0 until rowCnt){
            for(kolonaPodaci in podaci.values){
                val cell = PdfPCell(Phrase(kolonaPodaci[i],bodyFont))
                table.addCell(cell)
            }
        }

        document.add(table)
        if(summary != null){
            document.add(Chunk.NEWLINE)
            val summaryParagraph = Paragraph(summary, bodyFont)
            summaryParagraph.alignment = Element.ALIGN_LEFT
            document.add(summaryParagraph)
        }

        document.close()
    }
}