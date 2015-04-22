package clientGUIComponents;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;

import utilities.Category;
import utilities.Money;
import applications.ClientGUI;

import commLayer.Message;
import commLayer.MessageType;

import entities.Item;

public class SubmitPanel extends JPanel
{

	private static final long serialVersionUID = -6334501558318314753L;

	private ClientGUI clientGUI;

	private JTextField txtName;
	private JTextField txtDescription;
	private JTextField txtReservePrice;
	private JComboBox<Category> cmbCategory;
	private JSpinner spnStartDate;
	private JSpinner spnEndDate;
	private JSpinner spnStartTime;
	private JSpinner spnEndTime;


	public SubmitPanel(ClientGUI clientGUI)
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
		GridBagLayout gbl_pnlSubmitItem = new GridBagLayout();
		gbl_pnlSubmitItem.columnWidths = new int[]
		{ 0, 0, 0, 0, 30 };
		gbl_pnlSubmitItem.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlSubmitItem.columnWeights = new double[]
		{ 0.0, 1.0, 1.0, 1.0 };
		gbl_pnlSubmitItem.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_pnlSubmitItem);

		JLabel lblSubmitAnItem = new JLabel("Submit an Item");
		lblSubmitAnItem.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblSubmitAnItem = new GridBagConstraints();
		gbc_lblSubmitAnItem.anchor = GridBagConstraints.WEST;
		gbc_lblSubmitAnItem.insets = new Insets(15, 0, 15, 5);
		gbc_lblSubmitAnItem.gridx = 1;
		gbc_lblSubmitAnItem.gridy = 2;
		this.add(lblSubmitAnItem, gbc_lblSubmitAnItem);

		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 10, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 3;
		this.add(lblName, gbc_lblName);

		txtName = new JTextField();
		txtName.setFont(UIManager.getFont("TextField.font"));
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.BOTH;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 3;
		this.add(txtName, gbc_txtName);
		txtName.setColumns(10);

		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 5, 5, 5);
		gbc_lblDescription.anchor = GridBagConstraints.EAST;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 5;
		this.add(lblDescription, gbc_lblDescription);

		txtDescription = new JTextField();
		txtDescription.setFont(UIManager.getFont("TextField.font"));
		GridBagConstraints gbc_txtDescription = new GridBagConstraints();
		gbc_txtDescription.insets = new Insets(0, 0, 5, 5);
		gbc_txtDescription.fill = GridBagConstraints.BOTH;
		gbc_txtDescription.gridx = 1;
		gbc_txtDescription.gridy = 5;
		this.add(txtDescription, gbc_txtDescription);
		txtDescription.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Category");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 7;
		this.add(lblNewLabel_1, gbc_lblNewLabel_1);

		cmbCategory = new JComboBox<>();
		cmbCategory.setModel(new DefaultComboBoxModel<>(Category.values()));
		GridBagConstraints gbc_cmbCategory = new GridBagConstraints();
		gbc_cmbCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCategory.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCategory.gridx = 1;
		gbc_cmbCategory.gridy = 7;
		this.add(cmbCategory, gbc_cmbCategory);

		JLabel lblStartDate = new JLabel("Start Date");
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.insets = new Insets(0, 5, 5, 5);
		gbc_lblStartDate.anchor = GridBagConstraints.EAST;
		gbc_lblStartDate.gridx = 0;
		gbc_lblStartDate.gridy = 9;
		this.add(lblStartDate, gbc_lblStartDate);

		spnStartDate = new JSpinner();
		spnStartDate.setModel(new SpinnerDateModel(new Date(1428706800000L), new Date(1428706800000L), null, Calendar.DAY_OF_YEAR));
		spnStartDate.setEditor(new JSpinner.DateEditor(spnStartDate, "dd-MM-yyyy"));
		GridBagConstraints gbc_spnStartDate = new GridBagConstraints();
		gbc_spnStartDate.anchor = GridBagConstraints.WEST;
		gbc_spnStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_spnStartDate.gridx = 1;
		gbc_spnStartDate.gridy = 9;
		this.add(spnStartDate, gbc_spnStartDate);

		JLabel lblStartTime = new JLabel("Start Time");
		GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
		gbc_lblStartTime.anchor = GridBagConstraints.EAST;
		gbc_lblStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartTime.gridx = 2;
		gbc_lblStartTime.gridy = 9;
		this.add(lblStartTime, gbc_lblStartTime);

		spnStartTime = new JSpinner();
		spnStartTime.setModel(new SpinnerDateModel(new Date(1428966000000L), null, null, Calendar.HOUR_OF_DAY));
		spnStartTime.setEditor(new JSpinner.DateEditor(spnStartTime, "hh:mm"));
		GridBagConstraints gbc_spnStartTime = new GridBagConstraints();
		gbc_spnStartTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_spnStartTime.gridx = 3;
		gbc_spnStartTime.gridy = 9;
		this.add(spnStartTime, gbc_spnStartTime);

		JLabel lblEndDate = new JLabel("End Date");
		GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
		gbc_lblEndDate.insets = new Insets(0, 5, 5, 5);
		gbc_lblEndDate.anchor = GridBagConstraints.EAST;
		gbc_lblEndDate.gridx = 0;
		gbc_lblEndDate.gridy = 11;
		this.add(lblEndDate, gbc_lblEndDate);

		spnEndDate = new JSpinner();
		spnEndDate.setModel(new SpinnerDateModel(new Date(1428706800000L), new Date(1428706800000L), null, Calendar.DAY_OF_YEAR));
		spnEndDate.setEditor(new JSpinner.DateEditor(spnEndDate, "dd-MM-yyyy"));
		GridBagConstraints gbc_spnEndDate = new GridBagConstraints();
		gbc_spnEndDate.anchor = GridBagConstraints.WEST;
		gbc_spnEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_spnEndDate.gridx = 1;
		gbc_spnEndDate.gridy = 11;
		this.add(spnEndDate, gbc_spnEndDate);

		JLabel lblEndTime = new JLabel("End Time");
		GridBagConstraints gbc_lblEndTime = new GridBagConstraints();
		gbc_lblEndTime.anchor = GridBagConstraints.EAST;
		gbc_lblEndTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndTime.gridx = 2;
		gbc_lblEndTime.gridy = 11;
		this.add(lblEndTime, gbc_lblEndTime);

		spnEndTime = new JSpinner();
		spnEndTime.setModel(new SpinnerDateModel(new Date(1428966000000L), null, null, Calendar.HOUR_OF_DAY));
		spnEndTime.setEditor(new JSpinner.DateEditor(spnEndTime, "hh:mm"));
		GridBagConstraints gbc_spnEndTime = new GridBagConstraints();
		gbc_spnEndTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnEndTime.insets = new Insets(0, 0, 5, 5);
		gbc_spnEndTime.gridx = 3;
		gbc_spnEndTime.gridy = 11;
		this.add(spnEndTime, gbc_spnEndTime);

		JLabel lblReservePrice = new JLabel("Reserve price (Pounds.pennies)");
		GridBagConstraints gbc_lblReservePrice = new GridBagConstraints();
		gbc_lblReservePrice.insets = new Insets(0, 5, 5, 5);
		gbc_lblReservePrice.anchor = GridBagConstraints.EAST;
		gbc_lblReservePrice.gridx = 0;
		gbc_lblReservePrice.gridy = 13;
		this.add(lblReservePrice, gbc_lblReservePrice);

		txtReservePrice = new JTextField();
		GridBagConstraints gbc_txtReservePrice = new GridBagConstraints();
		gbc_txtReservePrice.insets = new Insets(0, 0, 5, 5);
		gbc_txtReservePrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtReservePrice.gridx = 1;
		gbc_txtReservePrice.gridy = 13;
		this.add(txtReservePrice, gbc_txtReservePrice);
		txtReservePrice.setColumns(10);

		JButton btnSubmitItem = new JButton("Submit Item");
		btnSubmitItem.addActionListener(new SubmitItemAction());
		btnSubmitItem.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnSubmitItem = new GridBagConstraints();
		gbc_btnSubmitItem.insets = new Insets(0, 0, 30, 5);
		gbc_btnSubmitItem.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnSubmitItem.gridx = 1;
		gbc_btnSubmitItem.gridy = 15;
		this.add(btnSubmitItem, gbc_btnSubmitItem);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				clientGUI.changeCard("pnlMain");
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 16;
		this.add(btnNewButton, gbc_btnNewButton);
	}


	private class SubmitItemAction implements ActionListener
	{

		private Item newItem;


		/**
		 * Submits an auction using the item details entered
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			boolean submitSuccesfull = submitAuction();
		}


		/**
		 * Creates a new Item object and sets it's properties as the values of the entry fields in the panel, then sends
		 * it to the server to be submitted
		 */
		private boolean submitAuction()
		{
			newItem = new Item(null, null, null, 0, null, null, null);

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
					+ newItem.getStartTime().toString() + " " + newItem.getEndTime().toString() + " " + newItem.getReservePrice().getValue());
			return clientGUI.sendMessage(new Message(MessageType.ITEM_DELIVERY, newItem));
		}

	}

}
