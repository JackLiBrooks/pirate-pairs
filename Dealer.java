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
    public void dealCard(Player player) {
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
}
