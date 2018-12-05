import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class ShapeDrawer extends Application {
 
  ArrayList<Line> lines = new ArrayList<Line>();
  ArrayList<Circle> circles = new ArrayList<Circle>();
  
  public void getData()
  {
    try
    {
     Scanner s = new Scanner(new File("shapes.txt"));
     int numLines = s.nextInt();
     for (int i = 0; i < numLines; i++)
     {
       int x1 = s.nextInt();
       int y1 = s.nextInt();
       int x2 = s.nextInt();
       int y2 = s.nextInt();      
       lines.add(new Line(x1,y1,x2,y2));
     }
     int numCircles = s.nextInt();
     for (int i = 0; i < numCircles; i++)
     {
       int x1 = s.nextInt();
       int y1 = s.nextInt();
       int rad = s.nextInt();     
       circles.add(new Circle(x1,y1,rad));
     }   
     s.close();
    }
    catch(Exception e) 
    { 
      System.out.println("load error"); 
    }
  }
 
    public static void main(String[] args) {
        launch(args);
    }
 
   // @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Shape Drawing Test");
        getData();
        Group root = new Group();
        Canvas canvas = new Canvas(340, 340);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        drawShapes(gc);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }    
 
   // https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm
   // http://www.dummies.com/how-to/content/how-to-create-lines-and-shapes-in-javafx.html
 
   public void drawShapes(GraphicsContext gc)
   {  
      for(Line l:lines)
          gc.strokeLine(l.getX1(),l.getY1(),l.getX2(),l.getY2());  
      for(Circle c:circles)      
		  gc.strokeOval(c.getX1(),c.getY1(),c.getRadius(),c.getRadius());
   }   
}