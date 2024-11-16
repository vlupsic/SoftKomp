package model

data class Kalkulacije (
    val epilog: List<KalkulacijeEpilog> = emptyList(),
    val kolone: List<KalkulacijeKolona> = emptyList(),
)

data class KalkulacijeEpilog(
    val naslov: String? = null,
    val operacija: String? = null,
    val kolona: String? = null,
    val uslov: String? = null,
    val uslovValue: String? = null,
    val sadrzaj: String? = null,
    val koeficijent: String? = null,
)

data class KalkulacijeKolona(
    val naslov: String?,
    val operacija: String?,
    val koeficijent: String? = null,
    val sadrzaj: List<String>
)