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
        //esercizi.forEach(e -> System.out.println(e.getNome()));
        //System.out.println("");
        //sessione.forEach(e -> System.out.println(e.getNome()));
        // System.out.println("\n\n");
        // esercizi.forEach(s -> System.out.println(s.getNome()));
        gestioneSuperSerie();
        // System.out.println("\n\n");
        // esercizi.forEach(s -> System.out.println(s.getNome()));
        // System.out.println("\n\n");
        //System.out.println(sessione);
//        System.out.println(esercizi);
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

    public SessionePush(SessionePush s) {
        this(s.getData(), s.getSessione());
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

        //sessione.forEach(e -> System.out.println(e.getNome()));
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
        //sessione.forEach(e -> System.out.println(e.getNome()));
    }

}
