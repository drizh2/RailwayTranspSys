package edu.drizh.forms;

import edu.drizh.entity.Dispatcher;
import edu.drizh.service.DispatcherService;
import edu.drizh.service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DispatcherCreationForm extends JFrame {
    private final ValidationService validationService;
    private final DispatcherService dispatcherService;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField middlenameField;
    private JTextField companyField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField experienceField;

    public DispatcherCreationForm(ValidationService validationService, DispatcherService dispatcherService) {
        this.validationService = validationService;
        this.dispatcherService = dispatcherService;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(8, 2));
        setLocationRelativeTo(null);
        setTitle("Форма створення диспетчерів");

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

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String middlename = middlenameField.getText();
                String company = companyField.getText();
                String address = addressField.getText();
                String phoneNumber = phoneNumberField.getText();

                if (experienceField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Поле Досвід не може бути порожнім!");
                } else {
                    try {
                        Integer experience = Integer.parseInt(experienceField.getText());

                        if (!validationService.fieldsNonNull(nameField, surnameField, middlenameField, companyField, addressField, phoneNumberField, experienceField)) {
                            return;
                        }

                        Dispatcher dispatcher = new Dispatcher(name, surname, middlename, company, address, phoneNumber, experience);

                        if (validationService.objectExists(MainForm.dispatchers, dispatcher)) {
                            return;
                        }

                        dispatcherService.saveDispatcherToFile(dispatcher, MainForm.dispatchers);

                        JOptionPane.showMessageDialog(new JFrame(), "Диспетчер " + name + " " + surname + " був створений!");

                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
                    }
                }
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
        add(experienceLabel);
        add(experienceField);
        add(new JLabel()); // Порожній компонент для розтягування
        add(saveButton);

        setVisible(true);
    }
}
