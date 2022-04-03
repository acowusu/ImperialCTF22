package escapemaster;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
public class Maze {
  private final char[][] maze;
  private final Node[][] prev;
  private final int sizeX;
  private final int sizeY;
  private Node lastNode;
  Maze(char[][] maze, int sizeX, int sizeY) {
    this.maze = maze;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    prev = new Node[sizeX][sizeY];
  }
  private boolean inBoundsX(int number){
    return number >= 0 && number < sizeX;
  }
  private boolean inBoundsY(int number){
    return number >= 0 && number < sizeY;
  }
  public void solve(Node start){
    Stack<Node> stack = new Stack<>();
    HashSet<Node> visited = new HashSet<>();
    stack.push(start);
    while(!stack.isEmpty()) {
      Node tmp = stack.pop();
      visited.add(tmp);
      if (maze[tmp.getX()][tmp.getY()] == 'H') {
        lastNode = tmp;
        break;
      }
      for(Node node : this.getAdjacentEdges(tmp)) {
        if (!visited.contains(node)) {
          stack.push(node);
          prev[node.getX()][node.getY()] = tmp;
        }
      }
    }
  }
  public String findPath(){
    String str = "";
    int i = 0, j = 0;
    while (i < sizeX && j < sizeY) {
      if (inBoundsY(j - 1) && maze[i][j - 1] == 'X') {
        maze[i][j - 1] = '*';
        str += "L";
        j--;
      }
      else if (inBoundsX(i - 1) && maze[i - 1][j] == 'X') {
        maze[i - 1][j] = '*';
        str += "U";
        i--;
      }
      else if (inBoundsY(j + 1) && maze[i][j + 1] == 'X') {
        maze[i][j + 1] = '*';
        str += "R";
        j++;
      }
      else if (inBoundsX(i + 1) && maze[i + 1][j] == 'X') {
        maze[i + 1][j] = '*';
        str += "D";
        i++;
      }
      else break;
    }
    return str;
  }
  public void fillPath() {
    if (lastNode == null) {
      System.out.println("No path in maze");
    } else {
      for (;;) {
        int x = lastNode.getX();
        int y = lastNode.getY();
        lastNode = prev[x][y];
        if (lastNode == null) {
          break;
        }
        maze[x][y] = 'X';
      }
    }
  }
  private List<Node> getAdjacentEdges(Node tmp) {
    List<Node> neighbours = new ArrayList<Node>();
    if(this.inBoundsX(tmp.getX()+1)){
      if(this.maze[tmp.getX() + 1][tmp.getY()] != '#'){
        neighbours.add(new Node(tmp.getX()+1, tmp.getY()));
      }
    }
    if(this.inBoundsX(tmp.getX()-1)){
      if(this.maze[tmp.getX() - 1][tmp.getY()] != '#'){
        neighbours.add(new Node(tmp.getX()-1, tmp.getY()));
      }
    }
    if(this.inBoundsY(tmp.getY()+1)){
      if(this.maze[tmp.getX()][tmp.getY() + 1] != '#'){
        neighbours.add(new Node(tmp.getX(), tmp.getY()+1));
      }
    }
    if(this.inBoundsY(tmp.getY()-1)){
      if(this.maze[tmp.getX()][tmp.getY() - 1] != '#'){
        neighbours.add(new Node(tmp.getX(), tmp.getY()-1));
      }
    }
    return neighbours;
  }

  public static void main(String args[]){
    char[][] maze =
        {
            {'L','#','#','#','#','#'},
            {'O','O','O','#','O','#'},
            {'#','O','#','#','O','#'},
            {'O','O','#','O','O','O'},
            {'O','#','#','O','#','O'},
            {'O','O','O','O','O','H'},
        };
    Maze m = new Maze(maze, maze.length, maze[0].length);
    m.solve(new Node(0,0));
    m.fillPath();
    for(int i = 0; i < maze.length; i++){
      for(int j = 0; j < maze[0].length; j++){
        System.out.print(" " + maze[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println(m.findPath());
  }
}