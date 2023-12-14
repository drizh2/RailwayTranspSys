package edu.drizh.forms;

import edu.drizh.entity.Contract;
import edu.drizh.entity.Customer;
import edu.drizh.entity.Dispatcher;
import edu.drizh.service.ContractService;
import edu.drizh.service.CustomerService;
import edu.drizh.service.DispatcherService;
import edu.drizh.service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class CustomerEditForm extends JFrame {
    private final CustomerService customerService;
    private final ValidationService validationService;
    private final DispatcherService dispatcherService;
    private final ContractService contractService;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField middlenameField;
    private JTextField companyField;
    private JTextField addressField;
    private JTextField phoneNumberField;

    private JComboBox customersCombobox;

    public CustomerEditForm(CustomerService customerService, ValidationService validationService, DispatcherService dispatcherService, ContractService contractService) {
        this.customerService = customerService;
        this.validationService = validationService;
        this.dispatcherService = dispatcherService;
        this.contractService = contractService;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(8, 2));
        setLocationRelativeTo(null);
        setTitle("Форма редагування замовників");

        JLabel nameLabel = new JLabel("Ім'я:");
        nameField = new JTextField();
        nameField.setName("Ім'я");

        JLabel surnameLabel = new JLabel("Прізвище:");
        surnameField = new JTextField();
        surnameField.setName("Прізвище");

        JLabel middlenameLabel = new JLabel("По-батькові:");
        middlenameField = new JTextField();
        middlenameField.setName("По-батькові");

        JLabel companyLabel = new JLabel("Компанія:");
        companyField = new JTextField();
        companyField.setName("Компанія");

        JLabel addressLabel = new JLabel("Адреса:");
        addressField = new JTextField();
        addressField.setName("Адреса");

        JLabel phoneNumberLabel = new JLabel("Номер телефону:");
        phoneNumberField = new JTextField();
        phoneNumberField.setName("Номер телефону");

        JLabel customerComboboxLabel = new JLabel("Оберіть клієнта: ");
        List<String> customersNames = MainForm.customers.stream()
                .map(c -> c.getName() + " " + c.getSurname())
                .collect(Collectors.toList());

        customersCombobox = new JComboBox(new Vector<>(customersNames));

        JButton saveButton = new JButton("Зберегти");
        JButton deleteButton = new JButton("Видалити");

        customersCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int customerIndex = customersCombobox.getSelectedIndex();

                Customer customer = MainForm.customers.get(customerIndex);

                nameField.setText(customer.getName());
                surnameField.setText(customer.getSurname());
                middlenameField.setText(customer.getMiddlename());
                companyField.setText(customer.getCompany());
                addressField.setText(customer.getAdress());
                phoneNumberField.setText(customer.getPhoneNumber());
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int customerIndex = customersCombobox.getSelectedIndex();

                if (customerIndex > -1) {

                    Customer customer = MainForm.customers.get(customerIndex);

                    if (customer.getCountOfContracts() != 0) {
                        List<Long> ids = customer.getContractsIds();

                        for (int i = 0; i < MainForm.contracts.size(); i++) {
                            Contract contract = MainForm.contracts.get(i);
                            if (ids.contains(contract.getId())) {
                                for (Dispatcher dispatcher : MainForm.dispatchers) {
                                    if (dispatcher.getContractsIds().contains(contract.getId())) {
                                        dispatcher.getContractsIds().remove(contract.getId());
                                    }
                                }
                                MainForm.contracts.remove(contract);
                            }
                        }

                        customerService.saveCustomerToFile(null, MainForm.customers);
                        dispatcherService.saveDispatcherToFile(null, MainForm.dispatchers);
                        contractService.saveContractToFile(null, MainForm.contracts);
                    }

                    MainForm.customers.remove(customerIndex);

                    customerService.saveCustomerToFile(null, MainForm.customers);

                    List<String> customersNames = MainForm.customers.stream()
                            .map(c -> c.getName() + " " + c.getSurname())
                            .collect(Collectors.toList());

                    customersCombobox.setModel(new DefaultComboBoxModel<String>(customersNames.toArray(new String[0])));

                    customerIndex = customersCombobox.getSelectedIndex();

                    if (customerIndex > -1) {
                        customer = MainForm.customers.get(customerIndex);

                        nameField.setText(customer.getName());
                        surnameField.setText(customer.getSurname());
                        middlenameField.setText(customer.getMiddlename());
                        companyField.setText(customer.getCompany());
                        addressField.setText(customer.getAdress());
                        phoneNumberField.setText(customer.getPhoneNumber());
                    } else {

                        nameField.setText(null);
                        surnameField.setText(null);
                        middlenameField.setText(null);
                        companyField.setText(null);
                        addressField.setText(null);
                        phoneNumberField.setText(null);

                    }

                    JOptionPane.showMessageDialog(new JFrame(), "Клієнта і його контракти було успішно видалено!");

                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Оберіть клієнта на видалення!");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int customerIndex = customersCombobox.getSelectedIndex();

                if (customerIndex > -1) {
                    if (validationService.fieldsNonNull(nameField, surnameField, middlenameField, companyField, addressField, phoneNumberField)) {
                        Customer customer = MainForm.customers.get(customerIndex);

                        customer.setName(nameField.getText());
                        customer.setSurname(surnameField.getText());
                        customer.setMiddlename(middlenameField.getText());
                        customer.setCompany(companyField.getText());
                        customer.setAdress(addressField.getText());
                        customer.setPhoneNumber(phoneNumberField.getText());

                        customerService.saveCustomerToFile(null, MainForm.customers);

                        List<String> customersNames = MainForm.customers.stream()
                                .map(c -> c.getName() + " " + c.getSurname())
                                .collect(Collectors.toList());

                        customersCombobox.setModel(new DefaultComboBoxModel<String>(customersNames.toArray(new String[0])));

                        JOptionPane.showMessageDialog(new JFrame(), "Клієнта успішно оновлено!");
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Клієнта не обрано!");
                }
            }
        });

        add(customerComboboxLabel);
        add(customersCombobox);
        add(nameLabel);
        add(nameField);
        add(surnameLabel);
        add(surnameField);
        add(middlenameLabel);
        add(middlenameField);
        add(companyLabel);
        add(companyField);
        add(addressLabel);
        add(addressField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(deleteButton);
        add(saveButton);

        setVisible(true);
    }
}
