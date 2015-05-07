package NxCorba;


/**
 * MONotifier�̊m���y�сAMONotifier�̃��\�b�h���`�����N���X�ł��B
 *
 * @version $Revision: 1.1 $ $Date: 2002/03/29 13:48:41 $
 * @author $Author: inagashima $
 */
public class NxObjectNotification{

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

	/** objectNotifier */
	protected NxObjects_v1.Notifier objectNotifier;

	/** MONotification��� */
	private boolean isObjectNotification = false;

	/** ObjectHandler */
	protected ObjectHandler objectHandler;

	/**
	 * NxObjectNotification���\�z���܂��B
	 *
	 * @param accesser NxCAAccessor�̃C���X�^���X
	 */
	public NxObjectNotification(NxCAAccessor accesser){
		this.authenticator = accesser.authenticator;
	}

	/**
	 * MONotification�𐶐�����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void makeObjectNotification() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isObjectNotification()){
			return;
		}

		// MONotifier�̐�������
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.MO_NOTIFIER).toString());

		// ObjectNotifier�̐���
		objectNotifier = NxObjects_v1.NotifierHelper.narrow(objRef);

		isObjectNotification = true;
	}

	/**
	 * MONotification�̃Z�b�V������Ԃ�Ԃ��B
	 * @return ������ԂȂ�true�A��������ԂȂ�false
	 */
	protected boolean isObjectNotification(){
		return isObjectNotification;
	}

	/**
	 * MONotification��ؒf����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void objectNotificationDisconnect() throws NxException{
		unregisterCallbackObject();
		isObjectNotification = false;
	}


	/**
	 * PortableServer ������
	 *
	 * @param handler NxObjects_v1.NotificationHandlerPOA
	 */
	private void activateCORBAServer(NxObjects_v1.NotificationHandlerPOA handler){
		try{
			oid = root_poa.activate_object(handler);
		}catch( org.omg.PortableServer.POAPackage.WrongPolicy e){
			System.out.println("****Wrong Policy for POA");
		}catch( org.omg.PortableServer.POAPackage.ServantAlreadyActive e ){
			System.out.println("****Servant Already Active");
		}
	}

	/**
	 * PortableServer �񊈐���
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
	 * registerNxCallback�𐶐�����B
	 *
	 * @param filter ObjectNotificationFilterWrapper
	 * @param objectHandler ObjectHandler
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void registerCallbackObject(ObjectNotificationFilterWrapper filter, ObjectHandler objectHandler) throws NxException{

		makeObjectNotification();

		try{
			this.objectHandler = objectHandler;
			poa_obj = authenticator.orb.resolve_initial_references("RootPOA");
			root_poa = org.omg.PortableServer.POAHelper.narrow(poa_obj);
			root_manager = root_poa.the_POAManager();
			activateCORBAServer(objectHandler);
			root_manager.activate();
			objectNotifier.registerCallback(authenticator.credentials, filter.getNotificationFilter(), objectHandler._this(authenticator.orb));
		}catch(NxObjects_v1.InvalidCredentials ic){
			throw new NxException(ic);
		}catch(NxObjects_v1.InvalidHandler ih){
			throw new NxException(ih);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.InvalidWatchAttributes iwa){
			throw new NxException(iwa);
		}catch(NxObjects_v1.MissingNotificationTypes mnt){
			throw new NxException(mnt);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}catch( org.omg.CORBA.ORBPackage.InvalidName e ){
			System.out.println("****Invalid Name RootPOA");
		}catch( org.omg.PortableServer.POAManagerPackage.AdapterInactive e){
			System.out.println("****AdapterInactive");
		}
	}

	/**
	 * registerNxCallback ����������
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void unregisterCallbackObject() throws NxException{

		if(objectHandler == null){ return; }

		makeObjectNotification();

		try{
			objectNotifier.unregisterCallback(objectHandler._this(authenticator.orb));
			deactivateCORBAServer();
		}catch(NxObjects_v1.InvalidHandler ih){
			throw new NxException(ih);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}
}
