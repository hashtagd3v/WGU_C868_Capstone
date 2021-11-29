package android.jmichalek.jaymichalek_capstone.All.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "termID")
    private int termID;

    @ColumnInfo(name="termName")
    private String termName;
    private String termStart;
    private String termEnd;
    private String created_date;

    public Term(int termID, String termName, String termStart, String termEnd, String created_date) {
        this.termID = termID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
        this.created_date = created_date;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termID=" + termID +
                ", termName='" + termName + '\'' +
                ", termStart='" + termStart + '\'' +
                ", termEnd='" + termEnd + '\'' +
                '}';
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

}
