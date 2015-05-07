package NxCorba;

import java.io.*;
import java.util.*;

/**
 * MO�AAttribute�̑���AEvent�̋N���Ȃǂ̃��\�b�h���`���܂��B<BR>
 * ���̃N���X���C���X�^���X�������NxCASession�N���X���p�����Ă���̂Ŏ����I��CA�T�[�o�ɐڑ����܂��B<BR>
 * ��`����Ă��郁�\�b�h���g����CA�̑�������ĉ������B<BR>
 * <P>
 *
 * �R���X�g���N�^�ɕK�v��IOR�t�@�C���f�B���N�g���A�V�X�e���v���p�e�B�A�y�ѐڑ����t�@�C���̐ݒ���@�͈ȉ��̒ʂ�ł��B<BR>
 * <P>
 * <P>
 *
 * IOR�t�@�C���f�B���N�g�� :<BR>
 * /export/home/netx/IOR
 * <P>
 * �V�X�e���v���p�e�B �ڑ����t�@�C�� :<BR>
 * NXSYSTEM : NetExpert�V�X�e����  �� IOR�T�[�o�o�R�ł͂Ȃ���<BR>
 * IOR_SERVER_HOST : IOR�T�[�o�z�X�gIP�A�������̓z�X�g��  �� IOR�T�[�o�o�R�̎�<BR>
 * IOR_SERVER_PORT : IOR�T�[�o�|�[�g(���l)  �� IOR�T�[�o�o�R�̎�<BR>
 * IOR_FILE_DIR : IOR�t�@�C���f�B���N�g�� �� IOR�t�@�C���f�B���N�g�����ݒ肳��Ă��Ȃ���<BR>
 * CONNECT_RETRY : connect���̃��g���C��(���l)<BR>
 * ALERTSESSION_PRIO : AlertSession Priority(���l)<BR>
 * ALERTSESSION_TO : AlertSession �^�C���A�E�g(���l)<BR>
 * MOSESSION_PRIO : MOSession Priority(���l)<BR>
 * MOSESSION_TO : MOSesson �^�C���A�E�g(���l)<BR>
 * IDEASSESSION_PRIO : IdeasSession Priority(���l)<BR>
 * IDEASSESSION_TO : IdeasSession �^�C���A�E�g(���l)<BR>
 * <P>
 *
 * �ݒ�� :<BR>
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
 * ���O�C���v���p�e�B�t�@�C�����ݒ�ł��܂��B<BR>
 * LOGIN_ID : NetExpert���[�U���O�C��ID<BR>
 * LOGIN_PASSWD : NetExpert���[�U�p�X���[�h<BR>
 * �V�X�e���v���p�e�B���ݒ��Ɠ����ł��B
 *
 * @version $Revision: 1.9 $ $Date: 2002/05/22 04:01:37 $
 * @author $Author: tookubo $
 */
public class NxCAAccessor extends NxCASession{

	/**
	 * CA�֐ڑ��������IOR�T�[�o�A�V�X�e���v���p�e�B����擾����NxCAAccessor���\�z���܂��B
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	public NxCAAccessor() throws NxException{
		super();
	}

	/**
	 * CA�֐ڑ��������IOR�T�[�o�A�ڑ��ݒ�t�@�C������擾����NxCAAccessor���\�z���܂��B
	 * @param sessionFile �ڑ��ݒ�t�@�C��
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	public NxCAAccessor(File sessionFile) throws NxException{
		super(sessionFile);
	}

	/**
	 * CA�֐ڑ��������IOR�T�[�o�AProperties����擾����NxCAAccessor���\�z���܂��B
	 * @param prop Properties
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	public NxCAAccessor(Properties prop) throws NxException{
		super(prop);
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���A�V�X�e���v���p�e�B����擾����NxCAAccessor���\�z���܂��B
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	public NxCAAccessor(String iorFileDir) throws NxException{
		super(iorFileDir);
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���A�ڑ��ݒ�t�@�C������擾����NxCAAccessor���\�z���܂��B
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @param sessionFile �ڑ��ݒ�t�@�C��
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	public NxCAAccessor(String iorFileDir, File sessionFile) throws NxException{
		super(iorFileDir, sessionFile);
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���AProperties����擾����NxCAAccessor���\�z���܂��B
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @param prop Properties
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	public NxCAAccessor(String iorFileDir, Properties prop) throws NxException{
		super(iorFileDir, prop);
	}


	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param loginId		NetExpert���O�C��ID
	 * @param loginPasswd	NetExpert���O�C���p�X���[�h
	 * @param doEncrypt		true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect(String loginId, String loginPasswd, boolean doEncrypt) throws NxException{
		super.connect(loginId, loginPasswd, doEncrypt);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param doEncrypt			true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect(boolean doEncrypt) throws NxException{
		super.connect(doEncrypt);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param sessionFile		�ڑ��ݒ�t�@�C��
	 * @param doEncrypt			true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect(File sessionFile, boolean doEncrypt) throws NxException{
		super.connect(sessionFile, doEncrypt);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param prop				Properties
	 * @param doEncrypt			true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect(Properties prop, boolean doEncrypt) throws NxException{
		super.connect(prop, doEncrypt);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 *
	 * @param loginId		NetExpert���O�C��ID
	 * @param loginPasswd	NetExpert���O�C���p�X���[�h
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect(String loginId, String loginPasswd) throws NxException{
		super.connect(loginId, loginPasswd);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect() throws NxException{
		super.connect();
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 *
	 * @param sessionFile		�ڑ��ݒ�t�@�C��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect(File sessionFile) throws NxException{
		super.connect(sessionFile);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 *
	 * @param prop				Properties
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void connect(Properties prop) throws NxException{
		super.connect(prop);
	}


	/**
	 * CA�Ƃ̐ڑ���ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void disconnect() throws NxException{
		alertSessionDisconnect();
		moSessionDisconnect();
		ideasSessionDisconnect();
		authenticationDisconnect();
	}

	/**
	 * �F�ؐڑ���ؒf����B���ۂɂ͊eSesion���m�����Ă���΁A�ؒf���邱�Ƃ͂ł��Ȃ��B
	 *
	 */
	public void authenticationDisconnect(){
		super.authenticationDisconnect();
//		authenticator.authenticationDisconnect();
	}

