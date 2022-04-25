package com.app.todo.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.app.todo.dao.QueryHelper;
import com.app.todo.operations.TaskListOperations;
import com.app.todo.pojo.TaskDetail;
import com.app.todo.utils.CommonUtils;
import com.app.todo.utils.Constants;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
public class TaskUpdationUI extends JFrame implements ActionListener, ItemListener  {
	
	private static final long serialVersionUID = 1L;
	private JPanel panelTextData, panelButtons;
	private JButton btnUpdate, btnBack;
	private JLabel labelTitle, labelDescription, labelCategory, labelStatus, labelDateCreated, labelDateCompleted, labelComment;
	private JTextField txtTitle, txtCategory, txtDateCreated, txtDateCompleted;
	private JTextArea taDescription, taComment;
	private JComboBox<String> comboStatus = null;
	
	private static int WIDTH = 700;
	private static int HEIGHT = 600;
	private TaskDetail task = null;
	private String updatedTaskStatus = null;
	private TaskListOperations taskOperations = null;
	
	public TaskUpdationUI(TaskDetail task) {
		try {
			taskOperations = new TaskListOperations();
			//System.out.println("Task to update: " + task.getTitle());
			this.task = task;
			initGUI();
			initBL();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initGUI() {
		setTitle(Constants.appName + ": Modify Task");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		
		add(setPanelTextData());
		add(setPanelButtons());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnUpdate.addActionListener(this);
		btnBack.addActionListener(this);
		comboStatus.addItemListener(this);
		
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
		
		btnUpdate = new JButton("MODIFY");
		btnUpdate.setBounds(50, 500, 150, 35);
		panelButtons.add(btnUpdate);

		btnBack = new JButton("BACK");
		btnBack.setBounds(540, 500, 100, 35);
		panelButtons.add(btnBack);
		
		return panelButtons;
	}
		
	private void initBL() {
		txtTitle.setText(task.getTitle());
		taDescription.setText(task.getDescription());
		txtCategory.setText(task.getCategory());
		comboStatus.setSelectedItem(task.getStatus());
		txtDateCreated.setText(task.getDateCreated().toString());
		txtDateCompleted.setText((task.getDateCompleted() != null)? task.getDateCompleted().toString() : "");
		taComment.setText(task.getComment());
		txtDateCreated.setEditable(false);
		txtDateCompleted.setEditable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			if(ae.getSource() == btnUpdate) {
				int output = JOptionPane.showConfirmDialog(this, "Are you sure to update the task?");
				if(output == JOptionPane.YES_OPTION) {
					String title = txtTitle.getText().toString().trim();
					String description = taDescription.getText().toString().trim();
					String category = txtCategory.getText().toString().trim();
					String status = this.updatedTaskStatus;
					String comment = taComment.getText().toString().trim();
					Date dateCompleted = null;
					if(txtDateCompleted.getText() != null && !(txtDateCompleted.getText().equals(""))) {
						dateCompleted = CommonUtils.getSdf().parse(txtDateCompleted.getText());
					}
					if(status.equalsIgnoreCase(QueryHelper.statusComplete)) {
						txtDateCompleted.setText(CommonUtils.getSdf().format(new Date()));				
						dateCompleted = CommonUtils.getSdf().parse(txtDateCompleted.getText().toString().trim());
					}
					TaskDetail updatedTask = new TaskDetail(title, description, category, status, task.getDateCreated(), dateCompleted, comment);
										
					String updateStatus = taskOperations.updateTask(task, updatedTask);
					JOptionPane.showMessageDialog(this, updateStatus);
				}
			} else if(ae.getSource() == btnBack) {
				this.setVisible(false);
				new TaskListUI();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent ae) {
		if(ae.getSource() == comboStatus) {
			this.updatedTaskStatus = comboStatus.getSelectedItem().toString();
		}		
	}
	
	private void initializeLabels(JPanel panel) {
		labelTitle = new JLabel("Task Title: "); 			   			labelTitle.setBounds(150, 50, 150, 30);
		labelTitle.setFont(new Font("Serif", Font.BOLD, 16));   		labelTitle.setForeground(Color.white);
		panel.add(labelTitle);
		
		labelDescription = new JLabel("Description: ");     			labelDescription.setBounds(150, 100, 150, 30);
		labelDescription.setFont(new Font("Serif", Font.BOLD, 16));   	labelDescription.setForeground(Color.white);
		panel.add(labelDescription);
		
		labelCategory = new JLabel("Categpry: ");						labelCategory.setBounds(150, 160, 150, 30);
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
		
		String[] states = { "NEW", "IN PROGRESS", "BLOCKAGE", "COMPLETED" };
		comboStatus = new JComboBox<String>(states);
		comboStatus.setBounds(280, 205, 250, 30);
		panel.add(comboStatus);
		
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
