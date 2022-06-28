package InterfaceTest;

import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BasicPage {
	//����������ʽ
	private Font font=new Font("����",Font.PLAIN, 20);
	private	URL url=null;
	private static	JFrame JFrame=null;
	private static	JPanel JPanel=null;
	private ImageIcon bg=null;
	private String BackgroundImg=null;
	private static	JLabel label=null;
	private static Properties resource=null;
	
	public BasicPage() throws Exception {
		//ͨ������·����ȡ�ӿڵ�����Ϣ
		//FileReader fin=new FileReader("Information.txt");
		InputStream fin=Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("InterfaceTest/Information.txt");
		resource=new Properties();
		resource.load(fin);
		fin.close();
		
		JFrame=new JFrame();//�½��������ô�С�̶�
		JFrame.setResizable(false);
		
		JFrame.setTitle("�ۺϽӿڵ���");
		//���û���ҳ�ı���
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		JFrame.setIconImage(toolkit.getImage("imges/title.png"));
		
		//����ͼƬ
		bg=new ImageIcon("imges/basic.jpg");
		label=new JLabel(bg);
		label.setSize(bg.getIconWidth()/10*7,bg.getIconHeight()/10*7);
		JFrame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		JPanel=(JPanel)JFrame.getContentPane();
		//����falseΪ����ײ�������ʾ
		JPanel.setOpaque(false);
		JPanel.setLayout(null);
		//����ҳ������
		//��ӵ�panel�о���
		JFrame.setSize(bg.getIconWidth()/10*7,bg.getIconHeight()/10*7);
		//���ô��ڱ���������Ļ������
		JFrame.setLocationRelativeTo(null);
		JFrame.setVisible(true);
		}
	//��ȡ���󣬵õ����ص�����
	protected static  String getResponse(String url) throws Exception {
		URL ur=new URL(url);
		HttpURLConnection huc =(HttpURLConnection) ur.openConnection();
		//��������ʽ����ȡ��ʱ�����ӳ�ʱ����������
		huc.setRequestMethod("GET");
		huc.setConnectTimeout(5000);
		huc.setReadTimeout(5000);
		huc.connect();
		StringBuffer jsondata=new StringBuffer();
		if (huc.getResponseCode()==200) {
			InputStream hucInputStream=huc.getInputStream();
			BufferedReader bufferedReader=
					new BufferedReader(new InputStreamReader(hucInputStream,StandardCharsets.UTF_8));
			String data;
			while ((data=bufferedReader.readLine())!=null) {
				jsondata.append(data);
				jsondata.append(System.getProperty("line.separator"));
			}
			bufferedReader.close();
			hucInputStream.close();
		}
		else {
			System.out.println("connectong Err");
		}
		huc.disconnect();
		return jsondata.toString();
	}
	//ͨ�������ϳɽӿڵ�ַ
	protected static  String geturl(Map<String, Object> cityinfo,String API_URL) {
		StringBuffer sf=new StringBuffer();
		StringBuffer sf2=new StringBuffer(API_URL);
		for(Map.Entry<String, Object> info: cityinfo.entrySet()) {
				try {
					sf.append(info.getKey()).append("=").append(URLEncoder.encode(info.getValue()+"","UTF-8")).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		}
		sf2.append("?").append(sf);
		String string=sf2.toString();
		return 	string.substring(0,string.lastIndexOf("&"));	
}
	//�ṩget��set����
	public static JPanel getJPanel() {
		return JPanel;
	}
	public void setJPanel(JPanel jPanel) {
		JPanel = jPanel;
	}
	public ImageIcon getBg() {
		return bg;
	}
	public void setBg(ImageIcon bg) {
		this.bg = bg;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	public static JFrame getJFrame() {
		return JFrame;
	}
	public void setJFrame(JFrame jFrame) {
		JFrame = jFrame;
	}
	public static JLabel getLabel() {
		return label;
	}
	public static void setLabel(JLabel label) {
		BasicPage.label = label;
	}
	public static Properties getResource() {
		return resource;
	}
	public static void setResource(Properties resource) {
		BasicPage.resource = resource;
	}
	public String getBackgroundImg() {
		return BackgroundImg;
	}
	public void setBackgroundImg(String backgroundImg) {
		BackgroundImg = backgroundImg;
	}
}

