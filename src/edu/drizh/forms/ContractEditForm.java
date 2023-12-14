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

public class ContractEditForm extends JFrame {
    private final ContractService contractService;
    private final CustomerService customerService;
    private final DispatcherService dispatcherService;
    private final ValidationService validationService;

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
    private JComboBox contractCombobox;

    public ContractEditForm(ContractService contractService, CustomerService customerService, DispatcherService dispatcherService, ValidationService validationService) {
        this.contractService = contractService;
        this.customerService = customerService;
        this.dispatcherService = dispatcherService;
        this.validationService = validationService;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(12, 2));
        setLocationRelativeTo(null);
        setTitle("Форма редагування контрактів");

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

        JLabel contractComboboxLabel = new JLabel("Оберіть контракт: ");
        List<String> contractsNames = MainForm.contracts.stream()
                .map(c -> c.getTypeOfCargo())
                .collect(Collectors.toList());
        contractCombobox = new JComboBox<>(new Vector<>(contractsNames));

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
        JButton deleteButton = new JButton("Видалити");

        contractCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int contractIndex = contractCombobox.getSelectedIndex();

               if (contractIndex > -1) {
                   Contract contract = MainForm.contracts.get(contractIndex);

                   for (int i = 0; i < MainForm.dispatchers.size(); i++) {
                       if (MainForm.dispatchers.get(i).getContractsIds().contains(contract.getId())) {
                           dispatchersComboBox.setSelectedIndex(i);
                       }
                   }

                   for (int i = 0; i < MainForm.customers.size(); i++) {
                       if (MainForm.customers.get(i).getContractsIds().contains(contract.getId())) {
                           customersComboBox.setSelectedIndex(i);
                       }
                   }

                   sumOfInsuranceField.setText(String.valueOf(contract.getSumOfInsurance()));
                   typeOfCargoField.setText(contract.getTypeOfCargo());
                   timeToTransportField.setText(String.valueOf(contract.getTimeToTransport()));
                   startStationField.setText(contract.getStartStation());
                   finishStationField.setText(contract.getFinishStation());
                   costField.setText(String.valueOf(contract.getCost()));
                   weightField.setText(String.valueOf(contract.getWeight()));
                   dateField.setText(contract.getDate());
               }

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int contractIndex = contractCombobox.getSelectedIndex();
                int customerIndex = customersComboBox.getSelectedIndex();
                int dispatcherIndex = dispatchersComboBox.getSelectedIndex();

                String sumOfInsuranceStr = sumOfInsuranceField.getText();
                String timeToTransportStr = timeToTransportField.getText();
                String costStr = costField.getText();
                String weightStr = weightField.getText();

                if (contractIndex > -1) {
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

                            Contract contract = MainForm.contracts.get(contractIndex);

                            contract.setSumOfInsurance(sumOfInsurance);
                            contract.setTypeOfCargo(typeOfCargo);
                            contract.setTimeToTransport(timeToTransport);
                            contract.setStartStation(startStation);
                            contract.setFinishStation(finishStation);
                            contract.setCost(cost);
                            contract.setWeight(weight);
                            contract.setDate(date);

                            for (Dispatcher dispatcher : MainForm.dispatchers) {
                                if (dispatcher.getContractsIds().contains(contract.getId())) {
                                    dispatcher.getContractsIds().remove(contract.getId());
                                }
                            }

                            for (Customer customer : MainForm.customers) {
                                if (customer.getContractsIds().contains(contract.getId())) {
                                    customer.getContractsIds().remove(contract.getId());
                                    customer.setCountOfContracts(customer.getCountOfContracts() - 1);
                                }
                            }

                            MainForm.customers.get(customerIndex).signContract(contract);
                            MainForm.dispatchers.get(dispatcherIndex).signContract(contract);

                            List<String> newContractNames = MainForm.contracts.stream()
                                            .map(c -> c.getTypeOfCargo())
                                            .collect(Collectors.toList());

                            contractCombobox.setModel(new DefaultComboBoxModel<String>(newContractNames.toArray(new String[0])));

                            customerService.saveCustomerToFile(null, MainForm.customers);
                            dispatcherService.saveDispatcherToFile(null, MainForm.dispatchers);
                            contractService.saveContractToFile(null, MainForm.contracts);

                            JOptionPane.showMessageDialog(new JFrame(), "Контракт було оновлено!");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Оберіть контракт для редагування");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int contractIndex = contractCombobox.getSelectedIndex();
                int customerIndex = customersComboBox.getSelectedIndex();
                int dispatcherIndex = dispatchersComboBox.getSelectedIndex();

                if (contractIndex > -1) {
                    Contract contract = MainForm.contracts.get(contractIndex);
                    Dispatcher dispatcher = MainForm.dispatchers.get(dispatcherIndex);
                    Customer customer = MainForm.customers.get(customerIndex);

                    dispatcher.getContractsIds().remove(contract.getId());
                    customer.getContractsIds().remove(contract.getId());
                    MainForm.contracts.remove(contract);

                    customerService.saveCustomerToFile(null, MainForm.customers);
                    dispatcherService.saveDispatcherToFile(null, MainForm.dispatchers);
                    contractService.saveContractToFile(null, MainForm.contracts);

                    List<String> contractsNames = MainForm.contracts.stream()
                            .map(c -> c.getTypeOfCargo())
                            .collect(Collectors.toList());

                    contractCombobox.setModel(new DefaultComboBoxModel<String>(contractsNames.toArray(new String[0])));

                    contractIndex = contractCombobox.getSelectedIndex();

                    if (contractIndex > -1) {
                        contract = MainForm.contracts.get(contractIndex);

                        for (int i = 0; i < MainForm.customers.size(); i++) {
                            customer = MainForm.customers.get(i);
                            if (customer.getContractsIds().contains(contract.getId())) {
                                customersComboBox.setSelectedIndex(i);
                            }
                        }

                        for (int i = 0; i < MainForm.dispatchers.size(); i++) {
                            dispatcher = MainForm.dispatchers.get(i);
                            if (dispatcher.getContractsIds().contains(contract.getId())) {
                                dispatchersComboBox.setSelectedIndex(i);
                            }
                        }

                        sumOfInsuranceField.setText(String.valueOf(contract.getSumOfInsurance()));
                        typeOfCargoField.setText(contract.getTypeOfCargo());
                        timeToTransportField.setText(String.valueOf(contract.getTimeToTransport()));
                        startStationField.setText(contract.getStartStation());
                        finishStationField.setText(contract.getFinishStation());
                        costField.setText(String.valueOf(contract.getCost()));
                        weightField.setText(String.valueOf(contract.getWeight()));
                        dateField.setText(contract.getDate());
                    } else {
                        sumOfInsuranceField.setText(null);
                        typeOfCargoField.setText(null);
                        timeToTransportField.setText(null);
                        startStationField.setText(null);
                        finishStationField.setText(null);
                        costField.setText(null);
                        weightField.setText(null);
                        dateField.setText(null);
                    }

                    JOptionPane.showMessageDialog(new JFrame(), "Контракт було видалено!");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Оберіть контракт для видалення!");
                }
            }
        });

        add(contractComboboxLabel);
        add(contractCombobox);
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
        add(deleteButton);
        add(saveButton);

        setVisible(true);
    }
}
