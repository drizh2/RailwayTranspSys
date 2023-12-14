package edu.drizh.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.drizh.entity.Contract;
import edu.drizh.entity.Customer;
import edu.drizh.entity.Dispatcher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonReader {

    public static List<Customer> readCustomersFromJson() {
        List<Customer> customers = null;
        File file = new File(Filenames.CUSTOMERS_FILENAME);
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Type type = new TypeToken<ArrayList<Customer>>(){}.getType();

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

                customers = gson.fromJson(fileReader, type);

                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Objects.nonNull(customers) ? customers : new ArrayList<>();
    }

    public static List<Dispatcher> readDispatchersFromJson() {
        List<Dispatcher> dispatchers = null;
        File file = new File(Filenames.DISPATCHERS_FILENAME);
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Type type = new TypeToken<ArrayList<Dispatcher>>(){}.getType();

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

                dispatchers = gson.fromJson(fileReader, type);

                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Objects.nonNull(dispatchers) ? dispatchers : new ArrayList<>();
    }

    public static List<Contract> readContractsFromJson() {
        List<Contract> contracts = null;
        File file = new File(Filenames.CONTRACTS_FILENAME);
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Type type = new TypeToken<ArrayList<Contract>>(){}.getType();

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

                contracts = gson.fromJson(fileReader, type);
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Objects.nonNull(contracts) ? contracts : new ArrayList<>();
    }
}
