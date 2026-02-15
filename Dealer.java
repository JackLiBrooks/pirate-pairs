public class Dealer {
    private Deck deck;
    private DiscardPile discardPile;

    public Dealer(Deck deck, DiscardPile discardPile){
        this.deck = deck;
        this.discardPile = discardPile;
    }

    public Deck getDeck(){
        return deck;
    }
    public int getDeckSize(){
        return deck.size();
    }
    public DiscardPile getDiscardPile(){
        return discardPile;
    }
    private void ensureDeckHasCards(){
        if (deck.isEmpty()){
            deck.recycleDiscards(discardPile.contents());
            discardPile.clear();
            deck.shuffleDeck();
        }
    }
    public void dealCard(Player player, String doOutputs){
        if (player.getStatus()){
            ensureDeckHasCards();
            int drawnCard = deck.drawTop();
            if (doOutputs.equals("yes")){
                System.out.println("The card " + drawnCard + " has been drawn!");
            }
            
            boolean pair = false;
            for (int i = 0; i < player.getHandSize(); i++){
                if (player.getHand()[i] == drawnCard){
                    pair = true;
                    break;
                }
            }
            if (!pair){
                player.handUpdate(player.getHandSize(), drawnCard);
                player.handSizeUpdate(player.getHandSize() + 1);
            } else {
                discardPile.add(drawnCard);
                for (int i = 0; i < player.getHandSize(); i++){
                    discardPile.add(player.getHand()[i]);
                }
                player.updateScore(drawnCard);
                player.clearHand();
            }
        }
    }
    public void resetDealer(){
        deck.reset();
        discardPile.reset();
    }
}
