public class Player {
    private String name;
    private int[] hand = new int[10];
    private int handSize;
    private int score;
    private boolean isIn = true;

    public String getName(){
        return name;
    }
    public void updateName(String n){
        name = n;
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
    public void upateStatus(){
        isIn = false;
    }
}