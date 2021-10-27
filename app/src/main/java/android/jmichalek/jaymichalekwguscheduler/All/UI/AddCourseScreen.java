package android.jmichalek.jaymichalekwguscheduler.All.UI;

import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Course;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseScreen extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_course_screen);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab associated term ID to use for saving course:
        currentTermID = getIntent().getIntExtra("id", -1);

        //Connect activity layout of edit text fields:
        editCourseTitle = findViewById(R.id.editText_courseTitle);
        editStart = findViewById(R.id.editText_courseStart);
        editEnd = findViewById(R.id.editText_courseEnd);
        editStatus = findViewById(R.id.editText_courseStatus);
        editInstructor = findViewById(R.id.editText_instructorName);
        editPhone = findViewById(R.id.editText_phone);
        editEmail = findViewById(R.id.editText_email);
        editNotes = findViewById(R.id.editText_note);

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

    /* This method adds new course to database.*/
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
        if (    courseTitle.isEmpty() || courseStart.isEmpty() || courseEnd.isEmpty()
                || courseStatus.isEmpty() || courseInstructor.isEmpty() ||
                phone.isEmpty() || email.isEmpty()) {

            Toast.makeText(AddCourseScreen.this, "Fill out required fields.", Toast.LENGTH_LONG).show();
            return;

        }
        else {

            Course newCourse = new Course(0, courseTitle, courseStart, courseEnd, courseStatus, courseInstructor, phone, email, notes, currentTermID);
            repository.insert(newCourse);
            Toast.makeText(AddCourseScreen.this, "New course added. Refresh previous screen.", Toast.LENGTH_LONG).show();

        }

    }

}