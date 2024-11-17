package model

data class ListaPodataka (
    val kolone: List<Podaci> = emptyList(),
)

data class Podaci(
    val id : String,
    val sadrzaj : List<String>
)