/*
 * Copyright (c) 2012, 2013 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */



import java.text.NumberFormat;
import javafx.beans.InvalidationListener;                          
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.util.StringConverter;


public class ConversionPanel extends TitledPane {
    
    final static int MAX = 10000;
    
    private ComboBox<Unit> comboBox;
    private Slider slider;
    private TextField textField;
    private DoubleProperty meters;
    private NumberFormat numberFormat;
    
    {
        //Create the text field format, and then the text field.
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
    }
    
    private InvalidationListener fromMeters = new InvalidationListener() {

        @Override
        public void invalidated(Observable arg0) {
            if (!textField.isFocused()) {
                textField.setText(numberFormat.format(meters.get() / getMultiplier()));
            }
        }
    };

    private InvalidationListener toMeters = new InvalidationListener() {

        @Override
        public void invalidated(Observable arg0) {
            if (!textField.isFocused()) {
                return;
            }
            try {
                meters.set(numberFormat.parse(textField.getText()).doubleValue() * getMultiplier());
            } catch (Exception ignored) {
            }
        }
    };

    public ConversionPanel(String title, ObservableList<Unit> units, DoubleProperty meters) {
        setText(title);
        setCollapsible(false);
        setContent(HBoxBuilder.create()
                .children(
                    VBoxBuilder.create()
                        .children(
                            textField = TextFieldBuilder.create()
                                .build(), 
                            slider = SliderBuilder.create()
                                .max(MAX)
                                .build()
                        )
                        .build(),
                    comboBox = ComboBoxBuilder.<Unit>create()
                        .items(units)
                        .converter(new StringConverter<Unit>() {

                            @Override
                            public String toString(Unit t) {
                                return t.description;
                            }

                            @Override
                            public Unit fromString(String string) {
                                throw new UnsupportedOperationException("Not supported yet.");
                            }
                        })
                        .build()
                )
                .build());
        this.meters = meters;
        
        comboBox.getSelectionModel().select(0);
        meters.addListener(fromMeters);
        comboBox.valueProperty().addListener(fromMeters);
        textField.textProperty().addListener(toMeters);
        fromMeters.invalidated(null);
        
        slider.valueProperty().bindBidirectional(meters);
    }
    
    /**
     * Returns the multiplier (units/meter) for the currently
     * selected unit of measurement.
     */
    public double getMultiplier() {
        return comboBox.getValue().multiplier;
    }    
}