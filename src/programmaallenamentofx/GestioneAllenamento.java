/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmaallenamentofx;

import java.io.IOException;
import java.text.DecimalFormat;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class GestioneAllenamento {

    final public static DecimalFormat DF = new DecimalFormat("####0.00");
    final static String UTENTE = "C";

    public static void main(String... args) throws IOException {

        String nomeFile = "";

        switch (UTENTE) {
            case "C":
                nomeFile = "C:\\Users\\Valentino\\Desktop\\AccessoriC.txt";
                break;
            case "A":
                nomeFile = "C:\\Users\\Valentino\\Desktop\\AccessoriA.txt";
                break;
        }

        EsercizioDAO allenamento = new EsercizioDAO();

        List<Esercizio> accessori = new ArrayList(allenamento.leggiFileAccessori(nomeFile));

        switch (UTENTE) {
            case "C":
                nomeFile = "C:\\Users\\Valentino\\Desktop\\FondamentaliC.txt";
                break;
            case "A":
                nomeFile = "C:\\Users\\Valentino\\Desktop\\FondamentaliA.txt";
                break;
        }

        List<Esercizio> fondamentali = new ArrayList(allenamento.leggiFileFondamentali(nomeFile));
//          for (Esercizio e : fondamentali) {
//                System.out.println(e.getNome() + ((Fondamentale) e).getInc());
//                for (Serie serie : e.getSerie()) {
//                    System.out.println(serie.getPeso() + " " + serie.getReps());
//
//                }
//            }
        List<Esercizio> esercizi = new ArrayList(fondamentali);
        esercizi.addAll(accessori);
//        esercizi.forEach(e -> System.out.println(e.getNome()));
//        System.out.println("\n\nSize: " + esercizi.size() + "\n\n");
        LocalDate dataInizio = LocalDate.of(2018, Month.SEPTEMBER, 03);
        LocalDate dataFine = LocalDate.of(2018, Month.DECEMBER, 03);

        List<Sessione> sessioni = new ArrayList(allenamento
                .generaSessioneSettimanale(dataInizio, dataFine, esercizi));

//        nomeFile="C:\\Users\\Giuseppe\\Desktop\\StampaSessioni.txt";
//        allenamento.stampaSessioni(nomeFile, sessioni);
    }

}
