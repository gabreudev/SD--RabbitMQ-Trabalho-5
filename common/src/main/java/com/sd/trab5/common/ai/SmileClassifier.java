package com.sd.trab5.common.ai;

import smile.classification.DiscreteNaiveBayes;

import java.util.*;

public class SmileClassifier {

    public static class Sample {
        private final String text;
        private final String label;

        public Sample(String text, String label) {
            this.text = text;
            this.label = label;
        }

        public String getText() {
            return text;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class ClassificationResult {
        private final String label;
        private final double confidence;
        private final Map<String, Double> classProbabilities;

        public ClassificationResult(String label, double confidence, Map<String, Double> classProbabilities) {
            this.label = label;
            this.confidence = confidence;
            this.classProbabilities = classProbabilities;
        }

        public String getLabel() {
            return label;
        }

        public double getConfidence() {
            return confidence;
        }

        public Map<String, Double> getClassProbabilities() {
            return classProbabilities;
        }

        @Override
        public String toString() {
            return String.format("%s (%.1f%%)", label, confidence * 100.0);
        }
    }

    private final List<String> classes;
    private final Map<String, Integer> classToIndex = new HashMap<>();
    private final TextVectorizer vectorizer;
    private final DiscreteNaiveBayes model;

    public SmileClassifier(List<String> classes, List<Sample> trainingData) {
        if (classes == null || classes.isEmpty()) {
            throw new IllegalArgumentException("Lista de classes não pode ser vazia.");
        }
        if (trainingData == null || trainingData.isEmpty()) {
            throw new IllegalArgumentException("Dados de treino não podem ser vazios.");
        }

        this.classes = new ArrayList<>(classes);
        for (int i = 0; i < classes.size(); i++) {
            classToIndex.put(classes.get(i), i);
        }

        // 1. Extrair textos e ajustar vocabulário
        List<String> texts = new ArrayList<>(trainingData.size());
        for (Sample s : trainingData) {
            texts.add(s.getText());
        }
        this.vectorizer = new TextVectorizer();
        this.vectorizer.fit(texts);

        int vocabSize = Math.max(1, vectorizer.getVocabularySize());
        int numClasses = classes.size();

        // 2. Inicializar Naive Bayes Multinomial do Smile
        this.model = new DiscreteNaiveBayes(DiscreteNaiveBayes.Model.MULTINOMIAL, numClasses, vocabSize);

        // 3. Treinar com os dados
        for (Sample s : trainingData) {
            Integer labelIdx = classToIndex.get(s.getLabel());
            if (labelIdx != null) {
                int[] vector = vectorizer.transform(s.getText());
                model.update(vector, labelIdx);
            }
        }
    }

    public ClassificationResult classify(String text) {
        int[] vector = vectorizer.transform(text);
        double[] posteriors = new double[classes.size()];
        int predictedIdx = model.predict(vector, posteriors);

        if (predictedIdx < 0 || predictedIdx >= classes.size()) {
            predictedIdx = 0;
        }

        String predictedLabel = classes.get(predictedIdx);
        double confidence = posteriors[predictedIdx];

        Map<String, Double> probs = new LinkedHashMap<>();
        for (int i = 0; i < classes.size(); i++) {
            probs.put(classes.get(i), posteriors[i]);
        }

        return new ClassificationResult(predictedLabel, confidence, probs);
    }

    public List<String> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public int getVocabularySize() {
        return vectorizer.getVocabularySize();
    }
}
