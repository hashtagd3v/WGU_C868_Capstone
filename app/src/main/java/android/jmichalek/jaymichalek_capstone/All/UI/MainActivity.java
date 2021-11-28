package android.jmichalek.jaymichalek_capstone.All.UI;

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

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        addSampleData();

    }

    /* When Enter button is pressed, this method brings user to next screen which
     * is TermList screen.*/
    public void enterButton(View view) {

        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

    }

    public void addSampleData() {

        Repository repository = new Repository(getApplication());
        Term term = new Term(0,"Spring Term", "1/1/21", "3/30/21");
        repository.insert(term);
        Repository term_repository = new Repository(getApplication());
        Term second_term = new Term(0,"Summer Term", "6/1/21", "8/31/21");
        term_repository.insert(second_term);

        Repository course_repository1 = new Repository(getApplication());
        Course course = new Course(0, "Potions", "10/31/21", "12/31/21", "Not started", "Severus Snape",
                "123-456-7891", "severus@hogwarts.com", "Polyjuice Potion", 1);
        course_repository1.insert(course);
        Repository course_repository2 = new Repository(getApplication());
        Course second_course = new Course(0, "Quidditch 101", "06/01/22", "08/31/22", "Not started", "Oliver Wood",
                "123-456-7891", "wood@hogwarts.com", "Golden Snitch Practice", 1);
        course_repository2.insert(second_course);

        //Used Upcasting for polymorphism:

        Assessment assessment = (Assessment) new PerformanceAssessment(0, "Practical Exam 1", "12/15/21", "12/31/21", 1,"Performance Assessment", 0);
        Repository addToAssessment1 = new Repository(getApplication());
        addToAssessment1.insert(assessment);

        Assessment second_assessment = (Assessment) new ObjectiveAssessment(0, "Objective Exam 2", "12/15/21", "12/31/21", 1,"Objective Assessment", 0);
        Repository addToAssessment2 = new Repository(getApplication());
        addToAssessment2.insert(second_assessment);

    }

}