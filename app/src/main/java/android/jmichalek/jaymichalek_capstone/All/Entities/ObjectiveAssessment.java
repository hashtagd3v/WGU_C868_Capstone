package android.jmichalek.jaymichalek_capstone.All.Entities;


public class ObjectiveAssessment extends Assessment{

    private int objective_reference;

    public ObjectiveAssessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type, int objective_reference) {
        super(assessment_id, assessmentName, assessmentStart, assessmentEnd, courseID, type);
        this.objective_reference = objective_reference;
    }

    public int getObjective_reference() {
        return objective_reference;
    }

}