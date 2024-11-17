package model

data class ExcelFormat(
    val naslov: ExcelTitle,
    val header: ExcelHeader,
    val podaci: ExcelData,
    val kolone : List<String>,
    val summary: ExcelSummary
)

data class ExcelTitle(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
 //   val color: String,
    val alignment: String
)

data class ExcelHeader(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val underline: Boolean,
//    val color: Short,
    val alignment: String,
    val borderStyle: String,
//    val borderColor: String,
)

data class ExcelData(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
//    val color: String,
    val alignment: String,
    val borderStyle: String,
//    val borderColor: String,
)

data class ExcelSummary(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
//    val color: String,
    val underline: Byte,
)
