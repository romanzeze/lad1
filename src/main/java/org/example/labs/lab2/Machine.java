package org.example.labs.lab2;

class Machine {
    int number;
    double[] criteria;

    public Machine(int number, double c1, double c2, double c3) {
        this.number = number;
        this.criteria = new double[]{c1, c2, c3};
    }

    @Override
    public String toString() {
        return String.format("Станок: %-3d | (Продуктивність): %-6.2f | (Надійність): %-6.2f | (Ціна): %-6.2f", number, criteria[0], criteria[1], criteria[2]);
    }

    // Метод для обчислення зваженої суми критеріїв
    public double weightedSum(double[] weights) {
        double sum = 0;
        for (int i = 0; i < criteria.length; i++) {
            sum += criteria[i] * weights[i];
        }
        return sum;
    }
}
