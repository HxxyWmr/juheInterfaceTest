package InterfaceTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FunnyPage extends BasicPage{

	public FunnyPage() throws Throwable {
		//�����±�������Ӱ�ť
		getLabel().setIcon(new ImageIcon("imges/fun.jpg"));
		JButton button=new JButton("һ��ˢ��");
		button.setFont(getFont());
		//���ð�ť��С��λ��
		button.setBounds(
				getJFrame().getWidth()/5*4,
				getJFrame().getHeight()/13*11,
				150,35);		
		getJPanel().add(button);
		getJPanel().updateUI();
		String[] newsData = show();
		ArrayList<JLabel> labels=new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			
			JLabel label=new JLabel();
			label.setFont(getFont());
			label.setBounds(
					getJFrame().getWidth()/25,
					135*i,
					getJFrame().getWidth()/5*4,
					getJFrame().getHeight()/5
			);	
			labels.add(label);
		}
		//�󶨰�ť�����¼�
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for (JLabel label : labels) {
						//���ñ�ǩ���ݼ�λ��
						label.setText("");
						label.setText(newsData[(int)(Math.random()*20)]);
						getJPanel().add(label);
					}
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				button.setText("����Ц��");
				getJPanel().updateUI();
			}
		});	
		
	}
	//��ȡ�ӿڷ�������
	private  static String[] show() throws Throwable{
		int pageSize=20;
		Map<String, Object> info=new HashMap<>();
		info.put("key",getResource().getProperty("Funny_KEY"));
		info.put("pagesize",pageSize);
		String[] reString=new String[pageSize];
		//��ȡĿ��json
		JSONObject jsonObject=JSONObject.fromObject(
				getResponse(geturl(info,getResource().getProperty("Funny_URL"))));
		int error_code = jsonObject.getInt("error_code");
        if (error_code == 0) {
       	JSONObject result=jsonObject.getJSONObject("result");
       	JSONArray data=result.getJSONArray("data");
        	for (int j=0; j < data.size(); j++) {
				JSONObject json=data.getJSONObject(j);
				reString[j]="<html>"
	        	+"��������:"+json.getString("updatetime")
	        	+"<br>Ц��"+j+":"+json.getString("content")
				+"</html>";	
			}
        } 
        return reString;
	}
}
