package spellchecker;

/**
 *
 * ICS 23 Summer 2004
 * Project #5: Lost for Words
 *
 * Implement your hash table here.  You are required to use the separate
 * chaining strategy that we discussed in lecture, meaning that collisions
 * are resolved by having each cell in the table be a linked list of all of
 * the strings that hashed to that cell.
 */

public class HashTable {

	private StringHasher hasher;
	private Word[] table;


	/**
   * The constructor is given a table size (i.e. how big to make the array)
   * and a StringHasher, which is used to hash the strings.
   *
   * @param tableSize number of elements in the hash array
   *        hasher    Object that creates the hash code for a string
   * @see StringHasher
   */
	public HashTable(int tableSize, StringHasher hasher)
	{
		this.hasher = hasher;
		this.table = new Word[tableSize];
	}


	/**
   * Takes a string and adds it to the hash table, if it's not already
   * in the hash table.  If it is, this method has no effect.
   *
   * @param s String to add
   */
	public void add(String s) {
		boolean findIsThereWord = lookup(s);
		if(!findIsThereWord){
			Word wordToAdd = new Word(s.toUpperCase());
			int wordHash = hasher.hash(s);
				wordHash = checkIfArrayIndexInBounds(wordHash);
			if (table[wordHash] != null){
				Word currentWord = table[wordHash];
				while(currentWord.getNext() != null){
					currentWord = currentWord.getNext();
				}
				currentWord.setNext(wordToAdd);
			} else {
				table[wordHash] = wordToAdd;
			}
		}
	}
	

	/**
  * Takes a string and returns true if that string appears in the
	* hash table, false otherwise.
  *
  * @param s String to look up
  */
	public boolean lookup(String s) {
		int hashKey = hasher.hash(s);
		hashKey = checkIfArrayIndexInBounds(hashKey);
		Word currentWord = table[hashKey];
		if(currentWord == null){
			return false;
		}

		if (currentWord.getContent().equals(s)) {
			return true;
		}

		while( currentWord.getNext() != null || currentWord.getContent().equals(s)) {

			if (currentWord.getContent().equals(s)) {
				return true;
			}
				currentWord = currentWord.getNext();
		}

		return false;
		}

	

	/**
   * Takes a string and removes it from the hash table, if it
   * appears in the hash table.  If it doesn't, this method has no effect.
   *
   * @param s String to remove
  */
	public void remove(String s) {
		int hashKey = hasher.hash(s);
		Word previousWord = null;
		Word currentWord = table[hashKey];
		if (currentWord != null){
			while(currentWord.getContent() != s && currentWord.getNext() != null){
				previousWord = currentWord;
				currentWord = currentWord.getNext();
				}
			}
		if (currentWord.getContent() == s) {
			previousWord.setNext(currentWord.getNext());
			currentWord.setNext(null);
		}
	}
	private int checkIfArrayIndexInBounds(int index) {
		if (index > table.length || index < 0) {
			index = Math.abs(index);
			index = index % (table.length - 1);
		}
		return index;
	}
}
