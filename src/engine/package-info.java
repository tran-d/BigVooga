package engine;


/**
This package contains the major classes which are used in the engine. 
Sub-packages like Actions and Conditions contain utilities which are used for specific purposes. 

The engine framework depends on the Controller->World->Layer->Object hierarchy. Objects populate layers, which populate worlds,
which populate the controller. Objects contain a mapping of conditions and actions which govern their behavior in a way that models a
stimulus-response scenario (or just if-then conditionals).
*/