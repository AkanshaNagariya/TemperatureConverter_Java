import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverterGUI extends JFrame {

    private JLabel inputLabel;
    private JTextField temperatureField;
    private JComboBox<String> sourceScaleComboBox;
    private JComboBox<String> targetScaleComboBox;
    private JButton convertButton;
    private JLabel resultLabel;

    public TemperatureConverterGUI() {
        setTitle("Temperature Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        inputLabel = new JLabel("Enter the temperature value:");
        temperatureField = new JTextField();
        sourceScaleComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        targetScaleComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        convertButton = new JButton("Convert");
        resultLabel = new JLabel();

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temperatureText = temperatureField.getText();
                if (temperatureText.isEmpty()) {
                    resultLabel.setText("Please enter a temperature value.");
                    return;
                }

                double temperature;
                try {
                    temperature = Double.parseDouble(temperatureText);
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid temperature value. Please enter a numeric value.");
                    return;
                }

                String sourceScale = (String) sourceScaleComboBox.getSelectedItem();
                String targetScale = (String) targetScaleComboBox.getSelectedItem();

                if (!isValidScale(sourceScale) || !isValidScale(targetScale)) {
                    resultLabel.setText("Invalid temperature scale selected.");
                    return;
                }

                double convertedTemperature = convertTemperature(temperature, sourceScale, targetScale);
                resultLabel.setText("Converted temperature: " + convertedTemperature + " " + targetScale.toUpperCase());
            }
        });

        panel.add(inputLabel);
        panel.add(temperatureField);
        panel.add(sourceScaleComboBox);
        panel.add(targetScaleComboBox);
        panel.add(convertButton);

        add(panel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    private double convertTemperature(double temperature, String sourceScale, String targetScale) {
        double convertedTemperature = 0;

        if (sourceScale.equals(targetScale)) {
            return temperature;
        }

        switch (sourceScale) {
            case "Celsius":
                switch (targetScale) {
                    case "Fahrenheit":
                        convertedTemperature = (temperature * 9 / 5) + 32;
                        break;
                    case "Kelvin":
                        convertedTemperature = temperature + 273.15;
                        break;
                }
                break;

            case "Fahrenheit":
                switch (targetScale) {
                    case "Celsius":
                        convertedTemperature = (temperature - 32) * 5 / 9;
                        break;
                    case "Kelvin":
                        convertedTemperature = (temperature - 32) * 5 / 9 + 273.15;
                        break;
                }
                break;

            case "Kelvin":
                switch (targetScale) {
                    case "Celsius":
                        convertedTemperature = temperature - 273.15;
                        break;
                    case "Fahrenheit":
                        convertedTemperature = (temperature - 273.15) * 9 / 5 + 32;
                        break;
                }
                break;
        }

        return convertedTemperature;
    }

    private boolean isValidScale(String scale) {
        return scale.equals("Celsius") || scale.equals("Fahrenheit") || scale.equals("Kelvin");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TemperatureConverterGUI converter = new TemperatureConverterGUI();
                converter.setVisible(true);
            }
        });
    }
}