	/**
	 * AlertSession��ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void alertSessionDisconnect() throws NxException{
		super.alertSessionDisconnect();
//		alertSession.alertSessionDisconnect();
	}

	/**
	 * MoSession��ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void moSessionDisconnect() throws NxException{
		super.moSessionDisconnect();
//		moSession.moSessionDisconnect();
	}

	/**
	 * IdeasSession��ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void ideasSessionDisconnect() throws NxException{
		super.ideasSessionDisconnect();
//		ideasSession.ideasSessionDisconnect();
	}

	/* -------------------------------------------------------------------------
	 AlertSession�n
	------------------------------------------------------------------------- */
	/**
	 * Alert�̒�`���擾���܂��B
	 *
	 * @param alertName_in Alert��
	 * @return Alert��`
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public AlertDefinitionWrapper getNxAlertDefinition(String alertName_in) throws NxException{

		return alertSession.getNxAlertDefinition(alertName_in);

	}

	/**
	 * Alert���擾���܂��B
	 *
	 * @param filter_in filter
	 * @return Alert�z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public AlertWrapper[] getNxOpenAlerts(String filter_in) throws NxException{

		return alertSession.getNxOpenAlerts(filter_in);

	}

	/**
	 * Alert��F�m����B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public String[] acknowledgeNxAlerts(String[] alerts_in) throws NxException{

		return alertSession.acknowledgeNxAlerts(alerts_in);

	}

	/**
	 * Alert���F�m����B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public String[] unacknowledgeNxAlerts(String[] alerts_in) throws NxException{

		return alertSession.unacknowledgeNxAlerts(alerts_in);

	}

	/**
	 * Alert����������B
	 *
	 * @param alerts_in Alert
	 * @return alertIDs
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public String[] clearNxAlerts(String[] alerts_in) throws NxException{

		return alertSession.clearNxAlerts(alerts_in);

	}

	/* -------------------------------------------------------------------------
	 MoSession�n
	------------------------------------------------------------------------- */
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
	public String createNxObject(String object_in, String class_in, String manager_in, 
								String superior_in, NxAttributeWrapper[] attributes_in) throws NxException{

		return moSession.createNxObject(object_in, class_in, manager_in, superior_in, attributes_in);

	}

	/**
	 * MO���폜���܂��B
	 * @param target_in �폜�Ώۂ�\��NxTarget
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void deleteNxObjects(NxTarget target_in) throws NxException{

		moSession.deleteNxObjects(target_in);

	}

	/**
	 * MO�̔z����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NxTarget
	 * @param withAttributes_in �������擾����Ȃ�true�AMO�݂̂Ȃ�false
	 * @return �擾����MO�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public NxObjectWrapper[] getNxObjects(NxTarget target_in, boolean withAttributes_in) throws NxException{

		return moSession.getNxObjects(target_in, withAttributes_in);

	}

	/**
	 * MO�����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NxTarget
	 * @return �擾����MO���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public String[] getNxObjectIds(NxTarget target_in) throws NxException{

		return moSession.getNxObjectIds(target_in);

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
	public Object getNxAttribute(String object_in, String attribute_in) throws NxException{

		return moSession.getNxAttribute(object_in, attribute_in);

	}


	/**
	 * Attribute�̔z����擾���܂��B
	 * @param object_in �擾�Ώۂ�\��MO��
	 * @param attributes_in �擾�Ώۂ�\��Attribute��
	 * @return �擾����Attribute�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public NxAttributeWrapper[] getNxAttributes(String object_in, String[] attributes_in) throws NxException{

		return moSession.getNxAttributes(object_in, attributes_in);

	}

	/**
	 * Attribute��ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NxTarget
	 * @param attributes_in �ݒ肷�鑮��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void setNxAttribute(NxTarget target_in, NxAttributeWrapper attribute_in) throws NxException{

		moSession.setNxAttribute(target_in, attribute_in);

	}

	/**
	 * Attribute�̔z���ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NxTarget
	 * @param attributes_in �ݒ肷�鑮���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void setNxAttributes(NxTarget target_in, NxAttributeWrapper[] attributes_in) throws NxException{

		moSession.setNxAttributes(target_in, attributes_in);

	}


	/* -------------------------------------------------------------------------
	 IdeasSession�n
	------------------------------------------------------------------------- */

