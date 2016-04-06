package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Dan on 2/25/2016.
 */
@Entity
@Table(name = "reclamation")
public class Reclamation extends BaseCase {

    @Id
    public Integer id;

    @Constraints.Required(message = "Subtype required")
    public String caseSubtype;

    public boolean watchItem;

    @Constraints.Required(message = "Name required")
    public String beneficiaryName;

    @Constraints.Required(message = "Acct # required")
    public String beneficiaryAcctNumber;

    public String beneficiarySSN;

    public String beneficiaryCustId;

    @Constraints.Required(message = "DoD required")
    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date dateOfDeath;

    @Constraints.Required(message = "Awareness Date required")
    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date dateCBAware;

    @Constraints.Required
    public boolean otherGovBenefits;

    public String govBenefits;

    public String claimNumber;

    @Constraints.Required(message = "Recovery Method required")
    public String recoveryMethod;

    public boolean checkMailed;

    public String checkNumber;

    public String mailedTo;

    public boolean fullRecovery;

    @Constraints.Required(message = "Completion Date required")
    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date completedDate;

    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date verifiedDate;

    public String verifiedBy;

    public String additionalNotes;

    @OneToMany(mappedBy="reclamation", cascade= CascadeType.ALL)
    public List<Payment> paymentList;

    private static final String[] subType_options = { "CRN", "DCN", "DNE", "Gov Reclamation", "Treasury Referral", "Treasury Refund" };
    private static final String[] recovery_options = { "ACH Return", "Cashiers Check Mailed", "Mixed Method" };

    private static Model.Finder<String, Reclamation> find = new Model.Finder<>(Reclamation.class);

    // Finder methods
    public static List<Reclamation> findAll() {
        return SearchHelper.findAll(find);
    }

    public static List<Reclamation> findAll(Status stat) {
        return SearchHelper.findAll(find, stat);
    }

    public static Reclamation findById(Integer id) {
        return SearchHelper.findById(find, id);
    }

    public static Reclamation retrieve(Integer id) {
        // retrieve an entity reference for this ID
        return SearchHelper.retrieve(find, id);
    }

    public static boolean exists(Integer id) {
        return SearchHelper.exists(find, id);
    }


    public static Map<String, String> subTypeOptions() {
        Map<String, String> subType_map = new LinkedHashMap<>();
        for (String subType : subType_options) {
            subType_map.put(subType, subType);
        }
        return subType_map;
    }

    public static Map<String, String> recoveryMethodOptions() {
        Map<String, String> type_map = new LinkedHashMap<>();
        for (String type : recovery_options) {
            type_map.put(type, type);
        }
        return type_map;
    }

    public double calculateTotalPayment() {
        double total = 0;
        for (Payment payment : paymentList) {
            total += payment.amount;
        }
        return total;
    }
}
