package NxCorba;

/**
 * NetExpertのNxObjects_v1.NotificationTypeを定義したクラスです。
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class ObjectNotificationTypeWrapper{

	/** ObjectCreation */
	public static final int ObjectCreation = 0;
	static final String OBJECT_CREATION = "ObjectCreation";
	/** ObjectDeletion */
	public static final int ObjectDeletion = 1;
	static final String OBJECT_DELETION = "ObjectDeletion";
	/** AttributeValueChange */
	public static final int AttributeValueChange = 2;
	static final String ATTRIBUTE_VALUE_CHANGE = "AttributeValueChange";

	static final String NOT_UNDERSTAND = "Not understand";

	protected NxObjects_v1.NotificationType notificationType = null;


	/**
	 * NxObjects_v1.NotificationTypeをカプセル化したオブジェクトを生成します。
	 *
	 * @param notificationType NxObjects_v1.NotificationType
	 */
	protected ObjectNotificationTypeWrapper(NxObjects_v1.NotificationType notificationType){

		this.notificationType = notificationType;
	}

	/**
	 * NxObjects_v1.NotificationTypeをカプセル化したオブジェクトを生成します。
	 *
	 * @param notificationType NotificationType
	 */
	public ObjectNotificationTypeWrapper(int notificationType){

		this.notificationType = NxObjects_v1.NotificationType.from_int(notificationType);
	}

	/**
	 * NotificationType を返します。
	 *
	 * @return NotificationType
	 */
	protected NxObjects_v1.NotificationType getNotificationType(){

		return notificationType;
	}

	/**
	 * NotificationType Value を返します。意味不明な場合は Integer.MAX_VALUE を返します。
	 *
	 * @return NotificationType Value
	 */
	public int getValue(){

		return notificationType.value();
	}

	/**
	 * NotificationType を文字列で返します。意味不明な場合は "Not understand" を返します。
	 *
	 * @return NotificationType String
	 */
	public String getString(){
		switch (notificationType.value()){
			case ObjectCreation:
				return OBJECT_CREATION;
			case ObjectDeletion:
				return OBJECT_DELETION;
			case AttributeValueChange:
				return ATTRIBUTE_VALUE_CHANGE;
			default:
				return NOT_UNDERSTAND;
		}
	}
}
