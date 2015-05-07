package NxCorba;

import java.io.*;
import java.util.*;

/**
 * ���̃N���X��NetExpert�ڑ��p�̃N���X�ł��BCaserver�Ɛڑ������ASession Object���擾���܂��B<BR>
 *
 * @version $Revision: 1.6 $ $Date: 2002/05/22 04:01:37 $
 * @author $Author: tookubo $
 */
class NxCASession{

	/** NxAuthenticator */
	protected NxAuthenticator authenticator;
	/** NxAlertSession */
	protected NxAlertSession alertSession;
	/** NxMoSession */
	protected NxMoSession moSession;
	/** NxIdeasSession */
	protected NxIdeasSession ideasSession;

	/**
	 * CA�֐ڑ��������IOR�T�[�o�A�V�X�e���v���p�e�B����擾���ANxAuthenticator�̃R���X�g���N�^���Ăяo���܂��B
	 * @throws NxException CA�֘A�ł̗�O������
	 *
	 */
	protected NxCASession() throws NxException{
		authenticator = new NxAuthenticator();
	}


	/**
	 * CA�֐ڑ��������IOR�T�[�o�A�ڑ��ݒ�t�@�C������擾���ANxAuthenticator�̃R���X�g���N�^���Ăяo���܂��B
	 *
	 * @param sessionFile �ڑ��ݒ�t�@�C��
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxCASession(File sessionFile) throws NxException{
		authenticator = new NxAuthenticator(sessionFile);
	}

	/**
	 * CA�֐ڑ��������IOR�T�[�o�AProperties����擾���ANxAuthenticator�̃R���X�g���N�^���Ăяo���܂��B
	 *
	 * @param prop Properties
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxCASession(Properties prop) throws NxException{
		authenticator = new NxAuthenticator(prop);
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���A�V�X�e���v���p�e�B����擾���ANxAuthenticator�̃R���X�g���N�^���Ăяo���܂��B
	 *
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxCASession(String iorFileDir) throws NxException{
		authenticator = new NxAuthenticator(iorFileDir);
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���A�ڑ��ݒ�t�@�C������擾���ANxAuthenticator�̃R���X�g���N�^���Ăяo���܂��B
	 *
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @param sessionFile �ڑ��ݒ�t�@�C��
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxCASession(String iorFileDir, File sessionFile) throws NxException{
		authenticator = new NxAuthenticator(iorFileDir, sessionFile);
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���AProperties����擾���ANxAuthenticator�̃R���X�g���N�^���Ăяo���܂��B
	 *
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @param prop Properties
	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxCASession(String iorFileDir, Properties prop) throws NxException{
		authenticator = new NxAuthenticator(iorFileDir, prop);
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
	protected void connect(String loginId, String loginPasswd, boolean doEncrypt) throws NxException{
		authenticator.connect(loginId, loginPasswd, doEncrypt);
		makeSession(authenticator);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param doEncrypt			true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(boolean doEncrypt) throws NxException{
		authenticator.connect(doEncrypt);
		makeSession(authenticator);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param sessionFile		�ڑ��ݒ�t�@�C��
	 * @param doEncrypt			true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(File sessionFile, boolean doEncrypt) throws NxException{
		authenticator.connect(sessionFile, doEncrypt);
		makeSession(authenticator);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param prop				Properties
	 * @param doEncrypt			true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(Properties prop, boolean doEncrypt) throws NxException{
		authenticator.connect(prop, doEncrypt);
		makeSession(authenticator);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 *
	 * @param loginId		NetExpert���O�C��ID
	 * @param loginPasswd	NetExpert���O�C���p�X���[�h
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(String loginId, String loginPasswd) throws NxException{
		authenticator.connect(loginId, loginPasswd);
		makeSession(authenticator);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect() throws NxException{
		authenticator.connect();
		makeSession(authenticator);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 *
	 * @param sessionFile		�ڑ��ݒ�t�@�C��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(File sessionFile) throws NxException{
		authenticator.connect(sessionFile);
		makeSession(authenticator);
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B
	 *
	 * @param prop				Properties
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(Properties prop) throws NxException{
		authenticator.connect(prop);
		makeSession(authenticator);
	}


	/**
	 * NxAlertSession�ANxMoSession�ANxMoSession�ANxAlertNotification�ANxObjectNotification�𐶐�����B
	 *
	 * @param authenticator NxAuthenticator�̃C���X�^���X
	 */
	protected void makeSession(NxAuthenticator authenticator){
		makeAlertSession(authenticator);
		makeMoSession(authenticator);
		makeIdeasSession(authenticator);
	}
	/**
	 * NxAlertSession�𐶐�����B
	 *
	 * @param authenticator NxAuthenticator�̃C���X�^���X
	 */
	protected void makeAlertSession(NxAuthenticator authenticator){
		alertSession = new NxAlertSession(authenticator);
	}

	/**
	 * NxMoSession�𐶐�����B
	 *
	 * @param authenticator NxAuthenticator�̃C���X�^���X
	 */
	protected void makeMoSession(NxAuthenticator authenticator){
		moSession = new NxMoSession(authenticator);
	}

	/**
	 * NxIdeasSession�𐶐�����B
	 *
	 * @param authenticator NxAuthenticator�̃C���X�^���X
	 */
	protected void makeIdeasSession(NxAuthenticator authenticator){
		ideasSession = new NxIdeasSession(authenticator);
	}

	/**
	 * CA�Ƃ̐ڑ���ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void disconnect() throws NxException{
		alertSessionDisconnect();
		moSessionDisconnect();
		ideasSessionDisconnect();
		authenticationDisconnect();
	}

	/**
	 * �F�ؐڑ���ؒf����B���ۂɂ͊eSesion���m�����Ă���΁A�ؒf���邱�Ƃ͂ł��Ȃ��B
	 *
	 */
	protected void authenticationDisconnect(){
		if(authenticator != null){
			authenticator.authenticationDisconnect();
		}
	}

	/**
	 * AlertSession��ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void alertSessionDisconnect() throws NxException{
		if(alertSession != null){
			alertSession.alertSessionDisconnect();
		}
	}

	/**
	 * MoSession��ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void moSessionDisconnect() throws NxException{
		if(moSession != null){
			moSession.moSessionDisconnect();
		}
	}

	/**
	 * IdeasSession��ؒf����B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void ideasSessionDisconnect() throws NxException{
		if(ideasSession != null){
			ideasSession.ideasSessionDisconnect();
		}
	}

}
