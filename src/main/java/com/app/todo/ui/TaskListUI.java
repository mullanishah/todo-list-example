package com.app.todo.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import com.app.todo.dao.QueryHelper;
import com.app.todo.operations.TaskListOperations;
import com.app.todo.pojo.TaskDetail;
import com.app.todo.utils.Constants;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
public class TaskListUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TaskListUI.class);
	
	private JPanel panelSearch, panelList, panelBtns;
	private JList<String> jlist;
	private JButton btnSearch, btnAdd, btnEdit, btnDelete;
	private JTextField txtSearch;

	private static int WIDTH = 700;
	private static int HEIGHT = 600;
	private static boolean selectedItemStatus = false;
	private static String selectedItemValue = null;

	private String[] taskTitles = null;
	private TaskListOperations taskOperations = null;
	private Map<String, TaskDetail> taskDetailMap = null;

	public TaskListUI() {
		try {
			taskOperations = new TaskListOperations();
			initialDataLoad();
			initGUI();
			initBL();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, QueryHelper.genericErrorAlert + e.getMessage());
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
				log.debug("Exiting from main TaskListUI screen");
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
		try {
			if(ae.getSource() == btnSearch) {
				
				String searchValue = txtSearch.getText().toString().trim();
				log.info("Search button clicked, search value: " + searchValue);
				if(!(searchValue.equals("")) && searchValue != null) {
					JOptionPane.showMessageDialog(this, taskOperations.searchTask(txtSearch.getText().toString().trim()));
				} else if(selectedItemStatus == true) {
					//JOptionPane.showMessageDialog(this, taskOperations.searchTask(selectedItemValue));
					JOptionPane.showMessageDialog(this, taskOperations.searchTask(selectedItemValue), "Task Summary", 
								JOptionPane.PLAIN_MESSAGE, null);
				} else {
					JOptionPane.showMessageDialog(this, QueryHelper.errorSearchEmpty);
				}
			} else if(ae.getActionCommand().equalsIgnoreCase("add") || ae.getSource() == btnAdd) {
				
				log.info("add button clicked, redirecting to TaskCreationUI screen");
				this.setVisible(false);
				new TaskCreationUI();
			} else if(ae.getSource() == btnEdit) {
				
				if(selectedItemStatus == true) {
					TaskDetail task = taskDetailMap.get(selectedItemValue);
					log.info("edit button clicked, redirecting to TaskUpdationUI screen");
					this.setVisible(false);
					new TaskUpdationUI(task);
				}
			} else if(ae.getSource() == btnDelete) {
				
				if(selectedItemStatus == true) {
					log.info("delete button clicked, Deletion status: " + selectedItemValue);
					TaskDetail task = taskDetailMap.get(selectedItemValue);
					int output = JOptionPane.showConfirmDialog(this, "Are you sure to remove?");
					if(output == JOptionPane.YES_OPTION) {
						String deleteStatus = taskOperations.deleteTask(task);
						log.info("delete button clicked, Deletion status: " + deleteStatus);
						JOptionPane.showMessageDialog(this, deleteStatus);
						
						//below code added to refresh the screen after deletion
						this.setVisible(false);
						new TaskListUI();
					}
				}
			}
		}catch (Exception e) {
			log.error("Error occurred in TaskListUI");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, QueryHelper.genericErrorAlert + e.getMessage());
		}
	}

	private void initialDataLoad() throws Exception {

		int index = 0;
		taskDetailMap = taskOperations.getAllTasks();
		taskTitles = new String[taskDetailMap.size()];
		for(String key : taskDetailMap.keySet()) {
			taskTitles[index++] = key;
		}
	}

	private void initBL() {

	}

}
