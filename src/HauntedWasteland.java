import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HauntedWasteland {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        String directions = bufferedReader.readLine();
        //empty line
        bufferedReader.read();

        Map<String, ArrayList<String>> nodeMap = new HashMap<>();
        String curr = "AAA";
        ArrayList<String> currentNodes = new ArrayList<>();


        while (!(line = bufferedReader.readLine()).isEmpty()) {
            String name = line.split("\\s+")[0];
            if (name.charAt(2) == 'A')
                currentNodes.add(name);
            String left = line.split("\\s+")[2].substring(1, 4);
            String right = line.split("\\s+")[3].substring(0, 3);
            ArrayList<String> lrc = new ArrayList<>();
            lrc.add(left);
            lrc.add(right);

            nodeMap.put(name, lrc);
        }

        System.out.println(nodeMap);

        long longCounter = 0;

        long startTime = System.currentTimeMillis();
        //Part 1
        while (true) {
            if (directions.charAt(0) == 'L')
                curr = nodeMap.get(curr).get(0);
            else if (directions.charAt(0) == 'R')
                curr = nodeMap.get(curr).get(1);

            if (curr.equals("ZZZ")) {
                System.out.println("Fount it!");
                break;
            }
            directions = directions.substring(1) + directions.charAt(0);
            longCounter++;
        }
        long endTime = System.currentTimeMillis();

        System.out.printf("Finished Part 1 in %.2fs!\n", (endTime - startTime) / 1000.0f);
        System.out.println("It took " + (longCounter + 1) + " steps!");


        longCounter = 0;
        startTime = System.currentTimeMillis();
        //Part 2
        System.out.println(currentNodes);
        while (true) {
            boolean flag = true;

            for (int i = 0; i < currentNodes.size(); i++) {
                String nextNode = null;
                if (directions.charAt(0) == 'L')
                    nextNode = nodeMap.get(currentNodes.get(i)).get(0);

                else if (directions.charAt(0) == 'R')
                    nextNode = nodeMap.get(currentNodes.get(i)).get(1);

                currentNodes.set(i, nextNode);

                if (nextNode.charAt(2) != 'Z')
                    flag = false;
            }

            if (flag) {
                System.out.println("Fount it!");
                break;
            }
            directions = directions.substring(1) + directions.charAt(0);
            longCounter++;
        }
        endTime = System.currentTimeMillis();
        System.out.println(currentNodes);
        System.out.printf("Finished Part 2 in %.2fs!\n", (endTime - startTime) / 1000.0f);
        System.out.println("It took " + (longCounter + 1) + " steps!");


    }


}
