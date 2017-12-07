import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import java.awt.List;
import javax.swing.JTextField;
import java.awt.Scrollbar;

public class test {

	private JFrame frame;
	private final JPanel panel = new JPanel();
	private final Action action = new SwingAction();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 365, 655);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JCheckBox lblNewLabel = new JCheckBox("Cell Phone");
		lblNewLabel.setBounds(12, 328, 97, 16);
		panel.add(lblNewLabel);
		
		JCheckBox lblNewLabel_1 = new JCheckBox("Minutes");
		lblNewLabel_1.setBounds(12, 404, 81, 16);
		panel.add(lblNewLabel_1);
		
		JCheckBox lblNewLabel_2 = new JCheckBox("Data");
		lblNewLabel_2.setBounds(8, 473, 67, 16);
		panel.add(lblNewLabel_2);
		
		JMenu mnNewMenu = new JMenu("Select");
		mnNewMenu.setBounds(41, 429, 123, 24);
		panel.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("1000 Minutes, $29.99");
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("5000 Minutes, $49.99");
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Unlimited Minutes, $69.99");
		mnNewMenu.add(mntmNewMenuItem_2);
		
		
		JButton btnNewButton = new JButton("Checkout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(137, 569, 97, 25);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(12, 569, 97, 25);
		panel.add(btnNewButton_1);
		
		JMenu mnNewMenu_2 = new JMenu("Select");
		mnNewMenu_2.setBounds(41, 353, 123, 24);
		panel.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Model 100, $39.95");
		mnNewMenu_2.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Model 200, $49.95");
		mnNewMenu_2.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Model 300, $59.95");
		mnNewMenu_2.add(mntmNewMenuItem_8);
		
		JMenu mnSelect = new JMenu("Select");
		mnSelect.setBounds(41, 498, 123, 24);
		panel.add(mnSelect);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("3 GB, $19.99");
		mnSelect.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("6 GB, $49.99");
		mnSelect.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Unlimited, $69.99");
		mnSelect.add(mntmNewMenuItem_5);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(12, 45, 56, 16);
		panel.add(lblName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(12, 109, 56, 16);
		panel.add(lblAddress);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(12, 173, 84, 16);
		panel.add(lblPhoneNumber);
		
		JLabel lblCustomerId = new JLabel("Customer ID");
		lblCustomerId.setBounds(12, 238, 81, 16);
		panel.add(lblCustomerId);
		
		textField = new JTextField();
		textField.setBounds(12, 74, 116, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(12, 138, 116, 22);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(12, 203, 116, 22);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(12, 267, 116, 22);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
