package model

import specifikacija.KalkInterface

class KalkImpl : KalkInterface {
    override fun zbir(
        novaKolona: String?,
        lista: List<List<Double?>>,
        koeficijent : Int?
    ): MutableList<String> {
        val stringovi = mutableListOf<String>()
        try {
            val lizta = MutableList(lista.size) { 0.0 }

//        for (col in lista) {
//            for (row in lista.indices) {
//                lizta[row] += col[row] ?: 0.0
//            }
//        }
            for (row in lista) {
                for (col in row.indices) {
                    lizta[col] += row[col] ?: 0.0
                }
            }

            for (broj in 0 until lizta.size) {
                if (koeficijent != null && koeficijent != 0) {
                    stringovi.add((lizta[broj] + koeficijent.toInt()).toString())
                } else {
                    stringovi.add(lizta[broj].toString())
                }
            }
        }catch (e : ClassCastException){
            println("Those aint Numbers!")
        }

        return stringovi
    }

    override fun mnozenje(
        novaKolona: String?,
        lista : List<List<Double?>>,
        koeficijent : Int?
    ): MutableList<String> {
        val stringovi = mutableListOf<String>()
        try {
            val lizta = MutableList(lista.size) { 1.0 }

            for (row in lista) {
                for (col in row.indices) {
                    lizta[col] *= row[col] ?: 0.0
                }
            }

            for (broj in 0 until lizta.size) {
                if (koeficijent != null && koeficijent != 0) {
                    stringovi.add((lizta[broj] * koeficijent.toInt()).toString())
                } else {
                    stringovi.add(lizta[broj].toString())
                }
            }
        }catch (e : ClassCastException){
                println("Those aint Numbers!")
            }
        return stringovi
    }

    override fun razlika(novaKolona : String?,
                         kolona1 : List<Double?>,
                         kolona2 : List<Double?>,
                         koeficijent : Int?
    ): MutableList<String> {
        val result = mutableListOf<String>()
        try {
            val maxRed = maxOf(kolona1.size, kolona2.size)
            val lizta = MutableList(maxRed) { 0.0 }

            for (i in 0 until maxRed) {
                lizta[i] = (kolona1[i] ?: 0.0) - (kolona2[i] ?: 0.0) - (koeficijent ?: 0)
            }

            for (broj in lizta) {
                result.add(broj.toString())
            }
        }catch (e : ClassCastException){
                println("Those aint Numbers!")
            }
        return result
    }

    override fun deljenje(
        novaKolona : String?,
        kolona1 : List<Double?>,
        kolona2 : List<Double?>,
        koeficijent : Int?
    ): MutableList<String> {
        val result = mutableListOf<String>()
        try {
            val brRed = maxOf(kolona1.size, kolona2.size)

            val lizta = MutableList(brRed) { 0.0 }

            for (i in 0 until brRed) {
                lizta[i] = (kolona1[i] ?: 0.0) / (kolona2[i] ?: 0.0)
            }

            for (broj in lizta) {
                if (koeficijent != null && koeficijent != 0) result.add((broj / koeficijent).toString())
                else result.add(broj.toString())
            }
        }catch (e : ClassCastException){
            println("Those aint Numbers!")
        }
        return result
    }

    override fun sum(kolona : MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent : Int?): String {
        var suma = 0.0f
        try {
            val condition = uslovValue?.toIntOrNull()
            if (uslov != "" && uslovValue != null && condition != null) {
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
            } else {
                if (kolona != null)
                    for (value in kolona) {
                        suma += value.toFloatOrNull() ?: 0.0f
                    }

            }

            if (koeficijent != null) suma += koeficijent
        }catch (e : ClassCastException){
            println("Those aint Numbers!")
        }
        return suma.toString()

    }

    override fun average(kolona: MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent: Int?): String {
        var suma = 0.0f
        try {
            val condition = uslovValue?.toIntOrNull()
            var brojac = 0

            if (uslov != "" && uslovValue != null && condition != null) {
                if (uslov.equals(Uslov.VECE.znak)) {
                    if (kolona != null)
                        for (value in kolona) {
                            val provera = value.toFloatOrNull()
                            if (provera != null && provera > condition) {
                                suma += value.toFloatOrNull() ?: 0.0f
                                brojac++
                            }
                        }
                } else if (uslov.equals(Uslov.MANJE.znak)) {
                    if (kolona != null)
                        for (value in kolona) {
                            val provera = value.toFloatOrNull()
                            if (provera != null && provera < condition) {
                                suma += value.toFloatOrNull() ?: 0.0f
                                brojac++
                            }
                        }
                } else if (uslov.equals(Uslov.JEDNAKO.znak)) {
                    if (kolona != null)
                        for (value in kolona) {
                            val provera = value.toFloatOrNull()
                            if (provera != null && provera.toInt() == condition.toInt()) {
                                suma += value.toFloatOrNull() ?: 0.0f
                                brojac++
                            }

                        }
                }
            } else {
                if (kolona != null) for (value in kolona) {
                    suma += value.toFloatOrNull() ?: 0.0f
                    brojac++
                }
            }

            if (koeficijent != null) suma += koeficijent
            if (kolona != null) suma /= brojac
        }catch (e : ClassCastException){
            println("Those aint Numbers!")
        }
        return suma.toString()
    }


    override fun count(kolona: MutableList<String>?, uslov: String?, uslovValue: String?): String {
        var brojac = 0
        try {
            val condition = uslovValue?.toIntOrNull()
            if (uslov != "" && uslovValue != null && condition != null) {
                if (uslov.equals(Uslov.VECE.znak)) {
                    if (kolona != null)
                        for (value in kolona) {
                            val provera = value.toFloatOrNull()
                            if (provera != null && provera > condition)
                                brojac++
                        }
                } else if (uslov.equals(Uslov.MANJE.znak)) {
                    if (kolona != null)
                        for (value in kolona) {
                            val provera = value.toFloatOrNull()
                            if (provera != null && provera < condition)
                                brojac++
                        }
                } else if (uslov.equals(Uslov.JEDNAKO.znak)) {
                    if (kolona != null)
                        for (value in kolona) {
                            val provera = value.toFloatOrNull()
                            if (provera != null && provera.toInt() == condition.toInt())
                                brojac++
                        }
                }
            } else {
                if (kolona != null)
                    for (value in kolona) {
                        brojac++
                    }

            }
        }catch (e : ClassCastException){
                println("Those aint Numbers!")
            }
        return brojac.toString()
    }

}


enum class Uslov(val znak: String?) {
    VECE(">"), MANJE("<"), JEDNAKO("=")
}