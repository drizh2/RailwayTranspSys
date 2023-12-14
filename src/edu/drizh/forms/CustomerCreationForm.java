package edu.drizh.forms;

import edu.drizh.entity.Customer;
import edu.drizh.service.CustomerService;
import edu.drizh.service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerCreationForm extends JFrame {
    private final CustomerService customerService;
    private final ValidationService validationService;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField middlenameField;
    private JTextField companyField;
    private JTextField addressField;
    private JTextField phoneNumberField;

    public CustomerCreationForm(CustomerService customerService, ValidationService validationService) {
        this.customerService = customerService;
        this.validationService = validationService;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(null);
        setTitle("Форма створення замовників");

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

        JButton saveButton = new JButton("Зберегти");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String middlename = middlenameField.getText();
                String company = companyField.getText();
                String address = addressField.getText();
                String phoneNumber = phoneNumberField.getText();

                if (!validationService.fieldsNonNull(nameField, surnameField, middlenameField, companyField, addressField, phoneNumberField)) {
                    return;
                }

                Customer customer = customerService.createCustomer(name, surname, middlename, company, address, phoneNumber, MainForm.customers);

                if (validationService.objectExists(MainForm.customers, customer)) {
                    return;
                }

                customerService.saveCustomerToFile(customer, MainForm.customers);

                JOptionPane.showMessageDialog(new JFrame(), "Клієнт " + name + " " + surname + " був створений!");

                dispose();
            }
        });

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
        add(new JLabel()); // Порожній компонент для розтягування
        add(saveButton);

        setVisible(true);
    }

}
