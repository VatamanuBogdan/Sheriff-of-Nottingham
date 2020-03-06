package com.tema1.player.elements;

import com.tema1.goods.Goods;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cards {
    private Map<Goods, Integer> cards;
    private int size = 0;

    public Cards() {
        cards = new HashMap<Goods, Integer>();
    }

    /**
     * Returneaza numerul de carti de tipul x pe care jucatorul le are in mana.
     * @param x Tipul de carte cautat.
     * @return Numarul de carti de tip x.
     */
    public int getCount(final Goods x) {
        return cards.get(x);
    }

    /**
     * Returneaza numarul de carti pe care jucatorul le are in mana.
     * @return numarul de carti pe care jucatorul le are in mana.
     */
    public int size() {
        return size;
    }

    /**
     * Adauga o carte de tipul x in mana player-ului.
     * @param x Cartea care va fi adaugata.
     */
    public void addCard(final Goods x) {
        size++;
        cards.put(x, cards.getOrDefault(x, 0) + 1);
    }

    /**
     * Sterge o carte de tipul x din mana player-ului.
     * @param x Cartea care va fi stearsa.
     */
    public void removeCard(final Goods x) {
        size--;
        int count =  cards.get(x);
        if (count == 1) {
            cards.remove(x);
        } else {
            cards.put(x, cards.get(x) - 1);
        }
    }

    /**
     * Sterge toate cartile din mana player-ului.
     */
    public void burnOldCards() {
        size = 0;
        cards.clear();
    }

    /**
     * Returneaza multime tipurilor de carti pe care le are player-ul.
     * @return Multimea tipurilor de carti pe care le are player-ul.
     */
    public Set<Goods> goodsSet() {
        return cards.keySet();
    }
}
