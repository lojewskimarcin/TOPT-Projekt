package pl.edu.pw.elka.topt.service;

import pl.edu.pw.elka.topt.model.Modulation;
import pl.edu.pw.elka.topt.model.PointExt;
import pl.edu.pw.elka.topt.util.Constant;
import pl.edu.pw.elka.topt.util.Math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateService {

    private List<PointExt> points;
    private double[][] probabilities;

    public Map<Double, Double> generate(Double distance, Double minSigma, Double maxSigma,
                                        Modulation modulation) {
        points = new ArrayList<>();
        probabilities = new double[Constant.CARTESIAN_AXES_SEGMENTS][modulation.getNumberOfBits() + 1];
        modulation.getPoints().forEach(p -> points.add(new PointExt(p, modulation)));
        points.forEach(p -> p.scale(distance));
        double sigmaResolution = (maxSigma - minSigma) / probabilities.length;
        for (int i = 0; i < probabilities.length; i++) {
            final int var = i;
            points.forEach(p1 -> points.forEach(p2 -> p1.addProbability(var, Math.getBinDiff(
                    p1.getValue(), p2.getValue()), Math.getProbability(
                    p1.getX(), p1.getY(), p2, minSigma + (sigmaResolution * var), distance))));
        }
        setProbabilities();
        HashMap results = new HashMap<>();
        for (int i = 0; i < probabilities.length; i++) {
            double MER = 0;
            double BER = 0;
            for (int j = 1; j < probabilities[i].length; j++) {
                MER += probabilities[i][j];
                BER += probabilities[i][j] * j / modulation.getNumberOfBits();
            }
            results.put(MER, BER);
        }
        return results;
    }

    private void setProbabilities() {
        points.forEach(p -> {
            for (int i = 0; i < probabilities.length; i++) {
                for (int j = 0; j < probabilities[i].length; j++) {
                    probabilities[i][j] += p.getProbability(i, j);
                }
            }
        });
        for (int i = 0; i < probabilities.length; i++) {
            for (int j = 0; j < probabilities[i].length; j++) {
                probabilities[i][j] /= points.size();
            }
        }
    }
}
