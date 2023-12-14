package edu.drizh.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.drizh.entity.Contract;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ContractService {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    File contractsFile = new File(Filenames.CONTRACTS_FILENAME);

    public Contract createContract(Integer sumOfInsurance, String typeOfCargo, Integer timeToTransport, String startStation, String finishStation, Integer cost, Integer weight, String date, List<Contract> contracts) {

        long id = 0L;

        id = Objects.nonNull(contracts) ? (long) contracts.size() : 0L;

        return new Contract(sumOfInsurance, typeOfCargo, timeToTransport, startStation, finishStation, cost, weight, date, id);
    }

    public void saveContractToFile(Contract contract, List<Contract> contracts) {

        if (Objects.nonNull(contract)) {
            contracts.add(contract);
        }

        try {
            new FileWriter(Filenames.CONTRACTS_FILENAME, false).close();
            FileWriter fileWriter = new FileWriter(contractsFile);
            gson.toJson(contracts, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Виникла помилка при записі в файл!");
        }

    }

}
