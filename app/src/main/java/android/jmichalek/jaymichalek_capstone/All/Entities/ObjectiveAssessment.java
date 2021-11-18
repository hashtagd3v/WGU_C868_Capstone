package android.jmichalek.jaymichalek_capstone.All.Entities;

import androidx.room.Entity;

@Entity(tableName = "objective_assessment")
public class ObjectiveAssessment extends Assessment{

    private String type;

    public ObjectiveAssessment(int assessmentID, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type) {
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