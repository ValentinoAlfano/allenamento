/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmaallenamentofx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
/**
 *
 * @author Giuseppe
 */
public class EsercizioDAO {

    public List<Fondamentale> leggiFileFondamentali(String nomeFile) throws FileNotFoundException, IOException {
        
        List<Fondamentale> listFondamentali = new ArrayList();

        String nome;
        double peso;
        byte serieTotali;
        double incPeso;
        TipoEsercizio tipo;
        double minIncremento;

        BufferedReader reader = new BufferedReader(new FileReader(nomeFile));
        String line = reader.readLine();
        while (line != null) {

//Parsing del file di testo (Tokenizer?) e caricamento dei dati nella lista   
            StringTokenizer st = new StringTokenizer(line, ",");

            nome = st.nextToken();
            if (nome.equalsIgnoreCase("Deadlift")) {

                peso = Double.parseDouble(st.nextToken()) - 5;
            } else {
                peso = Double.parseDouble(st.nextToken()) - 2.5;
            }
            serieTotali = Byte.parseByte(st.nextToken());

            ArrayList<Serie> serie = new ArrayList(creaSerieFondamentali(serieTotali, st, peso));
            incPeso = Double.parseDouble(st.nextToken());
            tipo = rilevaEsercizio(st);
            minIncremento = Double.parseDouble(st.nextToken());

            Fondamentale esercizio = new Fondamentale();

            esercizio.setSerie(serie);
            esercizio.setInc(incPeso);
            esercizio.setMinIncremento(minIncremento);
            esercizio.setNome(nome);
            esercizio.setTipo(tipo);

            // aggiornaProgressione(esercizio);
            Fondamentale copiaEsercizio = new Fondamentale(esercizio);

            listFondamentali.add(esercizio);

            line = reader.readLine();

        }

        return listFondamentali;
    }

    public List<Accessorio> leggiFileAccessori(String nomeFile) throws FileNotFoundException, IOException {
        List<Accessorio> listAccessori = new ArrayList();

        String nome;
        double peso;
        byte serieTotali;
        double percIncPeso;
        TipoEsercizio tipo;
        double minIncremento;

        BufferedReader reader = new BufferedReader(new FileReader(nomeFile));
        String line = reader.readLine();

        while (line != null) {

            StringTokenizer st = new StringTokenizer(line, ",");

            nome = st.nextToken();
            peso = Double.parseDouble(st.nextToken());
            serieTotali = Byte.parseByte(st.nextToken());

            ArrayList<Serie> serie = new ArrayList(creaSerieAccessori(serieTotali, st, peso));
            percIncPeso = Double.parseDouble(st.nextToken());
            tipo = rilevaEsercizio(st);
            minIncremento = Double.parseDouble(st.nextToken());

            Accessorio esercizio = new Accessorio();

            esercizio.setSerie(serie);
            esercizio.setPercent(percIncPeso);
            esercizio.setMinIncremento(minIncremento);
            esercizio.setNome(nome);
            esercizio.setTipo(tipo);

            listAccessori.add(esercizio);

            line = reader.readLine();
        }
        return listAccessori;
    }

    public List<Serie> creaSerieAccessori(int n, StringTokenizer st, double peso) {

        List<Serie> listSerie = new ArrayList(n);
        Serie s;

        for (int i = 0; i < n; i++) {
            s = new Serie();
            s.setReps(st.nextToken());
            s.setPeso(peso);
            listSerie.add(s);

        }

        return listSerie;
    }

    public List<Serie> creaSerieFondamentali(int n, StringTokenizer st, double peso) {

        List<Serie> listSerie = new ArrayList(n);
        Serie s;
        int i = 0;

        while (i < n) {
            s = new Serie();
            s.setReps(st.nextToken());
            listSerie.add(s);
            i++;
        }

        i = 0;

        for (Serie se : listSerie) {
            if (i++ < 4) {
                se.setPeso(peso * Double.parseDouble(st.nextToken()));
            } else {
                se.setPeso(peso);
            }
        }

        return listSerie;
    }

