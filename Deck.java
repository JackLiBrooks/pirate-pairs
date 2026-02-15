public class Deck {
    private int[] cards;

    public Deck(){
        reset();
    }

    public void reset(){
        cards = createDeck();
        shuffleDeck();
    }
    private int[] createDeck(){
        int[] deck = new int[55];
        int index = 0;
        for (int i = 1; i <= 10; i++){
            for (int j = 0; j < i; j++){
                deck[index] = i;
                index++;
            }
        }
        return deck;
    }
    public void shuffleDeck(){ //fisher yates shuffle
        for (int i = cards.length - 1; i > 0; i--){
            int j = (int)(Math.random() * (i + 1));
            int temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    public int size(){
        return cards.length;
    }
    public boolean isEmpty(){
        return cards.length == 0;
    }
    public int drawTop(){
        int top = cards[0];
        int[] newCards = new int[cards.length - 1];
        for (int i = 1; i < cards.length; i++){
            newCards[i - 1] = cards[i];
        }
        cards = newCards;
        return top;
    }
    public int[] recycleDiscards(int[] discards){
        cards = discards;
        return cards;
    }
}