package practice.rpcfs.chp7;

public enum ClusterStrategyEnum {
    WeightRandom("WeightRandom"),
    Polling("Polling"),
    WeightPolling("WeightPolling"),
    Hash("Hash"),
    Random("Random");

    private String name;
    ClusterStrategyEnum(String random) {
        this.name = name;
    }

    public static ClusterStrategyEnum queryByCode(String clusterStrategy) {
        for (ClusterStrategyEnum clusterStrategyEnum : ClusterStrategyEnum.values()) {
            if (clusterStrategyEnum.name.equalsIgnoreCase(clusterStrategy)) {
                return clusterStrategyEnum;
            }
        }
        return null;
    }
}
