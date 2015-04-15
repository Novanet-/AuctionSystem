package applications;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.CardLayout;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTree;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.Box;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import utilities.Category;
import utilities.Money;
import entities.Item;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;

import clientGUIComponents.LoginPanel;
import commLayer.ClientThread;
import commLayer.Comms;
import commLayer.Message;
import commLayer.MessageType;
import commLayer.RequestType;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

/**
 * Created using Java 8
 *
 */
public class ClientGUI
{

	public JFrame frmClient;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtFilterByID;
	private JTextField txtFilterBySeller;
	private final ButtonGroup btngrpFilters = new ButtonGroup();
	private JTextField txtName;
	private JTextField txtDescription;
	private JTextField txtReservePrice;
	private JComboBox<Category> cmbCategory;
	private JSpinner spnStartDate;
	private JSpinner spnEndDate;
	private JSpinner spnStartTime;
	private JSpinner spnEndTime;

	private ClientThread clientThread;
	private Comms clientComms;

	private boolean listItemSelected;

	private ArrayList<Item> auctionCache;
	private JList<String> lstAuctionItems;
	private DefaultListModel<String> auctionModel;
	private ResizingCardLayout lytCard;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{

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
		clientComms = new Comms(this, clientThread);
		clientComms.initClientSocket();

		listItemSelected = false;

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

		JPanel pnlLogin = new LoginPanel(this);
		frmClient.getContentPane().add(pnlLogin, "pnlLogin");
//		pnlLogin.setToolTipText("Login Screen");
//		frmClient.getContentPane().add(pnlLogin, "pnlLogin");
//		GridBagLayout gbl_pnlLogin = new GridBagLayout();
//		gbl_pnlLogin.columnWidths = new int[]
//		{ 30, 30, 30 };
//		gbl_pnlLogin.rowHeights = new int[]
//		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//		gbl_pnlLogin.columnWeights = new double[]
//		{ 1.0, 1.0, 1.0 };
//		gbl_pnlLogin.rowWeights = new double[]
//		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
//		pnlLogin.setLayout(gbl_pnlLogin);

//		JLabel lblUsername = new JLabel("Username");
//		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
//		gbc_lblUsername.anchor = GridBagConstraints.EAST;
//		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
//		gbc_lblUsername.gridx = 0;
//		gbc_lblUsername.gridy = 2;
//		pnlLogin.add(lblUsername, gbc_lblUsername);
//
//		txtUsername = new JTextField();
//		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
//		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
//		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
//		gbc_txtUsername.gridx = 1;
//		gbc_txtUsername.gridy = 2;
//		pnlLogin.add(txtUsername, gbc_txtUsername);
//		txtUsername.setColumns(10);
//
//		JLabel lblPassword = new JLabel("Password");
//		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
//		gbc_lblPassword.anchor = GridBagConstraints.EAST;
//		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
//		gbc_lblPassword.gridx = 0;
//		gbc_lblPassword.gridy = 4;
//		pnlLogin.add(lblPassword, gbc_lblPassword);
//
//		txtPassword = new JTextField();
//		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
//		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
//		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
//		gbc_txtPassword.gridx = 1;
//		gbc_txtPassword.gridy = 4;
//		pnlLogin.add(txtPassword, gbc_txtPassword);
//		txtPassword.setColumns(10);
//
//		JButton btnSubmitLogin = new JButton("Submit");
//		GridBagConstraints gbc_btnSubmitLogin = new GridBagConstraints();
//		gbc_btnSubmitLogin.insets = new Insets(0, 0, 5, 5);
//		gbc_btnSubmitLogin.gridx = 1;
//		gbc_btnSubmitLogin.gridy = 6;
//		pnlLogin.add(btnSubmitLogin, gbc_btnSubmitLogin);
//
//		JButton btnSwitchPanelsLogin = new JButton("Switch Panels");
//		btnSwitchPanelsLogin.addActionListener(new ActionListener()
//		{
//
//			public void actionPerformed(ActionEvent arg0)
//			{
//				lytCard.show(frmClient.getContentPane(), "pnlMain");
//				frmClient.pack();
//			}
//		});
//		GridBagConstraints gbc_btnSwitchPanelsLogin = new GridBagConstraints();
//		gbc_btnSwitchPanelsLogin.anchor = GridBagConstraints.NORTH;
//		gbc_btnSwitchPanelsLogin.insets = new Insets(0, 0, 5, 5);
//		gbc_btnSwitchPanelsLogin.gridx = 1;
//		gbc_btnSwitchPanelsLogin.gridy = 7;
//		pnlLogin.add(btnSwitchPanelsLogin, gbc_btnSwitchPanelsLogin);

		JPanel pnlMain = new JPanel();
		pnlMain.setPreferredSize(new Dimension(550, 450));
		frmClient.getContentPane().add(pnlMain, "pnlMain");
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.X_AXIS));

		JPanel pnlItemList = new JPanel();
		pnlMain.add(pnlItemList);
		GridBagLayout gbl_pnlItemList = new GridBagLayout();
		gbl_pnlItemList.columnWidths = new int[]
		{ 200 };
		gbl_pnlItemList.rowHeights = new int[]
		{ 30, 0, 0, 0, 0, 0 };
		gbl_pnlItemList.columnWeights = new double[]
		{ 1.0 };
		gbl_pnlItemList.rowWeights = new double[]
		{ 0.0, 1.0, 0.0, 0.0, 1.0, 0.0 };
		pnlItemList.setLayout(gbl_pnlItemList);

		JScrollPane scrlAuctionList = new JScrollPane();
		GridBagConstraints gbc_scrlAuctionList = new GridBagConstraints();
		gbc_scrlAuctionList.weighty = 0.5;
		gbc_scrlAuctionList.fill = GridBagConstraints.BOTH;
		gbc_scrlAuctionList.insets = new Insets(0, 5, 5, 5);
		gbc_scrlAuctionList.gridx = 0;
		gbc_scrlAuctionList.gridy = 1;
		pnlItemList.add(scrlAuctionList, gbc_scrlAuctionList);

		JPopupMenu popAuctionList = new JPopupMenu();
		// popAuctionList.add(new JMenuItem("Bid on Item"));

		lstAuctionItems = new JList<String>();
		auctionModel = new DefaultListModel<String>();
		lstAuctionItems.setModel(auctionModel);
		lstAuctionItems.addListSelectionListener(new ListSelectionListener()
		{

			public void valueChanged(ListSelectionEvent e)
			{
				if (!lstAuctionItems.isSelectionEmpty())
				{
					listItemSelected = true;
				}
			}
		});

		lstAuctionItems.addMouseListener(new MouseAdapter()
		{

			public void mousePressed(MouseEvent e)
			{
				showPopup(popAuctionList, e);
			}


			@Override
			public void mouseReleased(MouseEvent e)
			{
				showPopup(popAuctionList, e);
			}


			private void showPopup(JPopupMenu popAuctionList, MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					popAuctionList.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		scrlAuctionList.setViewportView(lstAuctionItems);
		lstAuctionItems.setVisibleRowCount(20);
		lstAuctionItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstAuctionItems.setLayoutOrientation(JList.VERTICAL_WRAP);
		lstAuctionItems.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel lblItemsForAuction = new JLabel("Items for Auction");
		GridBagConstraints gbc_lblItemsForAuction = new GridBagConstraints();
		gbc_lblItemsForAuction.insets = new Insets(5, 5, 5, 5);
		gbc_lblItemsForAuction.gridx = 0;
		gbc_lblItemsForAuction.gridy = 0;
		pnlItemList.add(lblItemsForAuction, gbc_lblItemsForAuction);
		lblItemsForAuction.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblItemsForAuction.setLabelFor(lstAuctionItems);

		JButton btnOpenSubmitForm = new JButton("Submit New Auction");
		btnOpenSubmitForm.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				changeCard("pnlSubmitItem");
			}
		});
		GridBagConstraints gbc_btnOpenSubmitForm = new GridBagConstraints();
		gbc_btnOpenSubmitForm.insets = new Insets(0, 5, 5, 5);
		gbc_btnOpenSubmitForm.anchor = GridBagConstraints.SOUTH;
		gbc_btnOpenSubmitForm.gridx = 0;
		gbc_btnOpenSubmitForm.gridy = 2;
		pnlItemList.add(btnOpenSubmitForm, gbc_btnOpenSubmitForm);

		JTextArea textArea = new JTextArea();
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 5, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 4;
		pnlItemList.add(textArea, gbc_textArea);

		JButton btnBidOnItem = new JButton("Bid on Auction");
		GridBagConstraints gbc_btnBidOnItem = new GridBagConstraints();
		gbc_btnBidOnItem.anchor = GridBagConstraints.SOUTH;
		gbc_btnBidOnItem.insets = new Insets(0, 5, 5, 5);
		gbc_btnBidOnItem.gridx = 0;
		gbc_btnBidOnItem.gridy = 5;
		pnlItemList.add(btnBidOnItem, gbc_btnBidOnItem);

		JPanel pnlFilters = new JPanel();
		pnlMain.add(pnlFilters);
		GridBagLayout gbl_pnlFilters = new GridBagLayout();
		gbl_pnlFilters.columnWidths = new int[]
		{ 30, 50 };
		gbl_pnlFilters.rowHeights = new int[]
		{ 30, 30, 30, 30, 30 };
		gbl_pnlFilters.columnWeights = new double[]
		{ 0.0, 1.0 };
		gbl_pnlFilters.rowWeights = new double[]
		{ 1.0, 1.0, 1.0, 1.0, 1.0 };
		pnlFilters.setLayout(gbl_pnlFilters);

		JButton btnViewAll = new JButton("View All Auctions");
		btnViewAll.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				clientComms.sendMessage(new Message(MessageType.ITEM_REQUEST, RequestType.ALL_OPEN_ITEMS));
			}
		});
		btngrpFilters.add(btnViewAll);
		GridBagConstraints gbc_btnViewAll = new GridBagConstraints();
		gbc_btnViewAll.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewAll.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewAll.gridx = 0;
		gbc_btnViewAll.gridy = 0;
		pnlFilters.add(btnViewAll, gbc_btnViewAll);

		JButton btnViewSold = new JButton("View All Finished Auctions");
		btngrpFilters.add(btnViewSold);
		GridBagConstraints gbc_btnViewSold = new GridBagConstraints();
		gbc_btnViewSold.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewSold.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewSold.gridx = 0;
		gbc_btnViewSold.gridy = 1;
		pnlFilters.add(btnViewSold, gbc_btnViewSold);

		JButton btnFilterByID = new JButton("Filter by Item ID");
		btngrpFilters.add(btnFilterByID);
		GridBagConstraints gbc_btnFilterByID = new GridBagConstraints();
		gbc_btnFilterByID.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilterByID.insets = new Insets(0, 0, 5, 5);
		gbc_btnFilterByID.gridx = 0;
		gbc_btnFilterByID.gridy = 2;
		pnlFilters.add(btnFilterByID, gbc_btnFilterByID);

		txtFilterByID = new JTextField();
		GridBagConstraints gbc_txtFilterByID = new GridBagConstraints();
		gbc_txtFilterByID.insets = new Insets(0, 0, 5, 5);
		gbc_txtFilterByID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFilterByID.gridx = 1;
		gbc_txtFilterByID.gridy = 2;
		pnlFilters.add(txtFilterByID, gbc_txtFilterByID);
		txtFilterByID.setColumns(10);

		JButton btnFilterBySeller = new JButton("Filter by Seller");
		btngrpFilters.add(btnFilterBySeller);
		GridBagConstraints gbc_btnFilterBySeller = new GridBagConstraints();
		gbc_btnFilterBySeller.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilterBySeller.insets = new Insets(0, 0, 5, 5);
		gbc_btnFilterBySeller.gridx = 0;
		gbc_btnFilterBySeller.gridy = 3;
		pnlFilters.add(btnFilterBySeller, gbc_btnFilterBySeller);

		txtFilterBySeller = new JTextField();
		GridBagConstraints gbc_txtFilterBySeller = new GridBagConstraints();
		gbc_txtFilterBySeller.insets = new Insets(0, 0, 5, 5);
		gbc_txtFilterBySeller.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFilterBySeller.gridx = 1;
		gbc_txtFilterBySeller.gridy = 3;
		pnlFilters.add(txtFilterBySeller, gbc_txtFilterBySeller);
		txtFilterBySeller.setColumns(10);

		JButton btnFilterByCategory = new JButton("Filter by Catgeory");
		btngrpFilters.add(btnFilterByCategory);
		GridBagConstraints gbc_btnFilterByCategory = new GridBagConstraints();
		gbc_btnFilterByCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilterByCategory.insets = new Insets(0, 0, 0, 5);
		gbc_btnFilterByCategory.gridx = 0;
		gbc_btnFilterByCategory.gridy = 4;
		pnlFilters.add(btnFilterByCategory, gbc_btnFilterByCategory);

		JComboBox<Category> cmbFilterBycategory = new JComboBox<Category>();
		cmbFilterBycategory.setModel(new DefaultComboBoxModel<Category>(Category.values()));
		GridBagConstraints gbc_cmbFilterBycategory = new GridBagConstraints();
		gbc_cmbFilterBycategory.insets = new Insets(0, 0, 0, 5);
		gbc_cmbFilterBycategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbFilterBycategory.gridx = 1;
		gbc_cmbFilterBycategory.gridy = 4;
		pnlFilters.add(cmbFilterBycategory, gbc_cmbFilterBycategory);

		JPanel pnlSubmitItem = new JPanel();
		frmClient.getContentPane().add(pnlSubmitItem, "pnlSubmitItem");
		GridBagLayout gbl_pnlSubmitItem = new GridBagLayout();
		gbl_pnlSubmitItem.columnWidths = new int[]
		{ 0, 0, 0, 0, 30 };
		gbl_pnlSubmitItem.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlSubmitItem.columnWeights = new double[]
		{ 0.0, 1.0, 1.0, 1.0 };
		gbl_pnlSubmitItem.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlSubmitItem.setLayout(gbl_pnlSubmitItem);

		JLabel lblSubmitAnItem = new JLabel("Submit an Item");
		lblSubmitAnItem.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblSubmitAnItem = new GridBagConstraints();
		gbc_lblSubmitAnItem.anchor = GridBagConstraints.WEST;
		gbc_lblSubmitAnItem.insets = new Insets(15, 0, 15, 5);
		gbc_lblSubmitAnItem.gridx = 1;
		gbc_lblSubmitAnItem.gridy = 2;
		pnlSubmitItem.add(lblSubmitAnItem, gbc_lblSubmitAnItem);

		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 10, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 3;
		pnlSubmitItem.add(lblName, gbc_lblName);

		txtName = new JTextField();
		txtName.setFont(UIManager.getFont("TextField.font"));
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.BOTH;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 3;
		pnlSubmitItem.add(txtName, gbc_txtName);
		txtName.setColumns(10);

		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 5, 5, 5);
		gbc_lblDescription.anchor = GridBagConstraints.EAST;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 5;
		pnlSubmitItem.add(lblDescription, gbc_lblDescription);

		txtDescription = new JTextField();
		txtDescription.setFont(UIManager.getFont("TextField.font"));
		GridBagConstraints gbc_txtDescription = new GridBagConstraints();
		gbc_txtDescription.insets = new Insets(0, 0, 5, 5);
		gbc_txtDescription.fill = GridBagConstraints.BOTH;
		gbc_txtDescription.gridx = 1;
		gbc_txtDescription.gridy = 5;
		pnlSubmitItem.add(txtDescription, gbc_txtDescription);
		txtDescription.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Category");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 7;
		pnlSubmitItem.add(lblNewLabel_1, gbc_lblNewLabel_1);

		cmbCategory = new JComboBox<Category>();
		cmbCategory.setModel(new DefaultComboBoxModel<Category>(Category.values()));
		GridBagConstraints gbc_cmbCategory = new GridBagConstraints();
		gbc_cmbCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCategory.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCategory.gridx = 1;
		gbc_cmbCategory.gridy = 7;
		pnlSubmitItem.add(cmbCategory, gbc_cmbCategory);

		JLabel lblStartDate = new JLabel("Start Date");
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.insets = new Insets(0, 5, 5, 5);
		gbc_lblStartDate.anchor = GridBagConstraints.EAST;
		gbc_lblStartDate.gridx = 0;
		gbc_lblStartDate.gridy = 9;
		pnlSubmitItem.add(lblStartDate, gbc_lblStartDate);

		spnStartDate = new JSpinner();
		spnStartDate.setModel(new SpinnerDateModel(new Date(1428706800000L), new Date(1428706800000L), null, Calendar.DAY_OF_YEAR));
		spnStartDate.setEditor(new JSpinner.DateEditor(spnStartDate, "dd-MM-yyyy"));
		GridBagConstraints gbc_spnStartDate = new GridBagConstraints();
		gbc_spnStartDate.anchor = GridBagConstraints.WEST;
		gbc_spnStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_spnStartDate.gridx = 1;
		gbc_spnStartDate.gridy = 9;
		pnlSubmitItem.add(spnStartDate, gbc_spnStartDate);

		JLabel lblStartTime = new JLabel("Start Time");
		GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
		gbc_lblStartTime.anchor = GridBagConstraints.EAST;
		gbc_lblStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartTime.gridx = 2;
		gbc_lblStartTime.gridy = 9;
		pnlSubmitItem.add(lblStartTime, gbc_lblStartTime);

		spnStartTime = new JSpinner();
		spnStartTime.setModel(new SpinnerDateModel(new Date(1428966000000L), null, null, Calendar.HOUR_OF_DAY));
		spnStartTime.setEditor(new JSpinner.DateEditor(spnStartTime, "hh:mm"));
		GridBagConstraints gbc_spnStartTime = new GridBagConstraints();
		gbc_spnStartTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_spnStartTime.gridx = 3;
		gbc_spnStartTime.gridy = 9;
		pnlSubmitItem.add(spnStartTime, gbc_spnStartTime);

		JLabel lblEndDate = new JLabel("End Date");
		GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
		gbc_lblEndDate.insets = new Insets(0, 5, 5, 5);
		gbc_lblEndDate.anchor = GridBagConstraints.EAST;
		gbc_lblEndDate.gridx = 0;
		gbc_lblEndDate.gridy = 11;
		pnlSubmitItem.add(lblEndDate, gbc_lblEndDate);

		spnEndDate = new JSpinner();
		spnEndDate.setModel(new SpinnerDateModel(new Date(1428706800000L), new Date(1428706800000L), null, Calendar.DAY_OF_YEAR));
		spnEndDate.setEditor(new JSpinner.DateEditor(spnEndDate, "dd-MM-yyyy"));
		GridBagConstraints gbc_spnEndDate = new GridBagConstraints();
		gbc_spnEndDate.anchor = GridBagConstraints.WEST;
		gbc_spnEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_spnEndDate.gridx = 1;
		gbc_spnEndDate.gridy = 11;
		pnlSubmitItem.add(spnEndDate, gbc_spnEndDate);

		JLabel lblEndTime = new JLabel("End Time");
		GridBagConstraints gbc_lblEndTime = new GridBagConstraints();
		gbc_lblEndTime.anchor = GridBagConstraints.EAST;
		gbc_lblEndTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndTime.gridx = 2;
		gbc_lblEndTime.gridy = 11;
		pnlSubmitItem.add(lblEndTime, gbc_lblEndTime);

		spnEndTime = new JSpinner();
		spnEndTime.setModel(new SpinnerDateModel(new Date(1428966000000L), null, null, Calendar.HOUR_OF_DAY));
		spnEndTime.setEditor(new JSpinner.DateEditor(spnEndTime, "hh:mm"));
		GridBagConstraints gbc_spnEndTime = new GridBagConstraints();
		gbc_spnEndTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnEndTime.insets = new Insets(0, 0, 5, 5);
		gbc_spnEndTime.gridx = 3;
		gbc_spnEndTime.gridy = 11;
		pnlSubmitItem.add(spnEndTime, gbc_spnEndTime);

		JLabel lblReservePrice = new JLabel("Reserve price (Pounds.pennies)");
		GridBagConstraints gbc_lblReservePrice = new GridBagConstraints();
		gbc_lblReservePrice.insets = new Insets(0, 5, 5, 5);
		gbc_lblReservePrice.anchor = GridBagConstraints.EAST;
		gbc_lblReservePrice.gridx = 0;
		gbc_lblReservePrice.gridy = 13;
		pnlSubmitItem.add(lblReservePrice, gbc_lblReservePrice);

		txtReservePrice = new JTextField();
		GridBagConstraints gbc_txtReservePrice = new GridBagConstraints();
		gbc_txtReservePrice.insets = new Insets(0, 0, 5, 5);
		gbc_txtReservePrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtReservePrice.gridx = 1;
		gbc_txtReservePrice.gridy = 13;
		pnlSubmitItem.add(txtReservePrice, gbc_txtReservePrice);
		txtReservePrice.setColumns(10);

		JButton btnSubmitItem = new JButton("Submit Item");
		btnSubmitItem.addActionListener(new SubmitItemAction());
		btnSubmitItem.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnSubmitItem = new GridBagConstraints();
		gbc_btnSubmitItem.insets = new Insets(0, 0, 30, 5);
		gbc_btnSubmitItem.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnSubmitItem.gridx = 1;
		gbc_btnSubmitItem.gridy = 15;
		pnlSubmitItem.add(btnSubmitItem, gbc_btnSubmitItem);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				changeCard("pnlMain");
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 16;
		pnlSubmitItem.add(btnNewButton, gbc_btnNewButton);

		JMenuBar mnuMenuBar = new JMenuBar();
		frmClient.setJMenuBar(mnuMenuBar);

		/*
		 * Ask server for number of auctions in history, set item.counter to this value Ask server for number of users
		 * in history, set user.counter to this value
		 */

		// frmClient.pack();
	}

	public void changeCard(String destinationPanel)
	{
		lytCard.show(frmClient.getContentPane(), destinationPanel);
		frmClient.pack();
	}

	public boolean addAuctionToCache(Item item)
	{
		return auctionCache.add(item);
	}


	public boolean refreshAuctionList()
	{
		auctionModel.clear();
		for (Item item : auctionCache)
		{
			auctionModel.addElement(item.getName());
		}
		return (auctionModel.size() == auctionCache.size());
	}


	public static class ResizingCardLayout extends CardLayout
	{

		private static final long serialVersionUID = -7578838449014921005L;


		public ResizingCardLayout()
		{
			super();
		}


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

	private class SubmitItemAction implements ActionListener
	{

		private Item newItem;


		@Override
		public void actionPerformed(ActionEvent e)
		{
			newItem = new Item(null, null, null, 0, null, null, null, null);

			Date rawStartDate, rawEndDate, rawStartTime, rawEndTime;
			LocalDate startDate, endDate;
			LocalTime startTime, endTime;

			newItem.setName(txtName.getText());
			newItem.setDescription(txtDescription.getText());
			newItem.setCategory(Category.valueOf(cmbCategory.getSelectedItem().toString()));

			rawStartDate = (Date) spnStartDate.getValue();
			rawStartTime = (Date) spnStartTime.getValue();
			rawEndDate = (Date) spnEndDate.getValue();
			rawEndTime = (Date) spnEndTime.getValue();

			startDate = rawStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			endDate = rawEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			startTime = rawStartTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			endTime = rawEndTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

			newItem.setStartTime(LocalDateTime.of(startDate, startTime));
			newItem.setEndTime(LocalDateTime.of(endDate, endTime));
			newItem.setReservePrice(new Money(Currency.getInstance("GBP"), Double.parseDouble(txtReservePrice.getText())));

			System.out.println(newItem.getName() + " " + newItem.getDescription() + " " + newItem.getCategory().toString() + " "
					+ newItem.getStartTime().toString() + " " + newItem.getEndTime().toString() + " " + newItem.getReservePrice().getAmount());
			clientComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, newItem));
		}

	}

}
