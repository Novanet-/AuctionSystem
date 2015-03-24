package applications;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

public class Client
{

	private JFrame frame;

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
	public Client()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlOuter = new JPanel();
		frame.getContentPane().add(pnlOuter, BorderLayout.CENTER);
		GridBagLayout gbl_pnlOuter = new GridBagLayout();
		gbl_pnlOuter.columnWidths = new int[] {0, 30, 30};
		gbl_pnlOuter.rowHeights = new int[] {30, 0, 30};
		gbl_pnlOuter.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_pnlOuter.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnlOuter.setLayout(gbl_pnlOuter);
		
		JLabel lblClientTitle = new JLabel("Client");
		lblClientTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblClientTitle = new GridBagConstraints();
		gbc_lblClientTitle.weighty = 1.0;
		gbc_lblClientTitle.weightx = 1.0;
		gbc_lblClientTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblClientTitle.gridx = 1;
		gbc_lblClientTitle.gridy = 1;
		pnlOuter.add(lblClientTitle, gbc_lblClientTitle);
	}

}
