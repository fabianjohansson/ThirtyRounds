package se.umu.fajo0035.thirtyrounds;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private int mScoreCounter = 0;
    private int mThrowCounter = 0;
    private int mRoundCounter = 0;
    private int[] dices = new int[7];
    private int sum = 0;

    public void setSelectedDices(int[] selectedDices) {
        this.selectedDices = selectedDices;
    }

    private int[] selectedDices = new  int[7];


    public int[] getSelectedDices() {
        return selectedDices;
    }

    public void setSelectedDice(int position, int value) {
        this.selectedDices[position] = value;
    }
    public int getSelectedDice(int index) {
        return selectedDices[index];
    }



    public int getmRoundCounter() {
        return mRoundCounter;
    }

    public void setmRoundCounter(int mRoundCounter) {
        this.mRoundCounter = mRoundCounter;
    }
    public int increasemRoundCounter () {
        if(checkNewRound()) {
            return mRoundCounter ++;
        }else{
            return mRoundCounter;
        }
    }
    public boolean checkNewRound () {
        if(mThrowCounter == 3 || mThrowCounter == 6 || mThrowCounter == 9 || mThrowCounter == 12 || mThrowCounter == 15
                || mThrowCounter == 18 || mThrowCounter == 21 || mThrowCounter == 24 || mThrowCounter == 27 || mThrowCounter == 30){
            return true;
        }else {
            return false;
        }
    }
    public int[] getDices() {
        return dices;
    }
    public int getDice(int index) {
        return dices[index];
    }

    public void setDice(int position , int value) {
        this.dices[position] = value;
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
        return mThrowCounter ++;
    }
    public void increasemScoreCounter() {
        mScoreCounter += sum;
        sum = 0;
    }

    public boolean verifySelectedDices(int i) {
        //int sum = 0;
        for(int dice: selectedDices) {
            sum += dice;
        }
        if(sum == i || sum == i * 2 || sum == i * 3 || sum == i * 4 || sum == i * 5 || sum == i * 6){
            return true;
        }else{
            return false;
        }
    }
}

