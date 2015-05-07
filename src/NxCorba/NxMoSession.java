package NxCorba;


/**
 * MoSession�̊m���y�сAMoSession�̃��\�b�h���`�����N���X�ł��B
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

	/** MoSession��� */
	private boolean isMoSession = false;

	/** �l���s���Ȓ萔 */
	private final static String UNKNOWN = "UNKNOWN";

	/**
	 * NxMoSession���\�z���܂��B
	 *
	 * @praram authenticator NxAuthenticator�̃C���X�^���X
	 */
	protected NxMoSession(NxAuthenticator authenticator){
		this.authenticator = authenticator;
	}

	/**
	 * MoSession�𐶐�����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void makeMoSession() throws NxException{

		if(!authenticator.isConnect()){
			authenticator.connect();
		}

		if(isMoSession()){
			return;
		}

		// ManagedObjectSessionManager�̐�������
		objRef = authenticator.orb.string_to_object(authenticator.caConn.getIorData().get(IORConnect.MO_SESSION).toString());

		// ManagedObjectSessionManager�̐���
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
	 * MoSession�̃Z�b�V������Ԃ�Ԃ��B
	 * @return ������ԂȂ�true�A��������ԂȂ�false
	 */
	protected boolean isMoSession(){
		return isMoSession;
	}

	/**
	 * MoSession��ؒf����B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * MO�𐶐����܂��B
	 * @param object_in MO��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param superior_in �eMO��
	 * @param attributes_in �����l
	 * @return ��������MO��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * MO���폜���܂��B
	 * @param target_in �폜�Ώۂ�\��NxTarget
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * MO�̔z����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NxTarget
	 * @param withAttributes_in �������擾����Ȃ�true�AMO�݂̂Ȃ�false
	 * @return �擾����MO�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
//			throw new NxException(new NxObjects_v1.InvalidTarget());	//���}���u
		}

		nm = new NxObjectWrapper[mo.length];
		for(int i = 0; i < nm.length; i++){
			nm[i] = new NxObjectWrapper(mo[i]);
			//nm[i].setMoObject(mo[i], withAttributes_in);
		}
		return nm;
	}

	/**
	 * MO�����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NxTarget
	 * @return �擾����MO���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Attribute�̒l���擾���܂��B<BR>
	 * �擾����l��NetExpert��Attribute�̌^�ɂ���ĈႤ�̂ŁA�ȉ����Q�l�ɃL���X�g�����ĉ������B<BR>
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
	 * @param object_in �擾�Ώۂ�\��MO��
	 * @param attribute_in �擾�Ώۂ�\��Attribute��
	 * @return �擾�����l
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Attribute�̔z����擾���܂��B
	 * @param object_in �擾�Ώۂ�\��MO��
	 * @param attributes_in �擾�Ώۂ�\��Attribute��
	 * @return �擾����Attribute�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Attribute��ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NxTarget
	 * @param attributes_in �ݒ肷�鑮��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Attribute�̔z���ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NxTarget
	 * @param attributes_in �ݒ肷�鑮���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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

	// �I���W�i�� ���\�b�h

	/**
	 * MO�𐶐����܂��B
	 * @param object_in MO��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param superior_in �eMO��
	 * @param attributes_in �����l
	 * @return ��������MO��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * MO���폜���܂��B
	 * @param target_in �폜�Ώۂ�\��NetExpert_v1.Target
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * MO�̔z����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NetExpert_v1.Target
	 * @param withAttributes_in �������擾����Ȃ�true�AMO�݂̂Ȃ�false
	 * @return �擾����MO�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
			throw new NxException(new NxObjects_v1.InvalidTarget());	//���}���u
		}

		return mo;
	}

	/**
	 * MO�����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NetExpert_v1.Target
	 * @return �擾����MO���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Attribute�̒l���擾���܂��B<BR>
	 * @param object_in �擾�Ώۂ�\��MO��
	 * @param attribute_in �擾�Ώۂ�\��Attribute��
	 * @return �擾�����l
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * getAttributes�����s����B
	 * @param object_in �擾�Ώ�MO��
	 * @param attributes_in �擾�Ώۑ�����
	 * @return �擾���������̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Attribute��ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NetExpert_v1.Target
	 * @param attributes_in �ݒ肷�鑮��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
	 * Attribute�̔z���ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NetExpert_v1.Target
	 * @param attributes_in �ݒ肷�鑮���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
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
