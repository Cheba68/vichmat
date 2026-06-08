package controllers;

import functions.Function;
import functions.FunctionRepository;

import javafx.application.Platform;

import javafx.stage.FileChooser;

import java.io.File;

import utils.FileInputReader;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.collections.FXCollections;

import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import methods.BisectionMethod;
import methods.ChordMethod;
import methods.Method;
import methods.NewtonMethod;
import methods.SecantMethod;
import methods.SimpleIterationMethod;

import models.IterationData;
import models.Result;

import utils.InputParser;
import utils.RootChecker;
import utils.FileExporter;

import systems.SystemFunction;
import systems.SystemRepository;
import systems.SimpleIterationSystemMethod;
import systems.System1;
import systems.System2;
import systems.NewtonSystemMethod;

import models.SystemIterationData;
import models.SystemResult;

import javafx.scene.chart.NumberAxis;

public class MainController {

    private Result lastResult;

    @FXML
    private ComboBox<Method> methodBox;

    @FXML
    private LineChart<Number, Number> systemChart;

    @FXML
    private ComboBox<Function> functionBox;

    @FXML
    private TextField aField;

    @FXML
    private TextField bField;

    @FXML
    private TextField epsField;

    @FXML
    private LineChart<Number, Number> chart;

    @FXML
    private TableView<IterationData> table;

    @FXML
    private TableColumn<IterationData, Integer> stepColumn;

    @FXML
    private TableColumn<IterationData, Double> xColumn;

    @FXML
    private TableColumn<IterationData, Double> fxColumn;

    @FXML
    private TableColumn<IterationData, Double> errorColumn;

    @FXML
    private Label rootLabel;

    @FXML
    private Label fxLabel;

    @FXML
    private Label iterationsLabel;

    @FXML
    private ComboBox<SystemFunction> systemBox;

    @FXML
    private TextField x0Field;

    @FXML
    private TextField y0Field;

    @FXML
    private TextField systemEpsField;

    @FXML
    private TableView<SystemIterationData> systemTable;

    @FXML
    private TableColumn<SystemIterationData, Integer>
            systemStepColumn;

    @FXML
    private TableColumn<SystemIterationData, Double>
            systemXColumn;

    @FXML
    private TableColumn<SystemIterationData, Double>
            systemYColumn;

    @FXML
    private TableColumn<SystemIterationData, Double>
            systemErrorXColumn;

    @FXML
    private TableColumn<SystemIterationData, Double>
            systemErrorYColumn;

    @FXML
    private Label systemResultLabel;

    @FXML
    private Label systemIterationsLabel;

