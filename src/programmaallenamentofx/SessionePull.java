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
public class SessionePull extends Sessione {

    private static byte variante;

    public SessionePull(LocalDate data, List<Esercizio> esercizi) {
        super(data, esercizi);

        switch (variante) {
            case 0:
                getSessione().removeIf(p
                        -> (p.getNome().equalsIgnoreCase("Barbell Row")
                        || p.getNome().equalsIgnoreCase("Pullups")
                        || p.getNome().equalsIgnoreCase("Chinups")));

                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Deadlift")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                break;
            case 1:
                getSessione().removeIf(p -> (p.getNome().equalsIgnoreCase("Deadlift")
                        || p.getNome().equalsIgnoreCase("Lat machine")
                        || p.getNome().equalsIgnoreCase("Chinups")));

                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Barbell Row")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                break;
            case 2:
                getSessione().removeIf(p -> (p.getNome().equalsIgnoreCase("Barbell Row")
                        || p.getNome().equalsIgnoreCase("Pullups")
                        || p.getNome().equalsIgnoreCase("Lat machine")));
                
                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Deadlift")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                break;
            case 3:
                getSessione().removeIf(p -> (p.getNome().equalsIgnoreCase("Deadlift")
                        || p.getNome().equalsIgnoreCase("Pullups")
                        || p.getNome().equalsIgnoreCase("Chinups")));
                
                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Barbell Row")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                break;
            case 4:
                getSessione().removeIf(p -> (p.getNome().equalsIgnoreCase("Barbell Row")
                        || p.getNome().equalsIgnoreCase("Lat machine")
                        || p.getNome().equalsIgnoreCase("Chinups")));
                
                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Deadlift")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                break;
            case 5:
                getSessione().removeIf(p -> (p.getNome().equalsIgnoreCase("Deadlift")
                        || p.getNome().equalsIgnoreCase("Pullups")
                        || p.getNome().equalsIgnoreCase("Lat machine")));
                
                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Barbell Row")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                variante = -1;
                break;

        }
        variante++;
    }

}
