package android.jmichalek.jaymichalek_capstone.All.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "performance_assessment", foreignKeys = {
        @ForeignKey(
                entity = Assessment.class,
                parentColumns = "assessment_id",
                childColumns = "assessment_reference",
                onUpdate = CASCADE,
                onDelete = CASCADE
        )
})
public class PerformanceAssessment extends Assessment{

    @ColumnInfo(name = "assessment_reference")
    private int assessment_reference;

    public PerformanceAssessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type, int assessment_reference) {
        super(assessment_id, assessmentName, assessmentStart, assessmentEnd, courseID, type);
        this.assessment_reference = assessment_reference;
    }

    public int getAssessment_reference() {
        return assessment_reference;
    }

}
