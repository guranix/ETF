package dbService.DataSets;

import java.sql.Date;

/**
 * Created by guran on 2/9/17.
 */
public class RequestHistory {
    private long id;
    private long userID;
    private long etfID;
    private java.sql.Date datetime;
    private String etfName;

    public RequestHistory(long id, long userID, long etfID, java.sql.Date datetime) {
        this.id = id;
        this.userID = userID;
        this.etfID = etfID;
        this.datetime = datetime;
    }

    public RequestHistory(long userID, long etfID, java.sql.Date datetime) {
        this(-1, userID, etfID, datetime);
    }

    public long getId() {
        return id;
    }

    public long getUserID() {
        return userID;
    }

    public long getEtfID() {
        return etfID;
    }

    public java.sql.Date getDatetime() {
        return datetime;
    }

    public String getEtfName() {
        return etfName;
    }

    public void setEtfName(String etfName) {
        this.etfName = etfName;
    }
}
