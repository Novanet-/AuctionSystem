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

import applications.ClientGUI;

public class LoginPanel extends JPanel
{

	private static final long serialVersionUID = -996719470440190853L;

	private ClientGUI clientGUI;

	private JTextField txtUsername;
	private JTextField txtPassword;


	public LoginPanel(ClientGUI clientGUI)
	{
		super();
		this.clientGUI = clientGUI;
		initialize();
	}


	public void initialize()
	{
		this.setToolTipText("Login Screen");
		GridBagLayout gbl_pnlLogin = new GridBagLayout();
		gbl_pnlLogin.columnWidths = new int[]
		{ 30, 30, 30 };
		gbl_pnlLogin.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlLogin.columnWeights = new double[]
		{ 1.0, 1.0, 1.0 };
		gbl_pnlLogin.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_pnlLogin);
		
		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 2;
		this.add(lblUsername, gbc_lblUsername);

		txtUsername = new JTextField();
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 2;
		this.add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 4;
		this.add(lblPassword, gbc_lblPassword);

		txtPassword = new JTextField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 4;
		this.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);

		JButton btnSubmitLogin = new JButton("Submit");
		GridBagConstraints gbc_btnSubmitLogin = new GridBagConstraints();
		gbc_btnSubmitLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmitLogin.gridx = 1;
		gbc_btnSubmitLogin.gridy = 6;
		this.add(btnSubmitLogin, gbc_btnSubmitLogin);

		JButton btnSwitchPanelsLogin = new JButton("Switch Panels");
		btnSwitchPanelsLogin.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				clientGUI.changeCard("pnlMain");
			}

		});
		GridBagConstraints gbc_btnSwitchPanelsLogin = new GridBagConstraints();
		gbc_btnSwitchPanelsLogin.anchor = GridBagConstraints.NORTH;
		gbc_btnSwitchPanelsLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnSwitchPanelsLogin.gridx = 1;
		gbc_btnSwitchPanelsLogin.gridy = 7;
		this.add(btnSwitchPanelsLogin, gbc_btnSwitchPanelsLogin);
	}

}
