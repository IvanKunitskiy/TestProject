package com.nymbus.models.client.details.type.individual;

public enum ConsumerInformationIndicator {
    A1_PERSONAL_RECEIVERSHIP("1A - Personal Receivership"),
    A2_LEASE_ASSUMPTION("2A - Lease Assumption"),
    A_PETITION_FOR_CHAPTER_7_BANKRUPTCY("A - Petition for Chapter 7 Bankruptcy"),
    B_PETITION_FOR_CHAPTER_11_BANKRUPTCY("B - Petition for Chapter 11 Bankruptcy"),
    C_PETITION_FOR_CHAPTER_12_BANKRUPTCY("C - Petition for Chapter 12 Bankruptcy"),
    D_PETITION_FOR_CHAPTER_13_BANKRUPTCY("D - Petition for Chapter 13 Bankruptcy"),
    E_DISCHARGED_THROUGH_BANKRUPTCY_CHAPTER_7("E - Discharged through Bankruptcy Chapter 7"),
    F_DISCHARGED_THROUGH_BANKRUPTCY_CHAPTER_11("F - Discharged through Bankruptcy Chapter 11"),
    G_DISCHARGED_THROUGH_BANKRUPTCY_CHAPTER_12("G - Discharged through Bankruptcy Chapter 12"),
    H_DISCHARGED_COMPLETED_THROUGH_BANKRUPTCY_CHAPTER_13("H - Discharged/Completed through Bankruptcy Chapter 13"),
    I_CHAPTER_7_BANKRUPTCY_DISMISSED("I - Chapter 7 Bankruptcy Dismissed"),
    J_CHAPTER_11_BANKRUPTCY_DISMISSED("J - Chapter 11 Bankruptcy Dismissed"),
    K_CHAPTER_12_BANKRUPTCY_DISMISSED("K - Chapter 12 Bankruptcy Dismissed"),
    L_CHAPTER_13_BANKRUPTCY_DISMISSED("L - Chapter 13 Bankruptcy Dismissed"),
    M_CHAPTER_7_BANKRUPTCY_WITHDRAWN("M - Chapter 7 Bankruptcy Withdrawn"),
    N_CHAPTER_11_BANKRUPTCY_WITHDRAWN("N - Chapter 11 Bankruptcy Withdrawn"),
    O_CHAPTER_12_BANKRUPTCY_WITHDRAWN("O - Chapter 12 Bankruptcy Withdrawn"),
    P_CHAPTER_13_BANKRUPTCY_WITHDRAWN("P - Chapter 13 Bankruptcy Withdrawn"),
    Q_REMOVES_BANKRUPTCY_OR_PERSONAL_RECEIVERSHIP_INDICATOR("Q - Removes Bankruptcy or Personal Receivership Indicator (A-P, Z, 1A)"),
    R_REAFFIRMATION_OF_DEBT("R - Reaffirmation of Debt"),
    S_REMOVES_REAFF_OF_DEBT_REAFF_OF_DEBT_RESCINDED_AND_LEASE_ASSUMPTION_INDICATORS("S - Removes Reaff of Debt, Reaff of Debt Rescinded and Lease"),
    T_CREDIT_GRANTOR_CANNOT_LOCATE_CONSUMER("T - Credit Grantor Cannot Locate Consumer"),
    U_CONSUMER_NOW_LOCATED("U - Consumer Now Located (Removes previously reported T Indicator)"),
    V_CHAPTER_7_REAFFIRMATION_OF_DEBT_RESCINDED("V - Chapter 7 Reaffirmation of Debt Rescinded"),
    Z_BANKRUPTCY_UNDESIGNATED_CHAPTER("Z - Bankruptcy -- Undesignated Chapter");

    private final String indicator;

    ConsumerInformationIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getIndicator() {
        return indicator;
    }
}
