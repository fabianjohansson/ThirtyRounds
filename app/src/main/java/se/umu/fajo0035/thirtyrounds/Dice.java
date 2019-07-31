package se.umu.fajo0035.thirtyrounds;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;
import java.util.Random;

/**
 * Model for dices that implements Parcelable so that a dice can be saved
 * with onSaveInstanceState when the application is in the background
 * and retrieved with onRetrieveInstanceState when the application is back in the foreground
 */
public class Dice implements Parcelable{
    /**
     * @param id number for identifying the dice
     * @param value number of the current value of the dice
     * @param clicked boolean to indicate if the dice is to be saved
     */
    private int id;
    private int value;
    private boolean clicked;

    /** creates an instance of dice
     * @param diceID for identification
     * value is generated randomly between numbers 1-6
     * clicked is set default to false
     */
    public Dice(int diceID){
        id = diceID;
        value = throwDice();
        clicked = false;
    }


    /**
     * @return returns a random number between 1-6 for the value of the dice
     */
    private int throwDice(){
        return new Random().nextInt(6) + 1;
    }

    public boolean getClicked() {
        return clicked;
    }
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
    public int getID() {
        return id;
    }
    public int getValue() {
        return value;
    }
    public void setValue() {
        this.value = throwDice();
    }

    /**
     * sets the value of the dice to zero
     */
    public void removeValue() {
        this.value = 0;
    }

    @Override
    public String toString() {
        return "id: " + id + " value: " + value + " clicked: " + clicked;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    /* save object in parcel */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(value);
        out.writeValue(clicked);
    }
    /* creator method for parcelable */
    public static final Parcelable.Creator<Dice> CREATOR = new Parcelable.Creator<Dice>() {
        public Dice createFromParcel(Parcel in) {
            return new Dice(in);
        }
        public Dice[] newArray(int size){
            return new Dice[size];
        }
    };
    /** recreate object from parcel */
    private Dice(Parcel in) {
        id = in.readInt();
        value = in.readInt();
        clicked = (Boolean)in.readValue(null);
    }
}
