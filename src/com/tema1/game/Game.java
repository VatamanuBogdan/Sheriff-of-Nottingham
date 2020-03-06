package com.tema1.game;

import java.util.List;
import com.tema1.player.model.Player;
import com.tema1.boardElements.Deck;
import com.tema1.player.types.BasicPlayer;
import com.tema1.player.types.BribePlayer;
import com.tema1.player.types.GreedyPlayer;

public final class Game {
    private static Game instance = new Game();
    private int roundsNum;
    private int playerNum;
    private Player[] players;
    private Deck gameDeck;
    private int round;
    private int sheriffId;

    private Game() { }

    public static void initGame(final int roundsNum, final List<String> playersType,
                                                                    final List<Integer> cards) {
        instance.roundsNum = roundsNum;
        instance.playerNum = playersType.size();
        instance.players = new Player[playersType.size()];
        int id = 0;
        for (String type : playersType) {
            instance.players[id] = instance.playerParser(type, id);
            id++;
        }
        instance.gameDeck = new Deck(cards);
    }

    public static List<Player> runGame() {
        return instance.run();
    }

    public static Deck getDeck() {
        return instance.gameDeck;
    }

    public static Player[] getPlayers() {
        return instance.players;
    }

    public static int getRound() {
        return instance.round;
    }

    private Player playerParser(final String playerType, final int id) {
        if (playerType.contentEquals("basic")) {
            return new BasicPlayer(id);
        } else if (playerType.contentEquals("bribed")) {
            return new BribePlayer(id);
        } else if (playerType.contentEquals("greedy")) {
            return new GreedyPlayer(id);
        }
        return null;
    }

    private void createSackStage() {
        for (int i = 0; i < playerNum; i++) {
            if (i != sheriffId) {
                players[i].createSack();
            }
        }
    }

    private void declareInspectStage() {
        players[sheriffId].inspectSack();
    }

    private void reloadCardsStage() {
        for (int i = 0; i < playerNum; i++) {
            if (i != sheriffId) {
                players[i].reloadCards();
            }
        }
    }

    private void loadGoodsStage() {
        for (int i = 0; i < playerNum; i++) {
            if (i != sheriffId) {
                players[i].putGoodsOnStand();
            }
        }
    }

    public List<Player> run() {
        for (round = 1; round <= roundsNum; round++) {
            for (sheriffId = 0; sheriffId < playerNum; sheriffId++) {
                reloadCardsStage();
                createSackStage();
                declareInspectStage();
                loadGoodsStage();
            }
        }
        return Score.computeResults(players);
    }
}
