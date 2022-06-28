package InterfaceTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HomePage extends BasicPage{
	public HomePage() throws Exception {
		/*创建主页的按钮以及显示内容*/
		int controls=4;
		HashMap<String,String> pageInfo=new HashMap<>();
		pageInfo.put("天气查询", "通过城市名称或城市ID查询天气预报情况,城市名称如：温州、上海、北京");
		pageInfo.put("开心一下","这里面有很多来自网络的幽默、搞笑、内涵段子！");
		pageInfo.put("看个新闻", "快速浏览最新新闻头条，各类国内、国际、体育、娱乐、科技等资讯");
		pageInfo.put("今日运势", "查看星座的运势现在只需按下右边的按钮即可，快来试试吧");
		JLabel[] lables=new JLabel[controls];
		JButton[] buttons=new JButton[controls];
		int  i=0;
		//设置主页内容
		for(Map.Entry<String, String> info:pageInfo.entrySet()) {
			//添加按钮
			buttons[i]=new JButton(info.getKey());
			buttons[i].setBounds(
					getJFrame().getWidth()/4*3,
					getJFrame().getHeight()/10*(i+3),
					150, 50);
			buttons[i].setFont(getFont());
			//添加标签
			lables[i]=new JLabel(info.getValue());
			lables[i].setBounds(
					getJFrame().getWidth()/5,
					getJFrame().getHeight()/10*(i+3),
					getJFrame().getWidth()/4*3 ,50); 
			lables[i].setFont(getFont());
			
			//设置触发事件
			setAction(buttons[i],i);
			getJPanel().add(lables[i]);
			getJPanel().add(buttons[i]);
			i++;
		}
		Bottom();
		//设置主窗口关闭时程序结束
		getJFrame().setDefaultCloseOperation(getJFrame().EXIT_ON_CLOSE);
	}
	private static void Bottom() throws Exception {

		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
		Map<String, Object> starinfo=new HashMap<>();
		starinfo.put("key",getResource().getProperty("Bottom_Key"));
		starinfo.put("date",sdf.format(date));
		//获取json对象
		JSONObject jsonObject=JSONObject.fromObject(
				getResponse(geturl(starinfo, getResource().getProperty("Bottom_URL"))));
		String reString=null;
		
		JSONArray result=jsonObject.getJSONArray("result");
		
		if (result.size()>0) {
			
			JSONObject info=result.getJSONObject(result.size()/2);
			reString= "历史上的今天:"+info.getString("date")+info.getString("title");
			
			JLabel bottomlable=new JLabel(reString);
			
			Font font=new Font("黑体",Font.PLAIN, 15);
			bottomlable.setFont(font);
			bottomlable.setForeground(Color.white);
			
			bottomlable.setBounds(
			getJFrame().getWidth()/2-300,
			getJFrame().getHeight()/10*9-20,
			600, 50);
			
			getJPanel().add(bottomlable);
			getJPanel().updateUI();
		}	
		
}
	//绑定每个按钮的触发事件
	private void setAction(JButton jButton, int i) {
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switch (i) {
					case 0:
						new Thread(()->{
							try {
								new WeathPage();
							} catch (Throwable e1) {
								e1.printStackTrace();
							}
						}).start();
						break;
					case 1:
						new Thread(()->{
							try {
								new NewsPage();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}).start();
						break;
					case 2:
						new Thread(()->{
							try {
								new StarPage();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}).start();
						break;
					case 3:
						new Thread(()->{
							try {
								new FunnyPage();
							} catch (Throwable e1) {
								e1.printStackTrace();
							}
						}).start();
						break;
					}
				}
			});
		}
}

