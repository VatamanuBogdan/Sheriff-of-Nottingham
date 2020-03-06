Vatamanu Bogdan-Mihai grupa 324CD

Clasa game este un singleton in interiorul caruia se retine lista jucatorilor,
pachetul de carti, runda curenta si id-ul sheriffului curent. Motivul pentru 
care am facut-o de tip singleton este pentru a accesa global toate campurile 
precedente. Metoda run porneste jocul aceasta returnand o lista ce reprezinta 
clasamentul jucatorilor. Pentru fiecare etapa a jocului am creat cate o metoda 
proprie care este apelata in run.

Pentru jucatori am creat clasa abstracta Player care este mostenita de catre 
clasa BasicPlayer. Tipurile greedy si bribed mostenesc BasicPlayer deoarece 
pentru implementarea lor avem nevoie de metode din BasicPlayer.
__________________________________________
Player --- BasicPlayer ------ GreedyPlayer|
                        \                 |
                         \-----BribePlayer|
__________________________________________|

In interiorul clasei player am creat metodele abstracte createSack si 
inspectSack care vor fi implementate in clasele care o mostenesc(acestea difera 
in functie de stategia adoptata de fiecare player). Metodele reloadCards si 
controlPlayer le-am implementat direc in clasa Player deoarece sunt la fel 
pentru fiecare strategie.
Metoda toString returneaza strategie/tipul player-ului.
        __________________
        |  > id           |
        |  > money        |
        |  > score        |
Player :|  > cards        |
        |  > sack         |
        |  > stand        |
        |  > declaration  |
        |_________________|

Pentru elementele pe care le detine player-ul am creat clasele:
    -> Cards - creata pentru cartile pe care le detine player-ul aceasta avand 
                ca structura de baza de memorie un hashmap in care retin 
                pereche Goods : numarul de aparitii al acestuia
    -> Declaration - creata pentru a retine declaratia player-ului si valoarea 
                     mitei(daca este oferita).
Pentru sac am folosit un Linked list iar pentru stand un hashmap.

In clasele BasicPlayer, GreedyPlayer si BribePlayer am implementat metodele 
createSack si inspectSack dupa strategia pe care o adopata fiecare player.
________________________________________________________________________________
[Basic]: Pentru stategia basic am mai creat metodele:                                              
        -> addLegalGoods - adauga bunul cu frecventa ce mai mare
        -> addIllegalGoods - adauga un singur bun ilegal
                                (cel mai valoros pe care il are)
        -> isBetter - ajuta la compararea bunurilor in metoda addLegalGoods
--------------------------------------------------------------------------------
[Greedy]: Pentru crearea sacului se foloseste de metodele addLegalGoods si 
          addIllegalGoods din clasa parinte.
--------------------------------------------------------------------------------
[Bribed]: Pentru strategia bribe am mai creat metodele:
        -> bribeStrategyForSack - adauga bunuri dupa stategia bribed
                                - returneaza true daca stategia se poate aplica
                                  si false in caz contrar
        -> getListOfSortedItems - returneaza lista bunurilor pe care jucatorul 
                                le are in mana acestea fiind sortate dupa profit
________________________________________________________________________________
                                        