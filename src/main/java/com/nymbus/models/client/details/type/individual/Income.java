package com.nymbus.models.client.details.type.individual;

public enum Income {
    FROM_0_TO_10K("0 - 10,000"),
    FROM_10K_TO_20K("10,000 - 20,000"),
    FROM_20K_TO_30K("20,000 - 30,000"),
    FROM_30K_TO_40K("30,000 - 40,000"),
    FROM_40K_TO_50K("40,000 - 50,000"),
    FROM_50K_TO_60K("50,000 - 60,000"),
    FROM_60K_TO_70K("60,000 - 70,000"),
    FROM_70K_TO_80K("70,000 - 80,000"),
    FROM_80K_TO_90K("80,000 - 90,000"),
    FROM_90K_TO_100K("90,000 - 100,000"),
    FROM_100K_TO_125K("100,000 - 125,000"),
    FROM_125K_TO_150K("125,000 - 150,000"),
    FROM_150K_TO_175K("150,000 - 175,000"),
    MORE_THAN_175K("> 175,000");

    private final String income;

    Income(String income) {
        this.income = income;
    }

    public String getIncome() {
        return income;
    }
}
