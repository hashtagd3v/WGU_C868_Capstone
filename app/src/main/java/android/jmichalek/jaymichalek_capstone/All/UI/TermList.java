package android.jmichalek.jaymichalek_capstone.All.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.jmichalek.jaymichalek_capstone.All.Database.Repository;
import android.jmichalek.jaymichalek_capstone.All.Entities.Term;
import android.jmichalek.jaymichalek_capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermList extends AppCompatActivity {

    private Repository repository;
    private TermAdapter termAdapter;

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

        if (allTerms.isEmpty()) {
            Toast.makeText(TermList.this, "No Terms Available.", Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView_term);
        termAdapter = new TermAdapter(this, allTerms);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

    }

    /*Inflates menu/refresh option for this screen.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_recyclerview, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };

        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search Terms");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                termAdapter.getFilter().filter(newText.toLowerCase().toString());
                return false;
            }
        });

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
                final TermAdapter termAdapter = new TermAdapter(this, allTerms);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
                Toast.makeText(TermList.this, "Refreshed.", Toast.LENGTH_LONG).show();

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