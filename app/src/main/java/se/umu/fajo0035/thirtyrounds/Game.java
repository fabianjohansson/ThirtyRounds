package se.umu.fajo0035.thirtyrounds;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    private int mScoreCounter = 0;
    private int mThrowCounter = 0;
    private int mRoundCounter = 0;
    private ArrayList<Dice> diceObjectsArray = new ArrayList<>();
    private HashMap<Integer,Integer> correctDices = new HashMap<>();
    private int sum;

    public ArrayList<Dice> getDiceObjectsArray() {
        return diceObjectsArray;
    }

    public void setDiceObjectsArray(ArrayList<Dice> diceObjectsArray) {
        this.diceObjectsArray = diceObjectsArray;
    }

    public void generateNewDice(int i) {
        diceObjectsArray.add(new Dice(i));
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

    public int increasemRoundCounter() {
        if (checkNewRound()) {
            return mRoundCounter++;
        } else {
            return mRoundCounter;
        }
    }

    public boolean checkMaxRounds() {
        return mRoundCounter == 10;
    }

    /*public boolean checkMaxThrows() {
        return mThrowCounter < 3;
    }*/
    public boolean checkNewRound() {
        return mThrowCounter == 3;
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

    public int increasemThrowcounter() {
        if (mThrowCounter == 3) {
            return mThrowCounter = 0;
        } else {
            return mThrowCounter++;
        }
    }

    public void increasemScoreCounter() {
        mScoreCounter += sum;
        //sum = 0;
    }

    public int calculateScore(int dropDownIndex) {
        setSum(0);
        if (dropDownIndex == 3) {
            return calculateLowSelected();
        } else {
            return calculateNotLowSelected(dropDownIndex);
        }

    }

    public int calculateLowSelected() {
        setSum(0);
        for (Dice dice : diceObjectsArray) {
            if (dice.getValue() < 4) {
                sum += dice.getValue();
            }
        }
        return sum;
    }


    public int calculateNotLowSelected(int valueOfSelection) {
        ArrayList<Dice> combinations = new ArrayList<>();
        int size = diceObjectsArray.size();
        //Collections.sort(diceObjectsArray, Dice.DiceValue);
        System.out.println("diceObjectArray before: " + diceObjectsArray.toString());
        /*for(int k = 0; k < diceObjectsArray.size(); k++){
            if(getDiceValue(k) == valueOfSelection) {
                int value = getDiceValue(k);
                int id = getDiceID(k);
                correctDices.put(id,value);
                diceObjectsArray.get(k).removeValue();
            }
        }*/
        //System.out.println("diceObjectArray after: " + diceObjectsArray.toString());
        //boolean[] exists = new boolean[size];

        notLowHelper(combinations,diceObjectsArray,valueOfSelection);

        System.out.println("diceObjectArray after: " + diceObjectsArray.toString());
        /*for (Dice dice : correctDices) {
            System.out.println("dice value: " + dice.getValue());
            sum += dice.getValue();
        }*/
        for (Integer value : correctDices.values()) {
            System.out.println("value: " + value);
            sum += value;
        }
        System.out.println("sum after: " + sum);
        System.out.println("correctDices after: " + correctDices.toString());
        correctDices.clear();
        return sum;
    }

    private void notLowHelper( ArrayList<Dice> combinations, ArrayList<Dice> diceObjectsArray, int desiredSum){
        if(diceObjectsArray.isEmpty()){
            if(checkContains(combinations) && getTotal(combinations) == desiredSum){
                for(Dice d: combinations){
                    correctDices.put(d.getID(),d.getValue());
                    d.removeValue();
                }
            }

        }else{
            Dice d = diceObjectsArray.get(0);
            diceObjectsArray.remove(0);
            System.out.println("diceObjectsArray first: " + diceObjectsArray.toString());

            notLowHelper(combinations,diceObjectsArray,desiredSum);

            combinations.add(d);
            System.out.println("combinaitons first: " + combinations.toString());
            notLowHelper(combinations,diceObjectsArray,desiredSum);

            diceObjectsArray.add(0,d);
            System.out.println("diceObejctsArray second: " + diceObjectsArray.toString());
            combinations.remove(combinations.size() -1);
            System.out.println("combinaitons second: " + combinations.toString());
        }
    }
    private boolean checkContains(ArrayList<Dice> combinations) {
        int[] exists = new int[combinations.size()];
        for(int i = 0; i < combinations.size(); i++){
            if(correctDices.containsKey(combinations.get(i).getID())){
                exists[i] = 1;
            }else{
                exists[i] = 0;
            }
        }
        int total = 0;
        for(int j = 0; j < exists.length; j++){
            total += exists[j];
        }
        if(total == 0){
            return true;
        }
        else {
            return false;
        }
    }

    private int getTotal(ArrayList<Dice> list) {
        int total = 0;
        for (Dice d : list) {
            total += d.getValue();
        }
        return total;
    }

    /*private void addCorrectDice(ArrayList<Dice> list) {
        for (Dice dice : list) {
            if (!correctDices.contains(dice)) {
                correctDices.add(dice);
                diceObjectsArray.remove(dice);
            }

        }
    }*/


        private int getDiceValue ( int index){
            return diceObjectsArray.get(index).getValue();
        }
        private int getDiceID (int index) {
        return diceObjectsArray.get(index).getID();
        }
        private boolean checkContainsDice (int id){
            return correctDices.containsKey(id);
        }


    }




