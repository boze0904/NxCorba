package NxCorba;

import java.net.*;
import java.io.*;
import java.util.*;
import org.omg.CORBA.ORB;

/**
 * IORサーバーに接続し、Caserverとの接続をします。
 *
 * @version $Revision: 1.5 $ $Date: 2002/05/22 04:01:39 $
 * @author $Author: tookubo $
 */
class IORConnect{

	/** ORB */
	private org.omg.CORBA.ORB orb_;

	/** IORデータのHashtable */
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
	 * IORファイルディレクトリから Caserverの接続情報を取得します。
	 *
	 * @param iorFileDir IORファイルディレクトリ
	 * @param nxSystem NetExpertシステム
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
 	 * IORサーバーに接続し、Caserverの接続情報を取得します。
	 *
	 * @param orb org.omg.CORBA.ORB
	 * @param host IORサーバーのホスト
	 * @param port IORサーバーのポート
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
	 * Caserverへの接続情報を取得します。
	 *
	 * @param host IORサーバーホスト
	 * @param port IORサーバーポート
	 * @return Caserver接続情報
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
		
        /*ストリームソケットを作成して、指定されたホストのポートに接続する*/
		sock=new Socket(host,port);
        /*接続したソケットの入力ストリームから読み込むInputStreamを作成する*/
		in = new ObjectInputStream(sock.getInputStream());
		
        /*ObjectInputStreamからオブジェクトを読み込む*/
		h = (Hashtable)in.readObject();

		in.close();
		sock.close();

		return h;
	}

	/**
	 * Caserverへの接続情報を取得します。
	 * @param file IORファイル名
	 * @return Caserver接続情報
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
			System.out.println("IORファイルの読み込みに失敗しました");
			e.printStackTrace();
		}

//		System.out.println(result+"\n");
		return result;
	}

	/**
	 * iorDataを返します。
	 *
	 * @return IORデータ
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
