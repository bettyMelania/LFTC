import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadFile {

    /*
    returneaza continutui unui fisier
    file:String numele fisierului din  care vream continutul

    return:
        String: continutul fisierului

     */
    public static List<String> getFromFile(String file){
        char[] buff=new char[0];
        List<String> program=new ArrayList<>();
        try(BufferedReader in=new BufferedReader(new FileReader((file)))){
            String line = in.readLine();
           while(line!=null) {
               program.add(line);
               line = in.readLine();
           }


        }catch (IOException e){
            e.printStackTrace();
        }
        return program;
    }

    public static String getSecventa(String file){
        char[] buff=new char[0];
        StringBuilder p=new StringBuilder("");
        try(BufferedReader in=new BufferedReader(new FileReader((file)))){
            String line = in.readLine();
            while(line!=null) {
                p.append(line);
                line = in.readLine();
            }


        }catch (IOException e){
            e.printStackTrace();
        }
        return p.toString();
    }
    /*
    returneaza codurile din fisierul dat
    file:String numele fisierului din  care vream codificarea

    return:
        HashMap : avand ca si chei cuvintele reyervare, sau operatori, sau separatori, iar valoarea e un cod unic
     */
    public static HashMap<String,Integer> getCodificareFile(String file){
        char[] buff ;
        String fileS="";
        try(BufferedReader in=new BufferedReader(new FileReader((file)))){
            buff=new char[ in.read()*1000];
            in.read(buff);
            fileS=new String(buff);

        }catch (IOException e){
            e.printStackTrace();
        }
        String[] parts=fileS.split("Codificare");
        String codTable=parts[1];
        String[] tabelLines=codTable.split("\r\n");

        HashMap<String,Integer> cods=new HashMap<>();
        String[] elems;
        for(int i=4;i<tabelLines.length-1;i++){
            elems=tabelLines[i].split(" ");
            cods.put(elems[0],Integer.parseInt(elems[1]));
        }



        return cods;
    }

    /*
    scrie intr-un fisier stringul dat
    result:String -Stringul pe care vrem sa scriem in fisier
     */
    public static void write(String result) {
        String path="C:\\Users\\Betty\\Desktop\\An 3 Semestrul 1\\LFTC\\Lab1\\";
        try(PrintWriter out=new PrintWriter(new FileWriter(path+"result.txt"))){
            out.write(result);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
