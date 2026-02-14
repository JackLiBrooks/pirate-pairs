public class Player {
    private static String[] names = {"Aiden", "Micah", "Kaden", "Henry", "Daniel", "Giada", "Pilar", "Ava", "Ainslie", "Lorenzo", "Dave", "Suki", "Silje", "Pheobe", "Alder"};
    private static String[] strategies = {"strategy1", "strategy2", "strategy3"};
    private String name;
    private String strategy;
    private int[] hand = new int[10];
    private int handSize;
    private int score;
    private boolean isIn = true;
    
    public Player() {
        int index = (int)(Math.random() * names.length);
        name = names[index];
        String[] newNames = new String[names.length - 1];
        int newIdx = 0;

        for (int i = 0; i < names.length; i++) {
            if (i != index) {
                newNames[newIdx] = names[i];
                newIdx++;
            }
        }
        names = newNames;
        
        if (strategies.length == 0){
            String[] original = {"strategy1", "strategy2", "strategy3"};
            strategies = original; 
        }
        index = (int)(Math.random() * strategies.length);
        strategy = strategies[index];
        String[] newStrategies = new String[strategies.length - 1];
        newIdx = 0;
        for (int i = 0; i < strategies.length; i++) {
            if (i != index) {
                newStrategies[newIdx] = strategies[i];
                newIdx++;
            }
        }
        strategies = newStrategies;
        
    }
    
    public String getName(){
        return name;
    }
    public String getStrategy(){
        return strategy;
    }
    public int[] getHand(){
        return hand;
    }
    public void handUpdate(int index, int numb){
        hand[index] = numb;
    }
    public int getHandSize(){
        return handSize;
    }
    public void clearHand(){
        hand = new int[10];
        handSize = 0;
    }
    public void handSizeUpdate(int numb){
        handSize = numb;
    }
    public int getScore(){
        return score;
    }
    public void updateScore(int numb){
        score += numb;
    }
    public boolean getStatus(){
        return isIn;
    }
    public void endTurn(int playerAmount){
        if (score > (60 / playerAmount + 1)){
            isIn = false;
        }
    }
    private void takeCard(Player[] players, DiscardPile discardPile) {
        if (isIn){
            Player fromPlayer = null;
            int fromIndex = -1;
            int smallest = 10;
            for (Player p : players) {
                if (p.getStatus() && p != this){
                    for (int i = 0; i < p.getHandSize(); i++) {
                        int card = p.getHand()[i];
                        if (card < smallest) {
                            smallest = card;
                            fromPlayer = p;
                            fromIndex = i;
                        }
                    }
                } 
            }
            if (fromPlayer != null){
                updateScore(smallest);
                for (int i = 0; i < handSize; i++) {
                    discardPile.add(hand[i]);
                }
                clearHand();
                int[] hand = fromPlayer.getHand();
                for (int i = fromIndex; i < fromPlayer.getHandSize() - 1; i++) {
                    hand[i] = hand[i + 1];
                }
                hand[fromPlayer.getHandSize() - 1] = 0;
                fromPlayer.handSizeUpdate(fromPlayer.getHandSize() - 1);
                discardPile.add(smallest);
            }
        }
    }
    public int checkLowestCard(Player[] players, DiscardPile discardPile){
        int smallest = 10;
        for (Player p : players) {
            if (p.getStatus() && p != this){
                for (int i = 0; i < p.getHandSize(); i++) {
                    int card = p.getHand()[i];
                    if (card < smallest) {
                        smallest = card;
                    }
                }
            } 
        }
        return smallest;
    }

    // Need to change the stratagies to only take if the lowest card is smaller than a certain number, otherwise the person who only draws will pretty much always win.
    public void turn(Dealer dealer, Player[] players, DiscardPile discardPile, int turn, int playerAmount, String Strategy){
        if (turn !=1){
            System.out.println("Smallest: " + this.checkLowestCard(players, discardPile));
            if (strategy.contains("1")){ // strategy 1 is to draw everytime
                dealer.dealCard(this);
                endTurn(playerAmount);
            } else if (strategy.contains("2")){
                if (turn % 2 == 0 && this.checkLowestCard(players, discardPile) < 6){
                    this.takeCard(players, discardPile);
                    endTurn(playerAmount);
                } else{
                    dealer.dealCard(this);
                    endTurn(playerAmount);
                }
            } else{
                boolean hasDuplicate = false;
                for (int card : hand){
                    for (Player p : players){
                        if (!hasDuplicate){
                            if (p.getStatus() && p != this){
                                if (!hasDuplicate){
                                    for (int c : p.getHand()){
                                        if (c == card){
                                            hasDuplicate = true;
                                            break;
                                        }
                                    }
                                } else{
                                    break;
                                }
                            }
                        }else {
                            break;
                        }
                    }
                    if (hasDuplicate == false){
                        for (int n : discardPile.contents()){
                            if (n == card){
                                hasDuplicate = true;
                                break;
                            }
                        }
                    }
                    if (hasDuplicate || this.checkLowestCard(players, discardPile) >= 6){
                        dealer.dealCard(this);
                        endTurn(playerAmount);
                    } else{
                        takeCard(players, discardPile);
                        endTurn(playerAmount);
                    }  
                }
            }
        } else{
            dealer.dealCard(this);
            endTurn(playerAmount);
        }
    }
}