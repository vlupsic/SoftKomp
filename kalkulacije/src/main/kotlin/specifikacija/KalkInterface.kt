package specifikacija

interface KalkInterface {

    fun zbir(novaKolona : String, lista : MutableList<String>) : String{
        return ""
    }

    fun mnozenje(novaKolona : String, lista : MutableList<String>): String{
        return ""
    }

    fun razlika(novaKolona : String, kolona1 : List<String>, kolona2 : List<String>) : String{
        return ""
    }

    fun deljenje(novaKolona : String, kolona1 : List<String>, kolona2 : List<String>) : String{
        return ""
    }

    fun sum(kolona : List<String>) : Float {
        return 0f
    }

    fun average(kolona : List<String>) : Float {
        return 0f
    }

    fun count(kolona: List<String>) : Int {
        return 0
    }
}