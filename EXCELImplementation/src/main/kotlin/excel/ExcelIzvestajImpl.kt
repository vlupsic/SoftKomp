package excel

import com.google.gson.Gson
import model.ExcelFormat
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.example.specifikacija.IzvestajInterface
import java.awt.Color
import java.io.File
import java.io.FileOutputStream
import kotlin.system.exitProcess

class ExcelIzvestajImpl() : IzvestajInterface {

    override val implementacija: String = "EXCEL"
    override val formating: Boolean = true

    override fun generisiIzvestaj(
        podaci: MutableMap<String, MutableList<String>>,
        destinacija: String,
        header: Boolean,
        naslov: String?,
        summary: String?,
        formatiranje: File?
    ) {
        try {
            val format = formatConf(formatiranje?: File(""))
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
            "medium_dashed" -> BorderStyle.MEDIUM_DASHED
            "dashed" -> BorderStyle.DASHED
            "double" -> BorderStyle.DOUBLE
            "medium" -> BorderStyle.MEDIUM
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
        try {
            val workbook: Workbook = XSSFWorkbook()
            val sheet: Sheet = workbook.createSheet("Report")

            naslov?.let {
                val titleRow: Row = sheet.createRow(0)
                val titleCell: Cell = titleRow.createCell(0)
                titleCell.setCellValue(it)
                sheet.addMergedRegion(CellRangeAddress(0, 0, 0, podaci.size - 1))
                val titleStyle = workbook.createCellStyle().apply {
                    if (format != null) {
                        alignment = stringToAlignment(format.naslov.alignment)
                    }
                    val titleFont = workbook.createFont().apply {
                        if (format != null) {
                            bold = format.naslov.bold
                            italic = format.naslov.italic
                            fontHeightInPoints = format.naslov.fontSize.toShort()
                            wrapText = true
                           /*// color = format.naslov.color.toShort()
                            if (this is XSSFFont && workbook is XSSFWorkbook) {
                                val color = Color.decode(format.naslov.color)
                                this.setColor(
                                    XSSFColor(
                                        byteArrayOf(color.red.toByte(), color.green.toByte(), color.blue.toByte()),
                                        workbook.stylesSource.indexedColors
                                    )
                                )
                            }*/
                        }
                    }
                    this.setFont(titleFont)
                }
                titleCell.cellStyle = titleStyle
            }
            if (header) {
                val headerRow: Row = sheet.createRow(1)
                podaci.keys.forEachIndexed { index, columnName ->

                    val headerStyle = workbook.createCellStyle().apply {
                        val redFont = workbook.createFont().apply {
                            if (format != null) {
                                bold = format.header.bold
                                italic = format.header.italic
                                underline = if (format.header.underline) Font.U_SINGLE else Font.U_NONE

                                /*//color = format.header.color.toShort()
                                if (this is XSSFFont && workbook is XSSFWorkbook) {
                                    val color = Color.decode(format.header.color.toString())
                                    this.setColor(
                                        XSSFColor(
                                            byteArrayOf(color.red.toByte(), color.green.toByte(), color.blue.toByte()),
                                            workbook.stylesSource.indexedColors
                                        )
                                    )
                                }*/
                                borderTop = stringToBorderStyle(format.header.borderStyle)
                                borderRight = stringToBorderStyle(format.header.borderStyle)
                                borderLeft = stringToBorderStyle(format.header.borderStyle)
                                borderBottom = stringToBorderStyle(format.header.borderStyle)
                             /*   topBorderColor = format.header.borderColor.toShort()
                                leftBorderColor = format.header.borderColor.toShort()
                                rightBorderColor = format.header.borderColor.toShort()
                                bottomBorderColor = format.header.borderColor.toShort()*/
                                alignment = stringToAlignment(format.header.alignment)
                                fontHeight = format.header.fontSize.toShort()
                                wrapText = true
                            }
                        }
                        this.setFont(redFont)
                    }
                    val cell = headerRow.createCell(index)
                    cell.setCellValue(columnName)
                    cell.cellStyle = headerStyle
                    if (sheet.getColumnWidth(index) < columnName.length * 260)
                        sheet.setColumnWidth(index, columnName.length)
                }

            }
            val numRows = podaci.values.first().size
            for (i in 0 until numRows) {
                val dataRow: Row = sheet.createRow(if (header) i + 2 else i + 1)

                podaci.keys.forEachIndexed { index, columnName ->
                    val cell = dataRow.createCell(index)
                    cell.setCellValue(podaci[columnName]?.get(i) ?: "")
                    val stil = workbook.createCellStyle().apply {
                        val redFont = workbook.createFont().apply {
                            if (format != null) {
                                bold = format.podaci.bold
                                italic = format.podaci.italic

                                /*//color = format.podaci.color.toShort()
                                if (this is XSSFFont && workbook is XSSFWorkbook) {
                                    val color = Color.decode(format.podaci.color.toString())
                                    this.setColor(
                                        XSSFColor(
                                            byteArrayOf(color.red.toByte(), color.green.toByte(), color.blue.toByte()),
                                            workbook.stylesSource.indexedColors
                                        )
                                    )
                                }*/
                                alignment = stringToAlignment(format.podaci.alignment)
                                borderTop = stringToBorderStyle(format.podaci.borderStyle)
                                borderRight = stringToBorderStyle(format.podaci.borderStyle)
                                borderLeft = stringToBorderStyle(format.podaci.borderStyle)
                                borderBottom = stringToBorderStyle(format.podaci.borderStyle)
                               /* topBorderColor = format.podaci.borderColor.toShort()
                                leftBorderColor = format.podaci.borderColor.toShort()
                                rightBorderColor = format.podaci.borderColor.toShort()
                                bottomBorderColor = format.podaci.borderColor.toShort()*/
                                fontHeight = format.podaci.fontSize.toShort()
                            }
                        }
                        this.setFont(redFont)
                    }
                    if (sheet.getColumnWidth(index) < (podaci[columnName]?.get(i)?.length ?: 20) * 260)
                        sheet.setColumnWidth(index, (podaci[columnName]?.get(i)?.length ?: 20) * 260)
                    cell.cellStyle = stil
                }
            }
            summary?.let {
                val summaryRow: Row = sheet.createRow(numRows + 2)
                val summaryCell: Cell = summaryRow.createCell(0)
                summaryCell.setCellValue("Summary: $summary")
                sheet.setColumnWidth(0, summary.length * 100)
                val summaryStyle = workbook.createCellStyle().apply {
                    val sumFont = workbook.createFont().apply {
                        if (format != null) {
                            bold = format.summary.bold
                            italic = format.summary.italic
                            fontHeight = format.summary.fontSize.toShort()
                            /*//color = format.summary.color.toShort()
                            if (this is XSSFFont && workbook is XSSFWorkbook) {
                                val color = Color.decode(format.summary.color.toString())
                                this.setColor(
                                    XSSFColor(
                                        byteArrayOf(color.red.toByte(), color.green.toByte(), color.blue.toByte()),
                                        workbook.stylesSource.indexedColors
                                    )
                                )
                            }*/
                            underline = format.summary.underline
                            wrapText = true
                        }
                    }
                    this.setFont(sumFont)
                }
                summaryCell.cellStyle = summaryStyle
            }



            return workbook
        }catch (e : Exception){
            println("Error while exporting Excel Report: " + e.message)
            exitProcess(1)
        }
    }

}