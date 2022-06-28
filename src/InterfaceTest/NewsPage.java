package InterfaceTest;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NewsPage extends BasicPage{

	public NewsPage() throws Exception {
		//�����±�������Ӱ�ť
		getLabel().setIcon(new ImageIcon("imges/news.jpg"));
		JButton button=new JButton("ˢ��һ��");
		button.setFont(getFont());
		button.setBounds(
				getJFrame().getWidth()/5*4,
				getJFrame().getHeight()/13*11,
				150,35);
		getJPanel().add(button);
		getJPanel().updateUI();
		
		ArrayList<JLabel> labels=new ArrayList<>();
		for (int i = 0; i <5 ; i++) {
			JLabel label=new JLabel();
			label.setFont(getFont());
			label.setBounds(
					getJFrame().getWidth()/9,
					135*i,
					getJFrame().getWidth()/5*4,
					getJFrame().getHeight()/5
			);
			getJPanel().add(label);
			
			labels.add(label);
		}
				
		//�󶨴����¼�
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[][] newsData=show();
					int i=0;
					for (JLabel label : labels) {	
						String url=newsData[i][1];
						//��������ǩ��������¼���������������ҳ
						label.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								try {
									URI uri=new URI(url);
									Desktop ds=Desktop.getDesktop();
									//���Դ����Ƿ��ڵ�ǰƽ̨�ϵõ�֧���Լ����Ե�ǰƽ̨�Ƿ�֧�ֲ���
									if (ds.isSupported(Action.BROWSE)&&ds.isDesktopSupported()) {
										//���ҳ��
										ds.browse(uri);
									}
								} catch (MalformedURLException e1) {
									e1.printStackTrace();
								} catch (URISyntaxException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}	
							}
						});
						//������������
						label.setText("");
						label.setText(newsData[(int)(Math.random()*30)][0]);
						i++;
					}	
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				getJPanel().updateUI();
			}
		});
			
	}
	
	private static String[][] show() throws Throwable{
		//�༭url��Ϣ
		Map<String, Object> info=new HashMap<>();
		info.put("type", getResource().getProperty("News_Type"));
		info.put("page_size",getResource().getProperty("News_PageSize"));
		info.put("key",getResource().getProperty("News_Key"));
		
		String [][] resNews=new String[Integer.valueOf(getResource().getProperty("News_PageSize"))][2];
		
		//��ȡĿ��json
		JSONObject jsonObject=JSONObject.fromObject(getResponse(geturl(info,getResource().getProperty("News_URL"))));
		int error_code = jsonObject.getInt("error_code");
        if (error_code == 0) {
       	JSONObject result=jsonObject.getJSONObject("result");
		JSONArray data=result.getJSONArray("data");
        	for (int j=0; j < data.size(); j++) {
				JSONObject json=data.getJSONObject(j);
				resNews[j][0]="<html>"
        	    +"<br>��Դ :"+json.getString("author_name")  
        	    +"<br>����ʱ��:"+json.getString("date")  
        	    +"<br>����:"+json.getString("category")
        	    +"<br>���ű���:"+json.getString("title")
        	    +"<br>���ŷ�������:"+json.getString("url")
				+"</html>";	
				resNews[j][1]=json.getString("url");
			}
        }
        return resNews;
	}

}
