package se.umu.fajo0035.thirtyrounds;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView resultDisplay;
    private int value;
    //Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("finalScore");
            //The key argument here must match that used in the other activity
        }

        resultDisplay = (TextView)findViewById(R.id.result_display);
        resultDisplay.setText("Your result is: " + Integer.toString(value));
    }
}
