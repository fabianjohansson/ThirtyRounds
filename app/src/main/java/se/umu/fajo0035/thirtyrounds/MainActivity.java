package se.umu.fajo0035.thirtyrounds;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


import java.util.*;

/**
 * Activity for the main view of the application/game implements onItemSelectedListener
 * for handling selections from the available score categories
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /* Button for initiating new dice throw and to go to ResultActivity after a game is finished */
    private Button mThrowButton;
    /* holds the adapter and selectable values */
    private Spinner mSelectionSpinner;
    /* displays the current amount of throws */
    private TextView mThrowCounter;
    /* displays the current score */
    private TextView mScoreCounter;
    /* displays the current round */
    private TextView mRoundCounter;
    /* instance of Game for handling dices */
    private Game game;
    /* boolean for enabling the throwbutton to redirect to the ResultActivity */
    private boolean seeResult = false;
    /* contains the drawable dice images reference */
    private int[] diceImages = {
            R.drawable.white1,
            R.drawable.white2,
            R.drawable.white3,
            R.drawable.white4,
            R.drawable.white5,
            R.drawable.white6,
    };
    /* contains the id for all dice ImageButtons in the xml file */
    private static final int[] diceButtonIDs = {
            R.id.dice_one,
            R.id.dice_two,
            R.id.dice_three,
            R.id.dice_four,
            R.id.dice_five,
            R.id.dice_six
    };
    /* Holds imageButtons for handling the imageButton ids int the xml file */
    private ImageButton[] imageButtons = new ImageButton[diceButtonIDs.length];
    /* Stores the IDs for the textviews in the xml file associated to each dice ImageButton */
    private int[] diceTextIDs = {
            R.id.dice1_text_display,
            R.id.dice2_text_display,
            R.id.dice3_text_display,
            R.id.dice4_text_display,
            R.id.dice5_text_display,
            R.id.dice6_text_display,
    };
    /* For handling display of text over the dice buttons when they are saved */
    private TextView[] diceTexts = new TextView[diceTextIDs.length];
    /* Stores the reference to strings of the selectable values */
    private ArrayList<String> mSelectableValues;
    /* Populates the spinner with the selectable values and sets the layout */
    private ArrayAdapter<String> mAdapter;
    /* string for identifying the current score being stored/retrieved in savedInstanceState */
    static final String STATE_SCORE = "currentScore";
    /* string for identifying the current round being stored/retrieved in savedInstanceState */
    static final String STATE_ROUND = "currentRound";
    /* string for identifying the current throw being stored/retrieved in savedInstanceState */
    static final String STATE_THROW = "currentThrow";
    /* string for identifying the score value being passed to ResultActivity */
    static final String END_RESULT = "finalScore";
    /* string for identifying the hashmap with score for each selection being passed to ResultActivity */
    static final String STATE_SCORE_ROUND = "scoreEachRound";
    /* string for identifying the arraylist of all current dices being stored/retrieved in savedInstanceState */
    static final String DICES_ARRAY = "dicesArrayList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the layout to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        game = new Game();

        //initiates the textviews for display of current throw, score and round
        mThrowCounter = (TextView)findViewById(R.id.throw_display);
        mScoreCounter = (TextView)findViewById(R.id.score_display);
        mRoundCounter = (TextView)findViewById(R.id.round_display);

        updateCountersDisplay();

        //initiates the selection spinner
        mSelectionSpinner = findViewById(R.id.selection_spinner);
        //initiates the list for selectable values
        mSelectableValues = new ArrayList<>();
        addSelectableValues();

        //initiates the adapter, adds the selectable strings and sets the dropdown layout
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, mSelectableValues);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//simple_spinner_dropdown_item
        mSelectionSpinner.setAdapter(mAdapter);
        mSelectionSpinner.setOnItemSelectedListener(this);
        mSelectionSpinner.setEnabled(false);


        //Initiates the throwbutton and adds onclicklistener
        mThrowButton = findViewById(R.id.throw_button);
        mThrowButton.setText(R.string.throw_button);
        mThrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For throwing randomized dices
                throwButtonClicked();

            }
        });
        //initiates the text display for dices
        addDicesTextDisplay();
        //initiate diceImageButtons and add onClickListener
        createDiceImageButtons();

    }

    /**
     * adds the string values available for selection
     */
    private void addSelectableValues(){
        mSelectableValues.add(getString(R.string.default_value));
        mSelectableValues.add(getString(R.string.low_value));
        mSelectableValues.add(getString(R.string.four_value));
        mSelectableValues.add(getString(R.string.five_value));
        mSelectableValues.add(getString(R.string.six_value));
        mSelectableValues.add(getString(R.string.seven_value));
        mSelectableValues.add(getString(R.string.eight_value));
        mSelectableValues.add(getString(R.string.nine_value));
        mSelectableValues.add(getString(R.string.ten_value));
        mSelectableValues.add(getString(R.string.eleven_value));
        mSelectableValues.add(getString(R.string.twelve_value));
    }

    /**
     * initiates and adds xml ID for all the textviews that displays if a dice is saved
     */
    private void addDicesTextDisplay(){
        for(int id = 0; id < diceTextIDs.length; id++){
            diceTexts[id] = (TextView)findViewById(diceTextIDs[id]);
        }
    }

    /**
     * initiates the imagebuttons, adds their ID from the xml layout file and adds on click listener
     */
    private void createDiceImageButtons() {
        for(int i = 0; i < diceButtonIDs.length; i++){
            final int button = i;
            imageButtons[button] = (ImageButton)findViewById(diceButtonIDs[button]);
            //sets image to a dice for size porpuses
            imageButtons[button].setImageResource(diceImages[0]);
            //set the dice to not visible because no dices have been rolled jet
            imageButtons[button].setVisibility(View.INVISIBLE);
            imageButtons[button].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //for choosing to save or removing dice and to toggle color
                    toggleImageButton(button);
                }
            });

        }
    }


    /**
     * on click listener for the throwButton
     */
    public void throwButtonClicked() {
        //increases current throw and current round if its the first throw of a round
        game.increasemThrowcounter();
        game.increasemRoundCounter();
        //if max amount of throws in a round have been made a selection have to bee made
       if(game.checkMaxThrows()){
           //makes the selection of values for score calculation clickable
            mSelectionSpinner.setEnabled(true);
            //makes it unable to generate new dice values until a score value have been selected
            mThrowButton.setClickable(false);
            // removes saved dices text display
            removeAllDicesText();
            //removes the black background from clicked dices
            removeImageButtonBackground();
       //if the max amount of rounds have been exceeded and the last selection have been made
       }else if(game.checkMaxRounds() && seeResult){
            goToResultView();
        }
       //generates new dices
        generateDices();
       //updates the dice images according to their new values
        updateDiceImages();
        //updates the throw, score & round displays
        updateCountersDisplay();
        //set selected value being display to default
        mSelectionSpinner.setSelection(0);
    }

    /**
     * creates intent for changing view to the ResultActivity and sends the final sore along
     */
    public void goToResultView() {
        Intent resultIntent = new Intent(MainActivity.this,ResultActivity.class);
        resultIntent.putExtra(END_RESULT,game.getmScoreCounter());
        resultIntent.putExtra(STATE_SCORE_ROUND,game.getScoreEachRound());
        startActivity(resultIntent);
        MainActivity.this.finish();
    }

    /**
     * @param savedInstanceState contains values to be saved when application is in the background
     * saves the state of current round, score, throw and all dices in the game
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_SCORE, game.getmScoreCounter());
        savedInstanceState.putInt(STATE_ROUND, game.getmRoundCounter());
        savedInstanceState.putInt(STATE_THROW, game.getmThrowCounter());
        savedInstanceState.putSerializable(STATE_SCORE_ROUND,game.getScoreEachRound());
        savedInstanceState.putParcelableArrayList(DICES_ARRAY,game.getDices());

        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * @param savedInstanceState saved values to be restored when application is back in the foreground.
     * retrieves state of throw, round, score, scoreEachSelection and all dices being used
     */
    public void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        game.setmScoreCounter(savedInstanceState.getInt(STATE_SCORE));
        game.setmRoundCounter(savedInstanceState.getInt(STATE_ROUND));
        game.setmThrowCounter(savedInstanceState.getInt(STATE_THROW));
        game.setScoreEachRound((HashMap<String, Integer>) savedInstanceState.getSerializable(STATE_SCORE_ROUND));
        game.setDices(savedInstanceState.<Dice>getParcelableArrayList(DICES_ARRAY));
        updateCountersDisplay();
        updateDiceImages();
        refreshImageButtons();
    }

    /**
     * Creates new dices from the game model and assigns position in the arraylist as ID
     */
    private void generateDices() {
        //if their is no dices in the game, six new are generated
        if(game.getDices().size() == 0){
            for (int i = 0;i < 6; i++) {
                game.generateNewDice(i);
            }
        }else {
            //if a dice is not saved a new value gets randomized
            for(Dice dice: game.getDices()){
                if(!dice.getClicked()){
                    dice.setValue();
                }
            }
        }
    }

    /** Handles the users selected value to be calculated for score
     * @param parent adapter where the selection happened
     * @param view  view within the adapter that was selected
     * @param position position of the view in the adapter
     * @param id the row id of the selected item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //if the last round is exceeded the throw button gets enabled to change view to ResultActivity
        if(game.getmRoundCounter() == 10){
            mThrowButton.setText(R.string.game_over);
            mThrowButton.setClickable(true);
            seeResult = true;
        }
        //If the user have made a selection other then the default text: Select value
        if (position != 0) {
            if(mSelectionSpinner.getSelectedItem().toString().contentEquals("Low")){
                System.out.println("test av if");
                game.calculateScore(3, mSelectableValues.get(position));
            }else{
                int selected = Integer.valueOf(mSelectableValues.get(position));
                game.calculateScore(selected, mSelectableValues.get(position));
            }
                //score is increased
                game.increasemScoreCounter();
                //removes the text of the selected value
                //mSelectableValues.set(position, " ");
                //removes the selected value
                mSelectableValues.remove(position);
                //throw button sets to clickable again
                mThrowButton.setClickable(true);
                //the selection spinner sets to not clickable
                mSelectionSpinner.setEnabled(false);
                //updates the current throw,round and score displays
                updateCountersDisplay();
                //removes all saved dices
                removeSelectedDices();
                //removes the dices current image representing their value
                removeAllImageButtonDrawables();
                //toast of score form the selected value
                Toast correctSelectionToast = Toast.makeText(getApplicationContext(),
                        "You gained " + Integer.toString(game.getSum()) + " points!", Toast.LENGTH_SHORT);
                correctSelectionToast.show();


        }
    }

    /**Does not need implementation at the moment
     * @param parent adapter for selection
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * for updating the dices image after throw button have been clicked or after onRestoreInstanceState
     */
    private void updateDiceImages() {
        int index = 0;
        //sets the dice value according to its current value and visible
        for(Dice dice: game.getDices()){
            int imageIndex = dice.getValue() -1;
            imageButtons[index] = findViewById(diceButtonIDs[index]);
            imageButtons[index].setImageResource(diceImages[imageIndex]);
            imageButtons[index].setVisibility(View.VISIBLE);
            //updates the textview display related to each dice
            diceTexts[index] = findViewById(diceTextIDs[index]);
            if(dice.getClicked()){
                diceTexts[index].setText(R.string.saved_dice);
            }
            index++;
        }
    }


    /**
     * sets all imagebuttons related to dices image to not visible
     */
    private void removeAllImageButtonDrawables(){
        for(ImageButton imageButton: imageButtons) {
            //imageButton.setImageResource(0);
            imageButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * updates the dices according to their state and value
     */
      private void refreshImageButtons() {
          int index = 0;
          for(Dice dice: game.getDices()){
              if(dice.getClicked()){
                  imageButtons[index].setBackgroundColor(getResources().getColor(android.R.color.background_dark));
              }
              index++;
          }
      }

    /**
     * @param index position of dice layout to be toggled when saved/unsaved
     */
    private void toggleImageButton(int index) {
        Dice dice = game.getDices().get(index);
        //if a dice is already clicked or max amount of throws have been made, default layout is set and dice.clicked sets to false
        if(dice.getClicked() || game.checkMaxThrows()){
            imageButtons[index].setBackgroundColor(getResources().getColor(android.R.color.white));
            diceTexts[index].setText(R.string.empty_string);
            dice.setClicked(false);
        //if a dice is not clicked the background sets to black, text sets to saved and dice.clicked sets to true
        }else {
            imageButtons[index].setBackgroundColor(getResources().getColor(android.R.color.background_dark));
            diceTexts[index].setVisibility(View.VISIBLE);
            diceTexts[index].setText(R.string.saved_dice);
            dice.setClicked(true);
        }
    }

    /**
     * sets the text display for each dice to empty string
     */
    private void removeAllDicesText() {
        for(TextView textView: diceTexts) {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * sets all dices clicked value to false, no dices get saved
     */
    private void removeSelectedDices () {
        for (Dice dice: game.getDices()){
            dice.setClicked(false);
        }

    }

    /**
     * sets the dice background to default when no dices should be saved
     */
    private void removeImageButtonBackground() {
        for(ImageButton imageButton: imageButtons){
            imageButton.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    /**
     * updates the text displays for current throw, round and score
     */
    private void updateCountersDisplay() {
        mScoreCounter.setText("Score: " + Integer.toString(game.getmScoreCounter()));
        mRoundCounter.setText("Round: " + Integer.toString(game.getmRoundCounter()));
        mThrowCounter.setText("Throw: " + Integer.toString(game.getmThrowCounter()));
    }


}
