package escapemaster;
import java.net.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Client
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
    List<Pair<Integer, Integer>> visited = new ArrayList<>();
    while (!stack.isEmpty()) {
      Coordinate pos = stack.pop();
      pos_r = pos.getCoordY();
      pos_c = pos.getCoordX();

      if (maze[pos_r][pos_c].equals("H")) {
        print_maze(maze);
        String result = backtrackPath(pos);
        System.out.println(result);
        out.writeBytes(result);
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
  public Client(String address, int port) throws IOException {
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

    String[][] maze = new String[50][50];
    maze[0] = line.split(" ");

    //while (!line.equals("Hurry! We only have a few seconds to spare!")) {
    for (int row = 1; row < 50; row++) {

      try
      {
        maze[row] = input.readLine().split(" ");
        //System.out.println(maze[row]);
      }
      catch(IOException i)
      {
        System.out.println(i);
      }
    }

    print_maze(maze);
    solve_maze(maze);

    for (int j = 0; j < 5; j++)
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
    Client client = new Client("192.168.125.100", 9003);
  }
}

