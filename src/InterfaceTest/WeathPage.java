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
		//设置新背景,提示语和添加按钮
		TextField txt=new TextField();
		txt.setFont(getFont());
		txt.setBounds(getJFrame().getWidth()/2-175, getJFrame().getHeight()/10,150,35);
		getJPanel().add(txt);
		
		JButton button=new JButton("一键查询");
		button.setFont(getFont());
		button.setBounds(getJFrame().getWidth()/2+175, getJFrame().getHeight()/10,150,35);
		getJPanel().add(button);
		
		JLabel city=new JLabel("请输入城市:");
		city.setFont(getFont());
		city.setForeground(Color.white);
		city.setBounds(getJFrame().getWidth()/2-300, getJFrame().getHeight()/10,150,35);
		getJPanel().add(city);
		//添加完控件要刷新面板
		getJPanel().updateUI();	
		
		ArrayList<JLabel> labels=new ArrayList<>();
		for (int i = 0; i <6; i++) {
			labels.add(new JLabel());
		}
		//绑定按钮触发事件,将得到的天气内容存放标签,固定在面板上
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
			//如果返回时正常,则显示全部内容
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
			//根据天气切换背景
			getLabel().setIcon(new ImageIcon(TodayWeather(weatherData[6])));
			}else {
				for (JLabel label : labels) {
					label.setText("");
				}
				JLabel ErrLable=labels.get(3);
				ErrLable.setText("网络错误或者城市名有误！");
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
		//定义数组存储返回值信息
		String[] reString=new String[7];
		//获取目标json
		JSONObject jsonObject=JSONObject.fromObject(
				getResponse(geturl(cityinfo,getResource().getProperty("Weather_URL"))));
		int error_code = jsonObject.getInt("error_code");
		
        if (error_code == 0) {
       	JSONObject result=jsonObject.getJSONObject("result");
		JSONObject realtime=result.getJSONObject("realtime");
		//编辑当天天气内容
        reString[0]="<html>"
        		+"城市:"+ result.getString("city")
        		+"<br>天气:"+realtime.getString("info")
        		+"<br>温度:"+realtime.getString( "temperature")
        		+"<br>湿度:"+realtime.getString( "humidity")
        		+"<br>风向:"+realtime.getString( "direct")
        		+"<br>风力:"+realtime.getString( "power")
        		+"<br>空气质量指数:"+realtime.getString( "aqi")
        		+"</html>";
        	JSONArray future=result.getJSONArray("future");
        	//未来5天天气内容
        	for (int i=0; i < future.size(); i++) {
				JSONObject json=future.getJSONObject(i);
        		reString[i+1]="<html>"
        	    +"日期:"+json.getString("date")  
        	    +"<br>天气:"+json.getString("weather")  
        	    +"<br>温度:"+json.getString("temperature")  
        		+"<br>风向:"+json.getString("direct")  
				+"</html>";	 
			}
        	//返回今日天气
        	reString[6]=realtime.getString("info");
        }
        else {
			return null;
		}
        return reString;
	}

	private static String TodayWeather(String weather) {
	
		//通过正则表达式大致判断今日天气情况如何，返回对应背景图
		Pattern p1=Pattern.compile("[晴][\\u4e00-\\u9fa5]*");
		Pattern p2=Pattern.compile("[\\u4e00-\\u9fa5]*[阴][\\u4e00-\\u9fa5]*");
		Pattern p3=Pattern.compile("[\\u4e00-\\u9fa5]*[云][\\u4e00-\\u9fa5]*");
		Pattern p4=Pattern.compile("[\\u4e00-\\u9fa5]*[雨][\\u4e00-\\u9fa5]*");
		Pattern p5=Pattern.compile("[\\u4e00-\\u9fa5]*[雪][\\u4e00-\\u9fa5]*");
		Pattern p6=Pattern.compile("[\\u4e00-\\u9fa5]*[雾霾]*[\\u4e00-\\u9fa5]*");
		//定义一个变量bg作为默认背景
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
