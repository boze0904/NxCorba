package NxCorba;

/**
 * NetExpert��NxAlerts_v1.NotificationFilter���`�����N���X�ł��B
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class AlertNotificationFilterWrapper{

	protected NxAlerts_v1.NotificationFilter notificationFilter = null;


	/**
	 * NxAlerts_v1.NotificationFilter���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 *
	 * @param notificationFilter - NxAlerts_v1.NotificationFilter
	 */
	protected AlertNotificationFilterWrapper(NxAlerts_v1.NotificationFilter notificationFilter){

		this.notificationFilter = notificationFilter;
	}

	/**
	 * NxAlerts_v1.NotificationFilter���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 *
	 * @param notifications - AlertNotificationTypeWrapper
	 * @param watchAttributes watchAttributes
	 * @param alertFilter alertFilter
	 */
	public AlertNotificationFilterWrapper(AlertNotificationTypeWrapper[] notifications, String[] watchAttributes, String alertFilter){

		NxAlerts_v1.NotificationType[] type = new NxAlerts_v1.NotificationType[notifications.length];
		for(int i = 0; i < notifications.length; i++){
			type[i] = notifications[i].getNotificationType();
		}
		this.notificationFilter = new NxAlerts_v1.NotificationFilter(type, watchAttributes, alertFilter);
	}


	/**
	 * NxAlerts_v1.NotificationFilter ��Ԃ��܂��B
	 *
	 * @return NxAlerts_v1.NotificationFilter
	 */
	protected NxAlerts_v1.NotificationFilter getNotificationFilter(){
		return notificationFilter;
	}

	/**
	 * AlertNotificationTypeWrapper ��Ԃ��܂��B
	 *
	 * @return AlertNotificationTypeWrapper
	 */
	public AlertNotificationTypeWrapper[] getNotificationType(){
		AlertNotificationTypeWrapper[] notificationType = new AlertNotificationTypeWrapper[notificationFilter.notifications.length];
		for(int i = 0; i < notificationFilter.notifications.length; i++){
			notificationType[i] = new AlertNotificationTypeWrapper(notificationFilter.notifications[i]);
		}
		return notificationType;
	}

	/**
	 * WatchAttributes ��Ԃ��܂��B
	 *
	 * @return WatchAttributes
	 */
	public String[] getWatchAttributes(){
		return notificationFilter.watchAttributes;
	}

	/**
	 * AlertFilter ��Ԃ��܂��B
	 *
	 * @return alertFilter
	 */
	public String getAlertFilter(){
		return notificationFilter.alertFilter;
	}
}
