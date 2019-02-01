package spellchecker;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * ICS 23 Summer 2004
 * Project #5: Lost for Words
 *
 * Implement your word checker here.  A word checker has two responsibilities:
 * given a word list, answer the questions "Is the word 'x' in the wordlist?"
 * and "What are some suggestions for the misspelled word 'x'?"
 *
 * WordChecker uses a class called WordList that I haven't provided the source
 * code for.  WordList has only one method that you'll ever need to call:
 *
 *     public boolean lookup(String word)
 *
 * which returns true if the given word is in the WordList and false if not.
 */

public class WordChecker {
    WordList wordlist;

    /**
     * Constructor that initializes a new WordChecker with a given WordList.
     *
     * @param wordList Initial word list to check against.
     * @see WordList
     */
    public WordChecker(WordList wordList) {
        this.wordlist = wordList;
    }


    /**
     * Returns true if the given word is in the WordList passed to the
     * constructor, false otherwise.
     *
     * @param word Word to chack against the internal word list
     * @return bollean indicating if the word was found or not.
     */
    public boolean wordExists(String word) {
        if (lookupForString(word)) {
            return true;
        } else {
            return false;
        }
    }




	/**
   * Returns an ArrayList of Strings containing the suggestions for the
   * given word.  If there are no suggestions for the given word, an empty
   * ArrayList of Strings (not null!) should be returned.
   *
   * @param word String to check against
   * @return A list of plausible matches
   */
	public ArrayList getSuggestions(String word)
	{
        ArrayList<String> suggestions = new ArrayList<>();

        //Swapping each adjacent pair of characters in the word.
        suggestions.addAll(swapAdjacentLetters(word));
	    //In between each adjacent pair of characters in the word (also before the first character and after the last character), each letter from 'A' through 'Z' is inserted.
        suggestions.addAll(letterMissing(word));
        //Deleting each character from the word.
        suggestions.addAll(letterRedundant(word));
        //Replacing each character in the word with each letter from 'A' through 'Z'.
        suggestions.addAll(replaceLetters(word));
        //Splitting the word into a pair of words by adding a space in between each adjacent pair of characters in the word. It should be noted that this will only generate a suggestion if both words in the pair are found in the wordlist.
        suggestions.addAll(splitWord(word));

        return suggestions;
	}

    private ArrayList<String> swapAdjacentLetters(String word) {
	    ArrayList<String> results = new ArrayList<>();
	    for (int i = 0 ; i< word.length()-2 ; i++){
            char[] wordInArray = word.toCharArray();
	        char indexChar = word.charAt(i);
	        char nextChar = word.charAt(i++);
            wordInArray[i] = nextChar;
            wordInArray[i++] = indexChar;
            String swappedString = new String(wordInArray);
            if(lookupForString(swappedString)){
                results.add(swappedString);
            }
        }
	    return results;
    }

    private ArrayList<String> letterMissing(String word) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        ArrayList<String> results = new ArrayList<>();
            for(int i = 0 ; i < word.length()+1 ; i++){
                for (int j = 0 ; j < alphabet.length - 1 ; j++){
                    String potentialWord;
                    if(i == 0){
                        potentialWord = alphabet[j] + word;
                        if(lookupForString(potentialWord)){
                            results.add(potentialWord);
                        }
                    } else if (i>0 && i < word.length()-1){
                        char[] wordInArray = word.toCharArray();
                        String start = new String(wordInArray, 0,i);
                        String end = new String(wordInArray, i+1,wordInArray.length-i-1);
                        potentialWord = start + alphabet[j] + end;

                    } else {
                        potentialWord = word + alphabet[j];

                    }
                    if(lookupForString(potentialWord)){
                        results.add(potentialWord);
                    }
                }
            }
        return results;
    }

    private ArrayList<String> letterRedundant(String word) {
        ArrayList<String> results = new ArrayList<>();

        for(int i = 0 ; i < word.length() ; i++){
            char[] wordInArray = word.toCharArray();
            wordInArray[i] = ' ';
            String potentialWord = "";
            for (char c : wordInArray){
                if(c != ' '){
                    potentialWord+=c;
                }
            }
            if(lookupForString(potentialWord)){
                results.add(potentialWord);
            }

        }

        return results;
    }

    private ArrayList<String> replaceLetters(String word) {
        ArrayList<String> results = new ArrayList<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0 ; i < word.length() ; i++){
            char[] wordInArray = word.toCharArray();
            for (int j = 0 ; j < alphabet.length ; j++){
                wordInArray[i] = alphabet[j];
                String potentialWord = wordInArray.toString();
                if(lookupForString(potentialWord)){
                    results.add(potentialWord);
                }
            }
        }

        return results;
    }

    private ArrayList<String> splitWord(String word) {
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0 ; i < word.length() ; i ++){
            char[] wordInArray = word.toCharArray();
            String start = new String(wordInArray, 0,i);
            String end = new String(wordInArray, i+1,wordInArray.length-i-1);
            if(lookupForString(start)){
                results.add(start);
            }
            if(lookupForString(end)){
                results.add(end);
            }
        }
        return results;
    }

    private boolean lookupForString(String str){
	    if (wordlist.lookup(str)){
	        return true;
        }
	    return false;
    }

}
