package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Drew on 2/28/16.
 */
@Entity
@Table(name = "payment")
public class Payment extends Model {

    @Id
    public Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reclamation_id")
    public Reclamation reclamation;

    @Constraints.Required(message = "At least one Payment required")
    public double amount;

    @Constraints.Required(message = "At least one Payment required")
    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date effectiveDate;

    private static Model.Finder<String, Payment> find = new Model.Finder<>(Payment.class);

    public static List<Payment> findAll() {
        return find.orderBy("id").findList();
    }

    public static boolean exists(Integer id) {
        return (find.where().eq("id", id).findRowCount() == 1) ? true : false;
    }
}
