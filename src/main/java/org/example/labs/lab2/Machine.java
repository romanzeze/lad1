package org.example.labs.lab2;

class Machine {
  int number;
     double[] criteria;

    public Machine(int number, double c1, double c2, double c3) {
        this.number = number;
        this.criteria = new double[]{c1, c2, c3};
    }

    public double weightedSum(double[] weights) {
        double sum = 0;
        for (int i = 0; i < criteria.length; i++) {
            if (i == 2) {  // Якщо це третій критерій (вартість)
                sum -= criteria[i] * weights[i]; // Мінімізуємо вартість, тому застосовуємо знак "-"
            } else {
            sum += criteria[i] * weights[i];
        }
        }
        return sum;
    }

    @Override
    public String toString() {
        return String.format("Станок: %-3d | (Продуктивність): %-6.2f | (Надійність): %-6.2f | (Ціна): %-6.2f", number, criteria[0], criteria[1], criteria[2]);
    }
}
