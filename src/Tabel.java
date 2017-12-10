import javafx.util.Pair;

import java.util.Set;

public class Tabel
{
    private Set<String> netermTermLine;
    private Set<String> termColumn;
    private Set<TableElement> val;

    public Tabel()
    {

    }

    public Tabel(Set<String> netermTermLine, Set<String> termColumn) {
        this.netermTermLine = netermTermLine;
        this.termColumn = termColumn;
    }

    public Set<String> getNetermTermLine() {
        return netermTermLine;
    }

    public void setNetermTermLine(Set<String> netermTermLine) {
        this.netermTermLine = netermTermLine;
    }

    public Set<String> getTermColumn() {
        return termColumn;
    }

    public void setTermColumn(Set<String> termColumn) {
        this.termColumn = termColumn;
    }

    public Set<TableElement> getVal() {
        return val;
    }

    public void setVal(Set<TableElement> val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Tabel{" +
                "netermTermLine=" + netermTermLine +
                ", termColumn=" + termColumn +
                ", val=" + val +
                '}';
    }
}
