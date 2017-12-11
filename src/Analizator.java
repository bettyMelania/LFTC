import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class Analizator {
    public static boolean analiza(Tabel tabel, String secventa,Gramatica g) {
        Queue<String> stivaIntrare=new LinkedBlockingDeque<>();
        for(Character c:secventa.toCharArray()){
            stivaIntrare.add(c.toString());
        }
        stivaIntrare.add("$");

        Stack<String> stivaLucru=new Stack<>();
        stivaLucru.push("$");
        stivaLucru.push(g.getSimbolStart());


        List<Integer> bandaDeIesire=new ArrayList<>();

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
                    }
                    break;
                }
            }
            printBanda(bandaDeIesire);
            creareTabelRelatii(bandaDeIesire);




        }


       // stivaIesire.push("AB");
       // String f6=stivaIesire.pop();

        //stivaIntrare.peek() //not remove
        //String f1=stivaIntrare.poll(); //remove

    }

    private static void creareTabelRelatii(List<Integer> bandaDeIesire) {


    }

    private static void printBanda(List<Integer> bandaDeIesire) {
        System.out.println("Banda de iesire: ");
        for (int nr:bandaDeIesire){
            System.out.print(nr+" ");
        }
    }
}
