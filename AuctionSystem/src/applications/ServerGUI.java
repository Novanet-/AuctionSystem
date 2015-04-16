package applications;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utilities.Category;
import utilities.Money;
import commLayer.ClientComms;
import commLayer.Message;
import commLayer.MessageType;
import commLayer.RequestType;
import commLayer.ServerComms;
import commLayer.ServerThread;
import entities.Bid;
import entities.Item;
import entities.User;

/**
 * Created using Java 8
 *
 */
public class ServerGUI
{

	private JFrame frame;

	private ArrayList<Item> auctionList;
	private ArrayList<User> userList;

	private ServerThread serverThread;
	private ServerComms serverComms;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		Item item = new Item("A", "B", Category.ART, 2, LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0), LocalDateTime.of(2015, Month.DECEMBER,
				31, 23, 59), new Money(Currency.getInstance("GBP"), 50.50), new ArrayList<Bid>());
		System.out.println(item.toString());
		System.out.println(item.getItemId());
		System.out.println(item.getName() + " " + item.getDescription() + " " + item.getCategory().toString() + " "
				+ item.getStartTime().toString() + " " + item.getEndTime().toString() + " " + item.getReservePrice().getAmount());
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

		serverComms = new ServerComms(this, serverThread);

		auctionList = new ArrayList<Item>();
		userList = new ArrayList<User>();

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

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				for (Item i : auctionList)
				{
					System.out.println(i.getItemId() + " " + i.getName() + " " + i.getDescription() + " " + i.getCategory().toString() + " "
							+ i.getStartTime().toString() + " " + i.getEndTime().toString() + " " + i.getReservePrice().getAmount());
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		pnlOuter.add(btnNewButton, gbc_btnNewButton);

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


	/**
	 * Adds an auction to the system
	 * 
	 * @param item
	 *            The auction to be added
	 * @return boolean - isAuctionAddSuccesful
	 */
	public boolean addAuctionToSystem(Item item)
	{
		return auctionList.add(item);
	}


	/**
	 * Adds a user to the system
	 * 
	 * @param user
	 *            The user to be added
	 * @return boolean - isUserAddSuccesful
	 */
	public boolean addUserToSystem(User user)
	{
		return userList.add(user);
	}


	/**
	 * Fetch all auctions that match the specified RequestType
	 * 
	 * @param requestType
	 *            The filter used to find the required auctions
	 * @return boolean - true if any matching auctions found
	 */
	public boolean fetchAuctions(RequestType requestType)
	{
		boolean openAuctionFound = false;
		if (requestType == RequestType.ALL_OPEN_ITEMS)
			for (Item auction : auctionList)
			{
				LocalDateTime currentDateTime = LocalDateTime.now();
				if ((auction.getStartTime().isBefore(currentDateTime)) && (auction.getEndTime().isAfter(currentDateTime)))
				{
					serverComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, auction));
					openAuctionFound = true;
				}
			}
		return openAuctionFound;

	}
}
