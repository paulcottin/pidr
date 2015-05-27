package vues;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.telelogic.rhapsody.core.RhapsodyAppServer;

import modele.Comparateur;

/**
 * Fenetre principale de l'application
 * @author paul
 *
 */
public class Fenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Comparateur c;
	private JPanel container;
	
	public Fenetre(Comparateur c) {
		super("SysML comparaison");
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.c = c;
		
		this.container = new JPanel();
		this.container.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.PAGE_AXIS));
		north.add(new FilesInformations(c));
		north.add(new Legende(c));
		this.container.add(north, BorderLayout.NORTH);
		this.container.add(new DifferencesPanel(c), BorderLayout.CENTER);
		
		this.setContentPane(container);
		
		this.setJMenuBar(new MenuBar(c));
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				RhapsodyAppServer.CloseSession();
				System.out.println("rhapsodyClose");
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.setVisible(true);
	}

	
}
