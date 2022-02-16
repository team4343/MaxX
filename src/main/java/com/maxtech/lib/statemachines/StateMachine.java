package com.maxtech.lib.statemachines;

import com.maxtech.lib.logging.RobotLogger;

import java.util.HashMap;
import java.util.function.Consumer;

public class StateMachine<T> {
    private T internalState;
    private HashMap<T, Consumer<StateMachineMeta>> handlers = new HashMap();

    RobotLogger logger = RobotLogger.getInstance();

    public StateMachine(T initialState) {
        internalState = initialState;
    }

    public void toState(T state) {
        if (state != internalState) {
            internalState = state;
            logger.log("Entered state %s.", state);

            // Run the associated handler, if there is one. If not, run the default consumer of nothing.
            handlers.getOrDefault(state, x -> {}).accept(new StateMachineMeta());
        }
    }

    /** Associate a state with a Consumer action. */
    public void associateState(T state, Consumer<StateMachineMeta> action) {
        handlers.put(state, action);
        logger.dbg("Associated %s with %s", state, action);
    }

    public T currentState() {
        return internalState;
    }
}