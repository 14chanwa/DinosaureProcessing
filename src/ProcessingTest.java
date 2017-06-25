import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jws.Oneway;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PSurface;

public class ProcessingTest extends PApplet{

	JFrame frame;
	JPanel drawPanel;
	
	PSurface ps;
	
	Stripe[] stripes = new Stripe[50];
	
	public ProcessingTest() {
		frame = new JFrame("JFrame Test");
		
		frame.setSize(800,600);
		frame.setMinimumSize(new Dimension(200,200));
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawPanel = new JPanel();
		frame.add(drawPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(new JLabel("salut"), BorderLayout.CENTER);
		
		JButton stopButton = new JButton("STOP");
		stopButton.addActionListener(new ActionListener() {
			boolean isStopped = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isStopped) {
					ps.resumeThread();
					isStopped = false;
					return;
				}
				ps.pauseThread();
				isStopped = true;
			}
			
		});
		bottomPanel.add(stopButton, BorderLayout.EAST);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		
		ps = initSurface();
		ps.setSize(200, 200);
		SmoothCanvas smoothCanvas = (SmoothCanvas)ps.getNative();
		drawPanel.add(smoothCanvas);
		ps.startThread();
		frame.setVisible(true);
	}
	
	public void setup() {
		 // Initialize all "stripes"
	    for (int i = 0; i < stripes.length; i++) {
	      stripes[i] = new Stripe(this);
	    }
	}
	
    public void settings(){
        size(drawPanel.getSize().width, frame.getContentPane().getSize().height);
        surface.setResizable(true);
    }

    public void draw(){	
    	surface.setSize(frame.getContentPane().getSize().width, frame.getContentPane().getSize().height);
        //background(0);
        //ellipse(mouseX, mouseY, 20, 20);
        background(100);
        // Move and display all "stripes"
        for (int i = 0; i < stripes.length; i++) {
          stripes[i].move();
          stripes[i].display();
        }
    }
    
    public static void main(String[] args){
        new ProcessingTest();
    }

}