package com.rottiakash.pagerank.pagerank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

class Unit
{
    List<String> points;
    double weight;

    public Unit(List<String> points, double weight) {
        this.points = points;
        this.weight = weight;
    }

    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

@SpringBootApplication
public class PageRankApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PageRankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of Iterations:");
        int iter = Integer.parseInt(scanner.nextLine());
        System.out.println("-----------------");
        System.out.print("Enter the number of Nodes:");
        int n = Integer.parseInt(scanner.nextLine());
        System.out.println("-----------------");
        Map<String, Unit> map = new HashMap<>();
        for(int i=0;i<n;i++)
        {
            System.out.print("Enter the name for Node "+ (i+1) + ":");
            String name = scanner.nextLine();
            System.out.println();
            System.out.println("Enter the nodes it points to in comma seperated format:");
            String points = scanner.nextLine();
            map.put(name, new Unit(Arrays.asList(points.split(",")),1/n));
            System.out.println("-----------------");
        }
        List<Map<String,Double>> iterations = new ArrayList<>();
        Map<String,Double> temp = new HashMap<>();
        Map<String, Double> finalTemp = temp;
        map.forEach((key, value)->{
            finalTemp.put(key,1.0/n);
        });
        iterations.add(temp);
        for(int i=1;i<=iter;i++)
        {
            temp = new HashMap<>();
            int finalI = i;
            Map<String, Double> finalTemp1 = temp;
            map.keySet().forEach(key->{
                AtomicReference<Double> weight = new AtomicReference<>(0.0);
                map.forEach((node,value)->{
                    if(value.getPoints().contains(key))
                    {
                        weight.updateAndGet(v -> v + (iterations.get(finalI - 1).get(node)/map.get(node).getPoints().size()));
                    }
                });
                finalTemp1.put(key,weight.get());
            });
            iterations.add(temp);
        }
        System.out.print("               ");
        for(int i=0;i<iterations.size();i++)
        {
            System.out.print("Iteration "+i+"    ");
        }
        System.out.println();
        map.keySet().forEach(key->{
            System.out.print(key+"              ");
            for(int j=0;j<iterations.size();j++)
            {
                DecimalFormat df = new DecimalFormat("####.####");
                System.out.format("%-15s",df.format(iterations.get(j).get(key)));
            }
            System.out.println();
        });
    }
}
