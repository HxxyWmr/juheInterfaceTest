package InterfaceTest;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeathPage extends BasicPage {

	public WeathPage() throws Throwable {
		//�����±���,��ʾ�����Ӱ�ť
		TextField txt=new TextField();
		txt.setFont(getFont());
		txt.setBounds(getJFrame().getWidth()/2-175, getJFrame().getHeight()/10,150,35);
		getJPanel().add(txt);
		
		JButton button=new JButton("һ����ѯ");
		button.setFont(getFont());
		button.setBounds(getJFrame().getWidth()/2+175, getJFrame().getHeight()/10,150,35);
		getJPanel().add(button);
		
		JLabel city=new JLabel("���������:");
		city.setFont(getFont());
		city.setForeground(Color.white);
		city.setBounds(getJFrame().getWidth()/2-300, getJFrame().getHeight()/10,150,35);
		getJPanel().add(city);
		//�����ؼ�Ҫˢ�����
		getJPanel().updateUI();	
		
		ArrayList<JLabel> labels=new ArrayList<>();
		for (int i = 0; i <6; i++) {
			labels.add(new JLabel());
		}
		//�󶨰�ť�����¼�,���õ����������ݴ�ű�ǩ,�̶��������
		button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {		
			WeathEvent(labels,txt);
		}
		});		
		txt.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar()==KeyEvent.VK_ENTER) {
					WeathEvent(labels,txt);
				}
			}
		});
	}	
	private void WeathEvent(ArrayList<JLabel> labels, TextField txt) {

		try {
			String[] weatherData=show(txt.getText());
			//�������ʱ����,����ʾȫ������
			if (weatherData!=null) {
				
				int i=0;
				for (JLabel label : labels) {
					label.setText("");
					label.setFont(getFont());
					label.setForeground(Color.white);
					label.setText(weatherData[i]);
					label.setBounds(
							getJFrame().getWidth()/weatherData.length*i+100,
							getJFrame().getHeight()/25,
							getJFrame().getWidth()/weatherData.length,
							getJFrame().getHeight());
					getJPanel().add(label);
					i++;		
			}
			//���������л�����
			getLabel().setIcon(new ImageIcon(TodayWeather(weatherData[6])));
			}else {
				for (JLabel label : labels) {
					label.setText("");
				}
				JLabel ErrLable=labels.get(3);
				ErrLable.setText("���������߳���������");
				ErrLable.setFont(getFont());
				ErrLable.setForeground(Color.white);
				ErrLable.setBounds(
						getJFrame().getWidth()/3,
						getJFrame().getHeight()/2,
						500,50
						);
				getJPanel().add(ErrLable);
			//	getJPanel().updateUI();
			}
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		
		txt.setText("");
		getJPanel().updateUI();
		}
	
	private static String[] show(String cityname) throws Throwable{

		Map<String, Object> cityinfo=new HashMap<>();
		cityinfo.put("city", cityname);
		cityinfo.put("key",getResource().getProperty("Weather_KEY"));
		//��������洢����ֵ��Ϣ
		String[] reString=new String[7];
		//��ȡĿ��json
		JSONObject jsonObject=JSONObject.fromObject(
				getResponse(geturl(cityinfo,getResource().getProperty("Weather_URL"))));
		int error_code = jsonObject.getInt("error_code");
		
        if (error_code == 0) {
       	JSONObject result=jsonObject.getJSONObject("result");
		JSONObject realtime=result.getJSONObject("realtime");
		//�༭������������
        reString[0]="<html>"
        		+"����:"+ result.getString("city")
        		+"<br>����:"+realtime.getString("info")
        		+"<br>�¶�:"+realtime.getString( "temperature")
        		+"<br>ʪ��:"+realtime.getString( "humidity")
        		+"<br>����:"+realtime.getString( "direct")
        		+"<br>����:"+realtime.getString( "power")
        		+"<br>��������ָ��:"+realtime.getString( "aqi")
        		+"</html>";
        	JSONArray future=result.getJSONArray("future");
        	//δ��5����������
        	for (int i=0; i < future.size(); i++) {
				JSONObject json=future.getJSONObject(i);
        		reString[i+1]="<html>"
        	    +"����:"+json.getString("date")  
        	    +"<br>����:"+json.getString("weather")  
        	    +"<br>�¶�:"+json.getString("temperature")  
        		+"<br>����:"+json.getString("direct")  
				+"</html>";	 
			}
        	//���ؽ�������
        	reString[6]=realtime.getString("info");
        }
        else {
			return null;
		}
        return reString;
	}

	private static String TodayWeather(String weather) {
	
		//ͨ��������ʽ�����жϽ������������Σ����ض�Ӧ����ͼ
		Pattern p1=Pattern.compile("[��][\\u4e00-\\u9fa5]*");
		Pattern p2=Pattern.compile("[\\u4e00-\\u9fa5]*[��][\\u4e00-\\u9fa5]*");
		Pattern p3=Pattern.compile("[\\u4e00-\\u9fa5]*[��][\\u4e00-\\u9fa5]*");
		Pattern p4=Pattern.compile("[\\u4e00-\\u9fa5]*[��][\\u4e00-\\u9fa5]*");
		Pattern p5=Pattern.compile("[\\u4e00-\\u9fa5]*[ѩ][\\u4e00-\\u9fa5]*");
		Pattern p6=Pattern.compile("[\\u4e00-\\u9fa5]*[����]*[\\u4e00-\\u9fa5]*");
		//����һ������bg��ΪĬ�ϱ���
		String bg="imges/sun.jpg";
		if (p1.matcher(weather).matches()) {
			bg="imges/sun.jpg";
		}
		else if (p2.matcher(weather).matches()) {
			bg="imges/overcast.jpg";
		}
		else if (p3.matcher(weather).matches()) {
			bg="imges/cloud.jpg";
		}
		else if (p4.matcher(weather).matches()) {
			bg="imges/rain.jpg";
		}
		else if (p5.matcher(weather).matches()) {
			bg="imges/snow.jpg";
		}
		else if (p6.matcher(weather).matches()) {
			bg="imges/fog.jpg";
		}
		return bg;
}
}
