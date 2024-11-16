package excel

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
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("Report")

        naslov?.let {
            val titleRow : Row = sheet.createRow(0)
            val titleCell : Cell = titleRow.createCell(0)
            titleCell.setCellValue(it)
            sheet.addMergedRegion(CellRangeAddress(0,0,0, podaci.size - 1))
            val titleStyle = workbook.createCellStyle().apply {
                alignment = HorizontalAlignment.CENTER
                val titleFont = workbook.createFont().apply {
                    bold = true
                    fontHeightInPoints = 18
                }
                this.setFont(titleFont)
            }
            titleCell.cellStyle = titleStyle
        }
        if(header){
            val headerRow : Row = sheet.createRow(1)
            podaci.keys.forEachIndexed { index, columnName ->
                headerRow.createCell(index).setCellValue(columnName)
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
            summaryCell.setCellValue("Summary: $it")
        }
        FileOutputStream(destinacija).use { outputStream ->
            workbook.write(outputStream)
        }
        workbook.close()

    }

}