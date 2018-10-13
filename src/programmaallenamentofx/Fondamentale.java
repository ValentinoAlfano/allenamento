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
public class Fondamentale extends Esercizio {

    private double inc;
    @Override
    void aggiornaProgressione() {
        
               getSerie().forEach((s) -> {
                    s.setPeso(s.getPeso() + inc);
                });
        }

    public Fondamentale(Esercizio e) {
        super(e);
       
    }

    public Fondamentale() {
        
    }

    public double getInc() {
        return inc;
    }

    public void setInc(double inc) {
        this.inc = inc;
    }
    
}
