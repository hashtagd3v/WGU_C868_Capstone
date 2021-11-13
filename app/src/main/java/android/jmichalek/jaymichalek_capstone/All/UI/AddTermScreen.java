package android.jmichalek.jaymichalek_capstone.All.UI;

import android.jmichalek.jaymichalek_capstone.All.Database.Repository;
import android.jmichalek.jaymichalek_capstone.All.Entities.Term;
import android.jmichalek.jaymichalek_capstone.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTermScreen extends AppCompatActivity {

    String termTitle;
    String termStart;
    String termEnd;
    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term_screen);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termTitle = getIntent().getStringExtra("name");
        termStart = getIntent().getStringExtra("start");
        termEnd = getIntent().getStringExtra("end");

        editTermTitle = findViewById(R.id.editText_termTitle);
        editTermStart = findViewById(R.id.editText_startDate);
        editTermEnd = findViewById(R.id.editText_endDate);

        editTermTitle.setText(termTitle);;
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);

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

    /*Enables user to add new term in Add Term Screen.*/
    public void saveTerm(View view) {

        String currentTermTitle = editTermTitle.getText().toString();
        String currentStart = editTermStart.getText().toString();
        String currentEnd = editTermEnd.getText().toString();

        //Check if term title edit text field is not empty:
        if (    currentTermTitle.isEmpty() ||
                currentStart.isEmpty()     ||
                currentEnd.isEmpty()    ) {

            Toast.makeText(AddTermScreen.this, "Fill out required fields.", Toast.LENGTH_LONG).show();
            return;

        }
        else {

            Term newTerm = new Term(0, currentTermTitle, currentStart, currentEnd);
            repository.insert(newTerm);
            Toast.makeText(AddTermScreen.this, "New term added. Go back and refresh screen.", Toast.LENGTH_LONG).show();

        }

    }

}