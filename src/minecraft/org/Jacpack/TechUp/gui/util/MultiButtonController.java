package org.Jacpack.TechUp.gui.util;

public class MultiButtonController
{
    private int currentState;
    private final IMultiButtonState[] validStates;

    private MultiButtonController(int var1, IMultiButtonState ... var2)
    {
        this.currentState = var1;
        this.validStates = var2;
    }

    public static MultiButtonController getController(int var0, IMultiButtonState ... var1)
    {
        return new MultiButtonController(var0, var1);
    }

    public MultiButtonController copy()
    {
        return new MultiButtonController(this.currentState, (IMultiButtonState[])this.validStates.clone());
    }

    public IMultiButtonState[] getValidStates()
    {
        return this.validStates;
    }

    public int incrementState()
    {
        int var1 = this.currentState + 1;

        if (var1 >= this.validStates.length)
        {
            var1 = 0;
        }

        this.currentState = var1;
        return this.currentState;
    }

    public void setCurrentState(int var1)
    {
        this.currentState = var1;
    }

    public int getCurrentState()
    {
        return this.currentState;
    }

    public IMultiButtonState getButtonState()
    {
        return this.validStates[this.currentState];
    }
}
