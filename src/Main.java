import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class Main {

    public static void main(String[] args) {
        Gramatica g=CitireGramatica.citireGramatice("grammar.txt");
        if(g==null) {
            System.out.print("gramatica invalida ");
            return;
        }
        HashMap<String,Set<String>> FIRST= First.first(g);
        HashMap<String,Set<String>> FOLLOW=Follow.follow(g,FIRST);

        Tabel tabel=CreareTabel.creareTabel(g,FIRST,FOLLOW);
        if(tabel!=null) {
            String secventa = LoadFile.getSecventa("program.txt");
            Analizator.analiza(tabel, secventa, g);
        }

    }
}
