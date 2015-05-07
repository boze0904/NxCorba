package NxCorba;

import java.io.*;
import java.util.*;
import org.omg.CORBA.ORB;

/**
 * ���̃N���X��NetExpert�ڑ��p�̃N���X�ł��BCaserver�Ɛڑ������A�eSession�̎Q�Ə��������܂��B<BR>
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
 * IOR_FILE_DIR=/export/home/netx/IOR
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
 * @version $Revision: 1.4 $ $Date: 2002/05/22 04:01:38 $
 * @author $Author: tookubo $
 */
class NxAuthenticator{

	/** org.omg.CORBA.ORB */
	protected ORB orb = null;
	/** org.omg.CORBA.Object */
	protected org.omg.CORBA.Object objRef;

	/**
	 * NxSession_v1.Priority
	 */
	protected final static NxSession_v1.Priority[] PRIO_TYPE = {
		NxSession_v1.Priority.High,
		NxSession_v1.Priority.Medium,
		NxSession_v1.Priority.Low
	};

	/** Authenticator */
	protected NxSecurity_v1.Authenticator authenticator;

	/** Credentials */
	protected NxSecurity_v1.Credentials credentials;

	/** IORConnect�C���X�^���X */
	protected IORConnect caConn;
	/** IOR�t�@�C���f�B���N�g�� */
	protected String iorFileDir;

	/** NetExpert�V�X�e���� */
	protected String nxSystem;
	/** login_ID */
	protected String loginId;
	/** login_Passwd */
	protected String loginPasswd;
	/** iorServer_Host */
	protected String iorServerHost;
	/** iorServer_Port */
	protected int iorServerPort;
	/** connectRetry */
	protected int connectRetry;
	/** alertSessionPriority */
	protected int alertSessionPriority;
	/** alertSessionTimeout */
	protected int alertSessionTimeout;
	/** moSessionPriority */
	protected int moSessionPriority;
	/** moSessionTimeout */
	protected int moSessionTimeout;
	/** ideasSessionPriority */
	protected int ideasSessionPriority;
	/** ideasSessionTimeout */
	protected int ideasSessionTimeout;

	/** �ڑ����@�t���O */
	private boolean howFlag;

	/** Connect��� */
	private boolean isConnect;


