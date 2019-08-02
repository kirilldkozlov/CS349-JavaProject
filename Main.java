import java.awt.*;
import javax.swing.*;
import java.util.*;

// Used provided starter pack
public class Main {
    public static void main(String[] args) {
        // PaintModel
        PaintModel model = new PaintModel();
        
        //View 1
        JFrame view = new View(model);
        // View 2
        CanvasView canvas = new CanvasView(model);
        // View 3
        ToolBarView toolbar = new ToolBarView(model);

        // Layout panel to show views side by side
        JPanel main = new JPanel();
        SpringLayout layout = new SpringLayout();
        main.setLayout(layout);
        main.add(toolbar);
        main.add(canvas);

        // Configure Spring Layout
        layout.putConstraint(SpringLayout.WEST, toolbar,
                            5,
                            SpringLayout.WEST, main);
        layout.putConstraint(SpringLayout.NORTH, toolbar,
                            5,
                            SpringLayout.NORTH, main);

        layout.putConstraint(SpringLayout.WEST, canvas,
                            5,
                            SpringLayout.EAST, toolbar);
        layout.putConstraint(SpringLayout.NORTH, canvas,
                            5,
                            SpringLayout.NORTH, main);
        layout.putConstraint(SpringLayout.EAST, main,
                            5,
                            SpringLayout.EAST, canvas);
        layout.putConstraint(SpringLayout.SOUTH, main,
                            5,
                            SpringLayout.SOUTH, canvas);

        // JFrame initialization
        view.getContentPane().add(main);
        view.setMinimumSize(new Dimension(400, 400));
        view.setSize(800, 600);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.pack();
        view.setVisible(true);
    }
}
