package NxCorba;

/**
 * NetExpert��NxAlerts_v1.NotificationType���`�����N���X�ł��B
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class AlertNotificationTypeWrapper{

	/** AlertCreation */
	public static final int AlertCreation = 0;
	static final String ALERT_CREATION = "AlertCreation";
	/** AlertDeletion */
	public static final int AlertDeletion = 1;
	static final String ALERT_DELETION = "AlertDeletion";
	/** AttributeValueChange */
	public static final int AttributeValueChange = 2;
	static final String ATTRIBUTE_VALUE_CHANGE = "AttributeValueChange";

	static final String NOT_UNDERSTAND = "Not understand";

	protected NxAlerts_v1.NotificationType notificationType = null;


	/**
	 * NxAlerts_v1.NotificationType���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 *
	 * @param notificationType NxAlerts_v1.NotificationType
	 */
	protected AlertNotificationTypeWrapper(NxAlerts_v1.NotificationType notificationType){

		this.notificationType = notificationType;
	}

	/**
	 * NxAlerts_v1.NotificationType���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 *
	 * @param notificationType NotificationType
	 */
	public AlertNotificationTypeWrapper(int notificationType){

		this.notificationType = NxAlerts_v1.NotificationType.from_int(notificationType);
	}

	/**
	 * NotificationType ��Ԃ��܂��B
	 *
	 * @return NotificationType
	 */
	protected NxAlerts_v1.NotificationType getNotificationType(){

		return notificationType;
	}

	/**
	 * NotificationType Value ��Ԃ��܂��B�Ӗ��s���ȏꍇ�� Integer.MAX_VALUE ��Ԃ��܂��B
	 *
	 * @return NotificationType Value
	 */
	public int getValue(){

		return notificationType.value();
	}

	/**
	 * NotificationType �𕶎���ŕԂ��܂��B�Ӗ��s���ȏꍇ�� "Not understand" ��Ԃ��܂��B
	 *
	 * @return NotificationType String
	 */
	public String getString(){
		switch (notificationType.value()){
			case AlertCreation:
				return ALERT_CREATION;
			case AlertDeletion:
				return ALERT_DELETION;
			case AttributeValueChange:
				return ATTRIBUTE_VALUE_CHANGE;
			default:
				return NOT_UNDERSTAND;
		}
	}
}
