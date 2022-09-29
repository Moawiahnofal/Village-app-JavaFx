package assignment8_000875260;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Expand this FXGUITemplate into your own Paint app. Don't forget to
 * comment according to course commenting standards.
 *
 * @Author Moawiah Nofal - 000875260
 */
public class PaintApp extends Application {

    // TODO: Instance Variables for View Components and Model
    private ArrayList<GeometricObject> shapes = new ArrayList<GeometricObject>();
    GraphicsContext gc;
    Pane root;
    final int radius = 10; // radius circle
    final int margin = 20; // margin from the center
    final int n = 10; // number of squares per row and column
    final int squareSize = (400) / n; // size of square
    int counter = 0;

    Button buttonAddManual;
    Label columnNumberLabel, rowNumberLabel, shapeLabel;
    TextField columnNumberTextField, rowNumberTextField, shapeTextField;

    /**
     * This is where you create your components and the model and add event
     * handlers.
     *
     * @param stage The main stage
     * @throws Exception
     */


    //mouse handler to handle the event of the mouse click
    private void pressHandler(MouseEvent me) {
        counter++;
        int row = (int) (me.getY() / squareSize);
        int column = (int) (me.getX() / squareSize);
        int x = column * squareSize + margin;
        int y = row * squareSize + margin;

        if (row >= 0 && row < 10 && column >= 0 && column < 10) {
            if (checkIfLocationTaken(x, y)) {
                showErrorMsg("Invalid location! this row and column is already taken. Please enter another one.");
            } else {
                if (counter % 2 == 0) {
                    Circle circle = new Circle(x, y, Color.DEEPPINK, radius);
                    circle.draw(gc);
                    shapes.add(circle);
                } else {
                    Square square = new Square(x, y, Color.GREENYELLOW, radius);
                    square.draw(gc);
                    shapes.add(square);
                }
            }
        }
    }


    // button handler for adding shape using GUI
    private void ButtonHandlerAddShape(ActionEvent actionEvent) {
        // this try/ catch will catch an exception if user forgot to input column or/and row value
        try {
            int columnValue = Integer.parseInt(columnNumberTextField.getText()); // get value entered in columnNumberTextField
            int rowValue = Integer.parseInt(rowNumberTextField.getText());       //get value entered in rowNumberTextField
            int x = columnValue * squareSize + margin;
            int y = rowValue * squareSize + margin;
            String shapeValue = shapeTextField.getText();                        // get value entered in shapeTextField

            // check for error entered values
            if (columnValue >= 0 && columnValue < 10 && rowValue >= 0 && rowValue < 10) {
                if (shapeValue.equalsIgnoreCase("square")) {
                    if (checkIfLocationTaken(x, y)) {
                        showErrorMsg("Invalid location! this row and column is already taken. Please enter another one.");
                    } else {
                        Square square = new Square(x, y, Color.GREENYELLOW, radius);
                        square.draw(gc);
                        shapes.add(square);
                    }
                } else if (shapeValue.equalsIgnoreCase("circle")) {
                    if (checkIfLocationTaken(x, y)) {
                        showErrorMsg("Invalid location! this row and column is already taken. Please enter another one.");
                    } else {
                        Circle circle = new Circle(x, y, Color.DEEPPINK, radius);
                        circle.draw(gc);
                        shapes.add(circle);
                    }
                } else {
                    showErrorMsg("Invalid shape! Please input either square or circle.");
                }
            } else {
                showErrorMsg("Invalid column/row number! please input a number between 0-9.");
            }
        } catch (Exception exception) {
            showErrorMsg(exception.getMessage().toString());
        }
    }


