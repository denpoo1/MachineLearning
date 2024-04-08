import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Perceptron {
    private double[] weights;
    private double learningRate;
    private int epochs;

    public Perceptron(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    public void runTrainingAndTesting(String trainFile, String testFile) {
        List<double[]> X_train = new ArrayList<>();
        List<double[]> X_test = new ArrayList<>();
        List<Integer> y_train = new ArrayList<>();
        List<Integer> y_test = new ArrayList<>();

        loadDataset(trainFile, X_train, y_train);
        loadDataset(testFile, X_test, y_test);

        trainPerceptron(X_train, y_train.stream().mapToInt(i -> i).toArray());
        testPerceptron(X_test, y_test.stream().mapToInt(i -> i).toArray());
    }

    private void loadDataset(String filename, List<double[]> X, List<Integer> y) {
        try {
            List<String> lines = Files.lines(Paths.get(filename)).toList();
            for (String line : lines) {
                String[] values = line.split(",");
                double[] features = new double[values.length - 1];
                for (int i = 0; i < features.length; i++) {
                    features[i] = Double.parseDouble(values[i]);
                }
                X.add(features);
                y.add(Integer.parseInt(values[values.length - 1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initializeWeights(int size) {
        weights = new double[size];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 0.2 - 0.1; // small random initialization
        }
    }

    private void trainPerceptron(List<double[]> X, int[] y) {
        initializeWeights(X.get(0).length + 1);
        for (int epoch = 0; epoch < epochs; epoch++) {
            shuffleData(X, y);
            for (int i = 0; i < X.size(); i++) {
                updateWeights(X.get(i), y[i]);
            }
            double accuracy = testPerceptron(X, y);
            System.out.println("Accuracy for epoch " + (epoch + 1) + ": " + accuracy);
        }
    }

    private void shuffleData(List<double[]> X, int[] y) {
        for (int i = X.size() - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));
            double[] tempX = X.get(index);
            X.set(index, X.get(i));
            X.set(i, tempX);
            int tempY = y[index];
            y[index] = y[i];
            y[i] = tempY;
        }
    }

    private void updateWeights(double[] xi, int target) {
        double update = learningRate * (target - predict(xi));
        for (int j = 0; j < xi.length; j++) {
            weights[j + 1] += update * xi[j];
        }
        weights[0] += update; // bias
    }

    private int predict(double[] X) {
        double activation = weights[0];
        for (int i = 0; i < X.length; i++) {
            activation += weights[i + 1] * X[i];
        }
        return activation >= 0 ? 1 : 0;
    }

    private double testPerceptron(List<double[]> X, int[] y) {
        int correctPredictions = 0;
        for (int i = 0; i < X.size(); i++) {
            int prediction = predict(X.get(i));
            if (prediction == y[i]) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / X.size();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter learning rate: ");
        double learningRate = scanner.nextDouble();

        System.out.print("Enter number of epochs: ");
        int epochs = scanner.nextInt();

        String trainFilePath = scanner.nextLine();
        System.out.print("Enter path to the training file: ");
        trainFilePath = scanner.nextLine();

        System.out.print("Enter path to the testing file: ");
        String testFilePath = scanner.nextLine();

        Perceptron perceptron = new Perceptron(learningRate, epochs);
        perceptron.runTrainingAndTesting(trainFilePath, testFilePath);

        scanner.close();
    }
}
