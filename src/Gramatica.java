
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Gramatica {
    private Set<String> neterminale;
    private Set<String> terminale;
    private HashMap<String,Productie> productii;
    private String simbolStart;

    public Gramatica(){

    }

    public Set<String> getNeterminale() {
        return neterminale;
    }

    public void setNeterminale(Set<String> neterminale) {
        this.neterminale = neterminale;
    }

    public Set<String> getTerminale() {
        return terminale;
    }

    public void setTerminale(Set<String> terminale) {
        this.terminale = terminale;
    }

    public HashMap<String,Productie> getProductii() {
        return productii;
    }

    public void setProductii(HashMap<String,Productie> productii) {
        this.productii = productii;
    }

    public String getSimbolStart() {
        return simbolStart;
    }

    public void setSimbolStart(String simbolStart) {
        this.simbolStart = simbolStart;
    }



    @Override
    public String toString() {
        return "Gramatica" +
                "\nN={" + printNeterminale() +
                "}\nE={" + printTerminale() +
                "}\n" + printProductii() +
                "\n" + simbolStart ;
    }

    public String printNeterminale() {
        String neterminaleS="";
        for (String neter:neterminale) {
            neterminaleS+=neter+",";
        }
        neterminaleS=neterminaleS.substring(0,neterminaleS.length()-1);
        return neterminaleS;
    }

    public String printTerminale() {
        String terminaleS="";
        for (String ter:terminale) {
            terminaleS+=ter+",";
        }
        terminaleS=terminaleS.substring(0,terminaleS.length()-1);
        return terminaleS;
    }

    public String printProductii() {
        String productiiS="";
        for (Map.Entry<String,Productie> p:productii.entrySet()) {
            productiiS+=p.getKey()+"->"+p.getValue();
        }
        productiiS=productiiS.substring(0,productiiS.length()-1);
        return productiiS;
    }

    public String printProductie(String s) {
        if(productii.containsKey(s))
            return s+"->"+productii.get(s);
        else
            return "";
    }
}
