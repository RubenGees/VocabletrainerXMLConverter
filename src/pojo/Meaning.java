package pojo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ruben on 24.04.2015.
 */
public class Meaning implements Iterable<String> {

    private List<String> meanings;

    public Meaning() {
        meanings = new LinkedList<>();
    }

    public void addMeaning(String meaning) {
        meanings.add(meaning);
    }

    @Override
    public Iterator<String> iterator() {
        return meanings.iterator();
    }
}
