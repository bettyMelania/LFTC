import javafx.util.Pair;

public class TableElement
{
    private Pair<String,String> pozTabel; // key=randuri && value=coloana
    private Pair<String,Integer> valTabel; // key=productie/pop/err/acc  && value=nr productie

    public TableElement()
    {
    }

    public TableElement(Pair<String, String> pozTabel, Pair<String, Integer> valTabel) {
        this.pozTabel = pozTabel;
        this.valTabel = valTabel;
    }

    public Pair<String, String> getPozTabel() {
        return pozTabel;
    }

    public void setPozTabel(Pair<String, String> pozTabel) {
        this.pozTabel = pozTabel;
    }

    public Pair<String, Integer> getValTabel() {
        return valTabel;
    }

    public void setValTabel(Pair<String, Integer> valTabel) {
        this.valTabel = valTabel;
    }
}

