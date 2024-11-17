package specifikacija

interface KalkInterface {

    /**
     *
     * Funkcija koja za proslednjene kolone vraca jednu kolonu koja ce sadrzati zbir njihovih vrednosti.
     *
     * @param novaKolona String koji sadrzi naziv nove kolone.
     *
     * @param lista Lista listi koja sadrzi proslednjene kolone.
     *
     * @param koeficijent Int parametar koji moze biti proslednjen radi modifikacije rezultata.
     *                    Bice sabran!
     *
     */
    fun zbir(novaKolona : String?, lista : List<List<Double?>>, koeficijent : Int?) : MutableList<String>

    /**
     *
     * Funkcija koja za proslednjene kolone vraca jednu kolonu koja ce sadrzati proizvod njihovih vrednosti.
     *
     * @param novaKolona String koji sadrzi naziv nove kolone.
     *
     * @param lista Lista listi koja sadrzi proslednjene kolone.
     *
     * @param koeficijent Int parametar koji moze biti proslednjen radi modifikacije rezultata.
     *                    Bice umnozen!
     *
     */
    fun mnozenje(novaKolona : String?, lista : List<List<Double?>>, koeficijent : Int?): MutableList<String>

    /**
     *
     * Funkcija koja za dve proslednjene kolone vraca jednu kolonu koja ce sadrzati razliku njihovih vrednosti.
     *
     * @param novaKolona String koji sadrzi naziv nove kolone.
     *
     * @param kolona1 Kolona od koje ce biti oduzeto.
     *
     * @param kolona2 Kolona koja se oduzima.
     *
     * @param koeficijent Int parametar koji moze biti proslednjen radi modifikacije rezultata.
     *                    Bice oduzet!
     *
     */
    fun razlika(novaKolona : String?, kolona1 : List<Double?>, kolona2 : List<Double?>, koeficijent : Int?) : MutableList<String>

    /**
     *
     * Funkcija koja za dve proslednjene kolone vraca jednu kolonu koja ce sadrzati kolicnik njihovih vrednosti.
     *
     * @param novaKolona String koji sadrzi naziv nove kolone.
     *
     * @param kolona1 Kolona deljenik.
     *
     * @param kolona2 Kolona delilac.
     *
     * @param koeficijent Int parametar koji moze biti proslednjen radi modifikacije rezultata.
     *                    Bice delilac!
     *
     */
    fun deljenje(novaKolona : String?, kolona1 : List<Double?>, kolona2 : List<Double?>, koeficijent : Int?) : MutableList<String>

    /**
     *
     * Funkcija koja sumira sva polja proslednjene kolone.
     *
     * @param kolona Lista koja sadrzi proslednjenu kolonu.
     *
     * @param uslov Opcioni uslov po kojem ce se vrsiti operacija.
     *              Moze biti: < , = , >
     *
     * @param uslovValue Opciona promenljiva po kojoj se uslov vrednuje.
     *
     * @param koeficijent Int parametar koji moze biti proslednjen radi modifikacije rezultata.
     *                    Bice sabran!
     *
     */
    fun sum(kolona : MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent : Int?) : String

    /**
     *
     * Funkcija koja vraca prosek svih polja proslednjene kolone.
     *
     * @param kolona Lista koja sadrzi proslednjenu kolonu.
     *
     * @param uslov Opcioni uslov po kojem ce se vrsiti operacija.
     *              Moze biti: < , = , >
     *
     * @param uslovValue Opciona promenljiva po kojoj se uslov vrednuje.
     *
     * @param koeficijent Int parametar koji moze biti proslednjen radi modifikacije rezultata.
     *                    Bice sabran pre deljenja!
     *
     */
    fun average(kolona : MutableList<String>?, uslov: String?, uslovValue: String?, koeficijent : Int?) : String

    /**
     *
     * Funkcija koja prebrojava sva polja proslednjene kolone.
     *
     * @param kolona Lista koja sadrzi proslednjenu kolonu.
     *
     * @param uslov Opcioni uslov po kojem ce se vrsiti operacija.
     *              Moze biti: < , = , >
     *
     * @param uslovValue Opciona promenljiva po kojoj se uslov vrednuje.
     *
     *
     */
    fun count(kolona: MutableList<String>?, uslov: String?, uslovValue: String?) : String
}