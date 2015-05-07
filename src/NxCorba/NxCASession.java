package NxCorba;

import java.io.*;
import java.util.*;

/**
 * このクラスはNetExpert接続用のクラスです。Caserverと接続をし、Session Objectを取得します。<BR>
 *
 * @version $Revision: 1.6 $ $Date: 2002/05/22 04:01:37 $
 * @author $Author: tookubo $
 */
class NxCASession{

	/** NxAuthenticator */
	protected NxAuthenticator authenticator;
	/** NxAlertSession */
	protected NxAlertSession alertSession;
	/** NxMoSession */
	protected NxMoSession moSession;
	/** NxIdeasSession */
	protected NxIdeasSession ideasSession;

	/**
	 * CAへ接続する情報をIORサーバ、システムプロパティから取得し、NxAuthenticatorのコンストラクタを呼び出します。
	 * @throws NxException CA関連での例外発生時
	 *
	 */
	protected NxCASession() throws NxException{
		authenticator = new NxAuthenticator();
	}


	/**
	 * CAへ接続する情報をIORサーバ、接続設定ファイルから取得し、NxAuthenticatorのコンストラクタを呼び出します。
	 *
	 * @param sessionFile 接続設定ファイル
	 * @throws NxException CA関連での例外発生時
	 */
	protected NxCASession(File sessionFile) throws NxException{
		authenticator = new NxAuthenticator(sessionFile);
	}

	/**
	 * CAへ接続する情報をIORサーバ、Propertiesから取得し、NxAuthenticatorのコンストラクタを呼び出します。
	 *
	 * @param prop Properties
	 * @throws NxException CA関連での例外発生時
	 */
	protected NxCASession(Properties prop) throws NxException{
		authenticator = new NxAuthenticator(prop);
	}

	/**
	 * CAへ接続する情報をIORファイル、システムプロパティから取得し、NxAuthenticatorのコンストラクタを呼び出します。
	 *
	 * @param iorFileDir IORファイルディレクトリ
	 * @throws NxException CA関連での例外発生時
	 */
	protected NxCASession(String iorFileDir) throws NxException{
		authenticator = new NxAuthenticator(iorFileDir);
	}

	/**
	 * CAへ接続する情報をIORファイル、接続設定ファイルから取得し、NxAuthenticatorのコンストラクタを呼び出します。
	 *
	 * @param iorFileDir IORファイルディレクトリ
	 * @param sessionFile 接続設定ファイル
	 * @throws NxException CA関連での例外発生時
	 */
	protected NxCASession(String iorFileDir, File sessionFile) throws NxException{
		authenticator = new NxAuthenticator(iorFileDir, sessionFile);
	}

	/**
	 * CAへ接続する情報をIORファイル、Propertiesから取得し、NxAuthenticatorのコンストラクタを呼び出します。
	 *
	 * @param iorFileDir IORファイルディレクトリ
	 * @param prop Properties
	 * @throws NxException CA関連での例外発生時
	 */
	protected NxCASession(String iorFileDir, Properties prop) throws NxException{
		authenticator = new NxAuthenticator(iorFileDir, prop);
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
		authenticator.connect(loginId, loginPasswd, doEncrypt);
		makeSession(authenticator);
	}

	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param doEncrypt			true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(boolean doEncrypt) throws NxException{
		authenticator.connect(doEncrypt);
		makeSession(authenticator);
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
		authenticator.connect(sessionFile, doEncrypt);
		makeSession(authenticator);
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
		authenticator.connect(prop, doEncrypt);
		makeSession(authenticator);
	}

	/**
	 * サーバとの接続をします。
	 *
	 * @param loginId		NetExpertログインID
	 * @param loginPasswd	NetExpertログインパスワード
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(String loginId, String loginPasswd) throws NxException{
		authenticator.connect(loginId, loginPasswd);
		makeSession(authenticator);
	}

	/**
	 * サーバとの接続をします。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect() throws NxException{
		authenticator.connect();
		makeSession(authenticator);
	}

	/**
	 * サーバとの接続をします。
	 *
	 * @param sessionFile		接続設定ファイル
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(File sessionFile) throws NxException{
		authenticator.connect(sessionFile);
		makeSession(authenticator);
	}

	/**
	 * サーバとの接続をします。
	 *
	 * @param prop				Properties
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void connect(Properties prop) throws NxException{
		authenticator.connect(prop);
		makeSession(authenticator);
	}


	/**
	 * NxAlertSession、NxMoSession、NxMoSession、NxAlertNotification、NxObjectNotificationを生成する。
	 *
	 * @param authenticator NxAuthenticatorのインスタンス
	 */
	protected void makeSession(NxAuthenticator authenticator){
		makeAlertSession(authenticator);
		makeMoSession(authenticator);
		makeIdeasSession(authenticator);
	}
	/**
	 * NxAlertSessionを生成する。
	 *
	 * @param authenticator NxAuthenticatorのインスタンス
	 */
	protected void makeAlertSession(NxAuthenticator authenticator){
		alertSession = new NxAlertSession(authenticator);
	}

	/**
	 * NxMoSessionを生成する。
	 *
	 * @param authenticator NxAuthenticatorのインスタンス
	 */
	protected void makeMoSession(NxAuthenticator authenticator){
		moSession = new NxMoSession(authenticator);
	}

	/**
	 * NxIdeasSessionを生成する。
	 *
	 * @param authenticator NxAuthenticatorのインスタンス
	 */
	protected void makeIdeasSession(NxAuthenticator authenticator){
		ideasSession = new NxIdeasSession(authenticator);
	}

	/**
	 * CAとの接続を切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void disconnect() throws NxException{
		alertSessionDisconnect();
		moSessionDisconnect();
		ideasSessionDisconnect();
		authenticationDisconnect();
	}

	/**
	 * 認証接続を切断する。実際には各Sesionが確立していれば、切断することはできない。
	 *
	 */
	protected void authenticationDisconnect(){
		if(authenticator != null){
			authenticator.authenticationDisconnect();
		}
	}

	/**
	 * AlertSessionを切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void alertSessionDisconnect() throws NxException{
		if(alertSession != null){
			alertSession.alertSessionDisconnect();
		}
	}

	/**
	 * MoSessionを切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void moSessionDisconnect() throws NxException{
		if(moSession != null){
			moSession.moSessionDisconnect();
		}
	}

	/**
	 * IdeasSessionを切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void ideasSessionDisconnect() throws NxException{
		if(ideasSession != null){
			ideasSession.ideasSessionDisconnect();
		}
	}

}