	/**
	 * CA�֐ڑ��������IOR�T�[�o�A�V�X�e���v���p�e�B����擾����NxAuthenticator���\�z���܂��B
	 * @throws NxException CA�֘A�ł̗�O������
	 *
	 */
	protected NxAuthenticator() throws NxException{
		//�ݒ�l�̊m�F
		try{
			Integer.parseInt(System.getProperty(CAConstants.IOR_SERVER_PORT));
			Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("�ݒ�l�Ɍ�肪����܂�");
			e.printStackTrace();
		}

		iorServerHost = System.getProperty(CAConstants.IOR_SERVER_HOST);
		iorServerPort = Integer.parseInt(System.getProperty(CAConstants.IOR_SERVER_PORT));
		connectRetry = Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));

		howFlag = true;
	}


	/**
	 * CA�֐ڑ��������IOR�T�[�o�A�ڑ��ݒ�t�@�C������擾����NxAuthenticator���\�z���܂��B
	 *
	 * @param sessionFile �ڑ��ݒ�t�@�C��
 	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxAuthenticator(File sessionFile) throws NxException{

		//�ݒ�t�@�C���̓ǂݍ���
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("�ڑ��ݒ�t�@�C���̓ǂݍ��݂Ɏ��s���܂���");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		//�ݒ�l�̊m�F
		try{
			Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("�ݒ�l�Ɍ�肪����܂�");
			e.printStackTrace();
		}

		iorServerHost = prop.getProperty(CAConstants.IOR_SERVER_HOST);
		iorServerPort = Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		howFlag = true;
	}

	/**
	 * CA�֐ڑ��������IOR�T�[�o�AProperties����擾����NxAuthenticator���\�z���܂��B
	 *
	 * @param prop Properties
 	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxAuthenticator(Properties prop) throws NxException{

		//�ݒ�l�̊m�F
		try{
			Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("�ݒ�l�Ɍ�肪����܂�");
			e.printStackTrace();
		}

		iorServerHost = prop.getProperty(CAConstants.IOR_SERVER_HOST);
		iorServerPort = Integer.parseInt(prop.getProperty(CAConstants.IOR_SERVER_PORT));
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		howFlag = true;
	}


	/**
	 * CA�֐ڑ��������IOR�t�@�C���A�V�X�e���v���p�e�B����擾����NxAuthenticator���\�z���܂��B
	 *
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
 	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxAuthenticator(String iorFileDir) throws NxException{

		//�ݒ�l�̊m�F
		try{
			Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("�ݒ�l�Ɍ�肪����܂�");
			e.printStackTrace();
		}

		nxSystem = System.getProperty(CAConstants.NXSYSTEM);
		connectRetry = Integer.parseInt(System.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(System.getProperty(CAConstants.IDEASSESSION_TO));

		if(iorFileDir == null || iorFileDir.equals("")){
			this.iorFileDir = System.getProperty(CAConstants.IOR_FILE_DIR);
		}else{
			this.iorFileDir = iorFileDir;
		}
		howFlag = false;
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���A�ڑ��ݒ�t�@�C������擾����NxAuthenticator���\�z���܂��B
	 *
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @param sessionFile �ڑ��ݒ�t�@�C��
 	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxAuthenticator(String iorFileDir, File sessionFile) throws NxException{

		//�ݒ�t�@�C���̓ǂݍ���
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("�ڑ��ݒ�t�@�C���̓ǂݍ��݂Ɏ��s���܂���");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		//�ݒ�l�̊m�F
		try {
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("�ݒ�l�Ɍ�肪����܂�");
			e.printStackTrace();
		}

		nxSystem = prop.getProperty(CAConstants.NXSYSTEM);
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		if(iorFileDir == null || iorFileDir.equals("")){
			this.iorFileDir = prop.getProperty(CAConstants.IOR_FILE_DIR);
		}else{
			this.iorFileDir = iorFileDir;
		}
		howFlag = false;
	}

	/**
	 * CA�֐ڑ��������IOR�t�@�C���AProperties����擾����NxAuthenticator���\�z���܂��B
	 *
	 * @param iorFileDir IOR�t�@�C���f�B���N�g��
	 * @param prop Properties
 	 * @throws NxException CA�֘A�ł̗�O������
	 */
	protected NxAuthenticator(String iorFileDir, Properties prop) throws NxException{

		//�ݒ�l�̊m�F
		try {
			Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
			Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));
		}catch(Exception e){
			System.err.println("�ݒ�l�Ɍ�肪����܂�");
			e.printStackTrace();
		}

		nxSystem = prop.getProperty(CAConstants.NXSYSTEM);
		connectRetry = Integer.parseInt(prop.getProperty(CAConstants.CONNECT_RETRY));
		alertSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_PRIO));
		alertSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.ALERTSESSION_TO));
		moSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_PRIO));
		moSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.MOSESSION_TO));
		ideasSessionPriority = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_PRIO));
		ideasSessionTimeout = Integer.parseInt(prop.getProperty(CAConstants.IDEASSESSION_TO));

		if(iorFileDir == null || iorFileDir.equals("")){
			this.iorFileDir = prop.getProperty(CAConstants.IOR_FILE_DIR);
		}else{
			this.iorFileDir = iorFileDir;
		}
		howFlag = false;
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

		// ���O�C�����擾
		this.loginId = loginId;
		this.loginPasswd = loginPasswd;

		
		//ORB�������B�����t��
		isConnect = false;
		// CA2.1�p
