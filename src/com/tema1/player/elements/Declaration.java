package com.tema1.player.elements;

import com.tema1.goods.Goods;

public class Declaration {
    private int numberOfGoods;
    private Goods declaredGood;
    private int bribe;
    public Declaration() {
        numberOfGoods = 0;
        declaredGood = null;
        bribe = 0;
    }

    /**
     * Returneaza numarul de bunuri care au fost declarate.
     * @return Numarul de bunuri care au fost declarate.
     */
    public int getNumberOfGoods() {
        return numberOfGoods;
    }

    /**
     * Returneaza bunul declarat.
     * @return Bunul declarat.
     */
    public Goods getDeclaredGood() {
        return declaredGood;
    }

    /**
     * Verifica daca player-ul este dispus sa ofere mita.
     * @return true == ofera mita | false == nu ofera.
     */
    public boolean isOfferingBribe() {
        return bribe != 0;
    }

    /**
     * Seteaza mita care va fi oferita de player.
     * @param bribe Valoarea mitei.
     */
    public void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    /**
     * Seteaza mita la valoare 0 si returneaza valoarea acesteia.
     * @return Mita care a fost respinsa de sheriff.
     */
    public int cleanBribe() {
        int temp = bribe;
        bribe = 0;
        return temp;
    }

    /**
     * Se creeaza declaratia care va fi zisa de catre player sheriff-ului.
     * @param number Numarul de bunuri declarat.
     * @param good Tipul de bun declarat.
     */
    public void setDeclaration(final int number, final Goods good) {
        numberOfGoods = number;
        declaredGood = good;
    }
}
