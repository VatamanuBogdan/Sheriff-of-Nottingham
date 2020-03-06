package com.tema1.boardElements;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private LinkedList<Goods> deckCards;

    public Deck(final List<Integer> idCards) {
        deckCards = new LinkedList<Goods>();
        for (Integer it : idCards) {
            deckCards.addFirst(GoodsFactory.getInstance().getGoodsById(it));
        }
    }

    /**
     * Adauga o noua carte la sfarsitul pachetului.
     * @param otherGood Carte care va fi adaugata la sfarsitul pachetului.
     */
    public void addCardAtBottom(final Goods otherGood) {
        deckCards.addFirst(otherGood);
    }

    /**
     * Extrage(sterge) cartea din varful pachetului si o returneaza.
     * @return Cartea extrasa.
     */
    public Goods extractCard() {
        return deckCards.removeLast();
    }
}
