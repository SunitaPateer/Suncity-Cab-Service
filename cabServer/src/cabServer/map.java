package cabServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.*;

public class map extends JPanel implements ActionListener,
		ChangeListener, MouseListener, MouseMotionListener {
	/**
     * 
     */
	// ??? ok eclipse wants this:
	private static final long serialVersionUID = 1L;
	private JPanel phasePanel;
	static JPanel contentPane;
	private JPanel picture1;

	int j1 = 0;
	int j2 = 0;

	Point point1, point2;
	private Line2D line2d;
	static int[][] adj = new int[10][10];
	static int h;
	// static int[][] Adj = new int[h][h];
	static int[][] line = new int[4][10];
	static int[][] city = new int[2][10];

	/** Creates a new instance of RunProgram */
	public map() {
		setLayout(new BorderLayout());
		// Create a panel and make it the content pane.28.
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(BorderFactory.createRaisedBevelBorder());
		// create panel to show original pictures32.
		picture1 = new JPanel();
		picture1.addMouseListener(this);
		// picture1.setBackground(Color.BLUE);
		picture1.setBorder(BorderFactory.createLoweredBevelBorder());
		picture1.setPreferredSize(new Dimension(1000, 1000));
		JPanel controlPanel = new JPanel();
		// controlPanel.setBackground(Color.GRAY);
		controlPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		controlPanel.setPreferredSize(new Dimension(800, 50));
		controlPanel.setLayout(new FlowLayout());
		// contentPane.add(phasePanel, BorderLayout.SOUTH);
		// contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(picture1, BorderLayout.WEST);

		// contentPane.add(bar, BorderLayout.NORTH);
		// contentPane.add(toolBar, BorderLayout.CENTER);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/** 59. * @param args the command line arguments */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Map of City");
		frame.add(new map());
		frame.setSize(1100, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setJMenuBar(bar); // does not exist
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		JButton b1 = new JButton("Save");
		// JButton b2 = new JButton("Cabs");
		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				for (int j = 0; j < 10; j++) {
					for (int i = 0; i < 10; i++) {
						if (check_contain(city[0][i], city[1][i], line[0][j],
								line[1][j])) {
							i1 = i;
							for (int k = 0; k < 10; k++) {
								if (check_contain(city[0][k], city[1][k],
										line[2][j], line[3][j])) {
									i2 = k;
									int di =    (int) Math
											.sqrt((line[0][j] - line[2][j])
													* (line[0][j] - line[2][j])
													+ (line[1][j] - line[3][j])
													* (line[1][j] - line[3][j]));
									// int Di = (int)di;
									adj[i1][i2] = di;
									adj[i2][i1] = di;
									i1 = 0;
									i2 = 0;
								} else {
									adj[i1][i2] = 0;
									adj[i2][i1] = 0;
								}
							}
						}
					}
				}
                                
                                 BufferedWriter output = null;
       try{
            output= new BufferedWriter(new FileWriter("Adjacency.txt", false));
           }
       catch(IOException ioe){
       ioe.printStackTrace();
         }
                        try {
                            output.write(Integer.toString(h));
                            output.newLine();
                        } catch (IOException ex) {
                            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
                        }
				for (int k = 0; k < h; k++) {
					for (int l = 0; l < h; l++) {
						if (adj[k][l] == 0) {
                                                    if(k==l){adj[k][l]=0;}
                                                    else{
							adj[k][l] = 100000;}
						}
						System.out.print(adj[k][l] + " ");
                                        try {
                                            output.append(Integer.toString(adj[k][l])+" ");
                                        } catch (IOException ex) {
                                            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
                                        }
					}
					System.out.println();
                                try {
                                    output.newLine();
                                } catch (IOException ex) {
                                    Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
                                }
				}
                        try {
                            output.close();
                            // addRectangle(city[0][0]-15, city[1][0]-15, e.getSource(),
                            // Color.yellow);
                            // addRectangle(city[0][0], city[1][0]-15, e.getSource(),
                            // Color.cyan);
                            // Color.magenta);
                            // Color.magenta);
                        } catch (IOException ex) {
                            Logger.getLogger(map.class.getName()).log(Level.SEVERE, null, ex);
                        }
			}
		});
		b1.setSize(80, 80);
		frame.add(b1);
		String x = JOptionPane
				.showInputDialog("Please enter the number of Cities:");
		h = Integer.parseInt(x);
	}

	static int i1 = 0;
	static int i2 = 0;
	static double dist;

	public static boolean check_contain(int X, int Y, int X1, int Y1) {
		dist = Math.sqrt((X1 - X) * (X1 - X) + (Y1 - Y) * (Y1 - Y));
		if (dist < 25)
			return true;
		return false;
	}

	public void drawCabs(Object source) {
		Graphics g = ((JComponent) source).getGraphics();
	
			g.setColor(Color.yellow);
			g.fillRect(city[0][0] - 30, city[1][0] - 30, 10, 10);
			
			g.setColor(Color.magenta);
			g.fillRect(city[0][0], city[1][0] - 30, 10, 10);
			
			g.setColor(Color.cyan);
			g.fillRect(city[0][0] + 30, city[1][0] - 30, 10, 10);
			
			
			g.setColor(Color.yellow);
			g.fillRect(city[0][4] - 30, city[1][4] - 30, 10, 10);
			
			g.setColor(Color.magenta);
			g.fillRect(city[0][4], city[1][4] - 30, 10, 10);
			
			g.setColor(Color.cyan);
			g.fillRect(city[0][4] + 30, city[1][4] - 30, 10, 10);
                        
			
			
			g.setColor(Color.yellow);
			g.fillRect(city[0][7] - 30, city[1][7] - 30, 10, 10);
			
			g.setColor(Color.magenta);
			g.fillRect(city[0][7], city[1][7] - 30, 10, 10);
			
			g.setColor(Color.cyan);
			g.fillRect(city[0][7] + 30, city[1][7] - 30, 10, 10);
			repaint();
		}
	

	public void mousePressed(MouseEvent e) {
		point1 = new Point();
		point1.x = e.getX();
		point1.y = e.getY();

	}

	public void mouseReleased(MouseEvent e) {
		point2 = new Point();
		point2.x = e.getX();
		point2.y = e.getY();
	
			if (point1.x != point2.x && point1.y != point2.y) {
				String lane = JOptionPane
						.showInputDialog("Please enter the name of the Road:");
				drawline(point1.x, point1.y, point2.x, point2.y, e.getSource(),
						lane);
				repaint();
				line[0][j1] = point1.x;
				line[1][j1] = point1.y;
				line[2][j1] = point2.x;
				line[3][j1] = point2.y;
				System.out.println("Line" + "-" + lane);
				System.out.println("x1 = " + point1.x + " , " + "y1 = "
						+ point1.y + " ; " + "x2 = " + point2.x + " , "
						+ "y2 = " + point2.y);
				j1++;
			} else {
				if (j2 < h) {
					String name = JOptionPane
							.showInputDialog("Please enter the name of the City:");
					if (name != null) {
						drawCircle(e.getX() - (radius / 2), e.getY()
								- (radius / 2), e.getSource(), true, name);
						repaint();
					}
					city[0][j2] = point1.x;
					city[1][j2] = point1.y;
					System.out.println("Circle" + "-" + (j2 + 1));
					System.out.println("x = " + point1.x + " , " + "y = "
							+ point1.y);
					j2++;
				}
			}
		
			System.out.println("Hello");
			drawCabs(e.getSource());
			repaint();
		
		// System.out.println(line[0][j]);
	}

	public void drawCircle(int x, int y, Object source, boolean fill,
			String name_yoyo) {
		if (source instanceof JPanel) {
			Graphics g = ((JComponent) source).getGraphics();
			// g.clearRect(0,0,450,1000);
			g.drawOval(x - radius, y - radius, (2 * radius - 1),
					(2 * radius - 1));
			g.drawString(name_yoyo, (point1.x - 20), (point1.y - 28));
			g.setColor(Color.black);
			if (fill) {
				g.fillOval(x - radius, y - radius, 2 * radius - 1,
						2 * radius - 1);
			}
		} // else ignore
	}

	public void drawline(int x1, int y1, int x2, int y2, Object source,
			String Lane) {
		if (source instanceof JPanel) {
			Graphics g = ((JComponent) source).getGraphics();
			// g.clearRect(0,0,450,1000);
			g.setColor(Color.BLACK);
			g.drawLine(point1.x, point1.y, point2.x, point2.y);
			g.drawString(Lane, ((point1.x + point2.x) / 2) + 5,
					(point1.y + point2.y) / 2);
		}
	}

	// public static void addRectangle(int x, int y, Object source, Color color)
	// {
	// Graphics g = ((JComponent) source).getGraphics();
	// g.setColor(color);
	// g.drawRect(x, y, 10, 10);
	// }
	
        public void paint(Graphics g) {

		 super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		if (point1 != null && point2 != null) {

			g2d.setPaint(Color.BLACK);
			g2d.setStroke(new BasicStroke(1.5f));
			g2d.draw(line2d);

		}
                
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	int x, y;
	int radius = 15;

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	@SuppressWarnings("empty-statement")
	public void mouseDragged(MouseEvent e) {

		// drawline(point1.x, point1.y, point2.x, point2.y, e.getSource());

		// repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}