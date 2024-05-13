import java.util.HashMap;

/**
 * This class represents a data point with features and a label.
 * // Ta klasa reprezentuje punkt danych z cechami i etykietą.
 */

public class DataPoint {
    private double[] features; // Cechy punktu danych
    private double label; // Etykieta punktu danych
    private static HashMap<String, Integer> CURRENT_STRING_LABELS = new HashMap<>();
    private static LabelTypeEnum LABEL_TYPE_ENUM;


    /**
     * Constructs a new DataPoint with the given features and label.
     * // Konstruuje nowy punkt danych z podanymi cechami i etykietą.
     *
     * @param features the features
     * @param label    the label
     */
    DataPoint(double[] features, int label) {
        LABEL_TYPE_ENUM = LabelTypeEnum.NUMBER_LABEL;
        this.features = features;
        this.label = label;
    }

    DataPoint(double[] features, String label) {
        LABEL_TYPE_ENUM = LabelTypeEnum.STRING_LABEL;
        this.features = features;
        if (CURRENT_STRING_LABELS.containsKey(label)) {
            this.label = CURRENT_STRING_LABELS.get(label);
        } else if (!CURRENT_STRING_LABELS.isEmpty()) {
            CURRENT_STRING_LABELS.put(label, 2);
            this.label = 2;
        } else {
            CURRENT_STRING_LABELS.put(label, 1);
            this.label = 1;
        }
    }

    public double getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double[] getFeatures() {
        return features;
    }

    public void setFeatures(double[] features) {
        this.features = features;
    }

    public static LabelTypeEnum getLabelTypeEnum() {
        return LABEL_TYPE_ENUM;
    }

    public static HashMap<String, Integer> getCurrentStringLabels() {
        return CURRENT_STRING_LABELS;
    }
}