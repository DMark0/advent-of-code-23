import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Trebuchet {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        ArrayList<Integer> lineNumbers = new ArrayList<>();
        HashMap<String,Integer> stringNumbers = new HashMap<>();
        //Initialize hashmap
        stringNumbers.put("one",1);
        stringNumbers.put("two",2);
        stringNumbers.put("three",3);
        stringNumbers.put("four",4);
        stringNumbers.put("five",5);
        stringNumbers.put("six",6);
        stringNumbers.put("seven",7);
        stringNumbers.put("eight",8);
        stringNumbers.put("nine",9);

        while (!(line = bufferedReader.readLine()).isEmpty()) {
            Integer firstDigit = null;
            Integer lastDigit = null;

            for (int i = 0; i < line.length(); i++) {
                //part 1
                if ( (Character.isDigit(line.charAt(i))))  {
                    int digit = Integer.parseInt(Character.toString(line.charAt(i)));
                    if (firstDigit == null)
                        firstDigit = digit;
                    lastDigit = digit;
                }
                //part 2
                for (String stringNumber : stringNumbers.keySet()){
                    if (line.startsWith(stringNumber,i)){
                        if (firstDigit == null){
                            firstDigit = stringNumbers.get(stringNumber);
                        }
                        lastDigit = stringNumbers.get(stringNumber);
                    }
                }
            }
            Integer code = firstDigit * 10 + lastDigit;
            System.out.println(line+" : "+code);
            lineNumbers.add(code);
        }

        Integer sum = lineNumbers.stream()
                .reduce(0, Integer::sum);
        System.out.println(sum);

    }
}
