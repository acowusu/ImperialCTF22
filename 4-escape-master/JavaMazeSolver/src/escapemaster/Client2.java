package escapemaster;
import java.net.*;
import java.io.*;
import java.util.ArrayDeque;

public class Client2
{
  // initialize socket and input output streams
  private Socket socket		 = null;
  private DataInputStream input = null;
  private DataOutputStream out	 = null;

  private void print_maze(String[][] maze) {
    for (String[] row : maze) {
      for (String col : row) {
        System.out.print(col + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  private boolean is_valid_position(String[][] maze, int pos_r, int pos_c) {
    return pos_r >= 0 && pos_r < maze.length && pos_c >= 0 && pos_c < maze[0].length && "OH".contains(maze[pos_r][pos_c]);
  }

  private String backtrackPath(Coordinate cur) {
    String path = "";
    Coordinate curr = cur.getParent();
    Coordinate prev = cur;

    while (curr != null) {
      path = curr.getMovement(prev) + path;

      prev = curr;
      curr = curr.getParent();
    }

    return path;
  }

  public boolean solve_maze(String[][] maze) throws IOException {
    ArrayDeque<Coordinate> stack = new ArrayDeque<>();
    stack.add(new Coordinate(0, 0));
    int pos_r, pos_c;
    while (!stack.isEmpty()) {
      Coordinate pos = stack.pop();
      pos_r = pos.getCoordY();
      pos_c = pos.getCoordX();

      if (maze[pos_r][pos_c].equals("H")) {
        //print_maze(maze);
        String result = backtrackPath(pos);
        System.out.println(result);
        out.writeChars(result);
        return true;
      }
      if (maze[pos_r][pos_c].equals("X")) {
        continue;
      }

      maze[pos_r][pos_c] = "X";
      if (is_valid_position(maze, pos_r - 1, pos_c)) {
        Coordinate nextPos = new Coordinate(pos_c, pos_r - 1);
        nextPos.setParent(pos);
        stack.add(nextPos);
        //result += "U";
      }
      if (is_valid_position(maze, pos_r + 1, pos_c)) {
        Coordinate nextPos = new Coordinate(pos_c, pos_r + 1);
        nextPos.setParent(pos);
        stack.add(nextPos);
        //result += "D";
      }
      if (is_valid_position(maze, pos_r, pos_c - 1)) {
        Coordinate nextPos = new Coordinate(pos_c - 1, pos_r);
        nextPos.setParent(pos);
        stack.add(nextPos);
        //result += "L";
      }
      if (is_valid_position(maze, pos_r, pos_c + 1)) {
        Coordinate nextPos = new Coordinate(pos_c + 1, pos_r);
        nextPos.setParent(pos);
        stack.add(nextPos);
        //result += "R";
      }
      //print_maze(maze);
    }
    System.out.println("False");
    return false;
  }

  // constructor to put ip address and port
  public Client2(String address, int port) throws IOException {
    // establish a connection
    try
    {
      socket = new Socket(address, port);
      System.out.println("Connected");

      // takes input from terminal
      input = new DataInputStream(socket.getInputStream());

      // sends output to the socket
      out = new DataOutputStream(socket.getOutputStream());
    }
    catch(UnknownHostException u)
    {
      System.out.println(u);
    }
    catch(IOException i)
    {
      System.out.println(i);
    }

    /*
    String[][] test_maze = new String[6][6];
    test_maze[0] = "L # # # # #".split(" ");
    test_maze[1] = "O O O # O #".split(" ");
    test_maze[2] = "# O # # O #".split(" ");
    test_maze[3] = "O O # O O O".split(" ");
    test_maze[4] = "O # # O # O".split(" ");
    test_maze[5] = "O O O O O H".split(" ");

    solve_maze(test_maze);

     */


    // string to read message from input
    String line = "";

    // keep reading until "Over" is input
    //while (!line.equals("  DRDDLDDRRRUURRDD"))
    for (int j = 0; j < 22; j++)
    {
      try
      {
        line = input.readLine();
        System.out.println(line);
      }
      catch(IOException i)
      {
        System.out.println(i);
      }
    }


    try {
      out.writeBytes("y");
    } catch (IOException e) {
      e.printStackTrace();
    }

    line = input.readLine();
    line = line.substring("Ready? (Y/N)".length());

    char[][] maze = new char[50][50];
    String[][] strMaze = new String[50][50];
    strMaze[0] = line.split(" ");

    for (int i = 0; i < 50; i++) {
      maze[0][i] = strMaze[0][i].charAt(0);
    }
    /*
    for (int i = 0; i < maze[0].length; i++) {
      System.out.print(maze[0][i] + " ");
    }
    System.out.println();

     */

    //while (!line.equals("Hurry! We only have a few seconds to spare!")) {
    for (int row = 1; row < 50; row++) {

      try
      {
        strMaze[row] = input.readLine().split(" ");
        for (int i = 0; i < 50; i++) {
          maze[row][i] = strMaze[row][i].charAt(0);
        }
        /*
        for (int i = 0; i < maze[row].length; i++) {
          System.out.print(maze[row][i] + " ");
        }
        System.out.println();
         */
      }
      catch(IOException i)
      {
        System.out.println(i);
      }
    }

    for (int j = 0; j < 2; j++)
    {
      try
      {
        line = input.readLine();
        System.out.println(line);
      }
      catch(IOException i)
      {
        System.out.println(i);
      }
    }

    Maze m = new Maze(maze, maze.length, maze[0].length);

    m.solve(new Node(0,0));
    m.fillPath();

    String result = m.findPath();
    System.out.println(result);

    out.writeBytes(result);

    for (int j = 0; j < 3; j++)
    {
      try
      {
        line = input.readLine();
        System.out.println(line);
      }
      catch(IOException i)
      {
        System.out.println(i);
      }
    }

    // close the connection
    try
    {
      input.close();
      out.close();
      socket.close();
    }
    catch(IOException i)
    {
      System.out.println(i);
    }
  }

  public static void main(String args[]) throws IOException {
    Client2 client2 = new Client2("192.168.125.100", 9003);
  }
}

