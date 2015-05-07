package NxCorba;


/**
 * AlertNotifierの確立及び、AlertNotifierのメソッドを定義したクラスです。
 *
 * @version $Revision: 1.1 $ $Date: 2002/03/29 13:48:41 $
 * @author $Author: inagashima $
 */
public class NxAlertNotification{

	/** org.omg.CORBA.Object */
	protected org.omg.CORBA.Object objRef;

	/** org.omg.CORBA.Object */
	private org.omg.CORBA.Object poa_obj;

	/** org.omg.PortableServer.POA */
	private org.omg.PortableServer.POA root_poa;

	/** org.omg.PortableServer.POAManager */
	private org.omg.PortableServer.POAManager root_manager;

	/** activateCORBAServer */
	private byte[] oid;

	/** NxAuthenticator */
	protected NxAuthenticator authenticator;

	/** AlertNotifier */
	protected NxAlerts_v1.Notifier alertNotifier;

	/** AlertNotification状態 */
	private boolean isAlertNotification = false;

	/** AlertHandler */
	protected AlertHandler alertHandler;

	/** register状態 */
	protected boolean register = false;;

	/**
	 * NxAlertNotificationを構築します。
	 *
	 * @param accesser NxCAAccessorのインスタンス
	 */
	public NxAlertNotification(NxCAAccessor accesser){
		this.authenticator = accesser.authenticator;
	}

	/**
	 * AlertNotificationを生成する。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void makeAlertNotification() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isAlertNotification()){
			return;
		}

		// AlertNotifierの生成準備
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.ALERT_NOTIFIER).toString());

		// AlertNotifierの生成
		alertNotifier = NxAlerts_v1.NotifierHelper.narrow(objRef);

		isAlertNotification = true;
	}

	/**
	 * AlertNotificationのセッション状態を返す。
	 * @return 生成状態ならtrue、未生成状態ならfalse
	 */
	protected boolean isAlertNotification(){
		return isAlertNotification;
	}

	/**
	 * AlertNotificationを切断する。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void alertNotificationDisconnect() throws NxException{
		unregisterCallbackAlert();
		isAlertNotification = false;
	}


	/**
	 * PortableServer 活性化
	 *
	 * @param handler NxAlerts_v1.NotificationHandlerPOA
	 */
	private void activateCORBAServer(NxAlerts_v1.NotificationHandlerPOA handler){
		try{
			oid = root_poa.activate_object(handler);
		}catch( org.omg.PortableServer.POAPackage.WrongPolicy e){
			System.out.println("****Wrong Policy for POA");
		}catch( org.omg.PortableServer.POAPackage.ServantAlreadyActive e ){
			System.out.println("****Servant Already Active");
		}
	}

	/**
	 * PortableServer 非活性化
	 *
	 */
	private void deactivateCORBAServer() {
		try{
			root_poa.deactivate_object(oid);
		}catch( org.omg.PortableServer.POAPackage.WrongPolicy e){
			System.out.println("****Wrong Policy for POA");
		}catch( org.omg.PortableServer.POAPackage.ObjectNotActive e ){
			System.out.println("****Servant not Active");
		}
	}


	/**
	 * registerNxCallbackを生成する。
	 *
	 * @param filter AlertNotificationFilterWrapper
	 * @param alertHandler AlertHandler
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void registerCallbackAlert(AlertNotificationFilterWrapper filter, AlertHandler alertHandler) throws NxException{

		makeAlertNotification();

		try{
			this.alertHandler = alertHandler;
			poa_obj = authenticator.orb.resolve_initial_references("RootPOA");
			root_poa = org.omg.PortableServer.POAHelper.narrow(poa_obj);
			root_manager = root_poa.the_POAManager();
			activateCORBAServer(alertHandler);
			root_manager.activate();
			alertNotifier.registerCallback(authenticator.credentials, filter.getNotificationFilter(), alertHandler._this(authenticator.orb));

			register = true;
		}catch(NxAlerts_v1.InvalidCredentials ic){
			throw new NxException(ic);
		}catch(NxAlerts_v1.InvalidFilter iF){
			throw new NxException(iF);
		}catch(NxAlerts_v1.InvalidHandler ih){
			throw new NxException(ih);
		}catch(NxAlerts_v1.InvalidWatchAttributes iwa){
			throw new NxException(iwa);
		}catch(NxAlerts_v1.MissingNotificationTypes mnt){
			throw new NxException(mnt);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}catch( org.omg.CORBA.ORBPackage.InvalidName e ){
			System.out.println("****Invalid Name RootPOA");
		}catch( org.omg.PortableServer.POAManagerPackage.AdapterInactive e){
			System.out.println("****AdapterInactive");
		}
	}

	/**
	 * registerNxCallback を解除する
	 *
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	public void unregisterCallbackAlert() throws NxException{

		if(alertHandler == null){
			return;
		}

		if(! register){
			return;
		}

		makeAlertNotification();

		try{
			alertNotifier.unregisterCallback(alertHandler._this(authenticator.orb));
			deactivateCORBAServer();

			register = false;
		}catch(NxAlerts_v1.InvalidHandler ih){
			throw new NxException(ih);
		}catch(NxAlerts_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}
}
