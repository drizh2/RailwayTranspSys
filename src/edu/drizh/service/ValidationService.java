package edu.drizh.service;

import edu.drizh.entity.Human;

import javax.swing.*;
import java.util.List;

public class ValidationService {

    public boolean fieldsNonNull(JTextField ... fields) {
        for (JTextField field : fields) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(), "Поле " + field.getName() + " не може бути порожнім!");
                return false;
            }
        }

        return true;
    }

    public boolean objectExists(List<? extends Human> list, Human object) {
        for (Human human : list) {
            if (human.getName().equals(object.getName()) && human.getSurname().equals(object.getSurname())) {
                JOptionPane.showMessageDialog(new JFrame(), "Об'єкт з таким ім'ям і прізвищем вже існує!");
                return true;
            }
        }

        return false;
    }
}
