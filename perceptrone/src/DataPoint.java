/**
 * This class represents a data point with features and a label.
 * // Ta klasa reprezentuje punkt danych z cechami i etykietą.
 */

public class DataPoint {
    double[] features; // Cechy punktu danych
    int label; // Etykieta punktu danych

    /**
     * Constructs a new DataPoint with the given features and label.
     * // Konstruuje nowy punkt danych z podanymi cechami i etykietą.
     *
     * @param features the features
     * @param label    the label
     */
    DataPoint(double[] features, int label) {
        this.features = features;
        this.label = label;
    }
}