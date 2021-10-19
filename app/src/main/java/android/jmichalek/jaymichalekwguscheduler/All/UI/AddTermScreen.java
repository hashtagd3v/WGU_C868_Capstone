package android.jmichalek.jaymichalekwguscheduler.All.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddTermScreen extends AppCompatActivity {

    String termTitle;
    String termStart;
    String termEnd;
    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;
    Repository repository;
//    Term currentTerm;

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

        if (currentTermTitle != null) {
            Term newTerm = new Term(0, currentTermTitle, currentStart, currentEnd);
            repository.insert(newTerm);
        }

    }

}