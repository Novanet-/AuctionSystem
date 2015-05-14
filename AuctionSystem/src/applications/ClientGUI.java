package applications;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import clientGUIComponents.LoginPanel;
import clientGUIComponents.MainPanel;
import clientGUIComponents.SubmitPanel;
import commLayer.ClientComms;
import commLayer.ClientThread;
import commLayer.Message;
import commLayer.RequestType;
import entities.Bid;
import entities.Item;
import entities.User;

import javax.swing.JMenuItem;
import javax.swing.JMenu;

/**
 * Created using Java 8
 *
 */
public class ClientGUI
{

	private JFrame				frmClient;

	private ClientThread		clientThread;
	private ClientComms			clientComms;

	private ArrayList<Item>		auctionCache;
	private ResizingCardLayout	lytCard;

	private LoginPanel			pnlLogin;

	private MainPanel			pnlMain;

	private SubmitPanel			pnlSubmitItem;

	private User				currentUser;
	private JMenu				mnTools;
	private JMenuItem			mntmLogout;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					ClientGUI window = new ClientGUI();
					window.frmClient.setVisible(true);
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
	public ClientGUI()
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
		clientComms = new ClientComms(this, clientThread);

		auctionCache = new ArrayList<Item>();

		frmClient = new JFrame();
		frmClient.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmClient.setIconImage(Toolkit.getDefaultToolkit().getImage(ClientGUI.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		frmClient.setTitle("Auction ClientGUI");
		frmClient.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmClient.setBounds(100, 100, 550, 650);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lytCard = new ResizingCardLayout();
		frmClient.getContentPane().setLayout(lytCard);

		pnlLogin = new LoginPanel(this);
		frmClient.getContentPane().add(pnlLogin, "pnlLogin");

		pnlMain = new MainPanel(this);
		frmClient.getContentPane().add(pnlMain, "pnlMain");

		pnlSubmitItem = new SubmitPanel(this);
		frmClient.getContentPane().add(pnlSubmitItem, "pnlSubmitItem");

		JMenuBar mnuMenuBar = new JMenuBar();
		frmClient.setJMenuBar(mnuMenuBar);

		mnTools = new JMenu("Tools");
		mnuMenuBar.add(mnTools);

		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(e ->
		{
			logoutUser();
		});
		mnTools.add(mntmLogout);

		pack();
	}


	/**
	 * Changes the card layout of the frame to display the specified card
	 * 
	 * @param destinationPanel
	 *            The name of the required panel
	 */
	public void changeCard(String destinationPanel)
	{
		lytCard.show(frmClient.getContentPane(), destinationPanel);
		pack();
	}


	/**
	 * Packs the gui frame and puts it in the centre of the user's screen
	 * 
	 * @see java.awt.Window#pack()
	 */
	public void pack()
	{
		frmClient.pack();
		frmClient.setLocationRelativeTo(null);
	}


	/**
	 * Forwards a message to the comms module, which then sends the message to the server
	 * 
	 * @param message
	 *            The Message to be forwarded
	 * @return boolean - isMessageSendSuccesful
	 */
	public boolean sendMessage(Message message)
	{
		return clientComms.sendMessage(message);
	}


	/**
	 * Adds an item to the auction cache
	 * 
	 * @param item
	 *            The item to be added
	 * @return boolean - isItemAddSuccesful
	 */
	synchronized public boolean addAuctionToCache(Item item)
	{
		return auctionCache.add(item);
	}


	/**
	 * Empties the auction cache
	 */
	public void clearCache()
	{
		auctionCache.clear();
	}


	/**
	 * Retrieves the auction at the given index from the auctionCache
	 * 
	 * @param index
	 * @return The item retrieved
	 */
	synchronized public Item getAuctionFromCache(int index)
	{
		return auctionCache.get(index);
	}


	/**
	 * Refreshes the list of auctions, clearing it and then re-adding elements from the auction cache to it
	 * 
	 * @return boolean - true if the auction list is the same size as the auction cache
	 */
	public boolean refreshAuctionList(RequestType filterType)
	{
		return pnlMain.refreshAuctionList(auctionCache, filterType);
	}


	/**
	 * Updates an auction in cache with a new bid that has been made
	 * 
	 * @param bid
	 *            The bid to be placed in the updated auction
	 * @return true if auction matching bid has been found and updated
	 */
	public boolean updateAuctionInCache(Bid bid)
	{
		for (int i = 0; i < auctionCache.size(); i++)
		{
			if (auctionCache.get(i).getItemId() == bid.getItemID())
			{
				auctionCache.get(i).getBids().push(bid);
				return true;
			}
		}
		return false;
	}


	/**
	 * Switches the logged in user to the main menu
	 */
	public void loginUser()
	{
		changeCard("pnlMain");
		pack();
	}


	/**
	 * Sets the currently lgoged in user to null and take the user back to the login screen
	 */
	private void logoutUser()
	{
		setCurrentUser(null);
		changeCard("pnlLogin");
		pack();
	}


	/**
	 * Enables the login button
	 */
	public void enableLogin()
	{
		pnlLogin.getBtnSubmitLogin().setEnabled(true);
	}


	/**
	 * Disable the login button
	 */
	public void disableLogin()
	{
		pnlLogin.getBtnSubmitLogin().setEnabled(true);
	}


	public User getCurrentUser()
	{
		return currentUser;
	}


	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}


	/**
	 * A card layout that resizes based on the current panel
	 *
	 */
	public static class ResizingCardLayout extends CardLayout
	{

		private static final long	serialVersionUID	= -7578838449014921005L;


		public ResizingCardLayout()
		{
			super();
		}


		/**
		 * Resizes the card layout based on the current components preferred size
		 * 
		 * @see java.awt.CardLayout#preferredLayoutSize(java.awt.Container)
		 */
		@Override
		public Dimension preferredLayoutSize(Container parent)
		{

			Component current = findCurrentComponent(parent);
			if (current != null)
			{
				Insets insets = parent.getInsets();
				Dimension pref = current.getPreferredSize();
				pref.width += insets.left + insets.right;
				pref.width *= 1.1;
				pref.height += insets.top + insets.bottom;
				pref.height *= 1.1;
				return pref;
			}
			return super.preferredLayoutSize(parent);
		}


		/**
		 * Returns the current component that the card layout is displaying
		 * 
		 * @param parent
		 *            The container of the card layout
		 * @return The current component
		 */
		public Component findCurrentComponent(Container parent)
		{
			for (Component comp : parent.getComponents())
			{
				if (comp.isVisible())
				{
					return comp;
				}
			}
			return null;
		}

	}

}