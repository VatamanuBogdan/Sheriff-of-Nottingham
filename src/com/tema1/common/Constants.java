package com.tema1.common;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;

public final class Constants {
    private Constants() {
    }
    public static final Goods APPLE = GoodsFactory.getInstance().getGoodsById(0);
    public static final int STARTMONEY = 80;
    public static final int MAXCARDS = 10;
    public static final int MAXCARDSINSACK = 8;
    public static final int MINCOINSFORINSPECT = 16;
    public static final int MINCOINS = 5;
    public static final int SMALLERBRIBE = 5;
    public static final int GREATERBRIBE = 10;
}
