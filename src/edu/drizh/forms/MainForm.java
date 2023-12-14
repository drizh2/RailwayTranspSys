package edu.drizh.forms;

import edu.drizh.entity.Contract;
import edu.drizh.entity.Customer;
import edu.drizh.entity.Dispatcher;
import edu.drizh.service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm extends JFrame {
    public static List<Customer> customers = JsonReader.readCustomersFromJson();
    public static List<Dispatcher> dispatchers = JsonReader.readDispatchersFromJson();
    public static List<Contract> contracts = JsonReader.readContractsFromJson();

    public MainForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(null);
        setTitle("Головна форма");

        JButton createClientButton = new JButton("Створити клієнта");
        JButton createDispatcherButton = new JButton("Створити диспетчера");
        JButton createContractButton = new JButton("Створити контракт");
        JButton editClientButton = new JButton("Редагувати клієнтів");
        JButton editDispatcherButton = new JButton("Редагувати диспетчерів");
        JButton editContractButton = new JButton("Редагувати контракт");
        JButton actionsButton = new JButton("Дії над об'єктами");

        createClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerCreationForm(new CustomerService(), new ValidationService());
            }
        });

        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!customers.isEmpty()) {
                    new CustomerEditForm(new CustomerService(), new ValidationService(), new DispatcherService(), new ContractService());
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Немає користувачів для редагування!");
                }
            }
        });

        actionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActionsForm(new CustomerService(), new DispatcherService(), new ContractService(), new ValidationService(), new ActionsService());
            }
        });

        createDispatcherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DispatcherCreationForm(new ValidationService(), new DispatcherService());
            }
        });

        editDispatcherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dispatchers.isEmpty()) {
                    new DispatcherEditForm(new DispatcherService(), new ValidationService(), new CustomerService(), new ContractService());
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Немає диспетчерів для редагування!");
                }
            }
        });

        createContractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customers.isEmpty() && dispatchers.isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Немає ні одного користувача або диспетчера");
                } else {
                    new ContractCreationForm(new ValidationService(), new ContractService(), new CustomerService(), new DispatcherService());
                }
            }
        });

        editContractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contracts.isEmpty()) {
                    new ContractEditForm(new ContractService(), new CustomerService(), new DispatcherService(), new ValidationService());
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Немає контрактів для редагування!");
                }
            }
        });

        editContractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        add(createClientButton);
        add(editClientButton);
        add(new JLabel());
        add(new JLabel());
        add(createDispatcherButton);
        add(editDispatcherButton);
        add(new JLabel());
        add(new JLabel());
        add(createContractButton);
        add(editContractButton);
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(actionsButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm();
            }
        });
    }
}
