package homework_7;

import java.text.DecimalFormat;
import java.util.*;

public class GraphImpl implements Graph {
    private final List<Vertex> vertexList;
    private final Integer[][] adjMatrix;
    private final boolean[][] verticesMatrix;
    private final Stack<Integer> stackDistance = new Stack<>();
    private final Integer[][] adjMatrixSpeed;
    private final Stack<Integer> stackSpeed = new Stack<>();
    private final HashMap<StringBuilder, String> hm = new HashMap<>();
    private int ras = 0;
    private double time = 0;
    public String lainCities = "Москва, Тула, Липецк, Воронеж, Рязань, Тамбов, Саратов, Калуга, Орёл, Курск";

    public GraphImpl(int maxVertexCount) {
        this.vertexList = new ArrayList<>(maxVertexCount);
        this.adjMatrix = new Integer[maxVertexCount][maxVertexCount];
        this.verticesMatrix = new boolean[maxVertexCount][maxVertexCount];
        this.adjMatrixSpeed = new Integer[maxVertexCount][maxVertexCount];
    }
    @Override
    public void addVertex(String label) {
        vertexList.add(new Vertex(label));
    }

    @Override
    public void addEdge(String startLabel, String secondLabel, Integer integer, Integer integer2) {
        int startIndex = indexOf(startLabel);
        int endIndex = indexOf(secondLabel);

        if (startIndex == -1 || endIndex == -1) {
            return;
        }

        adjMatrix[startIndex][endIndex] = integer;
//        adjMatrix[endIndex][startIndex] = integer;

        adjMatrixSpeed[startIndex][endIndex] = integer2;
//        adjMatrixSpeed[endIndex][startIndex] = integer2;

    }

