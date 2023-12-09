import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class WaitForIt {

    public static void getCode(TreeMap<Long, Long> timeDistanceMap){
        long result = 1;

        for (long time : timeDistanceMap.keySet()) {
            long counter = 0;
            for (long holdDuration = 0; holdDuration <= time; holdDuration++) {
                long driveDuration = time - holdDuration;
                long distanceDriven = holdDuration * driveDuration;
                if (distanceDriven > timeDistanceMap.get(time))
                    counter++;
            }
            result *= counter;
        }

        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] timeLine = bufferedReader.readLine().split("\\s+");
        String[] distanceLine = bufferedReader.readLine().split("\\s+");
        TreeMap<Long, Long> timeDistance = new TreeMap<>();
        TreeMap<Long, Long> timeDistance2 = new TreeMap<>();

        String newTime = "";
        String newDistance = "";

        for (int i = 1; i < timeLine.length; i++) {
            timeDistance.put(Long.parseLong(timeLine[i]), Long.parseLong(distanceLine[i]));
            newTime = newTime.concat(timeLine[i]);
            newDistance = newDistance.concat(distanceLine[i]);
        }

        timeDistance2.put(Long.parseLong(newTime),Long.parseLong(newDistance));

        getCode(timeDistance);
        getCode(timeDistance2);




    }

}
