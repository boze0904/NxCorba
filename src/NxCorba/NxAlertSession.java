package NxCorba;


/**
 * AlertSessionの確立及び、AlertSessionのメソッドを定義したクラスです。
 *
 * @version $Revision: 1.1 $ $Date: 2002/03/29 13:48:41 $
 * @author $Author: inagashima $
 */
class NxAlertSession{

	/** org.omg.CORBA.Object */
	protected org.omg.CORBA.Object objRef;

	/** NxAuthenticator */
	protected NxAuthenticator authenticator;

	/** Alert SessionManager */
	protected NxAlerts_v1.SessionManager alertSessionManager;
	/** Alert Session */
	protected NxAlerts_v1.Session alertSession;

	/** AlertSession状態 */
	private boolean isAlertSession = false;


	/**
	 * NxAlertSessionを構築します。
	 *
	 * @param authenticator NxAuthenticatorのインスタンス
	 */
	protected NxAlertSession(NxAuthenticator authenticator){
		this.authenticator = authenticator;
	}

	/**
	 * AlertSessionを生成する。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void makeAlertSession() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isAlertSession()){
			return;
		}

		// AlertSessionManagerの生成準備
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.ALERT_SESSION).toString());

		// AlertSessionManagerの生成
		alertSessionManager = NxAlerts_v1.SessionManagerHelper.narrow(objRef);

		try{
			alertSession = alertSessionManager.open(authenticator.credentials,
							authenticator.PRIO_TYPE[authenticator.alertSessionPriority], 
							authenticator.alertSessionTimeout);
		}catch(NxAlerts_v1.InvalidCredentials ic){
			throw new NxException(ic);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		isAlertSession = true;
	}

	/**
	 * AlertSessionのセッション状態を返す。
	 * @return 生成状態ならtrue、未生成状態ならfalse
	 */
	protected boolean isAlertSession(){
		return isAlertSession;
	}

	/**
	 * AlertSessionを切断する。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void alertSessionDisconnect() throws NxException{

		if(alertSessionManager != null) {
			try{
				alertSessionManager.close(alertSession);
			}catch(NxAlerts_v1.ProcessingFailure pf){
				throw new NxException(pf);
			}
		}
		isAlertSession = false;
	}

	/**
	 * Alert定義を取得する。
	 *
	 * @param alertName Alert名
	 * @return Alert定義
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected AlertDefinitionWrapper getNxAlertDefinition(String alertName_in) throws NxException{

		makeAlertSession();

		NxAlerts_v1.AlertDefinition alertDefinition;

		try{
			 alertDefinition = alertSession.getAlertDefinition(alertName_in);
		}catch(NxAlerts_v1.NoAlertDefinition nad){
			throw new NxException(nad);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		AlertDefinitionWrapper nxAlertDefinition = new AlertDefinitionWrapper(alertDefinition);
		return nxAlertDefinition;
	}

	/**
	 * Alertを取得する。
	 *
	 * @param filter_in filter
	 * @return Alert配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected AlertWrapper[] getNxOpenAlerts(String filter_in) throws NxException{

		makeAlertSession();

		NxAlerts_v1.Alert[] alert;

		try{
			 alert = alertSession.getOpenAlerts(filter_in);
		}catch(NxAlerts_v1.InvalidFilter iF){
			throw new NxException(iF);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		AlertWrapper[] nxAlert = new AlertWrapper[alert.length];
		for(int i = 0; i < alert.length; i++){
			nxAlert[i] = new AlertWrapper(alert[i]);
		}
		return nxAlert;
	}

	/**
	 * Alertを認知する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] acknowledgeNxAlerts(String[] alerts_in) throws NxException{

		makeAlertSession();

		try{
			return alertSession.acknowledgeAlerts(alerts_in);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * Alertを非認知する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] unacknowledgeNxAlerts(String[] alerts_in) throws NxException{

		makeAlertSession();

		try{
			return alertSession.unacknowledgeAlerts(alerts_in);
		}catch(NxAlerts_v1.InvalidOwner io){
			throw new NxException(io);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * Alertを消去する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] clearNxAlerts(String[] alerts_in) throws NxException{

		makeAlertSession();

		try{
			return alertSession.clearAlerts(alerts_in);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	// オリジナルメソッド

	/**
	 * Alert定義を取得する。
	 *
	 * @param alertName Alert名
	 * @return Alert定義
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NxAlerts_v1.AlertDefinition getAlertDefinition(String alertName_in) throws NxException{

		makeAlertSession();

		try{
			 return alertSession.getAlertDefinition(alertName_in);
		}catch(NxAlerts_v1.NoAlertDefinition nad){
			throw new NxException(nad);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

	}

	/**
	 * Alertを取得する。
	 *
	 * @param filter_in filter
	 * @return Alert配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NxAlerts_v1.Alert[] getOpenAlerts(String filter_in) throws NxException{

		makeAlertSession();

		try{
			 return alertSession.getOpenAlerts(filter_in);
		}catch(NxAlerts_v1.InvalidFilter iF){
			throw new NxException(iF);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

	}

	/**
	 * Alertを認知する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] acknowledgeAlerts(String[] alerts_in) throws NxException{

		makeAlertSession();

		try{
			return alertSession.acknowledgeAlerts(alerts_in);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * Alertを非認知する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] unacknowledgeAlerts(String[] alerts_in) throws NxException{

		makeAlertSession();

		try{
			return alertSession.unacknowledgeAlerts(alerts_in);
		}catch(NxAlerts_v1.InvalidOwner io){
			throw new NxException(io);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * Alertを消去する。
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] clearAlerts(String[] alerts_in) throws NxException{

		makeAlertSession();

		try{
			return alertSession.clearAlerts(alerts_in);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

}
