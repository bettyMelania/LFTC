public class Main {

    public static void main(String[] args) {

        Functionalitati f=new Functionalitati();
        Gramatica g=f.citireGramatice("C:\\Users\\Betty\\Documents\\LFTClab4\\g.txt");
       f.first(g);
       f.follow(g);
    }
}
