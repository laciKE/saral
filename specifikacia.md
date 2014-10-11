##ŠARAL 2.0 - ŠARIŠ ALGORITMIC LENGVIDŽ 2.0

Môj návrh vychádza z jazyka Šaral, ktorého prezentáciu v "populárno-vedeckej" forme je možné nájsť napr. na http://www.trsek.com/sr/clanky/saral (odporúčam vypočuť si zvukovú nahrávku dostupnú v spodnej časti stránky).

####Kľúčové slová:
* `furt`
* `meňak`
* `parcela`
* `dimenzion`
* `pravda`
* `ošaľ`
* `skoroošaľ`
* `logický`
* `skutočné`
* `neskutočné`
* `numeralio`
* `litera`
* `struna`
* `bar`
* `paľ do baru`
* `stuj`
* `keď`
* `potom`
* `inak`
* `zrob s meňakom`
* `od`
* `do`
* `dokým`
* `rob`
* `ciskaj`
* `vežmi`
* `sluchaj`
* `povidz`
* `a`
* `alebo`
* `nie`

Kľúčové slová `dimenzion` a `stuj` v návrhu jazyka Šaral 2.0 ostávajú iba pre zachovanie spätnej kompatibility s jazykom Šaral.

####Operátory
* `+`, `-`, `*`, `/`, `:`, `%`
* `&`, `|`, `!`, `^`, `a`, `alebo`, `nie` 
* `==`, `<`, `>`, `<=`, `>=`, `!=`, `<>`
* `=`

####Základné typy
* `skutočné numeralio`
* `neskutočné numeralio`
* `logický`
* `litera`
* `struna`
* `funduš`

####Deklarácia premenných a konštánt
```
modifikátor typ názov
modifikátor typ názov = hodnota
```

Modifikátor určuje, či bude identifikátor `názov` predstavovať premennú (`meňak`) alebo konštantu (`furt`). Prvý spôsob deklarácie je dovolený iba pre premenné, pri deklarovaní konštanty je potrebné ju hneď aj definovať.

#####Príklady
```
furt skutočné numeralio pi = 3.14
furt neskutočné numeralio odpoveď = 42
meňak logický najväčší = ošaľ
meňak struna meno
meno = "Ladislav"
meňak litera trieda = 'A'
```
