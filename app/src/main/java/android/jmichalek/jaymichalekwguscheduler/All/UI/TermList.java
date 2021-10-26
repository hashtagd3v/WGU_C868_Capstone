package android.jmichalek.jaymichalekwguscheduler.All.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class TermList extends AppCompatActivity {

    private Repository repository;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // Allows user to switch back to previous screen & retain information.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.recyclerView_term);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

    }

    /*Inflates menu/refresh option for this screen.*/
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
            case R.id.menu_refresh:
                repository = new Repository(getApplication());
                List<Term> allTerms = repository.getAllTerms();
                RecyclerView recyclerView = findViewById(R.id.recyclerView_term);
                final TermAdapter termAdapter = new TermAdapter(this);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);

        }

        return super.onOptionsItemSelected(item);
    }

    /* This method takes user to add term screen when Add Term button is clicked from
    * Term List screen.*/
    public void addTerm(View view) {

        Intent intent = new Intent(TermList.this, AddTermScreen.class);
        startActivity(intent);

    }

}