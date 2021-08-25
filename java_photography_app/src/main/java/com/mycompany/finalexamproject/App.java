/**
* Final Exam Project
* Author: Bogdan Ciobanu
* Project Purpose: Create an application that implements a GUI interface, allows
* the user to input data, manipulates that data using an event handler, displays
* the result, and performs file input and output operations. This program creates
* and manages photography orders.
* Input: The user enters a quantity into each text field and presses the various
* buttons to perform the indicated operations.
* Desired Output: The previous and next buttons will cycle through the photos, 
* the calculate button will display the total in the result label, the save 
* receipt button will display a confirmation, and the display receipt button 
* will display the saved receipt from the text file.
* Variables and Classes: There are six classes, one for the JavaFx application
* and five for the various click event handlers. There are eighteen labels, five
* buttons, six text fields, one text area, and one image viewer. All these are
* organized using hboxes, vboxes, and gridpanes.
* Formulas: total = hours + prints5x7 + prints8x10 + prints16x20 + album + usbDrive
* Testing: When the program is run, the user can use the buttons look through the 
* sample photos. Then the user can input the desired quantities into the text
* fields and pressed the calculate button to display the correct total. If the 
* user is satisfied with the total they can save the receipt to a file and display
* the contents of that file with the display receipt button.
* December 12, 2020
*/

