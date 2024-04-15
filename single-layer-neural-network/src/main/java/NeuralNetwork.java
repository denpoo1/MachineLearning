import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * This class represents a simple neural network for language identification.
 */
public class NeuralNetwork {
    private List<String> langList;
    private Map<String, double[]> langVectors;
    private double[][] langWeights;

    /**
     * Constructor for the NeuralNetwork class.
     * @param langList A list of languages that the neural network should be able to identify.
     */
    public NeuralNetwork(List<String> langList) {
        this.langList = langList;
        this.langVectors = new HashMap<>();
        this.langWeights = new double[langList.size()][128];
        for (int i = 0; i < langList.size(); i++) {
            for (int j = 0; j < 128; j++) {
                langWeights[i][j] = Math.random();
            }
        }
    }

    /**
     * Trains the neural network with the given files.
     * @param fileLocations A list of file locations that contain the training data.
     */
    public void learn(List<String> fileLocations) {
        List<String> textList = new ArrayList<>();
        for (String fileLocation : fileLocations) {
            try {
                String text = new String(Files.readAllBytes(Paths.get(fileLocation)), StandardCharsets.UTF_8);
                textList.add(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        prepareLangVectors(textList);
        for (int i = 0; i < langList.size(); i++) {
            for (int j = 0; j < 128; j++) {
                double sum = 0;
                for (String lang : langList) {
                    sum += langVectors.get(lang)[j];
                }
                langWeights[i][j] = sum / langList.size();
            }
        }
    }

    /**
     * Prepares the language vectors for the neural network.
     * @param textList A list of texts that will be used to prepare the language vectors.
     */
    private void prepareLangVectors(List<String> textList) {
        for (String lang : langList) {
            double[] langVector = new double[128];
            for (String text : textList) {
                if (text.startsWith(lang)) {
                    for (char c : text.toCharArray()) {
                        if (c < 128) {
                            langVector[c]++;
                        }
                    }
                }
            }
            langVectors.put(lang, langVector);
        }
    }

    /**
     * Identifies the language of the given text.
     * @param text The text whose language should be identified.
     * @return The identified language.
     */
    public String identify(String text) {
        double[] inputVector = new double[128];
        for (char c : text.toCharArray()) {
            if (c < 128) {
                inputVector[c]++;
            }
        }

        double maxOutput = Double.MIN_VALUE;
        String identifiedLang = null;

        for (int i = 0; i < langList.size(); i++) {
            double net = 0;
            for (int j = 0; j < 128; j++) {
                net += langWeights[i][j] * inputVector[j];
            }
            if (net > maxOutput) {
                maxOutput = net;
                identifiedLang = langList.get(i);
            }
        }

        return identifiedLang;
    }

    /**
     * The main method for the NeuralNetwork class.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        List<String> langList = new ArrayList<>();
        langList.add("pl");
        langList.add("en");
        langList.add("de");

        NeuralNetwork neuralNetwork = new NeuralNetwork(langList);

        List<String> fileLocations = new ArrayList<>();
        fileLocations.add("/home/denpool/IdeaProjects/Perceptrone/single-layer-neural-network/training-files/pl/text1.txt");
        fileLocations.add("/home/denpool/IdeaProjects/Perceptrone/single-layer-neural-network/training-files/en/text1.txt");
        fileLocations.add("/home/denpool/IdeaProjects/Perceptrone/single-layer-neural-network/training-files/de/text1.txt");

        neuralNetwork.learn(fileLocations);

        String textToIdentify = "I live in a house near the mountains. I have two brothers and one sister.";
        String identifiedLang = neuralNetwork.identify(textToIdentify);
        System.out.println("Identified language: " + identifiedLang);
    }
}