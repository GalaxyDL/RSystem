package bean;

import java.util.List;

public final class RWave {
    private List<Integer> positions;

    public RWave(List<Integer> positions) {
        this.positions = positions;
    }

    public List<Integer> getPostions() {
        return positions;
    }

    public void setPostions(List<Integer> postions) {
        this.positions = postions;
    }
}
