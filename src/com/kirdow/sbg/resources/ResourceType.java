package com.kirdow.sbg.resources;

public enum ResourceType {

    RAW("Raw", null),
    TEXTURE("Texture2D", "png"),
    JSON("JSON", "json");

    private String name;
    private String extension;

    private ResourceType(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public int getId() {
        return this.ordinal();
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return extension;
    }

    public String toString() {
        return name;
    }

    public static ResourceType getTypeByName(String name) {
        for (ResourceType type : values()) {
            if (type.name.equals(name)) return type;
        }
        return RAW;
    }

    public static ResourceType getTypeById(int id) {
        try {
            return values()[id];
        } catch (Exception e) {
            return null;
        }
    }



}
