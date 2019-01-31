package spellchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordList {
    private final HashTable hashTable;

    public WordList(String strToHash, StringHasher hasher) throws IOException {
        BufferedReader bReader = new BufferedReader(new FileReader(strToHash));
        int var4 = Integer.parseInt(bReader.readLine());
        this.hashTable = new HashTable((int)((double)var4 * 1.2D), hasher);

        for(int i = 0; i < var4; ++i) {
            this.hashTable.add(bReader.readLine().trim().toUpperCase());
        }

        bReader.close();
    }

    public boolean lookup(String var1) {
        return this.hashTable.lookup(var1.toUpperCase());
    }
}