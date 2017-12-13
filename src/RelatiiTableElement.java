public class RelatiiTableElement {
    private int index;
    private String element;
    private int tata;
    private int frateStanga;

    public RelatiiTableElement(int index, String element, int tata, int frateStanga) {
        this.index = index;
        this.element = element;
        this.tata = tata;
        this.frateStanga = frateStanga;
    }

    public int getIndex() {
        return index;
    }

    public String getElement() {
        return element;
    }

    public int getTata() {
        return tata;
    }

    public int getFrateStanga() {
        return frateStanga;
    }
}
