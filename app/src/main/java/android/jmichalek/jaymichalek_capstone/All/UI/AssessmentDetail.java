package android.jmichalek.jaymichalek_capstone.All.UI;

import static android.jmichalek.jaymichalek_capstone.All.UI.MyReceiver.REQUEST_CODE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.jmichalek.jaymichalek_capstone.All.Database.Repository;
import android.jmichalek.jaymichalek_capstone.All.Entities.Assessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.ObjectiveAssessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.PerformanceAssessment;
import android.jmichalek.jaymichalek_capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int currentCourseID;
    private int assessmentID;
    private boolean assessment_type;
    private String assessmentTitle;
    private String assessmentStart;
    private String assessmentEnd;
    private String current_assessmentType;
    private EditText editName;
    private EditText editStart;
    private EditText editEnd;
    private Spinner spinner_assessmentType;
    private Repository repository;
    private List<Assessment> assessmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab current course ID:
        currentCourseID = getIntent().getIntExtra("course", -1);

        //Grab information of selected assessment:
        assessmentID = getIntent().getIntExtra("assessment_id", -1);
        assessmentTitle = getIntent().getStringExtra("name");
        assessmentStart = getIntent().getStringExtra("start");
        assessmentEnd = getIntent().getStringExtra("end");
        current_assessmentType = getIntent().getStringExtra("type");
        System.out.println(current_assessmentType);

        //Connect activity layout of edit text fields:
        editName = findViewById(R.id.assessmentEditText_name);
        editStart = findViewById(R.id.assessmentEditText_start);
        editEnd = findViewById(R.id.assessmentEditText_end);

        //Connect activity layout for spinner and set choices for assessment types from string resource:
        spinner_assessmentType = findViewById(R.id.assessmentSpinner_type);
        spinner_assessmentType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessmentType_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_assessmentType.setAdapter(adapter);
        //Set default selection to current assessment type:
        if (current_assessmentType.equals("Performance Assessment")) {
            spinner_assessmentType.setSelection(adapter.getPosition("Performance Assessment"));
        }
        else {
            spinner_assessmentType.setSelection(adapter.getPosition("Objective Assessment"));
        }

        //Set text fields with assessment's information:
        editName.setText(assessmentTitle);
        editStart.setText(assessmentStart);
        editEnd.setText(assessmentEnd);

        repository = new Repository(getApplication());

    }

    /* Inflates refresh menu option for Recycler View.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_assessmentoptions, menu);
        return true;

    }

    /* This method enables user to switch back to previous screen.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.assessment_notifyStart:
                String startDate = editStart.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myStartDate = null;
                //Parse Start Date here:
                try {
                    if (!startDate.isEmpty())
                        myStartDate = sdf.parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myStartDate.getTime();
                //Set notification for start date here:
                Intent intent = new Intent(AssessmentDetail.this, MyReceiver.class);
                intent.putExtra("key", "Assessment has started.");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.assessment_notifyEnd:
                String endDate = editEnd.getText().toString();
                String myFormat2 = "MM/dd/yy";
                SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
                Date myEndDate = null;
                //Parse End Date here:
                try {
                    myEndDate = sdf2.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger2 = myEndDate.getTime();
                Intent secondIntent = new Intent(AssessmentDetail.this, MyReceiver.class);
                secondIntent.putExtra("key", "Assessment has ended.");
                PendingIntent sender2 = PendingIntent.getBroadcast(AssessmentDetail.this, REQUEST_CODE, secondIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    /* This method updates assessment details and saves to database. */
    public void updateAssessment(View view) {

        assessmentTitle = editName.getText().toString();
        assessmentStart = editStart.getText().toString();
        assessmentEnd = editEnd.getText().toString();

        if ( assessmentTitle.isEmpty() || assessmentStart.isEmpty() || assessmentEnd.isEmpty() ) {

            Toast.makeText(AssessmentDetail.this, "Fill out required fields.", Toast.LENGTH_LONG).show();

        } else {

            assessmentList = repository.getAssessmentsByCourseID(currentCourseID);
            for (int i = 0; i < assessmentList.size(); i++) {

                Assessment assessment = assessmentList.get(i);

                if (assessment.getAssessment_id() == assessmentID) {

                    repository.delete(assessment);

                 }

                }

            }

        //Check if performance or objective type of assessment and create new proper assessment type:
        //Used Downcasting for polymorphism requirement:

        if (assessment_type == true) {
            //Change type of assessment prior to saving based on user selection from spinner:
            String changeType = "Performance Assessment";
            Assessment performanceAssessment = (Assessment) new PerformanceAssessment(0, assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, changeType, 0);
            Repository addToAssessment = new Repository(getApplication());
            addToAssessment.insert(performanceAssessment);

        }
        else {
            //Change type of assessment prior to saving based on user selection from spinner:
            String changeType = "Objective Assessment";
            Assessment objectiveAssessment = (Assessment) new ObjectiveAssessment(0,assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, changeType, 0);
            Repository addToAssessment = new Repository(getApplication());
            addToAssessment.insert(objectiveAssessment);
            }

        }


    //This method deletes current assessment selected from database.
    public void deleteAssessment(View view) {

        assessmentList = repository.getAssessmentsByCourseID(currentCourseID);
        for (int i = 0; i < assessmentList.size(); i++) {

            Assessment assessment = assessmentList.get(i);

            if (assessment.getAssessment_id() == assessmentID) {

                repository.delete(assessment);
                Toast.makeText(AssessmentDetail.this, "Deleted.", Toast.LENGTH_LONG).show();

            }

        }

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        String selectedString = String.valueOf(adapterView.getItemAtPosition(pos));

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