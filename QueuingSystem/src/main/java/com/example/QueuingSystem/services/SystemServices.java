package com.example.QueuingSystem.services;

import com.example.QueuingSystem.model.SystemInputs;
import org.springframework.stereotype.Service;

@Service
public class SystemServices {

    private SystemInputs systemInputs;
    private Double L;
    private Double Lq;
    private Double W;
    private Double Wq;

    private double lambda;
    private double mu;

    public void setSystemInputs(SystemInputs systemInputs) {
        this.systemInputs = systemInputs;

        lambda = systemInputs.getArrivalRate();
        mu = systemInputs.getServiceRate();
    }

    public void handle_MM1_Model() {

        W = (1 / (mu - lambda));

        Wq = ((lambda / mu) * W);

        L = (lambda * W);

        Lq = (lambda * Wq);

    }

    private double k = -1;
    private double lambdaPar;
    private double row;

    public void handle_MM1K_Model() {

        k = systemInputs.getSystemCapacity();

        row = (lambda / mu);
        double rowK = Math.pow(row, k);
        double rowK1 = Math.pow(row, k + 1);

        L = (row * (((1 - ((k + 1) * rowK)) + (k * rowK1)) / ((1 - row) * (1 - rowK1))));

        double Pk;
        if (row == 1) {
            Pk = (1 / (k + 1));
        } else {
            Pk = ((rowK) * (((1 - row) / (1 - rowK1))));
        }

        lambdaPar = (lambda) * (1 - Pk);

        W = (L / lambdaPar);

        Wq = (W - (1 / mu));

        Lq = (lambdaPar * Wq);
    }

    private double c;
    private double r;

    private double summation;
    private double factorialOfC;
    private double P0;
    private double rc;

    public void handle_MMC_Model() {

        defineSomeVariables();
        calculateSummation1();

        if (row < 1) {
            P0 = ((summation) + ((c * rc) / (factorialOfC * (c - r))));
        } else {
            P0 = ((summation) + (((rc / factorialOfC)) * (((c * mu) / ((c * mu) - lambda)))));
        }

        P0 = 1 / P0;

        Lq = P0 * ((((Math.pow(r, c + 1)) / c)) / (factorialOfC * (Math.pow((1 - (r / c)), 2))));

        L = Lq + r;

        Wq = (Lq / lambda);

        W = (Wq + (1 / mu));
    }

    public void handle_MMCK_Model() {

        k = systemInputs.getSystemCapacity();
        defineSomeVariables();
        calculateSummation1();

        double rowCK = Math.pow(row, (k - c + 1));

        if (row != 1) {
            P0 = ((summation) + ((rc / factorialOfC) * ((1 - rowCK) / (1 - row))));
        } else {
            P0 = ((summation) + ((rc / factorialOfC) * ((k - c + 1))));
        }

        P0 = 1 / P0;

        Lq = (((row * rc * P0) / (factorialOfC * (Math.pow((1 - row), 2)))) * (1 - rowCK - ((1 - row) * (k - c + 1) * (Math.pow(row, (k - c))))));

        double summation2 = calculateSummation2();

        L = (Lq + c - (P0 * summation2));

        lambdaPar = calculateLambdaPar(P0);

        W = (L / lambdaPar);

        Wq = (Lq / lambdaPar);
    }

    private void defineSomeVariables() {
        c = systemInputs.getNumberOfServers();
        r = (lambda / mu);
        row = (r / c);
        rc = Math.pow(r, c);
    }

    private void calculateSummation1() {

        summation = 1;
        factorialOfC = 1;

        double factorialOfN = 1;
        for (int i = 1; i <= (c - 1); i++) {
            factorialOfN *= i;
            summation += ((Math.pow(r, i)) / (factorialOfN));
        }
        for (int i = 2; i <= c; i++) factorialOfC *= i;
    }

    private double calculateSummation2() {

        double summation = c;
        double factorialOfN = 1;

        for (int i = 1; i <= (c - 1); i++) {
            factorialOfN *= i;
            summation += ((c - i) * ((Math.pow(r, i)) / (factorialOfN)));
        }

        return summation;
    }

    private double calculateLambdaPar(double P0) {
        double pk = (Math.pow(r, k) / ((Math.pow(c, (k - c))) * factorialOfC)) * P0;
        return (lambda * (1 - pk));
    }

    public Double getL() {
        return L;
    }

    public Double getLq() {
        return Lq;
    }

    public Double getW() {
        return W;
    }

    public Double getWq() {
        return Wq;
    }
}
