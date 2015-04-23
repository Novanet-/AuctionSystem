package clientGUIComponents;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utilities.Category;
import utilities.Money;
import applications.ClientGUI;
import commLayer.Message;
import commLayer.MessageType;
import commLayer.RequestType;
import entities.Bid;
import entities.Item;

import java.awt.FlowLayout;
import java.awt.Component;

import javax.swing.Box;

public class MainPanel extends JPanel
{

	private static final long serialVersionUID = -1651115114692051343L;

	private ClientGUI clientGUI;

	private JTextField txtFilterByID;
	private JTextField txtFilterBySeller;
	private final ButtonGroup btngrpFilters = new ButtonGroup();

	private JList<String> lstAuctionItems;
	private DefaultListModel<String> auctionModel;

	private boolean listItemSelected;

	private JTextArea txtAuctionDetails;
	private JTextField txtMakeBid;


	public MainPanel(ClientGUI clientGUI)
	{
		super();
		this.clientGUI = clientGUI;
		initialize();
	}


	/**
	 * Initialises components of the panel and sets their constraints and listeners
	 */
	public void initialize()
	{
		this.setPreferredSize(new Dimension(550, 450));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		listItemSelected = false;

		JPanel pnlItemList = new JPanel();
		this.add(pnlItemList);
		GridBagLayout gbl_pnlItemList = new GridBagLayout();
		gbl_pnlItemList.columnWidths = new int[]
		{ 200 };
		gbl_pnlItemList.rowHeights = new int[]
		{ 30, 0, 0, 0, 0 };
		gbl_pnlItemList.columnWeights = new double[]
		{ 1.0 };
		gbl_pnlItemList.rowWeights = new double[]
		{ 0.0, 1.0, 0.0, 1.0, 0.0 };
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

		lstAuctionItems = new JList<>();
		auctionModel = new DefaultListModel<>();
		lstAuctionItems.setModel(auctionModel);
		lstAuctionItems.addListSelectionListener(new AuctionSelectedListener());

		lstAuctionItems.addMouseListener(new MouseAdapter()
		{

			@Override
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

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				clientGUI.changeCard("pnlSubmitItem");
			}
		});
		GridBagConstraints gbc_btnOpenSubmitForm = new GridBagConstraints();
		gbc_btnOpenSubmitForm.anchor = GridBagConstraints.NORTH;
		gbc_btnOpenSubmitForm.insets = new Insets(0, 5, 20, 5);
		gbc_btnOpenSubmitForm.gridx = 0;
		gbc_btnOpenSubmitForm.gridy = 2;
		pnlItemList.add(btnOpenSubmitForm, gbc_btnOpenSubmitForm);

		txtAuctionDetails = new JTextArea();
		txtAuctionDetails.setLineWrap(true);
		txtAuctionDetails.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_txtAuctionDetails = new GridBagConstraints();
		gbc_txtAuctionDetails.insets = new Insets(0, 5, 5, 5);
		gbc_txtAuctionDetails.fill = GridBagConstraints.BOTH;
		gbc_txtAuctionDetails.gridx = 0;
		gbc_txtAuctionDetails.gridy = 3;
		pnlItemList.add(txtAuctionDetails, gbc_txtAuctionDetails);

		JPanel pnlMakeBid = new JPanel();
		GridBagConstraints gbc_pnlMakeBid = new GridBagConstraints();
		gbc_pnlMakeBid.insets = new Insets(0, 5, 5, 5);
		gbc_pnlMakeBid.fill = GridBagConstraints.BOTH;
		gbc_pnlMakeBid.gridx = 0;
		gbc_pnlMakeBid.gridy = 4;
		pnlItemList.add(pnlMakeBid, gbc_pnlMakeBid);
		pnlMakeBid.setLayout(new BoxLayout(pnlMakeBid, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		pnlMakeBid.add(horizontalGlue);

		JButton btnBidOnItem = new JButton("Bid on Auction");
		pnlMakeBid.add(btnBidOnItem);

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		pnlMakeBid.add(horizontalGlue_2);

		txtMakeBid = new JTextField();
		pnlMakeBid.add(txtMakeBid);
		txtMakeBid.setColumns(10);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		pnlMakeBid.add(horizontalGlue_1);
		btnBidOnItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Item selectedAuction = clientGUI.getAuctionFromCache(lstAuctionItems.getSelectedIndex());
				double amountBid = Double.parseDouble(txtMakeBid.getText());
				Bid newBid = new Bid(1, selectedAuction.getItemId(), new Money(Currency.getInstance("GBP"), amountBid));
				clientGUI.sendMessage(new Message(MessageType.BID_DELIVERY, newBid));
			}
		});

		JPanel pnlFilters = new JPanel();
		this.add(pnlFilters);
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

			@Override
			public void actionPerformed(ActionEvent e)
			{
				clientGUI.sendMessage(new Message(MessageType.ITEM_REQUEST, RequestType.ALL_OPEN_ITEMS));
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

		JComboBox<Category> cmbFilterBycategory = new JComboBox<>();
		cmbFilterBycategory.setModel(new DefaultComboBoxModel<>(Category.values()));
		GridBagConstraints gbc_cmbFilterBycategory = new GridBagConstraints();
		gbc_cmbFilterBycategory.insets = new Insets(0, 0, 0, 5);
		gbc_cmbFilterBycategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbFilterBycategory.gridx = 1;
		gbc_cmbFilterBycategory.gridy = 4;
		pnlFilters.add(cmbFilterBycategory, gbc_cmbFilterBycategory);
	}


	/**
	 * Refreshes the list of auctions, clearing it and then re-adding elements from the auction cache to it
	 * 
	 * @return boolean - true if the auction list is the same size as the auction cache
	 */
	public boolean refreshAuctionList(ArrayList<Item> auctionCache, RequestType filterType)
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{

				@Override
				public void run()
				{
					auctionModel.clear();
					lstAuctionItems.clearSelection();
					txtAuctionDetails.setText("");
					for (Item item : auctionCache)
					{
						if (filterType == RequestType.ALL_OPEN_ITEMS)
						{
							auctionModel.addElement(item.getName());
						}
					}
				}
			});
		}
		catch (InvocationTargetException | InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (auctionModel.size() == auctionCache.size());
	}


	private final class AuctionSelectedListener implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			if (!lstAuctionItems.isSelectionEmpty())
			{
				listItemSelected = true;
				Item selectedAuction = clientGUI.getAuctionFromCache(lstAuctionItems.getSelectedIndex());
				txtAuctionDetails.setText("Item: " + selectedAuction.getName());
				txtAuctionDetails.append("\n" + "Description: " + selectedAuction.getDescription());
				txtAuctionDetails.append("\n" + "Category: " + selectedAuction.getCategory().toString());
				txtAuctionDetails.append("\n" + "Start Time: "
						+ selectedAuction.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm")));
				txtAuctionDetails.append("\n" + "End Time: "
						+ selectedAuction.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm")));
				Money highestBid;
				if (selectedAuction.getBids().isEmpty())
				{
					highestBid = new Money(Currency.getInstance("GBP"), 0);
				}
				else
				{
					highestBid = new Money(Currency.getInstance("GBP"), selectedAuction.getBids().peek().getAmount().getValue());
				}
				txtAuctionDetails.append("\n" + "Highest Bid: " +highestBid.getCurrencyType().getSymbol() + highestBid.getValue());
			}
		}
	}

}
