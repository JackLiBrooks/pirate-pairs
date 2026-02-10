import java.util.Scanner;
public class Pirate_Pairs {
    public static int[] deck = shuffleDeck(createDeck());
    public static int deckSize = deck.length;
    public static int[] discardPile = new int[55];
    public static int discardTop = 0;
    
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
    public static int[] shuffleDeck(int[] deck){
        for (int k = 0; k < 10000; k++) {
            int a = (int)(Math.random() * deck.length);
            int b = (int)(Math.random() * deck.length);

            int temp = deck[a];
            deck[a] = deck[b];
            deck[b] = temp;
        }
        return deck;
    }
    public static void removeTopCard(){
        int[] newDeck = new int[deck.length - 1];
        for (int i = 1; i < deck.length; i++) {
            newDeck[i - 1] = deck[i];
        }
        deck = newDeck;
        deckSize = deck.length;
    }
    public static void drawCard(Player player) {
        if (player.getStatus()){
            boolean pair = false;
            int drawnCard = deck[0];
            for (int card : player.getHand()){
                if (card != 0) {
                    if (card == drawnCard){
                        pair = true;
                        break;
                    }
                } else{
                    break;
                }
                
            } 
            if (!pair){
                player.handUpdate(player.getHandSize(), drawnCard);
                player.handSizeUpdate(player.getHandSize() + 1);
                removeTopCard();
            } else{
                discardPile[discardTop] = drawnCard;
                removeTopCard();
                discardTop++;
                for (int discarding : player.getHand()){
                    if (discarding != 0) {
                        discardPile[discardTop] = discarding;
                        discardTop++;
                    } else{
                        break;
                    }                    
                }
                player.updateScore(drawnCard);
                player.clearHand();
            }
        }
    }  
    public static void endTurn(Player player, int playerAmount){
        if (player.getScore() > (60 / playerAmount + 1)){//temp numb
            player.upateStatus();
        }    
    }
    public static void turn(Player player, int playerAmount){
        drawCard(player);
        endTurn(player, playerAmount);

    }
    public static int playersIn(Player[] players){
        int count = 0;
        for (Player player : players){
            if (player.getStatus()){
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        System.out.println("How many players? ");
        int playerAmount = sc.nextInt();
        sc.close();
        Player[] players = new Player[playerAmount]; 

        Player player1 = new Player();
        players[0] = player1;
        int turns = 1;
        while (playersIn(players) > 0){
            System.out.println("Turn " + turns + ":");
            turn(player1, players.length);
            System.out.print("Player 1's hand: ");
            for (int card : player1.getHand()){
                if (card != 0) {
                    System.out.print(card + ", ");
                } else{
                    break;
                }
            }
            System.out.println();
            System.out.print("Discard Pile: ");
            for (int discard : discardPile){
                if (discard != 0) {
                    System.out.print(discard + ", ");
                } else{
                    break;
                }
                
            }
            System.out.println();
            System.out.println("Player Score: " + player1.getScore());
            System.out.println("-----------------------------------");
            turns++;
        }     
        System.out.println("Cards remaining in deck: " + deckSize);
    }
}