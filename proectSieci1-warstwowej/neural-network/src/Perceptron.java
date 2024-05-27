import java.util.Arrays;
import java.util.Random;

public class Perceptron {
    double[] weights;
    int inputSize;

    public Perceptron(int inputSize) {
        this.inputSize = inputSize;
        this.weights = new double[inputSize];
        initializeWeights();
    }

    // Initialize weights with random values between -0.5 and 0.5
    private void initializeWeights() {
        Random random = new Random();
        for (int j = 0; j < inputSize; j++) {
            weights[j] = random.nextDouble() - 0.5;
        }
    }

    // Compute the output of the perceptron using the formula y = Σ w * x (dot product)
    public double output(double[] inputs) {
        double output = 0.0;
        for (int j = 0; j < inputSize; j++) {
            output += weights[j] * inputs[j];
        }
        return output;
    }

    // Update weights using the delta rule and formula w = w + η * x * δ (learning rate * input * delta)
    public void updateWeights(double[] inputs, double learningRate, double delta) {
        for (int j = 0; j < inputSize; j++) {
            weights[j] += learningRate * inputs[j] * delta;
        }
    }

    // Normalize weights to have a unit length with the formula w = w / ||w|| (Euclidean norm)
    public void normalizeWeights() {
        double norm = Math.sqrt(Arrays.stream(weights).map(w -> w * w).sum());
        for (int j = 0; j < inputSize; j++) {
            weights[j] /= norm;
        }
    }
}
