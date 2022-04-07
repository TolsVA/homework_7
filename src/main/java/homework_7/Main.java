package homework_7;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] cities = new String[10];
        GraphImpl graph = new GraphImpl(cities.length);
        for (int i = 0; i < cities.length; i++) {
            cities[i] = graph.lainCities.split(", ")[i];
            graph.addVertex(cities[i]);
        }
        System.out.println(Arrays.toString(cities));

        graph.addEdge("Москва", "Тула", 14, 10);
        graph.addEdge("Тула", "Липецк", 19, 20);
        graph.addEdge("Липецк", "Воронеж", 25, 30);
        graph.addEdge("Москва", "Рязань", 13, 80);
        graph.addEdge("Рязань", "Тамбов", 14, 120);
        graph.addEdge("Тамбов", "Саратов", 15, 90);
        graph.addEdge("Саратов", "Воронеж", 16, 70);
        graph.addEdge("Москва", "Калуга", 17, 80);
        graph.addEdge("Калуга", "Орёл", 18, 90);
        graph.addEdge("Орёл", "Курск", 19, 100);
        graph.addEdge("Курск", "Воронеж", 20, 120);
        graph.addEdge("Тула", "Тамбов", 22, 130);
        graph.addEdge("Калуга", "Тамбов", 23, 140);

        graph.addEdge("Липецк", "Саратов", 25, 30);
        graph.addEdge("Орёл", "Саратов", 19, 100);

        graph.addEdge("Курск", "Саратов", 20, 120);

        System.out.println("Size of graph is " + graph.getSize());
        graph.display();

        graph.dfs("Москва","Воронеж");


        System.out.println();
        graph.bfs("Москва","Воронеж");

        System.out.println();
        System.out.println();
        graph.bfsSi("Москва","Воронеж");
    }
}