    // the method is used whenever a new shape is added into my space to check if the exact coordinate(x/y) has a shape inside it
    private boolean checkIfLocationTaken(int x, int y) {
        for (GeometricObject shape : shapes) {
            if (shape.getX() == x && shape.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void reload() {
        root.getChildren().clear();
    }

    @Override
    public void start(Stage stage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root); // set the size here
        Canvas canvas = new Canvas(400, 700); // Set canvas Size in Pixels
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        stage.setTitle("FX GUI Template"); // set the window title here
        stage.setScene(scene);

        // add columns
        for (int col = 0; col < n; col++) {
            int x = col * squareSize;
            Line line = new Line(x, 0, x, 400);
            root.getChildren().add(line);
        }

        //add rows
        for (int row = 0; row < n; row++) {
            int y = row * squareSize;
            Line line = new Line(0, y, 400, y);
            root.getChildren().add(line);
        }

        //placing random circles and squares into  the table
        int x1 = 2 * squareSize + margin;
        int y1 = 5 * squareSize + margin;
        int x2 = 3 * squareSize + margin;
        int y2 = 5 * squareSize + margin;
        int x3 = 4 * squareSize + margin;
        int y3 = 6 * squareSize + margin;
        int x4 = 5 * squareSize + margin;
        int y4 = 2 * squareSize + margin;
        int x5 = 2 * squareSize + margin;
        int y5 = 2 * squareSize + margin;
        Circle circle = new Circle(x1, y1, Color.DEEPPINK, radius);
        circle.draw(gc);
        Circle circle2 = new Circle(x2, y2, Color.DEEPPINK, radius);
        circle2.draw(gc);
        Circle circle3 = new Circle(x3, y3, Color.DEEPPINK, radius);
        circle3.draw(gc);
        Square square = new Square(x4, y4, Color.GREENYELLOW, radius);
        square.draw(gc);
        Square square2 = new Square(x5, y5, Color.GREENYELLOW, radius);
        square2.draw(gc);
        //adding shapes into my array list of shapes for future use
        shapes.add(circle);
        shapes.add(circle2);
        shapes.add(circle3);
        shapes.add(square);
        shapes.add(square2);


        // add GUI views for adding shapes manually
        buttonAddManual = new Button("Add Now!");
        columnNumberLabel = new Label("Column Number(0-9): ");
        rowNumberLabel = new Label("Row Number(0-9): ");
        shapeLabel = new Label("Shape(Square/ Circle): ");
        shapeTextField = new TextField("");
        columnNumberTextField = new TextField("");
        rowNumberTextField = new TextField("");

        // setting the styles and location
        columnNumberLabel.relocate(10, 450);
        columnNumberLabel.setFont(new Font("Courier New", 15));
        columnNumberLabel.setStyle("-fx-wrap-text:true;-fx-text-fill:darkblue;-fx-alignment:left; -fx-cursor: crosshair");
        columnNumberLabel.setPrefSize(600, 50);

        rowNumberLabel.relocate(10, 500);
        rowNumberLabel.setFont(new Font("Courier New", 15));
        rowNumberLabel.setStyle("-fx-wrap-text:true;-fx-text-fill:darkblue;-fx-alignment:left; -fx-cursor: crosshair");
        rowNumberLabel.setPrefSize(600, 50);

        shapeLabel.relocate(10, 550);
        shapeLabel.setFont(new Font("Courier New", 15));
        shapeLabel.setStyle("-fx-wrap-text:true;-fx-text-fill:darkblue;-fx-alignment:left; -fx-cursor: crosshair");
        shapeLabel.setPrefSize(600, 50);

        columnNumberTextField.relocate(210, 460);
        rowNumberTextField.relocate(210, 510);
        shapeTextField.relocate(210, 560);

        buttonAddManual.relocate(10, 600);
        buttonAddManual.setOnAction(this::ButtonHandlerAddShape);

        // adding children into the root
        root.getChildren().addAll(buttonAddManual, columnNumberLabel, rowNumberLabel, columnNumberTextField, rowNumberTextField, shapeLabel, shapeTextField);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressHandler);
        stage.show();
    }

    // this method handles all the errors/ exceptions  in the project
    private void showErrorMsg(String errorMessage) {
        new Alert(Alert.AlertType.WARNING, errorMessage).showAndWait();
    }

    /**
     * Make no changes here.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
