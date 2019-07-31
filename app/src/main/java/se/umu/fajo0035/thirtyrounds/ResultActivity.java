package se.umu.fajo0035.thirtyrounds;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity for creating the result view after a game have been finished
 */
public class ResultActivity extends AppCompatActivity {

    /**
     * @param resultDisplay for displaying the total score of a finished game
     * @param newGame button to go to a new game
     * @param value stores the retrieved value of the final score
     */
    private TextView resultDisplay;
    private Button newGame;
    private int value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*Creates the view and sets the layout to portrait*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*Retrieves the intent passed by the main activity
        * If the recieved bundle is not empty value stores the final score
       */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("finalScore");
            //The key argument here must match that used in the other activity
        }

        /*Adds the final score to the text view*/
        resultDisplay = (TextView)findViewById(R.id.result_display);
        resultDisplay.setText("Your result is: " + Integer.toString(value));

        /* If newGame button is clicked the view is switched to main view*/
        newGame = (Button)findViewById(R.id.return_button);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for returning to main view
                Intent returnIntent = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(returnIntent);
            }
        });
    }
}
