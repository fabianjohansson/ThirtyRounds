package se.umu.fajo0035.thirtyrounds;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


import java.util.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mThrowButton;
    private Spinner mSelectionSpinner;
    private TextView mThrowCounter;
    private TextView mScoreCounter;
    private TextView mRoundCounter;
    private Game game;
    private int[] diceImages = {
            R.drawable.white1,
            R.drawable.white2,
            R.drawable.white3,
            R.drawable.white4,
            R.drawable.white5,
            R.drawable.white6,
    };
    private static final int[] diceButtonIDs = {
            R.id.dice_one,
            R.id.dice_two,
            R.id.dice_three,
            R.id.dice_four,
            R.id.dice_five,
            R.id.dice_six
    };
    private ImageButton[] imageButtons = new ImageButton[diceButtonIDs.length];
    private ArrayList<String> mSelectableValues;
    private ArrayAdapter<String> mAdapter;
    static final String STATE_SCORE = "currentScore";
    static final String STATE_ROUND = "currentRound";
    static final String STATE_THROW = "currentThrow";
    static final String END_RESULT = "finalScore";
    static final String DICES_ARRAY = "dicesArrayList";

    private int[] diceTextIDs = {
            R.id.dice1_text_display,
            R.id.dice2_text_display,
            R.id.dice3_text_display,
            R.id.dice4_text_display,
            R.id.dice5_text_display,
            R.id.dice6_text_display,
    };

    private TextView[] diceTexts = new TextView[diceTextIDs.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        game = new Game();
        mThrowCounter = (TextView)findViewById(R.id.throw_display);
        mScoreCounter = (TextView)findViewById(R.id.score_display);
        mRoundCounter = (TextView)findViewById(R.id.round_display);

        updateCountersDisplay();

        mSelectionSpinner = findViewById(R.id.selection_spinner);

        mSelectableValues = new ArrayList<>();
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

        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, mSelectableValues);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectionSpinner.setAdapter(mAdapter);
        mSelectionSpinner.setOnItemSelectedListener(this);
        mSelectionSpinner.setEnabled(false);


        mThrowButton = findViewById(R.id.throw_button);
        mThrowButton.setText(R.string.throw_button);
        mThrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For throwing randomized dices
                throwButtonClicked();

            }
        });


        for(int id = 0; id < diceTextIDs.length; id++){
            diceTexts[id] = (TextView)findViewById(diceTextIDs[id]);
        }

            for(int i = 0; i < diceButtonIDs.length; i++){
                final int button = i;
                imageButtons[button] = (ImageButton)findViewById(diceButtonIDs[button]);
                imageButtons[button].setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        //for choosing to save or removing dice and to toggle color
                        toggleImageButton(button);
                    }
                });

            }

    }
    public void throwButtonClicked() {
        game.increasemThrowcounter();
        if(game.checkMaxRounds()){
            goToResultView();
        }else if(game.checkNewRound()){
            mSelectionSpinner.setEnabled(true);
            mThrowButton.setClickable(false);
            removeAllDicesText();
            removeSelectedDices();
        }
        generateDices();
        updateDiceImages();
        game.increasemRoundCounter();
        updateCountersDisplay();
        System.out.println("diceObjectArray length: " + game.getDiceObjectsArray().size());
    }

    public void goToResultView() {
        Intent resultIntent = new Intent(MainActivity.this,ResultActivity.class);
        resultIntent.putExtra(END_RESULT,game.getmScoreCounter());
        startActivity(resultIntent);
        MainActivity.this.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_SCORE, game.getmScoreCounter());
        savedInstanceState.putInt(STATE_ROUND, game.getmRoundCounter());
        savedInstanceState.putInt(STATE_THROW, game.getmThrowCounter());
        savedInstanceState.putParcelableArrayList(DICES_ARRAY,game.getDiceObjectsArray());

        super.onSaveInstanceState(savedInstanceState);
    }
    public void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        game.setmScoreCounter(savedInstanceState.getInt(STATE_SCORE));
        game.setmRoundCounter(savedInstanceState.getInt(STATE_ROUND));
        game.setmThrowCounter(savedInstanceState.getInt(STATE_THROW));

        game.setDiceObjectsArray(savedInstanceState.<Dice>getParcelableArrayList(DICES_ARRAY));
        updateCountersDisplay();
        updateDiceImages();
        refreshImageButtons();
    }

    private void generateDices() {
        //for generating random dice values when rolled and saving clicked dices
        if(game.getDiceObjectsArray().size() == 0){
            for (int i = 0;i < 6; i++) {
                game.generateNewDice(i);
            }
        }else {
            for(Dice dice: game.getDiceObjectsArray()){
                if(!dice.getClicked()){
                    dice.setValue();
                }
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(game.getmRoundCounter() == 10){
            mThrowButton.setText(R.string.game_over);
        }

        if (position != 0) {
            game.calculateScore(position + 2);
                game.increasemScoreCounter();
                mSelectableValues.set(position, " ");
                mThrowButton.setClickable(true);
                mSelectionSpinner.setEnabled(false);
                updateCountersDisplay();
                removeSelectedDices();
                removeAllDicesText();
                removeAllImageButtonDrawables();
                Toast correctSelectionToast = Toast.makeText(getApplicationContext(),
                        "You gained " + Integer.toString(game.getSum()) + " points!", Toast.LENGTH_SHORT);
                correctSelectionToast.show();


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void updateDiceImages() {
        int index = 0;
        for(Dice dice: game.getDiceObjectsArray()){
            int imageIndex = dice.getValue() -1;
            imageButtons[index] = findViewById(diceButtonIDs[index]);
            imageButtons[index].setImageResource(diceImages[imageIndex]);

            diceTexts[index] = findViewById(diceTextIDs[index]);
            if(dice.getClicked()){
                diceTexts[index].setText("saved");
            }
            index++;
        }
    }


    private void removeAllImageButtonDrawables(){
        for(ImageButton imageButton: imageButtons) {
            imageButton.setImageResource(0);
        }
    }

      private void refreshImageButtons() {
          int index = 0;
          for(Dice dice: game.getDiceObjectsArray()){
              if(dice.getClicked()){
                  imageButtons[index].setBackgroundColor(getResources().getColor(android.R.color.background_dark));
              }
              index++;
          }
      }

    private void toggleImageButton(int index) {
        Dice dice = game.getDiceObjectsArray().get(index);
        if(dice.getClicked() || game.checkNewRound()){
            imageButtons[index].setBackgroundColor(getResources().getColor(android.R.color.white));
            diceTexts[index].setText("");
            dice.setClicked(false);
        }else {
            imageButtons[index].setBackgroundColor(getResources().getColor(android.R.color.background_dark));
            diceTexts[index].setText("saved");
            dice.setClicked(true);
        }
    }

    private void removeAllDicesText() {
        for(TextView textView: diceTexts) {
            textView.setText("");
        }
    }

    private void removeSelectedDices () {
        //for removing selected dices
        for (Dice dice: game.getDiceObjectsArray()){
            dice.setClicked(false);
        }
        for(ImageButton imageButton: imageButtons){
            imageButton.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    private void updateCountersDisplay() {
        mScoreCounter.setText("Score: " + Integer.toString(game.getmScoreCounter()));
        mRoundCounter.setText("Round: " + Integer.toString(game.getmRoundCounter()));
        mThrowCounter.setText("Throw: " + Integer.toString(game.getmThrowCounter()));
    }


}
