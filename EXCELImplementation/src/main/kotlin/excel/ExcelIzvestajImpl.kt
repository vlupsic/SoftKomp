package excel

import com.google.gson.Gson
import model.ExcelFormat
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.example.specifikacija.IzvestajInterface
import java.io.File
import java.io.FileOutputStream

class ExcelIzvestajImpl() : IzvestajInterface {

    override val implementacija: String = "EXCEL"
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
            val excel = excel(podaci, header, naslov, summary, format)
            FileOutputStream(destinacija).use { outputStream ->
            excel.write(outputStream)
           }
            excel.close()
        }catch (e: Exception) {
            e.printStackTrace()
            println("Greska pri generisanju EXCEL-a: ${e.message}")
        }
    }

    private fun formatConf(conf: File): ExcelFormat? {
        return try {
            val gson = Gson()

            conf.reader().use { reader ->
                gson.fromJson(reader, ExcelFormat::class.java)
            }
        }catch (e:Exception){
            println("Error loading config file: ${e.message}")
            null
        }
    }

    private fun stringToAlignment(alignment: String): HorizontalAlignment {
        return when (alignment.lowercase()) {
            "center" -> HorizontalAlignment.CENTER
            "left" -> HorizontalAlignment.LEFT
            "right" -> HorizontalAlignment.RIGHT
            "justify" -> HorizontalAlignment.JUSTIFY
            else -> HorizontalAlignment.GENERAL
        }
    }

    private fun applyStyleToRow(row: Row, style: CellStyle, numColumns: Int) {
        for (i in 0 until numColumns) {
            val cell = row.getCell(i)
            cell.cellStyle = style
        }
    }

    fun stringToBorderStyle(style: String): BorderStyle {
        return when (style.lowercase()) {
            "thin" -> BorderStyle.THIN
            "thick" -> BorderStyle.THICK
            "dotted" -> BorderStyle.DOTTED
            "dashed" -> BorderStyle.DASHED
            "double" -> BorderStyle.DOUBLE
            "medium" -> BorderStyle.MEDIUM
            "medium_dashed" -> BorderStyle.MEDIUM_DASHED
            "none" -> BorderStyle.NONE
            else -> BorderStyle.NONE
        }
    }

    private fun excel(
        podaci: MutableMap<String, MutableList<String>>,
        header: Boolean,
        naslov: String?,
        summary: String?,
        format: ExcelFormat?
    ) :Workbook{
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("Report")

        naslov?.let {
            val titleRow : Row = sheet.createRow(0)
            val titleCell : Cell = titleRow.createCell(0)
            titleCell.setCellValue(it)
            sheet.addMergedRegion(CellRangeAddress(0,0,0, podaci.size - 1))
            val titleStyle = workbook.createCellStyle().apply {
                if (format != null) {
                    alignment = stringToAlignment(format.naslov.alignment)
                }
                val titleFont = workbook.createFont().apply {
                    if (format != null) {
                        bold = format.naslov.bold
                        italic = format.naslov.italic
                        fontHeightInPoints = format.naslov.fontSize.toShort()
                        color = format.naslov.color.toShort()
                    }
                }
                this.setFont(titleFont)
            }
            titleCell.cellStyle = titleStyle
        }
        if(header){
            val headerRow : Row = sheet.createRow(1)
            podaci.keys.forEachIndexed { index, columnName ->
                headerRow.createCell(index).setCellValue(columnName)
                headerRow.rowStyle = workbook.createCellStyle().apply {
                    val redFont = workbook.createFont().apply {
                        if (format != null) {
                            bold = format.header.bold
                            italic = format.header.italic
                            underline = if (format.header.underline) Font.U_SINGLE else Font.U_NONE
                            fontHeight = format.header.fontSize.toShort()
                            color = format.header.color.toShort()
                            borderTop = stringToBorderStyle(format.header.borderStyle)
                            borderRight = stringToBorderStyle(format.header.borderStyle)
                            borderLeft = stringToBorderStyle(format.header.borderStyle)
                            borderBottom = stringToBorderStyle(format.header.borderStyle)
                            topBorderColor = format.header.borderColor.toShort()
                            leftBorderColor = format.header.borderColor.toShort()
                            rightBorderColor = format.header.borderColor.toShort()
                            bottomBorderColor = format.header.borderColor.toShort()
                            alignment = stringToAlignment(format.header.alignment)
                        }
                    }
                    this.setFont(redFont)
                }
            }

        }
        val numRows = podaci.values.first().size
        for (i in 1..numRows){
            val dataRow : Row = sheet.createRow(if (header) i + 2 else i + 1)
            podaci.keys.forEachIndexed { index, columnName ->
                dataRow.createCell(index).setCellValue(podaci[columnName]?.get(i) ?: "")
            }
        }
        summary?.let {
            val summaryRow : Row = sheet.createRow(numRows + 2)
            val summaryCell : Cell = summaryRow.createCell(0)
            summaryCell.setCellValue("Summary: $summary")
        }
        return workbook
    }

}