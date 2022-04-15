package com.app.todo.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.app.todo.utils.Constants;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
public class TaskCreationUI extends JFrame  {

	
	public TaskCreationUI() {
		initGUI();
		initBL();
	}
	
	private void initGUI() {
		setTitle(Constants.appName + ": New Task");
		setSize(600, 500);
		setLocationRelativeTo(null);
		
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void initBL() {
		
	}
}
