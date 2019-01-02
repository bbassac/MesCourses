package lioncorps.org.mescourses.bean;

import java.util.ArrayList;
import java.util.List;

public class Liste {

    private Long id;
    private String nom;
    private Boolean template;
    private List<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        return "Liste{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", template=" + template +
                '}';
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getTemplate() {
        return template;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
        if(item == null){
            items = new ArrayList<Item>();
        }
        items.add(item);
    }
}
