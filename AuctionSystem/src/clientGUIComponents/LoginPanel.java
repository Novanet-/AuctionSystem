package clientGUIComponents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import commLayer.Hasher;
import commLayer.Message;
import commLayer.MessageType;
import entities.User;
import applications.ClientGUI;

import java.awt.Font;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginPanel extends JPanel
{

	private static final long serialVersionUID = -996719470440190853L;

	private ClientGUI clientGUI;

	private JTextField txtFirstname;
	private JTextField txtPassword;
	private JTextField txtSurname;


	public LoginPanel(ClientGUI clientGUI)
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
		this.setToolTipText("Login Screen");
		GridBagLayout gbl_pnlLogin = new GridBagLayout();
		gbl_pnlLogin.columnWidths = new int[]
		{ 30, 0, 30, 30, 30 };
		gbl_pnlLogin.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlLogin.columnWeights = new double[]
		{ 1.0, 0.0, 0.0, 1.0 };
		gbl_pnlLogin.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_pnlLogin);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.insets = new Insets(5, 0, 5, 5);
		gbc_lblLogin.gridx = 2;
		gbc_lblLogin.gridy = 0;
		add(lblLogin, gbc_lblLogin);

		JLabel lblUsername = new JLabel("First Name");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 2;
		this.add(lblUsername, gbc_lblUsername);

		txtFirstname = new JTextField();
		GridBagConstraints gbc_txtFirstname = new GridBagConstraints();
		gbc_txtFirstname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFirstname.insets = new Insets(0, 0, 5, 5);
		gbc_txtFirstname.gridx = 2;
		gbc_txtFirstname.gridy = 2;
		this.add(txtFirstname, gbc_txtFirstname);
		txtFirstname.setColumns(10);

		JLabel lblSurname = new JLabel("Surname");
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.EAST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 1;
		gbc_lblSurname.gridy = 3;
		add(lblSurname, gbc_lblSurname);

		txtSurname = new JTextField();
		GridBagConstraints gbc_txtSurname = new GridBagConstraints();
		gbc_txtSurname.insets = new Insets(0, 0, 5, 5);
		gbc_txtSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSurname.gridx = 2;
		gbc_txtSurname.gridy = 3;
		add(txtSurname, gbc_txtSurname);
		txtSurname.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 4;
		this.add(lblPassword, gbc_lblPassword);

		txtPassword = new JTextField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 4;
		this.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);

		JButton btnSubmitLogin = new JButton("Login");
		btnSubmitLogin.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					User loginUser = new User(txtFirstname.getText(), txtSurname.getText(), Hasher.getPasswordHash(txtPassword.getText()));
					clientGUI.sendMessage(new Message(MessageType.LOGIN_REQUEST, loginUser));
				}
				catch (NoSuchAlgorithmException | UnsupportedEncodingException e1)
				{
					e1.printStackTrace();
				}
			}

		});
		GridBagConstraints gbc_btnSubmitLogin = new GridBagConstraints();
		gbc_btnSubmitLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmitLogin.gridx = 2;
		gbc_btnSubmitLogin.gridy = 6;
		this.add(btnSubmitLogin, gbc_btnSubmitLogin);

		JButton btnSwitchPanelsLogin = new JButton("Switch Panels");
		btnSwitchPanelsLogin.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				clientGUI.changeCard("pnlMain");
			}

		});
		GridBagConstraints gbc_btnSwitchPanelsLogin = new GridBagConstraints();
		gbc_btnSwitchPanelsLogin.anchor = GridBagConstraints.NORTH;
		gbc_btnSwitchPanelsLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnSwitchPanelsLogin.gridx = 2;
		gbc_btnSwitchPanelsLogin.gridy = 7;
		this.add(btnSwitchPanelsLogin, gbc_btnSwitchPanelsLogin);

		JButton btnRegisterNewAccount = new JButton("Register New Account");
		btnRegisterNewAccount.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					User newUser = new User(txtFirstname.getText(), txtSurname.getText(), Hasher.getPasswordHash(txtPassword.getText()));
					clientGUI.sendMessage(new Message(MessageType.USER_DELIVERY, newUser));
				}
				catch (NoSuchAlgorithmException | UnsupportedEncodingException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnRegisterNewAccount = new GridBagConstraints();
		gbc_btnRegisterNewAccount.anchor = GridBagConstraints.NORTH;
		gbc_btnRegisterNewAccount.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegisterNewAccount.gridx = 2;
		gbc_btnRegisterNewAccount.gridy = 8;
		add(btnRegisterNewAccount, gbc_btnRegisterNewAccount);
	}

}
