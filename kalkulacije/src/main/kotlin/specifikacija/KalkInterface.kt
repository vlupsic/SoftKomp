package specifikacija

interface KalkInterface {

    fun zbir(novaKolona : String?, lista : List<List<Double?>>, koeficijent : Int?) : MutableMap<String,MutableList<String>>

    fun mnozenje(novaKolona : String?, lista : List<List<Double?>>, koeficijent : Int?): MutableMap<String,MutableList<String>>

    fun razlika(novaKolona : String?, kolona1 : List<Double?>, kolona2 : List<Double?>, koeficijent : Int?) : MutableMap<String,MutableList<String>>

    fun deljenje(novaKolona : String?, kolona1 : List<Double?>, kolona2 : List<Double?>, koeficijent : Int?) : MutableMap<String,MutableList<String>>

    fun sum(kolona : MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent : Int?) : String

    fun average(kolona : MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent : Int?) : String

    fun count(kolona: MutableList<String>?, uslov: String?, uslovValue: String?) : String
}