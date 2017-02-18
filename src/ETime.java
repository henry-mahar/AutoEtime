import javax.swing.JFrame;
import java.awt.Rectangle;

import com.yrnehraham.ETime.gfx.ETimePanel;

public class ETime {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("AutoETime");
		ETimePanel panel = new ETimePanel();
		frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        
	}
}
