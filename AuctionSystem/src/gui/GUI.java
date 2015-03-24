package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;

public class GUI
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
					GUI window = new GUI();
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
	public GUI()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 586, 424);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel pnlOuter = new JPanel();
		GridBagConstraints gbc_pnlOuter = new GridBagConstraints();
		gbc_pnlOuter.fill = GridBagConstraints.BOTH;
		gbc_pnlOuter.gridx = 0;
		gbc_pnlOuter.gridy = 0;
		frame.getContentPane().add(pnlOuter, gbc_pnlOuter);
		GridBagLayout gbl_pnlOuter = new GridBagLayout();
		gbl_pnlOuter.columnWidths = new int[]{0, 0, 0};
		gbl_pnlOuter.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlOuter.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlOuter.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlOuter.setLayout(gbl_pnlOuter);
		
		JPanel pnlMandelbrot = new JPanel();
		pnlMandelbrot.setBackground(Color.GREEN);
		pnlMandelbrot.setForeground(Color.BLACK);
		GridBagConstraints gbc_pnlMandelbrot = new GridBagConstraints();
		gbc_pnlMandelbrot.gridheight = 2;
		gbc_pnlMandelbrot.gridwidth = 2;
		gbc_pnlMandelbrot.fill = GridBagConstraints.BOTH;
		gbc_pnlMandelbrot.weighty = 2.0;
		gbc_pnlMandelbrot.gridx = 0;
		gbc_pnlMandelbrot.gridy = 0;
		pnlOuter.add(pnlMandelbrot, gbc_pnlMandelbrot);
		GridBagLayout gbl_pnlMandelbrot = new GridBagLayout();
		gbl_pnlMandelbrot.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pnlMandelbrot.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlMandelbrot.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlMandelbrot.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlMandelbrot.setLayout(gbl_pnlMandelbrot);
		
		JPanel pnlInfo = new JPanel();
		pnlInfo.setBackground(Color.RED);
		GridBagConstraints gbc_pnlInfo = new GridBagConstraints();
		gbc_pnlInfo.gridwidth = 2;
		gbc_pnlInfo.fill = GridBagConstraints.BOTH;
		gbc_pnlInfo.gridx = 0;
		gbc_pnlInfo.gridy = 2;
		pnlOuter.add(pnlInfo, gbc_pnlInfo);
		GridBagLayout gbl_pnlInfo = new GridBagLayout();
		gbl_pnlInfo.columnWidths = new int[]{240, 89, 0};
		gbl_pnlInfo.rowHeights = new int[]{23, 0, 0};
		gbl_pnlInfo.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlInfo.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnlInfo.setLayout(gbl_pnlInfo);
		
		JButton btnNewButton = new JButton("New button");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		pnlInfo.add(btnNewButton, gbc_btnNewButton);
	}

}
