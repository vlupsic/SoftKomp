package model

import specifikacija.KalkInterface

class KalkImpl : KalkInterface {
    override fun zbir(
        novaKolona: String?,
        lista: List<List<Double?>>,
        koeficijent : Int?
    ): MutableMap<String, MutableList<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        if(novaKolona != null) {result[novaKolona] = mutableListOf()}


            for(row in lista) {
                var redSum = 0.0
                for(col in row) {
                    if (col != null) {
                        redSum += col
                    }
                    if (koeficijent != null) {
                        redSum += koeficijent
                    }
                }
                result[novaKolona]?.add(redSum.toString())
            }

        return result
    }

    override fun mnozenje(
        novaKolona: String?,
        lista : List<List<Double?>>,
        koeficijent : Int?
    ): MutableMap<String, MutableList<String>> {
        val result = mutableMapOf<String, MutableList<String>>()

        if (novaKolona != null){
            result[novaKolona] = mutableListOf()
        }


        for(row in lista) {
            var redSum = 0.0
            for(col in row) {
                if (col != null) redSum *= col
                if (koeficijent != null) redSum*=koeficijent
            }
            result[novaKolona]?.add(redSum.toString())
        }
        return result
    }

    override fun razlika(novaKolona : String?,
                         kolona1 : List<Double?>,
                         kolona2 : List<Double?>,
                         koeficijent : Int?
    ): MutableMap<String, MutableList<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        if (novaKolona != null){
            result[novaKolona] = mutableListOf()
        }

        val brRed = minOf(kolona1.size, kolona2.size)

        for (i in 0 until brRed) {
            val br1 = kolona1[i]
            val br2 = kolona2[i]
            var razlika = 0
            if (br1 != null && br2 != null) razlika = (br1 - br2).toInt()

            if (koeficijent != null) razlika -= koeficijent

            result[novaKolona]?.add(razlika.toString())
        }
        return result
    }

    override fun deljenje(
        novaKolona : String?,
        kolona1 : List<Double?>,
        kolona2 : List<Double?>,
        koeficijent : Int?
    ): MutableMap<String, MutableList<String>> {
        val result = mutableMapOf<String, MutableList<String>>()

        if (novaKolona != null){
            result[novaKolona] = mutableListOf()
        }

        val brRed = minOf(kolona1.size, kolona2.size)

        for (i in 0 until brRed) {
            val br1 = kolona1[i]
            val br2 = kolona2[i]
            var deljenje = 0
            if(br1 != null && br2 != null) deljenje = (br1 / br2).toInt()
            if (koeficijent != null) deljenje /= koeficijent

            result[novaKolona]?.add(deljenje.toString())
        }
        return result
    }

    override fun sum(kolona : MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent : Int?): String {
        var suma = 0.0f
        println("Usao")
        println("Kolona" + kolona.toString())
        println("Uslov: " + uslov)
        println("Value: " + uslovValue + "\nKoef: " + koeficijent + "\n ")

        val condition = uslovValue?.toIntOrNull()
        if (uslovValue != null && condition != null) {
            if (uslov.equals(Uslov.VECE.znak)){
                println("VECCEE")
                if (kolona != null)
                    for(value in kolona){
                        val provera = value.toFloatOrNull()
                        if (provera!=null && provera > condition)
                            suma += value.toFloatOrNull()?: 0.0f
                    }
            }else if (uslov.equals(Uslov.MANJE.znak)){
                if (kolona != null)
                    for(value in kolona){
                        val provera = value.toFloatOrNull()
                        if (provera!=null && provera < condition)
                            suma += value.toFloatOrNull()?: 0.0f
                    }
            }else if (uslov.equals(Uslov.JEDNAKO.znak)){
                if (kolona != null)
                    for(value in kolona){
                        val provera = value.toFloatOrNull()
                        if (provera!=null && provera.toInt() == condition.toInt())
                            suma += value.toFloatOrNull()?: 0.0f
                    }
            }
        }else{
            if (kolona != null)
            for(value in kolona){
                suma += value.toFloatOrNull()?: 0.0f
            }

        }

        if (koeficijent != null) suma += koeficijent

        println(suma)
        return suma.toString()

    }

    override fun average(kolona: MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent: Int?): String {
        var suma = 0.0f

        val condition = uslovValue?.toIntOrNull()

        if (uslovValue != null && condition != null) {
            if (uslov.equals(Uslov.VECE.znak)) {
                if (kolona != null)
                    for (value in kolona) {
                        val provera = value.toFloatOrNull()
                        if (provera != null && provera > condition)
                            suma += value.toFloatOrNull() ?: 0.0f
                    }
            } else if (uslov.equals(Uslov.MANJE.znak)) {
                if (kolona != null)
                    for (value in kolona) {
                        val provera = value.toFloatOrNull()
                        if (provera != null && provera < condition)
                            suma += value.toFloatOrNull() ?: 0.0f
                    }
            } else if (uslov.equals(Uslov.JEDNAKO.znak)) {
                if (kolona != null)
                    for (value in kolona) {
                        val provera = value.toFloatOrNull()
                        if (provera != null && provera.toInt() == condition.toInt())
                            suma += value.toFloatOrNull() ?: 0.0f
                    }
            }
        }
        else {
            if (kolona != null) for (value in kolona) suma += value.toFloatOrNull() ?: 0.0f
        }

        if (koeficijent != null) suma += koeficijent

        if (kolona != null) suma /= kolona.size

        return suma.toString()
    }


    override fun count(kolona: MutableList<String>?, uslov: String?, uslovValue: String?): String {
        var brojac = 0

        val condition = uslovValue?.toIntOrNull()
        if (uslovValue != null && condition != null) {
            if (uslov.equals(Uslov.VECE.znak)){
                if (kolona != null)
                    for(value in kolona){
                        val provera = value.toFloatOrNull()
                        if (provera!=null && provera > condition)
                            brojac++
                    }
            }else if (uslov.equals(Uslov.MANJE.znak)){
                if (kolona != null)
                    for(value in kolona){
                        val provera = value.toFloatOrNull()
                        if (provera!=null && provera < condition)
                            brojac++
                    }
            }else if (uslov.equals(Uslov.JEDNAKO.znak)){
                if (kolona != null)
                    for(value in kolona){
                        val provera = value.toFloatOrNull()
                        if (provera!=null && provera.toInt() == condition.toInt())
                            brojac++
                    }
            }
        }else{
            if (kolona != null)
                for(value in kolona){
                    brojac++
                }

        }

        return brojac.toString()
    }

}


enum class Uslov(val znak: String?) {
    VECE(">"), MANJE("<"), JEDNAKO("=")
}