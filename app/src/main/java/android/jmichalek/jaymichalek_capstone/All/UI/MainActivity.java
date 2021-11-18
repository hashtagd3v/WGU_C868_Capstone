package android.jmichalek.jaymichalek_capstone.All.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.jmichalek.jaymichalek_capstone.All.Database.Repository;
import android.jmichalek.jaymichalek_capstone.All.Entities.Assessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.Course;
import android.jmichalek.jaymichalek_capstone.All.Entities.ObjectiveAssessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.PerformanceAssessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.Term;
import android.jmichalek.jaymichalek_capstone.R;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Repository repository = new Repository(getApplication());
//        Term term = new Term(0,"Spring Term", "1/1/21", "3/30/21");
//        repository.insert(term);
//        Repository term_repository = new Repository(getApplication());
//        Term second_term = new Term(0,"Summer Term", "6/1/21", "8/31/21");
//        term_repository.insert(second_term);
//
//        Repository course_repository1 = new Repository(getApplication());
//        Course course = new Course(0, "Potions", "10/31/21", "12/31/21", "Not started", "Severus Snape",
//                "123-456-7891", "severus@hogwarts.com", "Polyjuice Potion", 1);
//        course_repository1.insert(course);
//        Repository course_repository2 = new Repository(getApplication());
//        Course second_course = new Course(0, "Quidditch 101", "06/01/22", "08/31/22", "Not started", "Oliver Wood",
//                "123-456-7891", "wood@hogwarts.com", "Golden Snitch Practice", 1);
//        course_repository2.insert(second_course);
//
//        Used Downcasting for polymorphism: //TODO: Test me first!!!

//        Repository addPerformance = new Repository(getApplication());
//        Assessment assessment = new PerformanceAssessment(0, "Practical Exam 1", "12/15/21", "12/31/21", 1. "Performance Assessment");
//        PerformanceAssessment castedPerformance = (PerformanceAssessment) assessment;
//        //Insert assessment to database performance_assessment table (Performance child type):
//        addPerformance.insert(castedPerformance);
//        Repository addToAssessment1 = new Repository(getApplication());
//        //Insert assessment to database assessment_table (Assessment parent type):
//        addToAssessment1.insert(assessment);
//
//        Repository addObjective = new Repository(getApplication());
//        Assessment second_assessment = new ObjectiveAssessment(0, "Objective Exam 2", "12/15/21", "12/31/21", 1, "Objective Assessment");
//        ObjectiveAssessment castedObjective = (ObjectiveAssessment) second_assessment;
//        //Insert assessment to database objective_assessment table (Objective child type):
//        addObjective.insert(castedObjective);
//        Repository addToAssessment2 = new Repository(getApplication());
//        //Insert assessment to database assessment_table (Assessment parent type):
//        addToAssessment2.insert(second_assessment);

    }

    /* When Enter button is pressed, this method brings user to next screen which
     * is TermList screen.*/
    public void enterButton(View view) {

        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

    }

}