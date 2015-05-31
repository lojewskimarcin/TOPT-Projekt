package pl.edu.pw.elka.topt.model;

import pl.edu.pw.elka.topt.util.QAM;

import java.util.List;

public enum Modulation {
    QAM4(2, QAM.POINTS_4QAM),
    QAM16(4, QAM.POINTS_16QAM),
    QAM64(8, QAM.POINTS_64QAM),
    QAM256(16, QAM.POINTS_256QAM);

    private int numberOfBits;
    private List<Point> points;

    Modulation(int numberOfBits, List<Point> points) {
        this.numberOfBits = numberOfBits;
        this.points = points;
    }

    public int getNumberOfBits() {
        return numberOfBits;
    }

    public List<Point> getPoints() {
        return points;
    }
}
