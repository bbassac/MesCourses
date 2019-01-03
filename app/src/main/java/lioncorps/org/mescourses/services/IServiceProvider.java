package lioncorps.org.mescourses.services;

import java.io.IOException;

import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Liste;
public
interface IServiceProvider {
    Collection loadCollection();

    Liste loadListe(Long listID);

    Liste addItemToListe(Long listId, String nom, String quantite);

    Collection addListe(String nom);

    Liste updateItem(Long id, String nom, String quantite, boolean done);
}
