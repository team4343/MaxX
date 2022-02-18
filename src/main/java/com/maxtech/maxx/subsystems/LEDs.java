package com.maxtech.maxx.subsystems;

import com.maxtech.lib.annotations.IncompleteIO;
import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;

@IncompleteIO
public class LEDs extends Subsystem {
    private static LEDs instance;

    public static LEDs getInstance() {
        if (instance == null) {
            instance = new LEDs();
        }

        return instance;
    }

    private LEDs() {
        statemachine.associateState(LEDTypes.Solid, this::handleSolid);
        statemachine.start();
    }

    private enum LEDTypes {
        Solid,
    }

    private StateMachine<LEDTypes> statemachine = new StateMachine<LEDTypes>("LEDs", LEDTypes.Solid);

    private void handleSolid(StateMachineMeta m) {
        RobotLogger.getInstance().dbg("Switched to solid state.");
    }

    @Override
    public void sendTelemetry(String prefix) {

    }
}
