package gui;
import bd.domains.Inkas;
import bd.domains.InkasId;
import bd.domains.Key;
import bd.services.InkasService;
import bd.services.KeyService;

import com.cloudgarden.layout.AnchorLayout;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDManager;

import core.ReaderClass;

import dao.OperatorDAO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainFrame extends javax.swing.JFrame {

	private JDialog jDialog1;
	private AbstractAction incasAction;
	private AbstractAction clientActiob;
	private JComboBox jComboBox1;
	private JTextArea jTextArea2;
	private JTextArea jTextArea1;
	private JPanel jPanel3;
	private JLabel jLabel4;//= new JLabel();
	private JLabel jLabel1;//= new JLabel();
	private JLabel jLabel3;// = new JLabel();
	private AbstractAction abstractAction1;
	private JButton jButton2;
	private JPanel jPanel2;
	private JTabbedPane jTabbedPane1;
	private OperatorDAO odao = null;
	private Double bonus;
	private ReaderClass rc;
	private JButton jButton1;
	private JTextField jTextField2;
	private JLabel jLabel6;
	private JPanel jPanel1;
	//private static LoginFrame lf;

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame();				
				
				//lf.setLocationRelativeTo(null);
				//lf.setVisible(true);
				inst.setLocationRelativeTo(null);	
				inst.setVisible(true);
			}
		});
	}
	
	public MainFrame() {
		super();
		initGUI();
	//	lf = new LoginFrame(this);
		rc = new ReaderClass("1", bonus);
		rc.addPropertyChangeListener
		( new PropertyChangeListener() {			
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("progress".equals(evt.getPropertyName())) 
				{
					jTextArea1.setText(evt.getNewValue().toString());
					jTextArea1.setWrapStyleWord(true);
					jTextArea1.setLineWrap(true);	
					jTextArea1.setDisabledTextColor(new Color(255,128,0));
					jTextArea1.setSelectedTextColor(new Color(255,128,0));
					jTextArea1.setForeground(new Color(255,128,0));
	             }
	         }			
		}
		);
		rc.addPropertyChangeListener
		( new PropertyChangeListener() {			
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("keys".equals(evt.getPropertyName())) 
				{
					jTextArea2.setText(evt.getNewValue().toString());
	             }
	         }			
		}
		);
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(26, 26)
				.addComponent(getJPanel1(), GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(29, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(getJPanel1(), GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});

			pack();
			this.setSize(400, 301);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private AbstractAction getClientActiob() {
		if(clientActiob == null) {
			clientActiob = new AbstractAction("\u041d\u0430\u0447\u0430\u0442\u044c", null) {
				public void actionPerformed(ActionEvent evt) 
				{
					if(jTextField2.getText().isEmpty() || jTextField2.getText()=="") bonus = new Double(0); else bonus = new Double(jTextField2.getText());
					String str = "с бонусом "+ bonus +" рублей";
					getJLabel3().setText("Регистрируем ключи на оператора:");
					getJLabel4().setText("1");
					getJLabel1().setText(str);
					rc.setOpername("1");
					rc.setAmmount(bonus);
					getJLabel3().setHorizontalAlignment(SwingConstants.CENTER);
					getJLabel1().setHorizontalAlignment(SwingConstants.CENTER);
					getJLabel4().setHorizontalAlignment(SwingConstants.CENTER);	
					getJLabel4().setHorizontalAlignment(SwingConstants.CENTER);
					getJDialog1().pack();
					jPanel1.setVisible(false);
					getJDialog1().setLocationRelativeTo(null);
					getJDialog1().setVisible(true);
					rc.execute();
				}
			};
		}
		return clientActiob;
	}
	
	private AbstractAction getIncasAction() {
		if(incasAction == null) {
			incasAction = new AbstractAction("\u041d\u0430\u0447\u0430\u0442\u044c", null) {
				public void actionPerformed(ActionEvent evt) {
				}
			};
		}
		return incasAction;
	}
	
	private JDialog getJDialog1() {
		if(jDialog1 == null) {
			jDialog1 = new JDialog(this);
			GroupLayout jDialog1Layout = new GroupLayout((JComponent)jDialog1.getContentPane());
			jDialog1.getContentPane().setLayout(jDialog1Layout);
			jDialog1.setSize(561, 416);
			jDialog1.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) 
				{
					rc.setFinised(true);
					getJDialog1().dispose();
					jPanel1.setVisible(true);
					//jDialog1WindowClosing(evt)
				}
			});
			jDialog1Layout.setHorizontalGroup(jDialog1Layout.createSequentialGroup()
				.addContainerGap(32, 32)
				.addComponent(getJPanel3(), 0, 503, Short.MAX_VALUE)
				.addContainerGap());
			jDialog1Layout.setVerticalGroup(jDialog1Layout.createSequentialGroup()
				.addContainerGap(16, 16)
				.addComponent(getJPanel3(), 0, 351, Short.MAX_VALUE)
				.addContainerGap());
		}
		return jDialog1;
	}

	private AbstractAction getAbstractAction1() {
		if(abstractAction1 == null) {
			abstractAction1 = new AbstractAction("\u0417\u0430\u043a\u043e\u043d\u0447\u0438\u0442\u044c", null) {
				public void actionPerformed(ActionEvent evt) 
				{
										
				}
			};
		}
		return abstractAction1;
	}
	
	private JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabel3;
	}
	
	private JLabel getJLabel4() {
		if(jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabel4;
	}
	
	private JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabel1;
	}
	
	private JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new JPanel();
			GroupLayout jPanel3Layout = new GroupLayout((JComponent)jPanel3);
			jPanel3.setLayout(jPanel3Layout);
			jPanel3Layout.setVerticalGroup(jPanel3Layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(getJLabel3(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(getJLabel4(), GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(getJLabel1(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addGap(15)
				.addComponent(getJTextArea1(), GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
				.addGap(16)
				.addComponent(getJTextArea2(), GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(76, Short.MAX_VALUE));
			jPanel3Layout.setHorizontalGroup(jPanel3Layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(jPanel3Layout.createParallelGroup()
				    .addGroup(jPanel3Layout.createSequentialGroup()
				        .addComponent(getJLabel3(), GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(jPanel3Layout.createSequentialGroup()
				        .addComponent(getJLabel4(), GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(jPanel3Layout.createSequentialGroup()
				        .addComponent(getJLabel1(), GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(jPanel3Layout.createSequentialGroup()
				        .addGap(23)
				        .addGroup(jPanel3Layout.createParallelGroup()
				            .addGroup(jPanel3Layout.createSequentialGroup()
				                .addComponent(getJTextArea1(), GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE))
				            .addGroup(jPanel3Layout.createSequentialGroup()
				                .addComponent(getJTextArea2(), GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE)))
				        .addGap(0, 20, Short.MAX_VALUE)))
				.addContainerGap(53, 53));
		}
		return jPanel3;
	}

	private JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new JPanel();
			GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap(90, 90)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(getJLabel6(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
				    .addComponent(getJTextField2(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
				.addGap(57)
				.addComponent(getJButton1(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(16, 16));
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap(75, 75)
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				        .addComponent(getJLabel6(), GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
				        .addGap(25)
				        .addComponent(getJTextField2(), GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				        .addGap(58)
				        .addComponent(getJButton1(), GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 64, Short.MAX_VALUE)))
				.addContainerGap(76, 76));
		}
		return jPanel1;
	}
	
	private JLabel getJLabel6() {
		if(jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("\u041d\u0430\u0447\u0430\u043b\u044c\u043d\u044b\u0439 \u0431\u043e\u043d\u0443\u0441");
		}
		return jLabel6;
	}
	
	private JTextField getJTextField2() {
		if(jTextField2 == null) {
			jTextField2 = new JTextField();
		}
		return jTextField2;
	}

	private JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("\u041d\u0430\u0447\u0430\u0442\u044c");
			jButton1.setAction(getClientActiob());
		}
		return jButton1;
	}
	
	private void thisWindowClosing(WindowEvent evt) 
	{
		System.exit(0);
		//TODO add your code for this.windowClosing
	}

	private void jDialog1WindowClosing(WindowEvent evt) {
		System.out.println("jDialog1.windowClosing, event="+evt);
		//TODO add your code for jDialog1.windowClosing
	}
	
	private JTextArea getJTextArea1() {
		if(jTextArea1 == null) {
			jTextArea1 = new JTextArea();
			jTextArea1.setEditable(false);
			jTextArea1.setAutoscrolls(false);
			jTextArea1.setWrapStyleWord(true);
			jTextArea1.setFont(new java.awt.Font("Arial",0,11));
			jTextArea1.setSelectedTextColor(new java.awt.Color(255,128,0));
			jTextArea1.setRows(3);
		}
		return jTextArea1;
	}
	
	private JTextArea getJTextArea2() {
		if(jTextArea2 == null) {
			jTextArea2 = new JTextArea();
			jTextArea2.setEditable(false);
			jTextArea2.setWrapStyleWord(true);
			jTextArea2.setFont(new java.awt.Font("Bookman Old Style",0,11));
			jTextArea2.setSelectedTextColor(new java.awt.Color(0,0,255));
		}
		return jTextArea2;
	}

}
