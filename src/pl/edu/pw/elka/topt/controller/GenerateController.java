package pl.edu.pw.elka.topt.controller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import pl.edu.pw.elka.topt.model.Modulation;
import pl.edu.pw.elka.topt.service.GenerateService;

import java.util.Map;

public class GenerateController {

    @FXML
    private Slider distanceSlider;

    @FXML
    private Slider minSigmaSlider;

    @FXML
    private Slider maxSigmaSlider;

    @FXML
    private Label distanceLabel;

    @FXML
    private Label minSigmaLabel;

    @FXML
    private Label maxSigmaLabel;

    @FXML
    private RadioButton qam4;

    @FXML
    private RadioButton qam16;

    @FXML
    private RadioButton qam64;

    @FXML
    private RadioButton qam256;

    @FXML
    private Button generateButton;

    @FXML
    private LineChart lineChart;


    public void initialize() {
        distanceLabel.setText(String.format("%.2f", distanceSlider.getValue()));
        minSigmaLabel.setText(String.format("%.2f", minSigmaSlider.getValue()));
        maxSigmaLabel.setText(String.format("%.2f", maxSigmaSlider.getValue()));
        distanceSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue,
                                                    Number newValue) -> {
            distanceLabel.setText((String.format("%.2f", newValue)));
        });
        minSigmaSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue,
                                                    Number newValue) -> {
            minSigmaLabel.setText((String.format("%.2f", newValue)));
            if (newValue.doubleValue() + 0.1 > maxSigmaSlider.getValue()) {
                maxSigmaSlider.setValue(newValue.doubleValue() + 0.1);
            }
        });
        maxSigmaSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue,
                                                    Number newValue) -> {
            maxSigmaLabel.setText((String.format("%.2f", newValue)));
            if (newValue.doubleValue() - 0.1 < minSigmaSlider.getValue()) {
                minSigmaSlider.setValue(newValue.doubleValue() - 0.1);
            }
        });
    }

    @FXML
    private void generate() {
        generateButton.setDisable(true);
        generateButton.setText("Czekaj...");
        Double distance = new Double(distanceLabel.getText().replace(',', '.'));
        Double minDisruption = new Double(minSigmaLabel.getText().replace(',', '.'));
        Double maxDisruption = new Double(maxSigmaLabel.getText().replace(',', '.'));
        Modulation modulation;
        String modulationString;
        if (qam4.isSelected()) {
            modulation = Modulation.QAM4;
            modulationString = qam4.getText();
        } else if (qam16.isSelected()) {
            modulation = Modulation.QAM16;
            modulationString = qam16.getText();
        } else if (qam64.isSelected()) {
            modulation = Modulation.QAM64;
            modulationString = qam64.getText();
        } else {
            modulation = Modulation.QAM256;
            modulationString = qam256.getText();
        }
        System.out.println(String.format("\nGenerate: distance[%s], minSigma[%s], maxSigma[%s], modulation[%s]",
                distanceLabel.getText(), minSigmaLabel.getText(), maxSigmaLabel.getText(), modulationString));
        new Thread() {
            @Override
            public void run() {
                GenerateService generateService = new GenerateService();
                Map<Double, Double> results = generateService.generate(distance, minDisruption, maxDisruption,
                        modulation);
                Platform.runLater(() -> {
                    LineChart.Series series = new LineChart.Series();
                    series.setName(String.format("Odległość symboli: %s, Minimalna moc zakłóceń: %s, " +
                                    "Maksymalna moc zakłóceń: %s, Modulacja: %s",
                            distanceLabel.getText(), minSigmaLabel.getText(), maxSigmaLabel.getText(),
                            modulationString));
                    for (Map.Entry<Double, Double> result : results.entrySet()) {
                        System.out.println(String.format("MER[%f], BER[%f], MER[%f]db, BER[%f]db",
                                result.getKey(), result.getValue(), 10 * Math.log10(result.getKey()),
                                10 * Math.log10(result.getValue())));
                        series.getData().add(new LineChart.Data<>(10 * Math.log10(result.getKey()),
                                10 * Math.log10(result.getValue())));
                    }
                    lineChart.getData().add(series);
                    generateButton.setDisable(false);
                    generateButton.setText("Generuj");
                });
            }
        }.start();
    }

    @FXML
    private void clear() {
        lineChart.getData().clear();
    }
}
