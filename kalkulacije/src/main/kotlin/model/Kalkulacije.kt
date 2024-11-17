package model

data class Kalkulacije (
    val kolone: List<KalkulacijeKolona> = emptyList(),
    val epilog: List<KalkulacijeEpilog> = emptyList()
)

data class KalkulacijeKolona(
    val naslov: String? = null,
    val operacija: String? = null,
    val koeficijent: String? = null,
    val sadrzaj: List<String>
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

