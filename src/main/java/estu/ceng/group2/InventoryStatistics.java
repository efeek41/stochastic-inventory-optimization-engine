package estu.ceng.group2;
/**
 * Utility class for statistical inventory calculations.
 * This class handles Z-table lookups and unit loss function calculations
 * to keep the main optimization logic clean.
 */
public class InventoryStatistics {

    /**
     * Calculates the Z-score for a given cumulative probability (Inverse Standard Normal CDF).
     * Uses Acklam's approximation for high precision.
     * @param p Cumulative probability (0 to 1).
     * @return The corresponding Z-score.
     */
    public static double lookupZTable(double p) {
        if (p <= 0 || p >= 1) return (p <= 0) ? -8.0 : 8.0;

        double a1 = -3.969683028665376e+01, a2 = 2.209460984245205e+02, a3 = -2.759285104469687e+02;
        double a4 = 1.383577518672690e+02, a5 = -3.066479806614716e+01, a6 = 2.506628277459239e+00;
        double b1 = -5.447609879822406e+01, b2 = 1.615858368580409e+02, b3 = -1.556989798598866e+02;
        double b4 = 6.680131188771972e+01, b5 = -1.328068155288572e+01;
        double c1 = -7.784894002430293e-03, c2 = -3.223964580411365e-01, c3 = -2.400758277161838e+00;
        double c4 = -2.549732539343734e+00, c5 = 4.374664141464968e+00, c6 = 2.938163982698783e+00;
        double d1 = 7.784695709041462e-03, d2 = 3.224671290700398e-01, d3 = 2.445134137142996e+00, d4 = 3.754408661907416e+00;

        double q, r, z;
        if (p < 0.02425) {
            q = Math.sqrt(-2 * Math.log(p));
            z = (((((c1 * q + c2) * q + c3) * q + c4) * q + c5) * q + c6) /
                ((((d1 * q + d2) * q + d3) * q + d4) * q + 1);
        } else if (p <= 1 - 0.02425) {
            q = p - 0.5;
            r = q * q;
            z = (((((a1 * r + a2) * r + a3) * r + a4) * r + a5) * r + a6) * q /
                (((((b1 * r + b2) * r + b3) * r + b4) * r + b5) * r + 1);
        } else {
            q = Math.sqrt(-2 * Math.log(1 - p));
            z = -(((((c1 * q + c2) * q + c3) * q + c4) * q + c5) * q + c6) /
                 ((((d1 * q + d2) * q + d3) * q + d4) * q + 1);
        }
        return z;
    }

    /**
     * Calculates the Standard Normal Loss Function L(z).
     * Formula: L(z) = phi(z) - z * (1 - Phi(z)).
     * @param z The Z-score to calculate the loss for.
     * @return The unit loss function value.
     */
    public static double lookupLossFunction(double z) {
        double pdf = Math.exp(-0.5 * z * z) / Math.sqrt(2 * Math.PI);
        double cdf = 0.5 * (1 + errorFunction(z / Math.sqrt(2)));
        return pdf - z * (1 - cdf);
    }

    /**
     * Approximation of the error function (erf) used for CDF calculations.
     */
    private static double errorFunction(double x) {
        double sign = (x < 0) ? -1 : 1;
        x = Math.abs(x);
        double t = 1.0 / (1.0 + 0.3275911 * x);
        double a1 = 0.254829592, a2 = -0.284496736, a3 = 1.421413741, a4 = -1.453152027, a5 = 1.061405429;
        double y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t * Math.exp(-x * x);
        return sign * y;
    }
}