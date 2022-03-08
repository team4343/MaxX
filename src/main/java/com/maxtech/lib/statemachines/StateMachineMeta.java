package com.maxtech.lib.statemachines;

public class StateMachineMeta {
    private boolean firstRun;

    public StateMachineMeta(boolean firstRun) {
        this.firstRun = firstRun;
    }

    public boolean isFirstRun() {
        return firstRun;
    }
}
