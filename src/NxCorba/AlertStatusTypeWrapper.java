package NxCorba;

/**
 * NetExpert��NxAlerts_v1.AlertStatusType���`�����N���X�ł��B<BR>
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class AlertStatusTypeWrapper{

	/** NoStatus */
	public static final int NoStatus = 0;
	static final String NO_STATUS = "NoStatus";
	/** Open */
	public static final int Open = 1;
	static final String OPEN = "Open";
	/** Cleared */
	public static final int Cleared = 2;
	static final String CLEARED = "Cleared";

	static final String NOT_UNDERSTAND = "Not understand";

	private NxAlerts_v1.StatusType statusType = null;


	/**
	 * NxAlerts_v1.AlertStatusType���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 * @param statusType - NxAlerts_v1.StatusType
	 */
	protected AlertStatusTypeWrapper(NxAlerts_v1.StatusType statusType){
		this.statusType = statusType;
	}

	/**
	 * StatusType �� NxAlerts_v1.StatusType �ŕԂ��܂��B
	 *
	 * @return NxAlerts_v1.StatusType
	 */
	protected NxAlerts_v1.StatusType getStatusType(){
		return statusType;
	}

	/**
	 * StatusType ��Ԃ��܂��B�Ӗ��s���ȏꍇ�� Integer.MAX_VALUE ��Ԃ��܂��B
	 *
	 * @return StatusType Value;
	 */
	public int getValue(){
		return statusType.value();
	}

	/**
	 * StatusType �𕶎���ŕԂ��܂��B�Ӗ��s���ȏꍇ�� "Not understand" ��Ԃ��܂��B
	 *
	 * @return StatusType String
	 */
	public String getString(){
		switch (statusType.value()){
			case NoStatus:
				return NO_STATUS;
			case Open:
				return OPEN;
			case Cleared:
				return CLEARED;
			default:
				return NOT_UNDERSTAND;
		}
	}

	/**
	 * StatusType �𕶎���ŕԂ��܂��B�Ӗ��s���ȏꍇ�� "Not understand" ��Ԃ��܂��B
	 *
	 * @param statusNum			Status
	 * @return StatusType String
	 */
	public static String getString(int statusNum){
		switch (statusNum){
			case NoStatus:
				return NO_STATUS;
			case Open:
				return OPEN;
			case Cleared:
				return CLEARED;
			default:
				return NOT_UNDERSTAND;
		}
	}
}
