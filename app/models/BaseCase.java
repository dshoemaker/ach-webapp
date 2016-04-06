package models;


import play.data.format.Formats;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by Drew on 2/28/16.
 */


@MappedSuperclass
public class BaseCase extends Model {

    @Constraints.Required
    public String caseType;

    @Constraints.Required(message = "Creation Date required")
    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern = DATE_FORMAT)
    public Date dateCreated;

    public String createdBy;

    public enum Status {
        OPEN("Open"), CLOSED("Closed"), REOPENED("Re-Opened");
        private String value;

        Status(String value){ this.value = value; }
        public String getValueOr() { return value != null ? value : "N/A"; }
    }

    @Enumerated(EnumType.STRING)
    public Status status;

    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern = DATE_FORMAT)
    public Date dateClosed;

    public String subStatus;

    private static final String[] type_options = { "Government Reclamations", "POA", "Returns", "Reversals/Deletions", "Unresolved/Dishonored Returns" };

    @Transient
    private final String DATE_FORMAT = "MM/dd/yyyy";

    public static Map<String, String> typeOptions() {
        Map<String, String> type_map = new LinkedHashMap<>();
        for (String type : type_options) {
            type_map.put(type, type);
        }
        return type_map;
    }

    public String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    public long daysOpen() {
        LocalDate today = LocalDate.now();
        LocalDate start = dateCreated.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(start, today);
    }
}


