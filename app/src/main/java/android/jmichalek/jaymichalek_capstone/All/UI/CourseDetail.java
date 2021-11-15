package android.jmichalek.jaymichalek_capstone.All.UI;

import static android.jmichalek.jaymichalek_capstone.All.UI.MyReceiver.REQUEST_CODE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.jmichalek.jaymichalek_capstone.All.Database.Repository;
import android.jmichalek.jaymichalek_capstone.All.Entities.Assessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.Course;
import android.jmichalek.jaymichalek_capstone.All.Entities.ObjectiveAssessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.PerformanceAssessment;
import android.jmichalek.jaymichalek_capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {

    private int courseID;
    private int currentTermID;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseInstructor;
    private String phone;
    private String email;
    private String notes;
    private EditText editCourseTitle;
    private EditText editStart;
    private EditText editEnd;
    private EditText editStatus;
    private EditText editInstructor;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editNotes;
    private Repository repository_performance;
    private Repository repository_objective;
    private Repository repository;
    private List<Course> mCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab associated term ID to use for saving course:
        currentTermID = getIntent().getIntExtra("term", -1);

        //Grab information of current term selected:
        courseID = getIntent().getIntExtra("id", -1);
        courseTitle = getIntent().getStringExtra("name");
        courseStart = getIntent().getStringExtra("start");
        courseEnd = getIntent().getStringExtra("end");
        courseStatus = getIntent().getStringExtra("status");
        courseInstructor = getIntent().getStringExtra("instructor");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        notes = getIntent().getStringExtra("note");

        //Connect activity layout with edit text fields:
        editCourseTitle = findViewById(R.id.editText_courseTitle);
        editStart = findViewById(R.id.editText_courseStart);
        editEnd = findViewById(R.id.editText_courseEnd);
        editStatus = findViewById(R.id.editText_courseStatus);
        editInstructor = findViewById(R.id.editText_instructorName);
        editPhone = findViewById(R.id.editText_phone);
        editEmail = findViewById(R.id.editText_email);
        editNotes = findViewById(R.id.editText_note);


        //Set text fields with selected course information:
        editCourseTitle.setText(courseTitle);
        editStart.setText(courseStart);
        editEnd.setText(courseEnd);
        editStatus.setText(courseStatus);
        editInstructor.setText(courseInstructor);
        editPhone.setText(phone);
        editEmail.setText(email);
        editNotes.setText(notes);


        //Filter only associated performance assessments:
        repository_performance = new Repository(getApplication());
        List<PerformanceAssessment> allPerformanceAssessments = repository_performance.getPerformanceAssessmentsByCourseID(courseID);

        if (allPerformanceAssessments.isEmpty()) {
            Toast.makeText(CourseDetail.this, "No Performance Assessments Available.", Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView_performance = findViewById(R.id.recyclerView_performanceList);
        final PerformanceAssessmentAdapter performanceAssessmentAdapter = new PerformanceAssessmentAdapter(this);
        recyclerView_performance.setAdapter(performanceAssessmentAdapter);
        recyclerView_performance.setLayoutManager(new LinearLayoutManager(this));
        performanceAssessmentAdapter.setAssessment(allPerformanceAssessments);


        //Filter only associated objective assessments:
        repository_objective = new Repository(getApplication());
        List<ObjectiveAssessment> allObjectiveAssessments = repository_objective.getObjectiveAssessmentsByCourseID(courseID);

        if (allObjectiveAssessments.isEmpty()) {
            Toast.makeText(CourseDetail.this, "No Objective Assessments Available.", Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView_objective = findViewById(R.id.recyclerView_objectiveList);
        final ObjectiveAssessmentAdapter objectiveAssessmentAdapter = new ObjectiveAssessmentAdapter(this);
        recyclerView_objective.setAdapter(objectiveAssessmentAdapter);
        recyclerView_objective.setLayoutManager(new LinearLayoutManager(this));
        objectiveAssessmentAdapter.setAssessment(allObjectiveAssessments);


    }

    /* Inflates refresh menu option for Recycler View.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;

    }

    /* This method enables user to switch back to previous screen or refresh screen.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notes);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.menu_notify:
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
                Intent intent = new Intent(CourseDetail.this, MyReceiver.class);
                intent.putExtra("key", "Course has started.");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetail.this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.menu_notifyEnd:
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
                Intent secondIntent = new Intent(CourseDetail.this, MyReceiver.class);
                secondIntent.putExtra("key", "Course has ended.");
                PendingIntent sender2 = PendingIntent.getBroadcast(CourseDetail.this, REQUEST_CODE, secondIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;
            case R.id.menu_optionRefresh:
                //Refresh performance list first:

                Repository repository = new Repository(getApplication());
                //Filter Performance Assessments by Course ID only:
                List<PerformanceAssessment> allPerformanceAssessments = repository.getPerformanceAssessmentsByCourseID(courseID);
                RecyclerView recyclerView = findViewById(R.id.recyclerView_performanceList);
                final PerformanceAssessmentAdapter performanceAssessmentAdapter = new PerformanceAssessmentAdapter(this);
                recyclerView.setAdapter(performanceAssessmentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                performanceAssessmentAdapter.setAssessment(allPerformanceAssessments);
                if (allPerformanceAssessments.isEmpty()) {
                    Toast.makeText(CourseDetail.this, "No Performance Assessments Available.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CourseDetail.this, "Refreshed Performance Assessment List.", Toast.LENGTH_LONG).show();
                }

                //Refresh objective list next:

                Repository repository1 = new Repository(getApplication());
                List<ObjectiveAssessment> allObjectiveAssessments = repository1.getObjectiveAssessmentsByCourseID(courseID);
                RecyclerView recyclerView1 = findViewById(R.id.recyclerView_objectiveList);
                final ObjectiveAssessmentAdapter objectiveAssessmentAdapter = new ObjectiveAssessmentAdapter(this);
                recyclerView1.setAdapter(objectiveAssessmentAdapter);
                recyclerView1.setLayoutManager(new LinearLayoutManager(this));
                objectiveAssessmentAdapter.setAssessment(allObjectiveAssessments);
                if (allObjectiveAssessments.isEmpty()) {
                    Toast.makeText(CourseDetail.this, "No Objective Assessments Available.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CourseDetail.this, "Refreshed Objective Assessment List.", Toast.LENGTH_LONG).show();
                }

        }

        return super.onOptionsItemSelected(item);

    }

    /* This method updates course information and saves to database. */
    public void updateCourse(View view) {

        Course currentCourse;
        courseTitle = editCourseTitle.getText().toString();
        courseStart = editStart.getText().toString();
        courseEnd = editEnd.getText().toString();
        courseStatus = editStatus.getText().toString();
        courseInstructor = editInstructor.getText().toString();
        phone = editPhone.getText().toString();
        email = editEmail.getText().toString();
        notes = editNotes.getText().toString();

        //Check if text fields are not empty:
        if (    courseTitle.isEmpty() || courseStart.isEmpty() || courseEnd.isEmpty()
                || courseStatus.isEmpty() || courseInstructor.isEmpty() ||
                phone.isEmpty() || email.isEmpty()  ) {

            Toast.makeText(CourseDetail.this, "Fill out required fields.", Toast.LENGTH_LONG).show();

        } else {

            mCourses = repository.getCoursesByTermId(currentTermID);
            for (int i = 0; i < mCourses.size(); i++) {

                currentCourse = mCourses.get(i);

                if (currentCourse.getCourseID() == courseID) {

                    currentCourse.setCourseName(courseTitle);
                    currentCourse.setCourseStart(courseStart);
                    currentCourse.setCourseEnd(courseEnd);
                    currentCourse.setStatus(courseStatus);
                    currentCourse.setCourseInstructorName(courseInstructor);
                    currentCourse.setInstructorPhone(phone);
                    currentCourse.setInstructorEmail(email);
                    currentCourse.setCourseNote(notes);
                    repository.update(currentCourse);
                    Toast.makeText(CourseDetail.this, "Updated course information. Refresh previous screen.", Toast.LENGTH_LONG).show();

                }
            }

        }

    }

    // This method enables user to delete course from database. //FIXME!!!! CRASHES APP WHEN DELETE BUTTON IS CLICKED.
    public void deleteCourse(View view) {

        Course current_course;

        mCourses = repository.getCoursesByTermId(currentTermID);         
        for(int i = 0; i < mCourses.size(); i++) {

            current_course = mCourses.get(i);

            if (current_course.getCourseID() == courseID) {

                List<Assessment> assessments = repository.getAssessmentsByCourseID(courseID);

                if (assessments.isEmpty()) {

                    repository.delete(current_course);
                    Toast.makeText(CourseDetail.this, "Course deleted. Go back and refresh screen.", Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(CourseDetail.this, "Course cannot be deleted. Please delete all associated assessments.", Toast.LENGTH_LONG).show();

                }
            }

        }

    }

    // This method enables user to add new assessment.
    public void addAssessment(View view) {

        Intent intent = new Intent(CourseDetail.this, AddAssessmentScreen.class);
        //Send current course ID to AddAssessment.java to utilize same course ID.
        intent.putExtra("id", courseID);
        startActivity(intent);

    }

}
