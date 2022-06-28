package InterfaceTest;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import net.sf.json.JSONObject;

public class StarPage extends BasicPage{
	public StarPage() throws Exception {
		//�����±�������Ӱ�ť
		getLabel().setIcon(new ImageIcon("imges/star.jpg"));
		TextField txt=new TextField();
		JLabel title=new JLabel("����������:");
		JButton button=new JButton("�鿴����");
		//��������
		title.setFont(getFont());
		title.setForeground(Color.white);
		txt.setFont(getFont());
		button.setFont(getFont());
		//���ñ��⡢�����Ͱ�ť��λ��
		title.setBounds(
				getJFrame().getWidth()/2-300, 
				getJFrame().getHeight()/10,
				150,35);
		txt.setBounds(
				getJFrame().getWidth()/2-175, 
				getJFrame().getHeight()/10,
				150,35);
		button.setBounds(
				getJFrame().getWidth()/2+175, 
				getJFrame().getHeight()/10,
				150,35);
		getJPanel().add(txt);
		getJPanel().add(title);
		getJPanel().add(button);
		getJPanel().updateUI();
		
		JLabel label =new JLabel();
		label.setFont(getFont());
		label.setForeground(Color.getHSBColor(102,102,102));	
		label.setBounds(
				getJFrame().getWidth()/8, 
				getJFrame().getHeight()/15,
				getJFrame().getWidth()/4*3,
				getJFrame().getHeight()/5*4);
		getJPanel().add(label);
		//��Ӱ�ť�¼�
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				StarEvent(label,txt,button);
			}
		});
		txt.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar()==KeyEvent.VK_ENTER) {
					StarEvent(label,txt,button);
				}
			}
			
		});
	}
	private  void StarEvent(JLabel label, TextField txt, JButton button) {
		try {
			label.setText(
					//�ж������Ƿ�����
					show(txt.getText())!=null?
							show(txt.getText()):
							"��������ȷ������"
					);
			txt.setText("");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		button.setText("�ٲ�һ��");
		getJPanel().updateUI();
	}

	private static String show(String consName) throws Exception{
		
		//��д�������������
		String cNmae=consName.endsWith("��")?consName:consName+"��";
		Map<String, Object> starinfo=new HashMap<>();
		starinfo.put("consName",cNmae);
		starinfo.put("type",getResource().getProperty("Star_Type"));
		starinfo.put("key",getResource().getProperty("Star_KEY"));
		
		//��ȡjson����
		JSONObject jsonObject=JSONObject.fromObject(
				getResponse(geturl(starinfo, 
						getResource().getProperty("Star_API"))));
		int errCode=jsonObject.getInt("error_code");
		
		//���ڴ洢���ַ���
		String reString=null;
		if (errCode==0) {
			//�༭���ص�json����
			reString="<html>"
			+"<br>����:"+jsonObject.getString("name")
			+"<br>����:"+jsonObject.getString("datetime")
			+"<br>�ۺ�ָ��"+jsonObject.getString("all")
			+"<br>����ɫ:"+jsonObject.getString("color")
			+"<br>����ָ��:"+jsonObject.getString("health")
			+"<br>����ָ��:"+jsonObject.getString("love")
			+"<br>����ָ��:"+jsonObject.getString("money")
			+"<br>��������:"+jsonObject.getString("number")
			+"<br>����ָ��:"+jsonObject.getString("work")
			+"<br>��������:"+jsonObject.getString("QFriend")
			+"<br>���ո���:"+jsonObject.getString("summary")
			+"</html>";
		}
		else {
			return null;
		}
		return reString;
	}
}
