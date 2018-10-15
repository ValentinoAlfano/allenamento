/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmaallenamentofx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

            StringTokenizer st = new StringTokenizer(line, ",");

            nome = st.nextToken();
            if (nome.equalsIgnoreCase("Deadlift")) {

                peso = Double.parseDouble(st.nextToken());
            } else {
                peso = Double.parseDouble(st.nextToken());
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

    public List<Sessione> generaSessioni(LocalDate dataInizio, LocalDate dataFine, List<Esercizio> esercizi) {

        List<Esercizio> eserciziMisc = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.MISC));
        List<Esercizio> eserciziLegs = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.LEGS));
        List<Esercizio> eserciziPull = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.PULL));
        List<Esercizio> eserciziPush = new ArrayList(estraiEsercizi(esercizi, TipoEsercizio.PUSH));

        eserciziPull.addAll(eserciziMisc);
        eserciziLegs.addAll(eserciziMisc);

        LocalDate dataInc;
        List<LocalDate> dateDeload = new ArrayList();
        dateDeload.add(LocalDate.of(2018, Month.DECEMBER, 4));
        dateDeload.add(LocalDate.of(2019, Month.MARCH, 5));
        dateDeload.add(LocalDate.of(2019, Month.JUNE, 6));
        dateDeload.add(LocalDate.of(2019, Month.JULY, 16));

        dataInc = dataInizio.plusWeeks(1);
        List<Sessione> sessione = new ArrayList();
        List<Esercizio> accessori = esercizi.stream()
                .filter(e -> e instanceof Accessorio)
                .collect(Collectors.toList());

        for (LocalDate date = dataInizio; date.isBefore(dataFine.plusDays(1)); date = date.plusDays(1)) {

            if (dataInc.equals(date)) {
                aggiornaProgressione(accessori);
                dataInc = dataInc.plusWeeks(1);
            }

            if (dateDeload.contains(date)) {
                deloadEsercizi(esercizi);

            }

            if (date.getDayOfWeek().equals(DayOfWeek.THURSDAY)
                    || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                continue;
            }

            sessione.add(creaSessione(date, eserciziPull, eserciziPush, eserciziLegs));

        }
        return sessione;
    }

    public static List<Esercizio> copiaEsercizi(List<Esercizio> esercizi) {

        List<Esercizio> eserciziCopy = new ArrayList();

        esercizi.forEach(e -> {

            if (e instanceof Fondamentale) {
                Fondamentale f = new Fondamentale(e);
                f.setSerie(copiaSerie(e.getSerie()));
                eserciziCopy.add(f);
            } else {
                Accessorio a = new Accessorio(e);
                a.setSerie(copiaSerie(e.getSerie()));
                eserciziCopy.add(a);
            }
        });

        return eserciziCopy;
    }

    public static ArrayList<Serie> copiaSerie(ArrayList<Serie> serie) {

        ArrayList<Serie> serieCopy = new ArrayList();

        serie.forEach(s -> serieCopy.add(new Serie(s)));

        return serieCopy;
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
                se.setPeso(Math.round((peso * perc) / f.getMinIncremento()) * f.getMinIncremento());
                perc += 0.2;
            } else {
                se.setPeso(Math.round(peso / f.getMinIncremento()) * f.getMinIncremento());
            }

        }
    }

    public void stampaSessioni(List<Sessione> sessioni, LocalDate dataInizio, LocalDate dataFine) {

        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.of("Europe/Rome")).withLocale(Locale.ITALY);

        List<Sessione> sessioneFiltrata = sessioni.stream()
                .filter(s -> s.getData()
                .isAfter(dataInizio.minusDays(1)) && s.getData().isBefore(dataFine.plusDays(1)))
                .collect(Collectors.toList());

        for (Sessione s : sessioneFiltrata) {
            System.out.println("");
            System.out.println(s.getData().format(f2));
            for (Esercizio e : s.getSessione()) {
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
            System.out.println("");
        }
    }

    public void deloadEsercizi(List<Esercizio> esercizi) {

        for (Esercizio e : esercizi) {
            if (e instanceof Accessorio) {
                ((Accessorio) e).setPercent(((Accessorio) e).getPercent()/1.7);
            }

            switch (e.getNome()) {

                case "Chinups":

                    e.getSerie().forEach((s) -> {
                        s.setReps(Byte.toString(((byte) Math.round(Byte.parseByte(s.getReps()) * 0.7))));
                    });
                    break;

                case "Pullups":
                    e.getSerie().forEach((s) -> {
                        s.setReps(Byte.toString(((byte) Math.round(Byte.parseByte(s.getReps()) * 0.7))));
                    });
                    break;

                case "Hanging Leg Raises":
                    e.getSerie().forEach((s) -> {
                        s.setReps(Byte.toString(((byte) Math.round(Byte.parseByte(s.getReps()) * 0.7))));
                    });
                    break;

                case "Incline Crunch":
                    e.getSerie().forEach((s) -> {
                        s.setReps(Byte.toString(((byte) Math.round(Byte.parseByte(s.getReps()) * 0.7))));
                    });
                    break;

                default:
                    e.getSerie().forEach((s) -> {
                        s.setPeso(Math.round(s.getPeso() * 0.7 / e.getMinIncremento()) * e.getMinIncremento());
                    });

            }
        }

    }

}
