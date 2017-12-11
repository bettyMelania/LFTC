import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CitireGramatica {

    public static Gramatica citireGramatice(String file){
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

        Collections.addAll(neterminale, neter);

        String[] ter=gramString.get(1).split(",");
        Set<String> terminale=new HashSet<>();

        Collections.addAll(terminale, ter);

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

}
