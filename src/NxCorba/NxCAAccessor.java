package NxCorba;

import java.io.*;
import java.util.*;

/**
 * MO、Attributeの操作、Eventの起動などのメソッドを定義します。<BR>
 * このクラスをインスタンス化するとNxCASessionクラスを継承しているので自動的にCAサーバに接続します。<BR>
 * 定義されてあるメソッドを使ってCAの操作をして下さい。<BR>
 * <P>
 *
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
 * IOR_FILE_DIR=/export/home/netx/IOR<BR>
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
 * @version $Revision: 1.9 $ $Date: 2002/05/22 04:01:37 $
 * @author $Author: tookubo $
 */
public class NxCAAccessor extends NxCASession{

	/**
	 * CAへ接続する情報をIORサーバ、システムプロパティから取得するNxCAAccessorを構築します。
	 * @throws NxException CA関連での例外発生時
	 */
	public NxCAAccessor() throws NxException{
		super();
	}

	/**
	 * CAへ接続する情報をIORサーバ、接続設定ファイルから取得するNxCAAccessorを構築します。
	 * @param sessionFile 接続設定ファイル
	 * @throws NxException CA関連での例外発生時
	 */
	public NxCAAccessor(File sessionFile) throws NxException{
		super(sessionFile);
	}

	/**
	 * CAへ接続する情報をIORサーバ、Propertiesから取得するNxCAAccessorを構築します。
	 * @param prop Properties
	 * @throws NxException CA関連での例外発生時
	 */
	public NxCAAccessor(Properties prop) throws NxException{
		super(prop);
	}

	/**
	 * CAへ接続する情報をIORファイル、システムプロパティから取得するNxCAAccessorを構築します。
	 * @param iorFileDir IORファイルディレクトリ
	 * @throws NxException CA関連での例外発生時
	 */
	public NxCAAccessor(String iorFileDir) throws NxException{
		super(iorFileDir);
	}

	/**
	 * CAへ接続する情報をIORファイル、接続設定ファイルから取得するNxCAAccessorを構築します。
	 * @param iorFileDir IORファイルディレクトリ
	 * @param sessionFile 接続設定ファイル
	 * @throws NxException CA関連での例外発生時
	 */
	public NxCAAccessor(String iorFileDir, File sessionFile) throws NxException{
		super(iorFileDir, sessionFile);
	}

