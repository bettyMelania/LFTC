import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class Analizator {
    public static boolean analiza(Tabel tabel, String secventa,Gramatica g) {
        Queue<String> stivaIntrare=new LinkedBlockingDeque<>();

        stivaIntrare.addAll(Arrays.asList(secventa.split(" ")));
        stivaIntrare.add("$");

        Stack<String> stivaLucru=new Stack<>();
        stivaLucru.push("$");
        stivaLucru.push(g.getSimbolStart());


        List<Integer> bandaDeIesire=new ArrayList<>();
        List<String> bandaDeIesireProductii=new ArrayList<>();

        Set<TableElement> elems= tabel.getVal();

        while(true){
            String headIntrare=stivaIntrare.peek();
            String headLucru=stivaLucru.peek();
            if(headIntrare.isEmpty()){
                stivaIntrare.poll();
                continue;
            }

            if(headIntrare.equals(headLucru) && headIntrare.equals("$")){
                System.out.println("Secventa valida");
                printBandaI(bandaDeIesire);
                printBanda(bandaDeIesireProductii);
                creareTabelRelatii(bandaDeIesireProductii,g);
                return true;
            }

            if(!g.getTerminale().contains("\""+headIntrare+"\"") && !headIntrare.equals("$")){
                System.out.println("Eroare la: "+headIntrare);
                return false;
            }

            for(TableElement tE:elems){
                if(tE.getPozTabel().getKey().equals(headLucru) && tE.getPozTabel().getValue().equals("\""+headIntrare+"\"")){
                    if(tE.getValTabel().getKey().equals("acc")){
                        System.out.println("Secventa valida");
                        printBandaI(bandaDeIesire);
                        printBanda(bandaDeIesireProductii);
                        creareTabelRelatii(bandaDeIesireProductii,g);
                        return true;
                    }

                    if(tE.getValTabel().getKey().equals("pop")){
                        stivaLucru.pop();
                        stivaIntrare.poll();
                        break;
                    }

                    if(tE.getValTabel().getKey().equals("err")){
                        System.out.println("Eroare la: "+headIntrare);
                        return false;
                    }
                    else{
                        String productie=tE.getValTabel().getKey();
                        int nrProductie=tE.getValTabel().getValue();
                        stivaLucru.pop();
                        if(!productie.equals("~")) {
                            String[] els = productie.split(" ");

                            for (int i = els.length - 1; i >= 0; i--)
                                stivaLucru.add(els[i]);
                        }
                        bandaDeIesire.add(nrProductie);
                        bandaDeIesireProductii.add(productie);

                    }
                    break;
                }
            }


        }

    }

    private static void creareTabelRelatii(List<String> bandaDeIesireProductii,Gramatica g) {
        List<RelatiiTableElement> table=new ArrayList<>();
        int index=1;
        int currentProd=0;

        RelatiiTableElement tableEl=new RelatiiTableElement(index,g.getSimbolStart(),-1,-1);
        table.add(tableEl);
        List<RelatiiTableElement> linie=new ArrayList<>();
        linie.add(tableEl);


        int indexAnterior=-1;

        Stack<RelatiiTableElement> stack=new Stack<>();
        stack.add(tableEl);
        //parcurgere in adancime
        while(!stack.isEmpty()){
            RelatiiTableElement elCurent=stack.pop();
            if(g.getNeterminale().contains(elCurent.getElement())){
                indexAnterior=-1;
                String[] productie=bandaDeIesireProductii.get(currentProd).split(" ");
                List<RelatiiTableElement> toADD=new ArrayList<>();
                for(int i=0;i<productie.length;i++){
                    String elProd=productie[i];
                    index++;
                    RelatiiTableElement tEl=new RelatiiTableElement(index,elProd,elCurent.getIndex(),indexAnterior);
                    toADD.add(tEl);
                    indexAnterior=index;
                    table.add(tEl);
                }
                for(int j=toADD.size()-1;j>=0;j--){
                    if(g.getNeterminale().contains(toADD.get(j).getElement()))
                        stack.add(toADD.get(j));
                }
                currentProd++;

            }
        }
        printRelatiiTabel(table);
    }

    private static void printRelatiiTabel(List<RelatiiTableElement> table) {
        System.out.println(" Tabel de relatii: ");
        for(RelatiiTableElement Tel:table){
            System.out.println("i: "+Tel.getIndex()+" el: "+Tel.getElement()+" parinte: "+Tel.getTata()+" frate: "+Tel.getFrateStanga());
        }

    }

    private static void printBanda(List<String> bandaDeIesire) {
        System.out.println(" ");
        System.out.print("Banda de iesire: ");
        for (String p:bandaDeIesire){
            System.out.print(p+" | ");
        }
    }

    private static void printBandaI(List<Integer> bandaDeIesire) {
        System.out.println(" ");
        System.out.print("Banda de iesire: ");
        for (int nr:bandaDeIesire){
            System.out.print(nr+" ");
        }
    }
}
