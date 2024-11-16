package pdf

import com.google.gson.Gson
import com.itextpdf.html2pdf.HtmlConverter
import model.Format
import org.example.specifikacija.IzvestajInterface
import java.io.File
import java.io.FileOutputStream

class PdfIzvestajImpl() : IzvestajInterface {

    override val implementacija: String = "PDF"
    override val formating: Boolean = true

    override fun generisiIzvestaj(
        podaci: MutableMap<String, MutableList<String>>,
        destinacija: String,
        header: Boolean,
        naslov: String?,
        summary: String?,
        fajl: File
    ) {
        try {
            val format = formatConf(fajl)
            val pdf = html(podaci, header, naslov, summary, format)
            val pdfOutput = FileOutputStream(destinacija)
            HtmlConverter.convertToPdf(pdf, pdfOutput)
        }catch (e: Exception) {
            e.printStackTrace()
            println("Greska pri generisanju PDF-a: ${e.message}")
        }

    }

    private fun formatConf(conf: File): Format? {
        return try {
            val gson = Gson()

            conf.reader().use { reader ->
                gson.fromJson(reader, Format::class.java)
            }
        }catch (e:Exception){
            println("Error loading config file: ${e.message}")
            null
        }
    }

    private fun html(
        podaci: MutableMap<String, MutableList<String>>,
        header: Boolean,
        naslov: String?,
        summary: String?,
        format: Format?
    ) : String {
        val builder = StringBuilder()
        val tableStyle = """
        @page {
            size: A4 landscape;
            margin: 20mm;
        }
        table {
            width: 90%; 
            border-collapse: collapse;
            table-layout: fixed; 
            margin: auto; 
        }
        th, td {
            padding: 6px;  
            border: 1px solid;
            word-wrap: break-word; 
            font-size: 12px; 
        }
    """
        val titleStyle = format?.naslov?.let {
            "font-size:${it.fontSize}px; color:${it.color}; text-align:${it.alignment}; font-weight:${if(it.bold == true){ "bold"} else if(it.italic == true){ "italic" } else " normal"}"
        } ?: "font-size: 18px; color: black;"



        builder.append("<html><head><style>")
        builder.append(tableStyle)
        builder.append("</style></head><body>")

        naslov?.let {
            builder.append("<h1 style='$titleStyle'>$it</h1>")
        }
        builder.append("<table>")
        val kolone = format?.kolone ?: podaci.keys.toList()
        if (header) {
            val headerStyle = format?.header?.let {
                "font-size:${it.fontSize}px; color:${it.color}; text-align:${it.alignment};"+
                        "font-weight:${if(it.bold == true){ "bold"} else if(it.italic == true){ "italic" } else " normal"};" +
                        "text-decoration:${if(it.underline == true) "underline" else " none"};" +
                        "border:${it.border}; border-color:${it.borderColor ?: "black"};"
            } ?: ""
            builder.append("<tr>")
            podaci.keys.forEach { kolona ->
                if(kolone.contains(kolona)) {
                    builder.append("<th style='$headerStyle'>$kolona</th>")
                }else{
                    builder.append("<th>$kolona</th>")
                }
            }
            builder.append("</tr>")
        }
        builder.append("</table>")

        summary?.let {
            val summaryStyle = format?.summary?.let {format ->
                "font-size:${format.fontSize}px; color:${format.color}; font-weight${if(format.bold == true){ "bold"} else if(format.italic == true){ "italic" } else " normal"};" +
                        "text-decoration:${if (format.underline == true) "underline" else " none"};"
            } ?: "font-size: 18px; color: black;"

            builder.append("<p style='$summaryStyle'>Summary</p>")

            val formatSummary = it.replace("\n", "<br>")
            builder.append("<p style='$summaryStyle'>$formatSummary</p>")
        }
        builder.append("</body></html>")
        return builder.toString()
    }


}