public class Pirate_Pairs {
    public static int[] deck = createDeck();
    public static int deckSize = deck.length; 
    public static int[] discardPile = new int[55];
    public static int discardSize = discardPile.length;
    
    public static int[] createDeck() {
        int[] deck = new int[55];
        int index = 0;
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < i; j++) {
                deck[index] = i;
                index++;
            }
        }
        return deck;
    }
    
    public static void drawCard(Player player) {
        int cardIndex = (int)(Math.random() * deckSize);
        player.hand[player.handSize] = deck[cardIndex];
        
        deckSize--;
        player.handSize++;
    }   
    public static void main(String[] args) {
        Player player1 = new Player();
        drawCard(player1);
        System.out.println("Player 1's hand:" + player1.hand[0]);
        for (int card : deck) {
            System.out.print(card + ", ");
        }

        
        
    }
}
