import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

// This is the PaintObject which represents each shape on the panel
public class PaintObject {
    public float x1, y1, x2, y2;
    public float rawX1, rawY1, rawX2, rawY2;
    public ShapeType type;

    public int border;
    public Color borderColor;
    public Color backgroundColor;
    public boolean fill = false;
    public boolean selected = false;

    PaintObject(float x1, float y1, float x2, float y2, int border, Color borderColor, ShapeType type) {
      this.rawX1 = x1;
      this.rawX2 = x2;
      this.rawY1 = y1;
      this.rawY2 = y2;

      if (x1 < x2) {
        this.x1 = x1;
        this.x2 = x2;
      }
      else {
        this.x1 = x2;
        this.x2 = x1;
      }

      if (y1 < y2) {
        this.y1 = y1;
        this.y2 = y2;
      }
      else {
        this.y1 = y2;
        this.y2 = y1;
      }

      int width = (int)this.x2 - (int)this.x1;
      int height = (int)this.y2 -(int)this.y1;

      this.border = border;
      this.borderColor = borderColor;
      this.type = type;
      this.backgroundColor = Color.WHITE;
    }

    public void update(float x1, float y1, float x2, float y2, int border, Color borderColor) {
      this.rawX1 = x1;
      this.rawX2 = x2;
      this.rawY1 = y1;
      this.rawY2 = y2;

      if (x1 < x2) {
        this.x1 = x1;
        this.x2 = x2;
      }
      else {
        this.x1 = x2;
        this.x2 = x1;
      }

      if (y1 < y2) {
        this.y1 = y1;
        this.y2 = y2;
      }
      else {
        this.y1 = y2;
        this.y2 = y1;
      }

      this.border = border;
      this.borderColor = borderColor;
      this.backgroundColor = Color.WHITE;
    }

    public void updatePos(float x1, float y1, float x2, float y2) {
      this.rawX1 = x1;
      this.rawX2 = x2;
      this.rawY1 = y1;
      this.rawY2 = y2;

      if (x1 < x2) {
        this.x1 = x1;
        this.x2 = x2;
      }
      else {
        this.x1 = x2;
        this.x2 = x1;
      }

      if (y1 < y2) {
        this.y1 = y1;
        this.y2 = y2;
      }
      else {
        this.y1 = y2;
        this.y2 = y1;
      }
    }

    public void fill(Color fillColor) {
      this.backgroundColor = fillColor;
      this.fill = true;
    }

    public void paint(Graphics graphics) {
      Graphics2D g = (Graphics2D)graphics;
      Shape shape;
      float width = this.x2 - this.x1;
      float height = this.y2 -this.y1;

      if (selected) {
        g.setStroke(new BasicStroke(this.border, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
      }
      else {
        g.setStroke(new BasicStroke(this.border));
      }
      g.setColor(borderColor);

      switch (this.type)
        {
        case SQUARE:
          shape = new Rectangle2D.Float(this.x1, this.y1, width, height);
          g.draw(shape);
          if (fill) {
            g.setPaint(backgroundColor);
            g.fill(shape);
          }
          break;
        case CIRCLE:
          float circleSize = Math.min(width, height);
          shape = new Ellipse2D.Float(this.x1, this.y1, circleSize, circleSize);
          g.draw(shape);
          if (fill) {
            g.setPaint(backgroundColor);
            g.fill(shape);
          }
          break;
        case LINE:
          shape = new Line2D.Float(this.rawX1, this.rawY1, this.rawX2, this.rawY2);
          g.draw(shape);
          if (fill) {
            g.setPaint(backgroundColor);
            g.fill(shape);
          }
          break;
        }

        g.setPaint(borderColor);
        g.setStroke(new BasicStroke(0));
    }
}
