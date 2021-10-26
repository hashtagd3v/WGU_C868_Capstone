package android.jmichalek.jaymichalekwguscheduler.All.UI;

import static android.jmichalek.jaymichalekwguscheduler.All.UI.MyReceiver.REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Assessment;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Course;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.util.Log;
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

public class CourseDetail extends AppCompatActivity {

    int courseID;
    String courseTitle;
    String courseStart;
    String courseEnd;
    String courseStatus;
    String courseInstructor;
    String phone;
    String email;
    String notes;
    EditText editCourseTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructor;
    EditText editPhone;
    EditText editEmail;
    EditText editNotes;
    Repository repository;
    List<Course> mCourses;
    int currentTermID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab associated term ID to use for saving course:
        currentTermID = getIntent().getIntExtra("id", -1);

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

        //Connect activity layout of edit text fields:
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

        repository = new Repository(getApplication());

        //Filter only associated assessments:
        List<Assessment> allAssessments = repository.getAssessmentsByCourseID(courseID);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_assessmentList);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessment(allAssessments);

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
                Repository repository = new Repository(getApplication());
                //Filter Assessments by Course ID only:
                List<Assessment> allAssessments = repository.getAssessmentsByCourseID(courseID);
                RecyclerView recyclerView = findViewById(R.id.recyclerView_assessmentList);
                final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
                recyclerView.setAdapter(assessmentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                assessmentAdapter.setAssessment(allAssessments);

        }

        return super.onOptionsItemSelected(item);

    }

    /* This method updates course information and saves to database.*/
    public void saveCourse(View view) {

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
        if (courseTitle.isEmpty() || courseStart.isEmpty() || courseEnd.isEmpty()
                || courseStatus.isEmpty() || courseInstructor.isEmpty() ||
                phone.isEmpty() || email.isEmpty()) {

            Toast.makeText(CourseDetail.this, "Fill out required fields.", Toast.LENGTH_LONG).show();

        } else {

            mCourses = repository.getAllCourses();
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

    // This method enables user to delete course from database.
    public void deleteCourse(View view) {

        Course current_course;

        mCourses = repository.getAllCourses();
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
