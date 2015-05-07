package NxCorba;


/**
 * IdeasSessionの確立及び、IdeasSessionのメソッドを定義したクラスです。
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

	/** IdeasSession状態 */
	private boolean isIdeasSession = false;


	/**
	 * NxIdeasSessionを構築します。
	 *
	 * @praram authenticator NxAuthenticatorのインスタンス
	 */
	protected NxIdeasSession(NxAuthenticator authenticator){
		this.authenticator = authenticator;
	}

	/**
	 * IdeasSessionを生成する。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void makeIdeasSession() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isIdeasSession()){
			return;
		}

		// IDEASSessionManagerの生成準備
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.IDEAS_SESSION).toString());

		// IDEASSessionManagerの生成
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
	 * IdeasSessionのセッション状態を返す。
	 * @return 生成状態ならtrue、未生成状態ならfalse
	 */
	protected boolean isIdeasSession(){
		return isIdeasSession;
	}

	/**
	 * IdeasSessionを切断する。
	 * @throws NxException ORB、CA関連での例外発生時
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
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void generateNxEvent( String event_in, String class_in, 
									String manager_in, NxAttributeWrapper[] attributes_in) throws NxException{

		makeIdeasSession();

		// Eventに渡す側
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
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @return Eventからのアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NxAttributeWrapper[] generateNxEventConfirmed(String event_in, String class_in, 
															String manager_in, NxAttributeWrapper[] attributes_in) 
		throws NxException{

		makeIdeasSession();

		// Eventに渡す側
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

		// Eventの戻りアトリビュート
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

		// attributes_outに戻す
		for(int i = 0; i < resattr.value.length; i++){
			nxOutAttr[i] = new NxAttributeWrapper();
			nxOutAttr[i].attr = resattr.value[i];
		}

		return nxOutAttr;
	}

	// オリジナル メソッド

	/**
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
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
	 * Eventを起動します。
	 * @param event_in イベント名
	 * @param class_in class名
	 * @param manager_in ManagerのMO名
	 * @param attributes_in イベントアトリビュートの配列
	 * @param イベント実行後のイベントアトリビュートの配列を持つAttributesHolder
	 * @return Eventからのアトリビュートの配列
	 * @throws NxException ORB、CA関連での例外発生時
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
