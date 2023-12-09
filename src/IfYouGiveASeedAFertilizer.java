import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

class Rule {
    private long source;
    private long destination;
    private long length;

    public Rule(long destination, long source, long length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
    }

    public long getSource() {
        return source;
    }

    public long getDestination() {
        return destination;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Rule{" + destination + " " + source + " " + length + " }";
    }
}

class Maps {

    private ArrayList<Rule> seedToSoilRules = null;
    private ArrayList<Rule> soilToFertilizerRules = null;
    private ArrayList<Rule> fertilizerToWaterRules = null;
    private ArrayList<Rule> waterToLightRules = null;
    private ArrayList<Rule> lightToTemperatureRules = null;
    private ArrayList<Rule> temperaturetoHumidityRules = null;
    private ArrayList<Rule> humidityToLocationRules = null;
    private long nearestLocation = Long.MAX_VALUE;
    private long theSeed = 0;

    public long getNearestLocation() {
        return nearestLocation;
    }


    public Maps() {
        seedToSoilRules = new ArrayList<>();
        soilToFertilizerRules = new ArrayList<>();
        fertilizerToWaterRules = new ArrayList<>();
        waterToLightRules = new ArrayList<>();
        lightToTemperatureRules = new ArrayList<>();
        temperaturetoHumidityRules = new ArrayList<>();
        humidityToLocationRules = new ArrayList<>();
    }

    public void addRule(String header, long source, long destination, long length) {
        Rule rule = new Rule(source, destination, length);

        switch (header) {
            case "seed-to-soil map:":
                seedToSoilRules.add(rule);
                break;
            case "soil-to-fertilizer map:":
                soilToFertilizerRules.add(rule);
                break;
            case "fertilizer-to-water map:":
                fertilizerToWaterRules.add(rule);
                break;
            case "water-to-light map:":
                waterToLightRules.add(rule);
                break;
            case "light-to-temperature map:":
                lightToTemperatureRules.add(rule);
                break;
            case "temperature-to-humidity map:":
                temperaturetoHumidityRules.add(rule);
                break;
            case "humidity-to-location map:":
                humidityToLocationRules.add(rule);
                break;
            default:
                System.out.println("Invalid header");
                break;
        }
    }

    public long getLocationFromSeed(Long seed) {
        long soil = seed;
        for (Rule rule : seedToSoilRules) {
            if (seed >= rule.getSource() && seed < rule.getSource() + rule.getLength()) {
                soil = seed + (rule.getDestination() - rule.getSource());
//                System.out.println("seed to soil "+ rule);
                break;
            }
        }

        long fertilizer = soil;
        for (Rule rule : soilToFertilizerRules) {
            if (soil >= rule.getSource() && soil < rule.getSource() + rule.getLength()) {
                fertilizer = soil + (rule.getDestination() - rule.getSource());
//                System.out.println("soil to fert "+ rule);
                break;
            }
        }

        long water = fertilizer;
        for (Rule rule : fertilizerToWaterRules) {
            if (fertilizer >= rule.getSource() && fertilizer < rule.getSource() + rule.getLength()) {
                water = fertilizer + (rule.getDestination() - rule.getSource());
//                System.out.println("fert to h2o "+ rule);
                break;
            }
        }

        long light = water;
        for (Rule rule : waterToLightRules) {
            if (water >= rule.getSource() && water < rule.getSource() + rule.getLength()) {
                light = water + (rule.getDestination() - rule.getSource());
//                System.out.println("h2o to light "+ rule);
                break;
            }
        }

        long temperature = light;
        for (Rule rule : lightToTemperatureRules) {
            if (light >= rule.getSource() && light < rule.getSource() + rule.getLength()) {
                temperature = light + (rule.getDestination() - rule.getSource());
//                System.out.println("light to temp "+ rule);
                break;
            }
        }

        long humidity = temperature;
        for (Rule rule : temperaturetoHumidityRules) {
            if (temperature >= rule.getSource() && temperature < rule.getSource() + rule.getLength()) {
                humidity = temperature + (rule.getDestination() - rule.getSource());
//                System.out.println("temp to hum "+ rule);
                break;
            }
        }

        long location = humidity;
        for (Rule rule : humidityToLocationRules) {
            if (humidity >= rule.getSource() && humidity < rule.getSource() + rule.getLength()) {
                location = humidity + (rule.getDestination() - rule.getSource());
//                System.out.println("hum to loc "+ rule);
                break;
            }
        }
//        System.out.println(seed+"-"+soil+"-"+fertilizer+"-"+water+"-"+light+"-"+temperature+"-"+humidity+"-"+location);
        if (location < nearestLocation){
            nearestLocation = location;
            theSeed = seed;
        }
        return location;
    }


