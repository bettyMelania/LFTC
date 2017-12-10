

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Functionalitati {

    public Gramatica citireGramatice(String file){
        int nrCategorii=3;
        Gramatica g=new Gramatica();
        List<String> gramString=new ArrayList<>();
        try(BufferedReader in=new BufferedReader(new FileReader((file)))){
            String sectiune="";
            String line = in.readLine();
            while(line!=null) {
                if(nrCategorii>0) {
                    if (line.equals("#")) {
                        gramString.add(sectiune);
                        nrCategorii--;
                        sectiune="";
                    } else
                        sectiune+=line;
                }
                else
                    gramString.add(line);
                line=in.readLine();
            }
        }catch (IOException e){
            return null;
        }
        if(gramString.size()<4)
            return null;
        String[] neter=gramString.get(0).split(",");
        Set<String> neterminale=new HashSet<>();
        for(int i=0;i<neter.length;i++){

            neterminale.add(neter[i]);
        }
        String[] ter=gramString.get(1).split(",");
        Set<String> terminale=new HashSet<>();
        for(int i=0;i<ter.length;i++){
            terminale.add(ter[i]);
        }
        String start=gramString.get(2);
        if(!neterminale.contains(start))
            return null;
        HashMap<String,Productie> productii=new HashMap<>();
        for(int i=3;i<gramString.size();i++){
            String[] linieProd=gramString.get(i).split("(->)|\\|");
            if (linieProd.length<1)
                return null;
            List<String> rezultate=new ArrayList<>();
            for(int j=1;j<linieProd.length;j++) {
                for(char c : linieProd[j].toCharArray()) {
                    if(!terminale.contains(String.valueOf(c)) && !neterminale.contains(String.valueOf(c)) && !String.valueOf(c).equals("~") &&String.valueOf(c).equals(""))
                        return null;
                }
                rezultate.add(linieProd[j]);

            }
            Productie p=new Productie(rezultate);
            productii.put(linieProd[0],p);

        }


        g.setNeterminale(neterminale);
        g.setTerminale(terminale);
        g.setSimbolStart(start);
        g.setProductii(productii);

        System.out.println(g);
        return g;
    }

    public boolean testRegular(Gramatica g) {
        Set<String> terminale=g.getTerminale();
        Set<String> neterminale=g.getNeterminale();
        HashMap<String,Productie> productii=g.getProductii();
        String start=g.getSimbolStart();
        if(!neterminale.contains(start))
            return false;
        boolean startEpsilon=productii.get(start).getRezultate().contains("~");
        for (Map.Entry<String,Productie> p:productii.entrySet()) {
            if(!p.getKey().equals(start)) {
                if (p.getValue().getRezultate().contains("~")) {
                    return false;
                }
                if (startEpsilon) {
                    for (String rez : p.getValue().getRezultate()) {
                        if (rez.contains(start))
                            return false;
                    }
                }
            }
            for (String rez:p.getValue().getRezultate()) {
                if(rez.length()==1){
                    if (!rez.equals("~") && !terminale.contains(rez))
                        return false;
                    }
                else{
                    if(rez.length()!=2)
                        return false;
                    String[] atomi=rez.split("");
                    if(!terminale.contains(atomi[0]) || !neterminale.contains(atomi[1]))
                        return false;
                    }
            }
        }
        return true;
    }

    public void first(Gramatica g){
        if(g==null) {
            System.out.println("Ceva e incorect! ");
            return;
        }

        Set<String> terminale=g.getTerminale();
        Set<String> neterminale=g.getNeterminale();
        HashMap<String,Productie> productii=g.getProductii();
        String start=g.getSimbolStart();


        //FIRST 0
        HashMap<String,Set<String>> FIRST=new HashMap<>();
        for (HashMap.Entry<String,Productie> entry:productii.entrySet()){
            String neterminal=entry.getKey();
            Set<String> firsts=new HashSet<>();
            for(String prod:entry.getValue().getRezultate()){
                int j=0;
                if(prod.startsWith(" "))
                    j=1;
                String prima=prod.split(" ")[j];
                if(terminale.contains(prima) || prima.equals("~"))
                    firsts.add(prima);
            }
            FIRST.put(neterminal,firsts);

        }
        //FIRST 1
        for (HashMap.Entry<String,Productie> entry:productii.entrySet()){
            String neterminal=entry.getKey();
            boolean goToNext;
            for(String prod:entry.getValue().getRezultate()){
                goToNext=false;
                String prima=prod.substring(0,1);
                if(neterminale.contains(prima)) {
                    Set<String> firsturiDeLa=FIRST.get(prima);
                    for(String s:firsturiDeLa) {
                        if(!s.equals("~"))
                            FIRST.get(neterminal).add(s);
                        else
                            goToNext=true;
                    }
                }
                int i =1;
                while(goToNext){
                    goToNext=false;
                    String second = prod.split(" ")[i];
                    if(neterminale.contains(second)) {
                        Set<String> firsturiDeLa=FIRST.get(second);
                        for(String s:firsturiDeLa) {
                            if(!s.equals("~"))
                                FIRST.get(neterminal).add(s);
                            else
                                goToNext=true;
                        }
                    }else{
                        FIRST.get(neterminal).add(second);
                    }
                    i++;
                }
            }


        }
        printFirst(FIRST);
    }

    public void follow(Gramatica g){
        if(g==null) {
            System.out.println("Ceva e incorect! ");
            return;
        }

        Set<String> terminale=g.getTerminale();
        Set<String> neterminale=g.getNeterminale();
        HashMap<String,Productie> productii=g.getProductii();
        String start=g.getSimbolStart();


        HashMap<String,Set<String>> FOLLOW=new HashMap<>();

        //TO DO


        printFollow(FOLLOW);
    }


    private void printFirst(HashMap<String, Set<String>> first) {
        System.out.println("FIRST");
        for(HashMap.Entry<String,Set<String>> entry:first.entrySet()){
            System.out.print(entry.getKey()+": ");
            for(String s:entry.getValue())
                System.out.print(s+" ");
            System.out.println();

        }
    }

    private void printFollow(HashMap<String, Set<String>> follow) {
        System.out.println("FOLLOW");
        for(HashMap.Entry<String,Set<String>> entry:follow.entrySet()){
            System.out.print(entry.getKey()+": ");
            for(String s:entry.getValue())
                System.out.print(s+" ");
            System.out.println();

        }
    }

}