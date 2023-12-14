package edu.drizh.service;

import edu.drizh.entity.Contract;
import edu.drizh.entity.Customer;
import edu.drizh.forms.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionsService {
    public void showListOfContracts() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Список контрактів");
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        JLabel contracts = new JLabel("<html>Список контрактів:<br/>");
        contracts.setFont(new Font("Montserrat", Font.PLAIN, 15));

        if (contracts.getHeight() > 400) {
            JScrollPane scrollBar = new JScrollPane(contracts);
        }

        for (Contract contract : MainForm.contracts) {
            contracts.setText(String.format("%s %d. %s[%s, %s, %d днів, %d грн., %d кг, %s]", contracts.getText(), contract.getId(), contract.getTypeOfCargo(), contract.getStartStation(), contract.getFinishStation(), contract.getTimeToTransport(), contract.getCost(), contract.getWeight(), contract.getDate()));
            if (!MainForm.contracts.get(MainForm.contracts.size() - 1).equals(contract)) {
                contracts.setText(contracts.getText() + ", <br/>");
            } else {
                contracts.setText(contracts.getText() + "</html>");
            }
        }

        JButton close = new JButton("Закрити");
        close.setSize(10, 30);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(contracts, BorderLayout.NORTH);
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void showListOfOrderAddresses() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Список адрес доставки");
        dialog.setSize(700, 400);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        JLabel addresses = new JLabel("<html>Список адрес доставки:<br/>");
        addresses.setFont(new Font("Montserrat", Font.PLAIN, 15));

        if (addresses.getHeight() > 400) {
            JScrollPane scrollBar = new JScrollPane(addresses);
        }

        for (Customer customer : MainForm.customers) {
            addresses.setText(addresses.getText() + String.format("%s %s - %s", customer.getName(), customer.getSurname(), customer.getAdress()));
            if (!MainForm.customers.get(MainForm.customers.size() - 1).equals(customer)) {
                addresses.setText(addresses.getText() + ", <br/>");
            } else {
                addresses.setText(addresses.getText() + "</html>");
            }
        }

        JButton close = new JButton("Закрити");
        close.setSize(10, 30);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(addresses, BorderLayout.NORTH);
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void showListOfOrderCompanies() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Список фірм-замовників");
        dialog.setSize(700, 400);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        JLabel addresses = new JLabel("<html>Список фірм-замовників:<br/>");
        addresses.setFont(new Font("Montserrat", Font.PLAIN, 15));

        if (addresses.getHeight() > 400) {
            JScrollPane scrollBar = new JScrollPane(addresses);
        }

        for (Customer customer : MainForm.customers) {
            addresses.setText(addresses.getText() + String.format("%s %s - %s", customer.getName(), customer.getSurname(), customer.getCompany()));
            if (!MainForm.customers.get(MainForm.customers.size() - 1).equals(customer)) {
                addresses.setText(addresses.getText() + ", <br/>");
            } else {
                addresses.setText(addresses.getText() + "</html>");
            }
        }

        JButton close = new JButton("Закрити");
        close.setSize(10, 30);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(addresses, BorderLayout.NORTH);
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void getGreatestTimeToTransport() {
        Contract greatestContract = MainForm.contracts.get(0);
        int greatestTime = MainForm.contracts.get(0).getTimeToTransport();

        for(Contract contract : MainForm.contracts) {
            if (contract.getTimeToTransport() > greatestContract.getTimeToTransport()) {
                greatestContract = contract;
                greatestTime = contract.getTimeToTransport();
            }
        }

        JOptionPane.showMessageDialog(new JFrame(), String.format("Найбільший час доставки має контракт %d - %s. Він становить %d днів.", greatestContract.getId(), greatestContract.getTypeOfCargo(), greatestTime));
    }

    public void getStationWithGreatestDemand() {
        int maxCount = 0;
        String maxFreqElem = "";
        for (Contract contract : MainForm.contracts) {
            int count = 0;
            for (Contract currContract : MainForm.contracts) {
                if (contract.getFinishStation() == currContract.getFinishStation()) {
                    count++;
                }
            }

            if (count > maxCount) {
                maxCount = count;
                maxFreqElem = contract.getFinishStation();
            }
        }

        JOptionPane.showMessageDialog(new JFrame(), String.format("Станція з найбільшим попитом - %s.", maxFreqElem));
    }

    public void getAverageTimeToTransport() {
        int sum = 0;
        int count = 0;

        for (Contract contract : MainForm.contracts) {
            sum += contract.getTimeToTransport();
            count++;
        }

        JOptionPane.showMessageDialog(new JFrame(), String.format("Середній час доставки усіх замовлень становить %d днів", sum/count));
    }
}
