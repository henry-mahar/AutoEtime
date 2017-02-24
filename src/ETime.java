import javax.swing.JFrame;
import java.awt.Rectangle;

import com.yrnehraham.ETime.gfx.ETimePanel;
import com.yrnehraham.ETime.parser.TimetableParser;
import com.yrnehraham.ETime.parser.AdpParser;

public class ETime {
	
    private static final int FPS = 10;
    private static final double SPF = 1000000000.0 / FPS;
    
	public static void main(String[] args) {
		TimetableParser tcdbParser = new TimetableParser();
		AdpParser adpParser = new AdpParser();
		
		System.setProperty("javax.net.ssl.trustStore","C:/Users/Henry/workspace/AutoEtime/cacerts");
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("AutoETime");
		ETimePanel panel = new ETimePanel(tcdbParser, adpParser);
		frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        long timeElapsed = 0;
        long prevTime = System.nanoTime();
        while (true) {
            long currentTime = System.nanoTime();
            timeElapsed += currentTime - prevTime;
            prevTime = currentTime;
            if (timeElapsed > SPF) {
                panel.paintImmediately(new Rectangle(0, 0, panel.getWidth(), panel.getHeight()));
                timeElapsed = (long) (timeElapsed - SPF);
            }
        }
	}
}
