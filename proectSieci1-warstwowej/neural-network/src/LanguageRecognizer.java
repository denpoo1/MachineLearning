import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LanguageRecognizer {

    private Perceptron[] perceptrons;
    private String[] languages;
    private int inputSize = 128; // ASCII table size

    // Sieć neuronowa ma tyle neuronów, ile jest unikalnych języków w zbiorze danych.
    public LanguageRecognizer(String[] languages) {
        this.languages = languages;
        this.perceptrons = new Perceptron[languages.length];
        for (int i = 0; i < languages.length; i++) {
            this.perceptrons[i] = new Perceptron(inputSize);
        }
    }

    // Wektor wejściowy reprezentuje proporcję każdej litery ASCII w danym tekście.
    public static double[] textToInputVector(String text, int inputSize) {
        double[] vector = new double[inputSize];
        text.toUpperCase().chars()
                .filter(c -> c < inputSize)
                .forEach(c -> vector[c]++);
        double total = Arrays.stream(vector).sum();
        for (int i = 0; i < vector.length; i++) vector[i] /= total;
        return vector;
    }

    // Remove non-ASCII characters from the text
    public static String preprocessText(String text) {
        return text.replaceAll("[^\\x00-\\x7F]", ""); // Remove non-ASCII characters
    }

    // Trenowanie: Należy zastosować regułę delta. Po każdym kroku uczenia wektory wag można znormalizować, aby poprawić klasyfikację.
    public void train(String directory, int iterations, double learningRate) throws IOException {
        Trainer trainer = new Trainer(languages, inputSize);
        trainer.train(directory, iterations, learningRate);
        perceptrons = trainer.getPerceptrons();
    }

    // Każdy neuron oblicza swoje wyjście liniowe (net). Użyj selektora maksimum, aby znaleźć, który neuron jest aktywowany (1) i załóż, że inne neurony nie są aktywowane (0).
    public String classify(String text) {
        double[] inputs = textToInputVector(text, inputSize);
        double[] outputs = new double[perceptrons.length];
        for (int i = 0; i < perceptrons.length; i++) {
            outputs[i] = perceptrons[i].output(inputs);
        }
        int maxIndex = IntStream.range(0, outputs.length).reduce((i, j) -> outputs[i] < outputs[j] ? j : i).getAsInt();
        return languages[maxIndex];
    }

    // Get language folders in the specified directory
    private static String[] getLanguageFolders(String directory) throws IOException {
        Path dirPath = Paths.get(directory);
        try (Stream<Path> paths = Files.walk(dirPath, 1)) {
            return paths
                    .filter(Files::isDirectory)
                    .filter(path -> !path.equals(dirPath))
                    .map(path -> path.getFileName().toString())
                    .toArray(String[]::new);
        }
    }

    // Testowanie: Zapewnij interfejs umożliwiający wprowadzenie krótkiego tekstu, który zostanie sklasyfikowany przez Twój program.
    public static void main(String[] args) throws IOException {
        String[] languages = getLanguageFolders(System.getProperty("user.dir") + "/proectSieci1-warstwowej/neural-network/src/data");
        System.out.println("Detected languages: " + Arrays.toString(languages));
        LanguageRecognizer recognizer = new LanguageRecognizer(languages);

        recognizer.train(System.getProperty("user.dir") + "/proectSieci1-warstwowej/neural-network/src/data", 1000, 0.01);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter text to classify (or 'exit' to quit):");
            String inputText = scanner.nextLine();
            if (inputText.equalsIgnoreCase("exit")) {
                break;
            }
            String language = recognizer.classify(inputText);
            System.out.println("Predicted language: " + language);
        }
        scanner.close();
    }
}
