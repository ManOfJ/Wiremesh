package com.manofj.minecraft.moj_wiremesh.block;

import net.minecraft.util.IStringSerializable;


public enum WiremeshSlabVariant
        implements IStringSerializable
{
    DEFAULT;

    @Override
    public String getName() {
        return "default";
    }
}
