public class Dealer {
    private Deck deck;
    private DiscardPile discardPile;

    public Dealer(Deck deck, DiscardPile discardPile) {
        this.deck = deck;
        this.discardPile = discardPile;
    }

    public Deck getDeck() {
        return deck;
    }
    public DiscardPile getDiscardPile() {
        return discardPile;
    }
    private void ensureDeckHasCards() {
        if (deck.isEmpty()) {
            deck.recycleDiscards(discardPile.contents());
            discardPile.clear();
            deck.shuffleDeck();
        }
    }
    public void drawCard(Player player) {
        if (player.getStatus()){
            ensureDeckHasCards();
            int drawnCard = deck.drawTop();
            boolean pair = false;
            for (int i = 0; i < player.getHandSize(); i++) {
                if (player.getHand()[i] == drawnCard) {
                    pair = true;
                    break;
                }
            }
            if (!pair) {
                player.handUpdate(player.getHandSize(), drawnCard);
                player.handSizeUpdate(player.getHandSize() + 1);
            } else {
                discardPile.add(drawnCard);
                for (int i = 0; i < player.getHandSize(); i++) {
                    discardPile.add(player.getHand()[i]);
                }
                player.updateScore(drawnCard);
                player.clearHand();
            }
        }
    }
    public static void takeCard(Player taker, Player[] players, DiscardPile discardPile) {
        if (taker.getStatus()){
            Player fromPlayer = null;
            int fromIndex = -1;
            int smallest = 10;
            for (Player p : players) {
                if (p.getStatus() && p != taker){
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
                taker.updateScore(smallest);
                taker.clearHand();
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
}
