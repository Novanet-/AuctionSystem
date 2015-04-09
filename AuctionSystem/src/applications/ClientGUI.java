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

import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;

public class ClientGUI
{

	private JFrame frmClient;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField textField;
	private JTextField textField_1;
	private final ButtonGroup btngrpFilters = new ButtonGroup();


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

		frmClient = new JFrame();
		frmClient.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmClient.setIconImage(Toolkit.getDefaultToolkit().getImage(ClientGUI.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		frmClient.setTitle("Auction ClientGUI");
		frmClient.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmClient.setBounds(100, 100, 516, 397);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ResizingCardLayout lytCard = new ResizingCardLayout();
		frmClient.getContentPane().setLayout(lytCard);

		JPanel pnlLogin = new JPanel();
		pnlLogin.setToolTipText("Login Screen");
		frmClient.getContentPane().add(pnlLogin, "name_13954175131700");
		GridBagLayout gbl_pnlLogin = new GridBagLayout();
		gbl_pnlLogin.columnWidths = new int[]
		{ 30, 30, 30 };
		gbl_pnlLogin.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlLogin.columnWeights = new double[]
		{ 1.0, 1.0, 1.0 };
		gbl_pnlLogin.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlLogin.setLayout(gbl_pnlLogin);

		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 2;
		pnlLogin.add(lblUsername, gbc_lblUsername);

		txtUsername = new JTextField();
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 2;
		pnlLogin.add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 4;
		pnlLogin.add(lblPassword, gbc_lblPassword);

		txtPassword = new JTextField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 4;
		pnlLogin.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 6;
		pnlLogin.add(btnSubmit, gbc_btnSubmit);

		JButton btnSwitchPanelsLogin = new JButton("Switch Panels");
		btnSwitchPanelsLogin.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				lytCard.next(frmClient.getContentPane());
				frmClient.pack();
			}
		});
		GridBagConstraints gbc_btnSwitchPanelsLogin = new GridBagConstraints();
		gbc_btnSwitchPanelsLogin.anchor = GridBagConstraints.NORTH;
		gbc_btnSwitchPanelsLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnSwitchPanelsLogin.gridx = 1;
		gbc_btnSwitchPanelsLogin.gridy = 7;
		pnlLogin.add(btnSwitchPanelsLogin, gbc_btnSwitchPanelsLogin);
		

		JPanel pnlMain = new JPanel();
		pnlMain.setPreferredSize(new Dimension(550, 450));
		frmClient.getContentPane().add(pnlMain, "name_13954130542009");
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.X_AXIS));

		JPanel pnlItemList = new JPanel();
		pnlMain.add(pnlItemList);
		GridBagLayout gbl_pnlItemList = new GridBagLayout();
		gbl_pnlItemList.columnWidths = new int[]
		{ 200 };
		gbl_pnlItemList.rowHeights = new int[]
		{ 30, 0, 0 };
		gbl_pnlItemList.columnWeights = new double[]
		{ 1.0 };
		gbl_pnlItemList.rowWeights = new double[]
		{ 0.0, 1.0, 0.0 };
		pnlItemList.setLayout(gbl_pnlItemList);
		
		JScrollPane scrlAuctionList = new JScrollPane();
		GridBagConstraints gbc_scrlAuctionList = new GridBagConstraints();
		gbc_scrlAuctionList.fill = GridBagConstraints.BOTH;
		gbc_scrlAuctionList.insets = new Insets(0, 5, 5, 5);
		gbc_scrlAuctionList.gridx = 0;
		gbc_scrlAuctionList.gridy = 1;
		pnlItemList.add(scrlAuctionList, gbc_scrlAuctionList);

		JList<String> lstAuctionItems = new JList<String>();
		scrlAuctionList.setViewportView(lstAuctionItems);
		lstAuctionItems.setVisibleRowCount(20);
		lstAuctionItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstAuctionItems.setLayoutOrientation(JList.VERTICAL_WRAP);
		lstAuctionItems.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel lblItemsForAuction = new JLabel("Items for Auction");
		GridBagConstraints gbc_lblItemsForAuction = new GridBagConstraints();
		gbc_lblItemsForAuction.insets = new Insets(5, 5, 5, 0);
		gbc_lblItemsForAuction.gridx = 0;
		gbc_lblItemsForAuction.gridy = 0;
		pnlItemList.add(lblItemsForAuction, gbc_lblItemsForAuction);
		lblItemsForAuction.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblItemsForAuction.setLabelFor(lstAuctionItems);

		JButton btnSubmitItem = new JButton("btnSubmitItem");
		GridBagConstraints gbc_btnSubmitItem = new GridBagConstraints();
		gbc_btnSubmitItem.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmitItem.anchor = GridBagConstraints.NORTH;
		gbc_btnSubmitItem.gridx = 0;
		gbc_btnSubmitItem.gridy = 2;
		pnlItemList.add(btnSubmitItem, gbc_btnSubmitItem);

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

		JButton btnViewAll = new JButton("btnViewAll");
		btngrpFilters.add(btnViewAll);
		GridBagConstraints gbc_btnViewAll = new GridBagConstraints();
		gbc_btnViewAll.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewAll.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewAll.gridx = 0;
		gbc_btnViewAll.gridy = 0;
		pnlFilters.add(btnViewAll, gbc_btnViewAll);

		JButton btnViewSold = new JButton("btnViewSold");
		btngrpFilters.add(btnViewSold);
		GridBagConstraints gbc_btnViewSold = new GridBagConstraints();
		gbc_btnViewSold.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewSold.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewSold.gridx = 0;
		gbc_btnViewSold.gridy = 1;
		pnlFilters.add(btnViewSold, gbc_btnViewSold);

		JButton btnFilterByID = new JButton("btnFilterByID");
		btngrpFilters.add(btnFilterByID);
		GridBagConstraints gbc_btnFilterByID = new GridBagConstraints();
		gbc_btnFilterByID.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilterByID.insets = new Insets(0, 0, 5, 5);
		gbc_btnFilterByID.gridx = 0;
		gbc_btnFilterByID.gridy = 2;
		pnlFilters.add(btnFilterByID, gbc_btnFilterByID);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		pnlFilters.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnFilterBySeller = new JButton("btnFilterBySeller");
		btngrpFilters.add(btnFilterBySeller);
		GridBagConstraints gbc_btnFilterBySeller = new GridBagConstraints();
		gbc_btnFilterBySeller.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilterBySeller.insets = new Insets(0, 0, 5, 5);
		gbc_btnFilterBySeller.gridx = 0;
		gbc_btnFilterBySeller.gridy = 3;
		pnlFilters.add(btnFilterBySeller, gbc_btnFilterBySeller);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		pnlFilters.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JButton btnFilterByCategory = new JButton("btnFilterByCategory");
		btngrpFilters.add(btnFilterByCategory);
		GridBagConstraints gbc_btnFilterByCategory = new GridBagConstraints();
		gbc_btnFilterByCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilterByCategory.insets = new Insets(0, 0, 0, 5);
		gbc_btnFilterByCategory.gridx = 0;
		gbc_btnFilterByCategory.gridy = 4;
		pnlFilters.add(btnFilterByCategory, gbc_btnFilterByCategory);

		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 4;
		pnlFilters.add(comboBox, gbc_comboBox);
		
		JPanel pnlSubmitItem = new JPanel();
		frmClient.getContentPane().add(pnlSubmitItem, "name_20844679967660");
		GridBagLayout gbl_pnlSubmitItem = new GridBagLayout();
		gbl_pnlSubmitItem.columnWidths = new int[]{0};
		gbl_pnlSubmitItem.rowHeights = new int[]{0};
		gbl_pnlSubmitItem.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_pnlSubmitItem.rowWeights = new double[]{Double.MIN_VALUE};
		pnlSubmitItem.setLayout(gbl_pnlSubmitItem);
		
		JMenuBar mnuMenuBar = new JMenuBar();
		frmClient.setJMenuBar(mnuMenuBar);
		
		
		/*
		 * Ask server for number of auctions in history, set item.counter to this value
		 * Ask server for number of users in history, set user.counter to this value
		 * 
		 * 
		 * 
		 */
		

//		frmClient.pack();
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
}
