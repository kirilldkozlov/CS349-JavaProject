// Used provided starter pack
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.geom.*;

public class CanvasView extends JPanel implements Observer {
    private PaintModel model;
    private boolean startShape = true;
    private boolean createShape = false;
    private int curX = 0;
    private int curY = 0;

    public CanvasView(PaintModel model) {
      // Configure the model connection
      this.model = model;
      model.addObserver(this);

      // Configure JPanel
      this.setBackground(Color.WHITE);
      this.setPreferredSize(new Dimension(650, 500));
      this.setSize(new Dimension(300, 400));
      // JScrollPane scrollPane = new JScrollPane(this);
      //  scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      //  scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


      this.addMouseMotionListener(new MouseAdapter() {
        public void mouseDragged(MouseEvent e) {

          Point held = e.getPoint();

          if (model.currentAction == Action.SQUARE || model.currentAction == Action.CIRCLE || model.currentAction == Action.LINE) {
            if (!createShape) {
              startShape = true;
              createShape = true;
              model.savedX = held.x;
              model.savedY = held.y;
            }

            if(startShape && createShape) {
              switch (model.currentAction)
              {
                case SQUARE:
                  model.shapes.push(new PaintObject(model.savedX, model.savedY, held.x, held.y, model.currentBorder, model.currentColor, ShapeType.SQUARE));
                  break;
                case CIRCLE:
                  model.shapes.push(new PaintObject(model.savedX, model.savedY, held.x, held.y, model.currentBorder, model.currentColor, ShapeType.CIRCLE));
                  break;
                case LINE:
                  model.shapes.push(new PaintObject(model.savedX, model.savedY, held.x, held.y, model.currentBorder, model.currentColor, ShapeType.LINE));
                  break;
              }

              startShape = false;
            }
            else if (createShape) {
              model.shapes.peek().update(model.savedX, model.savedY, held.x, held.y, model.currentBorder, model.currentColor);
            }
          }
          else if (model.currentAction == Action.SELECT && model.selectedObject != null) {
            int changeX = held.x - curX;
            int changeY = held.y - curY;
            model.selectedObject.updatePos(model.selectedObject.rawX1 + changeX, model.selectedObject.rawY1 + changeY, model.selectedObject.rawX2 + changeX, model.selectedObject.rawY2 + changeY);
            curX = curX + changeX;
            curY = curY + changeY;
          }

          model.notifyObservers();
        }
      });

      this.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          Point click = e.getPoint();
          PaintObject clicked = model.returnClicked(click.x, click.y);

          if (clicked != null) {
            switch (model.currentAction)
            {
              case FILL:
                clicked.fill(model.currentColor);
                break;
              case ERASE:
                model.shapes.remove(clicked);
                break;
              case SELECT:
                model.currentColor = clicked.borderColor;
                model.selectedObject = clicked;
                model.currentBorder = clicked.border;
                curX = click.x;
                curY = click.y;
                break;
            }

            model.notifyObservers();
          }
        }


        public void mouseReleased(MouseEvent e) {
          // draw shape
          Point release = e.getPoint();

          if (createShape) {
            model.shapes.peek().update(model.savedX, model.savedY, release.x, release.y, model.currentBorder, model.currentColor);
          }

          model.savedX = 0;
          model.savedY = 0;
          createShape = false;
          model.notifyObservers();
        }
      });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Iterator<PaintObject> obj = this.model.shapes.descendingIterator();
        while(obj.hasNext())
        {
            obj.next().paint(g);
        }
    }

    private void updateSelectedShape() {
      if (model.currentAction == Action.SELECT && model.selectedObject != null) {
        model.selectedObject.border = model.currentBorder;
        model.selectedObject.borderColor = model.currentColor;
        model.selectedObject.selected = true;
      }
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        updateSelectedShape();
        this.repaint();
    }
}
