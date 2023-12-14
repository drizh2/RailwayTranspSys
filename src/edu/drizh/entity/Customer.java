package edu.drizh.entity;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Human {
    private int countOfContracts;
    private long id;
    private List<Long> contractsIds = new ArrayList<>();

    public Customer(String name, String surname, String middlename, String company, String adress, String phoneNumber, long id) {
        super(name, surname, middlename, company, adress, phoneNumber);
        this.id = id;
    }

    public void print() {
        super.print();
        System.out.println("Id: " + getId());
    }

    public void signContract(Contract contract) {
        countOfContracts++;
        contractsIds.add(contract.getId());
    }

    public int getCountOfContracts() {
        return countOfContracts;
    }

    public void setCountOfContracts(int countOfContracts) {
        this.countOfContracts = countOfContracts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getContractsIds() {
        return contractsIds;
    }

    public void setContractsIds(List<Long> contractsIds) {
        this.contractsIds = contractsIds;
    }
}
