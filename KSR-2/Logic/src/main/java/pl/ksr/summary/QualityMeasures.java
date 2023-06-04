package pl.ksr.summary;

import java.util.List;

public class QualityMeasures {
    private final double T_1;
    private double T_2;
    private double T_3;
    private double T_4;
    private double T_5;
    private double T_6;
    private double T_7;
    private double T_8;
    private double T_9;
    private double T_10;
    private double T_11;

    public QualityMeasures(double t_1, double t_2, double t_3, double t_4, double t_5, double t_6, double t_7,
                           double t_8, double t_9, double t_10, double t_11) {
        T_1 = t_1;
        T_2 = t_2;
        T_3 = t_3;
        T_4 = t_4;
        T_5 = t_5;
        T_6 = t_6;
        T_7 = t_7;
        T_8 = t_8;
        T_9 = t_9;
        T_10 = t_10;
        T_11 = t_11;
    }

    public QualityMeasures(double t_1) {
        this.T_1 = t_1;
    }

    // An extended concept of the optimal summary
    public double calculateOptimalSummary(List<Double> weights) {
        double T = 0;
        List<Double> Ts = getAll();
        for (int i = 0; i < Ts.size(); i++) {
            if (!Double.isNaN(Ts.get(i))) {
                T += weights.get(i) * Ts.get(i);
            }
        }

        return T;
    }

    public List<Double> getAll() {
        return List.of(T_1, T_2, T_3, T_4, T_5, T_6, T_7, T_8, T_9, T_10, T_11);
    }

    public double getT_1() {
        return T_1;
    }

    public double getT_2() {
        return T_2;
    }

    public double getT_3() {
        return T_3;
    }

    public double getT_4() {
        return T_4;
    }

    public double getT_5() {
        return T_5;
    }

    public double getT_6() {
        return T_6;
    }

    public double getT_7() {
        return T_7;
    }

    public double getT_8() {
        return T_8;
    }

    public double getT_9() {
        return T_9;
    }

    public double getT_10() {
        return T_10;
    }
    public double getT_11() {
        return T_11;
    }
}
