package com.tema1.player.types;

import com.tema1.common.Constants;
import com.tema1.game.Game;
import com.tema1.player.model.Player;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;

public class BasicPlayer extends Player {

    public BasicPlayer(final int id) {
        super(id);
    }

    private boolean isBetter(final Goods current, final Goods selected) {
        if (current.getType() == GoodsType.Illegal) {
            return false;
        }
        if (selected == null) {
            return true;
        }
        int selectedCount = cards.getCount(selected);
        int currentCount = cards.getCount(current);
        if (selectedCount < currentCount) {
            return true;
        }
        if (selectedCount == currentCount) {
            if (selected.getProfit() < current.getProfit()) {
                return true;
            }
            return selected.getProfit() == current.getProfit()
                                        && current.getId() > selected.getId();
        }
        return false;
    }

    /**
     * Adauga in sacul player-ului bunurile cu cea mai mare frecventa.
     */
    protected void addLegalGoods() {
        Goods best = null;
        for (Goods good : cards.goodsSet()) {
            if (isBetter(good, best)) {
                best = good;
            }
        }
        if (best != null) {
            int count = Math.min(cards.getCount(best), Constants.MAXCARDSINSACK);
            while (count-- != 0) {
                sack.addLast(best);
                cards.removeCard(best);
            }
        }
    }

    /**
     * Extrage un bun ilegal din cartile player-ului si il adauga in sacul acestuia.
     */
    protected void addIllegalGood() {
        Goods best = null;
        for (Goods good : cards.goodsSet()) {
            if (good.getType() == GoodsType.Illegal
                    && (best == null || best.getProfit() < good.getProfit())) {
                best = good;
            }
        }
        if (best != null && best.getPenalty() <= money) {
            sack.addLast(best);
            cards.removeCard(best);
        }
    }

    /**
     * Aplica strategia de baza din enunt pentru creearea sacului.
     */
    protected void baseStrategyForSack() {
        addLegalGoods();
        if (sack.isEmpty()) {
            addIllegalGood();
            if (!sack.isEmpty()) {
                declaration.setDeclaration(1, Constants.APPLE);
            } else {
                declaration.setDeclaration(0, Constants.APPLE);
            }
        } else {
            declaration.setDeclaration(sack.size(), sack.getFirst());
        }
    }

    /**
     * Aplica strategia de baza pentru inspectie.
     */
    protected void baseStrategyForInspect() {
        Player[] players = Game.getPlayers();
        for (int id = 0; id < players.length && super.money >= Constants.MINCOINSFORINSPECT; id++) {
            if (id != super.id) {
                controlPlayer(players[id]);
            }
        }
    }

    /**
     * Creeaza sacul player-ului.
     */
    public void createSack() {
        baseStrategyForSack();
    }

    /**
     * Inspecteaza bunurile celorlalti jucatori.
     */
    public void inspectSack() {
        baseStrategyForInspect();
    }

    /**
     * Returneaza strategia pe care o are player-ul.
     * @return Stategia player-ului.
     */
    public String toString() {
        return "BASIC";
    }
}
