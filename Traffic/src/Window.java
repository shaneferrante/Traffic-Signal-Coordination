import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Window extends JPanel {
	
	private City city;
	private int width, height;
	
	public Window(int width, int height) {
		this.width = width;
		this.height = height;
		this.setSize(width, height);
	}
	
	public void setCity(City city) {
		city.addWindow(this);
		this.city = city;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		city.draw(g);
	}
	
}
