import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

class Set {
    private int red;
    private int green;
    private int blue;
    private String line;

    public Set(String line) {
        this.line = line;
        readSet();
    }

    public void readSet() {
        String[] parts = this.line.split(",");
        for (String part : parts) {
            switch (part.split(" ")[2]) {
                case "red" -> this.red = Integer.parseInt(part.split(" ")[1]);
                case "green" -> this.green = Integer.parseInt(part.split(" ")[1]);
                case "blue" -> this.blue = Integer.parseInt(part.split(" ")[1]);
            }
        }
    }

    public boolean isValid(int redLimit, int greenLimit, int blueLimit) {
        if (this.red > redLimit)
            return false;
        else if (this.green > greenLimit)
            return false;
        else if (this.blue > blueLimit)
            return false;
        return true;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}

class Game {
    private int id;
    private ArrayList<Set> sets;
    private String line;

    private int minRed;
    private int minGreen;
    private int minBlue;

    public Game(String line) {
        this.line = line;
        sets = new ArrayList<>();
        readGame();
    }

    public void readGame() {
        String[] parts = this.line.split(":");
        this.id = Integer.parseInt(parts[0].split("\\s+")[1]);
        Arrays.stream(parts[1].split(";"))
                .forEach(part -> sets.add(new Set(part)));
    }

    public boolean isValid(int redLimit, int greenLimit, int blueLimit) {
        for (Set set : sets) {
            if (!set.isValid(redLimit, greenLimit, blueLimit))
                return false;
        }
        return true;
    }

    public void getMinimalSet() {
        for (Set set : sets) {
            if (this.minRed < set.getRed())
                this.minRed = set.getRed();
            if (this.minGreen < set.getGreen())
                this.minGreen = set.getGreen();
            if (this.minBlue < set.getBlue())
                this.minBlue = set.getBlue();
        }
    }

    public int getProduct() {
        getMinimalSet();
        return minRed * minGreen * minBlue;
    }

    public int getId() {
        return id;
    }
}

class Games {
    private ArrayList<Game> games;
    private static final int redLimit = 12;
    private static final int greenLimit = 13;
    private static final int blueLimit = 14;


    public Games() {
        games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public int getPossibleSum() {
        int sum = 0;
        for (Game game : games) {
            if (game.isValid(redLimit, greenLimit, blueLimit))
                sum += game.getId();
        }
        return sum;
    }

    public int getPowerSum() {
        int sum = 0;
        for (Game game : games)
            sum += game.getProduct();
        return sum;
    }

}


public class CubeConundrum {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        Games games = new Games();

        while (!(line = bufferedReader.readLine()).isEmpty()) {
            games.addGame(new Game(line));
        }

        //part 1
        System.out.println(games.getPossibleSum());

        //part 2
        System.out.println(games.getPowerSum());
    }
}
