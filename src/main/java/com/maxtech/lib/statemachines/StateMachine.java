package com.maxtech.lib.statemachines;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.subsystems.Flywheel;

import java.util.HashMap;
import java.util.function.Consumer;

public class StateMachine<T> {
    private T internalState;
    private HashMap<T, Consumer> handlers = new HashMap();

    RobotLogger logger = RobotLogger.getInstance();

    public StateMachine(T initialState) {
        internalState = initialState;
    }

    public void toState(T state) {
        internalState = state;
        logger.log("Entered state %s.", state);

        // Run the associated handler, if there is one. If not, run the default consumer of nothing.
        handlers.getOrDefault(state, x -> {});
    }

    /** Associate a state with a Consumer action. */
    public void associateState(T state, Consumer action) {
        handlers.put(state, action);
    }

    public T currentState() {
        return internalState;
    }
}
