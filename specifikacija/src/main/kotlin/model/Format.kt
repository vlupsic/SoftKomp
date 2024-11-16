package model


data class Format(
    val naslov: Title,
    val header: Header,
    val podaci: Data,
    val kolone : List<String>,
    val summary: Summary
)

data class Title(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val color: String,
    val alignment: String
)

data class Header(
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

data class Data(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val color: String,
    val alignment: String,
    val border: String,
    val borderColor: String,
)

data class Summary(
    val bold: Boolean,
    val italic: Boolean,
    val fontSize: Int,
    val color: String,
    val underline: Boolean,
)

