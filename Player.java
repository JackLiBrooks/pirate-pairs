public class Player {
    private String[] names = {"Aiden", "Micah", "Kaden", "Henry", "Daniel", "Giada", "Pilar", "Ava", "Ainslie", "Lorenzo", "Dave", "Suki", "Silje", "Pheobe", "Alder"};
    private String name;
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
    }
    
    public String getName(){
        return name;
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
}