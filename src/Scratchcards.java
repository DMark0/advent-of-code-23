import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Cards{
    private final TreeMap<Card,Integer> totalCards;

    public Cards() {
        totalCards = new TreeMap<>();
    }

    public void add(Card card){
        totalCards.put(card,1);
    }

    public int sum(){
        return totalCards.keySet().stream()
                .mapToInt(Card::getPoints)
                .sum();
    }

    
    public int getTotal(){
        ArrayList<Card> cards = new ArrayList<>(totalCards.keySet());
        for (int i = 0; i < cards.size(); i++) {
            int winningNumbers = cards.get(i).getNumberOfWinningNumbers();
            for (int j = 1; j <= winningNumbers && i+j<totalCards.size(); j++) {
                System.out.println(i);
                totalCards.put(cards.get(i+j), totalCards.get(cards.get(i+j))+totalCards.get(cards.get(i)) );
            }
        }

        int sum = 0;
        for (Card card : cards){
            System.out.println(totalCards.get(card));
            sum += totalCards.get(card);
        }
        return sum;
    }
}

class Card implements Comparable<Card>{
    private int id;
    private final HashSet<Integer> winningNumbers;
    private final ArrayList<Integer> givenNumbers;

    public Card() {
        winningNumbers = new HashSet<>();
        givenNumbers = new ArrayList<>();
    }

    public void readCard(String line) {
        String[] parts = line.split(":");
        this.id = Integer.parseInt(parts[0].split("\\s+")[1]);
        String winning = parts[1].substring(1,30);
        String given = parts[1].substring(33);

        String[] w = winning.split("\\s+");
        for (String n : w){
            if (!n.equals(""))
                winningNumbers.add(Integer.parseInt(n));
        }


        String[] g = given.split("\\s+");
        for (String n : g){
            if (!n.equals(""))
                givenNumbers.add(Integer.parseInt(n));
        }
    }

    public int getPoints() {
        int counter = 0;
        for (int number : givenNumbers) {
            if (winningNumbers.contains(number))
                counter++;
        }
        int sum = 0;
        if (counter == 0)
            return 0;
        return (int) Math.pow(2,counter-1);
    }

    public int getNumberOfWinningNumbers(){
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Card "+this.id+" : ");
        for (int number : givenNumbers) {
            if (winningNumbers.contains(number)){
                sb.append(number+", ");
                counter++;
            }
        }
        System.out.println(sb);
        return counter;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Card o) {
        return this.getId() - o.getId();
    }
}


public class Scratchcards {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        Cards cards = new Cards();

        while (!(line = bufferedReader.readLine()).isEmpty()) {
            Card card = new Card();
            card.readCard(line);
            cards.add(card);
        }

        System.out.println(cards.sum());

        System.out.println(cards.getTotal());
    }
}
