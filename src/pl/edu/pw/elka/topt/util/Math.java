package pl.edu.pw.elka.topt.util;

import pl.edu.pw.elka.topt.model.Point;

public class Math {

    private static double PI = java.lang.Math.PI;

    private static double abs(double d) {
        return java.lang.Math.abs(d);
    }

    private static double pow2(double d) {
        return java.lang.Math.pow(d, 2.0);
    }

    private static double exp(double d) {
        return java.lang.Math.exp(d);
    }

    private static double f(double x, double y, double mx, double my, double sigma) {
        return (1.0 / (2.0 * PI * pow2(sigma))) * exp(-((pow2(abs(x - mx)) + pow2(abs(y - my))) / (2.0 * pow2(sigma))));
    }

    private static double trapezoidRule(double minX, double maxX, double minY, double maxY, double mx, double my,
                                        double sigma, double distance) {
        double xSegSize = Constant.TRAPEZOID_RULE_RESOLUTION * distance;
        double ySegSize = Constant.TRAPEZOID_RULE_RESOLUTION * distance;
        double volume = 0;
        int xSegs = (int) ((maxX - minX) / xSegSize);
        int ySegs = (int) ((maxY - minY) / ySegSize);
        for (int i = 0; i < xSegs; i++) {
            for (int j = 0; j < ySegs; j++) {
                double height = f(minX + (xSegSize * i), minY + (ySegSize * j), mx, my, sigma);
                height += f(minX + (xSegSize * (i + 1.0)), minY + (ySegSize * j), mx, my, sigma);
                height += f(minX + (xSegSize * (i + 1.0)), minY + (ySegSize * (j + 1.0)), mx, my, sigma);
                height += f(minX + (xSegSize * i), minY + (ySegSize * (j + 1.0)), mx, my, sigma);
                height /= 4.0;
                volume += xSegSize * ySegSize * height;
            }
        }
        return volume;
    }

    public static double getProbability(double x, double y, Point p, double sigma, double distance) {
        return trapezoidRule(p.getMinX(), p.getMaxX(), p.getMinY(), p.getMaxY(), x, y, sigma, distance);
    }

    public static int getBinDiff(int val1, int val2) {
        int xor = val1 ^ val2;
        return Integer.bitCount(xor);
    }
}
