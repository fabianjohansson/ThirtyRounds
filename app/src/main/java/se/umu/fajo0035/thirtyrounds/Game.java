package se.umu.fajo0035.thirtyrounds;


import java.util.ArrayList;
import java.util.HashMap;


/**
 * Model with all the values of a game and for calculating score
 */
public class Game {

    /* current score */
    private int mScoreCounter = 0;
    /* current throw */
    private int mThrowCounter = 0;
    /* current round */
    private int mRoundCounter = 0;
    /* all current dices in the game */
    private ArrayList<Dice> dices = new ArrayList<>();
    /* verified dices equal to the desired score calculation, key= dice.id, value= dice.value */
    private HashMap<Integer,Integer> correctDices = new HashMap<>();
    /* score for each selection, key= name of selection, value = score of the selection */
    private HashMap<String,Integer> scoreEachRound = new HashMap<>();
    /* current sum of all correct dices */
    private int sum;

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
    }

    /** adds a single dice in the collection of all dices
     * @param i ID for creating new instance of dice
     */
    public void generateNewDice(int i) {
        dices.add(new Dice(i));
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


    public int getmRoundCounter() {
        return mRoundCounter;
    }

    public void setmRoundCounter(int mRoundCounter) {
        this.mRoundCounter = mRoundCounter;
    }

    /**
     * @return If there is a new round we increase the the round counter
     * else we return the current value
     */
    public int increasemRoundCounter() {
        if (checkNewRound()) {
            return mRoundCounter++;
        } else {
           return mRoundCounter;
        }
    }

    /**
     * @return If the current round is larger then 10 the maximum amount of rounds have been exceeded
     */
    public boolean checkMaxRounds() {
        return mRoundCounter > 10;
    }

    /**
     * @return returns true when the max amount of throws, 3, have been reached else returns false
     */
    public boolean checkMaxThrows() {
        return mThrowCounter == 3;
    }

    /**
     * @return If the throw counter is one a new round has started and true is returned
     * else false is returned
     */
    public boolean checkNewRound() {
        return mThrowCounter == 1;
    }

    public int getmThrowCounter() {
        return mThrowCounter;
    }

    public void setmThrowCounter(int mThrowCounter) {
        this.mThrowCounter = mThrowCounter;
    }


    public int getmScoreCounter() {
        return mScoreCounter;
    }

    public void setmScoreCounter(int mScoreCounter) {
        this.mScoreCounter = mScoreCounter;
    }

    /** Keeps the throw counter between 1-3
     * @return if max amount of throws are made, 3, next throw will be 1 else we increment the current score by 1
     */
    public int increasemThrowcounter() {
        if (mThrowCounter == 3) {
            return mThrowCounter = 1;
        } else {
            return mThrowCounter++;
        }
    }

    /**
     * increases the total score from current sum
     */
    public void increasemScoreCounter() {
        mScoreCounter += sum;
    }

    public HashMap getScoreEachRound(){
        return scoreEachRound;
    }
    public void setScoreEachRound(HashMap<String,Integer> scoreEachRound) {
        this.scoreEachRound = scoreEachRound;
    }


    /** If the desired value for score calculation is 3 calculaLowSelected is run
     * else calculateNotLowSelected is run
     * @param selectedValue the user selected value to be calculated for score
     */
    public void calculateScore(int selectedValue, String selectedName) {
        setSum(0);
        if (selectedValue == 3) {
            calculateLowSelected(selectedName);
        } else {
             calculateNotLowSelected(selectedValue,selectedName);
        }

    }

    /**
     * @return sum of all the dices values that are less than or equal to 3
     */
    public int calculateLowSelected(String selectedName) {
        for (Dice dice : dices) {
            if (dice.getValue() < 4) {
                sum += dice.getValue();
            }
        }
        scoreEachRound.put(selectedName,sum);
        return sum;
    }


    /** To find subsets equal to the users selected value
     * @param valueOfSelection value the user have selected
     * @return the sum of all non duplicated dices values in subsets equal to the users selection
     */
    private int calculateNotLowSelected(int valueOfSelection, String nameOfSelection) {
        ArrayList<Dice> combinations = new ArrayList<>();
        notLowHelper(combinations, dices,valueOfSelection);
        /* Calculates the sum of all  verified subsets */
        for (Integer value : correctDices.values()) {
            sum += value;
        }
        /* Removes all elements from the verified dices before next round */
        correctDices.clear();
        scoreEachRound.put(nameOfSelection,sum);
        return sum;
    }

    /** Method that finds all combinations of dices values
     * and outputs all subsets that equals the desired sum
     * verifies that dices can be used only once
     * @param combinations for storing all combinations of dice values
     * @param diceObjects  contains all dice object
     * @param desiredSum  desired value to be found in subsets of all dices
     */
    private void notLowHelper( ArrayList<Dice> combinations, ArrayList<Dice> diceObjects, int desiredSum){
        //no more combinations can be made
        if(diceObjects.isEmpty()){
            /*if the subset combined sum equals the selected  value
            * and none of the dices have been added to the correct dices
            * we add the subset of dices to correct dices and sets the added dices value to zero*/
            if(getTotal(combinations) == desiredSum && dicesAreNotUsed(combinations)){
                for(Dice d: combinations){
                    correctDices.put(d.getID(),d.getValue());
                    d.removeValue();
                }
            }

        }else{
            //retrieves current first dice from collection
            Dice d = diceObjects.get(0);
            //removes current first dice in collection
            diceObjects.remove(0);
            //explores combinations without selected dice
            notLowHelper(combinations,diceObjects,desiredSum);
            //adds the selected dice to combinations
            combinations.add(d);
            //explores combinations with selected dice
            notLowHelper(combinations,diceObjects,desiredSum);
            //adds selected back to position in all dices
            diceObjects.add(0,d);
            //removes selected dice to be combined
            combinations.remove(combinations.size() -1);
        }
    }

    /**
     * @param combinations contains subsets to be checked
     * @return Returns false if a dice in the subset have been added to the correct dices.
     *      Return true if non of the dices in the subset have been added to the correct dices
     */
    private boolean dicesAreNotUsed(ArrayList<Dice> combinations) {
        for(int i = 0; i < combinations.size(); i++){
            if(correctDices.containsKey(combinations.get(i).getID())){
                return false;
            }
        }
        return true;
    }

    /**
     * @param list  contains dices to be calculated for sum of their values
     * @return the total amount of all dices values in the collection
     */
    private int getTotal(ArrayList<Dice> list) {
        int total = 0;
        for (Dice d : list) {
            total += d.getValue();
        }
        return total;
    }


    }




