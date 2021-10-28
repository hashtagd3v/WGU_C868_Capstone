package android.jmichalek.jaymichalekwguscheduler.All.UI;

import static android.jmichalek.jaymichalekwguscheduler.All.UI.MyReceiver.REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Assessment;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {

    int currentCourseID;
    int assessmentID;
    String assessmentTitle;
    String assessmentStart;
    String assessmentEnd;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Repository repository;
    List<Assessment> mAssessments;

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

        //Connect activity layout of edit text fields
        editName = findViewById(R.id.assessmentEditText_name);
        editStart = findViewById(R.id.assessmentEditText_start);
        editEnd = findViewById(R.id.assessmentEditText_end);

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

        Assessment assessment;

        assessmentTitle = editName.getText().toString();
        assessmentStart = editStart.getText().toString();
        assessmentEnd = editEnd.getText().toString();

        if ( assessmentTitle.isEmpty() || assessmentStart.isEmpty() || assessmentEnd.isEmpty() ) {

            Toast.makeText(AssessmentDetail.this, "Fill out required fields.", Toast.LENGTH_LONG).show();

        } else {

            mAssessments = repository.getAssessmentsByCourseID(currentCourseID);
            for (int i = 0; i < mAssessments.size(); i++) {

                assessment = mAssessments.get(i);

                if (assessment.getAssessmentID() == assessmentID) {

                    assessment.setAssessmentName(assessmentTitle);
                    assessment.setAssessmentStart(assessmentStart);
                    assessment.setAssessmentEnd(assessmentEnd);
                    repository.update(assessment);
                    Toast.makeText(AssessmentDetail.this, "Assessment updated. Refresh previous screen.", Toast.LENGTH_LONG).show();

                }

            }
        }

    }

    //This method deletes current assessment selected from database.
    public void deleteAssessment(View view) {

        Assessment assessment;

        mAssessments = repository.getAssessmentsByCourseID(currentCourseID);
        for (int i = 0; i < mAssessments.size(); i++) {

            assessment = mAssessments.get(i);

            if (assessment.getAssessmentID() == assessmentID) {

                repository.delete(assessment);
                Toast.makeText(AssessmentDetail.this, "Assessment deleted. Go back and refresh screen.", Toast.LENGTH_LONG).show();
                break;

            }

        }

    }

}