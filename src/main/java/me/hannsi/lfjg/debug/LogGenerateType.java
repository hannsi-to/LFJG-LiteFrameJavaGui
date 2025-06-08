package me.hannsi.lfjg.debug;

public enum LogGenerateType {
    CREATE_CACHE(0, "CreateCache"),
    CLEANUP(1, "Cleanup"),
    LOAD(2, "Load");

    final int id;
    final String name;

    LogGenerateType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
