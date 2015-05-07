package NxCorba;

/**
 * NetExpert��NxObjects_v1.NotificationFilter���`�����N���X�ł��B
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class ObjectNotificationFilterWrapper{

	protected NxObjects_v1.NotificationFilter notificationFilter = null;


	/**
	 * NxObjects_v1.NotificationFilter���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 *
	 * @param notificationFilter - NxObjects_v1.NotificationFilter
	 */
	protected ObjectNotificationFilterWrapper(NxObjects_v1.NotificationFilter notificationFilter){

		this.notificationFilter = notificationFilter;
	}

	/**
	 * NxObjects_v1.NotificationFilter���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 *
	 * @param notifications - ObjectNotificationTypeWrapper
	 * @param target NxTarget
	 * @param watchAttributes watchAttributes
	 */
	public ObjectNotificationFilterWrapper(ObjectNotificationTypeWrapper[] notifications, NxTarget target, String[] watchAttributes){

		NxObjects_v1.NotificationType[] type = new NxObjects_v1.NotificationType[notifications.length];
		for(int i = 0; i < notifications.length; i++){
			type[i] = notifications[i].getNotificationType();
		}
		this.notificationFilter = new NxObjects_v1.NotificationFilter(type, target.getTarget(), watchAttributes);
	}


	/**
	 * NxObjects_v1.NotificationFilter ��Ԃ��܂��B
	 *
	 * @return NxObjects_v1.NotificationFilter
	 */
	protected NxObjects_v1.NotificationFilter getNotificationFilter(){
		return notificationFilter;
	}

	/**
	 * ObjectNotificationTypeWrapper ��Ԃ��܂��B
	 *
	 * @return ObjectNotificationTypeWrapper
	 */
	public ObjectNotificationTypeWrapper[] getNotificationType(){
		ObjectNotificationTypeWrapper[] notificationType = new ObjectNotificationTypeWrapper[notificationFilter.notifications.length];
		for(int i = 0; i < notificationFilter.notifications.length; i++){
			notificationType[i] = new ObjectNotificationTypeWrapper(notificationFilter.notifications[i]);
		}
		return notificationType;
	}

	/**
	 * NxTarget ��Ԃ��܂��B
	 *
	 * @return NxTarget
	 */
	public NxTarget getTarget(){
		return new NxTarget(notificationFilter.targetObjects);
	}

	/**
	 * WatchAttributes ��Ԃ��܂��B
	 *
	 * @return WatchAttributes
	 */
	public String[] getWatchAttributes(){
		return notificationFilter.watchAttributes;
	}
}
