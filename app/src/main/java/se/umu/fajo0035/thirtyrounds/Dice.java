package se.umu.fajo0035.thirtyrounds;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;
import java.util.Random;

public class Dice implements Parcelable{
    private int id;
    private int value;
    private boolean clicked;

    public Dice(int diceID/*, boolean clickedState, int currentValue*/){
        id = diceID;
        value = throwDice();
        clicked = false;
        /*clicked = clickedState;
        value = currentValue;*/
    }


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
    public void removeValue() {
        this.value = 0;
    }

    @Override
    public String toString() {
        return "id: " + id + " value: " + value + " clicked: " + clicked;
    }
    public static Comparator<Dice> DiceValue = new Comparator<Dice>() {
        @Override
        public int compare(Dice o1, Dice o2) {
            int value1 = o1.getValue();
            int value2 = o2.getValue();
            return value2 - value1;
        }
    };

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
