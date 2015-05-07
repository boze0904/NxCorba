package NxCorba;

import java.net.*;
import java.io.*;
import java.util.*;
import org.omg.CORBA.ORB;

/**
 * IOR�T�[�o�[�ɐڑ����ACaserver�Ƃ̐ڑ������܂��B
 *
 * @version $Revision: 1.5 $ $Date: 2002/05/22 04:01:39 $
 * @author $Author: tookubo $
 */
class IORConnect{

	/** ORB */
	private org.omg.CORBA.ORB orb_;

	/** IOR�f�[�^��Hashtable */
	private Hashtable iorData = new Hashtable();

	protected static final String AUTHENTIC = "PrincipalAuthenticator";
	protected static final String ALERT_SESSION = "AlertSessionManager";
	protected static final String IDEAS_SESSION = "IDEASSessionManager";
	protected static final String MO_SESSION = "ManagedObjectSessionManager";
	protected static final String ALERT_NOTIFIER = "AlertNotifier";
	protected static final String MO_NOTIFIER = "ManagedObjectNotifier";
	protected static final String CA_CONFIG = "CAConfigManager";
	protected static final String RECONNECT_NOTIFIER = "ReconnectNotifier";
	protected static final String SERVER_NOTIFIER = "ServerNotifier";

	/**
	 * IOR�t�@�C���f�B���N�g������ Caserver�̐ڑ������擾���܂��B
	 *
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @param nxSystem NetExpert�V�X�e��
	 */
	protected IORConnect(String iorFileDir, String nxSystem){

		nxSystem = "nx." + nxSystem + ".caserver.";

		if (iorFileDir.charAt(iorFileDir.length() - 1) != File.separator.charAt(0)){
			iorFileDir += File.separator;
		}

		iorData.put(ALERT_NOTIFIER, getIOR(iorFileDir + nxSystem + ALERT_NOTIFIER + ".ior"));
		iorData.put(ALERT_SESSION, getIOR(iorFileDir + nxSystem + ALERT_SESSION + ".ior"));
		iorData.put(IDEAS_SESSION, getIOR(iorFileDir + nxSystem +IDEAS_SESSION + ".ior"));
		iorData.put(MO_NOTIFIER, getIOR(iorFileDir +  nxSystem + MO_NOTIFIER + ".ior"));
		iorData.put(MO_SESSION, getIOR(iorFileDir + nxSystem + MO_SESSION + ".ior"));
		iorData.put(AUTHENTIC, getIOR(iorFileDir + nxSystem + AUTHENTIC + ".ior"));
		iorData.put(CA_CONFIG, getIOR(iorFileDir + nxSystem + CA_CONFIG + ".ior"));
		iorData.put(RECONNECT_NOTIFIER, getIOR(iorFileDir + nxSystem + RECONNECT_NOTIFIER + ".ior"));
		iorData.put(SERVER_NOTIFIER, getIOR(iorFileDir + nxSystem + SERVER_NOTIFIER + ".ior"));

	}

	/**
 	 * IOR�T�[�o�[�ɐڑ����ACaserver�̐ڑ������擾���܂��B
	 *
	 * @param orb org.omg.CORBA.ORB
	 * @param host IOR�T�[�o�[�̃z�X�g
	 * @param port IOR�T�[�o�[�̃|�[�g
	 * @throws java.io.IOException
	 * @throws java.io.StreamCorruptedException
	 * @throws java.lang.ClassNotFoundException
 	 */
	protected IORConnect(org.omg.CORBA.ORB orb,String host,int port)
			throws java.io.IOException,
					java.io.StreamCorruptedException,
					java.lang.ClassNotFoundException {
		orb_ = orb;

		boolean connectError = false;

		this.iorData = getIORfromCA(host,port);
	}


	/**
	 * Caserver�ւ̐ڑ������擾���܂��B
	 *
	 * @param host IOR�T�[�o�[�z�X�g
	 * @param port IOR�T�[�o�[�|�[�g
	 * @return Caserver�ڑ����
	 * @throws java.io.IOException
	 * @throws java.io.StreamCorruptedException
	 * @throws java.lang.ClassNotFoundException
	 */
	private Hashtable getIORfromCA(String host,int port)
			throws java.io.IOException,
					java.io.StreamCorruptedException,
					java.lang.ClassNotFoundException { 
		Socket sock;
		ObjectInputStream in;
		Hashtable h = new Hashtable();
		
        /*�X�g���[���\�P�b�g���쐬���āA�w�肳�ꂽ�z�X�g�̃|�[�g�ɐڑ�����*/
		sock=new Socket(host,port);
        /*�ڑ������\�P�b�g�̓��̓X�g���[������ǂݍ���InputStream���쐬����*/
		in = new ObjectInputStream(sock.getInputStream());
		
        /*ObjectInputStream����I�u�W�F�N�g��ǂݍ���*/
		h = (Hashtable)in.readObject();

		in.close();
		sock.close();

		return h;
	}

	/**
	 * Caserver�ւ̐ڑ������擾���܂��B
	 * @param file IOR�t�@�C����
	 * @return Caserver�ڑ����
	 */
	private synchronized String getIOR(String file){
		String result = null;
		try{
			FileInputStream in = new FileInputStream(file);
			int num = in.available();
			byte[] b = new byte[num - 1];
			int res = in.read(b);
			result = new String(b);
			in.close();
		}catch(Exception e){
			System.out.println("IOR�t�@�C���̓ǂݍ��݂Ɏ��s���܂���");
			e.printStackTrace();
		}

//		System.out.println(result+"\n");
		return result;
	}

	/**
	 * iorData��Ԃ��܂��B
	 *
	 * @return IOR�f�[�^
	 */
	protected Hashtable getIorData(){
		return this.iorData;
	}


	public static void main(String[] args){
		if (args.length != 2){
			System.out.println("Usage: java IORConnect HOST PORT");
			System.exit(1);
		}

		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String[])null, null);

		IORConnect ior = null;
		try {
			ior = new IORConnect(orb, args[0], Integer.parseInt(args[1]));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Hashtable h = ior.getIorData();

		for (Enumeration e = h.keys();e.hasMoreElements();){
			String str = (String)e.nextElement();
			System.out.println(str+"=\n"+h.get(str).toString()+"\n");
		}
	}

}
