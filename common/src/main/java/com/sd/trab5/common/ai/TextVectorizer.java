package com.sd.trab5.common.ai;

import java.text.Normalizer;
import java.util.*;

public class TextVectorizer {

    private final Map<String, Integer> wordToIndex = new HashMap<>();
    private final List<String> indexToWord = new ArrayList<>();
    private boolean frozen = false;

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "de", "a", "o", "que", "e", "do", "da", "em", "um", "para", "com", "nao", "uma", "os", "no",
            "se", "na", "por", "mais", "as", "dos", "como", "mas", "foi", "ao", "ele", "das", "tem",
            "seu", "sua", "ou", "ser", "quando", "muito", "ha", "nos", "ja", "esta", "eu", "tambem",
            "so", "pelo", "pela", "ate", "isso", "ela", "entre", "era", "depois", "sem", "mesmo",
            "aos", "ter", "seus", "quem", "nas", "me", "esse", "eles", "estao", "voce", "tinha",
            "foram", "essa", "num", "nem", "suas", "meu", "minha", "numa", "pelos", "elas", "havia",
            "seja", "qual", "sera", "tenho", "lhe", "deles", "essas", "esses", "pelas", "este",
            "dele", "tu", "te", "voces", "meus", "minhas", "teu", "tua", "nosso", "nossa", "isto",
            "aquilo", "estou", "estamos", "estive", "esteve", "estiveram", "estava", "estavam",
            "pra", "pro", "ne", "ai", "ta", "tudo", "todo", "toda", "todos", "todas"
    ));

    public List<String> tokenize(String text) {
        if (text == null) {
            return Collections.emptyList();
        }

        // 1. Normalizar acentuação (remover diacríticos: ç -> c, é -> e, etc.)
        String normalized = Normalizer.normalize(text.toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // 2. Substituir pontuações e caracteres não alfanuméricos por espaço
        String cleaned = normalized.replaceAll("[^a-z0-9\\s]", " ");

        // 3. Separar por espaço, filtrar palavras curtas e stopwords
        String[] tokens = cleaned.split("\\s+");
        List<String> result = new ArrayList<>();
        for (String token : tokens) {
            token = token.trim();
            if (token.length() >= 2 && !STOP_WORDS.contains(token)) {
                result.add(token);
            }
        }
        return result;
    }

    public void fit(List<String> texts) {
        for (String text : texts) {
            List<String> tokens = tokenize(text);
            for (String token : tokens) {
                if (!wordToIndex.containsKey(token)) {
                    int idx = wordToIndex.size();
                    wordToIndex.put(token, idx);
                    indexToWord.add(token);
                }
            }
        }
        this.frozen = true;
    }

    public int[] transform(String text) {
        int[] vector = new int[wordToIndex.size()];
        List<String> tokens = tokenize(text);
        for (String token : tokens) {
            Integer idx = wordToIndex.get(token);
            if (idx != null) {
                vector[idx]++;
            }
        }
        return vector;
    }

    public int getVocabularySize() {
        return wordToIndex.size();
    }

    public Map<String, Integer> getWordToIndex() {
        return Collections.unmodifiableMap(wordToIndex);
    }
}
