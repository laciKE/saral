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
* `písmeno`
* `slovo`
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
* `&`, `|`, `!`, `^`, `a`, `alebo`, `ne` 
* `==`, `<`, `>`, `<=`, `>=`, `!=`, `<>`
* `=`
* `[]`

####Základné typy
* `skutočné numeralio`
* `neskutočné numeralio`
* `logický`
* `písmeno`
* `slovo`
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
meňak slovo meno
meno = "Ladislav"
meňak písmeno trieda = 'A'
```

####Deklarácia polí
```
funduš názov typ [rozmer]
funduš názov typ [rozmer][rozmer]

dimenzion funduš názov typ [rozmer]
dimenzion funduš názov typ [rozmer][rozmer]
```
Odporúčané je používať prvý spôsob deklarácie, možnosti s kľúčovým slovom `dimenzion` sú podporované iba kvôli zachovaniu spätnej kompatibility s jazykom Šaral. Rozmer musí byť meňak, furt alebo hodnota typu `neskutočné numerálio`.

#####Príklady
```
funduš šč skutočné numeralio [3]
meňal neskutočné numeralio N = 42
dimenzion funduš fň logický [N][10]
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

#####Príklady
```
bar ZámenaManželiek(neskutočné numerálio a, neskutočné numerálio b)
	a = a + b
	b = a - b
	a = a - b
	
bar slovo SlovakPub()
	vrac "Bryndzové halušky"

meňak slovo jedlo
jedlo = vrac mi z baru SlovakPub()

meňak neskutočné numerálio X = 500
meňak neskutočné numerálio Y = 600
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
While cyklus zapíšeme nasledovne:
```
kým (furt alebo meňak) == (furt alebo meňak) rob
	...

kým (logický furt alebo meňak) rob
	...
```
Použitie druhej konštrukcie s logickým furtom je však odporúčané dobre si premyslieť.
