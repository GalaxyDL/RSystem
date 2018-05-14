package bean;

import java.util.List;

public final class ECG {
    private List<Integer> signal;
    private int offset;

    public ECG(List<Integer> signal, int offset) {
        this.signal = signal;
        this.offset = offset;
    }

    public List<Integer> getSignal() {
        return signal;
    }

    public void setSignal(List<Integer> signal) {
        this.signal = signal;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
