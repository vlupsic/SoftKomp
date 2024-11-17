package model


data class PdfFormat(
    val naslov: PdfTitle,
    val header: PdfHeader,
    val podaci: PdfData,
    val kolone : List<String>,
    val summary: PdfSummary
)

data class PdfTitle(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val color: String,
    val alignment: String
)

data class PdfHeader(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val underline: Boolean,
    val color: String,
    val alignment: String,
    val border: String,
    val borderColor: String,
    val borderWidth: String
)

data class PdfData(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val color: String,
    val alignment: String,
    val border: String,
    val borderColor: String,
)

data class PdfSummary(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val color: String,
    val underline: Boolean,
)

