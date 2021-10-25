package android.jmichalek.jaymichalekwguscheduler.All.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;

public class CourseDetail extends AppCompatActivity {

    String courseID;
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

        System.out.println("Course Detail: Term ID is " + currentTermID);

        //Grab information of current term selected:
        courseID = getIntent().getStringExtra("id");
        courseTitle = getIntent().getStringExtra("name");
        courseStart = getIntent().getStringExtra("start");
        courseEnd = getIntent().getStringExtra("end");
        courseStatus = getIntent().getStringExtra("status");
        courseInstructor = getIntent().getStringExtra("instructor");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        notes = getIntent().getStringExtra("note");

        System.out.println("Course Detail: Course Name is " + courseTitle);

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

        //FIXME: List/filter only assessments associated with course ID/create new method in repository:
        List<Assessment> allAssessments = repository.getAllAssessments();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_assessmentList);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessment(allAssessments);

    }

    /* Inflates refresh menu option for Recycler View.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_recyclerview, menu);
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
            //TODO: Implement start course date alert.

        }

        return super.onOptionsItemSelected(item);

    }

    /* This method updates course information and saves to database.*/
    public void saveCourse(View view) {

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

            Course newCourse = new Course(0, courseTitle, courseStart, courseEnd, courseStatus, courseInstructor, phone, email, notes, currentTermID);
            repository.insert(newCourse);
            Toast.makeText(CourseDetail.this, "New course added. Refresh previous screen.", Toast.LENGTH_LONG).show();

        }

    }

    // This method enables user to delete course from database.
    public void deleteCourse(View view) {

        //TODO: Implement delete course feature.

    }

    // This method enables user to add new assessment.
    public void addAssessment(View view) {

        //TODO: Send data to addAssessment screen and create new intent to show next screen.

    }

}