    @FXML
    public void initialize() {

        systemBox.getItems().addAll(
                SystemRepository.getSystems()
        );

        methodBox.getItems().addAll(
            new BisectionMethod(),
            new NewtonMethod(),
            new SecantMethod(),
            new ChordMethod(),
            new SimpleIterationMethod()
        );

        functionBox.getItems().addAll(
                FunctionRepository.getFunctions()
        );

        stepColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getStep()
                ).asObject()
        );

        xColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleDoubleProperty(
                        data.getValue().getX()
                ).asObject()
        );

        fxColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleDoubleProperty(
                        data.getValue().getFx()
                ).asObject()
        );

        errorColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleDoubleProperty(
                        data.getValue().getError()
                ).asObject()
        );
        table.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY
        );

        systemStepColumn.setCellValueFactory(
                data ->
                        new SimpleIntegerProperty(
                                data.getValue().getStep()
                        ).asObject()
        );
        
        systemXColumn.setCellValueFactory(
                data ->
                        new SimpleDoubleProperty(
                                data.getValue().getX()
                        ).asObject()
        );
        
        systemYColumn.setCellValueFactory(
                data ->
                        new SimpleDoubleProperty(
                                data.getValue().getY()
                        ).asObject()
        );
        
        systemErrorXColumn.setCellValueFactory(
                data ->
                        new SimpleDoubleProperty(
                                data.getValue().getErrorX()
                        ).asObject()
        );
        
        systemErrorYColumn.setCellValueFactory(
                data ->
                        new SimpleDoubleProperty(
                                data.getValue().getErrorY()
                        ).asObject()
        );

        systemTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        systemStepColumn.setStyle("-fx-alignment: CENTER;");
        systemXColumn.setStyle("-fx-alignment: CENTER;");
        systemYColumn.setStyle("-fx-alignment: CENTER;");
        systemErrorXColumn.setStyle("-fx-alignment: CENTER;");
        systemErrorYColumn.setStyle("-fx-alignment: CENTER;");
    }

    

    @FXML
    private void solve() {

        try {
            Method method = methodBox.getValue();
            Function function = functionBox.getValue();
            
            double a = InputParser.parse(aField.getText());
            double b = InputParser.parse(bField.getText());
            double eps = InputParser.parse(epsField.getText());
            
            int roots =
                    RootChecker.countRoots(
                            function,
                            a,
                            b
                    );

            if (a >= b) {
                throw new RuntimeException(
                        "Неверный интервал"
                );
            }
                
            if (roots == 0) {
            
                throw new RuntimeException(
                        "На интервале нет корней"
                );
            }
            
            if (roots > 1) {
            
                throw new RuntimeException(
                        "На интервале несколько корней"
                );
            }
            
            


            Result result =
                    method.solve(function, a, b, eps);

            lastResult = result;

            table.setItems(
                    FXCollections.observableArrayList(
                            result.getIterationsTable()
                    )
            );

            rootLabel.setText(
                    "Корень: " + result.getRoot()
            );

            fxLabel.setText(
                    "f(x): " + result.getFunctionValue()
            );

            iterationsLabel.setText(
                    "Итерации: " + result.getIterations()
            );

            drawGraph(function, a, b);

            drawRoot(result.getRoot(),
                result.getFunctionValue());

        } catch (Exception ex) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    private void drawGraph(Function function,
                               double a,
                               double b) {

            chart.getData().clear();

            XYChart.Series<Number, Number> functionSeries =
                    new XYChart.Series<>();

            double step = (b - a) / 1000.0;

            for (double x = a; x <= b; x += step) {

                functionSeries.getData().add(
                        new XYChart.Data<>(
                                x,
                                function.f(x)
                        )
                );
            }

            chart.getData().add(functionSeries);
        }

        private void drawRoot(double x,
                          double y) {

        XYChart.Series<Number, Number> rootSeries =
                new XYChart.Series<>();

        rootSeries.getData().add(
                new XYChart.Data<>(x, y)
        );

        chart.getData().add(rootSeries);
    }

    @FXML
    private void solveSystem() {

        try {

            SystemFunction system =
                    systemBox.getValue();

                

            double x0 =
                    InputParser.parse(
                            x0Field.getText()
                    );

            double y0 =
                    InputParser.parse(
                            y0Field.getText()
                    );

            double eps =
                    InputParser.parse(
                            systemEpsField.getText()
                    );

            NewtonSystemMethod method =
                    new NewtonSystemMethod();

            SystemResult result =
                    method.solve(
                            system,
                            x0,
                            y0,
                            eps
                    );
            if (Math.abs(
                    system.f1(
                            result.getX(),
                            result.getY()
                    )
            ) > eps ||
            
                    Math.abs(
                            system.f2(
                                    result.getX(),
                                    result.getY()
                            )
                    ) > eps) {
                    
                throw new RuntimeException(
                        "Решение системы не прошло проверку"
                );
            }

            systemTable.setItems(
                    FXCollections.observableArrayList(
                            result.getTable()
                    )
            );

            systemResultLabel.setText(
                    "Результат: x = "
                            + format(result.getX())
                            + ", y = "
                            + format(result.getY())
            );

            systemIterationsLabel.setText(
                    "Итерации: "
                            + result.getIterations()
            );

            drawSystemGraph(system);

        } catch (Exception ex) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(
                    ex.getMessage()
            );

            alert.showAndWait();
        }

        
    }

    private String format(double value) {
        return String.format("%.6f", value);
    }

    private void drawSystemGraph(SystemFunction system) {

        systemChart.getData().clear();
    
        if (system instanceof System1) {
    
            drawSystem1();
    
        } else if (system instanceof System2) {
    
            drawSystem2();
        }
    }

    private void drawSystem1() {

    systemChart.getData().clear();

    XYChart.Series<Number, Number> sinSeries =
            new XYChart.Series<>();

    XYChart.Series<Number, Number> verticalSeries =
            new XYChart.Series<>();

    sinSeries.setName("sin(x+1)-1.2");
    verticalSeries.setName("2x+cos(y)=2");

    for (double x = -5; x <= 5; x += 0.01) {

        double y =
                Math.sin(x + 1) - 1.2;

        sinSeries.getData().add(
                new XYChart.Data<>(x, y)
        );
    }

    for (double y = -3; y <= 3; y += 0.02) {

        double x =
                (2 - Math.cos(y)) / 2.0;

        verticalSeries.getData().add(
                new XYChart.Data<>(x, y)
        );
    }

    systemChart.getData().addAll(
            sinSeries,
            verticalSeries
    );

    sinSeries.getNode().setStyle(
            "-fx-stroke-width: 2px;"
    );

    verticalSeries.getNode().setStyle(
            "-fx-stroke: transparent;"
    );
}

    private void drawSystem2() {

    systemChart.getData().clear();

    XYChart.Series<Number, Number> upperCircle =
            new XYChart.Series<>();

    XYChart.Series<Number, Number> lowerCircle =
            new XYChart.Series<>();

    XYChart.Series<Number, Number> line =
            new XYChart.Series<>();


    for (double x = -2; x <= 2; x += 0.01) {

        double y =
                Math.sqrt(4 - x * x);

        upperCircle.getData().add(
                new XYChart.Data<>(x, y)
        );
    }


    for (double x = -2; x <= 2; x += 0.01) {

        double y =
                -Math.sqrt(4 - x * x);

        lowerCircle.getData().add(
                new XYChart.Data<>(x, y)
        );
        lowerCircle.getData().add(new XYChart.Data<>(2,0));
    }

    for (double x = -3; x <= 3; x += 0.01) {

        line.getData().add(
                new XYChart.Data<>(
                        x,
                        x - 1
                )
        );
    }

    systemChart.getData().addAll(
            upperCircle,
            lowerCircle,
            line
    );

    Platform.runLater(() -> {

    upperCircle.getNode().setStyle(
            "-fx-stroke: #1f77b4;"
    );

    lowerCircle.getNode().setStyle(
            "-fx-stroke: #1f77b4;"
    );

    for (var point : upperCircle.getData()) {

        if (point.getNode() != null) {

            point.getNode().setStyle(
                    "-fx-background-color: #1f77b4;"
            );
        }
    }

    for (var point : lowerCircle.getData()) {

        if (point.getNode() != null) {

            point.getNode().setStyle(
                    "-fx-background-color: #1f77b4;"
            );
        }
    }
});
}

    @FXML
    private void loadFromFile() {

        try {

            FileChooser chooser =
                    new FileChooser();

            chooser.setTitle(
                    "Выберите файл"
            );

            File file =
                    chooser.showOpenDialog(null);

            if (file == null) {
                return;
            }

            double[] data =
                    FileInputReader.read(
                            file.getAbsolutePath()
                    );

            aField.setText(
                    String.valueOf(data[0])
            );

            bField.setText(
                    String.valueOf(data[1])
            );

            epsField.setText(
                    String.valueOf(data[2])
            );

        } catch (Exception ex) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(
                    ex.getMessage()
            );

            alert.showAndWait();
        }
    }

    @FXML
    private void saveToFile() {

        try {

            if (lastResult == null) {

                throw new RuntimeException(
                        "Сначала решите уравнение"
                );
            }

            FileExporter.export(
                    lastResult,
                    "result.txt"
            );

            Alert alert =
                    new Alert(Alert.AlertType.INFORMATION);

            alert.setContentText(
                    "Результат сохранён в result.txt"
            );

            alert.showAndWait();

        } catch (Exception ex) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(
                    ex.getMessage()
            );

            alert.showAndWait();
        }
    }
}
