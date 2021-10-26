package android.jmichalek.jaymichalekwguscheduler.All.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab information of selected assessment:
        assessmentID = getIntent().getIntExtra("id", -1);
        assessmentTitle = getIntent().getStringExtra("name");
        assessmentStart = getIntent().getStringExtra("start");
        assessmentEnd = getIntent().getStringExtra("end");
        currentCourseID = getIntent().getIntExtra("id", -1);

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

}