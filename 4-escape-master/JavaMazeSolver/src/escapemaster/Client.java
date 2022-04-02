package escapemaster;
import java.net.*;
import java.io.*;
import java.util.ArrayDeque;

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

  public boolean solve_maze(String[][] maze) throws IOException {
    ArrayDeque<Pair<Integer, Integer>> stack = new ArrayDeque<>();
    String result = "";
    stack.add(new Pair<>(0, 0));
    int pos_r, pos_c;
    while (!stack.isEmpty()) {
      Pair<Integer, Integer> pos = stack.pop();
      pos_r = pos.first();
      pos_c = pos.second();

      if (maze[pos_r][pos_c].equals("H")) {
        print_maze(maze);
        System.out.println(result);
        //out.writeBytes(result);
        return true;
      }
      if (maze[pos_r][pos_c].equals("X")) {
        continue;
      }

      maze[pos_r][pos_c] = "X";
      if (is_valid_position(maze, pos_r - 1, pos_c)) {
        stack.add(new Pair<>(pos_r - 1, pos_c));
        result += "U";
      }
      if (is_valid_position(maze, pos_r + 1, pos_c)) {
        stack.add(new Pair<>(pos_r + 1, pos_c));
        result += "D";
      }
      if (is_valid_position(maze, pos_r, pos_c - 1)) {
        stack.add(new Pair<>(pos_r, pos_c - 1));
        result += "L";
      }
      if (is_valid_position(maze, pos_r, pos_c + 1)) {
        stack.add(new Pair<>(pos_r, pos_c + 1));
        result += "R";
      }
      print_maze(maze);
      System.out.println(result);

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

    String[][] test_maze = new String[6][6];
    test_maze[0] = "L # # # # #".split(" ");
    test_maze[1] = "O O O # O #".split(" ");
    test_maze[2] = "# O # # O #".split(" ");
    test_maze[3] = "O O # O O O".split(" ");
    test_maze[4] = "O # # O # O".split(" ");
    test_maze[5] = "O O O O O H".split(" ");

    solve_maze(test_maze);


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

    //solve_maze(maze);

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

