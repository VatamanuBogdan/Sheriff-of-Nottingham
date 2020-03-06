package com.tema1.player.model;

import com.tema1.common.Constants;
import com.tema1.player.elements.Declaration;
import com.tema1.player.elements.Cards;
import com.tema1.boardElements.Deck;
import com.tema1.game.Game;
import com.tema1.goods.Goods;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


public abstract class Player {
    protected final int id;
    protected int money;
    private int score;
    protected final Cards cards;
    protected final LinkedList<Goods> sack;
    protected final Map<Goods, Integer> stand;
    protected final Declaration declaration;

    public Player(final int id) {
        this.id = id;
        money = Constants.STARTMONEY;
        cards = new Cards();
        sack = new LinkedList<>();
        stand = new HashMap<>();
        declaration = new Declaration();
    }

    /**
     * Returneaza id-ul player-ului.
     * @return id-ul player-ului.
     */
    public int getId() {
        return id;
    }

    /**
     * Returneaza scorul player-ului.
     * @return scorul player-ului.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returneaza banii player-ului.
     * @return banii player-ului.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Returneaza sacul player-ului.
     * @return sacul player-ului.
     */
    public LinkedList<Goods> getSack() {
        return sack;
    }

    /**
     * Returneaza standul player-ului.
     * @return standul player-ului.
     */
    public Map<Goods, Integer> getStand() {
        return stand;
    }

    /**
     * Returneaza declaratia player-ului.
     * @return declaratia player-ului.
     */
    public Declaration getDeclaration() {
        return declaration;
    }

    /**
     * Adauga la scorul player-ului valoarea value.
     * @param value Valoarea adaugata la scor.
     */
    public void addScore(final int value) {
        score += value;
    }

    /**
     * Seteaza mita care va fi oferita la declarare bunurilor.
     * @param bribe Mita care va fi oferita la declarare bunurilor.
     */
    public void setBribeForSack(final int bribe) {
        money -= bribe;
        declaration.setBribe(bribe);
    }

    /**
     * Player-ul accepta mita de la player-ul x.
     * @param x Player-ul care ofera mita.
     */
    public void takeBribeFrom(final Player x) {
        money += x.getDeclaration().cleanBribe();
    }

    public abstract void createSack();

    public abstract void inspectSack();

    /**
     * Se recompleteaza man de carti a player-ului.
     */
    public void reloadCards() {
        Deck gameDeck = Game.getDeck();
        cards.burnOldCards();
        for (int i = 0; i < Constants.MAXCARDS; i++) {
            cards.addCard(gameDeck.extractCard());
        }
    }

    /**
     * Se pun pe stand bunurile ramase in sac ale player-ului.
     */
    public void putGoodsOnStand() {
        money += declaration.cleanBribe();
        while (!sack.isEmpty()) {
            Goods item = sack.pop();
            stand.put(item, stand.getOrDefault(item, 0) + 1);
        }
    }

    /**
     * Este controlat player-ul suspect.
     * @param suspect Player-ul care este controlat.
     */
    protected void controlPlayer(final Player suspect) {
        Goods declaredGood = suspect.getDeclaration().getDeclaredGood();
        Deck gameDeck = Game.getDeck();
        Iterator<Goods> it = suspect.getSack().iterator();
        boolean isTrue = true;
        while (it.hasNext()) {
            Goods curr = it.next();
            if (!(curr.equals(declaredGood))) {
                isTrue = false;
                money += curr.getPenalty();
                suspect.money -= curr.getPenalty();
                gameDeck.addCardAtBottom(curr);
                it.remove();
            }
        }

        if (isTrue) {
            int amount = suspect.getDeclaration().getNumberOfGoods()
                                * suspect.getDeclaration().getDeclaredGood().getPenalty();
            money -= amount;
            suspect.money += amount;
        }

    }

    public abstract String toString();
}
