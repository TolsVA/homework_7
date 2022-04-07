package homework_7;

public interface Graph {

    void addVertex(String label);

    void addEdge(String startLabel, String secondLabel, Integer integer, Integer integer2);

    int getSize();

    void display();

//  Поиск в глубину
    void dfs(String startLabel, String endLabel);

//  Поиск в ширину
    void bfs(String startLabel, String endLabel);

    void bfsSi(String startLabel, String endLabel);

}
