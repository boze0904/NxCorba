package NxCorba;

import java.io.*;
import java.util.*;
import org.omg.CORBA.ORB;

/**
 * このクラスはNetExpert接続用のクラスです。Caserverと接続をし、各Sessionの参照準備をします。<BR>
 * コンストラクタに必要なIORファイルディレクトリ、システムプロパティ、及び接続環境ファイルの設定方法は以下の通りです。<BR>
 * <P>
 * <P>
 *
 * IORファイルディレクトリ :<BR>
 * /export/home/netx/IOR
 * <P>
 * システムプロパティ 接続環境ファイル :<BR>
 * NXSYSTEM : NetExpertシステム名  ← IORサーバ経由ではない時<BR>
 * IOR_SERVER_HOST : IORサーバホストIP、もしくはホスト名  ← IORサーバ経由の時<BR>
 * IOR_SERVER_PORT : IORサーバポート(数値)  ← IORサーバ経由の時<BR>
 * IOR_FILE_DIR : IORファイルディレクトリ ← IORファイルディレクトリが設定されていない時<BR>
 * CONNECT_RETRY : connect時のリトライ回数(数値)<BR>
 * ALERTSESSION_PRIO : AlertSession Priority(数値)<BR>
 * ALERTSESSION_TO : AlertSession タイムアウト(数値)<BR>
 * MOSESSION_PRIO : MOSession Priority(数値)<BR>
 * MOSESSION_TO : MOSesson タイムアウト(数値)<BR>
 * IDEASSESSION_PRIO : IdeasSession Priority(数値)<BR>
 * IDEASSESSION_TO : IdeasSession タイムアウト(数値)<BR>
 * <P>
 *
 * 設定例 :<BR>
 * NXSYSTEM=netx_system<BR>
 * IOR_SERVER_HOST=1.1.1.1<BR>
 * IOR_SERVER_PORT=10001<BR>
 * IOR_FILE_DIR=/export/home/netx/IOR
 * CONNECT_RETRY=5<BR>
 * LOGIN_ID=netx<BR>
 * LOGIN_PASSWD=netx<BR>
 * ALERTSESSION_PRIO=0<BR>
 * ALERTSESSION_TO=0<BR>
 * MOSESSION_PRIO=0<BR>
 * MOSESSION_TO=0<BR>
 * IDEASSESSION_PRIO=0<BR>
 * IDEASSESSION_TO=0<BR>
 *
 * <p>
 * ログインプロパティファイルも設定できます。<BR>
 * LOGIN_ID : NetExpertユーザログインID<BR>
 * LOGIN_PASSWD : NetExpertユーザパスワード<BR>
 * システムプロパティも設定例と同じです。
 *
 * @version $Revision: 1.4 $ $Date: 2002/05/22 04:01:38 $
 * @author $Author: tookubo $
 */
class NxAuthenticator{

	/** org.omg.CORBA.ORB */
	protected ORB orb = null;
	/** org.omg.CORBA.Object */
	protected org.omg.CORBA.Object objRef;

	/**
	 * NxSession_v1.Priority
	 */
	protected final static NxSession_v1.Priority[] PRIO_TYPE = {
		NxSession_v1.Priority.High,
		NxSession_v1.Priority.Medium,
		NxSession_v1.Priority.Low
	};

	/** Authenticator */
	protected NxSecurity_v1.Authenticator authenticator;

	/** Credentials */
	protected NxSecurity_v1.Credentials credentials;

	/** IORConnectインスタンス */
	protected IORConnect caConn;
	/** IORファイルディレクトリ */
	protected String iorFileDir;

	/** NetExpertシステム名 */
	protected String nxSystem;
	/** login_ID */
	protected String loginId;
	/** login_Passwd */
	protected String loginPasswd;
	/** iorServer_Host */
	protected String iorServerHost;
	/** iorServer_Port */
	protected int iorServerPort;
	/** connectRetry */
	protected int connectRetry;
	/** alertSessionPriority */
	protected int alertSessionPriority;
	/** alertSessionTimeout */
	protected int alertSessionTimeout;
	/** moSessionPriority */
	protected int moSessionPriority;
	/** moSessionTimeout */
	protected int moSessionTimeout;
	/** ideasSessionPriority */
	protected int ideasSessionPriority;
	/** ideasSessionTimeout */
	protected int ideasSessionTimeout;

	/** 接続方法フラグ */
	private boolean howFlag;

	/** Connect状態 */
	private boolean isConnect;


