package org.Jacpack.TechUp.gui.util;

public class MultiButtonController
{
    private int currentState;
    private final IMultiButtonState[] validStates;

    private MultiButtonController(int startState, IMultiButtonState[] validStates)
    {
        this.currentState = startState;
        this.validStates = validStates;
    }

    public static MultiButtonController getController(int startState, IMultiButtonState[] validStates) {
        return new MultiButtonController(startState, validStates);
    }

    public MultiButtonController copy()
    {
        return new MultiButtonController(this.currentState, (IMultiButtonState[])this.validStates.clone());
    }

    public IMultiButtonState[] getValidStates()
    {
        return this.validStates;
    }

    public int incrementState() {
        int newState = this.currentState + 1;
        if (newState >= this.validStates.length) {
          newState = 0;
        }
        this.currentState = newState;
        return this.currentState;
    }

    public void setCurrentState(int state) {
        this.currentState = state;
      }

    public int getCurrentState() {
      return this.currentState;
    }

    public IMultiButtonState getButtonState() {
      return this.validStates[this.currentState];
    }
}
