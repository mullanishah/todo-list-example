package com.app.todo.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.log4j.Logger;
import com.app.todo.dao.QueryHelper;
import com.app.todo.operations.TaskListOperations;
import com.app.todo.pojo.TaskDetail;
import com.app.todo.utils.CommonUtils;
import com.app.todo.utils.Constants;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
public class TaskCreationUI extends JFrame implements ActionListener  {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TaskListUI.class);
	
	private JPanel panelTextData, panelButtons;
	private JButton btnAdd, btnClear, btnBack;
	private JLabel labelTitle, labelDescription, labelCategory, labelStatus, labelDateCreated, labelDateCompleted, labelComment;
	private JTextField txtTitle, txtCategory, txtStatus, txtDateCreated, txtDateCompleted;
	private JTextArea taDescription, taComment;
	private TaskListOperations taskOperations = null;

	private static int WIDTH = 700;
	private static int HEIGHT = 600;

	public TaskCreationUI() {
		try {
			taskOperations = new TaskListOperations();
			initGUI();
			initBL();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initGUI() {
		setTitle(Constants.appName + ": New Task");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);

		add(setPanelTextData());
		add(setPanelButtons());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnAdd.addActionListener(this);
		btnBack.addActionListener(this);
		btnClear.addActionListener(this);

		setVisible(true);
	}

	private Component setPanelTextData() {
		panelTextData = new JPanel();
		panelTextData.setLayout(null);
		panelTextData.setBackground(Color.gray);
		panelTextData.setBounds(1, 1, WIDTH, 480);

		initializeLabels(panelTextData);
		initializeTexts(panelTextData);

		return panelTextData;
	}

	private Component setPanelButtons() {
		panelButtons = new JPanel();
		panelButtons.setLayout(null);
		panelButtons.setBackground(Color.cyan);
		panelButtons.setBounds(0, 483, WIDTH, 80);

		btnAdd = new JButton("REGISTER");
		btnAdd.setBounds(50, 500, 150, 35);
		panelButtons.add(btnAdd);

		btnClear = new JButton("CLEAR");
		btnClear.setBounds(430, 500, 100, 35);
		panelButtons.add(btnClear);

		btnBack = new JButton("BACK");
		btnBack.setBounds(540, 500, 100, 35);
		panelButtons.add(btnBack);

		return panelButtons;
	}

	private void initBL() {
		txtStatus.setText("NEW");
		txtStatus.setEditable(false);
		txtDateCreated.setText(CommonUtils.getSdf().format(new Date()));
		txtDateCreated.setEditable(false);
		txtDateCompleted.setText(null);
		txtDateCompleted.setEditable(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			if(ae.getSource() == btnAdd) {
				String title = txtTitle.getText().toString().trim();
				String description = taDescription.getText().toString().trim();
				String category = txtCategory.getText().toString().trim();
				String status = txtStatus.getText().toString().trim();
				String comment = taComment.getText().toString().trim();
				Date dateCreated = CommonUtils.getSdf().parse(txtDateCreated.getText().toString().trim());
				Date dateCompleted = null;
				if(title == null || title.equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(this, QueryHelper.errorTitleEmpty);
				else {
					TaskDetail task = new TaskDetail(title, description, category, status, dateCreated, dateCompleted, comment);
					String insertStatus = taskOperations.addTask(task);
					JOptionPane.showMessageDialog(this, insertStatus);
					log.info("register button clicked, insertion status " + insertStatus);
				}
			} else if(ae.getSource() == btnClear) {
				txtTitle.setText("");
				taDescription.setText("");
				txtCategory.setText("");
				taComment.setText("");
				log.debug("clear button clicked");
				JOptionPane.showMessageDialog(this, "Cleared !!");
			} else if(ae.getSource() == btnBack) {
				this.setVisible(false);
				new TaskListUI();
				log.debug("back button clicked");
			}
		} catch (Exception e) {
			log.error("Error occurred while task creation");
			JOptionPane.showMessageDialog(this, QueryHelper.genericErrorAlert + e.getMessage());
			e.printStackTrace();
		}
	}

	private void initializeLabels(JPanel panel) {
		labelTitle = new JLabel("Task Title*: "); 			   			labelTitle.setBounds(150, 50, 150, 30);
		labelTitle.setFont(new Font("Serif", Font.BOLD, 16));   		labelTitle.setForeground(Color.white);
		panel.add(labelTitle);

		labelDescription = new JLabel("Description: ");     			labelDescription.setBounds(150, 100, 150, 30);
		labelDescription.setFont(new Font("Serif", Font.BOLD, 16));   	labelDescription.setForeground(Color.white);
		panel.add(labelDescription);

		labelCategory = new JLabel("Category: ");						labelCategory.setBounds(150, 160, 150, 30);
		labelCategory.setFont(new Font("Serif", Font.BOLD, 16)); 		labelCategory.setForeground(Color.white);
		panel.add(labelCategory);

		labelStatus = new JLabel("Status: ");							labelStatus.setBounds(150, 205, 150, 30);
		labelStatus.setFont(new Font("Serif", Font.BOLD, 16));			labelStatus.setForeground(Color.white);
		panel.add(labelStatus);

		labelDateCreated = new JLabel("Created On: "); 	     			labelDateCreated.setBounds(150, 250, 150, 30);
		labelDateCreated.setFont(new Font("Serif", Font.BOLD, 16)); 	labelDateCreated.setForeground(Color.white);
		panel.add(labelDateCreated);

		labelDateCompleted = new JLabel("Completed On: ");				labelDateCompleted.setBounds(150, 295, 150, 30);
		labelDateCompleted.setFont(new Font("Serif", Font.BOLD, 16)); 	labelDateCompleted.setForeground(Color.white);
		panel.add(labelDateCompleted);

		labelComment = new JLabel("Comment: ");					    	labelComment.setBounds(150, 345, 250, 30);
		labelComment.setFont(new Font("Serif", Font.BOLD, 16)); 		labelComment.setForeground(Color.white);
		panel.add(labelComment);
	}

	private void initializeTexts(JPanel panel) {
		
		txtTitle = new JTextField(50);
		txtTitle.setBounds(280, 50, 250, 30);
		panel.add(txtTitle);

		taDescription = new JTextArea(4, 50);
		taDescription.setBounds(280, 95, 250, 50);
		panel.add(taDescription);

		txtCategory = new JTextField("");
		txtCategory.setBounds(280, 160, 250, 30);
		panel.add(txtCategory);

		txtStatus = new JTextField("");
		txtStatus.setBounds(280, 205, 250, 30);
		panel.add(txtStatus);

		txtDateCreated = new JTextField("");
		txtDateCreated.setBounds(280, 250, 250, 30);
		panel.add(txtDateCreated);

		txtDateCompleted = new JTextField("");
		txtDateCompleted.setBounds(280, 295, 250, 30);
		panel.add(txtDateCompleted);

		taComment = new JTextArea(4, 50);
		taComment.setBounds(280, 340, 250, 50);
		panel.add(taComment);
	}
}
