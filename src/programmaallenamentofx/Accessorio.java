/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmaallenamentofx;

/**
 *
 * @author Giuseppe
 */
public class Accessorio extends Esercizio {

    @Override
    void aggiornaProgressione() {

    }

    private double percent;

    public Accessorio() {
    }

    public Accessorio(Esercizio e) {
        super(e);

    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

}
