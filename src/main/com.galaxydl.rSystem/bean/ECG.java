package bean;

import java.util.ArrayList;

public final class ECG {
    private ArrayList<Integer> signal;
    private int offset;

    public ECG(ArrayList<Integer> signal, int offset) {
        this.signal = signal;
        this.offset = offset;
    }

    public ArrayList<Integer> getSignal() {
        return signal;
    }

    public void setSignal(ArrayList<Integer> signal) {
        this.signal = signal;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
