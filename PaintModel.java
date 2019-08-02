// Used provided starter pack
import java.io.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.awt.geom.Point2D;

public class PaintModel {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    public Color currentColor;
    public int currentBorder;
    public Deque<PaintObject> shapes;
    public int savedX, savedY;
    public Action currentAction;
    public PaintObject selectedObject;

    /**
     * Create a new model.
     */
    public PaintModel() {
        this.observers = new ArrayList<Observer>();
        this.shapes = new ArrayDeque<PaintObject>();
        this.currentColor = Color.BLUE;
        this.currentBorder = 2;
        this.currentAction = Action.SELECT;
    }

    public PaintObject returnClicked(float x, float y) {
      for (PaintObject temp : this.shapes) {
        if (temp.type == ShapeType.CIRCLE) {
          float circleMarker = Math.min((temp.x2 - temp.x1), (temp.y2 - temp.y1));
          float radiusX = temp.x1 + circleMarker/2;
          float radiusY = temp.y1 + circleMarker/2;
          float radiusComp = circleMarker/2;

          if (Math.abs(x - radiusX) < radiusComp && Math.abs(y - radiusY) < radiusComp) {
            return temp;
          }
        }
        else if (temp.type == ShapeType.LINE) {
          Point2D.Float line1 = new Point2D.Float(temp.rawX1, temp.rawY1);
          Point2D.Float line2 = new Point2D.Float(temp.rawX2, temp.rawY2);
          Point2D.Float point = new Point2D.Float(x, y);

          if (lineToPoint(point, line1, line2) < 5) {
            return temp;
          }
        }
        else if (temp.type == ShapeType.SQUARE) {
          if ((x >= temp.x1 && x <= temp.x2) && (y >= temp.y1 && y <= temp.y2)) {
            return temp;
          }
        }
      }

      return null;
    }

    private float sqr(float x) {
      return x * x ;
    }

    private float dist2(Point2D.Float v, Point2D.Float w) {
      return sqr(v.x - w.x) + sqr(v.y - w.y);
    }

    // Based on adapted code
    private float lineToPoint(Point2D.Float p, Point2D.Float v, Point2D.Float w) {
      float l2 = dist2(v, w);

      if (l2 == 0) {
        return dist2(p, v);
      }

      float t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
      t = Math.max(0, Math.min(1, t));
      Point2D.Float temp = new Point2D.Float(v.x + t * (w.x - v.x), v.y + t * (w.y - v.y));
      return dist2(p, temp);
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }
}
