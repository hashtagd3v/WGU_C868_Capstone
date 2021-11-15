package android.jmichalek.jaymichalek_capstone.All.Entities;

import androidx.room.Entity;

@Entity(tableName = "performance_assessment")
public class PerformanceAssessment extends Assessment{

    private String type = "Performance Assessment";

    public PerformanceAssessment(int assessmentID, String assessmentName, String assessmentStart, String assessmentEnd, int courseID) {
        super(assessmentID, assessmentName, assessmentStart, assessmentEnd, courseID);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
