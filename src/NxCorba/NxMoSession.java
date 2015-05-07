package NxCorba;


/**
 * MoSessionの確立及び、MoSessionのメソッドを定義したクラスです。
 *
 * @version $Revision: 1.1 $ $Date: 2002/03/29 13:48:40 $
 * @author $Author: inagashima $
 */
class NxMoSession{

	/** org.omg.CORBA.Object */
	protected org.omg.CORBA.Object objRef;

	/** NxAuthenticator */
	protected NxAuthenticator authenticator;

	/** MO SessionManager */
	protected NxObjects_v1.SessionManager moSessionManager;
	/** MO Session */
	protected NxObjects_v1.Session moSession;

	/** MoSession状態 */
	private boolean isMoSession = false;

	/** 値が不明な定数 */
	private final static String UNKNOWN = "UNKNOWN";

	/**
	 * NxMoSessionを構築します。
	 *
	 * @praram authenticator NxAuthenticatorのインスタンス
	 */
	protected NxMoSession(NxAuthenticator authenticator){
		this.authenticator = authenticator;
	}

	/**
	 * MoSessionを生成する。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void makeMoSession() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isMoSession()){
			return;
		}

		// ManagedObjectSessionManagerの生成準備
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.MO_SESSION).toString());

		// ManagedObjectSessionManagerの生成
		moSessionManager = NxObjects_v1.SessionManagerHelper.narrow(objRef);

		try{
			moSession = moSessionManager.open(authenticator.credentials, 
							NxAuthenticator.PRIO_TYPE[authenticator.moSessionPriority], 
							authenticator.moSessionTimeout);
		}catch(NxObjects_v1.InvalidCredentials ic){
			throw new NxException(ic);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}catch(NxObjects_v1.SessionLimit sl){
			throw new NxException(sl);
		}

		isMoSession = true;
	}


	/**
	 * MoSessionのセッション状態を返す。
	 * @return 生成状態ならtrue、未生成状態ならfalse
	 */
	protected boolean isMoSession(){
		return isMoSession;
	}

