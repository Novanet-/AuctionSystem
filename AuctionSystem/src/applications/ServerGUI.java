package applications;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import commLayer.Comms;
import commLayer.ServerThread;
import utilities.Category;
import utilities.Money;
import entities.Bid;
import entities.Item;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.security.Provider;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;

/**
 * Created using Java 8
 *
 */
public class ServerGUI
{

	private JFrame frame;
	
	private ServerThread serverThread;
	private Comms serverComms;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		Item item = new Item("A", "B", Category.ART, 2, LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0), LocalDateTime.of(2015, Month.DECEMBER, 31, 23, 59), new Money(Currency.getInstance("GBP"), 50.50), new ArrayList<Bid>());
		System.out.println(item.toString());
		System.out.println(item.getItemId());
		System.out.println(item.getName() + " " + item.getDescription() + " " + item.getCategory().toString() + " " + item.getStartTime().toString() + " " + item.getEndTime().toString() + " " + item.getReservePrice().getAmount());
		EventQueue.invokeLater(new Runnable()
		{

			public void run()
			{
				try
				{
					ServerGUI window = new ServerGUI();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public ServerGUI()
	{
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		serverComms = new Comms(serverThread);
		serverComms.initServerSocket();
		
		frame = new JFrame();
		frame.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pnlOuter = new JPanel();
		frame.getContentPane().add(pnlOuter, BorderLayout.CENTER);
		GridBagLayout gbl_pnlOuter = new GridBagLayout();
		gbl_pnlOuter.columnWidths = new int[]
		{ 30, 0, 30 };
		gbl_pnlOuter.rowHeights = new int[]
		{ 30, 0, 30 };
		gbl_pnlOuter.columnWeights = new double[]
		{ 0.0, 0.0, 0.0 };
		gbl_pnlOuter.rowWeights = new double[]
		{ 0.0, 0.0, 0.0 };
		pnlOuter.setLayout(gbl_pnlOuter);

		JLabel lblServer = new JLabel("ServerGUI");
		lblServer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.weighty = 2.0;
		gbc_lblServer.weightx = 2.0;
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.gridx = 1;
		gbc_lblServer.gridy = 1;
		pnlOuter.add(lblServer, gbc_lblServer);
	}

}
