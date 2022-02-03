package com.maxtech.lib.logging;

public interface LoggableInputs {
    /** Turn inputs into a Table. */
    void serialize(Table t);

    /** Turn Table into inputs. */
    void deserialize(Table t);
}
