import java.util.HashMap;

/**
 * This class represents a data point with features and a label.
 * // Класс представляет точку данных с признаками и меткой.
 */
public class DataPoint {
    private double[] features; // Признаки точки данных
    private double label; // Метка точки данных
    private static HashMap<String, Integer> CURRENT_STRING_LABELS = new HashMap<>();
    private static LabelTypeEnum LABEL_TYPE_ENUM;

    /**
     * Constructs a new DataPoint with the given features and label.
     * // Создает новую точку данных с указанными признаками и меткой.
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
            CURRENT_STRING_LABELS.put(label, 1);
            this.label = 1;
        } else {
            CURRENT_STRING_LABELS.put(label, 0);
            this.label = 0;
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
