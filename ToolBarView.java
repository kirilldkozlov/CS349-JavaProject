// Used provided starter pack
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.border.*;

public class ToolBarView extends JPanel implements Observer {
    private PaintModel model;
    Border thickBorder;
    Border redBorder;
    Border emptyBorder;
    ButtonGroup linesGroup;
    ButtonGroup colorGroup;
    JLabel curColor;
    JToggleButton blue, orange, yellow, red, pink, green;
    JToggleButton fat, med, thin;
    JButton colorChooser;

    public ToolBarView(PaintModel model) {
        // Configure the model connection
        this.model = model;
        model.addObserver(this);

        // Look & feel adjustments for buttons
        UIManager.put("ToggleButton.select", Color.WHITE);
        thickBorder = new LineBorder(Color.BLACK, 2);
        redBorder = new LineBorder(Color.RED, 1);
        emptyBorder = BorderFactory.createEmptyBorder();

        // Color selector buttons configuration
        red = new JToggleButton();
        red.setMnemonic(KeyEvent.VK_A);
        red.setActionCommand("Red");
        red.setSelected(true);
        red.setBackground(Color.RED);
        red.setContentAreaFilled(false);
        red.setOpaque(true);
        red.setPreferredSize(new Dimension(45, 25));

        blue = new JToggleButton();
        blue.setMnemonic(KeyEvent.VK_E);
        blue.setActionCommand("Blue");
        blue.setBackground(Color.BLUE);
        blue.setContentAreaFilled(false);
        blue.setOpaque(true);
        blue.setPreferredSize(new Dimension(45, 25));
        blue.setSelected(true);
        blue.setBorder(thickBorder);

        orange = new JToggleButton();
        orange.setMnemonic(KeyEvent.VK_W);
        orange.setActionCommand("Orange");
        orange.setBackground(Color.ORANGE);
        orange.setContentAreaFilled(false);
        orange.setOpaque(true);
        orange.setPreferredSize(new Dimension(45, 25));

        yellow = new JToggleButton();
        yellow.setMnemonic(KeyEvent.VK_H);
        yellow.setActionCommand("Yellow");
        yellow.setBackground(Color.YELLOW);
        yellow.setContentAreaFilled(false);
        yellow.setOpaque(true);
        yellow.setPreferredSize(new Dimension(45, 25));

        green = new JToggleButton();
        green.setMnemonic(KeyEvent.VK_K);
        green.setActionCommand("Green");
        green.setBackground(Color.GREEN);
        green.setContentAreaFilled(false);
        green.setOpaque(true);
        green.setPreferredSize(new Dimension(45, 25));

        pink = new JToggleButton();
        pink.setMnemonic(KeyEvent.VK_S);
        pink.setActionCommand("Pink");
        pink.setBackground(Color.PINK);
        pink.setContentAreaFilled(false);
        pink.setOpaque(true);
        pink.setPreferredSize(new Dimension(45, 25));

        // Toolbar button configuration
        JToggleButton select = new JToggleButton();
        select.setMnemonic(KeyEvent.VK_B);
        select.setActionCommand("Selection");
        select.setSelected(true);
        select.setFocusPainted(false);
        select.setPreferredSize(new Dimension(45, 25));

        JToggleButton erase = new JToggleButton();
        erase.setMnemonic(KeyEvent.VK_C);
        erase.setActionCommand("Erase");
        erase.setFocusPainted(false);
        erase.setPreferredSize(new Dimension(45, 25));

        JToggleButton line = new JToggleButton();
        line.setMnemonic(KeyEvent.VK_D);
        line.setActionCommand("Line");
        line.setFocusPainted(false);
        line.setPreferredSize(new Dimension(45, 25));

        JToggleButton circle = new JToggleButton();
        circle.setMnemonic(KeyEvent.VK_R);
        circle.setActionCommand("Circle");
        circle.setFocusPainted(false);
        circle.setPreferredSize(new Dimension(45, 25));

        JToggleButton rectangle = new JToggleButton();
        rectangle.setMnemonic(KeyEvent.VK_P);
        rectangle.setActionCommand("Rectangle");
        rectangle.setFocusPainted(false);
        rectangle.setPreferredSize(new Dimension(45, 25));

        JToggleButton fill = new JToggleButton();
        fill.setMnemonic(KeyEvent.VK_F);
        fill.setActionCommand("Fill");
        fill.setFocusPainted(false);
        fill.setPreferredSize(new Dimension(45, 25));

        // Load icons for toolbar buttons
        try {
          Image selectImg = ImageIO.read(getClass().getResource("Resources/select.png"));
          select.setIcon(new ImageIcon(selectImg));

          Image eraseImg = ImageIO.read(getClass().getResource("Resources/eraser.png"));
          erase.setIcon(new ImageIcon(eraseImg));

          Image lineImg = ImageIO.read(getClass().getResource("Resources/edit.png"));
          line.setIcon(new ImageIcon(lineImg));

          Image circleImg = ImageIO.read(getClass().getResource("Resources/circle.png"));
          circle.setIcon(new ImageIcon(circleImg));

          Image rectangleImg = ImageIO.read(getClass().getResource("Resources/rectangle.png"));
          rectangle.setIcon(new ImageIcon(rectangleImg));

          Image fillImg = ImageIO.read(getClass().getResource("Resources/fill.png"));
          fill.setIcon(new ImageIcon(fillImg));
        } catch (Exception ex) {
          System.out.println(ex);
        }

        //Group the tools buttons.
        ButtonGroup toolsGroup = new ButtonGroup();
        toolsGroup.add(select);
        toolsGroup.add(erase);
        toolsGroup.add(line);
        toolsGroup.add(fill);
        toolsGroup.add(circle);
        toolsGroup.add(rectangle);

        //Group the color buttons.
        colorGroup = new ButtonGroup();
        colorGroup.add(blue);
        colorGroup.add(red);
        colorGroup.add(orange);
        colorGroup.add(yellow);
        colorGroup.add(green);
        colorGroup.add(pink);

        // Tool buttons layout
        JPanel toolsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        toolsPanel.add(select);
        toolsPanel.add(erase);
        toolsPanel.add(line);
        toolsPanel.add(fill);
        toolsPanel.add(circle);
        toolsPanel.add(rectangle);

        // Color buttons layout
        JPanel colorPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        colorPanel.add(blue);
        colorPanel.add(red);
        colorPanel.add(orange);
        colorPanel.add(yellow);
        colorPanel.add(green);
        colorPanel.add(pink);

        // Custom color chooser
        colorChooser = new JButton("Custom color");
        colorChooser.setSize(65, 35);

        // Current color
        JLabel colorInfo = new JLabel("Current color:");
        curColor = new JLabel();
        curColor.setBackground(Color.BLUE);
        curColor.setOpaque(true);
        curColor.setPreferredSize(new Dimension(45, 25));

        JPanel colorInfoCard = new JPanel();
        colorInfoCard.setLayout(new GridLayout(2, 1, 3, 3));
        colorInfoCard.add(colorInfo);
        colorInfoCard.add(curColor);

        // Action listener for colour selecter
        this.setPreferredSize(new Dimension(150, 400));
        colorChooser.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Color color = JColorChooser.showDialog(null, "Choose a color", Color.blue);
            model.currentColor = color;
            model.notifyObservers();
          }
        });

        this.add(toolsPanel);

        JPanel colorControls = new JPanel();
        BoxLayout boxlayout = new BoxLayout(colorControls, BoxLayout.Y_AXIS);
        colorControls.setLayout(boxlayout);

        colorInfoCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorControls.add(colorInfoCard);
        colorControls.add(Box.createRigidArea(new Dimension(0, 10)));
        colorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorControls.add(colorPanel);
        colorControls.add(Box.createRigidArea(new Dimension(0, 10)));
        colorChooser.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorControls.add(colorChooser);
        colorControls.add(Box.createRigidArea(new Dimension(0, 8)));
        this.add(colorControls);

        thin = new LineSelectButton(2);
        thin.setActionCommand("Thin");
        thin.setFocusPainted(false);
        thin.setPreferredSize(new Dimension(75, 25));
        thin.setSelected(true);
        thin.setBorder(redBorder);
        thin.setBackground(Color.WHITE);

        med = new LineSelectButton(4);
        med.setActionCommand("Med");
        med.setFocusPainted(false);
        med.setPreferredSize(new Dimension(75, 25));
        med.setBackground(Color.WHITE);

        fat = new LineSelectButton(6);
        fat.setActionCommand("Fat");
        fat.setFocusPainted(false);
        fat.setPreferredSize(new Dimension(75, 25));
        fat.setBackground(Color.WHITE);

        //Group the color buttons.
        linesGroup = new ButtonGroup();
        linesGroup.add(thin);
        linesGroup.add(med);
        linesGroup.add(fat);

        JPanel linesCard = new JPanel();
        linesCard.setLayout(new GridLayout(3, 1, 0, 0));
        linesCard.add(thin);
        linesCard.add(med);
        linesCard.add(fat);

        this.add(linesCard);

        // Start condition
        toggleGroup(colorGroup, false);
        toggleGroup(linesGroup, false);
        colorChooser.setEnabled(false);

        //Register a listener for the radio buttons.
        select.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (select.getModel().isSelected()){
              toggleGroup(colorGroup, false);
              toggleGroup(linesGroup, false);
              colorChooser.setEnabled(true);
              model.currentAction = Action.SELECT;
              model.notifyObservers();
            }
          }
        });
        fill.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (fill.getModel().isSelected()){
              if (model.selectedObject != null) {
                model.selectedObject.selected = false;
                model.selectedObject = null;
              }
              toggleGroup(colorGroup, true);
              toggleGroup(linesGroup, false);
              colorChooser.setEnabled(true);
              model.currentAction = Action.FILL;
              model.notifyObservers();
            }
          }
        });
        erase.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (erase.getModel().isSelected()){
              if (model.selectedObject != null) {
                model.selectedObject.selected = false;
                model.selectedObject = null;
              }
              toggleGroup(colorGroup, false);
              toggleGroup(linesGroup, false);
              colorChooser.setEnabled(false);
              model.currentAction = Action.ERASE;
              model.notifyObservers();
            }
          }
        });
        line.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (line.getModel().isSelected()){
              if (model.selectedObject != null) {
                model.selectedObject.selected = false;
                model.selectedObject = null;
              }
              toggleGroup(colorGroup, true);
              toggleGroup(linesGroup, true);
              colorChooser.setEnabled(true);
              model.currentAction = Action.LINE;
              model.notifyObservers();
            }
          }
        });
        circle.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (circle.getModel().isSelected()){
              if (model.selectedObject != null) {
                model.selectedObject.selected = false;
                model.selectedObject = null;
              }
              toggleGroup(colorGroup, true);
              toggleGroup(linesGroup, true);
              colorChooser.setEnabled(true);
              model.currentAction = Action.CIRCLE;
              model.notifyObservers();
            }
          }
        });
        rectangle.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (rectangle.getModel().isSelected()){
              if (model.selectedObject != null) {
                model.selectedObject.selected = false;
                model.selectedObject = null;
              }

              toggleGroup(colorGroup, true);
              toggleGroup(linesGroup, true);
              colorChooser.setEnabled(true);
              model.currentAction = Action.SQUARE;
              model.notifyObservers();
            }
          }
        });


        for (Enumeration<AbstractButton> buttons = colorGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            button.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                toggleColorButtons();
              }
            });
        }

        for (Enumeration<AbstractButton> buttons = linesGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            button.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                toggleLineButtons();
              }
            });
        }
    }

    private void toggleColorButtons() {
      for (Enumeration<AbstractButton> buttons = colorGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
            button.setBorder(thickBorder);

            if (button.getActionCommand() == "Blue") {
              model.currentColor = Color.BLUE;
            }
            else if (button.getActionCommand() == "Orange") {
              model.currentColor = Color.ORANGE;
            }
            else if (button.getActionCommand() == "Yellow") {
              model.currentColor = Color.YELLOW;
            }
            else if (button.getActionCommand() == "Red") {
              model.currentColor = Color.RED;
            }
            else if (button.getActionCommand() == "Pink") {
              model.currentColor = Color.PINK;
            }
            else if (button.getActionCommand() == "Green") {
              model.currentColor = Color.GREEN;
            }

            model.notifyObservers();
          }
          else {
            button.setBorder(emptyBorder);
          }
      }
    }

    private void toggleLineButtons() {
      for (Enumeration<AbstractButton> buttons = linesGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
            if (button.getActionCommand() == "Fat") {
              model.currentBorder = 6;
            }
            else if (button.getActionCommand() == "Med") {
              model.currentBorder = 4;
            }
            else if (button.getActionCommand() == "Thin") {
              model.currentBorder = 2;
            }

            button.setBorder(redBorder);
            model.notifyObservers();
          }
          else {
            button.setBorder(emptyBorder);
          }
      }
    }

    private void updateLineColorButtons() {
      if (model.currentColor.getRGB() == (Color.BLUE).getRGB()) {
        blue.setSelected(true);
      }
      else if (model.currentColor.getRGB() == (Color.RED).getRGB()) {
        red.setSelected(true);
      }
      else if (model.currentColor.getRGB() == (Color.YELLOW).getRGB()) {
        yellow.setSelected(true);
      }
      else if (model.currentColor.getRGB() == (Color.PINK).getRGB()) {
        pink.setSelected(true);
      }
      else if (model.currentColor.getRGB() == (Color.GREEN).getRGB()) {
        green.setSelected(true);
      }
      else if (model.currentColor.getRGB() == (Color.ORANGE).getRGB()) {
        orange.setSelected(true);
      }


      if (model.currentBorder == 2) {
        thin.setSelected(true);
      }
      else if (model.currentBorder == 4) {
        med.setSelected(true);
      }
      else if (model.currentBorder == 6) {
        fat.setSelected(true);
      }

      for (Enumeration<AbstractButton> buttons = linesGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
            button.setBorder(redBorder);
          }
          else {
            button.setBorder(emptyBorder);
          }
      }

      for (Enumeration<AbstractButton> buttons = colorGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
            button.setBorder(thickBorder);
          }
          else {
            button.setBorder(emptyBorder);
          }
      }
    }

    private void selectedObject() {
      if (model.selectedObject != null && model.currentAction == Action.SELECT) {
        toggleGroup(colorGroup, true);
        toggleGroup(linesGroup, true);
        colorChooser.setEnabled(true);
      }
      else if (model.currentAction == Action.SELECT) {
        toggleGroup(colorGroup, false);
        toggleGroup(linesGroup, false);
        colorChooser.setEnabled(false);
      }
    }

    private void toggleGroup(ButtonGroup group, boolean status) {
      Enumeration<AbstractButton> buttons = group.getElements();
      while (buttons.hasMoreElements()) {
          buttons.nextElement().setEnabled(status);
      }
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        curColor.setBackground(model.currentColor);
        selectedObject();
        updateLineColorButtons();
    }
}
