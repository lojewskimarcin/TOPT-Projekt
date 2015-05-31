package pl.edu.pw.elka.topt.model;

public class Point {
    private double x;
    private double y;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private int value;

    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
        this.minX = point.getMinX();
        this.maxX = point.getMaxX();
        this.minY = point.getMinY();
        this.maxY = point.getMaxY();
        this.value = point.getValue();
    }

    public Point(double x, double y, double minX, double maxX, double minY, double maxY, int value) {
        this.x = x;
        this.y = y;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.value = value;
    }

    public void scale(Double distance) {
        x *= distance;
        y *= distance;
        minX *= distance;
        maxX *= distance;
        minY *= distance;
        maxY *= distance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public int getValue() {
        return value;
    }
}
