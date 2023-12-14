//package edu.drizh;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import edu.drizh.entity.Contract;
//import edu.drizh.entity.Customer;
//import edu.drizh.entity.Dispatcher;
//import edu.drizh.service.Filenames;
//import edu.drizh.service.JsonReader;
//
//import javax.swing.*;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.*;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//
//        List<Customer> customers = JsonReader.readCustomersFromJson();
//        List<Dispatcher> dispatchers = JsonReader.readDispatchersFromJson();
//        List<Contract> contracts = JsonReader.readContractsFromJson();
//
//        File dispatchersFile = new File(Filenames.DISPATCHERS_FILENAME);
//        File customersFile = new File(Filenames.CUSTOMERS_FILENAME);
//        File contractsFile = new File(Filenames.CONTRACTS_FILENAME);
//
//        while (true) {
//            System.out.println("Enter a number to get access to the function: ");
//            System.out.println("1. List of contracts");
//            System.out.println("2. List of delivery addresses");
//            System.out.println("3. List of companies");
//            System.out.println("4. The longest delivery time");
//            System.out.println("5. The station with greatest demand");
//            System.out.println("6. Average delivery time");
//            System.out.println("7. Create contract");
//            System.out.println("8. Create customer");
//            System.out.println("9. Create dispatcher");
//            System.out.println("10. Edit entities");
//            System.out.println("0. Exit");
//
//            Integer result = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (result) {
//                case 1:
//                    Contract.printAllContracts();
//                    break;
//                case 2:
//                    for (int i = 0; i < contracts.size(); i++) {
//                        System.out.println(i + " contract: " + contracts.get(i).getFinishStation());
//                    }
//                    break;
//                case 3:
//                    for (int i = 0; i < customers.size(); i++) {
//                        System.out.println(i + " customer: " + customers.get(i).getCompany());
//                    }
//                    break;
//                case 4:
//                    if (contracts.size() != 0) {
//                        int greatestIndex = 0;
//                        int greatestTime = 0;
//                        for (int i = 0; i < contracts.size(); i++) {
//                            Contract currentContract = contracts.get(i);
//                            if (currentContract.getTimeToTransport() > greatestTime) {
//                                greatestIndex = i;
//                                greatestTime = currentContract.getTimeToTransport();
//                            }
//                        }
//                        System.out.println("The longest delivery time has " + greatestIndex + "th contract. It is " + greatestTime + " days.");
//                    }
//                    break;
//                case 5:
//                    if (contracts.size() != 0) {
//                        int maxCount = 0;
//                        String maxFreqElem = "";
//                        for (int i = 0; i < contracts.size(); i++) {
//                            int count = 0;
//                            for (int j = 0; j < contracts.size(); j++) {
//                                if (contracts.get(i).getFinishStation() == contracts.get(j).getFinishStation()) {
//                                    count++;
//                                }
//                            }
//
//                            if (count > maxCount) {
//                                maxCount = count;
//                                maxFreqElem = contracts.get(i).getFinishStation();
//                            }
//                        }
//
//                        System.out.println("The station with greatest demand is: " + maxFreqElem + ". It appears " + maxCount + " times.");
//                    }
//                    break;
//                case 6:
//                    if (contracts.size() != 0) {
//                        int sum = 0;
//                        int count = 0;
//
//                        for (Contract contract : contracts) {
//                            sum += contract.getTimeToTransport();
//                            count++;
//                        }
//
//                        System.out.println("Average delivery time is: " + sum / count + " days.");
//                    }
//                    break;
//                case 7:
//                    if (customers.size() != 0 && dispatchers.size() != 0) {
//                        System.out.println("Which customer you wanna add this contract to?");
//                        for (int i = 0; i < customers.size(); i++) {
//                            Customer currentCustomer = customers.get(i);
//                            System.out.println(i + " customer(" + currentCustomer.getName() + " " + currentCustomer.getSurname() + ")");
//                        }
//
//                        int indexOfCustomer = scanner.nextInt();
//                        scanner.nextLine();
//
//                        System.out.println("Which dispatcher you wanna add this contract to?");
//                        for (int i = 0; i < dispatchers.size(); i++) {
//                            Dispatcher currentDispatcher = dispatchers.get(i);
//                            System.out.println(i + " dispatcher(" + currentDispatcher.getName() + " " + currentDispatcher.getSurname() + ")");
//                        }
//
//                        int indexOfDispatcher = scanner.nextInt();
//                        scanner.nextLine();
//
//                        System.out.println("Enter the sum of insurance: ");
//                        int sumOfInsurance = scanner.nextInt();
//                        scanner.nextLine();
//
//                        System.out.println("Enter the type of cargo: ");
//                        String typeOfCargo = scanner.nextLine();
//
//                        System.out.println("Enter the time to transport: ");
//                        int timeToTransport = scanner.nextInt();
//                        scanner.nextLine();
//
//                        System.out.println("Enter the start station: ");
//                        String startStation = scanner.nextLine();
//
//                        System.out.println("Enter the finish station: ");
//                        String finishStation = scanner.nextLine();
//
//                        System.out.println("Enter the cost: ");
//                        int cost = scanner.nextInt();
//                        scanner.nextLine();
//
//                        System.out.println("Enter the weight: ");
//                        int weight = scanner.nextInt();
//                        scanner.nextLine();
//
//                        System.out.println("Enter the date: ");
//                        String date = scanner.nextLine();
//
//                        long id = 0L;
//
//                        id = Objects.nonNull(customers) ? (long) customers.size() : 0L;
//
//                        Contract contract = new Contract(sumOfInsurance, typeOfCargo, timeToTransport, startStation, finishStation, cost, weight, date, id);
//
//                        contracts.add(contract);
//                        customers.get(indexOfCustomer).signContract(contract);
//                        dispatchers.get(indexOfDispatcher).signContract(contract);
//
//                        try {
//                            FileWriter fileWriter = new FileWriter(contractsFile);
//                            gson.toJson(contracts, fileWriter);
//                            fileWriter.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        System.out.println("There are no customers or dispatchers!");
//                    }
//                    break;
//                case 8:
//                    System.out.println("Enter the name: ");
//                    String name = scanner.nextLine();
//
//                    System.out.println("Enter the surname: ");
//                    String surname = scanner.nextLine();
//
//                    System.out.println("Enter the middlename: ");
//                    String middlename = scanner.nextLine();
//
//                    System.out.println("Enter the company: ");
//                    String company = scanner.nextLine();
//
//                    System.out.println("Enter the address: ");
//                    String address = scanner.nextLine();
//
//                    System.out.println("Enter the phone number: ");
//                    String phoneNumber = scanner.nextLine();
//
//                    Long id = 0L;
//
//                    id = Objects.nonNull(customers) ? (long) customers.size() : 0L;
//
//                    Customer tempCustomer = new Customer(name, surname, middlename, company, address, phoneNumber, id);
//                    customers.add(tempCustomer);
//
//                    try {
//                        FileWriter fileWriter = new FileWriter(customersFile);
//                        gson.toJson(customers, fileWriter);
//                        fileWriter.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 9:
//                    System.out.println("Enter the name: ");
//                    String dispatcherName = scanner.nextLine();
//
//                    System.out.println("Enter the surname: ");
//                    String dispatcherSurname = scanner.nextLine();
//
//                    System.out.println("Enter the middlename: ");
//                    String dispatcherMiddlename = scanner.nextLine();
//
//                    System.out.println("Enter the company: ");
//                    String dispatcherCompany = scanner.nextLine();
//
//                    System.out.println("Enter the address: ");
//                    String dispatcherAddress = scanner.nextLine();
//
//                    System.out.println("Enter the phone number: ");
//                    String dispatcherPhoneNumber = scanner.nextLine();
//
//                    System.out.println("Enter the experience: ");
//                    int experience = scanner.nextInt();
//                    scanner.nextLine();
//
//                    Dispatcher dispatcher = new Dispatcher(dispatcherName, dispatcherSurname, dispatcherMiddlename, dispatcherCompany, dispatcherAddress, dispatcherPhoneNumber, experience);
//                    dispatchers.add(dispatcher);
//
//
//                    try {
//                        FileWriter fileWriter = new FileWriter(dispatchersFile);
//                        gson.toJson(dispatchers, fileWriter);
//                        fileWriter.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 10:
//                    System.out.println("Enter a number to get access to the function: ");
//                    System.out.println("1. Edit contract");
//                    System.out.println("2. Edit customer");
//                    System.out.println("3. Delete contract");
//                    System.out.println("4. Delete customer");
//                    System.out.println("0. Exit");
//
//                    int res = scanner.nextInt();
//                    switch (res) {
//                        case 1:
//                            if (contracts.size() != 0) {
//                                System.out.println("Which contract you wanna edit?");
//                                for (int i = 0; i < contracts.size(); i++) {
//                                    Contract currentContract = contracts.get(i);
//                                    System.out.println(i + " contract(" + currentContract.getTypeOfCargo() + ")");
//                                }
//
//                                int index = scanner.nextInt();
//                                scanner.nextLine();
//
//                                Contract contractToEdit = contracts.get(index);
//
//                                System.out.println("Enter the sum of insurance: ");
//                                int sumOfInsurance = scanner.nextInt();
//                                scanner.nextLine();
//                                contractToEdit.setSumOfInsurance(sumOfInsurance);
//
//                                System.out.println("Enter the type of cargo: ");
//                                String typeOfCargo = scanner.nextLine();
//                                contractToEdit.setTypeOfCargo(typeOfCargo);
//
//                                System.out.println("Enter the time to transport: ");
//                                int timeToTransport = scanner.nextInt();
//                                scanner.nextLine();
//                                contractToEdit.setTimeToTransport(timeToTransport);
//
//                                System.out.println("Enter the start station: ");
//                                String startStation = scanner.nextLine();
//                                contractToEdit.setStartStation(startStation);
//
//                                System.out.println("Enter the finish station: ");
//                                String finishStation = scanner.nextLine();
//                                contractToEdit.setFinishStation(finishStation);
//
//                                System.out.println("Enter the cost: ");
//                                int cost = scanner.nextInt();
//                                scanner.nextLine();
//                                contractToEdit.setCost(cost);
//
//                                System.out.println("Enter the weight: ");
//                                int weight = scanner.nextInt();
//                                scanner.nextLine();
//                                contractToEdit.setWeight(weight);
//
//                                System.out.println("Enter the date: ");
//                                String date = scanner.nextLine();
//                                contractToEdit.setDate(date);
//
//                                new FileWriter(Filenames.CONTRACTS_FILENAME, false).close();
//
//                                Contract.printAllContracts();
//
//                                try {
//                                    FileWriter fileWriter = new FileWriter(contractsFile);
//                                    gson.toJson(contracts, fileWriter);
//                                    fileWriter.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            break;
//                        case 2:
//                            if (customers.size() != 0) {
//                                System.out.println("Which customer you wanna edit?");
//                                for (int i = 0; i < customers.size(); i++) {
//                                    Customer currentCustomer = customers.get(i);
//                                    System.out.println(i + " customer(" + currentCustomer.getName() + " " + currentCustomer.getSurname() + ")");
//                                }
//
//                                int index = scanner.nextInt();
//                                scanner.nextLine();
//
//                                Customer customerToEdit = customers.get(index);
//
//                                System.out.println("Enter the name: ");
//                                String customerName = scanner.nextLine();
//                                customerToEdit.setName(customerName);
//
//                                System.out.println("Enter the surname: ");
//                                String customerSurname = scanner.nextLine();
//                                customerToEdit.setSurname(customerSurname);
//
//                                System.out.println("Enter the middlename: ");
//                                String customerMiddleName = scanner.nextLine();
//                                customerToEdit.setMiddlename(customerMiddleName);
//
//                                System.out.println("Enter the company: ");
//                                String customerCompany = scanner.nextLine();
//                                customerToEdit.setCompany(customerCompany);
//
//                                System.out.println("Enter the address: ");
//                                String customerAddress = scanner.nextLine();
//                                customerToEdit.setAdress(customerAddress);
//
//                                System.out.println("Enter the phone number: ");
//                                String customerPhoneNumber = scanner.nextLine();
//                                customerToEdit.setPhoneNumber(customerPhoneNumber);
//
//                                try {
//                                    FileWriter fileWriter = new FileWriter(customersFile);
//                                    gson.toJson(customers, fileWriter);
//                                    fileWriter.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            break;
//                        case 3:
//                            if (contracts.size() != 0) {
//                                System.out.println("Which contract you wanna delete?");
//                                for (int i = 0; i < contracts.size(); i++) {
//                                    Contract currentContract = contracts.get(i);
//                                    System.out.println(i + " contract(" + currentContract.getTypeOfCargo() + ")");
//                                }
//
//                                int contractIndex = scanner.nextInt();
//                                scanner.nextLine();
//
//                                contracts.remove(contractIndex);
//
//                                new FileWriter(Filenames.CONTRACTS_FILENAME, false).close();
//
//                                try {
//                                    FileWriter fileWriter = new FileWriter(contractsFile);
//                                    gson.toJson(contracts, fileWriter);
//                                    fileWriter.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                System.out.println(contractIndex + " has been deleted successfully!");
//                            } else {
//                                System.out.println("There are no contracts!");
//                            }
//                            break;
//                        case 4:
//                            if (customers.size() != 0) {
//                                System.out.println("Which customer you wanna delete?");
//                                for (int i = 0; i < customers.size(); i++) {
//                                    Customer currentCustomer = customers.get(i);
//                                    System.out.println(i + " customer(" + currentCustomer.getName() + " " + currentCustomer.getSurname() + ")");
//                                }
//
//                                int index = scanner.nextInt();
//                                scanner.nextLine();
//
//                                customers.remove(index);
//
//                                new FileWriter(Filenames.CUSTOMERS_FILENAME, false).close();
//
//                                try {
//                                    FileWriter fileWriter = new FileWriter(customersFile);
//                                    gson.toJson(customers, fileWriter);
//                                    fileWriter.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                System.out.println(index + " customer has been deleted succesfully!");
//                            } else {
//                                System.out.println("There are no contracts!");
//                            }
//                            break;
//                        case 0:
//                            continue;
//                        default:
//                            System.out.println("You entered wrong number! Please try again!");
//                            continue;
//                    }
//                    break;
//                default:
//                    System.out.println("You entered wrong number! Please try again!");
//                    continue;
//            }
//        }
//    }
//}