package com.mycompany.finalexamproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class App extends Application 
{   
    public static void main(String[] args) 
    {
        launch(args);
    }
   
    //Define private fields
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private Image image5;
    private ImageView imageView;
    private ArrayList<Image> images;
    private int index = 0;
    private String receipt;
    
    private TextField hoursField;
    private TextField qty5x7Field;
    private TextField qty8x10Field;
    private TextField qty16x20Field;
    private TextField albumPagesField;
    private TextField usbDriveField;
    private Label totalLabel;
    private Label savedLabel;
    private TextArea receiptTextArea;
    
    private final double HOUR_SHOOT = 80;
    private final double PRINT_5X7 = 2;
    private final double PRINT_8X10 = 5;
    private final double PRINT_16X20 = 20;
    private final double ALBUM = 50;
    private final double ALBUM_PAGE = 25;
    private final double USB_DRIVE = 15;
    

    @Override
    public void start(Stage stage) 
    {               
        //Create heading label and set to bold 
        Label samplesLabel = new Label("Sample Photos");
        samplesLabel.setStyle("-fx-font-weight: bold");
        
        //Create ArrayList, add image objects, and create imageview
        images = new ArrayList<>();
        
        image1 = new Image("file:PhotoApp-001.jpg");
        image2 = new Image("file:PhotoApp-002.jpg");
        image3 = new Image("file:PhotoApp-003.jpg");
        image4 = new Image("file:PhotoApp-004.jpg");
        image5 = new Image("file:PhotoApp-005.jpg");
        
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);

        imageView = new ImageView(images.get(index));
        
        //Create previous and next buttons and register event handlers
        Button nextButton = new Button("Next");
        nextButton.setOnAction(new NextButtonClickHandler());
        
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(new PreviousButtonClickHandler());
        
        //Organize controls with hbox and vbox
        HBox hbox1 = new HBox(10, previousButton, nextButton);
        hbox1.setAlignment(Pos.CENTER);
        
        VBox vbox1 = new VBox(10, samplesLabel, imageView, hbox1);
        vbox1.setAlignment(Pos.CENTER);
        
        //Create heading label and set to bold 
        Label shootLabel = new Label("Photo Shoot Pricing");
        shootLabel.setStyle("-fx-font-weight: bold");
        
        //Create labels and textfield
        Label hoursLabel = new Label("Photo Shoot Hours:\n"
                + "(1 Hour = 20 Photos)");
        Label hoursPriceLabel = new Label(String.format("$%,.2f / hour", 
                HOUR_SHOOT));

        hoursField = new TextField();

        //Organize with vbox and gridpane
        GridPane gridpane2 = new GridPane();
        gridpane2.add(hoursLabel, 0, 0);
        gridpane2.add(hoursField, 1, 0);
        gridpane2.add(hoursPriceLabel, 2, 0);
        
        gridpane2.setHgap(30);
        gridpane2.setAlignment(Pos.CENTER);
        
        VBox vbox2 = new VBox(10, shootLabel, gridpane2);
        vbox2.setAlignment(Pos.CENTER);
        
        //Create heading label and set to bold 
        Label printsLabel = new Label("Photo Prints Pricing");
        printsLabel.setStyle("-fx-font-weight: bold");

        //Create labels and textfields
        Label print5x7Label = new Label("5 x 7 Prints: ");
        Label print8x10Label = new Label("8 x 10 Prints: ");
        Label print16x20Label = new Label("16 x 20 Prints: ");
        
        Label price5x7Label = new Label(String.format("$%,.2f each", 
                PRINT_5X7));
        Label price8x10Label = new Label(String.format("$%,.2f each", 
                PRINT_8X10));
        Label price16x20Label = new Label(String.format("$%,.2f each", 
                PRINT_16X20));
        
        qty5x7Field = new TextField();
        qty8x10Field = new TextField();
        qty16x20Field = new TextField();
        
        //Organize with vbox and gridpane
        GridPane gridpane3 = new GridPane();
        gridpane3.add(print5x7Label, 0, 0);
        gridpane3.add(qty5x7Field, 1, 0);
        gridpane3.add(price5x7Label, 2, 0);
        gridpane3.add(print8x10Label, 0, 1);
        gridpane3.add(qty8x10Field, 1, 1);
        gridpane3.add(price8x10Label, 2, 1);
        gridpane3.add(print16x20Label, 0, 2);
        gridpane3.add(qty16x20Field, 1, 2);
        gridpane3.add(price16x20Label, 2, 2);
        
        gridpane3.setHgap(30);
        gridpane3.setVgap(10);
        gridpane3.setAlignment(Pos.CENTER);
        
        VBox vbox3 = new VBox(10, printsLabel, gridpane3);
        vbox3.setAlignment(Pos.CENTER);
        
        //Create heading label and set to bold 
        Label itemsLabel = new Label("Other Items Pricing");
        itemsLabel.setStyle("-fx-font-weight: bold");

        //Create labels and textfields
        Label albumLabel = new Label("Photo Album: ");
        Label usbDriveLabel = new Label("Photos on USB Drive: ");
        
        Label albumPriceLabel = new Label(String.format("$%,.2f + $%,.2f /page",
                ALBUM, ALBUM_PAGE));
        Label drivePriceLabel = new Label(String.format("$%,.2f each", USB_DRIVE));
        
        albumPagesField = new TextField();
        usbDriveField = new TextField();
        
        //Organize with vbox and gridpane
        GridPane gridpane4 = new GridPane();
        gridpane4.add(albumLabel, 0, 0);
        gridpane4.add(albumPagesField, 1, 0);
        gridpane4.add(albumPriceLabel, 2, 0);
        gridpane4.add(usbDriveLabel, 0, 1);
        gridpane4.add(usbDriveField, 1, 1);
        gridpane4.add(drivePriceLabel, 2, 1);
        
        gridpane4.setHgap(30);
        gridpane4.setVgap(10);
        gridpane4.setAlignment(Pos.CENTER);
        
        VBox vbox4 = new VBox(10, itemsLabel, gridpane4);
        vbox4.setAlignment(Pos.CENTER);
        
        //Create total & save labels, and non-editable textarea for receipt
        totalLabel = new Label("Total: $0.00");
        savedLabel = new Label();
        receiptTextArea = new TextArea("");
        receiptTextArea.setEditable(false);
        
        //Create buttons and register event handlers
        Button calcButton = new Button("Calculate Price");
        calcButton.setOnAction(new CalcButtonClickHandler());
        
        Button saveButton = new Button("Save Receipt");
        saveButton.setOnAction(new SaveButtonClickHandler());
        
        Button displayButton = new Button("Display Recepit");
        displayButton.setOnAction(new DisplayButtonClickHandler());

        //Organize controls with hbox and vbox
        HBox hbox5 = new HBox(10, calcButton, saveButton, displayButton);
        hbox5.setAlignment(Pos.CENTER);
        
        VBox vbox5 = new VBox(10, totalLabel, hbox5, savedLabel, receiptTextArea);
        vbox5.setAlignment(Pos.CENTER);
        
        //Create root node
        VBox rootVBox = new VBox(10, vbox1, vbox2, vbox3, vbox4, vbox5);
        rootVBox.setPadding(new Insets(10));
        
        //Create scene, set stage & title, and display window
        Scene scene = new Scene(rootVBox, 500, 800);
        stage.setScene(scene);
        stage.setTitle("Photography Order Manager");
        stage.show();
    }
    
    //Create event handlers
    class NextButtonClickHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if(index < images.size())
            {
                imageView.setImage(images.get(index + 1));
                index++;
            }
        }
    }
    
    class PreviousButtonClickHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if(index > 0)
            {
                imageView.setImage(images.get(index - 1));
                index--;
            }
        }
    }
    
    class CalcButtonClickHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            double hours = 0;
            double prints5x7 = 0;
            double prints8x10 = 0;
            double prints16x20 = 0;
            double album = 0;
            double usbDrive = 0;
            double total = 0;

            //Calculate and add up all field entries
            if(!hoursField.getText().isEmpty())
                hours = Integer.parseInt(hoursField.getText()) * HOUR_SHOOT;
                
            if(!qty5x7Field.getText().isEmpty())
                prints5x7 = Integer.parseInt(qty5x7Field.getText()) * PRINT_5X7;
            
            if(!qty8x10Field.getText().isEmpty())
                prints8x10 = Integer.parseInt(qty8x10Field.getText()) * PRINT_8X10;
            
            if(!qty16x20Field.getText().isEmpty())
                prints16x20 = Integer.parseInt(qty16x20Field.getText()) * PRINT_16X20;
            
            if(!albumPagesField.getText().isEmpty() && 
                    Integer.parseInt(albumPagesField.getText()) != 0)
            {
                album = (Integer.parseInt(albumPagesField.getText()) * ALBUM_PAGE)
                        + ALBUM;
            }
            
            if(!usbDriveField.getText().isEmpty())
                usbDrive = Integer.parseInt(usbDriveField.getText()) * USB_DRIVE;
            
            total = hours + prints5x7 + prints8x10 + prints16x20 + album + 
                    usbDrive;
            
            //Display total in total label
            totalLabel.setText(String.format("Totals: $%,.2f", total));
            
            //Save data to receipt string
            receipt = String.format("Photography Order\n\n"
                    + "Photo Shoot Hours: $%,.2f\n"
                    + "5x7 Prints: $%,.2f\n"
                    + "8x10 Prints: $%,.2f\n"
                    + "16x20 Prints: $%,.2f\n"
                    + "Photo Album: $%,.2f\n"
                    + "USB Drives: $%,.2f\n"
                    + "-------------------\n"
                    + "Total: $%,.2f\n", hours, prints5x7, prints8x10, prints16x20,
                    album, usbDrive, total);
        }
    }
    
    class SaveButtonClickHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            //try-with-resources statement automatically closes the file 
            try (PrintWriter outputFile = new PrintWriter("receipt.txt")) 
            {
                outputFile.println(receipt);
                
                savedLabel.setText("Receipt saved to receipt.txt");
            }
            catch (Exception e)
            {
                receiptTextArea.setText(e.getMessage());
            }
        }
    }
    
    class DisplayButtonClickHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            File file = null;
            Scanner inputFile = null;
            
            try 
            {
                file = new File("receipt.txt");
                inputFile = new Scanner(file);
                
                receiptTextArea.clear();
                
                while (inputFile.hasNext())
                {
                    receiptTextArea.appendText(inputFile.nextLine() + "\n");
                }
            }
            catch (FileNotFoundException e)
            {
                receiptTextArea.setText("Error: No Receipt File Found");
            }
            finally
            {
                inputFile.close();    
            }
        }
    }
}

