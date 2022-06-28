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
		//设置新背景，添加按钮
		getLabel().setIcon(new ImageIcon("imges/star.jpg"));
		TextField txt=new TextField();
		JLabel title=new JLabel("请输入星座:");
		JButton button=new JButton("查看运势");
		//设置字体
		title.setFont(getFont());
		title.setForeground(Color.white);
		txt.setFont(getFont());
		button.setFont(getFont());
		//设置标题、输入框和按钮的位置
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
		//添加按钮事件
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
					//判断输入是否有误
					show(txt.getText())!=null?
							show(txt.getText()):
							"请输入正确的星座"
					);
			txt.setText("");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		button.setText("再测一次");
		getJPanel().updateUI();
	}

	private static String show(String consName) throws Exception{
		
		//填写完整输入的内容
		String cNmae=consName.endsWith("座")?consName:consName+"座";
		Map<String, Object> starinfo=new HashMap<>();
		starinfo.put("consName",cNmae);
		starinfo.put("type",getResource().getProperty("Star_Type"));
		starinfo.put("key",getResource().getProperty("Star_KEY"));
		
		//获取json对象
		JSONObject jsonObject=JSONObject.fromObject(
				getResponse(geturl(starinfo, 
						getResource().getProperty("Star_API"))));
		int errCode=jsonObject.getInt("error_code");
		
		//用于存储的字符串
		String reString=null;
		if (errCode==0) {
			//编辑返回的json内容
			reString="<html>"
			+"<br>星座:"+jsonObject.getString("name")
			+"<br>日期:"+jsonObject.getString("datetime")
			+"<br>综合指数"+jsonObject.getString("all")
			+"<br>幸运色:"+jsonObject.getString("color")
			+"<br>健康指数:"+jsonObject.getString("health")
			+"<br>爱情指数:"+jsonObject.getString("love")
			+"<br>财运指数:"+jsonObject.getString("money")
			+"<br>幸运数字:"+jsonObject.getString("number")
			+"<br>工作指数:"+jsonObject.getString("work")
			+"<br>速配星座:"+jsonObject.getString("QFriend")
			+"<br>今日概述:"+jsonObject.getString("summary")
			+"</html>";
		}
		else {
			return null;
		}
		return reString;
	}
}
