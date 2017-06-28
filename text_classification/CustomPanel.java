package cluster;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CustomPanel extends JPanel {
	private Color startColor = new Color(238, 238, 238);
	private Color endColor = new Color(255, 255, 255);
	GeneralPath path;
	Color accentColor = new Color(0x80ffffff);
	Color textColor = new Color(0x0f0f0f);
	String title;
	String img;
public CustomPanel(String img){
	super();
	this.img=img;
}
public CustomPanel(Color color1, Color color2){
	super();
	startColor = color1;
	endColor = color2;
}
public void paintComponent(Graphics g){
	Graphics2D g2d = (Graphics2D) g.create();
	int h = getHeight();
	int w = getWidth();
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	GeneralPath path = new GeneralPath();
	path.moveTo(70, 0);
	path.lineTo(8, 0);
	path.quadTo(0, 0, 0, 7);
	path.lineTo(0, 55);
	path.lineTo(getWidth() - 1, 55);
	path.lineTo(getWidth() - 1, 7);
	path.quadTo(getWidth() - 1, 0, getWidth() - 8, 0);
	path.lineTo(30, 0);
	Rectangle bounds1 = path.getBounds();
	GradientPaint painter = new GradientPaint(0, path.getBounds().y,true ? endColor : startColor, 0,bounds1.y + bounds1.height - 1, true ? startColor : endColor);
	g2d.setPaint(painter);
	g2d.fill(path);
	Rectangle rectangle = new Rectangle(0, 40, getWidth(), 20);
	g2d.fill(rectangle);
	g2d.setColor(new Color(128, 128, 128));
	g2d.draw(path);
	g2d.setPaint(new Color(240, 240, 240));
	g2d.fillRect(0, 31, getWidth() - 1, h - 50);
	g2d.setColor(new Color(128, 128, 128));
	g2d.drawLine(12, 0, getWidth() - 10, 0);
	g2d.drawRect(0, 30, getWidth() - 1, h - 50);
	h = h - 30;
	path = new GeneralPath();
	path.moveTo(0, h);
	path.lineTo(0, h + 22);
	path.quadTo(0, h + 29, 8, h + 29);
	path.lineTo(getWidth() - 8, h + 29);
	path.quadTo(getWidth() - 1, h + 29, getWidth() - 1, h + 22);
	path.lineTo(getWidth() - 1, h);
	g2d.setColor(Color.GRAY);
	startColor = new Color(192, 192, 192);
	endColor = new Color(238, 238, 238);
	bounds1 = path.getBounds();
	painter = new GradientPaint(0, path.getBounds().y, endColor, 0,bounds1.y + bounds1.height - 1, startColor);
	g2d.setPaint(painter);
	g2d.fill(path);
	g2d.setColor(new Color(128, 128, 128));
	g2d.draw(path);
	g2d.setColor(new Color(128, 128, 128));
	g2d.drawLine(0, h - 1, getWidth() - 1, h - 1);
	g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont((float) g2d.getFont().getSize() + 1));
	g2d.setColor(accentColor);
	g2d.drawString(title, 40, 22);
	g2d.setColor(textColor);
	g2d.drawString(title, 40, 21);
	g2d.drawImage(new ImageIcon(img).getImage(), 10, 0,null, null);
	}
public void setStartColor(Color color) {
	startColor = color;
}
public void setEndColor(Color pressedColor) {
	endColor = pressedColor;
}
public Color getStartColor() {
	return startColor;
}
public Color getEndColor() {
	return endColor;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
}