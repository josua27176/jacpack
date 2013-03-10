package org.Jacpack.TechUp.gui;

public enum EnumGui
{
	BLAST_FURNACE(true);
    private final byte id;
    private final boolean hasContainer;
    private static byte nextId = 0;

    private EnumGui(boolean hasContainer)
    {
      this.id = getNextId();
      this.hasContainer = hasContainer;
    }

    public byte getId()
    {
      return this.id;
    }

    private static byte getNextId()
    {
      return nextId++;
    }

    public boolean hasContainer()
    {
      return this.hasContainer;
    }

    public static EnumGui fromId(int i)
    {
      for (EnumGui e : values()) {
        if (e.getId() == i) {
          return e;
        }
      }
      return BLAST_FURNACE;
    }
}