	/**
	 * CAへ接続する情報をIORサーバ、システムプロパティから取得するNxAuthenticatorを構築します。
	 * @throws NxException CA関連での例外発生時
	 *
	 */
	protected NxAuthenticator() throws NxException{
		//設定値の確認
		try{
			Integer.parseInt(System.getProperty(CAConstants.IOR_SERVER_PORT));
			Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("設定値に誤りがあります");
			e.printStackTrace();
		}

		iorServerHost = System.getProperty(CAConstants.IOR_SERVER_HOST);
		iorServerPort = Integer.parseInt(System.getProperty(CAConstants.IOR_SERVER_PORT));
		connectRetry = Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));

		howFlag = true;
	}


	/**
	 * CAへ接続する情報をIORサーバ、接続設定ファイルから取得するNxAuthenticatorを構築します。
	 *
	 * @param sessionFile 接続設定ファイル
 	 * @throws NxException CA関連での例外発生時
	 */
	protected NxAuthenticator(File sessionFile) throws NxException{

		//設定ファイルの読み込み
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("接続設定ファイルの読み込みに失敗しました");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		//設定値の確認
		try{
			Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("設定値に誤りがあります");
			e.printStackTrace();
		}

		iorServerHost = prop.getProperty(CAConstants.IOR_SERVER_HOST);
		iorServerPort = Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		howFlag = true;
	}

	/**
	 * CAへ接続する情報をIORサーバ、Propertiesから取得するNxAuthenticatorを構築します。
	 *
	 * @param prop Properties
 	 * @throws NxException CA関連での例外発生時
	 */
	protected NxAuthenticator(Properties prop) throws NxException{

		//設定値の確認
		try{
			Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("設定値に誤りがあります");
			e.printStackTrace();
		}

		iorServerHost = prop.getProperty(CAConstants.IOR_SERVER_HOST);
		iorServerPort = Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		howFlag = true;
	}


	/**
	 * CAへ接続する情報をIORファイル、システムプロパティから取得するNxAuthenticatorを構築します。
	 *
	 * @param iorFileDir IORファイルディレクトリ
 	 * @throws NxException CA関連での例外発生時
	 */
	protected NxAuthenticator(String iorFileDir) throws NxException{

		//設定値の確認
		try{
			Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("設定値に誤りがあります");
			e.printStackTrace();
		}

		nxSystem = System.getProperty(CAConstants.NXSYSTEM);
		connectRetry = Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));

		if(iorFileDir == null || iorFileDir.equals("")){
			this.iorFileDir = System.getProperty(CAConstants.IOR_FILE_DIR);
		}else{
			this.iorFileDir = iorFileDir;
		}
		howFlag = false;
	}

	/**
	 * CAへ接続する情報をIORファイル、接続設定ファイルから取得するNxAuthenticatorを構築します。
	 *
	 * @param iorFileDir IORファイルディレクトリ
	 * @param sessionFile 接続設定ファイル
 	 * @throws NxException CA関連での例外発生時
	 */
	protected NxAuthenticator(String iorFileDir, File sessionFile) throws NxException{

		//設定ファイルの読み込み
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("接続設定ファイルの読み込みに失敗しました");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		//設定値の確認
		try {
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("設定値に誤りがあります");
			e.printStackTrace();
		}

		nxSystem = prop.getProperty(CAConstants.NXSYSTEM);
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		if(iorFileDir == null || iorFileDir.equals("")){
			this.iorFileDir = prop.getProperty(CAConstants.IOR_FILE_DIR);
		}else{
			this.iorFileDir = iorFileDir;
		}
		howFlag = false;
	}

	/**
	 * CAへ接続する情報をIORファイル、Propertiesから取得するNxAuthenticatorを構築します。
	 *
	 * @param iorFileDir IORファイルディレクトリ
	 * @param prop Properties
 	 * @throws NxException CA関連での例外発生時
	 */
	protected NxAuthenticator(String iorFileDir, Properties prop) throws NxException{

		//設定値の確認
		try {
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("設定値に誤りがあります");
			e.printStackTrace();
		}

		nxSystem = prop.getProperty(CAConstants.NXSYSTEM);
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		if(iorFileDir == null || iorFileDir.equals("")){
			this.iorFileDir = prop.getProperty(CAConstants.IOR_FILE_DIR);
		}else{
			this.iorFileDir = iorFileDir;
		}
		howFlag = false;
	}


	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param loginId		NetExpertログインID
	 * @param loginPasswd	NetExpertログインパスワード
	 * @param doEncrypt		true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(String loginId, String loginPasswd, boolean doEncrypt) throws NxException{

		// ログインを取得
		this.loginId = loginId;
		this.loginPasswd = loginPasswd;

		
		//ORB初期化。引数付き
		isConnect = false;
		// CA2.1用
//		System.setProperty("org.omg.CORBA.ORBClass","com.iona.corba.art.artimpl.ORBImpl");
//		System.setProperty("org.omg.CORBA.ORBSingletonClass","com.iona.corba.art.artimpl.ORBSingleton");
		orb = ORB.init((String[])null, null);

		// 接続方法振り分け
		// Caserverへの接続準備
		if(howConnect()){
			//IORデータの取得
			try {
				caConn = new IORConnect(orb, iorServerHost, iorServerPort);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			caConn = new IORConnect(iorFileDir, nxSystem);
		}
		objRef = orb.string_to_object(caConn.getIorData().get(IORConnect.AUTHENTIC).toString());

		// 認証
		authenticator = NxSecurity_v1.AuthenticatorHelper.narrow(objRef);


		//CAでうまく処理されない場合の対策
		for(int retry = 0; ; retry++){
			try{
				credentials = authenticator.login(loginId, loginPasswd, doEncrypt);
			}catch(NxSecurity_v1.InvalidLogin ile){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("InvalidLogin on connect. retry...");
					continue;
				}
				throw new NxException(ile);
			}catch(NxSecurity_v1.ProcessingFailure pf){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("ProcessingFailure on connect. retry...");
					continue;
				}
				throw new NxException(pf);
			}catch(NxSecurity_v1.HASecondary hase){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("HASecondary on connect. retry...");
					continue;
				}
				throw new NxException(hase);
			}

			if(credentials == null){
				if(retry < connectRetry){
					System.err.println("credentials is null on connect. retry...");
					continue;
				}
				throw new NxException(new NxSecurity_v1.ProcessingFailure());
			}
			//成功したら抜ける
			break;
		}
		isConnect = true;
	}

	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param doEncrypt			true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(boolean doEncrypt) throws NxException{

		// ログインを取得
		String id = System.getProperty(CAConstants.LOGIN_ID);
		String passwd = System.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd, doEncrypt);

	}

	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param sessionFile		接続設定ファイル
	 * @param doEncrypt			true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(File sessionFile, boolean doEncrypt) throws NxException{

		//設定ファイルの読み込み
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("接続設定ファイルの読み込みに失敗しました");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		// ログインを取得
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd, doEncrypt);

	}

	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param prop				Properties
	 * @param doEncrypt			true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(Properties prop, boolean doEncrypt) throws NxException{

		// ログインを取得
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd, doEncrypt);

	}


	/**
	 * サーバとの接続をします。<br>
	 * 暗号化はしません。
	 *
	 * @param loginId		NetExpertログインID
	 * @param loginPasswd	NetExpertログインパスワード
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(String loginId, String loginPasswd) throws NxException{

		// ログインを取得
		this.loginId = loginId;
		this.loginPasswd = loginPasswd;

		//ORB初期化。引数付き
		isConnect = false;
		// CA2.1用
//		System.setProperty("org.omg.CORBA.ORBClass","com.iona.corba.art.artimpl.ORBImpl");
//		System.setProperty("org.omg.CORBA.ORBSingletonClass","com.iona.corba.art.artimpl.ORBSingleton");
		orb = ORB.init((String[])null, null);

		// 接続方法振り分け
		// Caserverへの接続準備
		if(howConnect()){
			//IORデータの取得
			try {
				caConn = new IORConnect(orb, iorServerHost, iorServerPort);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			caConn = new IORConnect(iorFileDir, nxSystem);
		}
		objRef = orb.string_to_object(caConn.getIorData().get(IORConnect.AUTHENTIC).toString());

		// 認証
		authenticator = NxSecurity_v1.AuthenticatorHelper.narrow(objRef);


		//CAでうまく処理されない場合の対策
		for(int retry = 0; ; retry++){
			try{
//				authenticator = caConn.loadPrincipalAuthenticator();
				credentials = authenticator.authenticate(loginId, loginPasswd);
			}catch(NxSecurity_v1.InvalidLogin ile){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("InvalidLogin on connect. retry...");
					continue;
				}
				throw new NxException(ile);
			}catch(NxSecurity_v1.ProcessingFailure pf){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("ProcessingFailure on connect. retry...");
					continue;
				}
				throw new NxException(pf);
			}catch(NxSecurity_v1.HASecondary hase){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("HASecondary on connect. retry...");
					continue;
				}
				throw new NxException(hase);
			}

			if(credentials == null){
				if(retry < connectRetry){
					System.err.println("credentials is null on connect. retry...");
					continue;
				}
				throw new NxException(new NxSecurity_v1.ProcessingFailure());
			}
			//成功したら抜ける
			break;
		}
		isConnect = true;

	}


	/**
	 * サーバとの接続をします。<br>
	 * 暗号化はしません。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect() throws NxException{

		// ログインを取得
		String id = System.getProperty(CAConstants.LOGIN_ID);
		String passwd = System.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd);

	}

	/**
	 * サーバとの接続をします。<br>
	 * 暗号化はしません。
	 *
	 * @param sessionFile		接続設定ファイル
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(File sessionFile) throws NxException{

		//設定ファイルの読み込み
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("接続設定ファイルの読み込みに失敗しました");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		// ログインを取得
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd);

	}

	/**
	 * サーバとの接続をします。<br>
	 * 暗号化はしません。
	 *
	 * @param prop				Properties
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(Properties prop) throws NxException{

		// ログインを取得
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd);

	}



	/**
	 * 接続方法を返す。
	 * @return IORサーバ経由ならtrue、接続設定ファイルならfalse
	 */
	private boolean howConnect(){
		return howFlag;
	}

	/**
	* 接続状態を返す。
	* @return 接続済みならtrue、未接続ならfalse
	*/
	protected boolean isConnect(){
		return isConnect;
	}

	/**
	 * 認証接続を切断する。実際には各Sesionが確立していれば、切断することはできない。
	 */
	protected void authenticationDisconnect(){

		if(orb != null){
//			orb = null;
//			orb.shutdown(true);
			orb.destroy();
		}
		isConnect = false;
	}
}
