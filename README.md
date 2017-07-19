## ŠARAL 2.0 - ŠARIŠ ALGORITMIC LENGVIDŽ 2.0

Projekt na predmet Kompilátory. Úlohou je navrhnúť turingovsky úplný jazyk a vytvoriť kompilátor z tohto jazyka do LLVM

Môj návrh vychádza z jazyka Šaral, ktorého prezentáciu v "populárno-vedeckej" forme je možné nájsť napr. na http://www.trsek.com/sr/clanky/saral (odporúčam vypočuť si zvukovú nahrávku dostupnú v spodnej časti stránky).

Špecifikáciu jazyka nájdete v súbore `specifikacia.md`, ukážkové príklady v adresári `examples`.

#### Kompilovanie
*Pre správnu funkčnosť je potrebné do adresára `lib/` umiestniť súbor a`antlr-4.4-complete.jar` dostupný na stránke http://www.antlr.org/download.html.*

Najprv je potrebné skompilovať samotný kompilátor, ten sa skompiluje pomocou skriptu `build.sh`, alebo pomocou `make`, resp. `make all`.

Programy napísané v jazyku Šaral sa kompilujú pomocou skriptu `compile.sh`, ktorý prijíma dva argumenty: meno súbora so zdrojovým kódom programu v jazyky Šaral (prípona .srl nie je v argumente povinná) a názvu binárky, do ktorej sa má program skompilovať (druhý argument nie je povinný).

#### Zmeny v porovnaní s pôvodnou špecifikáciou
Podporované sú iba jednorozmerné polia (ale klasickým trikom sa dajú využívať ako dvojrozmerné, ako je ukázané v ukážkovom súbore `examples/graf.srl`). 
V podmienkach v if a while cykle je možné použiť ľubovoľný výraz, ktorý vracia logický typ, a nie iba premennú alebo konštantu, prípadne operátor rovnosti.
Pre typ `slovo` je podporovaný operátor `+`, ktorý spája dve reťazce do jedného.
Preprocessor podporuje vkladanie skladov (pomocou kľúčového slova `falda`) nielen z aktuálneho adresára, ale aj z adresára, ktorý dostane ako parameter (štandardne je to adresár `include`).
Všetky premenné sú lokálne a sú viditeľné iba v rámci bloku, v ktorom boli definované. Vo vnorených blokoch je možné prekrýt premenné s rovnakým názvom z nadradeného bloku. Funkcie vidia iba vlastné lokálne premenné a svoje argumenty. Na prístup k premenným z iných funkcií alebo blokov však môžu využiť knižnicu `storage`, prípadne ukážkovú implementáciu `modul_lib` a `modul_use` (viac info v časti o knižniciach).

#### Knižnice
Štandardné knižnice jazyka Šaral sa nachádzajú v adresári `include`.

Knižnica `slova.srl` je na prácu s typom `slovo` (zisťovanie dĺžky reťazca, porovnávanie reťazcov, reverz slova, konverziu základných typov na typ `slovo`). Táto knižnica je celá napísaná v jazyku Šaral a je možné ju použiť na ľubovoľnom mieste programu, teda napríklad ak ju chcem používať iba v rámci tela nejakej funkcie, môžem ju vložiť iba do danej funkcie. (ukážka `examples/modul_lib.srl`).

Knižnica `storage.srl` deklaruje tri externé funkcie napísané v jazyku C++ (v zdrojovom kóde `libstorage.cpp`), ktoré sa dajú použiť ako asociatívne pole. Asociatívne pole je implementované pomocou STL Map pre textové dvojice <kľúč, hodnota>. Spolu s konverziou základných typov na typ `slovo` je tak túto knižnicu možné použiť na ukladanie hodnôt, ku ktorým sa funkcie budú chcieť vrátiť neskôr. Ukážková implementácia funkcie, ktorá sa dá použiť ako akýsi "modul", je v súbore `examples/modul_lib.srl` a jej použitie je v súbore `examples/modul_use.srl`.
Po doprogramovaní funkcií na konverziu typu `slovo` na základné typy je takto možné uložiť a znova načítať ľubovoľný základný typ.
