package android.jmichalek.jaymichalek_capstone.All.Entities;

public class PerformanceAssessment extends Assessment{

    private int assessment_reference;

    public PerformanceAssessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type, int assessment_reference) {
        super(assessment_id, assessmentName, assessmentStart, assessmentEnd, courseID, type);
        this.assessment_reference = assessment_reference;
    }

    public int getAssessment_reference() {
        return assessment_reference;
    }

}
