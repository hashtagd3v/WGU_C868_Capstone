package android.jmichalek.jaymichalekwguscheduler.All.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class TermDetail extends AppCompatActivity {

    String termTitle;
    String termStart;
    String termEnd;
    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;
    Repository repository;
    int current_termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //FIXME: console println shows -1 for ID and null for name. Unable to obtain info.
        //Grab Term ID and other term information from Adapter:
        current_termID = getIntent().getIntExtra("id", -1);
        termTitle = getIntent().getStringExtra("name");
        termStart = getIntent().getStringExtra("start");
        termEnd = getIntent().getStringExtra("end");

        System.out.println("Current Term Selected: " + current_termID + " " + termTitle);

        editTermTitle = findViewById(R.id.editText_termTitle);
        editTermStart = findViewById(R.id.editText_startDate);
        editTermEnd = findViewById(R.id.editText_endDate);

        //Set EditText fields with selected term information:
        editTermTitle.setText(termTitle);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);

        repository = new Repository(getApplication());

    }

    /*Inflates refresh menu option for Recycler View.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_recyclerview, menu);
        return true;

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

    //FIXME: Obtain previous info and populate Edit Text Fields with info on screen.


    /*This method enables user to edit course detail when course item row is clicked from Term Detail screen.*/
    public void editCourse(View view) {

        //TODO: Implement edit course feature.

    }

    /*This method enables user to update term information of selected term item into database.*/
    public void updateTerm(View view) {

        //TODO: Implement update current term feature.

    }

}