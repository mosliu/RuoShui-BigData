package com.ruoshui.market.enums;

public enum ResType {

    JSON("JSON"),
    XML("XML");

    private String desc;

    ResType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
