
import java.util.List;

public class Productie {
    private List<String> rezultate;

    public Productie( List<String> rezultate) {
        this.rezultate = rezultate;
    }

    @Override
    public String toString() {
        String s="";
        for (String rez:rezultate ) {
            s+=rez+"|";

        }
        if(s.length()>0)
            s=s.substring(0,s.length()-1);
        s+="\n";
        return s;
    }

    public List<String> getRezultate() {
        return rezultate;
    }
}
