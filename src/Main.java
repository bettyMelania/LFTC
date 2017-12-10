import java.util.HashMap;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Functionalitati f=new Functionalitati();
        Gramatica g=f.citireGramatice("g.txt");
        if(g==null)
            return;
        HashMap<String,Set<String>> FIRST= f.first(g);
        HashMap<String,Set<String>> FOLLOW=f.follow(g,FIRST);
        f.creareTabel(g,FIRST,FOLLOW);
    }
}
