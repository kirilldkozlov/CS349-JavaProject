// Used provided starter pack
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

public class View extends JFrame implements Observer {
    private PaintModel model;

    JMenu file;
    JMenuItem newFile, load, save, exit;

    /**
     * Create a new View.
     */
    public View(PaintModel model) {
        // Set up components
        JMenuBar menu = new JMenuBar();
        // tell PaintModel about View.
        model.addObserver(this);
        this.model = model;

        // Build Menu
        newFile = new JMenuItem("New");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        file = new JMenu("File");

        file.add(newFile);
        file.add(load);
        file.add(save);
        file.add(exit);

        menu.add(file);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher( new KeyEventDispatcher() {
          public boolean dispatchKeyEvent(KeyEvent e) {
              if(e.getID() != KeyEvent.KEY_TYPED) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                  if (model.selectedObject != null) {
                    model.selectedObject.selected = false;
                    model.selectedObject = null;
                  }
                  model.notifyObservers();
                }
              }

              return false;
          }
        });

        newFile.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            model.shapes = new ArrayDeque<PaintObject>();
            model.currentColor = Color.BLUE;
            model.currentBorder = 2;
            model.currentAction = Action.SELECT;
            model.selectedObject = null;
            model.savedX = 0;
            model.savedY = 0;

            model.notifyObservers();
          }
        });

        exit.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });

        load.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
              model.shapes = new ArrayDeque<PaintObject>();
              model.currentColor = Color.BLUE;
              model.currentBorder = 2;
              model.currentAction = Action.SELECT;
              model.selectedObject = null;
              model.savedX = 0;
              model.savedY = 0;

              try {
               DataInputStream dataIn = new DataInputStream(new FileInputStream(fileChooser.getSelectedFile()));

               while(dataIn.available()>0) {
                float rawx1 = dataIn.readFloat();
                float rawy1 = dataIn.readFloat();
                float rawx2 = dataIn.readFloat();
                float rawy2 = dataIn.readFloat();
                int ord = dataIn.readInt();
                int border = dataIn.readInt();
                int rgb = dataIn.readInt();
                int brgb = dataIn.readInt();
                boolean fill = dataIn.readBoolean();
                model.shapes.push(new PaintObject(rawx1, rawy1, rawx2, rawy2, border, new Color(rgb), ShapeType.values()[ord]));

                if (brgb != 0) {
                  model.shapes.peek().fill(new Color(brgb));
                }
              }

              dataIn.close();
              }
              catch(Exception ex) {
                System.out.println(ex);
              }
              model.notifyObservers();
            }
          }
        });

        save.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
              try {
                FileOutputStream fileWriter = new FileOutputStream(fileChooser.getSelectedFile() + ".txt");
                DataOutputStream dos = new DataOutputStream(fileWriter);

                Iterator<PaintObject> temp = model.shapes.descendingIterator();
                while(temp.hasNext()) {
                  PaintObject cur = temp.next();
                  dos.writeFloat(cur.rawX1);
                  dos.writeFloat(cur.rawY1);
                  dos.writeFloat(cur.rawX2);
                  dos.writeFloat(cur.rawY2);
                  dos.writeInt(cur.type.ordinal());
                  dos.writeInt(cur.border);
                  dos.writeInt(cur.borderColor.getRGB());
                  if (cur.fill) {
                    dos.writeInt(cur.backgroundColor.getRGB());
                  }
                  else {
                    dos.writeInt(0);
                  }
                  dos.writeBoolean(cur.fill);
                }

                dos.close();
              }
              catch (Exception ex) {

                System.out.println("Error");
              }
            }
          }
        });
        // set the model

        this.setTitle("jPaint");
        this.setJMenuBar(menu);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {

    }
}
