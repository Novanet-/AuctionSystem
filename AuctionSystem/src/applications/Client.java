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

public class Client
{

	private JFrame frmClient;
	private JTextField txtUsername;
	private JTextField txtPassword;


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
					Client window = new Client();
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
	public Client()
	{
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmClient = new JFrame();
		frmClient.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmClient.setIconImage(Toolkit.getDefaultToolkit().getImage(Client.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		frmClient.setTitle("Auction Client");
		frmClient.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmClient.setBounds(100, 100, 550, 450);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ResizingCardLayout lytCard = new ResizingCardLayout();
		frmClient.getContentPane().setLayout(lytCard);

		JPanel pnlLogin = new JPanel();
		pnlLogin.setToolTipText("Login Screen");
		frmClient.getContentPane().add(pnlLogin, "name_13954175131700");
		GridBagLayout gbl_pnlLogin = new GridBagLayout();
		gbl_pnlLogin.columnWidths = new int[]
		{ 0, 0, 0, 0, 0, 0 };
		gbl_pnlLogin.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlLogin.columnWeights = new double[]
		{ 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_txtUsername.gridx = 2;
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
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 4;
		pnlLogin.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 2;
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
		gbc_btnSwitchPanelsLogin.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSwitchPanelsLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnSwitchPanelsLogin.gridx = 4;
		gbc_btnSwitchPanelsLogin.gridy = 6;
		pnlLogin.add(btnSwitchPanelsLogin, gbc_btnSwitchPanelsLogin);

		JPanel pnlMain = new JPanel();
		frmClient.getContentPane().add(pnlMain, "name_13954130542009");
		GridBagLayout gbl_pnlMain = new GridBagLayout();
		gbl_pnlMain.columnWidths = new int[] {0, 30, 30};
		gbl_pnlMain.rowHeights = new int[] {30, 30};
		gbl_pnlMain.columnWeights = new double[]{1.0, 1.0, 0.0};
		gbl_pnlMain.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0};
		pnlMain.setLayout(gbl_pnlMain);
										
												JLabel lblItemsForAuction = new JLabel("Items for Auction");
												lblItemsForAuction.setFont(new Font("Segoe UI", Font.PLAIN, 12));
												GridBagConstraints gbc_lblItemsForAuction = new GridBagConstraints();
												gbc_lblItemsForAuction.insets = new Insets(0, 50, 5, 5);
												gbc_lblItemsForAuction.gridx = 0;
												gbc_lblItemsForAuction.gridy = 1;
												pnlMain.add(lblItemsForAuction, gbc_lblItemsForAuction);
												lblItemsForAuction.setLabelFor(lstAuctionItems);
										
										JList list = new JList();
										list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
										GridBagConstraints gbc_list = new GridBagConstraints();
										gbc_list.gridheight = 3;
										gbc_list.insets = new Insets(0, 50, 25, 5);
										gbc_list.fill = GridBagConstraints.BOTH;
										gbc_list.gridx = 0;
										gbc_list.gridy = 2;
										pnlMain.add(list, gbc_list);
										
												JButton btnSwitchPanelsMain = new JButton("Switch Panels");
												btnSwitchPanelsMain.setFont(new Font("Segoe UI", Font.PLAIN, 12));
												btnSwitchPanelsMain.addActionListener(new ActionListener()
												{

													public void actionPerformed(ActionEvent arg0)
													{
														lytCard.next(frmClient.getContentPane());
														frmClient.pack();
													}
												});
												GridBagConstraints gbc_btnSwitchPanelsMain = new GridBagConstraints();
												gbc_btnSwitchPanelsMain.anchor = GridBagConstraints.NORTH;
												gbc_btnSwitchPanelsMain.insets = new Insets(0, 0, 5, 5);
												gbc_btnSwitchPanelsMain.gridx = 1;
												gbc_btnSwitchPanelsMain.gridy = 2;
												pnlMain.add(btnSwitchPanelsMain, gbc_btnSwitchPanelsMain);

		// frmClient.pack();
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
				// pref.width *= 1.5;
				pref.height += insets.top + insets.bottom;
				// pref.height *= 1.5;
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
