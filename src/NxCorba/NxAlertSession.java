package NxCorba;


/**
 * AlertSession�̊m���y�сAAlertSession�̃��\�b�h���`�����N���X�ł��B
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

	/** AlertSession��� */
	private boolean isAlertSession = false;


	/**
	 * NxAlertSession���\�z���܂��B
	 *
	 * @param authenticator NxAuthenticator�̃C���X�^���X
	 */
	protected NxAlertSession(NxAuthenticator authenticator){
		this.authenticator = authenticator;
	}

	/**
	 * AlertSession�𐶐�����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void makeAlertSession() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isAlertSession()){
			return;
		}

		// AlertSessionManager�̐�������
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.ALERT_SESSION).toString());

		// AlertSessionManager�̐���
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
	 * AlertSession�̃Z�b�V������Ԃ�Ԃ��B
	 * @return ������ԂȂ�true�A��������ԂȂ�false
	 */
	protected boolean isAlertSession(){
		return isAlertSession;
	}

	/**
	 * AlertSession��ؒf����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert��`���擾����B
	 *
	 * @param alertName Alert��
	 * @return Alert��`
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert���擾����B
	 *
	 * @param filter_in filter
	 * @return Alert�z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert��F�m����B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert���F�m����B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert����������B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected String[] clearNxAlerts(String[] alerts_in) throws NxException{

		makeAlertSession();

		try{
			return alertSession.clearAlerts(alerts_in);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	// �I���W�i�����\�b�h

	/**
	 * Alert��`���擾����B
	 *
	 * @param alertName Alert��
	 * @return Alert��`
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert���擾����B
	 *
	 * @param filter_in filter
	 * @return Alert�z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert��F�m����B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert���F�m����B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Alert����������B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
