package edu.drizh.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.drizh.entity.Customer;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomerService {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    File customersFile = new File(Filenames.CUSTOMERS_FILENAME);

    public Customer createCustomer(String name, String surname, String middlename, String company, String address, String phoneNumber, List<Customer> customers) {
        Long id = 0L;

        id = Objects.nonNull(customers) ? (long) customers.size() : 0L;

        return new Customer(name, surname, middlename, company, address, phoneNumber, id);
    }

    public void saveCustomerToFile(Customer customer, List<Customer> customers) {

        if (Objects.nonNull(customer)) {
            customers.add(customer);
        }
        try {
            new FileWriter(Filenames.CUSTOMERS_FILENAME, false).close();
            FileWriter fileWriter = new FileWriter(customersFile);
            gson.toJson(customers, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Виникла помилка при записі в файл!");
        }

    }
}
