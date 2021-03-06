package lioncorps.org.mescourses.bean;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private Long id;
    private List<Liste> listes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Liste> getListes() {
        return listes;
    }

    public void setListes(List<Liste> listes) {
        this.listes = listes;
    }

    public void addListe(Liste l) {
        if(listes == null){
            listes = new ArrayList<>();
        }
        listes.add(l);
    }
}
