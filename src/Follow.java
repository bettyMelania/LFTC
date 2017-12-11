import java.util.*;

public class Follow {

    public static HashMap<String,Set<String>> follow(Gramatica g, HashMap<String,Set<String>> FIRST){

        Set<String> terminale=g.getTerminale();
        Set<String> neterminale=g.getNeterminale();
        HashMap<String,Productie> productii=g.getProductii();
        String start=g.getSimbolStart();


        HashMap<String,Set<String>> FOLLOW=new HashMap<>();

        //INTIIALIZARE
        for(String net:neterminale){
            Set follows=new HashSet<String>();
            if(net.equals(start))
                follows.add("~");
            FOLLOW.put(net,follows);
        }
        boolean repeat=true;
        while(repeat){
            repeat=false;
            for(String neterminal:neterminale) {
                for (HashMap.Entry<String, Productie> entry : productii.entrySet()) {
                    String net=entry.getKey();
                    for (String rez : entry.getValue().getRezultate()) {
                        if (rez.contains(neterminal)) {
                            List<String> elems = Arrays.asList(rez.split(" "));
                            int i;
                            for (int j=0;j<elems.size();j++) {
                                if (elems.get(j).equals(neterminal)){
                                    i=j;
                                }else continue;

                                boolean cont = true;
                                String follow;
                                if (i == elems.size() - 1) {
                                    for (String s : FOLLOW.get(net)) {
                                        if (!s.equals("~")) {
                                            if (!FOLLOW.get(neterminal).contains(s)) {
                                                FOLLOW.get(neterminal).add(s);
                                                repeat = true;
                                            }
                                        }
                                    }
                                    if (net.equals(start)) {
                                        if (!FOLLOW.get(neterminal).contains("~")) {
                                            FOLLOW.get(neterminal).add("~");
                                            repeat = true;
                                        }
                                    }
                                }
                                while (cont) {
                                    cont = false;
                                    i++;
                                    if (i == elems.size()) break;
                                    follow = elems.get(i);
                                    if (terminale.contains(follow)) {
                                        if (!FOLLOW.get(neterminal).contains(follow)) {
                                            FOLLOW.get(neterminal).add(follow);
                                            repeat = true;
                                        }
                                        if (i == elems.size() - 1)
                                            break;
                                    } else if (neterminale.contains(follow)) {
                                        for (String s : FIRST.get(follow)) {
                                            if (!s.equals("~")) {
                                                if (!FOLLOW.get(neterminal).contains(s)) {
                                                    FOLLOW.get(neterminal).add(s);
                                                    repeat = true;
                                                }
                                            } else
                                                cont = true;
                                        }
                                        if (i == elems.size() - 1) { //ultima
                                            for (String s : FOLLOW.get(follow)) {
                                                if (!s.equals("~")) {
                                                    if (!FOLLOW.get(neterminal).contains(s)) {
                                                        FOLLOW.get(neterminal).add(s);
                                                        repeat = true;
                                                    }
                                                }
                                            }
                                            if (net.equals(start)) {
                                                if (!FOLLOW.get(neterminal).contains("~")) {
                                                    FOLLOW.get(neterminal).add("~");
                                                    repeat = true;
                                                }
                                            }
                                            break;
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        printFollow(FOLLOW);
        return FOLLOW;
    }




    private static  void printFollow(HashMap<String, Set<String>> follow) {
        System.out.println("FOLLOW");
        for(HashMap.Entry<String,Set<String>> entry:follow.entrySet()){
            System.out.print(entry.getKey()+": ");
            for(String s:entry.getValue())
                System.out.print(s+" ");
            System.out.println();
        }
    }
}