//		System.setProperty("org.omg.CORBA.ORBClass","com.iona.corba.art.artimpl.ORBImpl");
//		System.setProperty("org.omg.CORBA.ORBSingletonClass","com.iona.corba.art.artimpl.ORBSingleton");
		orb = ORB.init((String[])null, null);

		// �ڑ����@�U�蕪��
		// Caserver�ւ̐ڑ�����
		if(howConnect()){
			//IOR�f�[�^�̎擾
			try {
				caConn = new IORConnect(orb, iorServerHost, iorServerPort);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			caConn = new IORConnect(iorFileDir, nxSystem);
		}
		objRef = orb.string_to_object(caConn.getIorData().get(IORConnect.AUTHENTIC).toString());

		// �F��
		authenticator = NxSecurity_v1.AuthenticatorHelper.narrow(objRef);


		//CA�ł��܂���������Ȃ��ꍇ�̑΍�
		for(int retry = 0; ; retry++){
			try{
				credentials = authenticator.login(loginId, loginPasswd, doEncrypt);
			}catch(NxSecurity_v1.InvalidLogin ile){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("InvalidLogin on connect. retry...");
					continue;
				}
				throw new NxException(ile);
			}catch(NxSecurity_v1.ProcessingFailure pf){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("ProcessingFailure on connect. retry...");
					continue;
				}
				throw new NxException(pf);
			}catch(NxSecurity_v1.HASecondary hase){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("HASecondary on connect. retry...");
					continue;
				}
				throw new NxException(hase);
			}

			if(credentials == null){
				if(retry < connectRetry){
					System.err.println("credentials is null on connect. retry...");
					continue;
				}
				throw new NxException(new NxSecurity_v1.ProcessingFailure());
			}
			//���������甲����
			break;
		}
		isConnect = true;
	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<BR>
	 * CA2.x �p login ���\�b�h
	 *
	 * @param doEncrypt			true - �Í���  false - ��Í���
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(boolean doEncrypt) throws NxException{

		// ���O�C�����擾
		String id = System.getProperty(CAConstants.LOGIN_ID);
		String passwd = System.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd, doEncrypt);

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

		//�ݒ�t�@�C���̓ǂݍ���
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("�ڑ��ݒ�t�@�C���̓ǂݍ��݂Ɏ��s���܂���");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		// ���O�C�����擾
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd, doEncrypt);

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

		// ���O�C�����擾
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd, doEncrypt);

	}


	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<br>
	 * �Í����͂��܂���B
	 *
	 * @param loginId		NetExpert���O�C��ID
	 * @param loginPasswd	NetExpert���O�C���p�X���[�h
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(String loginId, String loginPasswd) throws NxException{

		// ���O�C�����擾
		this.loginId = loginId;
		this.loginPasswd = loginPasswd;

		//ORB�������B�����t��
		isConnect = false;
		// CA2.1�p
//		System.setProperty("org.omg.CORBA.ORBClass","com.iona.corba.art.artimpl.ORBImpl");
//		System.setProperty("org.omg.CORBA.ORBSingletonClass","com.iona.corba.art.artimpl.ORBSingleton");
		orb = ORB.init((String[])null, null);

		// �ڑ����@�U�蕪��
		// Caserver�ւ̐ڑ�����
		if(howConnect()){
			//IOR�f�[�^�̎擾
			try {
				caConn = new IORConnect(orb, iorServerHost, iorServerPort);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			caConn = new IORConnect(iorFileDir, nxSystem);
		}
		objRef = orb.string_to_object(caConn.getIorData().get(IORConnect.AUTHENTIC).toString());

		// �F��
		authenticator = NxSecurity_v1.AuthenticatorHelper.narrow(objRef);


		//CA�ł��܂���������Ȃ��ꍇ�̑΍�
		for(int retry = 0; ; retry++){
			try{
//				authenticator = caConn.loadPrincipalAuthenticator();
				credentials = authenticator.authenticate(loginId, loginPasswd);
			}catch(NxSecurity_v1.InvalidLogin ile){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("InvalidLogin on connect. retry...");
					continue;
				}
				throw new NxException(ile);
			}catch(NxSecurity_v1.ProcessingFailure pf){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("ProcessingFailure on connect. retry...");
					continue;
				}
				throw new NxException(pf);
			}catch(NxSecurity_v1.HASecondary hase){
				isConnect = false;
				if(retry < connectRetry){
					System.err.println("HASecondary on connect. retry...");
					continue;
				}
				throw new NxException(hase);
			}

			if(credentials == null){
				if(retry < connectRetry){
					System.err.println("credentials is null on connect. retry...");
					continue;
				}
				throw new NxException(new NxSecurity_v1.ProcessingFailure());
			}
			//���������甲����
			break;
		}
		isConnect = true;

	}


	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<br>
	 * �Í����͂��܂���B
	 *
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect() throws NxException{

		// ���O�C�����擾
		String id = System.getProperty(CAConstants.LOGIN_ID);
		String passwd = System.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd);

	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<br>
	 * �Í����͂��܂���B
	 *
	 * @param sessionFile		�ڑ��ݒ�t�@�C��
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(File sessionFile) throws NxException{

		//�ݒ�t�@�C���̓ǂݍ���
		//propName = FILENAME;
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try{
			in = new BufferedInputStream(new FileInputStream(sessionFile));
			prop.load(in);
		}catch(Exception e){
			System.err.println("�ڑ��ݒ�t�@�C���̓ǂݍ��݂Ɏ��s���܂���");
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception e){}
		}

		// ���O�C�����擾
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd);

	}

	/**
	 * �T�[�o�Ƃ̐ڑ������܂��B<br>
	 * �Í����͂��܂���B
	 *
	 * @param prop				Properties
	 * @throws NxException ORB�ACA�֘A�ł̗�O������
	 */
	protected void connect(Properties prop) throws NxException{

		// ���O�C�����擾
		String id = prop.getProperty(CAConstants.LOGIN_ID);
		String passwd = prop.getProperty(CAConstants.LOGIN_PASSWD);

		connect(id, passwd);

	}



	/**
	 * �ڑ����@��Ԃ��B
	 * @return IOR�T�[�o�o�R�Ȃ�true�A�ڑ��ݒ�t�@�C���Ȃ�false
	 */
	private boolean howConnect(){
		return howFlag;
	}

	/**
	* �ڑ���Ԃ�Ԃ��B
	* @return �ڑ��ς݂Ȃ�true�A���ڑ��Ȃ�false
	*/
	protected boolean isConnect(){
		return isConnect;
	}

	/**
	 * �F�ؐڑ���ؒf����B���ۂɂ͊eSesion���m�����Ă���΁A�ؒf���邱�Ƃ͂ł��Ȃ��B
	 */
	protected void authenticationDisconnect(){

		if(orb != null){
//			orb = null;
//			orb.shutdown(true);
			orb.destroy();
		}
		isConnect = false;
	}
}
