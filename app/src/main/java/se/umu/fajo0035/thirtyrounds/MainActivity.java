package se.umu.fajo0035.thirtyrounds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mThrowButton;
    private Spinner mSelectionSpinner;
    private ImageButton mImageButton1;
    private ImageButton mImageButton2;
    private ImageButton mImageButton3;
    private ImageButton mImageButton4;
    private ImageButton mImageButton5;
    private ImageButton mImageButton6;
    private TextView mThrowCounter;
    private TextView mScoreCounter;
    private TextView mRoundCounter;
    private Game game;
    private Random random;
    private int[] diceImages = {
            R.drawable.white1,
            R.drawable.white2,
            R.drawable.white3,
            R.drawable.white4,
            R.drawable.white5,
            R.drawable.white6
    };
    private HashMap<Integer, Boolean> mClickedImageButtons = new HashMap<Integer, Boolean> (){
        {
            put(1, false);
            put(2, false);
            put(3, false);
            put(4, false);
            put(5, false);
            put(6, false);
        }
    };
    private ArrayList<String> mSelectableValues;
    private ArrayAdapter<String> mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        mThrowCounter = (TextView)findViewById(R.id.throw_display);
        mThrowCounter.setText("Throw: ");

        mScoreCounter = (TextView)findViewById(R.id.score_display);
        mScoreCounter.setText("Score: ");

        mRoundCounter = (TextView)findViewById(R.id.round_display);
        mRoundCounter.setText("Round: ");



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
        mThrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For throwing randomized dices
                game.increasemThrowcounter();
                mThrowCounter.setText("Throw: " + Integer.toString(game.getmThrowCounter()));
                game.increasemRoundCounter();
                mRoundCounter.setText("Round: " + Integer.toString(game.getmRoundCounter()));
                generateDices();
                updateDiceImages();
                mSelectionSpinner.setSelection(0);
                if(game.checkNewRound()){
                    mSelectionSpinner.setEnabled(true);
                    mThrowButton.setClickable(false);
                }
            }
        });

        mImageButton1 = (ImageButton)findViewById(R.id.dice_one);
        mImageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for choosing to save or removing dice and to toggle color
                toggleImageButton(1, mImageButton1);
            }
        });

        mImageButton2 = (ImageButton)findViewById(R.id.dice_two);
        mImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for choosing to save dice
                toggleImageButton(2, mImageButton2);
            }
        });
        mImageButton3 = (ImageButton)findViewById(R.id.dice_three);
        mImageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for choosing to save dice
                toggleImageButton(3, mImageButton3);
            }
        });

        mImageButton4 = (ImageButton)findViewById(R.id.dice_four);
        mImageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for choosing to save dice
                toggleImageButton(4,mImageButton4);
            }
        });
        mImageButton5 = (ImageButton)findViewById(R.id.dice_five);
        mImageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for choosing to save dice
                toggleImageButton(5, mImageButton5);
            }
        });
        mImageButton6 = (ImageButton)findViewById(R.id.dice_six);
        mImageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for choosing to save dice
                toggleImageButton(6, mImageButton6);
            }
        });



    }
    private void generateDices() {
        //for generating random dice values when rolled and saving clicked dices
        for (int i = 1;i < game.getDices().length; i++){
            int randomDice = new Random().nextInt(6) + 1;
            if(!mClickedImageButtons.get(i)){
                game.setDice(i,randomDice);
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            //Iterate over clicked Images to calculate score
            for (int i = 1; i < game.getDices().length; i++) {
                if (mClickedImageButtons.get(i)) {
                    game.setSelectedDice(i, game.getDice(i));
                    System.out.println("selected dice " + game.getSelectedDice(i));
                }
            }
            if(game.verifySelectedDices(position + 2)){
                //game.increasemScoreCounter();
                game.increasemScoreCounter();
                mSelectableValues.set(position, " ");
                mThrowButton.setClickable(true);
                mSelectionSpinner.setEnabled(false);
                mScoreCounter.setText("Score " + game.getmScoreCounter());
                removeSelectedDices();
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Invalid selection, try again",
                        Toast.LENGTH_SHORT);

                toast.show();
                mSelectionSpinner.setSelection(0);
                removeSelectedDices();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void updateDiceImages() {
        ImageButton mImageButton1 = findViewById(R.id.dice_one);
        setImageButtonDrawable(mImageButton1,1);
        ImageButton mImageButton2 = findViewById(R.id.dice_two);
        setImageButtonDrawable(mImageButton2,2);
        ImageButton mImageButton3 = findViewById(R.id.dice_three);
        setImageButtonDrawable(mImageButton3,3);
        ImageButton mImageButton4 = findViewById(R.id.dice_four);
        setImageButtonDrawable(mImageButton4,4);
        ImageButton mImageButton5 = findViewById(R.id.dice_five);
        setImageButtonDrawable(mImageButton5,5);
        ImageButton mImageButton6 = findViewById(R.id.dice_six);
        setImageButtonDrawable(mImageButton6,6);
    }
    private void setImageButtonDrawable(ImageButton imageButton,int index) {
        //uses -1 because of avoiding arrayOutOfBounds
        int diceValue = game.getDice(index);
        imageButton.setImageResource(diceImages[diceValue - 1]);
    }
    private void setImageButtonColor(ImageButton imageButton) {
        imageButton.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
    }
    private void removeImageButtonColor(ImageButton imageButton) {
        imageButton.setBackgroundColor(getResources().getColor(android.R.color.white));
    }
    private void toggleImageButton(int i, ImageButton imageButton) {
        if(mClickedImageButtons.get(i)) {
            removeImageButtonColor(imageButton);
            mClickedImageButtons.put(i,false);
        }else if(!mClickedImageButtons.get(i)){
            setImageButtonColor(imageButton);
            mClickedImageButtons.put(i, true);
        }
    }

    private void removeSelectedDices () {
        //for removing selected dices
        for (Map.Entry<Integer, Boolean> entry : mClickedImageButtons.entrySet()) {
            entry.setValue(false);
        }
        removeImageButtonColor(mImageButton1);
        removeImageButtonColor(mImageButton2);
        removeImageButtonColor(mImageButton3);
        removeImageButtonColor(mImageButton4);
        removeImageButtonColor(mImageButton5);
        removeImageButtonColor(mImageButton6);

        System.out.println("selected dices length: " + game.getSelectedDices().length);
        int[] emptyArray = {0,0,0,0,0,0,0};
        System.out.println("empty array length: " + emptyArray.length);
        game.setSelectedDices(emptyArray);
    }


}
