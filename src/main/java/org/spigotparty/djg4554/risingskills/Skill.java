package org.spigotparty.djg4554.risingskills;

public enum Skill {
    MINING("mining_exp"), FISHING("fishing_exp"), FARMING("farming_exp"), CHOPPING("chopping_exp"), DIGGING("digging_exp");

    private String dataLabel;

    private Skill(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getDataLabel() {
        return dataLabel;
    }
}
