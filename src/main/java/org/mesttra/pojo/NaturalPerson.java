package org.mesttra.pojo;

public class NaturalPerson extends Client {
    private String cpf, name;
    private int age;

    public NaturalPerson(String phoneNumber, double overDraft, String cpf, String name, int age) {
        super(phoneNumber, overDraft);
        this.cpf = cpf;
        this.name = name;
        this.age = age;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String ToString() {
        return ("[AccountNumber: " + super.getAccountNumber() + " " +
                "Agency: " + super.getAgency() + " " +
                "PhoneNumber: " + super.getPhoneNumber() + " " +
                "Amount: " + super.getAmount() + " " +
                "OverDraft: " + super.getOverDraft() + " " +
                "CPF: " + this.cpf + " " +
                "Name: " + this.name + " " +
                "Age: " + this.age + "]"
        );
    }
}