	/**
	 * Event���N�����܂��B
	 * @param event_in �C�x���g��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param attributes_in �C�x���g�A�g���r���[�g�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void generateNxEvent(String event_in, String class_in, String manager_in, 
								NxAttributeWrapper[] attributes_in) throws NxException{

		ideasSession.generateNxEvent(event_in, class_in, manager_in, attributes_in);

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
	public NxAttributeWrapper[] generateNxEventConfirmed(String event_in, String class_in, 
														String manager_in, NxAttributeWrapper[] attributes_in)
			throws NxException{

		return ideasSession.generateNxEventConfirmed(event_in, class_in, manager_in, attributes_in);

	}


	// �I���W�i�� ���\�b�h

	/* -------------------------------------------------------------------------
	 AlertSession�n
	------------------------------------------------------------------------- */
	/* -------------------------------------------------------------------------
	 MoSession�n
	------------------------------------------------------------------------- */
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
	public String createObject( String object_in, String class_in, String manager_in, 
								String superior_in, NetExpert_v1.NxAttribute[] attributes_in) throws NxException{

		return moSession.createObject(object_in, class_in, manager_in, superior_in, attributes_in);

	}

	/**
	 * MO���폜���܂��B
	 * @param target_in �폜�Ώۂ�\��NetExpert_v1.Target
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void deleteObjects(NetExpert_v1.Target target_in) throws NxException{

		moSession.deleteObjects(target_in);

	}

	/**
	 * MO�̔z����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NetExpert_v1.Target
	 * @param withAttributes_in �������擾����Ȃ�true�AMO�݂̂Ȃ�false
	 * @return �擾����MO�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public NxObjects_v1.NxObject[] getObjects(NetExpert_v1.Target target_in, 
												boolean withAttributes_in) throws NxException{

		return moSession.getObjects(target_in, withAttributes_in);

	}

	/**
	 * MO�����擾���܂��B
	 * @param target_in �擾�Ώۂ�\��NetExpert_v1.Target
	 * @return �擾����MO���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public String[] getObjectIds(NetExpert_v1.Target target_in) throws NxException{

		return moSession.getObjectIds(target_in);

	}

	/**
	 * Attribute�̒l���擾���܂��B<BR>
	 * @param object_in �擾�Ώۂ�\��MO��
	 * @param attribute_in �擾�Ώۂ�\��Attribute��
	 * @return �擾�����l
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public NetExpert_v1.AttributeValue getAttribute(String object_in, String attribute_in) throws NxException{

		return moSession.getAttribute(object_in, attribute_in);

	}

	/**
	 * getAttributes�����s����B
	 * @param object_in �擾�Ώ�MO��
	 * @param attributes_in �擾�Ώۑ�����
	 * @return �擾���������̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public NetExpert_v1.NxAttribute[] getAttributes(String object_in, String[] attributes_in) throws NxException{

		return moSession.getAttributes(object_in, attributes_in);

	}

	/**
	 * Attribute��ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NetExpert_v1.Target
	 * @param attributes_in �ݒ肷�鑮��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void setAttribute(NetExpert_v1.Target target_in, NetExpert_v1.NxAttribute attribute_in) throws NxException{

		moSession.setAttribute(target_in, attribute_in);

	}

	/**
	 * Attribute�̔z���ݒ肵�܂��B
	 * @param target_in �ݒ�Ώۂ�\��NetExpert_v1.Target
	 * @param attributes_in �ݒ肷�鑮���̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void setAttributes(NetExpert_v1.Target target_in, 
								NetExpert_v1.NxAttribute[] attributes_in)throws NxException{

		moSession.setAttributes(target_in, attributes_in);

	}

	/* -------------------------------------------------------------------------
	 IdeasSession�n
	------------------------------------------------------------------------- */
	/**
	 * Event���N�����܂��B
	 * @param event_in �C�x���g��
	 * @param class_in class��
	 * @param manager_in Manager��MO��
	 * @param attributes_in �C�x���g�A�g���r���[�g�̔z��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	public void generateEvent(String event_in, String class_in, String manager_in, 
								NetExpert_v1.NxAttribute[] attributes_in) throws NxException{

		ideasSession.generateEvent(event_in, class_in, manager_in, attributes_in);

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
	public void generateEventConfirmed(String event_in, String class_in, String manager_in, 
										NetExpert_v1.NxAttribute[] attributes_in, 
										NetExpert_v1.AttributesHolder attributes_out) throws NxException{

		ideasSession.generateEventConfirmed(event_in, class_in, manager_in, attributes_in, attributes_out);

	}
}


