public class Player {
    private static String[] names = {"Aiden", "Micah", "Kaden", "Henry", "Daniel", "Giada", "Pilar", "Ava", "Ainslie", "Lorenzo", "Dave", "Suki", "Silje", "Pheobe", "Alder"};
    private static String[] strategies = {"strategy1", "strategy2", "strategy3"};
    private String name;
    private String strategy;
    private int[] hand = new int[10];
    private int handSize;
    private int score;
    private boolean isIn = true;
    
    public Player(){
        int index = (int)(Math.random() * names.length);
        name = names[index];
        String[] newNames = new String[names.length - 1];
        int newIdx = 0;

        for (int i = 0; i < names.length; i++){
            if (i != index){
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
        for (int i = 0; i < strategies.length; i++){
            if (i != index){
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
        int[] noZeros = new int[handSize];
        for (int i = 0; i < handSize; i++){
            noZeros[i] = hand[i];
        }
        return noZeros;
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
    public void resetScore(){
        score = 0;
    }
    public void resetForNewGame(){
        clearHand();
        resetScore();
        isIn = true;
    }

    public int HighestCardInHand(){
        int highest = hand[0];
        for (int i = 1; i < handSize; i++){
            if (hand[i] > highest){
                highest = hand[i];
            }
        }
        return highest;
    }
    private void takeCard(Player[] players, DiscardPile discardPile, String doOutputs){
        if (isIn){
            Player fromPlayer = null;
            int fromIndex = -1;
            int smallest = 10;
            for (Player p : players){
                if (p.getStatus() && p != this){
                    for (int i = 0; i < p.getHandSize(); i++){
                        int card = p.getHand()[i];
                        if (card < smallest){
                            smallest = card;
                            fromPlayer = p;
                            fromIndex = i;
                        }
                    }
                } 
            }
            if (fromPlayer != null){
                if (doOutputs.equals("yes")){
                    System.out.println("The card " + smallest + " has been taken!");
                }
                updateScore(smallest);
                for (int i = 0; i < handSize; i++){
                    discardPile.add(hand[i]);
                }
                clearHand();
                int[] hand = fromPlayer.getHand();
                for (int i = fromIndex; i < fromPlayer.getHandSize() - 1; i++){
                    hand[i] = hand[i + 1];
                }
                hand[fromPlayer.getHandSize() - 1] = 0;
                fromPlayer.handSizeUpdate(fromPlayer.getHandSize() - 1);
                discardPile.add(smallest);
            }
        }
    }
    private int checkLowestCard(Player[] players, DiscardPile discardPile){
        int smallest = 10;
        for (Player p : players){
            if (p.getStatus() && p != this){
                for (int i = 0; i < p.getHandSize(); i++){
                    int card = p.getHand()[i];
                    if (card < smallest){
                        smallest = card;
                    }
                }
            } 
        }
        return smallest;
    }
    private double chanceOfDrawingACardInHand(Player[] players, DiscardPile discardPile, Dealer dealer){
        double probability = 0.0;
        for (int card : hand){
            double appearences = 1;
            for (Player p : players){
                if (p.getStatus() && p != this){
                    for (int c : p.getHand()){
                        if (c == card){
                            appearences ++;
                        }
                    }
                } else{
                    continue;
                }
            }
            for (int n : discardPile.contents()){
                if (n == card){
                    appearences ++;
                }
            }
            probability += ((card - appearences) / dealer.getDeckSize());
        }
        return probability;
    }
    private void strategyOne(Player[] players, Dealer dealer, DiscardPile discardPile, int turn, int playerAmount, String doOutputs){
        if (turn % 2 == 0 && this.checkLowestCard(players, discardPile) < 6){
            this.takeCard(players, discardPile, doOutputs);
            endTurn(playerAmount);
        } else{
            dealer.dealCard(this, doOutputs);
            endTurn(playerAmount);
        }
    }
    private void strategyTwo(Player[] players, Dealer dealer, DiscardPile discardPile, int playerAmount, String doOutputs){
        double probability = chanceOfDrawingACardInHand(players, discardPile, dealer);
        if (probability > 0.50){
            takeCard(players, discardPile, doOutputs);
            endTurn(playerAmount);
        } else{
            dealer.dealCard(this, doOutputs);
            endTurn(playerAmount);
        }
    }
    private void strategyThree(Player[] players, Dealer dealer, DiscardPile discardPile, int playerAmount, String doOutputs){
        double probability = chanceOfDrawingACardInHand(players, discardPile, dealer);
        boolean takenCardWillBeLessThanHighestCardInHand = false;
        if (this.checkLowestCard(players, discardPile) < this.HighestCardInHand()){
            takenCardWillBeLessThanHighestCardInHand = true;
        }
        if (probability > 0.40 && takenCardWillBeLessThanHighestCardInHand){
            takeCard(players, discardPile, doOutputs);
            endTurn(playerAmount);
        } else{
            dealer.dealCard(this, doOutputs);
            endTurn(playerAmount);
        }
    }
    public void turn(Dealer dealer, Player[] players, DiscardPile discardPile, int turn, int playerAmount, String Strategy, String doOutputs){
        if (this.handSize != 0){
            if (strategy.contains("1")){ // alternates drawing and taking if lowest card is < 6
                strategyOne(players, dealer, discardPile, turn, playerAmount, doOutputs);
            } else if (strategy.contains("2")){ // takes card if drawing card in hand is greater than 50%
                strategyTwo(players, dealer, discardPile, playerAmount, doOutputs);
            } else{ // takes card if drawing card in hand is greater than 40% and the taken card is smaller that cards in hand
                strategyThree(players, dealer, discardPile, playerAmount, doOutputs);
            }
        } else{
            dealer.dealCard(this, doOutputs);
            endTurn(playerAmount);
        }
    }
}