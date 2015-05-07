package NxCorba;


/**
 * IdeasSession�̊m���y�сAIdeasSession�̃��\�b�h���`�����N���X�ł��B
 *
 * @version $Revision: 1.1 $ $Date: 2002/03/29 13:48:40 $
 * @author $Author: inagashima $
 */
class NxIdeasSession{

	/** org.omg.CORBA.Object */
	protected org.omg.CORBA.Object objRef;

	/** NxAuthenticator */
	protected NxAuthenticator authenticator;

	/** IDEAS SessionManager */
	protected NxIdeas_v1.SessionManager ideasSessionManager;
	/** IDEAS Session */
	protected NxIdeas_v1.Session ideasSession;

	/** IdeasSession��� */
	private boolean isIdeasSession = false;


	/**
	 * NxIdeasSession���\�z���܂��B
	 *
	 * @praram authenticator NxAuthenticator�̃C���X�^���X
	 */
	protected NxIdeasSession(NxAuthenticator authenticator){
		this.authenticator = authenticator;
	}

	/**
	 * IdeasSession�𐶐�����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void makeIdeasSession() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isIdeasSession()){
			return;
		}

		// IDEASSessionManager�̐�������
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.IDEAS_SESSION).toString());

		// IDEASSessionManager�̐���
		ideasSessionManager = NxIdeas_v1.SessionManagerHelper.narrow(objRef);

		try{
			ideasSession = ideasSessionManager.open(authenticator.credentials, 
							authenticator.PRIO_TYPE[authenticator.ideasSessionPriority], 
							authenticator.ideasSessionTimeout);
		}catch(NxIdeas_v1.InvalidCredentials ic){
			throw new NxException(ic);
		}catch(NxIdeas_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		isIdeasSession = true;
	}

	/**
	 * IdeasSession�̃Z�b�V������Ԃ�Ԃ��B
	 * @return ������ԂȂ�true�A��������ԂȂ�false
	 */
	protected boolean isIdeasSession(){
		return isIdeasSession;
	}

	/**
	 * IdeasSession��ؒf����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void ideasSessionDisconnect() throws NxException{

		if(ideasSessionManager != null) {
			try{
				ideasSessionManager.close(ideasSession);
			}catch(NxIdeas_v1.ProcessingFailure pf){
				throw new NxException(pf);
			}
		}
		isIdeasSession = false;
	}


	/**
	 * Event���N�����܂��B
	 * @param event_in �C�x���g��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param attributes_in �C�x���g�A�g���r���[�g�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void generateNxEvent( String event_in, String class_in, 
									String manager_in, NxAttributeWrapper[] attributes_in) throws NxException{

		makeIdeasSession();

		// Event�ɓn����
		NetExpert_v1.NxAttribute[] inattr = null;
		if(attributes_in == null){
			inattr = new NetExpert_v1.NxAttribute[0];
		}else{
			inattr = new NetExpert_v1.NxAttribute[attributes_in.length];
			for(int i = 0; i < attributes_in.length; i++){
				inattr[i] = new NetExpert_v1.NxAttribute();
				inattr[i] = attributes_in[i].attr;
			}
		}

		// generate Event
		try{
			ideasSession.generateEvent(event_in, class_in, manager_in, inattr);
		}catch(NxIdeas_v1.InvalidEvent ie){
			throw new NxException(ie);
		}catch(NxIdeas_v1.InvalidClass ic){
			throw new NxException(ic);
		}catch(NxIdeas_v1.InvalidManager im){
			throw new NxException(im);
		}catch(NxIdeas_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxIdeas_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxIdeas_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return;
	}

	/**
	 * Event���N�����܂��B
	 * @param event_in �C�x���g��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param attributes_in �C�x���g�A�g���r���[�g�̔z��
	 * @return Event����̃A�g���r���[�g�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected NxAttributeWrapper[] generateNxEventConfirmed(String event_in, String class_in, 
															String manager_in, NxAttributeWrapper[] attributes_in) 
		throws NxException{

		makeIdeasSession();

		// Event�ɓn����
		NetExpert_v1.NxAttribute[] inattr = null;
		if(attributes_in == null){
			inattr = new NetExpert_v1.NxAttribute[0];
		}else{
			inattr = new NetExpert_v1.NxAttribute[attributes_in.length];
			for(int i = 0; i < attributes_in.length; i++){
				inattr[i] = new NetExpert_v1.NxAttribute();
				inattr[i] = attributes_in[i].attr;
			}
		}

		// Event�̖߂�A�g���r���[�g
		NetExpert_v1.AttributesHolder resattr = new NetExpert_v1.AttributesHolder();


		// generate Event
		try{
			ideasSession.generateEventConfirmed(event_in, class_in, manager_in, inattr, resattr);
		}catch(NxIdeas_v1.InvalidEvent ie){
			throw new NxException(ie);
		}catch(NxIdeas_v1.InvalidClass ic){
			throw new NxException(ic);
		}catch(NxIdeas_v1.InvalidManager im){
			throw new NxException(im);
		}catch(NxIdeas_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxIdeas_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxIdeas_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		NxAttributeWrapper[] nxOutAttr = new NxAttributeWrapper[resattr.value.length];

		// attributes_out�ɖ߂�
		for(int i = 0; i < resattr.value.length; i++){
			nxOutAttr[i] = new NxAttributeWrapper();
			nxOutAttr[i].attr = resattr.value[i];
		}

		return nxOutAttr;
	}

	// �I���W�i�� ���\�b�h

	/**
	 * Event���N�����܂��B
	 * @param event_in �C�x���g��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param attributes_in �C�x���g�A�g���r���[�g�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void generateEvent(String event_in, String class_in, 
								String manager_in, NetExpert_v1.NxAttribute[] attributes_in) throws NxException{

		makeIdeasSession();

		// generate Event
		try{
			ideasSession.generateEvent(event_in, class_in, manager_in, attributes_in);
		}catch(NxIdeas_v1.InvalidEvent ie){
			throw new NxException(ie);
		}catch(NxIdeas_v1.InvalidClass ic){
			throw new NxException(ic);
		}catch(NxIdeas_v1.InvalidManager im){
			throw new NxException(im);
		}catch(NxIdeas_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxIdeas_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxIdeas_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return;
	}

	/**
	 * Event���N�����܂��B
	 * @param event_in �C�x���g��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param attributes_in �C�x���g�A�g���r���[�g�̔z��
	 * @param �C�x���g���s��̃C�x���g�A�g���r���[�g�̔z�������AttributesHolder
	 * @return Event����̃A�g���r���[�g�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void generateEventConfirmed(String event_in, String class_in, String manager_in, 
											NetExpert_v1.NxAttribute[] attributes_in, 
											NetExpert_v1.AttributesHolder attributes_out) throws NxException{

		makeIdeasSession();

		// generate Event
		try{
			ideasSession.generateEventConfirmed(event_in, class_in, manager_in, attributes_in, attributes_out);
		}catch(NxIdeas_v1.InvalidEvent ie){
			throw new NxException(ie);
		}catch(NxIdeas_v1.InvalidClass ic){
			throw new NxException(ic);
		}catch(NxIdeas_v1.InvalidManager im){
			throw new NxException(im);
		}catch(NxIdeas_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxIdeas_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxIdeas_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return;
	}


}
