package com.ruoshui.metadata.enums;

public enum SyncStatus {

    NotSync("0"),
    InSync("1"),
    IsSync("2");

    private final String key;

    SyncStatus(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
