public class Main {

    public static void main(String[] args) {
        Functionalitati f=new Functionalitati();
        Gramatica g=f.citireGramatice("g.txt");
        f.first(g);
        f.follow(g);
    }
}
