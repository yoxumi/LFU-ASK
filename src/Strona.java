public class Strona {
    int numerStrony;       // numer strony w pamięci
    int czestotliwosc;     // ile razy była używana
    long czasWejscia;      // czas wejścia do cache (do remisów)

    // Konstruktor – tylko jeden argument: numer strony
    public Strona(int numerStrony) {
        this.numerStrony = numerStrony;
        this.czestotliwosc = 1;                          // pierwsze użycie
        this.czasWejscia = System.currentTimeMillis();   // aktualny czas w milisekundach
    }
}