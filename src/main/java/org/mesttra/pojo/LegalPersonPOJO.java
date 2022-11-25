package org.mesttra.pojo;


public class LegalPersonPOJO extends Client {
    private String cpnj, socialReason, fantasyName;

    public LegalPersonPOJO(String phoneNumber, double overDraft, String cpnj, String socialReason, String fantasyName, String[] associateName) {
        super(phoneNumber, overDraft);
        this.cpnj = cpnj;
        this.socialReason = socialReason;
        this.fantasyName = fantasyName;
    }

    public String getCpnj() {
        return cpnj;
    }

    public void setCpnj(String cpnj) {
        this.cpnj = cpnj;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    @Override
    public String ToString(){
        return ("[AccountNumber: " + super.getAccountNumber() + " " +
                "Agency: " + super.getAgency() + " " +
                "PhoneNumber: " + super.getPhoneNumber() + " " +
                "Amount: " + super.getAmount() + " " +
                "OverDraft: " + super.getOverDraft() + " " +
                "CNPJ: " + this.cpnj + " " +
                "SocialReason: " + this.socialReason + " " +
                "FantasyName: " + this.fantasyName + " " + "]"
        );
    }
}