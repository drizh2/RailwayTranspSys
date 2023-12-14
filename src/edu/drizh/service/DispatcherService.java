package edu.drizh.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.drizh.entity.Dispatcher;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DispatcherService {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    File dispatchersFile = new File(Filenames.DISPATCHERS_FILENAME);

    public void saveDispatcherToFile(Dispatcher dispatcher, List<Dispatcher> dispatchers) {

        if (Objects.nonNull(dispatcher)) {
            dispatchers.add(dispatcher);
        }

        try {
            new FileWriter(Filenames.DISPATCHERS_FILENAME, false).close();
            FileWriter fileWriter = new FileWriter(dispatchersFile);
            gson.toJson(dispatchers, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Виникла помилка при записі в файл!");
        }

    }
}