	/**
	 * MoSessionを切断する。
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void moSessionDisconnect() throws NxException{

		if(moSessionManager != null) {
			try{
				moSessionManager.close(moSession, true);
			}catch(NxObjects_v1.InvalidStatus is){
				throw new NxException(is);
			}catch(NxObjects_v1.ProcessingFailure pf){
				throw new NxException(pf);
			}
		}
		isMoSession = false;
	}


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
	protected String createNxObject(String object_in, String class_in, String manager_in, 
									String superior_in, NxAttributeWrapper[] attributes_in) throws NxException{

		makeMoSession();

		NetExpert_v1.NxAttribute[] attr = new NetExpert_v1.NxAttribute[attributes_in.length];
		for(int i = 0; i < attr.length; i++ ){
			attr[i] = attributes_in[i].attr;
		}

		String ret = null;

		try{
			ret = moSession.createObject(object_in, class_in, manager_in, superior_in, attr);
		}catch(NxObjects_v1.DuplicateName dup){
			throw new NxException(dup);
		}catch(NxObjects_v1.InvalidObject io){
			throw new NxException(io);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidClass ic){
			throw new NxException(ic);
		}catch(NxObjects_v1.InvalidManager im){
			throw new NxException(im);
		}catch(NxObjects_v1.InvalidName in){
			throw new NxException(in);
		}catch(NxObjects_v1.InvalidSuperior is){
			throw new NxException(is);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return ret;
	}

	/**
	 * MOを削除します。
	 * @param target_in 削除対象を表すNxTarget
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void deleteNxObjects(NxTarget target_in) throws NxException{

		makeMoSession();

		try{
			moSession.deleteObjects(target_in.getTarget());
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * MOの配列を取得します。
	 * @param target_in 取得対象を表すNxTarget
	 * @param withAttributes_in 属性も取得するならtrue、MOのみならfalse
	 * @return 取得したMOの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NxObjectWrapper[] getNxObjects(NxTarget target_in, boolean withAttributes_in) throws NxException{

		makeMoSession();

		NxObjects_v1.NxObject[] mo;
		NxObjectWrapper[] nm;

		try{
			mo = moSession.getObjects(target_in.getTarget(), withAttributes_in);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		if (mo.length == 0) {
			nm = new NxObjectWrapper[0];
			return nm;
//			throw new NxException(new NxObjects_v1.InvalidTarget());	//応急処置
		}

		nm = new NxObjectWrapper[mo.length];
		for(int i = 0; i < nm.length; i++){
			nm[i] = new NxObjectWrapper(mo[i]);
			//nm[i].setMoObject(mo[i], withAttributes_in);
		}
		return nm;
	}

	/**
	 * MO名を取得します。
	 * @param target_in 取得対象を表すNxTarget
	 * @return 取得したMO名の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] getNxObjectIds(NxTarget target_in) throws NxException{

		makeMoSession();

		String[] objectIds;

		try{
			objectIds = moSession.getObjectIds(target_in.getTarget());
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return objectIds;
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
	protected Object getNxAttribute(String object_in, String attribute_in) throws NxException{

		makeMoSession();

		NetExpert_v1.AttributeValue value;

		try{
			value = moSession.getAttribute(object_in, attribute_in);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidObject io){
			throw new NxException(io);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		switch(value.discriminator().value()){
			case NetExpert_v1.AttributeType._BoolType:
				return new Boolean(value.boolValue());
			case NetExpert_v1.AttributeType._LongType:
				return new Long(value.longValue());
			case NetExpert_v1.AttributeType._DoubleType:
				return new Double(value.doubleValue());
			case NetExpert_v1.AttributeType._EnumType:
				return EUCConv.decode((String)value.enumValue());
			case NetExpert_v1.AttributeType._StringType:
				return EUCConv.decode(value.stringValue());
			case NetExpert_v1.AttributeType._MOType:
				String object = (String)EUCConv.decode(value.moValue());
				return object.equals(UNKNOWN) ? "" : object;
			case NetExpert_v1.AttributeType._LongsType:
				NetExpert_v1.LongSetOfElement[] le = value.longsValue();
				long[] longs = new long[le.length];
				for (int i = 0; i < le.length; i++) {
					longs[i] = le[i].value;
				}
				return longs;
			case NetExpert_v1.AttributeType._DoublesType:
				NetExpert_v1.DoubleSetOfElement[] de = value.doublesValue();
				double[] doubles = new double[de.length];
				for (int i = 0; i < de.length; i++) {
					doubles[i] = de[i].value;
				}
				return doubles;
			case NetExpert_v1.AttributeType._EnumsType:
				NetExpert_v1.StringSetOfElement[] ee = value.enumsValue();
				String[] enums = new String[ee.length];
				for (int i = 0; i < ee.length; i++) {
					enums[i] = EUCConv.decode(ee[i].value);
				}
				return enums;
			case NetExpert_v1.AttributeType._StringsType:
				NetExpert_v1.StringSetOfElement[] se = value.stringsValue();
				String[] strings = new String[se.length];
				for (int i = 0; i < se.length; i++) {
					strings[i] = EUCConv.decode(se[i].value);
				}
				return strings;
			case NetExpert_v1.AttributeType._MOsType:
				NetExpert_v1.StringSetOfElement[] me = value.mosValue();
				String[] mos = new String[me.length];
				for (int i = 0; i < me.length; i++) {
					mos[i] = EUCConv.decode(me[i].value);
					if (mos[i].equals(UNKNOWN)) {
						mos[i] = "";
					}
				}
				return mos;
			default:
				return null;
		}
	}

	/**
	 * Attributeの配列を取得します。
	 * @param object_in 取得対象を表すMO名
	 * @param attributes_in 取得対象を表すAttribute名
	 * @return 取得したAttributeの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NxAttributeWrapper[] getNxAttributes(String object_in, String[] attributes_in) throws NxException{

		makeMoSession();

		NetExpert_v1.NxAttribute[] attributes;

		try{
			attributes = moSession.getAttributes(object_in, attributes_in);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidObject io){
			throw new NxException(io);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		NxAttributeWrapper[] na = new NxAttributeWrapper[attributes.length];
		for(int i = 0; i < attributes.length; i++){
			na[i] = new NxAttributeWrapper(attributes[i]);
		}
		return na;
	}

	/**
	 * Attributeを設定します。
	 * @param target_in 設定対象を表すNxTarget
	 * @param attributes_in 設定する属性
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void setNxAttribute(NxTarget target_in, NxAttributeWrapper attribute_in) throws NxException{

		makeMoSession();

		try{
			moSession.setAttribute(target_in.getTarget(), attribute_in.attr);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * Attributeの配列を設定します。
	 * @param target_in 設定対象を表すNxTarget
	 * @param attributes_in 設定する属性の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void setNxAttributes(NxTarget target_in, NxAttributeWrapper[] attributes_in) throws NxException{

		makeMoSession();

		NetExpert_v1.NxAttribute[] na = new NetExpert_v1.NxAttribute[attributes_in.length];
		for(int i = 0; i < attributes_in.length; i++){
			na[i] = attributes_in[i].attr;
		}

		try{
			moSession.setAttributes(target_in.getTarget(), na);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	// オリジナル メソッド

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
	protected String createObject(String object_in, String class_in, String manager_in, 
									String superior_in, NetExpert_v1.NxAttribute[] attributes_in) throws NxException{

		makeMoSession();

		String ret;

		try{
			ret = moSession.createObject(object_in, class_in, manager_in, superior_in, attributes_in);
		}catch(NxObjects_v1.DuplicateName dup){
			throw new NxException(dup);
		}catch(NxObjects_v1.InvalidObject io){
			throw new NxException(io);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidClass ic){
			throw new NxException(ic);
		}catch(NxObjects_v1.InvalidManager im){
			throw new NxException(im);
		}catch(NxObjects_v1.InvalidName in){
			throw new NxException(in);
		}catch(NxObjects_v1.InvalidSuperior is){
			throw new NxException(is);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return ret;
	}

	/**
	 * MOを削除します。
	 * @param target_in 削除対象を表すNetExpert_v1.Target
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void deleteObjects(NetExpert_v1.Target target_in) throws NxException{

		makeMoSession();

		try{
			moSession.deleteObjects(target_in);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * MOの配列を取得します。
	 * @param target_in 取得対象を表すNetExpert_v1.Target
	 * @param withAttributes_in 属性も取得するならtrue、MOのみならfalse
	 * @return 取得したMOの配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NxObjects_v1.NxObject[] getObjects(NetExpert_v1.Target target_in, boolean withAttributes_in) 
			throws NxException{

		makeMoSession();

		NxObjects_v1.NxObject[] mo;

		try{
			mo = moSession.getObjects(target_in, withAttributes_in);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		if (mo.length == 0) {
			throw new NxException(new NxObjects_v1.InvalidTarget());	//応急処置
		}

		return mo;
	}

	/**
	 * MO名を取得します。
	 * @param target_in 取得対象を表すNetExpert_v1.Target
	 * @return 取得したMO名の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected String[] getObjectIds(NetExpert_v1.Target target_in) throws NxException{

		makeMoSession();

		String[] objectIds;

		try{
			objectIds = moSession.getObjectIds(target_in);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return objectIds;
	}

	/**
	 * Attributeの値を取得します。<BR>
	 * @param object_in 取得対象を表すMO名
	 * @param attribute_in 取得対象を表すAttribute名
	 * @return 取得した値
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NetExpert_v1.AttributeValue getAttribute(String object_in, String attribute_in) throws NxException{

		makeMoSession();

		NetExpert_v1.AttributeValue value;

		try{
			value = moSession.getAttribute(object_in, attribute_in);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidObject io){
			throw new NxException(io);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return value;

	}

	/**
	 * getAttributesを実行する。
	 * @param object_in 取得対象MO名
	 * @param attributes_in 取得対象属性名
	 * @return 取得した属性の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected NetExpert_v1.NxAttribute[] getAttributes(String object_in, String[] attributes_in)
			throws NxException{

		makeMoSession();

		NetExpert_v1.NxAttribute[] attributes;
		try{
			attributes = moSession.getAttributes(object_in, attributes_in);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidObject io){
			throw new NxException(io);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}

		return attributes;
	}

	/**
	 * Attributeを設定します。
	 * @param target_in 設定対象を表すNetExpert_v1.Target
	 * @param attributes_in 設定する属性
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void setAttribute(NetExpert_v1.Target target_in, NetExpert_v1.NxAttribute attribute_in) 
			throws NxException{

		makeMoSession();

		try{
			moSession.setAttribute(target_in, attribute_in);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}

	/**
	 * Attributeの配列を設定します。
	 * @param target_in 設定対象を表すNetExpert_v1.Target
	 * @param attributes_in 設定する属性の配列
	 * @throws NxException ORB、CA関連での例外発生時
	 */
	protected void setAttributes(NetExpert_v1.Target target_in, NetExpert_v1.NxAttribute[] attributes_in) 
			throws NxException{

		makeMoSession();

		try{
			moSession.setAttributes(target_in, attributes_in);
		}catch(NxObjects_v1.InvalidAttribute ia){
			throw new NxException(ia);
		}catch(NxObjects_v1.InvalidAttributeValue iav){
			throw new NxException(iav);
		}catch(NxObjects_v1.InvalidTarget it){
			throw new NxException(it);
		}catch(NxObjects_v1.ProcessingFailure pf){
			throw new NxException(pf);
		}
	}


}