    private int indexOf(String label) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).getLabel().equals(label)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public int getSize() {
        return vertexList.size();
    }

    private void visitVertex(Stack<Vertex> stack, Vertex vertex) {
        System.out.print(vertex.getLabel() + " ");
        stack.push(vertex);
        vertex.setIsVisited(true);
    }

    private void visitVertex(Queue<Vertex> stack, Vertex vertex) {
        System.out.print(vertex.getLabel() + " ");
        stack.add(vertex);
        vertex.setIsVisited(true);
    }

    @Override
    public void dfs(String startLabel, String endLabel) {
        int startIndex = indexOf(startLabel);

        if (startIndex == -1) {
            throw new IllegalArgumentException("неверная вершина " + startLabel);
        }

        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertex(stack, vertex);
        while (!stack.isEmpty()) {
            vertex = getNearUnvisitedVertex(stack.peek());
            if (vertex != null) {
                visitVertex(stack, vertex);
            } else {
                stack.pop();
            }
        }
        for (Vertex value : vertexList) {
            value.setIsVisited(false);
        }
    }



    private Vertex getNearUnvisitedVertex(Vertex vertex) {
        int currentIndex = vertexList.indexOf(vertex);

        for (int i = 0; i < getSize(); i++) {
            if (adjMatrix[currentIndex][i] != null && vertexList.get(i).getIsVisited()) {
                return vertexList.get(i);
            }
        }

        return null;
    }

    @Override
    public void bfs(String startLabel, String endLabel) {
        int startIndex = indexOf(startLabel);

        if (startIndex == -1) {
            throw new IllegalArgumentException("неверная вершина " + startLabel);
        }

        Queue<Vertex> stack = new LinkedList<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertex(stack, vertex);
        while (!stack.isEmpty()) {
            vertex = getNearUnvisitedVertex(stack.peek());
            if (vertex != null) {
                visitVertex(stack, vertex);
            } else {
                stack.remove();
            }
        }
        for (Vertex value : vertexList) {
            value.setIsVisited(false);
        }
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getSize(); i++) {
            sb.append(vertexList.get(i));
            for (int j = 0; j < getSize(); j++) {
                if (adjMatrix[i][j] != null) {
                    sb.append(" -(").append(adjMatrix[i][j]).append(")-> ").append(vertexList.get(j));
                    sb.append("\n ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void bfsSi(String startLabel, String endLabel) {
        int startIndex = indexOf(startLabel);

        if (startIndex == -1) {
            throw new IllegalArgumentException("неверная вершина " + startLabel);
        }

        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertex(stack, vertex, endLabel);
        while (!stack.isEmpty()) {
            vertex = getUnvisitedVertex(stack);
            if (vertex != null) {
                visitVertex(stack, vertex, endLabel);
                if (vertex.getLabel().equals(endLabel)) {
                    clear(stack, startLabel);
                    stack.pop();
                    stackDistance.pop();
                    stackSpeed.pop();
                }
            } else {
                clear(stack, startLabel);
                stack.pop();
                if (stackDistance.size() != 0) {
                    stackDistance.pop();
                    stackSpeed.pop();
                }
            }
        }
        System.out.println(hm);
        HashMap<StringBuilder, String> newHm = new HashMap<>();
        HashMap<StringBuilder, String> newHmTime = new HashMap<>();
        int min = 1000000000;
        double minSpeed = 10000000000.00;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (Map.Entry<StringBuilder, String> o : hm.entrySet()) {
            String s = o.getValue().split(", ")[0];
            String z = o.getValue().split(", ")[1];
            String str = decimalFormat.format(Double.parseDouble(z));
            if (minSpeed > Double.parseDouble(z)) {
                minSpeed = Double.parseDouble(z);
                newHmTime.clear();
                newHmTime.put(o.getKey(), " " + s + " км., Время в пути - " + str + " ч.");
            }
            if (min > Integer.parseInt(s)) {
                min = Integer.parseInt(s);
                newHm.clear();
                newHm.put(o.getKey(), " " + s + "км., Время в пути - " + str + " ч.");
            }
        }
        System.out.println("Самый короткий путь");
        System.out.println(newHm);
        System.out.println("Самый быстрый путь");
        System.out.println(newHmTime);
    }


    private void clear(Stack<Vertex> stack, String startLabel) {
        int currentIndex = vertexList.indexOf(stack.peek());

        for (int i = 0; i < getSize(); i++) {
            if (!vertexList.get(i).getLabel().equals(startLabel)) {
                verticesMatrix[currentIndex][i] = false;
                verticesMatrix[i][currentIndex] = false;
            }
        }
        vertexList.get(currentIndex).setIsVisited(false);
        if (stack.size() > 1) {
            int currentIndexPrev = vertexList.indexOf(stack.get(stack.size() - 2));
            verticesMatrix[currentIndex][currentIndexPrev] = true;
            verticesMatrix[currentIndexPrev][currentIndex] = true;
        }
    }

    private Vertex getUnvisitedVertex(Stack<Vertex> stack) {
        int currentIndex = vertexList.indexOf(stack.peek());
        for (int i = 0; i < getSize(); i++) {
            if (adjMatrix[currentIndex][i] != null && !verticesMatrix[currentIndex][i] && vertexList.get(i).getIsVisited()) {
                return vertexList.get(i);
            }
        }
        return null;
    }

    private void visitVertex(Stack<Vertex> stack, Vertex vertex, String endLabel) {
        if (stack.size() > 0) {
            int startIndex = indexOf(stack.peek().getLabel());
            int endIndex = indexOf(vertex.getLabel());

            stackDistance.push(adjMatrix[startIndex][endIndex]);
            stackSpeed.push(adjMatrixSpeed[startIndex][endIndex]);

            StringBuilder sb = new StringBuilder();

            if (vertex.getLabel().equals(endLabel)) {
                for (int i = 0; i < stackDistance.size(); i++) {
                    ras += stackDistance.get(i);
                    time += (double) stackDistance.get(i)/stackSpeed.get(i);
                }
                int i;
                for (i = 0; i < stack.size(); i++) {
                    sb.append(stack.get(i));
                    sb.append(" -(").append(stackDistance.get(i)).append(" км. / ");
                    sb.append(stackSpeed.get(i)).append(" км.ч)-> ");
                }
                sb.append(vertex.getLabel()).append(": Общее расстояние ");
                hm.put(sb, ras + ", " + time + "\n");
                ras = 0;
                time = 0;
            }
            verticesMatrix[startIndex][endIndex] = true;
            verticesMatrix[endIndex][startIndex] = true;
        } else {
            int startIndex = indexOf(vertex.getLabel());
            for (int i = 0; i < verticesMatrix.length; i++) {
                verticesMatrix[i][startIndex] = true;
            }
        }
        stack.add(vertex);
        vertex.setIsVisited(true);
    }
}
