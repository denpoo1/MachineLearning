import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class LanguageIdentifierTerminal {
    private NeuralNetwork neuralNetwork;

    public LanguageIdentifierTerminal() {
        // Инициализация нейронной сети
        List<String> langList = Arrays.asList("pl", "en", "de");
        neuralNetwork = new NeuralNetwork(langList);
        neuralNetwork.learn(Arrays.asList(
                "/home/denpool/IdeaProjects/Perceptrone/single-layer-neural-network/training-files/pl/text1.txt",
                "/home/denpool/IdeaProjects/Perceptrone/single-layer-neural-network/training-files/en/text1.txt",
                "/home/denpool/IdeaProjects/Perceptrone/single-layer-neural-network/training-files/de/text1.txt"
        ));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter text (or 'exit' to exit):");
            String text = scanner.nextLine();
            if ("exit".equals(text)) {
                break;
            }
            String identifiedLang = neuralNetwork.identify(text);
            System.out.println("Identified language: " + identifiedLang);
        }
    }

    public static void main(String[] args) {
        new LanguageIdentifierTerminal().start();
    }
}