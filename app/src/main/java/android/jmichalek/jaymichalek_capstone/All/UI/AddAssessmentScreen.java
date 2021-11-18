package android.jmichalek.jaymichalek_capstone.All.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.jmichalek.jaymichalek_capstone.All.Database.Repository;
import android.jmichalek.jaymichalek_capstone.All.Entities.Assessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.ObjectiveAssessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.PerformanceAssessment;
import android.jmichalek.jaymichalek_capstone.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAssessmentScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int currentCourseID;
    private String assessmentTitle;
    private String assessmentStart;
    private String assessmentEnd;
    private String selectedString;
    private boolean assessment_type;
    private EditText editName;
    private EditText editStart;
    private EditText editEnd;
    private Spinner spinner_assessmentType;
    private Repository repository;

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

        //Connect activity layout for spinner and set choices for assessment types from string resource:
        spinner_assessmentType = findViewById(R.id.addassessmentSpinner_type);
        spinner_assessmentType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessmentType_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_assessmentType.setAdapter(adapter);

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

            //Check assessment type selected (used Downcasting for polymorphism):

            if (assessment_type == true) {
                Assessment performanceAssessment = new PerformanceAssessment(0, assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, selectedString);
                PerformanceAssessment castedPerformance = (PerformanceAssessment) performanceAssessment;
                //Insert assessment to database performance_assessment table (Performance child type):
                repository.insert(castedPerformance);
                Repository addToAssessment = new Repository(getApplication());
                //Insert assessment to database assessment_table (Assessment parent type):
                addToAssessment.insert(performanceAssessment);

            }
            else {
                Assessment objectiveAssessment = new ObjectiveAssessment(0,assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, selectedString);
                ObjectiveAssessment castedObjective = (ObjectiveAssessment) objectiveAssessment;
                //Insert assessment to database objective_assessment table (Objective child type):
                repository.insert(castedObjective);
                Repository addToAssessment = new Repository(getApplication());
                //Insert assessment to database assessment_table (Assessment parent type):
                addToAssessment.insert(objectiveAssessment);
            }

            Toast.makeText(AddAssessmentScreen.this, "New assessment added. Refresh previous screen.", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        //Grab user selection from spinner if Performance of Objective type:
        selectedString = String.valueOf(adapterView.getItemAtPosition(pos));

        if (selectedString.equals("Performance Assessment")) {
            //value of true == performance assessment
            assessment_type = true;
        } else {
            //value of false == objective assessment
            assessment_type = false;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        //Do nothing.

    }

}