    public TipoEsercizio rilevaEsercizio(StringTokenizer st) {

        TipoEsercizio tipo;

        switch (st.nextToken()) {
            case "LEGS":
                tipo = TipoEsercizio.LEGS;
                break;

            case "PULL":
                tipo = TipoEsercizio.PULL;
                break;

            case "PUSH":
                tipo = TipoEsercizio.PUSH;
                break;

            case "MISC":
                tipo = TipoEsercizio.MISC;
                break;

            default:
                tipo = null;

        }
        return tipo;
    }

    public List<Esercizio> estraiEsercizi(List<Esercizio> lista, TipoEsercizio tipo) {

        List<Esercizio> esercizi = lista.stream()
                .filter(line -> (line.getTipo().equals(tipo)))
                .collect(Collectors.toList());

        return esercizi;
    }

    public Sessione creaSessione(LocalDate data, List<Esercizio> pull, List<Esercizio> push, List<Esercizio> legs) {

        Sessione sessione = null;
        switch (Sessione.getTipoSessione()) {

            case 1:

                return new SessionePull(data, pull.stream()
                        .filter(p -> !p.getNome().equals("Iperextension"))
                        .collect(Collectors.toList()));

            case 2:

                return new SessionePush(data, push);

            case 3:
                sessione = new SessioneLegs(data, legs);
                Sessione.setTipoEsercizio((byte) 1);
        }
        return sessione;
    }

    public List<Sessione> generaSessioneSettimanale(LocalDate dataInizio, LocalDate dataFine, List<Esercizio> esercizi) {

        List<Esercizio> eserciziMisc = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.MISC));
        List<Esercizio> eserciziLegs = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.LEGS));
        List<Esercizio> eserciziPull = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.PULL));
        List<Esercizio> eserciziPush = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.PUSH));

        eserciziPull.addAll(eserciziMisc);
        eserciziLegs.addAll(eserciziMisc);

        LocalDate dataInc;

        dataInc = dataInizio.plusWeeks(1);
        List<Sessione> sessione = new ArrayList();
        List<Esercizio> accessori = esercizi.stream()
                .filter(e -> e instanceof Accessorio)
                .collect(Collectors.toList());
        
          Sessione stampaProva = null;
        for (LocalDate date = dataInizio; date.isBefore(dataFine.plusDays(1)); date = date.plusDays(1)) {
//            eserciziPush.forEach(s -> System.out.println(s.getNome()));
//            System.out.println("Size: "+eserciziPush.size());
            if (dataInc.equals(date)) {

                aggiornaProgressione(accessori);

//                   for (Esercizio e : esercizi.stream()
//                        .filter(e -> e instanceof Accessorio)
//                        .collect(Collectors.toList())) {
//
//                    System.out.println(e.getNome());
//                    for (Serie serie : e.getSerie()) {
//                        System.out.println(serie.getPeso() + " " + serie.getReps());
//
//                    }
//                }
//                aggiornaProgressione(eserciziPush);
//                aggiornaProgressione(eserciziPull);
//                aggiornaProgressione(eserciziLegs);
                dataInc = dataInc.plusWeeks(1);
            }

            if (date.getDayOfWeek().equals(DayOfWeek.THURSDAY)
                    || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                continue;
            }
          
            sessione.add(stampaProva = creaSessione(date, eserciziPull, eserciziPush, eserciziLegs));

//            System.out.println(stampaProva.getData());
//
//            stampaProva.getSessione().stream()
//                    
//                    .map((e) -> {
//                        System.out.println(e.getNome());
//                        return e;
//                    }).forEachOrdered((e) -> {
//                e.getSerie().forEach((s) -> {
//                    System.out.println("Peso: " + s.getPeso() + " Reps: " + s.getReps());
//                });
//            });

            
            
                System.out.println("");
            System.out.println(stampaProva.getData());
            for (Esercizio e : stampaProva.getSessione()) {
                System.out.println("");
                if (e instanceof Accessorio) {
                    
                    System.out.print(e.getNome() + ": ");
                    System.out.print(" " + GestioneAllenamento.DF.format(e.getSerie().get(0).getPeso()) + " " + e.getSerie().size() + "x" + e.getSerie().get(0).getReps() + " ");

                } else {
                    System.out.print(e.getNome() + ": ");
                    for (Serie serie : e.getSerie()) {
                        System.out.print(GestioneAllenamento.DF.format(serie.getPeso()) + " " + serie.getReps() + " | ");
                    }
                }
            }
        }

