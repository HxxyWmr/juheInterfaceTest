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
		/*������ҳ�İ�ť�Լ���ʾ����*/
		int controls=4;
		HashMap<String,String> pageInfo=new HashMap<>();
		pageInfo.put("������ѯ", "ͨ���������ƻ����ID��ѯ����Ԥ�����,���������磺���ݡ��Ϻ�������");
		pageInfo.put("����һ��","�������кܶ������������Ĭ����Ц���ں����ӣ�");
		pageInfo.put("��������", "���������������ͷ����������ڡ����ʡ����������֡��Ƽ�����Ѷ");
		pageInfo.put("��������", "�鿴��������������ֻ�谴���ұߵİ�ť���ɣ��������԰�");
		JLabel[] lables=new JLabel[controls];
		JButton[] buttons=new JButton[controls];
		int  i=0;
		//������ҳ����
		for(Map.Entry<String, String> info:pageInfo.entrySet()) {
			//��Ӱ�ť
			buttons[i]=new JButton(info.getKey());
			buttons[i].setBounds(
					getJFrame().getWidth()/4*3,
					getJFrame().getHeight()/10*(i+3),
					150, 50);
			buttons[i].setFont(getFont());
			//��ӱ�ǩ
			lables[i]=new JLabel(info.getValue());
			lables[i].setBounds(
					getJFrame().getWidth()/5,
					getJFrame().getHeight()/10*(i+3),
					getJFrame().getWidth()/4*3 ,50); 
			lables[i].setFont(getFont());
			
			//���ô����¼�
			setAction(buttons[i],i);
			getJPanel().add(lables[i]);
			getJPanel().add(buttons[i]);
			i++;
		}
		Bottom();
		//���������ڹر�ʱ�������
		getJFrame().setDefaultCloseOperation(getJFrame().EXIT_ON_CLOSE);
	}
	private static void Bottom() throws Exception {

		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
		Map<String, Object> starinfo=new HashMap<>();
		starinfo.put("key",getResource().getProperty("Bottom_Key"));
		starinfo.put("date",sdf.format(date));
		//��ȡjson����
		JSONObject jsonObject=JSONObject.fromObject(
				getResponse(geturl(starinfo, getResource().getProperty("Bottom_URL"))));
		String reString=null;
		
		JSONArray result=jsonObject.getJSONArray("result");
		
		if (result.size()>0) {
			
			JSONObject info=result.getJSONObject(result.size()/2);
			reString= "��ʷ�ϵĽ���:"+info.getString("date")+info.getString("title");
			
			JLabel bottomlable=new JLabel(reString);
			
			Font font=new Font("����",Font.PLAIN, 15);
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
	//��ÿ����ť�Ĵ����¼�
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

