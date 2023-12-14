package edu.drizh.forms;

import edu.drizh.entity.Customer;
import edu.drizh.service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionsForm extends JFrame {
    private final CustomerService customerService;
    private final DispatcherService dispatcherService;
    private final ContractService contractService;
    private final ValidationService validationService;
    private final ActionsService actionsService;

    public ActionsForm(CustomerService customerService, DispatcherService dispatcherService, ContractService contractService, ValidationService validationService, ActionsService actionsService) {
        this.customerService = customerService;
        this.dispatcherService = dispatcherService;
        this.contractService = contractService;
        this.validationService = validationService;
        this.actionsService = actionsService;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        setTitle("Форма дій");

        JButton listOfContractsButton = new JButton("Список контрактів");
        JButton listOfOrderAddressesButton = new JButton("Список адрес доставки");
        JButton listOfOrderCompaniesButton = new JButton("Список фірм-замовників");
        JButton greatestTimeToTransportButton = new JButton("Найбільший час доставки");
        JButton stationWithGreatestDemandButton = new JButton("Станція призначення з найбільшим попитом");
        JButton averageTimeToTransportButton = new JButton("Середній час доставки");

        listOfContractsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainForm.contracts.size() > 0) {
                    actionsService.showListOfContracts();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Спочатку додайте щонайменше один контракт!");
                }
            }
        });

        listOfOrderAddressesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isContract = false;

                for (Customer customer : MainForm.customers) {
                    isContract = customer.getCountOfContracts() > 0;
                    if (isContract) {
                        break;
                    }
                }

                if (MainForm.customers.size() > 0 && isContract) {
                    actionsService.showListOfOrderAddresses();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Спочатку додайте щонайменше одного користувача з контрактом!");
                }
            }
        });

        listOfOrderCompaniesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isContract = false;

                for (Customer customer : MainForm.customers) {
                    isContract = customer.getCountOfContracts() > 0;
                    if (isContract) {
                        break;
                    }
                }

                if (MainForm.customers.size() > 0 && isContract) {
                    actionsService.showListOfOrderCompanies();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Спочатку додайте щонайменше одного користувача з контрактом!");
                }
            }
        });

        greatestTimeToTransportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainForm.contracts.size() > 0) {
                    actionsService.getGreatestTimeToTransport();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Спочатку створіть щонайменше один контракт!");
                }
            }
        });

        stationWithGreatestDemandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainForm.contracts.size() > 0) {
                    actionsService.getStationWithGreatestDemand();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Спочатку створіть щонайменше один контракт!");
                }
            }
        });

        averageTimeToTransportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainForm.contracts.size() > 0) {
                    actionsService.getAverageTimeToTransport();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Спочатку створіть щонайменше один контракт!");
                }
            }
        });

        add(listOfContractsButton);
        add(listOfOrderAddressesButton);
        add(listOfOrderCompaniesButton);
        add(greatestTimeToTransportButton);
        add(stationWithGreatestDemandButton);
        add(averageTimeToTransportButton);

        setVisible(true);
    }
}
