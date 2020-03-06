package com.tema1.main;

import com.tema1.game.Game;
import com.tema1.player.model.Player;
import java.util.List;

public final class Main {
    private Main() {
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        Game.initGame(gameInput.getRounds(), gameInput.getPlayerNames(), gameInput.getAssetIds());
        List<Player> result = Game.runGame();
        for (Player player : result) {
            System.out.println(player.getId() + " " + player.toString() + " " + player.getScore());
        }
    }
}
