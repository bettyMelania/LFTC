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
            if(headIntrare.isEmpty() && headLucru.isEmpty()){
                System.out.println("Secventa valida");
                return true;
            }

            // pop
            if(headLucru.equals(headIntrare)){
                stivaLucru.pop();
                stivaIntrare.poll();
                continue;
            }

            if(g.getTerminale().contains(headLucru)){
                System.out.println("Eroare la: "+headIntrare);
                return false;
            }

            // head Lucru este neterminal, facem expandare

            for(TableElement tE:elems){
                if(tE.getPozTabel().getKey().equals(headLucru) && tE.getPozTabel().getValue().equals(headIntrare)){
                    if(tE.getValTabel().getKey().equals("err")){
                        System.out.println("Eroare la: "+headIntrare);
                        return false;
                    }
                    else{
                        String productie=tE.getValTabel().getKey();
                        int nrProductie=tE.getValTabel().getValue();
                        stivaLucru.pop();
                        String[] els=productie.split(" ");

                        for(int i=els.length-1;i>=0;i--)
                            stivaLucru.add(els[i]);

                        bandaDeIesire.add(nrProductie);
                        bandaDeIesireProductii.add(productie);
                    }
                    break;
                }
            }
            printBanda(bandaDeIesire);
            creareTabelRelatii(bandaDeIesireProductii,g);




        }

    }

    private static void creareTabelRelatii(List<String> bandaDeIesireProductii,Gramatica g) {
        List<RelatiiTableElement> table=new ArrayList<>();
        int index=1;
        int currentProd=0;

        RelatiiTableElement tableEl=new RelatiiTableElement(index,g.getSimbolStart(),-1,-1);
        table.add(tableEl);
        List<RelatiiTableElement> linie=new ArrayList<>(); //String-element, Integer,index
        linie.add(tableEl);


        int indexAnterior=-1;

        boolean cont=true;
        while(cont){
            cont=false;
            List<RelatiiTableElement> linieNoua=new ArrayList<>();
            for(RelatiiTableElement el:linie){
                if(g.getNeterminale().contains(el.getElement())){
                    cont=true;
                    for(String elProd:Arrays.asList(bandaDeIesireProductii.get(currentProd).split(" "))){
                        index++;
                        RelatiiTableElement tEl=new RelatiiTableElement(index,elProd,el.getIndex(),indexAnterior);
                        indexAnterior=index;
                        linieNoua.add(tEl);

                    }
                    currentProd++;
                }
            }
            linie.clear();
            linie=linieNoua;
            for(RelatiiTableElement Tel:linie){
                table.add(Tel);
            }
            indexAnterior=-1;
        }
        printRelatiiTabel(table);


    }

    private static void printRelatiiTabel(List<RelatiiTableElement> table) {
        System.out.println(" Tabel de relatii: ");
        for(RelatiiTableElement Tel:table){
            System.out.println("i: "+Tel.getIndex()+" el: "+Tel.getElement()+" parinte: "+Tel.getTata()+" frate: "+Tel.getFrateStanga());
        }

    }

    private static void printBanda(List<Integer> bandaDeIesire) {
        System.out.println("Banda de iesire: ");
        for (int nr:bandaDeIesire){
            System.out.print(nr+" ");
        }
    }
}
