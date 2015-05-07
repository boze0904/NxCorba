package NxCorba;

/**
 * ORB CA関係の例外発生時Exceptionクラスです。<BR>
 *
 * @version $Revision: 1.7 $ $Date: 2002/06/20 10:14:01 $
 * @author $Author: inagashima $
 */
public class NxException extends Exception implements NxExceptionConstants{

	/** エラーコード */
	private int code;

	/** メッセージ */
	private String message;

	/**
	 * NxExceptionを新しく構築します。
	 */
	public NxException(){
		message = null;
		code = 0;
	}

	/**
	 * 指定されたNetExpertのExceptionのオブジェクトでエラーメッセージを新しく構築します。
	 * @param obj NetExpertのExceptionオブジェクト
	 */
	public NxException(Object obj){

		/* ------------------------------------------------------------------------
		 Security
		------------------------------------------------------------------------ */
		// NxSecurity_v1.InvalidLogin
		if(obj instanceof NxSecurity_v1.InvalidLogin){
			code = 400;
			message = INVALID_LOGIN;

		// NxSecurity_v1.HASecondary
		}else if(obj instanceof NxSecurity_v1.HASecondary){
			code = 401;
			message = HASECONDARY;

		/* ------------------------------------------------------------------------
		 Alert
		------------------------------------------------------------------------ */
		// NxAlerts_v1.InvalidCredentials
		}else if(obj instanceof NxAlerts_v1.InvalidCredentials){
			code = 100;
			message = INVALID_CREDENTIALS;

		// NxAlerts_v1.ProcessingFailure
		}else if(obj instanceof NxAlerts_v1.ProcessingFailure){
			code = 101;
			message = PROCESSING_FAILURE;

		// NxAlerts_v1.NoAlertDefinition
		}else if(obj instanceof NxAlerts_v1.NoAlertDefinition){
			code = 102;
			message = NO_ALERT_DEFINITION;

		// NxAlerts_v1.InvalidFilter
		}else if(obj instanceof NxAlerts_v1.InvalidFilter){
			code = 103;
			message = INVALID_FILTER;

		// NxAlerts_v1.InvalidOwner
		}else if(obj instanceof NxAlerts_v1.InvalidOwner){
			code = 104;
			message = INVALID_OWNER;

		}else if(obj instanceof NxAlerts_v1.InvalidHandler){
			code = 105;
			message = INVALID_HANDLER;

		}else if(obj instanceof NxAlerts_v1.InvalidWatchAttributes){
			code = 106;
			message = INVALID_WATCH_ATTRIBUTES;

		}else if(obj instanceof NxAlerts_v1.MissingNotificationTypes){
			code = 107;
			message = MISSING_NOTIFICATION_TYPES;

		/* ------------------------------------------------------------------------
		 Object
		------------------------------------------------------------------------ */
		// NxObjects_v1.InvalidCredentials
		}else if(obj instanceof NxObjects_v1.InvalidCredentials){
			code = 200;
			message = INVALID_CREDENTIALS;

		// NxObjects_v1.SessionLimit
		}else if(obj instanceof NxObjects_v1.SessionLimit){
			code = 201;
			message = SESSION_LIMIT;

		// NxObjects_v1.InvalidStatus
		}else if(obj instanceof NxObjects_v1.InvalidStatus){
			code = 202;
			message = INVALID_STATUS;

		// NxObjects_v1.DuplicateName
		}else if(obj instanceof NxObjects_v1.DuplicateName){
			code = 203;
			message = DUPLICATE_NAME;

		// NxObjects_v1.InvalidObject
		}else if(obj instanceof NxObjects_v1.InvalidObject){
			code = 204;
			message = INVALID_OBJECT;

		// NxObjects_v1.InvalidAttribute
		}else if(obj instanceof NxObjects_v1.InvalidAttribute){
			code = 205;
			message = INVALID_ATTRIBUTE;

		// NxObjects_v1.InvalidAttributeValue
		}else if(obj instanceof NxObjects_v1.InvalidAttributeValue){
			code = 206;
			message = INVALID_ATTRIBUTEVALUE;

		// NxObjects_v1.InvalidClass
		}else if(obj instanceof NxObjects_v1.InvalidClass){
			code = 207;
			message = INVALID_CLASS;

		// NxObjects_v1.InvalidManager
		}else if(obj instanceof NxObjects_v1.InvalidManager){
			code = 208;
			message = INVALID_MANAGER;

		// NxObjects_v1.InvalidName
		}else if(obj instanceof NxObjects_v1.InvalidName){
			code = 209;
			message = INVALID_NAME;

		// NxObjects_v1.InvalidSuperior
		}else if(obj instanceof NxObjects_v1.InvalidSuperior){
			code = 210;
			message = INVALID_SUPERIOR;

		// NxObjects_v1.ProcessingFailure
		}else if(obj instanceof NxObjects_v1.ProcessingFailure){
			code = 211;
			message = PROCESSING_FAILURE;

		// NxObjects_v1.InvalidTarget
		}else if(obj instanceof NxObjects_v1.InvalidTarget){
			code = 212;
			message = INVALID_TARGET;

		/* ------------------------------------------------------------------------
		 Ideas
		------------------------------------------------------------------------ */
		// NxIdeas_v1.InvalidCredentials
		}else if(obj instanceof NxIdeas_v1.InvalidCredentials){
			code = 300;
			message = INVALID_CREDENTIALS;

		// NxIdeas_v1.ProcessingFailure
		}else if(obj instanceof NxIdeas_v1.ProcessingFailure){
			code = 301;
			message = PROCESSING_FAILURE;

		// NxIdeas_v1.InvalidEvent
		}else if(obj instanceof NxIdeas_v1.InvalidEvent){
			code = 302;
			message = INVALID_EVENT;

		// NxIdeas_v1.InvalidClass
		}else if(obj instanceof NxIdeas_v1.InvalidClass){
			code = 303;
			message = INVALID_CLASS;

		// NxIdeas_v1.InvalidManager
		}else if(obj instanceof NxIdeas_v1.InvalidManager){
			code = 304;
			message = INVALID_MANAGER;

		// NxIdeas_v1.InvalidAttribute
		}else if(obj instanceof NxIdeas_v1.InvalidAttribute){
			code = 305;
			message = INVALID_ATTRIBUTE;

		// NxIdeas_v1.InvalidAttributeValue
		}else if(obj instanceof NxIdeas_v1.InvalidAttributeValue){
			code = 306;
			message = INVALID_ATTRIBUTEVALUE;

		// org.omg.CORBA.SystemException
		}else if(obj instanceof org.omg.CORBA.SystemException){
			code = 500;
			message = "org.omg.CORBA.SystemException";

		}
	}

	/**
	 * エラーコードを返します。
	 *
	 * @return エラーコード
	 */
	public int getCode(){
		return code;
	}

	/**
	 * メッセージを返します。
	 *
	 * @return メッセージ
	 */
	public String getMessage(){
		return message;
	}

}
