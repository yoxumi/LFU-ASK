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
  | Osoba                    | Gałąź                  | Plik, który tworzysz                | Co dokładnie robisz  |
|--------------------------|--------------------------|-------------------------------------|----------------------|
| Maria Engel (ja)         | main                     | `Main.java`                         | Uruchomienie, argumenty, scalanie, Word |
| Alan Gajlewicz           | alan-gajlewicz           | `TraceGenerator.java`               | Generator odwołań z lokalnością 80/20 |
| Aleh Hrytsyna            | aleh-hrytsyna            | `FrequencyStructures.java`          | HashMap + LinkedList + increaseFreq() |
| Maksymilian Jabłoński    | maksymilian-jablonski    | `LFUReplacement.java`               | evictLFU() + addNewPage() + hit/miss |
| Mykhailo Honchar         | mykhailo-honchar         | `ProgressPrinter.java`              | Postęp co 10 000 odwołań + bieżący hit ratio |
| Oleksandr Fedkiv         | oleksandr-fedkiv         | `Statistics.java`                   | Końcowa tabelka + TOP 10 najczęściej używanych stron |
| Volodymyr Havenko        | volodymyr-havenko        | `ManualTests.java`                  | 12 testów ręcznych + wyniki krok po kroku

WSTEPNIE TAK TO WYGLADA POZNIEJ DOPISZE DOKLADNIEJ JAK MOZECIE ZAPISAC KOD JESLI NIE WIECIE POGOLADAJCIE COS NA YT JAKIES PORADNIKI O TYM.
