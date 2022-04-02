def load_maze(file_name):
    f = open(file_name)
    maze = f.read()
    f.close()
    return maze

def convert_maze(maze):
    converted_maze = []
    lines = maze.splitlines()
    for line in lines:
        converted_maze.append(list(line))
    return converted_maze

def print_maze(maze):
    for row in maze:
        for item in row:
            print(item, end='')
        print()

def find_start(maze):
    for row in range(len(maze)):
        for col in range(len(maze[0])):
            if maze[row][col] == 'L':
                return row, col

from time import sleep

def is_valid_position(maze, pos_r, pos_c):
    if pos_r < 0 or pos_c < 0:
        return False
    if pos_r >= len(maze) or pos_c >= len(maze[0]):
        return False
    if maze[pos_r][pos_c] in 'OH':
        return True
    return False

def solve_maze(maze, start):
    stack = []
    str = []
    stack.append(start)
    while len(stack) > 0:
        pos_r, pos_c = stack.pop()
        print("Current position", pos_r, pos_c)
        if maze[pos_r][pos_c] == 'H':
            print("GOAL")
            return True
        if maze[pos_r][pos_c] == 'X':
            continue
        maze[pos_r][pos_c] = 'X'
        if is_valid_position(maze, pos_r - 1, pos_c):
            stack.append((pos_r - 1, pos_c))
            str.append('U')
        if is_valid_position(maze, pos_r + 1, pos_c):
            stack.append((pos_r + 1, pos_c))
            str.append('D')
        if is_valid_position(maze, pos_r, pos_c - 1):
            stack.append((pos_r, pos_c - 1))
            str.append('L')
        if is_valid_position(maze, pos_r, pos_c + 1):
            stack.append((pos_r, pos_c + 1))
            str.append('R')

        print_maze(maze)
    print(str)
    return False

maze = load_maze("main.txt")
maze = convert_maze(maze)
print_maze(maze)
start = find_start(maze)
print(start)
print(solve_maze(maze, start))
