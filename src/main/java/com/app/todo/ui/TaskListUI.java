package com.app.todo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXTextField;

import com.app.todo.dao.TaskListGetDao;
import com.app.todo.pojo.TaskDetail;
import com.app.todo.utils.Constants;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
public class TaskListUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel panelSearch, panelList, panelBtns;
	private JList<String> jlist;
	private JButton btnSearch, btnAdd, btnEdit, btnDelete;
	private JTextField txtSearch;

	private static int WIDTH = 700;
	private static int HEIGHT = 600;
	private static boolean selectedItemStatus = false;
	private static String selectedItemValue = null;

	private String[] taskTitles = null;
	private TaskListGetDao taskListDao = null;
	private Map<String, TaskDetail> taskDetailMap = null;

	public TaskListUI() {
		try {
			initialDataLoad();
			initGUI();
			initBL();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Exiting");
				System.exit(0);
			}
		});

		btnSearch.addActionListener(this);
		btnAdd.addActionListener(this);
		btnEdit.addActionListener(this);
		btnDelete.addActionListener(this);

		setVisible(true);
	}

	private Component setPanelSearch() {
		panelSearch = new JPanel();
		panelSearch.setBounds(1, 10, WIDTH, 50);

		txtSearch = new JTextField("Enter task title to search", 30);
		txtSearch.setPreferredSize(new Dimension(150, 30));
		txtSearch.setBackground(Color.pink);
		panelSearch.setOpaque(true);
		panelSearch.add(txtSearch);

		btnSearch = new JButton("SEARCH");
		panelSearch.add(btnSearch);

		return panelSearch;
	}

	private Component setPanelList() {
		panelList = new JPanel();
		panelList.setBounds(1, 62, WIDTH, 420);

		jlist = new JList<String>(taskTitles);
		jlist.setFixedCellHeight(30);
		jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jlist.setPreferredSize(new Dimension(WIDTH - 80, 550));
		jlist.setVisibleRowCount(15);
		jlist.setBackground(Color.gray);
		jlist.setForeground(Color.white);
		jlist.setAlignmentY(CENTER_ALIGNMENT);
		jlist.setFont(new Font("Serif", Font.BOLD, 16)); 
		jlist.setOpaque(true);
		jlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedItemStatus = true;
				selectedItemValue = jlist.getSelectedValue();
				System.out.println(selectedItemStatus + selectedItemValue);
				
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
			}
		});
		JScrollPane jsp = new JScrollPane(jlist);
		panelList.add(jsp);	
		return panelList;
	}

	private Component setPanelButtons() {
		panelBtns = new JPanel();
		panelBtns.setLayout(null);
		panelBtns.setBounds(1, 483, WIDTH, 80);

		btnAdd = new JButton("ADD NEW TASK");
		btnAdd.setBounds(50, 20, 150, 35);
		panelBtns.add(btnAdd);

		btnEdit = new JButton("MODIFY TASK");
		btnEdit.setBounds(400, 20, 130, 35);
		btnEdit.setVisible(false);
		panelBtns.add(btnEdit);

		btnDelete = new JButton("REMOVE");
		btnDelete.setBounds(540, 20, 100, 35);
		btnDelete.setVisible(false);
		panelBtns.add(btnDelete);

		return panelBtns;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnSearch) {
			System.out.println("Search");
		} else if(ae.getActionCommand().equalsIgnoreCase("add") || ae.getSource() == btnAdd) {
			this.setVisible(false);
			new TaskCreationUI();
		} else if(ae.getSource() == btnEdit) {
			if(selectedItemStatus == true) {
				TaskDetail task = taskDetailMap.get(selectedItemValue);
				this.setVisible(false);
				new TaskUpdationUI(task);
				System.out.println(task);
			}
		} else if(ae.getSource() == btnDelete) {
			if(selectedItemStatus == true) {
				int output = JOptionPane.showConfirmDialog(this, "Are you sure?");
				if(output == JOptionPane.YES_OPTION) {
					System.out.println("delete confirmed for " + selectedItemValue);
				}
			}
		}
	}

	private void initialDataLoad() throws Exception {

		int index = 0;
		taskListDao = new TaskListGetDao();
		taskDetailMap = taskListDao.getAllTaskDetails();
		taskTitles = new String[taskDetailMap.size()];
		for(String key : taskDetailMap.keySet()) {
			taskTitles[index++] = key;
		}
	}

	private void initBL() {

	}

}
