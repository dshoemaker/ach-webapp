package models;

import com.avaje.ebean.Model;

import java.util.List;

/**
 * Created by Dan on 3/17/2016.
 */
public class SearchHelper {

    public static <T> List<T> findAll(Model.Finder<String, T> find) {
        return find.orderBy("dateCreated desc").findList();
    }

    public static <T> List<T> findAll(Model.Finder<String, T> find, BaseCase.Status stat) {
        return find.where().eq("status", stat).orderBy("dateCreated desc").findList();
    }

    public static <T> T findById(Model.Finder<String, T> find, Integer id) {
        return find.byId(id.toString());
    }

    public static <T> T retrieve(Model.Finder<String, T> find, Integer id) {
        return find.ref(id.toString());
    }

    public static <T> boolean exists(Model.Finder<String, T> find, Integer id) {
        return (find.where().eq("id", id).findRowCount() == 1) ? true : false;
    }

}
