package com.englishaoe.lesson.database.entity.partnership;

public enum PartnerTypeEnum {
    COMPANY("company"),
    INDIVIDUAL("individual");

    private final String type;

    PartnerTypeEnum(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
