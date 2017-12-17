

import javafx.util.Pair;


import java.util.*;

public class CreareTabel {



    public static Tabel creareTabel(Gramatica g, HashMap<String, Set<String>> FIRST, HashMap<String, Set<String>> FOLLOW)
    {
        Set<String> netermTermLine = new HashSet<>();netermTermLine.addAll(g.getNeterminale());
        Set<String> termColumn = g.getTerminale();
        HashMap<String,Productie> productii = g.getProductii();
        List<Triplet<String,String,Integer>> pairList = new ArrayList<>();
        int nr = 1;
        Set<TableElement> pairSet = new HashSet<>();

        netermTermLine.addAll(g.getTerminale());
        netermTermLine.add("$");
        termColumn.add("$");

        Tabel tab = new Tabel(netermTermLine,termColumn);

        for (String name: productii.keySet())
        {
            String value = productii.get(name).toString();
            value = value.replaceAll("\n", "");
            String segments[] = value.split("\\|");
            for (String segment : segments)
            {
                Triplet<String, String, Integer> newP = new Triplet<>(name, segment, nr);
                pairList.add(newP);
                nr++;
            }
        }

        for (String netermTerm : tab.getNetermTermLine()) {
            if (g.getTerminale().contains(netermTerm)) {
                for (String term : tab.getTermColumn()) {
                    if (netermTerm.compareTo(term) == 0 && netermTerm.compareTo("$") != 0 && term.compareTo("$") != 0) {
                        Pair<String, String> p1 = new Pair<>(netermTerm, term);
                        Pair<String, Integer> p2 = new Pair<>("pop", 0);
                        pairSet.add(new TableElement(p1, p2));
                    } else if (netermTerm.compareTo(term) == 0 && netermTerm.compareTo("$") == 0 && term.compareTo("$") == 0) {
                        Pair<String, String> p1 = new Pair<>(netermTerm, term);
                        Pair<String, Integer> p2 = new Pair<>("acc", 0);
                        pairSet.add(new TableElement(p1, p2));
                    }
                    else if(netermTerm.compareTo(term) != 0){
                        Pair<String, String> p1 = new Pair<>(netermTerm, term);
                        Pair<String, Integer> p2 = new Pair<>("err", 0);
                        pairSet.add(new TableElement(p1, p2));
                    }
                }
            } else {

                Set<Pair<Integer, Set<String>>> firstVal = new HashSet<>();
                Set<String> followVal = FOLLOW.get(netermTerm);
                int ok = 0;


                for (Triplet<String, String, Integer> trip : pairList)
                    if (trip.getFirst().compareTo(netermTerm) == 0)
                        if (!trip.getSecond().equals("~")) {
                            firstVal.add(new Pair<>(trip.getThird(), First.firstProductie(trip.getSecond(), FIRST, g)));
                        } else {
                            Set<String> temps = new HashSet<>();
                            temps.add("~");
                            firstVal.add(new Pair<>(trip.getThird(), temps));
                        }

                for (String term : tab.getTermColumn()) {
                    Pair<String, Integer> savedValues = new Pair<>("", 0);
                    ok = 0;
                    if (firstVal != null && followVal != null)
                    {
                        for (Pair<Integer, Set<String>> elem : firstVal)
                        {
                            for (String a : elem.getValue())
                            {
                                if (a.compareTo(term) == 0 && a.compareTo("~") != 0) {
                                    for (Triplet<String, String, Integer> trip : pairList)
                                        if (trip.getFirst().compareTo(netermTerm) == 0)
                                            if (trip.getSecond().compareTo("~") != 0)
                                                if (trip.getThird() == elem.getKey()) {
                                                    Pair<String, String> p1 = new Pair<>(netermTerm, term);
                                                    Pair<String, Integer> p2 = new Pair<>(trip.getSecond(), trip.getThird());
                                                    savedValues = p2;
                                                    pairSet.add(new TableElement(p1, p2));
                                                    ok ++;
                                                    break;
                                                }
                                } else if (a.compareTo("~") == 0)
                                {
                                    for (String b : followVal)
                                    {
                                        if (b.compareTo(term) == 0 || term.compareTo("$") == 0)
                                            for (Triplet<String, String, Integer> trip : pairList)
                                            {
                                                if (trip.getFirst().compareTo(netermTerm) == 0)
                                                    if (trip.getSecond().compareTo("~") == 0) {
                                                        Pair<String, String> p1 = new Pair<>(netermTerm, term);
                                                        Pair<String, Integer> p2 = new Pair<>(trip.getSecond(), trip.getThird());
                                                        savedValues = p2;
                                                        pairSet.add(new TableElement(p1, p2));
                                                        ok ++;
                                                        break;
                                                    }
                                            }
                                            if (ok == 1)
                                                break;
                                    }
                                }
                            }
                        }
                        if (ok == 0) {

                                Pair<String, String> p1 = new Pair<>(netermTerm, term);
                                Pair<String, Integer> p2 = new Pair<>("err", 0);
                                pairSet.add(new TableElement(p1, p2));

                            ok = 1;
                        }

                        if (ok > 1) {
                            for (Triplet<String, String, Integer> trip : pairList)
                                if (trip.getFirst().compareTo(netermTerm) == 0)
                                    if (trip.getSecond().compareTo("~") == 0) {
                                        Pair<String, String> p1 = new Pair<>(netermTerm, term);
                                        System.out.println("NU ESTE LL(1) IN NETERMINALUL " + netermTerm  + " si terminalul "+term+"  PRODUCTIILE ( " + savedValues.getKey() + "," + savedValues.getValue() + " )" + " , (" + p1.getKey() + "," + p1.getValue() + ")");
                                        return null;
                                    }

                            break;
                        }
                    }
                }
            }
        }
        tab.setVal(pairSet);

        System.out.println("TABEL");

        String[] stringuri = new String[]{"A","B","C","D","a","+","*","(",")","$"};

        for (String val: stringuri)
            for (TableElement elem : tab.getVal())
            {
                Pair<String,String> p1 = elem.getPozTabel();
                Pair<String,Integer> p2 = elem.getValTabel();
                if (p1.getKey().compareTo(val) == 0)
                    System.out.println("( " + p1.getKey() + "," + p1.getValue() + " )" + " == " + "( " + p2.getKey() + "," + p2.getValue() + " )");
            }
        return tab;
        }


}
