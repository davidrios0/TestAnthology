package com.documentproject.comparison_microservice.services;

import java.util.*;

public class TextProcessor {


    public static List<String> textToArray(String text) {

        text = text.toLowerCase();

        String[] words = text.split("[,.¿? ]");

        List<String> wordList = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                wordList.add(word);
            }
        }

        return wordList;
    }



    public static Map<String, List<Integer>> getCompareText(String text) {

        List<String> words = textToArray(text);

        Map<String, List<Integer>> allText = new HashMap<>();

        for (int index = 0; index < words.size(); index++) {
            String word = words.get(index);
            if (allText.containsKey(word)) {
                allText.get(word).add(index);
            } else {
                List<Integer> indexes = new ArrayList<>();
                indexes.add(index);
                allText.put(word, indexes);
            }
        }

        return allText;
    }



    public static List<String> getSimilarity(String text_1, String text_2) {

        Map<String, List<Integer>> referenceText = getCompareText(text_2);
        List<String> wordsText_1 = textToArray(text_1);
        List<String> wordsText_2 = textToArray(text_2);
        List<Integer> indexesProcessed = new ArrayList<>();
        Map<String, Map<String, Integer>> matchingWords = new HashMap<>() ;
        Map<String, Integer> matchingSentences = new HashMap<>();

        for (int index = 0; index < wordsText_1.size(); index++) {
            String word = wordsText_1.get(index);

            if (referenceText.containsKey(word)) {

                //Getting the matching words and their count in each text
                if (matchingWords.containsKey(word)) {
                    Integer countText_1 = matchingWords.get(word).get("text_1");
                    countText_1++;
                    matchingWords.get(word).put("text_1", countText_1);
                }
                else {
                    Map<String, Integer> countText = new HashMap<>();
                    countText.put("text_1", 1);
                    countText.put("text_2", referenceText.get(word).size());
                    matchingWords.put(word, countText);
                }

                //Getting the matching sentences and their count in each text
                List<Integer> indexesWord = referenceText.get(word);
                for (int j = 0; j < indexesWord.size(); j++) {
                    Integer indexText = indexesWord.get(j);
                    Integer incrementIndex = 1;
                    boolean enableSaveSentence = true;
                    List<String> sentence = new ArrayList<>();
                    sentence.add(word);

                    while (enableSaveSentence && index + incrementIndex < wordsText_1.size() && indexText + incrementIndex < wordsText_2.size()) {
                        int indexForText_1 = index + incrementIndex;
                        int indexForText_2 = indexText + incrementIndex;
                        String nextWordText_1 = wordsText_1.get(indexForText_1);
                        String nextWordText_2 = wordsText_2.get(indexForText_2);
                        if (nextWordText_1.equals(nextWordText_2) && !(indexesProcessed.contains(index))) {
                            sentence.add(nextWordText_1);
                            indexesProcessed.add(indexForText_1);
                            incrementIndex++;
                        }
                        else {
                            enableSaveSentence = false;
                        }

                    }

                    if (sentence.size() > 1) {
                        String matchingSentence = String.join(" ", sentence);
                        if (matchingSentences.containsKey(matchingSentence)) {
                            Integer countSentence = matchingSentences.get(matchingSentence);
                            matchingSentences.put(matchingSentence, countSentence ++);
                        }
                        else {
                            matchingSentences.put(matchingSentence, 1);
                        }
                    }
                }
            }

        }

        Set<String> keySentences = matchingSentences.keySet();

        return new ArrayList<>(keySentences);
    }

}
