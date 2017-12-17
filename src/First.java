import java.util.*;

public class First {

    public static  HashMap<String,Set<String>> first(Gramatica g){

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
                if(terminale.contains(prima))
                    firsts.add(prima);
            }
            FIRST.put(neterminal,firsts);

        }




        //FIRST 1
        boolean changed=true;
        while(changed) {
            changed=false;
            for (HashMap.Entry<String, Productie> entry : productii.entrySet()) {
                String neterminal = entry.getKey();
                boolean goToNext;
                for (String prod : entry.getValue().getRezultate()) {
                    goToNext = false;
                    String prima = prod.split(" ")[0];
                    if (neterminale.contains(prima)) {
                        Set<String> firsturiDeLa = FIRST.get(prima);
                        for (String s : firsturiDeLa) {
                            if (!s.equals("~")) {
                                if(!FIRST.get(neterminal).contains(s)) {
                                    changed = true;
                                    FIRST.get(neterminal).add(s);
                                }
                            }
                            else
                                goToNext = true;
                        }
                    }
                    int i = 1;
                    while (goToNext) {
                        goToNext = false;
                        String second = prod.split(" ")[i];
                        if (neterminale.contains(second)) {
                            Set<String> firsturiDeLa = FIRST.get(second);
                            for (String s : firsturiDeLa) {
                                if (!s.equals("~"))
                                    if(!FIRST.get(neterminal).contains(s)) {
                                        changed = true;
                                        FIRST.get(neterminal).add(s);
                                    }
                                else
                                    goToNext = true;
                            }
                        } else {
                            if(!FIRST.get(neterminal).contains(second)) {
                                changed = true;
                                FIRST.get(neterminal).add(second);
                            }
                        }
                        i++;
                    }
                }


            }
        }
        printFirst(FIRST);
        return FIRST;
    }

    public static Set<String> firstProductie(String productie,HashMap<String,Set<String>> FIRST,Gramatica gramatica ){
        Set<String> termFirst=new HashSet<>();
        Set<String> terminale=gramatica.getTerminale();

        List<String> elems= Arrays.asList(productie.split(" "));
        for(String el:elems){
            if (terminale.contains(el)) {
                termFirst.add(el);
                break;
            }
            else {
                Set<String> firsturi=FIRST.get(el);

                for(String f:firsturi){
                    if(!f.equals("~")) {
                        termFirst.add(f);
                    }

                }
                if(!firsturi.contains("~"))
                    break;

            }
        }
        return termFirst;
    }

    private static void printFirst(HashMap<String, Set<String>> first) {
        System.out.println("\n \n FIRST");
        for(HashMap.Entry<String,Set<String>> entry:first.entrySet()){
            System.out.print(entry.getKey()+": ");
            for(String s:entry.getValue())
                System.out.print(s+",");
            System.out.println();

        }
    }
}
