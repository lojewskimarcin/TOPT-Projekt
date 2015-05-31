package pl.edu.pw.elka.topt;

public class QAMGenerator {

    private static final int TYPE = 256;

    public static void main(String[] args) {
        double max = (Math.sqrt(TYPE)) / 2;
        double point = (Math.sqrt(TYPE) - 1) / 2;

        for (double y = point; y >= -point; y -= 1) {
            for (double x = -point; x <= point; x += 1) {

                double minX = x - 0.5;
                double maxX = x + 0.5;
                double minY = y - 0.5;
                double maxY = y + 0.5;

                String minXStr;
                String maxXStr;
                String minYStr;
                String maxYStr;

                if (minX < -point) {
                    minXStr = String.format("-%.0f - Constant.CARTESIAN_AXES_INFINITY", max);
                } else {
                    minXStr = String.format("%.0f", minX);
                }
                if (maxX > point) {
                    maxXStr = String.format("%.0f + Constant.CARTESIAN_AXES_INFINITY", max);
                } else {
                    maxXStr = String.format("%.0f", maxX);
                }
                if (minY < -point) {
                    minYStr = String.format("-%.0f - Constant.CARTESIAN_AXES_INFINITY", max);
                } else {
                    minYStr = String.format("%.0f", minY);
                }
                if (maxY > point) {
                    maxYStr = String.format("%.0f + Constant.CARTESIAN_AXES_INFINITY", max);
                } else {
                    maxYStr = String.format("%.0f", maxY);
                }

                System.out.println(String.format("POINTS_%sQAM.add(new Point(%.1f, %.1f, %s, %s, %s, %s, 0b));",
                        TYPE, x, y, minXStr, maxXStr, minYStr, maxYStr).replace(",5", ".5"));
            }
        }
    }
}
