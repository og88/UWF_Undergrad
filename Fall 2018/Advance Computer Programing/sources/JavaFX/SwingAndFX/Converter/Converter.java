/*
 * Copyright (c) 2012, 2013 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */



import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.SceneBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;


public class Converter extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private ObservableList<Unit> metricDistances;
    private ObservableList<Unit> usaDistances;
    private DoubleProperty meters = new SimpleDoubleProperty(1);
    

    public Converter() {
        //Create Unit objects for metric distances, and then
        //instantiate a ConversionPanel with these Units.
        metricDistances = FXCollections.observableArrayList(
                new Unit("Centimeters", 0.01),
                new Unit("Meters", 1.0),
                new Unit("Kilometers", 1000.0));
        
        //Create Unit objects for U.S. distances, and then
        //instantiate a ConversionPanel with these Units.
        usaDistances = FXCollections.observableArrayList(
                new Unit("Inches", 0.0254),
                new Unit("Feet", 0.305),
                new Unit("Yards", 0.914),
                new Unit("Miles", 1613.0));
    }
    
    @Override
    public void start(Stage stage) {
        
        StageBuilder.create()
                .title("Converter")
                .scene(SceneBuilder.create()
                    .root(VBoxBuilder.create()
                        .children(
                            new ConversionPanel(
                                "Metric System", metricDistances, meters),
                            new ConversionPanel(
                                "U.S. System", usaDistances, meters))
                        .build())
                    .build())
                .applyTo(stage);
        
        stage.show();
    }
}