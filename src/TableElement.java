import javafx.util.Pair;

public class TableElement
{
    private Pair<String,String> pozTabel;
    private Pair<String,Integer> valTabel;

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

