package javafxapplication.fp;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxapplication.fp.model.Order;

/**
 *
 * @author karthik
 */
public class JavaFXApplication extends Application {
    public static Stage stage;
    public static List<Order> ordLi = new ArrayList<Order>();
    public static boolean saved = false;
    public static int count = 0;
    public static int item_cnt = 1;
    
    @Override
    public void start(Stage primary) throws Exception {
        stage = primary;
        Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/FXMLDocument.fxml"));        
        Scene scene = new Scene(root);        
        primary.setScene(scene);
        primary.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
