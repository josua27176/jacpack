package org.Jacpack.TechUp.modules;

public abstract class JACModule
{
    public void preInit() {}

    public void initFirst() {}

    public void initSecond() {}

    public void postInit() {}

    public void postInitNotLoaded() {}

    public boolean canModuleLoad()
    {
        return true;
    }

    public void printLoadError() {}

    public String toString()
    {
        return this.getClass().getSimpleName();
    }
}
