import java.util.*;
/**
 * @author 
 * @version 4/25/18
 */
public class HangmanManager {
   private int guesses;
   private String pattern;
   private TreeSet<Character> guessedLetters;
   private TreeSet<String> possibleWords;
   
/**
 * Constructor for HangmanManager that uses the desired length to populate a TreeSet of all potential words
 * and creates the pattern that matches the length of desired words
 *
 * @param dictionary a String list of all the words from the dictionary file
 * @param length a integer that is the length of words to use
 * @param max a integer that is max guesses
 * @throws IllegalArgumentException if length is less than 1 or guesses is negative
 */     
   public HangmanManager(List<String> dictionary, int length, int max) {
   // throws IllegalArgumentException if length less than 1 or max is less than 0
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      
      guesses = max;
      possibleWords = new TreeSet<String>();
      guessedLetters = new TreeSet<Character>();
   // adds all words that match the length given from dictionary      
      for(String potential : dictionary) {
         if(potential.length() == length) {
            possibleWords.add(potential);
         }
      }      
      pattern = "";
      for (int i=0; i<length; i++) {
         pattern += "-";
      }
   }
/**
 * method that returns potential words to be used
 *
 * @return possibleWords potential words to use
 */      
   public Set<String> words() {
      return possibleWords;
   }
/**
 * method that returns the guesses left
 *
 * @return guesses the integer value of guesses left
 */      
   public int guessesLeft() {
      return guesses;
   }
/**
 * method that returns letters already guessed
 * 
 * @return guessedLetters as a set of letters used
 */      
   public Set<Character> guesses() {
      return guessedLetters;
   }
/** 
 * method that returns the pattern with correct guessed letters
 * 
 * @return pattern a string of correctly guessed letters in a pattern
 * @throws IllegalStateException if potential words to use is empty
 */    
   public String pattern() {  
      if (possibleWords.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
/**
 * method that chooses the largest list of words based on whats guessed
 *
 * @param guess is a character of guessed letter
 * @return guesses an integer for amount of guesses left till zero
 * @throws IllegalStateException if list of potential words is empty or no guesses left
 * @throws IllegalArgumentException if character is already guessed 
 */    
   public int record(char guess) {
      if (possibleWords.isEmpty() || guesses < 1) {
         throw new IllegalStateException();
      }
      if (guessedLetters.contains(guess)) {
         throw new IllegalArgumentException();
      } 
         
      guessedLetters.add(guess);
      Map<String, TreeSet<String>> charMap = new TreeMap<String, TreeSet<String>>();
   // map is filled with the most combinations of an option   
      for (String current : possibleWords) {
         mapContents(charMap, current, pattern, guess);         
      }
      int maxWord = 0;
      String maxPattern = "";
      for (String key : charMap.keySet()) {
         Set<String> temp = charMap.get(key);
         if (temp.size() > maxWord) {
            maxWord = charMap.get(key).size();
            maxPattern = key;
         }
      }
      possibleWords = charMap.get(maxPattern);
      pattern = maxPattern;
      
      guesses--;
      return guesses;             
   } 
   
   private void mapContents(Map<String, TreeSet<String>> charMap,String current, String newPattern, char guess) {
      int length = pattern.length();
      pattern = "";
      for (int i=0; i<length; i++) {  
         if (guessedLetters.contains(current.charAt(i))) {
            pattern += current.charAt(i);
         } else if (current.charAt(i) == guess) {
            pattern += guess;
         } else {
            pattern += "-";
         }
      }
      if (!charMap.containsKey(pattern)) {
         charMap.put(pattern, new TreeSet<String>());
      }
      charMap.get(pattern).add(current);
   }    
}