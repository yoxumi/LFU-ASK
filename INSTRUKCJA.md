Symulator algorytmu zastępowania stron LFU

Temat projektu: Wykonaj symulator algorytmu LFU zastępowania stron pamięci podręcznej
Język realizacji: JAVA

CO TO JEST LFU? – dokładna definicja z wykładu (Wykład 9, str. 15)
„Algorytm LFU (least frequently used – najrzadziej używany) – polega na zastępowaniu bloku w sekcji którego dotyczyło najmniej odniesień.”
Czyli: gdy cache jest pełny wyrzucamy stronę, do której odwoływano się najrzadziej

najprostrzy przyklad jak to zrozumiec mniej wiecej:

Komputer ma bardzo szybką, ale małą pamięć podręczną (cache).  
U nas będzie ona miała tylko 8 miejsc (8 stron pamięci).

Program cały czas prosi o różne strony:  
np. strona 5, strona 12, strona 5, strona 5, strona 27, strona 5, strona 8…

Kiedy wszystkie 8 miejsc jest zajętych, a program chce nową stronę – trzeba coś wyrzucić.

**LFU = Least Frequently Used**  
czyli:  
→ **„wyrzuć tę stronę, której używaliśmy NAJRZADZIEJ”**

Komputer pamięta, ile razy była używana każda strona, która jest w cache.  
Gdy trzeba zrobić miejsce – patrzy na liczniki i wyrzuca tę z najmniejszą liczbą użyć.

Przykład:  
W cache mamy:  
- strona 5 → użyta 30 razy  
- strona 12 → użyta 15 razy  
- strona 8 → użyta 3 razy ← najmniejsza liczba  
→ jak trzeba miejsce → wyrzucamy stronę 8.

Dzięki temu najczęściej używane strony zostają długo w szybkiej pamięci, a rzadko używane wylatują.

## Co robimy w projekcie?
Zbudujemy w Javie symulator pamięci podręcznej z algorytmem LFU:
- cache o rozmiarze 8 stron
- 100 000+ odwołań z lokalnością 80/20 (z wykładu 9 – „lokalność odniesień”)
- na końcu: hit ratio, statystyki, TOP 10 najczęściej używanych stron, testy ręczne

  ## PODZIAŁ ZADAŃ – każda osoba ma swoją gałąź i swój plik
  Maria Engel (ja) – gałąź main  
Plik: Main.java  
→ uruchamiam całą symulację, tworze obiekty (generator, cache, progress, statystyki), pobieram argumenty z linii poleceń, na końcu wywołuje statystyki i testy ręczne + robie sprawozdanie Word.

Alan Gajlewicz – gałąź alan-gajlewicz  
Plik: TraceGenerator.java  
→ klasa, która generuje kolejne odwołania do stron z lokalnością 80/20  
→ musi mieć metodę public int nextPage()  
→ przykład: 80 % szans na wylosowanie strony 0–9, 20 % szans na 0–49

Aleh Hrytsyna – gałąź aleh-hrytsyna  
Plik: FrequencyStructures.java  
→ trzymasz w niej trzy rzeczy:  
   • Map<Integer, Integer> pageFreq – ile razy użyto danej strony  
   • Map<Integer, LinkedList<Integer>> freqMap – dla każdej częstotliwości lista stron  
   • int minFreq – aktualna najmniejsza częstotliwość  
→ jedna metoda public void increaseFreq(int page) – zwiększa licznik i przenosi stronę na właściwą listę

Maksymilian Jabłoński – gałąź maksymilian-jablonski  
Plik: LFUReplacement.java  
→ główna logika cache  
→ metody:  
   • boolean contains(int page)  
   • void access(int page) – sprawdza hit/miss, jak miss i pełny → evictLFU() → addNewPage(page)  
   • void evictLFU() – usuwa stronę z najmniejszej częstotliwości  
   • void addNewPage(int page) – dodaje nową stronę z freq = 1

Mykhailo Honchar – gałąź mykhailo-honchar  
Plik: ProgressPrinter.java  
→ co 10 000 odwołań wypisuje w konsoli coś w stylu:  
   Przetworzono: 350000 / 1000000 (35.00%)   Aktualny hit ratio: 93.87%

Oleksandr Fedkiv – gałąź oleksandr-fedkiv  
Plik: Statistics.java  
→ na samym końcu programu wypisuje ładną ramkę:  
   Odwołań: 1 000 000  
   Hit:     942 130  
   Miss:     57 870  
   Hit ratio: 94.21%  
   + TOP 10 najczęściej używanych stron (np. strona 5 → 87 421 użyć itd.)

Volodymyr Havenko – gałąź volodymyr-havenko  
Plik: ManualTests.java  
→ 12 małych, ręcznych testów (sekwencje po 10–20 odwołań)  
→ dla każdej sekwencji wypisuje krok po kroku co się dzieje, co wyrzuciło i ile było miss  
→ przykład: sekwencja 0,1,2,3,0,4,0,1 → 6 miss, wyrzuciło stronę 2 itd.

WSTEPNIE TAK TO WYGLADA POZNIEJ DOPISZE DOKLADNIEJ JAK MOZECIE ZAPISAC KOD JESLI NIE WIECIE, ALBO JESLI KTOS MA SWOJ POMYSL I COS CHCE ZMIENIC I PRZEGADAC TO PISZCIE + JESLI KOD NIE BEDZIE SPOJNY Z CZYIMS KAWALKIEM KODU Z PRZYDZIELONEGO ZADANIA NAJWYZEJ ZROBIMY POPRAWKI MALE I OPRACUJEMY RAZEM ;))
