package android.jmichalek.jaymichalekwguscheduler.All.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.All.Database.Repository;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Try code/DB test:
//        Repository repository = new Repository(getApplication());
//        Term term = new Term(0,"Term 1", "1/1/21", "3/30/21");
//        repository.insert(term);

        //TODO: Add 2 terms, 2 courses, 2 assessments - dummy data

    }

    /* When Enter button is pressed, this method brings user to next screen which
     * is TermList screen.*/
    public void enterButton(View view) {

        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

    }

}