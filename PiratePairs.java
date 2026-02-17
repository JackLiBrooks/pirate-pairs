import java.util.Scanner;
public class PiratePairs {
    public static Dealer dealer = new Dealer(new Deck(), new DiscardPile());
    
    public static int playersIn(Player[] players){
        int count = 0;
        for (Player player : players){
            if (player.getStatus()){
                count++;
            }
        }
        return count;
    }
    public static int greatestValueStrategy(int[] stratagies){
        int greatest = 0;
        int strat = 0;
        int index = 0;
        for (int numb : stratagies){
            if (numb > greatest){
                greatest = numb;
                strat = index;
            }
            index ++;
        }
        return strat;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("How many players? ");
        int playerAmount = sc.nextInt();
        System.out.print("How many games do you want to play? ");
        int gamesPlaying = sc.nextInt();
        sc.nextLine();
        System.out.print("Do you want a turn by turn breakdown? ");
        String doOutputs = sc.nextLine().trim();
        sc.close();
        Player[] players = new Player[playerAmount]; 
        for (int i = 0; i < playerAmount; i++){
            Player temp = new Player();
            players[i] = temp;
        }
        String[] winners = new String[gamesPlaying];
        if (doOutputs.toLowerCase().equals("yes")){
            System.out.println();
            for (int i = 0; i < gamesPlaying; i ++){
                int turns = 1;
                while (playersIn(players) > 1){
                    System.out.println("Turn " + turns + ":");
                    System.out.println("-----------------------------------");
                    for (Player player : players){
                        if (playersIn(players) == 1){
                            break;
                        }
                        player.turn(dealer, players, dealer.getDiscardPile(), turns, playerAmount, player.getStrategy(), doOutputs);
                        if (player.getStatus()){
                            System.out.print(player.getName() + "'s hand: ");
                            for (int card : player.getHand()){
                                if (card != 0){
                                    System.out.print(card + ", ");
                                } else{
                                    break;
                                }
                            }
                            System.out.println();
                            System.out.println(player.getName() + "'s Strategy: " + player.getStrategy());
                            System.out.println(player.getName() + " Score: " + player.getScore());
                            System.out.println("-----------------------------------");
                        } else{
                            System.out.println(player.getName() + " is out!");
                            System.out.println("-----------------------------------");
                        }
                    }
                    // System.out.print("Discard Pile: ");
                    // dealer.getDiscardPile().print();
                    // System.out.println();
                    // System.out.println("-----------------------------------");
                    turns++;
                }
                for (Player player : players){
                    if (player.getStatus()){
                        System.out.println();
                        System.out.println(player.getName() + " Wins Using " + player.getStrategy());
                        winners[i] = player.getStrategy();
                        dealer.resetDealer();
                        for (Player p : players){
                            p.resetForNewGame();
                        }
                        break;
                    }
                }
            }
        } else{
            for (int i = 0; i < gamesPlaying; i ++){
                int turns = 1;
                while (playersIn(players) > 1){
                    for (Player player : players){
                        if (playersIn(players) == 1){
                            break;
                        }
                        player.turn(dealer, players, dealer.getDiscardPile(), turns, playerAmount, player.getStrategy(), doOutputs);
                    }
                    turns++;
                }
                for (Player player : players){
                    if (player.getStatus()){
                        winners[i] = player.getStrategy();
                        dealer.resetDealer();
                        for (Player p : players){
                            p.resetForNewGame();
                        }
                        break;
                    }
                }
            }
        }
        int strategy1Count = 0, strategy2Count = 0, strategy3Count = 0;
        for (String strategy : winners){
            if (strategy.equals("strategy1")){
                strategy1Count ++;
            } else if (strategy.equals("strategy2")){
                strategy2Count ++;
            } else {
                strategy3Count ++;
            }
        }
        int[] strategyCounts = {strategy1Count, strategy2Count, strategy3Count};
        int bestIndex = greatestValueStrategy(strategyCounts);
        System.out.println();
        System.out.println("The strategies with the most wins was Strategy " + (bestIndex + 1) + " with " + strategyCounts[bestIndex] + " wins!");
    }
}