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
                nomeFile = "/home/balint/AccessoriC.txt";
                break;
            case "A":
                nomeFile = "/home/balint/AccessoriA.txt";
                break;
        }

        EsercizioDAO allenamento = new EsercizioDAO();

        List<Esercizio> accessori = new ArrayList(allenamento.leggiFileAccessori(nomeFile));

        switch (UTENTE) {
            case "C":
                nomeFile = "/home/balint/FondamentaliC.txt";
                break;
            case "A":
                nomeFile = "/home/balint/FondamentaliA.txt";
                break;
        }

        List<Esercizio> fondamentali = new ArrayList(allenamento.leggiFileFondamentali(nomeFile));

        List<Esercizio> esercizi = new ArrayList(fondamentali);
        esercizi.addAll(accessori);

        LocalDate dataInizio = LocalDate.of(2018, Month.SEPTEMBER, 03);
        LocalDate dataFine = LocalDate.of(2019, Month.JULY, 31);

        List<Sessione> sessioni = new ArrayList(allenamento
                .generaSessioni(dataInizio, dataFine, esercizi));

        
  // Inserire qui le date per avere la stampa delle sessioni filtrate
        LocalDate dataInizioFiltro = LocalDate.of(2018, Month.OCTOBER, 29);
        LocalDate dataFineFiltro = LocalDate.of(2018, Month.DECEMBER, 4);
        
        allenamento.stampaSessioni(sessioni,dataInizioFiltro,dataFineFiltro);
    }

}
