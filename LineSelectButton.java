import javax.swing.*;
import java.awt.*;

// This is a modification of the JToggleButton so that we can use it to select line thickness.
public class LineSelectButton extends JToggleButton {
    public int thickness = 0;

    LineSelectButton(int val) {
      this.thickness = val;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D)graphics;
        int linePos = getSize().height / 2;

        g.setStroke(new BasicStroke(this.thickness));
        g.drawLine(0, linePos, getSize().width, linePos);
        g.setStroke(new BasicStroke(0));
    }
}
