package NxCorba;

/**
 * Caserverとのインターフェースを定義したインターフェースです。
 *
 * @version $Revision: 1.5 $ $Date: 2002/06/03 04:05:51 $
 * @author $Author: inagashima $
 */
interface CAConstants{
	/** NetExpertシステム名 */
	static String NXSYSTEM		= "NXSYSTEM";
	/** IORServer名 */
	static String IOR_SERVER_HOST	= "IOR_SERVER_HOST";
	/** IORServerPort */
	static String IOR_SERVER_PORT	= "IOR_SERVER_PORT";
	/** IORファイルディレクトリ */
	static String IOR_FILE_DIR	= "IOR_FILE_DIR";
	/** connect時のリトライ回数 */
	static String CONNECT_RETRY	= "CONNECT_RETRY";
	/** Login ID */
	static String LOGIN_ID			= "LOGIN_ID";
	/** Login Passwd */
	static String LOGIN_PASSWD		= "LOGIN_PASSWD";
	/** MOSession Priority */
	static String MOSESSION_PRIO	= "MOSESSION_PRIO";
	/** MOSesson TIMEOUT */
	static String MOSESSION_TO		= "MOSESSION_TO";
	/** AlertSession Priority */
	static String ALERTSESSION_PRIO= "ALERTSESSION_PRIO";
	/** AlertSession TIMEOUT */
	static String ALERTSESSION_TO	= "ALERTSESSION_TO";
	/** IdeasSession Priority */
	static String IDEASSESSION_PRIO= "IDEASSESSION_PRIO";
	/** IdeasSession TIMEOUT */
	static String IDEASSESSION_TO	= "IDEASSESSION_TO";

}
