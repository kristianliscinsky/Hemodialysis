package com.example.myapplication.enums;

public enum LiquidStandards {
    MILILITER(1),
    CUP(200),
    CUP_SMALL(100),
    MUG_SMALL(200),
    MUG_LARGE(300),
    GLASS_SMALL(200),
    GLASS_LARGE(300),
    GLASS_WHISKEY(150),
    GLASS_WINE(250),
    SHOT_SMALL(40),
    SHOT_LARGE(50),
    BOWL_LARGE(400),
    BOWL_SMALL(200),
    PLATE(300),
    KRIGL(500),
    SIP(50);

    private final int liquidStandards;

    LiquidStandards(int liquidStandards) {
        this.liquidStandards = liquidStandards;
    }

    public int getLiquidStandards() {
        return this.liquidStandards;
    }
}
