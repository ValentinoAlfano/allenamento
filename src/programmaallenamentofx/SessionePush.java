/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmaallenamentofx;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class SessionePush extends Sessione {

    private static byte variante;

    public SessionePush(LocalDate data, List<Esercizio> esercizi) {
        super(data, esercizi);
 
        gestioneSuperSerie();

        switch (variante) {
            
            case 0:

                getSessione().removeIf(p -> (p.getNome().equalsIgnoreCase("Bench pressA")
                        || p.getNome().equalsIgnoreCase("Overhead pressF")));

                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Bench pressF")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                break;
           
            case 1:
                
                getSessione().removeIf(p -> (p.getNome().equalsIgnoreCase("Bench pressF")
                        || p.getNome().equalsIgnoreCase("Overhead pressA")));

                esercizi.forEach(e -> {
                    if (e.getNome().equalsIgnoreCase("Overhead pressF")) {
                        EsercizioDAO.aggiornaProgressione((Fondamentale) e);
                    }
                });

                variante = -1;
                break;

        }
        variante++;
    }


    private void gestioneSuperSerie() {

        Esercizio lateralRaises;
        String[] prova = {"Overhead triceps extensions", "Triceps pushdowns"};
        int i;

        lateralRaises = getSessione().stream()
                .filter(customer -> "Lateral raises".equalsIgnoreCase(customer.getNome()))
                .findAny()
                .orElse(null);

        getSessione().removeAll(Collections.singleton(lateralRaises));
        
        for (String s : prova) {
            getSessione().add(i = getSessione()
                    .indexOf(getSessione()
                            .stream()
                            .filter(esercizio -> s.equalsIgnoreCase(esercizio.getNome()))
                            .findAny()
                            .orElse(null)) + 1,
                    lateralRaises);
            getSessione().get(i - 1).setNome(s.concat(" SS"));
        }
    }

}
