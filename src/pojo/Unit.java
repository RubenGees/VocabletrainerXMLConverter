package pojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ruben on 24.04.2015.
 */
public class Unit implements Iterable<Vocable> {

    private String title;
    private List<Vocable> vocables;

    public Unit() {
        vocables = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addVocable(Vocable vocable) {
        vocables.add(vocable);
    }

    @Override
    public Iterator<Vocable> iterator() {
        return vocables.iterator();
    }
}
