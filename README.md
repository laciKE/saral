Kompilatory 2014
=============

Na spustenie testovaca potrebujete python a okrem standardnych modulov
potrebujete aj modul sh, ktory viete nainstalovat pomocou **sudo pip install
sh**.

Format projektu
-------------
Na to, aby ste mohli pouzit testovac, vyrobte adresar s menom vasho
projektu (napriklad nazov vasho jazyka, vase meno).

Adresar by mal obsahovat nasledovne subory/skripty:
* **build.sh**  - skompiluje vas kompilator
* **compile.sh input output** - spusti vas kompilator na vstupe input, a vyprodukuje subor output
* **run.sh program** - spusti zadany program (vystup z kompilatora).
* **inputs.in** - obsahuje dvojice *uloha*, *subor* oddelene medzerou, kde uloha je z mnoziny {primes, graph, sort} a
subor je cesta k suboru, ktory obsahuje program, ktory bude kompilovat vas kompilator.
* Vas kompilator, ako aj testovacie programy.

Pozrite si vzorovy adresar c++. Ak spustite judge.py, tak sa vam program
otestuje na vzorovych vstupoch. Parametre su taketo: **python judge.py projekt
[testy ...]** kde projekt je meno vasho adresara a testy je zoznam testov na
ktorych chcete spustit testy (je to nepovinny argument, ak to vynechate, spusti
to na vsetkych testoch). Napriklad **python judge.py c++ sort graph** otestuje
c++ kompilator na ulohach sort (utriedit postupnost) a graph (zistit ci je graf
suvisly). **python judge.py c++** otestuje c++ kompilator na vsetkych ulohach.


Odovzdanie
-------------
Ak sa vas kompilator lisi v niecom od povodnej specifikacie, spomente to pri
odovzdani (inac to budem povazovat za chybu).

Vyberte si jeden zo sposobov odovzdania, uvadzam ich v preferovanom poradi.
- Spravte pull request do tohto repozitara s vasim projektom tak, aby sa dal
pomocou programu judge.py otestovat. To znamena v samostatnom adresari zo subormi
build.sh, compile.sh, run.sh a inputs.in
- To iste ako predtym, ale poslite mi to emailom (odovzdajte prosim jeden archiv)
- Poslite projekt emailom (ako jeden archiv, ktory obsahuje vsetko) aj s podrobnymi instrukciami
o tom, ako to skompilovat, spustit, ...
