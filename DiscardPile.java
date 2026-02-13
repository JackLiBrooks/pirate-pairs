public class DiscardPile {
    private int[] pile;
    private int topIndex;

    public DiscardPile() {
        pile = new int[50];
        topIndex = 0;
    }
    
    public void add(int card) {
        if (topIndex < pile.length) {
            pile[topIndex] = card;
            topIndex ++;
        }
    }
    public int[] contents() {
        int[] noZeros = new int[topIndex];
        for (int i = 0; i < topIndex; i++) {
            noZeros[i] = pile[i];
        }
        return noZeros;
    }
    public void clear() {
        pile = new int[pile.length];
        topIndex = 0;
    }
    public void print() {
        for (int i = 0; i < topIndex; i++) {
            System.out.print(pile[i] + ", ");
        }
    }
}

