package com.tema1.player.types;

import com.tema1.common.Constants;
import com.tema1.game.Game;
import com.tema1.player.model.Player;

public class GreedyPlayer extends BasicPlayer {
    public GreedyPlayer(final int id) {
        super(id);
    }

    /**
     * Creeaza sacul dupa strategia greedy.
     */
    public void createSack() {
        super.baseStrategyForSack();
        if (sack.size() < Constants.MAXCARDSINSACK && Game.getRound() % 2 == 0) {
            super.addIllegalGood();
        }
    }

    /**
     * Inspecteaza sacul.
     */
    public void inspectSack() {
        Player[] players = Game.getPlayers();
        for (int id = 0; id < players.length; id++) {
            if (id != super.id) {
                if (players[id].getDeclaration().isOfferingBribe()) {
                    takeBribeFrom(players[id]);
                } else if (super.money >= Constants.MINCOINSFORINSPECT) {
                    controlPlayer(players[id]);
                }
            }
        }
    }

    /**
     * Returneaza strategia pe care o are player-ul.
     * @return Strategia player-ului.
     */
    public String toString() {
        return "GREEDY";
    }
}
