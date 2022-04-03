package escapemaster;

public class Coordinate {

  private int coordX;
  private int coordY;
  private Coordinate parent;

  public Coordinate(int coordX, int coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
  }

  public void setParent(Coordinate parent) {
    this.parent = parent;
  }

  public Coordinate getParent() {
    return parent;
  }

  public int getCoordX() {
    return coordX;
  }

  public int getCoordY() {
    return coordY;
  }

  public String getMovement(Coordinate next) {
    if (coordX + 1 == next.coordX && coordY == next.coordY) {
      return "R";
    }
    if (coordX - 1 == next.coordX && coordY == next.coordY) {
      return "L";
    }
    if (coordX == next.coordX && coordY + 1 == next.coordY ) {
      return "D";
    }
    if (coordX == next.coordX && coordY - 1 == next.coordY ) {
      return "U";
    }
    throw new UnsupportedOperationException("invalid movement");
  }
}
