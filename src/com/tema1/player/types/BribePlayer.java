package com.tema1.player.types;

import com.tema1.player.model.Player;
import com.tema1.common.Constants;
import com.tema1.goods.GoodsType;
import com.tema1.game.Game;
import com.tema1.goods.Goods;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class BribePlayer extends BasicPlayer {
    public BribePlayer(final int id) {
        super(id);
    }

    private List<Goods> getListOfSortedItems() {
        List<Goods> temp = new LinkedList<Goods>();
        for (Goods item : cards.goodsSet()) {
            int n = cards.getCount(item);
            while (n-- != 0) {
                temp.add(item);
            }
        }
        temp.sort((Goods a, Goods b) -> {
            if (b.getProfit() == a.getProfit()) {
                return b.getId() - a.getId();
            }
            return b.getProfit() - a.getProfit(); });
        return temp;
        }

    private boolean bribeStrategyForSack() {
        if (money <= Constants.MINCOINS) {
            return false;
        }
        int balanceTemp = money, cnt = 0;
        int illegalCount = 0;
        ListIterator<Goods> it = getListOfSortedItems().listIterator();
        while (it.hasNext() && cnt < Constants.MAXCARDSINSACK) {
            Goods item = it.next();
            if (item.getType() == GoodsType.Legal && illegalCount == 0) {
                return false;
            }
            if (item.getPenalty() < balanceTemp) {
                if (item.getType() == GoodsType.Illegal) {
                    illegalCount++;
                }
                sack.add(item);
                cards.removeCard(item);
                balanceTemp -= item.getPenalty();
            }
            cnt++;
        }

        if (illegalCount == 0) {
            return false;
        }
        int bribe = illegalCount > 2 ? Constants.GREATERBRIBE : Constants.SMALLERBRIBE;
        setBribeForSack(bribe);
        declaration.setDeclaration(cnt, Constants.APPLE);
        return true;
    }

    /**
     * Creeaza sacul dupa stategia bribe.
     */
    public void createSack() {
        boolean worked = bribeStrategyForSack();
        if (!worked) {
            super.baseStrategyForSack();
        }
    }

    /**
     * Inspecteaza sacul dupa strategia Bribe.
     */
    public void inspectSack() {
        Player[] players = Game.getPlayers();
        int idRight = id + 1 < players.length ? id + 1 : 0;
        int idLeft = id - 1 > -1 ? id - 1 : players.length - 1;
        if (money >= Constants.MINCOINSFORINSPECT) {
            controlPlayer(players[idLeft]);
        }
        if (idRight != idLeft && money >= Constants.MINCOINSFORINSPECT) {
            controlPlayer(players[idRight]);
        }
        for (int i = 0; i < players.length; i++) {
            if (i != idLeft && i != idRight && players[i].getDeclaration().isOfferingBribe()) {
                takeBribeFrom(players[i]);
            }
        }
    }

    /**
     *Returneaza strategia pe care o are player-ul.
     * @return Strategia player-ului.
     */
    public String toString() {
        return "BRIBED";
    }
}
