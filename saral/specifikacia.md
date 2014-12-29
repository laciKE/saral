##ŠARAL 2.0 - ŠARIŠ ALGORITMIC LENGVIDŽ 2.0

Môj návrh vychádza z jazyka Šaral, ktorého prezentáciu v "populárno-vedeckej" forme je možné nájsť napr. na http://www.trsek.com/sr/clanky/saral (odporúčam vypočuť si zvukovú nahrávku dostupnú v spodnej časti stránky).

Platné identifikátory sú postupnosti malých a veľkých písmen, číslic a podtržníkov začínajúce písmenom alebo jedným podrtžníkom a písmenom, teda `_?[:letter:]([:digit:][:letter:]_)*`.

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
* `písmeno`
* `slovo`
* `falda`
* `inakši`
* `bar`
* `paľ do baru`
* `vrac še z baru`
* `vrac`
* `stuj`
* `keď`
* `potom`
* `inak`
* `zrob s meňakom`
* `od`
* `do`
* `kým`
* `rob`
* `ciskaj`
* `vežmi`
* `sluchaj`
* `povidz`
* `a`
* `alebo`
* `ne`

Kľúčové slová `dimenzion` a `stuj` v návrhu jazyka Šaral 2.0 ostávajú iba pre zachovanie spätnej kompatibility s jazykom Šaral.

####Operátory
* `+`, `-`, `*`, `/`, `:`, `%`
* `a`, `alebo`, `ne` 
* `==`, `<`, `>`, `<=`, `>=`, `<>`
* `=`
* `[]`

####Základné typy
* `skutočné numeralio`
* `neskutočné numeralio`
* `logický`
* `písmeno`
* `slovo`
* `funduš`

#####Trojhodnotová logika
Jazyk Šaral (aj Šaral 2.0) používa trojhodnotovú logiku s hodnotami `pravda`, `ošaľ` a `skoroošaľ` zodpovedajúcimi hodnotam `True`, `False` a `Unknown` v Kleeneho logike. Pri vyhodnocovaní logických výrazov v podmienkach a cykloch sa podmienka vyhodnotí ako pravdivá jedine v prípade, ak je jej hodnota `Pravda`.

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
meňak slovo meno
meno = "Ladislav"
meňak písmeno trieda = 'A'
```

####Deklarácia polí
```
funduš typ názov [rozmer]
funduš typ názov [rozmer][rozmer]

dimenzion funduš typ názov [rozmer]
dimenzion funduš typ názov [rozmer][rozmer]
```
Odporúčané je používať prvý spôsob deklarácie, možnosti s kľúčovým slovom `dimenzion` sú podporované iba kvôli zachovaniu spätnej kompatibility s jazykom Šaral. Rozmer musí byť meňak, furt alebo hodnota typu `neskutočné numerálio`.

#####Príklady
```
funduš skutočné numeralio šč [3]
meňak neskutočné numeralio N = 42
dimenzion funduš logický fň [N][10]
```

####Definícia procedúr a funkcií
V jazyku Šaral 2.0 sú podporované funkcie a procedúry, ktoré pri pohľade z vonku vyzerajú ako čierne skrinky, resp. bary, do ktorých vstupujú rôzne veci, a občas sa z nich aj niečo vráti. Odtiaľ pochádza aj spôzob ich zápisu v jazyku Šaral 2.0:

```
bar názov(typ názov, typ názov, ...) 
	...
	popis toho, čo sa deje v bare
	...


bar typ názov(typ názov, typ názov, ...) 
	...
	popis toho, čo sa deje v bare
	...
	vrac hodnota
```

Prvý bar nám nevracia nič, kým druhý bar po skončení svojej činnosti vráti jednu hodnotu, ktorá je rovnakého typu ako bar.
Pokiaľ niekde chceme využiť služby, ktoré nám ponúkajú bary, použijeme buď kľúčovú konštrukciu `paľ do baru` alebo `vrac mi z baru`.
*Funkcie berú ako argumenty referencie na premenné.*

#####Externé funkcie
V jazyku Šaral 2.0 je možné použiť aj bary z cudzokrajných prostredí, v ktorých sa rozpráva iným jazykom (ak sú samozrejme v tých baroch  použité rovnaké typy, len inak nazvané, lebo cudzí jazyk). O našej snahe okoštovať takéto cudzie bary informujeme jazyk Šaral pomocou kunštrukcie

```
inakši bar názov(typ názov, typ názov, ...) 

inakši bar typ názov(typ názov, typ názov, ...) 
```


#####Príklady
```
inakši bar neskutočné numeralio puts(slovo s)

bar ZámenaManželiek(neskutočné numeralio a, neskutočné numeralio b)
	a = a + b
	b = a - b
	a = a - b
	
bar slovo SlovakPub()
	vrac "Bryndzové halušky"

meňak slovo jedlo
jedlo = vrac mi z baru SlovakPub()

meňak neskutočné numeralio X = 500
meňak neskutočné numeralio Y = 600
paľ do baru ZámenaManželiek(X, Y)
```
 
####Podmienky
```
keď (furt alebo meňak) == (furt alebo meňak) potom
	...
inak
	...
	
keď (logický furt alebo meňak) potom
	...
inak
	...
```
Časti `inak` povinné nie sú.

####Cykly
Cyklus s pevným počtom opakovaní (tiež zvaný kolečko) zapíšeme nasledovne:
```
zrob s meňakom názov od (furt alebo meňak) do (furt alebo meňak)
	...
```
*premenná v cykle nadobúda hodnoty z intervalu <od;do)*

While cyklus zapíšeme nasledovne:
```
kým (furt alebo meňak) == (furt alebo meňak) rob
	...

kým (logický furt alebo meňak) rob
	...
```
Je však prudko odporúčané dobre si premyslieť použitie druhej konštrukcie s logickým furtom.

####Vstup a výstup
#####stdin/stdout
Ak chceme niečo vypísať, použijeme príkaz `ciskaj názov`, kde názov je názov meňaku alebo furtu, ktorý treba vypísať.
Vstup sa načítava pomocou príkazu `vežmi názov`, kde názov je názov meňaku, ktorý treba načítať.

#####nstdin/nstdout
Pre počítače 8. generácie a ich (v dobe vytvorenia jazyka Šaral 1.0) nestandardný vstup a výstup má tento jazyk podporu aj pre inštrukcie `povidz typ názov` a `sluchaj typ názov`, ktoré možno budú podporované už v jazyku Šaral 2.0 (ak bude čas a podarí sa mi to rozbehať)

####Rozšírenie schopností jayzka
V prípade, že máme užitočnú zbierku barov, meňakov, furtov alebo fundušov, môžeme si ich odložiť do skladu. Keď ich opať budeme chcieť použiť, jazyku Šaral 2.0 to oznámime pomocou príkazu `falda` (sklad) nasledovaného menom skladu (meno súbora). Obsah tohto súbora sa vloží namiesto riadka s príkazom falda. Celé toto sa deje ešte v predspracovaní vstupu a pred lexikálnou analýzou.

####*Platnosť premenných*
*Premenné existujú v bloku, v ktorom boli definované. Premenné definované v najvyššom bloku sú globálne a všetky procedúry a funkcie k nim majú prístup*
