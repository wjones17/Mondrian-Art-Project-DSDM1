package edu.westga.cs3151.mondrian;

import java.util.Optional;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

/**
 * Class MondrianArt
 * 
 * @author CS3151
 * @version Spring 2022
 */
public class MondrianArt extends Application {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	private int lineWidth;
	private int baseHeight;
	private int baseWidth;
	private Random rand = new Random();

	@Override
	public void start(Stage primaryStage) {
		try {
			this.displayDialog();

			Canvas canvas = new Canvas(WIDTH, HEIGHT);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			this.createGraphics(gc);

			Group group = new Group();
			group.getChildren().add(canvas);
			Scene scene = new Scene(new BorderPane(group), WIDTH, HEIGHT);
			primaryStage.setTitle("Mondrian Art by William Jones");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Entry point
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param args Not used
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Example code displaying a dialog to get user input
	 */
	private void displayDialog() {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setHeaderText(
				"Enter Width, Height, and Line-Width." + System.lineSeparator() + "Width & Height <= 600.");
		dialog.setResizable(true);

		Label label1 = new Label("Base-case Width: ");
		Label label2 = new Label("Base-case Height: ");
		Label label3 = new Label("Line-Width: ");
		TextField widthText = new TextField();
		TextField heightText = new TextField();
		TextField lineWidthText = new TextField();

		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(widthText, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(heightText, 2, 2);
		grid.add(label3, 1, 3);
		grid.add(lineWidthText, 2, 3);
		dialog.getDialogPane().setContent(grid);

		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			this.baseWidth = Integer.parseInt(widthText.getText());
			this.baseHeight = Integer.parseInt(heightText.getText());
			this.lineWidth = Integer.parseInt(lineWidthText.getText());
		}

	}

	private void createGraphics(GraphicsContext gc) {
		Rectangle rect;
		if (this.lineWidth == 1) {
			rect = new Rectangle(-1, -1, HEIGHT + 2, WIDTH + 2);
		} else {
			rect = new Rectangle(-this.lineWidth / 2, -this.lineWidth / 2, HEIGHT + this.lineWidth + 2,
					WIDTH + this.lineWidth +2);
		}
		this.splitAndFillRect(rect, gc);
	}

	/**
	 * splits the given rectangle and draws another line at the split
	 * 
	 * @param rect the rectangle to split
	 * @param gc   the graphics context
	 */
	private void splitAndFillRect(Rectangle rect, GraphicsContext gc) {
		if (rect.getWidth() < this.baseWidth && rect.getHeight() < this.baseHeight) {
			return;
		}
		int randOffset = 0;
		Rectangle r1 = null;
		Rectangle r2 = null;
		double fromX = 0.0;
		double fromY = 0.0;
		double toX = 0.0;
		double toY = 0.0;

		if (rect.getWidth() <= rect.getHeight()) {
			while (randOffset <= 0) {
				randOffset = this.rand.nextInt((int) rect.getHeight()) + this.lineWidth;
			}
			r1 = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), randOffset);
			r2 = new Rectangle(rect.getX(), rect.getY() + randOffset, rect.getWidth(), rect.getHeight() - randOffset);
			fromX = rect.getX() + ((double) this.lineWidth / 2);
			fromY = rect.getY() + randOffset;
			toX = fromX + rect.getWidth();
			toY = fromY;
		} else {
			while (randOffset <= 0) {
				randOffset = this.rand.nextInt((int) rect.getWidth());
			}
			r1 = new Rectangle(rect.getX(), rect.getY(), randOffset, rect.getHeight());
			r2 = new Rectangle(rect.getX() + randOffset, rect.getY(), rect.getWidth() - randOffset, rect.getHeight());
			fromX = rect.getX() + randOffset;
			fromY = rect.getY() + ((double) this.lineWidth / 2);
			toX = fromX;
			toY = rect.getY() + rect.getHeight();
		}

		double gap = (double) this.lineWidth / 2;

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(this.lineWidth);
		gc.setLineCap(StrokeLineCap.BUTT);
		gc.strokeLine(fromX, fromY, toX, toY);
		this.fillInRect(r1, gap, gc);
		this.fillInRect(r2, gap, gc);
		this.splitAndFillRect(r1, gc);
		this.splitAndFillRect(r2, gc);
	}

	private void fillInRect(Rectangle rect, double gap, GraphicsContext gc) {
		int red = this.rand.nextInt(((255 - 150) + 1) + 150);
		int green = this.rand.nextInt(((255 - 100) + 1) + 100);
		int blue = this.rand.nextInt(((255 - 80) + 1) + 80);
		Color randomColor = Color.rgb(red, green, blue);
		int red2 = this.rand.nextInt(((255 - 180) + 1) + 180);
		int green2 = this.rand.nextInt(((255 - 150) + 1) + 150);
		int blue2 = this.rand.nextInt(((255 - 100) + 1) + 100);
		Color randomColor2 = Color.rgb(red2, green2, blue2);
		int red3 = this.rand.nextInt(((255 - 90) + 1) + 90);
		int green3 = this.rand.nextInt(((255 - 75) + 1) + 75);
		int blue3 = this.rand.nextInt(((255 - 80) + 1) + 80);
		Color randomColor3 = Color.rgb(red3, green3, blue3);
		Stop[] stop = { new Stop(0, randomColor), new Stop(0.5, randomColor2), new Stop(1, randomColor3) };
		LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stop);
		gc.setFill(linearGradient);
		gc.fillRect(rect.getX() + gap, rect.getY() + gap, rect.getWidth() - (2 * gap), rect.getHeight() - (2 * gap));
		this.drawRandomCircles(rect, gc, gap);
	}
	
	private void drawRandomCircles(Rectangle rect, GraphicsContext gc, double gap) {
		int red = this.rand.nextInt(((255 - 150) + 1) + 150);
		int green = this.rand.nextInt(((255 - 100) + 1) + 100);
		int blue = this.rand.nextInt(((255 - 80) + 1) + 80);
		Color randomColor = Color.rgb(red, green, blue);
		int red2 = this.rand.nextInt(((255 - 180) + 1) + 180);
		int green2 = this.rand.nextInt(((255 - 150) + 1) + 150);
		int blue2 = this.rand.nextInt(((255 - 100) + 1) + 100);
		Color randomColor2 = Color.rgb(red2, green2, blue2);
		int red3 = this.rand.nextInt(((255 - 90) + 1) + 90);
		int green3 = this.rand.nextInt(((255 - 75) + 1) + 75);
		int blue3 = this.rand.nextInt(((255 - 80) + 1) + 80);
		Color randomColor3 = Color.rgb(red3, green3, blue3);
		Stop[] stop = { new Stop(0, randomColor), new Stop(0.5, randomColor2), new Stop(1, randomColor3) };
		LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stop);
		gc.setFill(linearGradient);
        int randomNumber = this.rand.nextInt(400);
        if (randomNumber % 5 == 0) {
            gc.setLineWidth(this.lineWidth);
            gc.setFill(linearGradient);
            gc.setStroke(Color.BLACK);
            gc.fillOval(rect.getX() + gap, rect.getY() + gap, rect.getWidth() - (2 * gap), rect.getHeight() - (2 * gap));
            gc.strokeOval(rect.getX() + gap, rect.getY() + gap, rect.getWidth() - (2 * gap), rect.getHeight() - (2 * gap));
        }
    }

}
