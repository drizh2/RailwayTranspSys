package edu.drizh.entity;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher extends Human {
    private Integer experience;
    private List<Long> contractsIds = new ArrayList<>();

    public Dispatcher(String name, String surname, String middlename, String company, String adress, String phoneNumber, Integer experience) {
        super(name, surname, middlename, company, adress, phoneNumber);
        this.experience = experience;
    }

    public void print() {
        super.print();
        System.out.println("Experience: " + experience);
    }

    public void signContract(Contract contract) {
        contractsIds.add(contract.getId());
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public List<Long> getContractsIds() {
        return contractsIds;
    }

    public void setContractsIds(List<Long> contractsIds) {
        this.contractsIds = contractsIds;
    }
}