//        List<Sessione> sessioneFiltrata = sessione.stream()
//                .filter(
//                        s
//                        -> s.getData()
//                                .isAfter(LocalDate.of(2018, 10, 10)) && s.getData().isBefore(LocalDate.of(2018, 10, 21))
//                )
//                .collect(Collectors.toList());

       
        

        
        return sessione;
    }

    public static List<Esercizio> copiaEsercizi(List<Esercizio> esercizi) {

        List<Esercizio> eserciziCopy = new ArrayList();

        esercizi.forEach(e -> {
            if (e instanceof Fondamentale) {
                eserciziCopy.add(new Fondamentale(e));
            } else {
                eserciziCopy.add(new Accessorio(e));
            }
        }
        );

        return eserciziCopy;
    }

    public void aggiornaProgressione(List<Esercizio> esercizi) {

        Esercizio legPress = new Accessorio(esercizi.stream()
                .filter(customer -> "Leg press".equalsIgnoreCase(customer.getNome()))
                .findAny()
                .orElse(null));
        
        for (Esercizio e : esercizi) {
            switch (e.getNome()) {

                case "Incline Crunch":
                    e.getSerie().forEach((s) -> {
                        s.setReps(Byte.toString(((byte) (Byte.parseByte(s.getReps()) + 1))));
                    });
                    break;

                case "Hanging Leg Raises":

                    e.getSerie().forEach((s) -> {
                        s.setReps(Byte.toString(((byte) (Byte.parseByte(s.getReps()) + 1))));

                    });
                    break;

                case "Pullups":

                    if (legPress.getSerie().get(1).getReps().equals("12")) {
                        e.getSerie().forEach((s) -> {
                            s.setReps(Byte.toString(((byte) (Byte.parseByte(s.getReps()) + 1))));

                        });
                    }
                    break;

                case "Chinups":

                    if (legPress.getSerie().get(1).getReps().equals("12")) {
                        e.getSerie().forEach((s) -> {
                            s.setReps(Byte.toString(((byte) (Byte.parseByte(s.getReps()) + 1))));

                        });
                    }
                    break;

                default:
                    if (e instanceof Accessorio) {
                        if (e.getSerie().get(1).getReps().equals("12")) {
                            e.getSerie().forEach((s) -> {
                                double value = s.getPeso() + s.getPeso() * ((Accessorio) e).getPercent();
                                s.setReps("8");
                                s.setPeso(Math.round(value / e.getMinIncremento()) * e.getMinIncremento());
                            });
                        } else {
                            e.getSerie().forEach((s) -> {
                                s.setReps(Byte.toString(((byte) (Byte.parseByte(s.getReps()) + 2))));
                            });
                        }
                    }

            }
        }
    }

    public static void aggiornaProgressione(Fondamentale f) {

        int i = 0;
        double perc = 0.3, peso = f.getSerie().get(f.getSerie().size() - 1).getPeso() + f.getInc();
        for (Serie se : f.getSerie()) {
            if (i++ < 4) {
                se.setPeso(Math.round((peso * perc) / f.getMinIncremento()) * f.getMinIncremento()); //Math.round(value / factor) * factor;
                perc += 0.2;
            } else {
                se.setPeso(Math.round(peso / f.getMinIncremento()) * f.getMinIncremento());
            }

        }
    }

    public void stampaSessioni(String nomeFile, List<Sessione> sessioni) throws FileNotFoundException, IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile));
        int i;

        for (Sessione s : sessioni) {
            i = sessioni.indexOf(s);
            if (i >= sessioni.size() - 2 || i % 3 != 0) {
                continue;
            }
            writer.write("Sessione n." + (i + 1) + " Data: " + s.getData().toString() + "\t\t"
                    + "Sessione n." + (i + 2) + " Data: " + sessioni.get(i + 1).getData().toString() + "\t\t"
                    + "Sessione n." + (i + 3) + " Data: " + sessioni.get(i + 2).getData().toString() + "\t\t\n");
            writer.newLine();
        }

        writer.flush();
    }

}
