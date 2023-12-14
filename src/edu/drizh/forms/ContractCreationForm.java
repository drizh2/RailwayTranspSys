package edu.drizh.forms;

import edu.drizh.entity.Contract;
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

public class ContractCreationForm extends JFrame {
    private final ValidationService validationService;
    private final ContractService contractService;
    private final CustomerService customerService;
    private final DispatcherService dispatcherService;

    private JTextField sumOfInsuranceField;
    private JTextField typeOfCargoField;
    private JTextField timeToTransportField;
    private JTextField startStationField;
    private JTextField finishStationField;
    private JTextField costField;
    private JTextField weightField;
    private JTextField dateField;

    private JComboBox customersComboBox;
    private JComboBox dispatchersComboBox;

    public ContractCreationForm(ValidationService validationService, ContractService contractService, CustomerService customerService, DispatcherService dispatcherService) {
        this.validationService = validationService;
        this.contractService = contractService;
        this.customerService = customerService;
        this.dispatcherService = dispatcherService;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(11, 2));
        setLocationRelativeTo(null);
        setTitle("Форма створення контрактів");

        JLabel sumOfInsuranceLabel = new JLabel("Сума страхування:");
        sumOfInsuranceField = new JTextField();
        sumOfInsuranceField.setName("Сума страхування");

        JLabel typeOfCargoLabel = new JLabel("Тип вантажу:");
        typeOfCargoField = new JTextField();
        typeOfCargoField.setName("Тип вантажу");

        JLabel timeToTransportLabel = new JLabel("Час доставки:");
        timeToTransportField = new JTextField();
        timeToTransportField.setName("Час доставки");

        JLabel startStationLabel = new JLabel("Станція відправлення:");
        startStationField = new JTextField();
        startStationField.setName("Станція відправлення");

        JLabel finishStationLabel = new JLabel("Станція прибуття:");
        finishStationField = new JTextField();
        finishStationField.setName("Станція прибуття");

        JLabel costLabel = new JLabel("Ціна:");
        costField = new JTextField();
        costField.setName("Ціна");

        JLabel weightLabel = new JLabel("Вага:");
        weightField = new JTextField();
        weightField.setName("Вага");

        JLabel dateLabel = new JLabel("Дата:");
        dateField = new JTextField();
        dateField.setName("Дата");

        JLabel customerLabel = new JLabel("Клієнт:");
        List<String> customersNames = MainForm.customers.stream()
                .map(c -> c.getName() + " " + c.getSurname())
                .collect(Collectors.toList());
        customersComboBox = new JComboBox<>(new Vector<>(customersNames));

        JLabel dispatcherLabel = new JLabel("Диспетчер:");
        List<String> dispatchersNames = MainForm.dispatchers.stream()
                .map(d -> d.getName() + " " + d.getSurname())
                .collect(Collectors.toList());
        dispatchersComboBox = new JComboBox<>(new Vector<>(dispatchersNames));

        JButton saveButton = new JButton("Зберегти");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sumOfInsuranceStr = sumOfInsuranceField.getText();
                String timeToTransportStr = timeToTransportField.getText();
                String costStr = costField.getText();
                String weightStr = weightField.getText();
                if (sumOfInsuranceStr.isEmpty() ||
                    timeToTransportStr.isEmpty() ||
                    costStr.isEmpty() ||
                    weightStr.isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Поля не можуть бути порожніми!");
                } else {
                    try {

                        Integer sumOfInsurance = Integer.parseInt(sumOfInsuranceStr);
                        Integer timeToTransport = Integer.parseInt(timeToTransportStr);
                        Integer cost = Integer.parseInt(costStr);
                        Integer weight = Integer.parseInt(weightStr);

                        String typeOfCargo = typeOfCargoField.getText();
                        String startStation = startStationField.getText();
                        String finishStation = finishStationField.getText();
                        String date = dateField.getText();

                        if (!validationService.fieldsNonNull(typeOfCargoField, startStationField, finishStationField, dateField)) {
                            return;
                        }

                        Contract contract = contractService.createContract(sumOfInsurance, typeOfCargo, timeToTransport, startStation, finishStation, cost, weight, date, MainForm.contracts);

                        int dispatcherIndex = dispatchersComboBox.getSelectedIndex();
                        int customerIndex = customersComboBox.getSelectedIndex();

                        MainForm.customers.get(customerIndex).signContract(contract);
                        MainForm.dispatchers.get(dispatcherIndex).signContract(contract);

                        customerService.saveCustomerToFile(null, MainForm.customers);
                        dispatcherService.saveDispatcherToFile(null, MainForm.dispatchers);

                        contractService.saveContractToFile(contract, MainForm.contracts);

                        JOptionPane.showMessageDialog(new JFrame(), "Контракт " + typeOfCargo + " був створений!");

                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
                    }


                }
            }
        });

        add(customerLabel);
        add(customersComboBox);
        add(dispatcherLabel);
        add(dispatchersComboBox);
        add(sumOfInsuranceLabel);
        add(sumOfInsuranceField);
        add(typeOfCargoLabel);
        add(typeOfCargoField);
        add(timeToTransportLabel);
        add(timeToTransportField);
        add(startStationLabel);
        add(startStationField);
        add(finishStationLabel);
        add(finishStationField);
        add(costLabel);
        add(costField);
        add(weightLabel);
        add(weightField);
        add(dateLabel);
        add(dateField);
        add(new JLabel());
        add(saveButton);

        setVisible(true);

    }
}
