import java.util.Scanner;
public class PiratePairs {
    public static Dealer dealer = new Dealer(new Deck(), new DiscardPile());
    
    public static void turn(Player player, int playerAmount){
        dealer.drawCard(player);
        player.endTurn(playerAmount);
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
        System.out.print("How many players? ");
        int playerAmount = sc.nextInt();
        System.err.println();
        sc.close();
        Player[] players = new Player[playerAmount]; 
        for (int i = 0; i < playerAmount; i++){
            Player temp = new Player();
            players[i] = temp;
        }
        System.out.println("For this game, you have to have a score under "+ (60 / playerAmount + 1));
        int turns = 1;
        while (playersIn(players) > 1){
            System.out.println("Turn " + turns + ":");
            System.out.println("-----------------------------------");
            for (Player player : players){
                if (playersIn(players) == 1){
                    break;
                }
                turn(player, players.length);
                if (player.getStatus()){
                    System.out.print(player.getName() + "'s hand: ");
                    for (int card : player.getHand()){
                        if (card != 0) {
                            System.out.print(card + ", ");
                        } else{
                            break;
                        }
                    }
                    System.out.println();
                    System.out.println(player.getName() + " Score: " + player.getScore());
                    System.out.println("-----------------------------------");
                } else{
                    System.out.println(player.getName() + " is out!");
                    System.out.println("-----------------------------------");
                }
            }
            System.out.print("Discard Pile: ");
            dealer.getDiscardPile().print();
            System.out.println();
            System.out.println("-----------------------------------");
            turns++;
        }   
        for (Player player : players){
            if (player.getStatus()){
                System.out.println(player.getName() + " Wins!");
                break;
            }
        }
    }
}