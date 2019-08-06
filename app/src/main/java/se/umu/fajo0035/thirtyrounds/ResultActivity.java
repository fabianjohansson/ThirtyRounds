package se.umu.fajo0035.thirtyrounds;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int[] roundScoreIDs = {
            R.id.choise_round1,
            R.id.choise_round2,
            R.id.choise_round3,
            R.id.choise_round4,
            R.id.choise_round5,
            R.id.choise_round6,
            R.id.choise_round7,
            R.id.choise_round8,
            R.id.choise_round9,
            R.id.choise_round10
    };
    private TextView[] roundScoreDisplay = new TextView[roundScoreIDs.length];
    private Button newGame;
    private int value;
    private HashMap<String,Integer> scoreEachSelection;

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
            //value sets to the total score received from activity_main
            value = extras.getInt("finalScore");
            //scoreEachSelection sets to the score for each selection received from activity_main
            scoreEachSelection = (HashMap<String, Integer>)extras.getSerializable("scoreEachRound");
        }

        /*Adds the final score to the text view*/
        resultDisplay = (TextView)findViewById(R.id.total_score_display);
        resultDisplay.setText("Total score: " + Integer.toString(value));

        //generates the Textviews to display result for each selection
        for(int i = 0; i < roundScoreIDs.length;i++){
            roundScoreDisplay[i] = (TextView)findViewById(roundScoreIDs[i]);
        }

        /*
        * Assigns the score for each values to a textview */
        int index = 0;
        for(Map.Entry<String,Integer> entry: scoreEachSelection.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            roundScoreDisplay[index].setText("Selection " + key +  " gave " + Integer.toString(value)+ " points.");
            index++;
        }

        /* If newGame button is clicked the view is switched to main view*/
        newGame = (Button)findViewById(R.id.return_button);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for returning to main view
                Intent returnIntent = new Intent(ResultActivity.this,MainActivity.class);
                //for clearing backstack
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //starts activity_main
                startActivity(returnIntent);
                //finishes current activity
                finish();
            }
        });
    }
}
