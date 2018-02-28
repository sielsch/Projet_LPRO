package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Employe;
import model.EmployeDAO;
import util.DBUtil;

public class DigiCodeWindow extends JDialog {

	private JButton[] digitButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JButton okButton;
	private JTextField pswField;
	private JPanel gridPanel;
	private JPanel mainPanel;
	String psw;
	boolean correct;
	String numBadge;

	public DigiCodeWindow(String numBadge) {
		this.numBadge = numBadge;
		correct = false;
		initComponent();
		initListener();
		setTitle("Saisissez votre mot de passe");
		setSize(400, 400);
		setModalityType(ModalityType.APPLICATION_MODAL);

	}

	public void initComponent() {
		digitButton = new JButton[10];
		cancelButton = new JButton("Annuler");

		deleteButton = new JButton("Corriger");

		okButton = new JButton("OK");

		pswField = new JPasswordField(10);

		gridPanel = new JPanel(new GridLayout(5, 3));
		digitButton[0] = new JButton("0");
		for (int i = 1; i < digitButton.length; i++) {
			digitButton[i] = new JButton(i + "");
			gridPanel.add(digitButton[i]);
		}

		okButton.setPreferredSize(cancelButton.getPreferredSize());
		gridPanel.add(new JPanel());
		gridPanel.add(digitButton[0]);
		gridPanel.add(new JPanel());
		gridPanel.add(cancelButton);
		cancelButton.setOpaque(true);
		cancelButton.setBackground(new Color(255, 25, 25));
		gridPanel.add(deleteButton);
		deleteButton.setOpaque(true);
		deleteButton.setBackground(new Color(241, 230, 61));
		gridPanel.add(okButton);
		okButton.setOpaque(true);
		okButton.setBackground(new Color(83, 193, 63));
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(gridPanel, BorderLayout.CENTER);
		mainPanel.add(pswField, BorderLayout.PAGE_END);
		add(mainPanel);
	}

	public void initListener() {
		for (int i = 0; i < digitButton.length; i++) {
			digitButton[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					pswField.setText(pswField.getText() + ((JButton) e.getSource()).getText());
				}
			});
		}

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pswField.setText("");

			}
		});

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String select = "Select password from employes where numBadge='" + numBadge + "';";

					List<String[]> values = (ArrayList<String[]>) DBUtil.dbExecuteQueryRasp(select);
					String pswToFind = "";
					if (values.size() == 1) {
						String[] value = values.get(0);
						pswToFind = value[0];
					}

					// Employe employe = EmployeDAO.searchEmploye(numBadge);
					String psw = pswField.getText();
					if (psw.equals(pswToFind)) {
						correct = true;
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setPswField(String string) {

		pswField.setText(string);
		
	}

}
