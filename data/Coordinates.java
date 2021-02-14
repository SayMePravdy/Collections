package data;

public class Coordinates {
    private double x;
    private float y; //Максимальное значение поля: 266

    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "data.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