	/**
	 * CAへ接続する情報をIORファイル、Propertiesから取得するNxCAAccessorを構築します。
	 * @param iorFileDir IORファイルディレクトリ
	 * @param prop Properties
	 * @throws NxException CA関連での例外発生時
	 */
	public NxCAAccessor(String iorFileDir, Properties prop) throws NxException{
		super(iorFileDir, prop);
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
	public void connect(String loginId, String loginPasswd, boolean doEncrypt) throws NxException{
		super.connect(loginId, loginPasswd, doEncrypt);
	}

	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param doEncrypt			true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void connect(boolean doEncrypt) throws NxException{
		super.connect(doEncrypt);
	}

	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param sessionFile		接続設定ファイル
	 * @param doEncrypt			true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void connect(File sessionFile, boolean doEncrypt) throws NxException{
		super.connect(sessionFile, doEncrypt);
	}

	/**
	 * サーバとの接続をします。<BR>
	 * CA2.x 用 login メソッド
	 *
	 * @param prop				Properties
	 * @param doEncrypt			true - 暗号化  false - 非暗号化
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void connect(Properties prop, boolean doEncrypt) throws NxException{
		super.connect(prop, doEncrypt);
	}

	/**
	 * サーバとの接続をします。
	 *
	 * @param loginId		NetExpertログインID
	 * @param loginPasswd	NetExpertログインパスワード
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void connect(String loginId, String loginPasswd) throws NxException{
		super.connect(loginId, loginPasswd);
	}

	/**
	 * サーバとの接続をします。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void connect() throws NxException{
		super.connect();
	}

	/**
	 * サーバとの接続をします。
	 *
	 * @param sessionFile		接続設定ファイル
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void connect(File sessionFile) throws NxException{
		super.connect(sessionFile);
	}

	/**
	 * サーバとの接続をします。
	 *
	 * @param prop				Properties
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void connect(Properties prop) throws NxException{
		super.connect(prop);
	}


	/**
	 * CAとの接続を切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void disconnect() throws NxException{
		alertSessionDisconnect();
		moSessionDisconnect();
		ideasSessionDisconnect();
		authenticationDisconnect();
	}

	/**
	 * 認証接続を切断する。実際には各Sesionが確立していれば、切断することはできない。
	 *
	 */
	public void authenticationDisconnect(){
		super.authenticationDisconnect();
//		authenticator.authenticationDisconnect();
	}

	/**
	 * AlertSessionを切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void alertSessionDisconnect() throws NxException{
		super.alertSessionDisconnect();
//		alertSession.alertSessionDisconnect();
	}

	/**
	 * MoSessionを切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void moSessionDisconnect() throws NxException{
		super.moSessionDisconnect();
//		moSession.moSessionDisconnect();
	}

	/**
	 * IdeasSessionを切断する。
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void ideasSessionDisconnect() throws NxException{
		super.ideasSessionDisconnect();
//		ideasSession.ideasSessionDisconnect();
	}

	/* -------------------------------------------------------------------------
	 AlertSession系
	------------------------------------------------------------------------- */
	/**
	 * Alertの定義を取得します。
	 *
	 * @param alertName_in Alert名
	 * @return Alert定義
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public AlertDefinitionWrapper getNxAlertDefinition(String alertName_in) throws NxException{

		return alertSession.getNxAlertDefinition(alertName_in);

	}

	/**
	 * Alertを取得します。
	 *
	 * @param filter_in filter
	 * @return Alert配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public AlertWrapper[] getNxOpenAlerts(String filter_in) throws NxException{

		return alertSession.getNxOpenAlerts(filter_in);

	}

	/**
	 * Alertを認知する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public String[] acknowledgeNxAlerts(String[] alerts_in) throws NxException{

		return alertSession.acknowledgeNxAlerts(alerts_in);

	}

	/**
	 * Alertを非認知する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public String[] unacknowledgeNxAlerts(String[] alerts_in) throws NxException{

		return alertSession.unacknowledgeNxAlerts(alerts_in);

	}

	/**
	 * Alertを消去する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public String[] clearNxAlerts(String[] alerts_in) throws NxException{

		return alertSession.clearNxAlerts(alerts_in);

	}

	/* -------------------------------------------------------------------------
	 MoSession系
	------------------------------------------------------------------------- */
	/**
	 * MOを生成します。
	 * @param object_in MO名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param superior_in 親MO名
	 * @param attributes_in 属性値
	 * @return 生成したMO名
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public String createNxObject(String object_in, String class_in, String manager_in, 
								String superior_in, NxAttributeWrapper[] attributes_in) throws NxException{

		return moSession.createNxObject(object_in, class_in, manager_in, superior_in, attributes_in);

	}

	/**
	 * MOを削除します。
	 * @param target_in 削除対象を表すNxTarget
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void deleteNxObjects(NxTarget target_in) throws NxException{

		moSession.deleteNxObjects(target_in);

	}

	/**
	 * MOの配列を取得します。
	 * @param target_in 取得対象を表すNxTarget
	 * @param withAttributes_in 属性も取得するならtrue、MOのみならfalse
	 * @return 取得したMOの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public NxObjectWrapper[] getNxObjects(NxTarget target_in, boolean withAttributes_in) throws NxException{

		return moSession.getNxObjects(target_in, withAttributes_in);

	}

	/**
	 * MO名を取得します。
	 * @param target_in 取得対象を表すNxTarget
	 * @return 取得したMO名の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public String[] getNxObjectIds(NxTarget target_in) throws NxException{

		return moSession.getNxObjectIds(target_in);

	}

	/**
	 * Attributeの値を取得します。<BR>
	 * 取得する値はNetExpertのAttributeの型によって違うので、以下を参考にキャストをして下さい。<BR>
	 * <P>
	 * Boolean -> Boolean<BR>
	 * Long -> Long<BR>
	 * double -> Double<BR>
	 * Enum -> String<BR>
	 * String -> String<BR>
	 * MO -> String<BR>
	 * SequenceOfLong -> long[]<BR>
	 * SequenceOfDouble -> double[]<BR>
	 * SequenceOfEnum -> String[]<BR>
	 * SequenceOfString -> String[]<BR>
	 * SequenceOfMO -> String[]
	 * @param object_in 取得対象を表すMO名
	 * @param attribute_in 取得対象を表すAttribute名
	 * @return 取得した値
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public Object getNxAttribute(String object_in, String attribute_in) throws NxException{

		return moSession.getNxAttribute(object_in, attribute_in);

	}


	/**
	 * Attributeの配列を取得します。
	 * @param object_in 取得対象を表すMO名
	 * @param attributes_in 取得対象を表すAttribute名
	 * @return 取得したAttributeの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public NxAttributeWrapper[] getNxAttributes(String object_in, String[] attributes_in) throws NxException{

		return moSession.getNxAttributes(object_in, attributes_in);

	}

	/**
	 * Attributeを設定します。
	 * @param target_in 設定対象を表すNxTarget
	 * @param attributes_in 設定する属性
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void setNxAttribute(NxTarget target_in, NxAttributeWrapper attribute_in) throws NxException{

		moSession.setNxAttribute(target_in, attribute_in);

	}

	/**
	 * Attributeの配列を設定します。
	 * @param target_in 設定対象を表すNxTarget
	 * @param attributes_in 設定する属性の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void setNxAttributes(NxTarget target_in, NxAttributeWrapper[] attributes_in) throws NxException{

		moSession.setNxAttributes(target_in, attributes_in);

	}


	/* -------------------------------------------------------------------------
	 IdeasSession系
	------------------------------------------------------------------------- */

	/**
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void generateNxEvent(String event_in, String class_in, String manager_in, 
								NxAttributeWrapper[] attributes_in) throws NxException{

		ideasSession.generateNxEvent(event_in, class_in, manager_in, attributes_in);

	}


	/**
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @return Eventからのアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public NxAttributeWrapper[] generateNxEventConfirmed(String event_in, String class_in, 
														String manager_in, NxAttributeWrapper[] attributes_in)
			throws NxException{

		return ideasSession.generateNxEventConfirmed(event_in, class_in, manager_in, attributes_in);

	}


	// オリジナル メソッド

	/* -------------------------------------------------------------------------
	 AlertSession系
	------------------------------------------------------------------------- */
	/* -------------------------------------------------------------------------
	 MoSession系
	------------------------------------------------------------------------- */
	/**
	 * MOを生成します。
	 * @param object_in MO名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param superior_in 親MO名
	 * @param attributes_in 属性値
	 * @return 生成したMO名
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public String createObject( String object_in, String class_in, String manager_in, 
								String superior_in, NetExpert_v1.NxAttribute[] attributes_in) throws NxException{

		return moSession.createObject(object_in, class_in, manager_in, superior_in, attributes_in);

	}

	/**
	 * MOを削除します。
	 * @param target_in 削除対象を表すNetExpert_v1.Target
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void deleteObjects(NetExpert_v1.Target target_in) throws NxException{

		moSession.deleteObjects(target_in);

	}

	/**
	 * MOの配列を取得します。
	 * @param target_in 取得対象を表すNetExpert_v1.Target
	 * @param withAttributes_in 属性も取得するならtrue、MOのみならfalse
	 * @return 取得したMOの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public NxObjects_v1.NxObject[] getObjects(NetExpert_v1.Target target_in, 
												boolean withAttributes_in) throws NxException{

		return moSession.getObjects(target_in, withAttributes_in);

	}

	/**
	 * MO名を取得します。
	 * @param target_in 取得対象を表すNetExpert_v1.Target
	 * @return 取得したMO名の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public String[] getObjectIds(NetExpert_v1.Target target_in) throws NxException{

		return moSession.getObjectIds(target_in);

	}

	/**
	 * Attributeの値を取得します。<BR>
	 * @param object_in 取得対象を表すMO名
	 * @param attribute_in 取得対象を表すAttribute名
	 * @return 取得した値
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public NetExpert_v1.AttributeValue getAttribute(String object_in, String attribute_in) throws NxException{

		return moSession.getAttribute(object_in, attribute_in);

	}

	/**
	 * getAttributesを実行する。
	 * @param object_in 取得対象MO名
	 * @param attributes_in 取得対象属性名
	 * @return 取得した属性の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public NetExpert_v1.NxAttribute[] getAttributes(String object_in, String[] attributes_in) throws NxException{

		return moSession.getAttributes(object_in, attributes_in);

	}

	/**
	 * Attributeを設定します。
	 * @param target_in 設定対象を表すNetExpert_v1.Target
	 * @param attributes_in 設定する属性
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void setAttribute(NetExpert_v1.Target target_in, NetExpert_v1.NxAttribute attribute_in) throws NxException{

		moSession.setAttribute(target_in, attribute_in);

	}

	/**
	 * Attributeの配列を設定します。
	 * @param target_in 設定対象を表すNetExpert_v1.Target
	 * @param attributes_in 設定する属性の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void setAttributes(NetExpert_v1.Target target_in, 
								NetExpert_v1.NxAttribute[] attributes_in)throws NxException{

		moSession.setAttributes(target_in, attributes_in);

	}

	/* -------------------------------------------------------------------------
	 IdeasSession系
	------------------------------------------------------------------------- */
	/**
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void generateEvent(String event_in, String class_in, String manager_in, 
								NetExpert_v1.NxAttribute[] attributes_in) throws NxException{

		ideasSession.generateEvent(event_in, class_in, manager_in, attributes_in);

	}

	/**
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @param イベント実行後のイベントアトリビュートの配列を持つAttributesHolder
	 * @return Eventからのアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void generateEventConfirmed(String event_in, String class_in, String manager_in, 
										NetExpert_v1.NxAttribute[] attributes_in, 
										NetExpert_v1.AttributesHolder attributes_out) throws NxException{

		ideasSession.generateEventConfirmed(event_in, class_in, manager_in, attributes_in, attributes_out);

	}
}


