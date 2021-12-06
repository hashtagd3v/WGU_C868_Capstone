package android.jmichalek.jaymichalek_capstone.All.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.jmichalek.jaymichalek_capstone.All.Database.Repository;
import android.jmichalek.jaymichalek_capstone.All.Entities.Course;
import android.jmichalek.jaymichalek_capstone.All.Entities.Term;
import android.jmichalek.jaymichalek_capstone.All.Util.DateValidator;
import android.jmichalek.jaymichalek_capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TermDetail extends AppCompatActivity {

    private String termTitle;
    private String termStart;
    private String termEnd;
    private String created_date;
    private EditText editTermTitle;
    private EditText editTermStart;
    private EditText editTermEnd;
    private Repository repository;
    private List<Term> mTerms;
    private int current_termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab Term ID and other term information from Adapter:
        current_termID = getIntent().getIntExtra("id", -1);
        termTitle = getIntent().getStringExtra("name");
        termStart = getIntent().getStringExtra("start");
        termEnd = getIntent().getStringExtra("end");
        created_date = getIntent().getStringExtra("created");

        //Connect activity layout with edit text fields:
        editTermTitle = findViewById(R.id.editText_termTitle);
        editTermStart = findViewById(R.id.editText_startDate);
        editTermEnd = findViewById(R.id.editText_endDate);

        //Set EditText fields with selected term information:
        editTermTitle.setText(termTitle);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);

        repository = new Repository(getApplication());

        //Set Recycler View to show list of associated courses:
        List<Course> allCourses = repository.getCoursesByTermId(current_termID);

        if (allCourses.isEmpty()) {
            Toast.makeText(TermDetail.this, "No Courses Available.", Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView_courseList);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourse(allCourses);

    }

    /* Inflates refresh menu option for Recycler View.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;

    }

    /* This method enables user to switch back to previous screen.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_refresh:
                repository = new Repository(getApplication());
                //Filter courses by Term ID only:
                List<Course> allCourses = repository.getCoursesByTermId(current_termID);
                RecyclerView recyclerView = findViewById(R.id.recyclerView_courseList);
                final CourseAdapter courseAdapter = new CourseAdapter(this);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                courseAdapter.setCourse(allCourses);
                if (allCourses.isEmpty()) {
                    Toast.makeText(TermDetail.this, "No Courses Available.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetail.this, "Refreshed.", Toast.LENGTH_LONG).show();
                }

        }

        return super.onOptionsItemSelected(item);
    }

    /* This method enables user to update term information of selected term item into database.*/
    public void updateTerm(View view) {

        Term current_term;
        String name;
        String start;
        String end;

        Date currentDateTime = Calendar.getInstance().getTime();
        String created_date = currentDateTime.toString();

        DateValidator validator = new DateValidator();

        name = editTermTitle.getText().toString();
        start = editTermStart.getText().toString();
        end = editTermEnd.getText().toString();

        //Validates date user input from text fields:
        if (validator.isDateValid(start) && validator.isDateValid(end)) {

            if (!validator.isDateSequenceValid(start, end)) {

                Toast.makeText(TermDetail.this, "Please ensure that start date is prior to end date.", Toast.LENGTH_LONG).show();

            } else {

                mTerms = repository.getAllTerms();
                for (int i = 0; i < mTerms.size(); i++) {

                    current_term = mTerms.get(i);

                    if (current_term.getTermID() == current_termID) {

                        current_term.setTermName(name);
                        current_term.setTermStart(start);
                        current_term.setTermEnd(end);
                        current_term.setCreated_date(created_date);

                        repository.update(current_term);
                        Toast.makeText(TermDetail.this, "Term Updated.", Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(TermDetail.this, TermList.class);
                        startActivity(intent);

                    }

                }

            }

        } else if (name.isEmpty() || start.isEmpty() || end.isEmpty()){

            Toast.makeText(TermDetail.this, "Fill out required fields.", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(TermDetail.this, "Please type required input date format in text fields.", Toast.LENGTH_LONG).show();

        }


    }

    /* This method deletes selected term from database. */
    public void deleteTerm(View view) {

        Term current_term;
        mTerms = repository.getAllTerms();
        for (int i = 0; i < mTerms.size(); i++) {

            current_term = mTerms.get(i);
            if (current_term.getTermID() == current_termID) {
                List<Course> allCourses = repository.getCoursesByTermId(current_termID);
                //Check if term has courses in it:
                if (allCourses.isEmpty()) {
                    repository.delete(current_term);
                    Toast.makeText(TermDetail.this, "Term Deleted.", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(TermDetail.this, TermList.class);
                    startActivity(intent);
                    break;
                }
                else {
                    Toast.makeText(TermDetail.this, "Unable to delete terms with associated courses. Please delete courses listed first.", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    /* This method takes user to next screen/add course screen.*/
    public void addCourse(View view) {

        Intent intent = new Intent(TermDetail.this, AddCourseScreen.class);
        //SEND CURRENT TERM'S ID TO AddCourseScreen.java TO UTILIZE SAME TERM ID:
        intent.putExtra("id", current_termID);
        startActivity(intent);

    }

}