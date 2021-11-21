package android.jmichalek.jaymichalek_capstone.All.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "objective_assessment", foreignKeys = {
        @ForeignKey(
                entity = Assessment.class,
                parentColumns = "assessment_id",
                childColumns = "objective_reference",
                onUpdate = CASCADE,
                onDelete = CASCADE
        )
})
public class ObjectiveAssessment extends Assessment{

    @ColumnInfo(name = "objective_reference")
    private int objective_reference;

    public ObjectiveAssessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type, int objective_reference) {
        super(assessment_id, assessmentName, assessmentStart, assessmentEnd, courseID, type);
        this.objective_reference = objective_reference;
    }

    public int getObjective_reference() {
        return objective_reference;
    }

}