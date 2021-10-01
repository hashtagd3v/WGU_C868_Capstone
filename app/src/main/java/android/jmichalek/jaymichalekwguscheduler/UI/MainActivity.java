package android.jmichalek.jaymichalekwguscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* When Enter button is pressed, this method brings user to next screen which
     * is TermList screen.*/
    public void enterButton(View view) {

        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

    }

}