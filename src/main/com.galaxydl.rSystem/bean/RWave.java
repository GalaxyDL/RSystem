package bean;

import java.util.Map;

public final class RWave {
    private Map<Integer, Integer> positions;

    public RWave(Map<Integer, Integer> positions) {
        this.positions = positions;
    }

    public Map<Integer, Integer> getPostions() {
        return positions;
    }

    public void setPostions(Map<Integer, Integer> postions) {
        this.positions = postions;
    }
}
