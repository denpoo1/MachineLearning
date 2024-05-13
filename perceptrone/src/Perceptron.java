import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents a Perceptron, a simple machine learning algorithm for binary classification.
 * // Ta klasa reprezentuje perceptron, prosty algorytm uczenia maszynowego do binarnej klasyfikacji.
 */
public class Perceptron {
    private double[] weights; // Wagi perceptronu
    private double learningRate; // Współczynnik uczenia perceptronu
    private int epochs; // Liczba epok uczenia

    /**
     * Constructs a new Perceptron with the given learning rate and number of epochs.
     * // Konstruuje nowy perceptron z podanym współczynnikiem uczenia i liczbą epok.
     *
     * @param learningRate the learning rate
     * @param epochs       the number of training epochs
     */
    public Perceptron(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    /**
     * Runs the training and testing process of the Perceptron.
     * // Uruchamia proces uczenia i testowania perceptronu.
     *
     * @param trainFile the path to the training file
     * @param testFile  the path to the testing file
     */
    public void runTrainingAndTesting(String trainFile, String testFile) {
        var trainingData = loadDataset(trainFile);
        var testingData = loadDataset(testFile);

        trainPerceptron(trainingData);
        testPerceptron(testingData);
    }

    /**
     * Loads a dataset from a file.
     * // Wczytuje zbiór danych z pliku.
     *
     * @param filename the path to the file
     * @return a list of data points
     */
    public List<DataPoint> loadDataset(String filename) {
        var dataPoints = new ArrayList<DataPoint>();
        try {
            Files.lines(Paths.get(filename)).forEach(line -> {
                var values = line.split(",");
                var features = new double[values.length - 1];
                for (int i = 0; i < features.length; i++) {
                    features[i] = Double.parseDouble(values[i]);
                }
                dataPoints.add(new DataPoint(features, values[values.length - 1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPoints;
    }

    /**
     * Trains the Perceptron on the given data points.
     * // Uczy perceptron na podanych punktach danych.
     *
     * @param dataPoints the data points
     */
    public void trainPerceptron(List<DataPoint> dataPoints) {
        weights = new double[dataPoints.get(0).getFeatures().length + 1]; // inicjalizuje wagi na zera
        for (int epoch = 0; epoch < epochs; epoch++) {
            dataPoints.forEach(this::updateWeights); // aktualizuje wagi dla każdego punktu danych
            System.out.println("Accuracy for epoch " + (epoch + 1) + ": " + testPerceptron(dataPoints));
        }
    }

    /**
     * Updates the weights of the Perceptron based on a single data point.
     * // Aktualizuje wagi perceptronu na podstawie pojedynczego punktu danych.
     *
     * @param dataPoint the data point
     */
    private void updateWeights(DataPoint dataPoint) {
        double update = learningRate * (dataPoint.getLabel() - predict(dataPoint.getFeatures()));
        for (int j = 0; j < dataPoint.getFeatures().length; j++) {
            weights[j + 1] += update * dataPoint.getFeatures()[j];
        }
        weights[0] += update; // bias
    }

    /**
     * Predicts the label of a data point based on its features.
     * // Przewiduje etykietę punktu danych na podstawie jego cech.
     *
     * @param features the features of the data point
     * @return the predicted label (1 or 0)
     */
    public int predict(double[] features) {
        double activation = weights[0];
        for (int i = 0; i < features.length; i++) {
            activation += weights[i + 1] * features[i];
        }
        return activation >= 0 ? 1 : 0;
    }

    /**
     * Tests the Perceptron on the given data points and returns the accuracy.
     * // Metoda testująca Perceptron na podanych punktach danych i zwracająca dokładność.
     *
     * @param dataPoints the data points
     * @return the accuracy
     */
    public double testPerceptron(List<DataPoint> dataPoints) {
        // Liczymy ilość poprawnych przewidywań, sprawdzając, czy przewidziana etykieta odpowiada rzeczywistej etykiecie.
        // Считаем количество правильных предсказаний, проверяя, совпадает ли предсказанная метка с фактической меткой.
        long correctPredictions = dataPoints.stream().filter(dataPoint -> predict(dataPoint.getFeatures()) == dataPoint.getLabel()).count();

        // Obliczamy dokładność, dzieląc ilość poprawnych przewidywań przez całkowitą liczbę punktów danych.
        // Вычисляем точность, разделяя количество правильных предсказаний на общее количество точек данных.
        return (double) correctPredictions / dataPoints.size();
    }

    public void askForAttributesAndPredict() {
        Scanner scanner = new Scanner(System.in);
        double[] attributes = new double[this.weights.length - 1]; // Assuming you have 16 attributes

        System.out.println("Please enter the attributes:");
        for (int i = 0; i < attributes.length; i++) {
            System.out.print("Attribute " + (i + 1) + ": ");
            attributes[i] = scanner.nextDouble();
        }

        int prediction = predict(attributes);

        String allLabels = "";
        if (DataPoint.getLabelTypeEnum().equals(LabelTypeEnum.STRING_LABEL)) {
            allLabels = DataPoint.getCurrentStringLabels().toString();
        }

        System.out.println("Predicted class: " + prediction + "\n" + allLabels);
    }


    /**
     * The main method. It prompts the user to enter the learning rate, number of epochs, and paths to the training and testing files.
     * // Metoda główna. Prosi użytkownika o podanie współczynnika uczenia, liczby epok oraz ścieżek do plików treningowych i testowych.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Enter learning rate: ");
        double learningRate = scanner.nextDouble();

        System.out.print("Enter number of epochs: ");
        int epochs = scanner.nextInt();

        System.out.print("Enter path to the training file: ");
        String trainFilePath = scanner.next();

        System.out.print("Enter path to the testing file: ");
        String testFilePath = scanner.next();

        Perceptron perceptron = new Perceptron(learningRate, epochs);
        perceptron.runTrainingAndTesting(trainFilePath, testFilePath);
        perceptron.askForAttributesAndPredict();
    }
}

/**
 * Tests the Perceptron on the given data points and returns the accuracy.
 * // Metoda testująca Perceptron na podanych punktach danych i zwracająca dokładność.
 * <p>
 * This method calculates the accuracy of the Perceptron model by comparing the predicted labels
 * to the actual labels of the given data points. It iterates through each data point, predicts its
 * label using the predict method, and checks if the predicted label matches the actual label. It
 * then counts the number of correct predictions and divides it by the total number of data points
 * to calculate the accuracy. Finally, it returns the accuracy as a double value.
 * // Ta metoda oblicza dokładność modelu Perceptronu, porównując przewidywane etykiety
 * do rzeczywistych etykiet podanych punktów danych. Iteruje przez każdy punkt danych, przewiduje
 * jego etykietę za pomocą metody predict, sprawdza, czy przewidziana etykieta odpowiada rzeczywistej
 * etykiecie. Następnie liczy liczbę poprawnych przewidywań i dzieli ją przez całkowitą liczbę punktów
 * danych, aby obliczyć dokładność. Na koniec zwraca dokładność jako wartość double.
 */


