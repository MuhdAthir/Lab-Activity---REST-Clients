package lab7;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JLabel;
import javax.swing.JScrollBar;

public class lab7 {

	private JFrame frame;
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
					lab7 window = new lab7();
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
	public lab7() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 349);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(10, 39, 414, 93);
		frame.getContentPane().add(textArea);
		
		JButton btnRetriveDataFrom = new JButton("Retrive data from JSON");
		btnRetriveDataFrom.setBounds(133, 11, 190, 23);
		frame.getContentPane().add(btnRetriveDataFrom);
		btnRetriveDataFrom.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread (new Runnable()
				{
					public void run()
					{
						
						String params = null;
						String strUrl = "https://jsonplaceholder.typicode.com/todos";
						JSONArray jsonObj;
						try {
							jsonObj = new JSONArray(makeHttpRequest(strUrl,"GET", params)) ;
							String strFromPHP = null;
							strFromPHP = jsonObj.toString();
							textArea.setText(strFromPHP);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
					}
				});
				thread.start();
	           
			}
		});
		
		textField = new JTextField();
		textField.setBounds(76, 174, 77, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblSearchUserBy = new JLabel("Search to do");
		lblSearchUserBy.setBounds(20, 146, 133, 14);
		frame.getContentPane().add(lblSearchUserBy);
		
		
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(407, 39, 17, 93);
		frame.getContentPane().add(scrollBar);
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(20, 202, 46, 14);
		frame.getContentPane().add(lblResult);
		
		textField_1 = new JTextField();
		textField_1.setBounds(133, 218, 276, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(133, 249, 276, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(46, 221, 77, 14);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblComplete = new JLabel("Completed:");
		lblComplete.setBounds(46, 252, 77, 14);
		frame.getContentPane().add(lblComplete);
		
		JLabel lblUserid = new JLabel("UserID:");
		lblUserid.setBounds(20, 177, 46, 14);
		frame.getContentPane().add(lblUserid);
		
		JLabel lblToDoId = new JLabel("To Do ID:");
		lblToDoId.setBounds(163, 177, 64, 14);
		frame.getContentPane().add(lblToDoId);
		
		textField_3 = new JTextField();
		textField_3.setBounds(237, 174, 77, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(335, 173, 89, 23);
		frame.getContentPane().add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread (new Runnable()
				{
					public void run()
					{
						String value1 = textField.getText();
						String value2 = textField_3.getText();
						String params = "userId="+value1+"&id="+value2;
						String strUrl = "https://jsonplaceholder.typicode.com/todos";
						JSONArray jsonObj;
						
						try {
							jsonObj = new JSONArray(makeHttpRequest(strUrl,"GET", params)) ;
							 
							JSONObject obj1 = jsonObj.getJSONObject(0);
							String title = obj1.getString("title");
							String completed = obj1.get("completed").toString();
							textField_1.setText(title);
							textField_2.setText(completed);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
					}
				});
				thread.start();
	           
			}
		});
	}
	
	public String makeHttpRequest(String strUrl, String method, String params) throws JSONException {
		InputStream is = null;
		String json = "";
		
		try {
			strUrl = strUrl+"?"+params;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine())!=null) 
				sb.append(line+"\n");
			is.close();
			json = sb.toString();
			
		}	catch (Exception ee) {
			ee.printStackTrace();
		}
		return json;
	}
}
