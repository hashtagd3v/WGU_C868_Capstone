package android.jmichalek.jaymichalekwguscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class CourseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    /* This method takes user to add assessment screen from course detail screen*/
    public void addAssessmentBtn(View view) {

        Intent intent = new Intent(CourseDetail.this, AddAssessmentScreen.class);
        startActivity(intent);

    }

    /* This method takes a user to add notes screen from course detail screen.*/
    public void addNoteBtn(View view) {

        Intent intent = new Intent(CourseDetail.this, AddNoteScreen.class);
        startActivity(intent);

    }

}