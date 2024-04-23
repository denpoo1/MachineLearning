import java.util.Scanner;

/**
 * The AnimalClassifier class uses a Perceptron to classify animals based on a set of attributes.
 * The attributes are asked to the user via console input.
 */
public class AnimalClassifier {
    private Perceptron perceptron;
    // Array of questions to ask the user about the animal's attributes
    private static final String[] QUESTIONS = {
            "Does the animal have hair? (Enter 0 for No, 1 for Yes)",
            "Does the animal have feathers? (Enter 0 for No, 1 for Yes)",
            "Does the animal lay eggs? (Enter 0 for No, 1 for Yes)",
            "Does the animal give milk? (Enter 0 for No, 1 for Yes)",
            "Can the animal fly? (Enter 0 for No, 1 for Yes)",
            "Does the animal live in water? (Enter 0 for No, 1 for Yes)",
            "Is the animal a predator? (Enter 0 for No, 1 for Yes)",
            "Does the animal have teeth? (Enter 0 for No, 1 for Yes)",
            "Does the animal have a backbone? (Enter 0 for No, 1 for Yes)",
            "Does the animal breathe? (Enter 0 for No, 1 for Yes)",
            "Is the animal venomous? (Enter 0 for No, 1 for Yes)",
            "Does the animal have fins? (Enter 0 for No, 1 for Yes)",
            "How many legs does the animal have?",
            "Does the animal have a tail? (Enter 0 for No, 1 for Yes)",
            "Is the animal domestic? (Enter 0 for No, 1 for Yes)",
            "Is the animal small in size? (Enter 0 for No, 1 for Yes)"
    };

    /**
     * Constructor for the AnimalClassifier class.
     * @param perceptron The Perceptron object to be used for classification.
     */
    public AnimalClassifier(Perceptron perceptron) {
        this.perceptron = perceptron;
    }

    /**
     * Starts the classification process.
     * Asks the user for the attributes of the animal and classifies it.
     * Continues to ask for attributes and classify until the user decides to stop.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            double[] observation = new double[QUESTIONS.length];
            for (int i = 0; i < QUESTIONS.length; i++) {
                System.out.println(QUESTIONS[i]);
                observation[i] = scanner.nextDouble();
            }
            int prediction = perceptron.predict(observation);
            System.out.println("Predicted class: " + (prediction == 0 ? "Class 1" : "Class 2"));
            System.out.println("Do you want to continue? (yes/no)");
            if (!scanner.next().equalsIgnoreCase("yes")) {
                break;
            }
        }
        scanner.close();
    }

    /**
     * The main method of the AnimalClassifier class.
     * Creates a Perceptron object and an AnimalClassifier object and starts the classification process.
     * @param args Command line arguments. Not used in this program.
     */
    public static void main(String[] args) {
        Perceptron perceptron = new Perceptron(0.1, 100);
        AnimalClassifier classifier = new AnimalClassifier(perceptron);
        classifier.start();
    }
}