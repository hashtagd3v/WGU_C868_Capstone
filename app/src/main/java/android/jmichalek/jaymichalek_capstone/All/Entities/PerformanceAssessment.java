package android.jmichalek.jaymichalek_capstone.All.Entities;

public class PerformanceAssessment extends Assessment{

    private int performance_reference;

    public PerformanceAssessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type, int performance_reference) {
        super(assessment_id, assessmentName, assessmentStart, assessmentEnd, courseID, type);
        this.performance_reference = performance_reference;
    }

    public int getPerformance_reference() {
        return performance_reference;
    }

}
