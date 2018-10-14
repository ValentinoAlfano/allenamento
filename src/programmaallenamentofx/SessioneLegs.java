/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmaallenamentofx;

import java.time.LocalDate;

import java.util.List;


/**
 *
 * @author Giuseppe
 */
public class SessioneLegs extends Sessione {
    
    public SessioneLegs(LocalDate data, List<Esercizio> esercizi) {
        super(data, esercizi);
        
        Esercizio es = esercizi.stream()
                .filter(customer -> "Squat".equalsIgnoreCase(customer.getNome()))
                .findAny()
                .orElse(null);
        
        EsercizioDAO.aggiornaProgressione((Fondamentale) es);
    }
    
    public SessioneLegs(Sessione s) {
    super(s);
    }
    
}