    public void updateNearestLocation(ArrayList<Long> seeds) throws InterruptedException {

        for (Long seed : seeds) {
//            System.out.println("--------" + seed + "---------");
            getLocationFromSeed(seed);
        }
    }

    public void printRules() {
        System.out.println("seed-to-soil");
        for (Rule rule : seedToSoilRules)
            System.out.println(rule);
        System.out.println("soil-to-fert");
        for (Rule rule : soilToFertilizerRules)
            System.out.println(rule);
        System.out.println("fert-to-h2o");
        for (Rule rule : fertilizerToWaterRules)
            System.out.println(rule);
        System.out.println("h2o-to-light");
        for (Rule rule : waterToLightRules)
            System.out.println(rule);
        System.out.println("light-to-temp");
        for (Rule rule : lightToTemperatureRules)
            System.out.println(rule);
        System.out.println("temp-to-hum");
        for (Rule rule : temperaturetoHumidityRules)
            System.out.println(rule);
        System.out.println("hum-to-loc");
        for (Rule rule : humidityToLocationRules)
            System.out.println(rule);


    }

    public long getTheSeed() {
        return theSeed;
    }
}

public class IfYouGiveASeedAFertilizer {

    public static void main(String[] args) throws IOException, InterruptedException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        ArrayList<Long> seeds = new ArrayList<>();

        Maps maps = new Maps();


        long max = 0;
        String header = "";
        while ((line = bufferedReader.readLine()) != null) {

            if (header.equals("humidity-to-location map:") && line.isEmpty())
                break;

            if (line.startsWith("seeds:")) {
                String[] temp = line.split(":")[1].split("\\s+");
                for (String t : temp) {
                    if (!Objects.equals(t, ""))
                        seeds.add(Long.parseLong(t));
                }
            } else if (line.contains("map:")) {
                header = line;
            } else if (line.matches("[0-9]+ [0-9]+ [0-9]+")) {
                String[] parts = line.split("\\s+");
                maps.addRule(header, Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));

            }
        }

        System.out.println("End of reading");
        //part1
        //Maps.updateNearestLocation(seeds);
        //System.out.println(Maps.getNearestLocation());

        //part2
        long sum = 0;
        long counter = 0;
        for (int i = 0; i < seeds.size(); i += 2) {
            sum += seeds.get(i+1);
        }


        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < seeds.size(); i += 2) {
            long startSeed = seeds.get(i);
            long range = seeds.get(i+1);
            System.out.println("Range "+ (i/2+1)+": "+startSeed+"-"+(startSeed+range-1));
            for (long j = 0; j < range; j++) {
                long tempSeed = startSeed+j;
                //System.out.print(tempSeed+" - ");
                maps.getLocationFromSeed(tempSeed);
            }

            final long elapsedTime = System.currentTimeMillis();
            counter+=seeds.get(i+1);

            System.out.printf("%.2f%% %d/%d Elapsed time: %d s, ETA %d s\n",(double) counter /sum*100.0,counter,sum, (elapsedTime-startTime)/1000, (elapsedTime-startTime)/1000 * sum / (counter+1));

        }

        final long endTime = System.currentTimeMillis();


        System.out.println("Duration: " + (endTime - startTime));
        System.out.println("Sum: " + sum);
        System.out.println("Nearest location: " + maps.getNearestLocation() + " from the seed: " +maps.getTheSeed());
    }

}
