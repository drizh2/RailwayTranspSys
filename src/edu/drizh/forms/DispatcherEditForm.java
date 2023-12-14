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

public class DispatcherEditForm extends JFrame {
    private final DispatcherService dispatcherService;
    private final ValidationService validationService;
    private final CustomerService customerService;
    private final ContractService contractService;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField middlenameField;
    private JTextField companyField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField experienceField;

    private JComboBox dispatchersCombobox;

    public DispatcherEditForm(DispatcherService dispatcherService, ValidationService validationService, CustomerService customerService, ContractService contractService) {
        this.dispatcherService = dispatcherService;
        this.validationService = validationService;
        this.customerService = customerService;
        this.contractService = contractService;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(null);
        setTitle("Форма редагування диспетчерів");

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

        JLabel experienceLabel = new JLabel("Досвід:");
        experienceField = new JTextField();
        experienceField.setName("Досвід");

        JButton saveButton = new JButton("Зберегти");
        JButton deleteButton = new JButton("Видалити");

        JLabel dispatchersComboboxLabel = new JLabel("Оберіть диспетчера: ");

        List<String> dispatchersNames = MainForm.dispatchers.stream()
                .map(d -> d.getName() + " " + d.getSurname())
                .collect(Collectors.toList());

        dispatchersCombobox = new JComboBox<>(new Vector<>(dispatchersNames));

        dispatchersCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dispatcherIndex = dispatchersCombobox.getSelectedIndex();

                if (dispatcherIndex > -1) {
                    Dispatcher dispatcher = MainForm.dispatchers.get(dispatcherIndex);

                    nameField.setText(dispatcher.getName());
                    surnameField.setText(dispatcher.getSurname());
                    middlenameField.setText(dispatcher.getMiddlename());
                    companyField.setText(dispatcher.getCompany());
                    addressField.setText(dispatcher.getAdress());
                    phoneNumberField.setText(dispatcher.getPhoneNumber());
                    experienceField.setText(String.valueOf(dispatcher.getExperience()));
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dispatcherIndex = dispatchersCombobox.getSelectedIndex();

                Dispatcher dispatcher = MainForm.dispatchers.get(dispatcherIndex);

                if (dispatcher.getContractsIds().size() != 0) {
                    List<Long> ids = dispatcher.getContractsIds();

                    for (int i = 0; i < MainForm.contracts.size(); i++) {
                        Contract contract = MainForm.contracts.get(i);
                        if (ids.contains(contract.getId())) {
                            for (Customer customer : MainForm.customers) {
                                if (customer.getContractsIds().contains(contract.getId())) {
                                    customer.getContractsIds().remove(contract.getId());
                                }
                            }
                            MainForm.contracts.remove(contract);
                        }
                    }

                    customerService.saveCustomerToFile(null, MainForm.customers);
                    dispatcherService.saveDispatcherToFile(null, MainForm.dispatchers);
                    contractService.saveContractToFile(null, MainForm.contracts);
                }

                MainForm.dispatchers.remove(dispatcherIndex);

                dispatcherService.saveDispatcherToFile(null, MainForm.dispatchers);

                List<String> dispatchersNames = MainForm.dispatchers.stream()
                        .map(d -> d.getName() + " " + d.getSurname())
                        .collect(Collectors.toList());

                dispatchersCombobox.setModel(new DefaultComboBoxModel<String>(dispatchersNames.toArray(new String[0])));

                dispatcherIndex = dispatchersCombobox.getSelectedIndex();

                if (dispatcherIndex > -1) {
                    dispatcher = MainForm.dispatchers.get(dispatcherIndex);

                    nameField.setText(dispatcher.getName());
                    surnameField.setText(dispatcher.getSurname());
                    middlenameField.setText(dispatcher.getMiddlename());
                    companyField.setText(dispatcher.getCompany());
                    addressField.setText(dispatcher.getAdress());
                    phoneNumberField.setText(dispatcher.getPhoneNumber());
                    experienceField.setText(String.valueOf(dispatcher.getExperience()));
                } else {

                    nameField.setText(null);
                    surnameField.setText(null);
                    middlenameField.setText(null);
                    companyField.setText(null);
                    addressField.setText(null);
                    phoneNumberField.setText(null);
                    experienceField.setText(null);
                }
                JOptionPane.showMessageDialog(new JFrame(), "Диспетчера і його контракти було успішно видалено!");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dispatcherIndex = dispatchersCombobox.getSelectedIndex();

                if (dispatcherIndex <= -1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Диспетчера не обрано!");
                } else {

                    if (!experienceField.getText().isEmpty()) {
                        if (validationService.fieldsNonNull(nameField, surnameField, middlenameField, companyField, addressField, phoneNumberField)) {
                            Dispatcher dispatcher = MainForm.dispatchers.get(dispatcherIndex);

                            dispatcher.setName(nameField.getText());
                            dispatcher.setSurname(surnameField.getText());
                            dispatcher.setMiddlename(middlenameField.getText());
                            dispatcher.setCompany(companyField.getText());
                            dispatcher.setAdress(addressField.getText());
                            dispatcher.setPhoneNumber(phoneNumberField.getText());

                            try {
                                dispatcher.setExperience(Integer.parseInt(experienceField.getText()));

                                dispatcherService.saveDispatcherToFile(null, MainForm.dispatchers);

                                List<String> dispatchersNames = MainForm.dispatchers.stream()
                                        .map(d -> d.getName() + " " + d.getSurname())
                                        .collect(Collectors.toList());

                                dispatchersCombobox.setModel(new DefaultComboBoxModel<String>(dispatchersNames.toArray(new String[0])));

                                JOptionPane.showMessageDialog(new JFrame(), "Диспетчера успішно оновлено!");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Поле Досвід не може бути порожнім!");
                    }

                }
            }
        });

        add(dispatchersComboboxLabel);
        add(dispatchersCombobox);
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
        add(experienceLabel);
        add(experienceField);
        add(deleteButton);
        add(saveButton);

        setVisible(true);
    }
}
