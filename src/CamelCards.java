import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;

enum Type{
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD,
    ERROR
}


class SortByHand implements Comparator<Hand>{

    private ArrayList<Type> types;
    private ArrayList<String> strengthRanking;


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
    public int compare(Hand o1, Hand o2) {
        int result = types.indexOf(o2.getType()) - types.indexOf(o1.getType());
        if (result!=0)
            return result;

        CamelCard[] o1Cards = o1.getCards();
        CamelCard[] o2Cards = o2.getCards();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++)
            sb.append(o1Cards[i]);
        sb.append(" vs ");
        for (int i = 0; i < 5; i++)
            sb.append(o2Cards[i]);

        System.out.println(sb);


        for (int i = 0; i < 5; i++) {
            result = strengthRanking.indexOf(o2Cards[i].getStrength()) - strengthRanking.indexOf(o1Cards[i].getStrength());

            if(result!=0){
                return result;
            }
        }
        return result;
    }
}

class SortByHand2 implements Comparator<Hand>{

    private ArrayList<Type> types;
    private ArrayList<String> strengthRanking;


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
    public int compare(Hand o1, Hand o2) {
        int result = types.indexOf(o2.getType()) - types.indexOf(o1.getType());
        if (result!=0)
            return result;

        CamelCard[] o1Cards = o1.getCards();
        CamelCard[] o2Cards = o2.getCards();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++)
            sb.append(o1Cards[i]);
        sb.append(" vs ");
        for (int i = 0; i < 5; i++)
            sb.append(o2Cards[i]);

        System.out.println(sb);


        for (int i = 0; i < 5; i++) {
            result = strengthRanking.indexOf(o2Cards[i].getStrength()) - strengthRanking.indexOf(o1Cards[i].getStrength());

            if(result!=0){
                return result;
            }
        }
        return result;
    }
}


class CamelCard{
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

class Hand{
    private CamelCard[] cards;
    private long bet;
    private Type type;

    public Hand(String line) {
        Map<String,Integer> counts = new HashMap<>();
        cards = new CamelCard[5];
        String[] parts = line.split("\\s+");
        String[] rawCards = parts[0].split("");
        bet = Long.parseLong(parts[1]);

        for (int i = 0; i < 5; i++) {
            cards[i] = new CamelCard(rawCards[i]);
            if (counts.containsKey(rawCards[i]))
                counts.replace(rawCards[i],counts.get(rawCards[i])+1);
            else
                counts.put(rawCards[i],1);
        }
        type = setType(counts);
    }

    public Type setType(Map<String,Integer> counts){

        switch (counts.values().size()){
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
        for (CamelCard card : cards){
            sb.append(card.getStrength());
        }
        sb.append("-"+bet+"-"+type);
        return sb.toString();
    }
}


public class CamelCards {

    public static void main(String[] args) throws IOException {

        ArrayList<Hand> hands = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while (!(line=bufferedReader.readLine()).isEmpty()){
            String[] parts = line.split("\\s+");
            hands.add(new Hand(line));
        }

        System.out.println(hands);

        hands.sort(new SortByHand());

        System.out.println(hands);

        long sum = 0;

        for (Hand hand : hands){
            System.out.println(hand.getBet()+" * "+(hands.indexOf(hand)+1));

            long prod = hand.getBet() * (hands.indexOf(hand)+1);
            sum += prod;
        }

        System.out.println(sum);


    }

}
