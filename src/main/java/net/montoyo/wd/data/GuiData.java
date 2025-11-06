package net.montoyo.wd.data;

import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class GuiData {
    
    protected static class GuiType {
        Class<? extends GuiData> clazz;
        Supplier<GuiData> constructor;

        public GuiType(Class<? extends GuiData> clazz, Supplier<GuiData> constructor) {
            this.clazz = clazz;
            this.constructor = constructor;
        }

        public GuiData create() {
            return constructor.get();
        }
    }

    private static final HashMap<String, GuiType> dataTable = new HashMap<>();

    public static GuiData read(String name, FriendlyByteBuf buf) {
        GuiType type = dataTable.get(name);
        if (type == null) {
            throw new IllegalArgumentException("Unknown GUI data type: " + name);
        }
        GuiData data = type.create();
        data.deserialize(buf);
        return data;
    }

    public static Class<? extends GuiData> classOf(String name) {
        GuiType type = dataTable.get(name);
        return type != null ? type.clazz : null;
    }

    public GuiData() {
    }

    public abstract String getName();

    public abstract void serialize(FriendlyByteBuf buf);

    public abstract void deserialize(FriendlyByteBuf buf);
}