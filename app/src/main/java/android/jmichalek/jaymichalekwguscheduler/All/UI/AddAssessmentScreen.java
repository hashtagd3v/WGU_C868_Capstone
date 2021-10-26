package android.jmichalek.jaymichalekwguscheduler.All.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Assessment;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddAssessmentScreen extends AppCompatActivity {

    int currentCourseID;
    String assessmentTitle;
    String assessmentStart;
    String assessmentEnd;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_screen);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab associated course ID to use for saving assessment:
        currentCourseID = getIntent().getIntExtra("id", -1);

        //Connect activity layout of edit text fields
        editName = findViewById(R.id.assessmentEditText_name);
        editStart = findViewById(R.id.assessmentEditText_start);
        editEnd = findViewById(R.id.assessmentEditText_end);

        repository = new Repository(getApplication());

    }

    /* This method enables user to switch back to previous screen.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }

    /* This method enables user to add new assessment on the database.*/
    public void saveAssessment(View view) {

        assessmentTitle = editName.getText().toString();
        assessmentStart = editStart.getText().toString();
        assessmentEnd = editEnd.getText().toString();

        //Check if fields are empty:
        if (assessmentTitle.isEmpty() || assessmentStart.isEmpty() || assessmentEnd.isEmpty()) {

            Toast.makeText(AddAssessmentScreen.this, "Fill out required fields.", Toast.LENGTH_LONG).show();
            return;

        }
        else {

            Assessment assessment = new Assessment(0, assessmentTitle, assessmentStart, assessmentEnd, currentCourseID);
            repository.insert(assessment);
            Toast.makeText(AddAssessmentScreen.this, "New assessment added. Refresh previous screen.", Toast.LENGTH_LONG).show();

        }

    }

}