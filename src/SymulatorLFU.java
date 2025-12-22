import java.util.*;

public class SymulatorLFU {
    private int rozmiarCache;                        // maksymalna liczba ramek w pamięci podręcznej
    private Map<Integer, Strona> mapaCache;          // szybki dostęp: numer strony → obiekt Strona
    private PriorityQueue<Strona> kolejkaMin;         // min-heap do szybkiego znajdowania najmniej używanej strony

    // Konstruktor – tworzymy symulator o podanym rozmiarze cache
    public SymulatorLFU(int rozmiarCache) {
        this.rozmiarCache = rozmiarCache;
        this.mapaCache = new HashMap<>();

        // Min-heap: najpierw porównujemy częstotliwość (mniejsza = wyżej),
        // przy remisie – czas wejścia (starsza strona wyżej)
        this.kolejkaMin = new PriorityQueue<>((a, b) -> {
            if (a.czestotliwosc != b.czestotliwosc) {
                return a.czestotliwosc - b.czestotliwosc;   // mniejsza częstotliwość na górze
            }
            return Long.compare(a.czasWejscia, b.czasWejscia); // starsza strona (mniejszy czas) na górze
        });
    }

    // Główna metoda – obsługa odwołania do strony
    public void odwolanieDoStrony(int numerStrony) {
        if (mapaCache.containsKey(numerStrony)) {
            // HIT – strona jest już w cache
            Strona strona = mapaCache.get(numerStrony);
            kolejkaMin.remove(strona);         // usuwamy starą wersję z kolejki
            strona.czestotliwosc++;            // zwiększamy licznik użyć
            kolejkaMin.add(strona);            // dodajemy zaktualizowaną wersję
            System.out.println("HIT dla strony " + numerStrony);
        } else {
            // MISS – strony nie ma w cache
            System.out.println("MISS dla strony " + numerStrony);

            // Jeśli cache jest pełny – wyrzucamy najmniej używaną stronę
            if (mapaCache.size() >= rozmiarCache) {
                Strona doUsuniecia = kolejkaMin.poll();  // bierzemy stronę z najmniejszą częstotliwością
                mapaCache.remove(doUsuniecia.numerStrony);
                System.out.println("  Wyrzucono stronę " + doUsuniecia.numerStrony +
                        " (częstotliwość: " + doUsuniecia.czestotliwosc + ")");
            }

            // Wczytujemy nową stronę
            Strona nowaStrona = new Strona(numerStrony);
            mapaCache.put(numerStrony, nowaStrona);
            kolejkaMin.add(nowaStrona);
            System.out.println("  Wczytano stronę " + numerStrony);
        }
    }

    // Metoda pomocnicza – pokazuje aktualny stan cache w konsoli (do debugowania)
    public void pokazStanCache() {
        System.out.print("Aktualny stan cache: ");
        for (Strona s : mapaCache.values()) {
            System.out.print(s.numerStrony + "(" + s.czestotliwosc + ") ");
        }
        System.out.println();
    }

    // WAŻNA METODA DLA GUI!
    // Zwraca wszystkie strony obecnie znajdujące się w cache
    // Dzięki temu klasa InterfejsGraficzny może je narysować
    public Collection<Strona> pobierzStronyWCache() {
        return mapaCache.values();
    }
}