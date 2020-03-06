package com.tema1.game;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.goods.IllegalGoods;
import com.tema1.goods.LegalGoods;
import com.tema1.player.model.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Score {
    private static final Map<LegalGoods, GoodRank> RANKING = new HashMap<>();

    private Score() {

    }

    private static class GoodRank {
        private int kingCount;
        private int queenCount;
        private Player king;
        private Player queen;
        GoodRank(final int kingCount, final int queenCount) {
            this.kingCount = kingCount;
            this.queenCount = queenCount;
        }
    }

    private static void computeKingAndQueenRanking(final Player[] players) {
        Map<Goods, Integer> stand;
        GoodRank rank;
        for (var player : players) {
            stand = player.getStand();
            for (var x : stand.entrySet()) {
                Goods item = x.getKey();
                if (item.getType() != GoodsType.Illegal) {
                    int count = x.getValue();
                    if (!RANKING.containsKey(item)) {
                        rank = new GoodRank(count, 0);
                        rank.king = player;
                        RANKING.put((LegalGoods) item, rank);

                    } else {
                        rank = RANKING.get(item);
                        if (count > rank.kingCount) {
                            rank.queenCount = rank.kingCount;
                            rank.kingCount = count;
                            rank.queen = rank.king;
                            rank.king = player;
                        } else if (count > rank.queenCount) {
                            rank.queenCount = count;
                            rank.queen = player;
                        }
                    }
                }
            }
        }
    }

    private static void addItemsProfit(final Player player) {
        for (Map.Entry<Goods, Integer> x : player.getStand().entrySet()) {
            player.addScore(x.getKey().getProfit() * x.getValue());
        }
    }

    private static void addIllegalBonus(final Map<Goods, Integer> stand) {
        Map<Goods, Integer> temp = new HashMap<>();
        for (var x : stand.entrySet()) {
            Goods item = x.getKey();
            if (item.getType() == GoodsType.Illegal) {
                IllegalGoods iGood = (IllegalGoods) item;
                for (var y : iGood.getIllegalBonus().entrySet()) {
                    temp.put(y.getKey(), temp.getOrDefault(y.getKey(), 0)
                                                                + y.getValue() * x.getValue());
                }
            }
        }
        for (var x : temp.entrySet()) {
            stand.put(x.getKey(), stand.getOrDefault(x.getKey(), 0) + x.getValue());
        }
    }

    private static void addKingQueenBonus() {
        for (var x : RANKING.entrySet()) {
            LegalGoods item = x.getKey();
            GoodRank z = x.getValue();
            if (z.king != null) {
                z.king.addScore(item.getKingBonus());
            }
            if (z.queen != null) {
                z.queen.addScore(item.getQueenBonus());
            }
        }
    }

    private static void computeScore(final Player[] players) {
        for (Player player : players) {
            addIllegalBonus(player.getStand());
        }
        computeKingAndQueenRanking(players);
        addKingQueenBonus();
        for (Player player : players) {
            player.addScore(player.getMoney());
            addItemsProfit(player);
        }
    }

    public static List<Player> computeResults(final Player[] players) {
        List<Player> result = Arrays.asList(players);
        RANKING.clear();
        computeScore(players);
        result.sort((Player a, Player b) -> {
                                    return b.getScore() - a.getScore(); });
        return result;
    }
}
