package core;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDManager;

import bd.domains.Inkas;
import bd.domains.InkasId;
import bd.domains.Key;
import bd.services.InkasService;
import bd.services.KeyService;

public class ReaderClass extends SwingWorker<String, String> {

	
	private volatile boolean finised = false;
	private String opername;
	private Double ammount;
	private String datakey;
	private static String account;
	private KeyService ks;
	private InkasService is;
	private int keyCount = 0;
	private int inkKeyCount = 0;
	private volatile String sysMess = "";
	private volatile HIDDevice hiddev = null;
	private volatile HIDDeviceInfo ourDevice = null;
	private boolean devfound = false;
	private boolean inkasKey = false;
	private boolean keyStored = false;
	private StringBuffer sb = new StringBuffer();
	private String previousKey = "";
	private volatile HIDManager hm;
	
	public ReaderClass(String opername, Double ammount) 
	{
		super();		
		System.loadLibrary("hidapi-jni-64");			
		this.opername = "1";
		if (ammount == null) this.ammount = new Double(0);else this.ammount = ammount;
		ks = new KeyService();
		this.setAccount(ks.getLastAccount());
		ks = null;		
	}
	
	public HIDDeviceInfo getOurDevice() {
		return ourDevice;
	}
	public void setOurDevice(HIDDeviceInfo ourDevice) {
		this.ourDevice = ourDevice;
	}
	public boolean isDevfound() {
		return devfound;
	}
	public void setDevfound(boolean devfound) {
		this.devfound = devfound;
	}
	public String getSysMess() {
		return sysMess;
	}
	public  synchronized void  setSysMess(String sysMess) {
		this.sysMess = sysMess;
		notifyAll();
	}
	public String getOpername() {
		return "1";
	}
	public void setOpername(String opername) {
		this.opername = "1";
	}
	public Double getAmmount() {
		return ammount;
	}
	public void setAmmount(Double ammount) {
		this.ammount = ammount;
	}
	public String getDatakey() {
		return datakey;
	}
	public void setDatakey(String datakey) {
		this.datakey = datakey;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public boolean isFinised() {
		return finised;
	}
	public synchronized void setFinised(boolean finised) {
		this.finised = finised;
		notifyAll();
	}	
	
	public boolean isKeyStored() {
		return keyStored;
	}
	public void setKeyStored(boolean keyStored) {
		this.keyStored = keyStored;
	}
	
	public HIDDevice getDev() {
		return hiddev;
	}
	public void setDev(HIDDevice dev) {
		this.hiddev = dev;
	}
	
	public void play() // Звук чтения ключа
	{
		 try {
		       // URL defaultSound = getClass().getResource("sound2.wav");// getClass().getSy.getResource("/images/ads/WindowsNavigationStart.wav");
		        File soundFile = new File("sound2.wav");
		       // System.out.println("www "+soundFile);
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start( );
		        Thread.sleep(1000);
		    } catch (Exception ex) {}
	}
	
	public void findDevice() // Ищем девайс
	{
		
		try {
			hm = HIDManager.getInstance();
			HIDDeviceInfo[] devlist =  hm.listDevices();		
			
			for(HIDDeviceInfo di: devlist)
			{
				System.out.println("Vendor ID: "+di.getVendor_id());
				System.out.println("Serial №: "+di.getSerial_number());
				if(di.getVendor_id() == 0x04d8 && di.getProduct_id() == 0x0002)
				{
					this.setDevfound(true);
					this.setOurDevice(di);
				}
			}
			if(!devfound)
			{
				firePropertyChange("progress", getSysMess(),"Девайс не подключен");
				setSysMess("Девайс не подключен");
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			firePropertyChange("progress", getSysMess(),"Ошибка подключения к шине");
			setSysMess("Ошибка подключения к шине");
		}catch  (NullPointerException e)
		{
			firePropertyChange("progress", getSysMess(),"Девайс не подключен");
			setSysMess("Девайс не подключен");
		}	
	}
	
	private boolean readKey()
	{
			try {
					HIDDevice dev = HIDManager.getInstance().openById(0x04d8, 0x0002, "0000001");
					if(dev == null){System.out.println("dev not found");} //	this.setDev(this.getOurDevice().open());		
					else{
						System.out.println("device found");
					byte[] bts = new byte[256];
					byte[] bytes = new byte[256];
					byte[] bt = new byte[256];		
				//	while(!isFinised()) // Пока не нажата кнопка завершить
					{
						bytes[1] = 0;
						while(bytes[1]!=10) // Пока не прочитан ключ
						{
							bt[0]=0;bt[1]=1;bt[2]=1; // Отправляем запрос на чтение ключа
							dev.sendFeatureReport(bt);
							try {
									Thread.sleep(100); // Ждем 0,1 сек
								} catch (InterruptedException e) {e.printStackTrace(); dev.close(); return false;}
							int bc = dev.getFeatureReport(bytes); //Получаем отчет о чтении ключа							
							if(bytes[1] == 10) // Если прочитан
							{
								for(int i = 3;i<bc+1;i++) //Форматируем битмап ключа
								{						
									if((Integer.toHexString(bytes[i] & 0xff)).length()<2)
									{
										sb.append("0");
										sb.append(Integer.toHexString(bytes[i] & 0xff));
									}
									else sb.append(Integer.toHexString(bytes[i] & 0xff));
								}
								String str = new String(sb).toUpperCase();
								System.out.println("Key readed: "+str);
								sb.delete(0, sb.length());
								if(!str.equalsIgnoreCase(previousKey))
								{
									String tmpstr = str.substring(0, 2);
									if(tmpstr.equalsIgnoreCase("33"))
									{
										bts[0]=0;bts[1]=1;bts[2]=2;
										dev.sendFeatureReport(bts);
										System.out.println("Okk");
										try {
											Thread.sleep(100); // Ждем 0,1 сек
										} catch (InterruptedException e) {e.printStackTrace(); dev.close(); return false;}
									}
									previousKey = str;
									dev.close();
									play();
									return true;
								} else{dev.close(); return false;}
							}			
						}
					}
					}
			} catch (Exception e) 
			{
				System.out.println("Девайс не подключен");
				firePropertyChange("progress", getSysMess(),"Девайс не подключен");
				setSysMess("Девайс не подключен");
				return false;
			}
			return false;
	}
		
	private void storeKey()  // Сохраняем ключ пользователя в базе
	{
		Long acc = Long.parseLong(getAccount())+1;
		//setAccount(acc.toString());		
		StringBuffer sb = new StringBuffer();
		for(int i = acc.toString().length();i<6;i++)
				sb.append("0");
		sb.append(acc.toString());
		String keyacc = new String(sb);
		setAccount(keyacc);	
		Key newKey = new Key();
		newKey.setBal(ammount);
		newKey.setBon(0);
		newKey.setBonadd(0);
		newKey.setDat(new Date());
		newKey.setDatakey(previousKey);
		newKey.setNumkey(getAccount());
		newKey.setOperator("1");
		ks = new KeyService();
		String checkKey = ks.checkClientKey(previousKey);// Проверяем наличие ключа в базе
		if(checkKey != null)
		{
			if(checkKey.equalsIgnoreCase("Error"))
			{
				firePropertyChange("progress", getSysMess(),"Ошибка проверки ключа");
				setSysMess("Ошибка проверки ключа");
			} else 
			{
				firePropertyChange("progress", getSysMess(),"Ключ "+previousKey+" уже зарегестрирован ");
				setSysMess("Ключ уже зарегестрирован");
			}
		}
		else
		{
			if(ks.storeClientKey(newKey)) //Если ключ не зарегестрирован - добавляем
				{
					this.keyCount++;
					firePropertyChange("progress", getSysMess(),"Ключ "+previousKey+ " успешно зарегестрирован");
					firePropertyChange("keys", null,"Инкассатора: "+inkKeyCount+"\n Пользователя: "+keyCount);
					setSysMess("Ключ "+previousKey+ " успешно зарегестрирован  ");
				}
			else
				{
					firePropertyChange("progress", getSysMess(),"Ошибка сохранения ключа");
					setSysMess("Ошибка сохранения ключа");
				}
		}		
		ks = null;
	}
	
	private void storeInkasKey() // Сохраняем ключ инкассатора в базе 
	{
		Key newKey = new Key();
		newKey.setBal(0);
		newKey.setBon(ammount);
		newKey.setBonadd(0);
		newKey.setDat(new Date());
		newKey.setDatakey(previousKey);
		newKey.setNumkey("000000");
		newKey.setOperator("1");
		InkasId iID = new InkasId();
		iID.setDat(new Date());
		iID.setDatakey(previousKey);
		iID.setName("");
		iID.setOperator(getOpername());
		iID.setPrava("Запрещено");
		iID.setPrim("");
		iID.setSumm(new Double(0));
		Inkas ink = new Inkas(iID);
		ks = new KeyService();
		String checkKey = ks.checkClientKey(previousKey);// Проверяем наличие ключа в базе
		if(checkKey != null)
		{
			if(checkKey.equalsIgnoreCase("Error"))
			{
				firePropertyChange("progress", getSysMess(),"Ошибка проверки ключа");
				setSysMess("Ошибка проверки ключа");
			} else 
			{
				firePropertyChange("progress", getSysMess(),"Ключ инкассатора уже зарегестрирован");
				setSysMess("Ключ инкассатора "+previousKey+" уже зарегестрирован");
			}
		}
		else
		{
			is = new InkasService();
			if(ks.storeClientKey(newKey)&&is.storeInkas(ink)) //Если ключ не зарегестрирован - добавляем
				{
					this.inkKeyCount++;
					firePropertyChange("progress", getSysMess(),"Ключ инкассатора "+previousKey+ " успешно зарегестрирован");
					firePropertyChange("keys", null,"Инкассатора: "+inkKeyCount+"\n Пользователя: "+keyCount);
					setSysMess("Ключ инкассатора "+previousKey+ " успешно зарегестрирован"); 
				}
		else
				{
					firePropertyChange("progress", getSysMess(),"Ошибка сохранения ключа" );
					setSysMess("Ошибка сохранения ключа");
				}
		}		
		ks = null;
	}
	@Override
	protected String doInBackground() throws Exception 
	{
		while(!isFinised())
			{
				System.out.println("working");
				if(readKey()) // Читаем ключ
				{
					// Проверяем какой ключ считан инкассатора или пользователя и сохраняем в базе
					if (previousKey.substring(0, 2).equalsIgnoreCase("33")) {storeInkasKey();}else{storeKey();};
				}
			}
		System.out.println("Ending....");
		setSysMess("Добавлено ключей "+keyCount);
		return getSysMess();
	}		
}
