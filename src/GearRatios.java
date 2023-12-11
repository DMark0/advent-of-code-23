//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Inet4Address;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//class Matrix {
//    private String[][] matrix;
//    private ArrayList<Integer> partNumbers;
//
//    public Matrix() {
//        this.matrix = new String[140][140];
//        this.partNumbers = new ArrayList<>();
//    }
//
//    public void add(String s, int i, int j) {
//        this.matrix[i][j] = s;
//    }
//
//    public int getWholeNumber(int i, int j) {
//        int number = Integer.parseInt(matrix[i][j]);
//        int j2 = j;
//        int counter = 1;
//        //search left
//        while (--j >= 0) {
//            if (matrix[i][j].matches("[0-9]")) {
//                number = Integer.parseInt(matrix[i][j]) * (int)Math.pow(10,counter) + number;
//                counter++;
//            } else
//                break;
//
//        }
//        //search right
//        while (++j2 < matrix[0].length) {
//            if (matrix[i][j2].matches("[0-9]"))
//                number = number * 10 + Integer.parseInt(matrix[i][j2]);
//            else
//                break;
//        }
////        System.out.println("*"+number+"*");
//        return number;
//    }
//
//    public List<Integer> getGearNumbers(int i, int j) {
//        Set<Integer> numbers = new HashSet<>();
//        if (i - 1 >= 0) {
//            if (j - 1 >= 0) {
//                if (matrix[i - 1][j - 1].matches("[0-9]"))
//                    numbers.add(getWholeNumber(i - 1, j - 1));
//            }
//            if (matrix[i - 1][j].matches("[0-9]"))
//                numbers.add(getWholeNumber(i - 1, j));
//            if (j + 1 < matrix[0].length) {
//                if (matrix[i - 1][j + 1].matches("[0-9]"))
//                    numbers.add(getWholeNumber(i - 1, j + 1));
//            }
//        }
//
//        if (j - 1 >= 0) {
//            if (matrix[i][j - 1].matches("[0-9]"))
//                numbers.add(getWholeNumber(i, j - 1));
//        }
//        if (j + 1 < matrix[0].length) {
//            if (matrix[i][j + 1].matches("[0-9]"))
//                numbers.add(getWholeNumber(i, j + 1));
//        }
//
//        if (i + 1 < matrix[0].length) {
//            if (j - 1 >= 0) {
//                if (matrix[i + 1][j - 1].matches("[0-9]"))
//                    numbers.add(getWholeNumber(i + 1, j - 1));
//            }
//            if (matrix[i + 1][j].matches("[0-9]"))
//                numbers.add(getWholeNumber(i + 1, j));
//            if (j + 1 <= matrix[0].length) {
//                if (matrix[i + 1][j + 1].matches("[0-9]"))
//                    numbers.add(getWholeNumber(i + 1, j + 1));
//            }
//        }
//
////        System.out.println("----------");
//        for (int number : numbers) {
////            System.out.println(number);
//        }
//        return numbers.stream().toList();
//    }
//
//    public void getAllValidPartNumbers() {
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                if (matrix[i][j].matches("[0-9]")) {
//                    boolean flag = false;
//                    int number = Integer.parseInt(matrix[i][j]);
//                    flag = flag || isValid(i, j);
//                    while (++j < matrix[i].length) {
//                        if (matrix[i][j].matches("[0-9]")) {
//                            number *= 10;
//                            number += Integer.parseInt(matrix[i][j]);
//                            flag = flag || isValid(i, j);
//                        } else
//                            break;
//                    }
////                    System.out.println(number + " : " + flag);
//                    if (flag)
//                        partNumbers.add(number);
//                }
//            }
//        }
//    }
//
//    public boolean isValid(int i, int j) {
//        if (i - 1 >= 0) {
//            if (j - 1 >= 0) {
//                if (matrix[i - 1][j - 1].matches("[^0-9.]"))
//                    return true;
//            }
//            if (matrix[i - 1][j].matches("[^0-9.]"))
//                return true;
//            if (j + 1 <= 139) {
//                if (matrix[i - 1][j + 1].matches("[^0-9.]"))
//                    return true;
//            }
//        }
//        if (i + 1 <= 139) {
//            if (j - 1 >= 0) {
//                if (matrix[i + 1][j - 1].matches("[^0-9.]"))
//                    return true;
//            }
//            if (matrix[i + 1][j].matches("[^0-9.]"))
//                return true;
//            if (j + 1 <= 139) {
//                if (matrix[i + 1][j + 1].matches("[^0-9.]"))
//                    return true;
//            }
//        }
//        if (j - 1 >= 0) {
//            if (matrix[i][j - 1].matches("[^0-9.]"))
//                return true;
//        }
//        if (j + 1 <= 139) {
//            if (matrix[i][j + 1].matches("[^0-9.]"))
//                return true;
//        }
//        return false;
//    }
//
//    public int getValidSum() {
//        getAllValidPartNumbers();
//        return partNumbers.stream()
//                .reduce(0, Integer::sum);
//    }
//
//    public int getGearSum() {
//        int sum = 0;
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                if (matrix[i][j].matches("[*]")) {
//                    List<Integer> numbers = getGearNumbers(i, j);
//                    int prod = 1;
//                    if (numbers.size() == 2) {
//                        for (int number : numbers)
//                            prod *= number;
//                        sum += prod;
////                        System.out.println(numbers.get(0)+" * "+numbers.get(1));
//                    }
//                }
//            }
//        }
//        return sum;
//    }
//}
//
//
//public class GearRatios {
//
//    public static void main(String[] args) throws IOException {
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        String line;
//
//        ArrayList<String> lines = new ArrayList<>();
//
//        while (!(line = bufferedReader.readLine()).isEmpty())
//            lines.add(line);
//
//        Matrix matrix = new Matrix();
//
//        for (int i = 0; i < lines.size(); i++) {
//            for (int j = 0; j < lines.get(i).length(); j++)
//                matrix.add(Character.toString(lines.get(i).charAt(j)), i, j);
//        }
//        //part 1
//        System.out.println(matrix.getValidSum());
//        //part 2
//        System.out.println(matrix.getGearSum());
//
//    }
//
//}
