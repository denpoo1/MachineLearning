import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Trainer {
    private final Perceptron[] perceptrons;
    private final String[] languages;
    private final int inputSize;

    public Trainer(String[] languages, int inputSize) {
        this.languages = languages;
        this.inputSize = inputSize;
        this.perceptrons = new Perceptron[languages.length];
        for (int i = 0; i < languages.length; i++) {
            perceptrons[i] = new Perceptron(inputSize);
        }
    }

    // Trenowanie: Należy zastosować regułę delta. Po każdym kroku uczenia wektory wag można znormalizować, aby poprawić klasyfikację.
    public void train(String directory, int iterations, double learningRate) throws IOException {
        Path dirPath = Paths.get(directory);
        for (int iter = 0; iter < iterations; iter++) {
            Files.walk(dirPath)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            String language = path.getParent().getFileName().toString();
                            int targetIndex = Arrays.asList(languages).indexOf(language);

                            String text = LanguageRecognizer.preprocessText(Files.readString(path));

                            // Convert text to an input vector
                            double[] inputs = LanguageRecognizer.textToInputVector(text, inputSize);
                            // Compute the output of the perceptron using the formula y = Σ w * x (dot product)
                            double[] outputs = new double[languages.length];

                            // Update weights using the delta rule and formula w = w + η * x * δ (learning rate * input * delta)
                            for (int i = 0; i < languages.length; i++) {
                                outputs[i] = perceptrons[i].output(inputs);
                            }

                            // Normalize weights to have a unit length with the formula w = w / ||w|| (Euclidean norm)
                            IntStream.range(0, outputs.length).reduce((i, j) -> outputs[i] < outputs[j] ? j : i).getAsInt();

                            // Update weights using the delta rule and formula w = w + η * x * δ (learning rate * input * delta)
                            for (int i = 0; i < languages.length; i++) {
                                double delta = (i == targetIndex ? 1 : 0) - outputs[i];
                                perceptrons[i].updateWeights(inputs, learningRate, delta);
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    });
            for (Perceptron perceptron : perceptrons) {
                perceptron.normalizeWeights();
            }
        }
    }

    public Perceptron[] getPerceptrons() {
        return perceptrons;
    }
}
