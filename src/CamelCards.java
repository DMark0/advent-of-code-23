import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;

enum Type {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD,
    ERROR
}

class SortByStrength implements Comparator<String> {

    private ArrayList<String> strengthRanking;

    public SortByStrength() {
        this.strengthRanking = new ArrayList<>();
        strengthRanking.add("A");
        strengthRanking.add("K");
        strengthRanking.add("Q");
        strengthRanking.add("J");
        strengthRanking.add("T");
        strengthRanking.add("9");
        strengthRanking.add("8");
        strengthRanking.add("7");
        strengthRanking.add("6");
        strengthRanking.add("5");
        strengthRanking.add("4");
        strengthRanking.add("3");
        strengthRanking.add("2");
    }

    @Override
    public int compare(String o1, String o2) {
        return strengthRanking.indexOf(o2) - strengthRanking.indexOf(o1);
    }
}

class SortByStrength2 implements Comparator<String> {

    private ArrayList<String> strengthRanking;

    public SortByStrength2() {
        this.strengthRanking = new ArrayList<>();
        strengthRanking.add("A");
        strengthRanking.add("K");
        strengthRanking.add("Q");
        strengthRanking.add("T");
        strengthRanking.add("9");
        strengthRanking.add("8");
        strengthRanking.add("7");
        strengthRanking.add("6");
        strengthRanking.add("5");
        strengthRanking.add("4");
        strengthRanking.add("3");
        strengthRanking.add("2");
        strengthRanking.add("J");
    }

    @Override
    public int compare(String o1, String o2) {
        return strengthRanking.indexOf(o2) - strengthRanking.indexOf(o1);
    }
}

class SortByHand implements Comparator<Hand> {

    private ArrayList<Type> types;
    private SortByStrength sortByStrength;


    public SortByHand() {
        types = new ArrayList<>();
        types.add(Type.FIVE_OF_A_KIND);
        types.add(Type.FOUR_OF_A_KIND);
        types.add(Type.FULL_HOUSE);
        types.add(Type.THREE_OF_A_KIND);
        types.add(Type.TWO_PAIR);
        types.add(Type.ONE_PAIR);
        types.add(Type.HIGH_CARD);
        types.add(Type.ERROR);

        sortByStrength = new SortByStrength();

    }

    @Override
    public int compare(Hand o1, Hand o2) {
        int result = types.indexOf(o2.getType()) - types.indexOf(o1.getType());
        if (result != 0)
            return result;

        CamelCard[] o1Cards = o1.getCards();
        CamelCard[] o2Cards = o2.getCards();

        for (int i = 0; i < 5; i++) {
            result = sortByStrength.compare(o1Cards[i].getStrength(), o2Cards[i].getStrength());
            if (result != 0)
                return result;
        }
        return result;
    }
}

class SortByHand2 implements Comparator<Hand> {

    private ArrayList<Type> types;
    private SortByStrength2 sortByStrength;


    public SortByHand2() {
        types = new ArrayList<>();
        types.add(Type.FIVE_OF_A_KIND);
        types.add(Type.FOUR_OF_A_KIND);
        types.add(Type.FULL_HOUSE);
        types.add(Type.THREE_OF_A_KIND);
        types.add(Type.TWO_PAIR);
        types.add(Type.ONE_PAIR);
        types.add(Type.HIGH_CARD);
        types.add(Type.ERROR);

        sortByStrength = new SortByStrength2();

    }

    @Override
    public int compare(Hand o1, Hand o2) {
        int result = types.indexOf(o2.getType()) - types.indexOf(o1.getType());
        if (result != 0)
            return result;

        CamelCard[] o1Cards = o1.getCards();
        CamelCard[] o2Cards = o2.getCards();

        for (int i = 0; i < 5; i++) {
            result = sortByStrength.compare(o1Cards[i].getStrength(), o2Cards[i].getStrength());
            if (result != 0)
                return result;
        }
        return result;
    }
}


class CamelCard {
    private String strength;

    public CamelCard(String strength) {
        this.strength = strength;
    }

    public String getStrength() {
        return strength;
    }

    @Override
    public String toString() {
        return getStrength();
    }
}

class Hand {
    private CamelCard[] cards;
    private long bet;
    private Type type;


    public Hand(String line) {
        Map<String, Integer> counts = new HashMap<>();
        cards = new CamelCard[5];
        String[] parts = line.split("\\s+");
        String[] rawCards = parts[0].split("");
        bet = Long.parseLong(parts[1]);

        for (int i = 0; i < 5; i++) {
            cards[i] = new CamelCard(rawCards[i]);
            if (counts.containsKey(rawCards[i]))
                counts.replace(rawCards[i], counts.get(rawCards[i]) + 1);
            else
                counts.put(rawCards[i], 1);
        }
        // part 1
        //type = setType(counts);
        //part 2
        type = adjustCount(counts);
    }


    public Type adjustCount(Map<String, Integer> counts) {

        Integer jCount = counts.remove("J");
        if (jCount != null) {
            if (jCount == 5)
                counts.put("A", 5);
            else {
                SortByStrength2 sortByStrength2 = new SortByStrength2();
                String bestCardStrength = "J";
                int bestCardCount = 1;

                for (String strength : counts.keySet()) {
                    if (counts.get(strength) > bestCardCount) {
                        bestCardStrength = strength;
                        bestCardCount = counts.get(strength);
                    } else if (counts.get(strength) == bestCardCount) {
                        if (sortByStrength2.compare(strength, bestCardStrength) > 0) {
                            bestCardCount = counts.get(strength);
                            bestCardStrength = strength;
                        }
                    }
                }
                counts.replace(bestCardStrength, bestCardCount + jCount);
            }
        }

        return setType(counts);
    }

    public Type setType(Map<String, Integer> counts) {


        switch (counts.values().size()) {
            case 1:
                return Type.FIVE_OF_A_KIND;
            case 2:
                if (counts.containsValue(4))
                    return Type.FOUR_OF_A_KIND;
                return Type.FULL_HOUSE;
            case 3:
                if (counts.containsValue(3))
                    return Type.THREE_OF_A_KIND;
                return Type.TWO_PAIR;
            case 4:
                return Type.ONE_PAIR;
            case 5:
                return Type.HIGH_CARD;
        }
        return Type.ERROR;
    }

    public Type getType() {
        return type;
    }

    public CamelCard[] getCards() {
        return cards;
    }

    public long getBet() {
        return bet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CamelCard card : cards) {
            sb.append(card.getStrength());
        }
        sb.append("-" + bet + "-" + type);
        return sb.toString();
    }
}


public class CamelCards {

    public static void main(String[] args) throws IOException {

        ArrayList<Hand> hands = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while (!(line = bufferedReader.readLine()).isEmpty()) {
            hands.add(new Hand(line));
        }

        System.out.println(hands);
        //part 1
        //hands.sort(new SortByHand());
        //part 2
        hands.sort(new SortByHand2());

        System.out.println(hands);

        long sum = 0;

        for (Hand hand : hands) {
            System.out.println(hand + " " + hand.getBet() + " * " + (hands.indexOf(hand) + 1));

            long prod = hand.getBet() * (hands.indexOf(hand) + 1);
            sum += prod;
        }

        System.out.println(sum);
    }

}
