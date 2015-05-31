package pl.edu.pw.elka.topt.model;

import pl.edu.pw.elka.topt.util.Constant;

public class PointExt extends Point {
    private double[][] probabilities;

    public PointExt(Point point, Modulation modulation) {
        super(point);
        probabilities = new double[Constant.CARTESIAN_AXES_SEGMENTS][modulation.getNumberOfBits() + 1];
    }

    public void addProbability(int segment, int bitDifference, double probability) {
        probabilities[segment][bitDifference] += probability;
    }

    public double getProbability(int segment, int bitDifference) {
        return probabilities[segment][bitDifference];
    }
}
