package com.app.todo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTextField;

import com.app.todo.utils.Constants;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
public class TaskListUI extends JFrame  {

	private static final long serialVersionUID = 1L;
	private JPanel panelSearch, panelList, panelBtns;
	
	private static int WIDTH = 700;
	private static int HEIGHT = 600;
	
	public TaskListUI() {
		initGUI();
		initBL();
	}
	
	/**
	 * @param 
	 * @return void
	 */
	private void initGUI() {
		setTitle(Constants.appName + ": All Tasks");
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setLocationRelativeTo(null);
		
		add(setPanelSearch());
		add(setPanelList());
		add(setPanelButtons());
		
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private Component setPanelSearch() {
		panelSearch = new JPanel();
		panelSearch.setBounds(1, 10, WIDTH, 50);
		
		JTextField searchTxt = new JTextField("Enter task title to search", 30);
		searchTxt.setPreferredSize(new Dimension(150, 30));
		searchTxt.setBackground(Color.pink);
		panelSearch.setOpaque(true);
		panelSearch.add(searchTxt);
		
		JButton btnSearch = new JButton("Search");
		btnSearch = new JButton("SEARCH");
		panelSearch.add(btnSearch);
		
		return panelSearch;
	}
	
	private Component setPanelList() {
		panelList = new JPanel(new GridLayout());
		panelList.setBackground(Color.gray);
		panelList.setBounds(1, 62, WIDTH, 420);
		return panelList;
	}
	
	private Component setPanelButtons() {
		panelBtns = new JPanel();
		panelBtns.setLayout(null);
		//panelBtns.setBackground(Color.green);
		panelBtns.setBounds(1, 483, WIDTH, 80);
		
		JButton btnAdd = new JButton("ADD TASK");
		btnAdd.setBounds(180, 20, 100, 35);
		panelBtns.add(btnAdd);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.setBounds(290, 20, 100, 35);
		panelBtns.add(btnEdit);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setBounds(400, 20, 100, 35);
		panelBtns.add(btnDelete);
		
		return panelBtns;
	}
	
	private void initBL() {
		
	}
}
