package NxCorba;

/**
 * NetExpert��NxAlerts_v1.ClearedByType���`�����N���X�ł��B<BR>
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class ClearedByTypeWrapper{

	/** None */
	public static final int None = 0;
	static final String NONE = "None";
	/** Operator */
	public static final int Operator = 1;
	static final String OPERATOR = "Operator";
	/** Timeout */
	public static final int Timeout = 2;
	static final String TIMEOUT = "Timeout";
	/** Event */
	public static final int Event = 3;
	static final String EVENT = "Event";


	static final String NOT_UNDERSTAND = "Not understand";

	private NxAlerts_v1.ClearedByType clearedBy = null;


	/**
	 * NxAlerts_v1.ClearedByType���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 * @param clearedBy - NxAlerts_v1.ClearedByType
	 */
	protected ClearedByTypeWrapper(NxAlerts_v1.ClearedByType clearedBy){
		this.clearedBy = clearedBy;
	}

	/**
	 * NxAlerts_v1.ClearedByType �ŕԂ��܂��B
	 *
	 * @return NxAlerts_v1.ClearedByType
	 */
	protected NxAlerts_v1.ClearedByType getClearedBy(){
		return clearedBy;
	}

	/**
	 * ClearedByType ��Ԃ��܂��B�Ӗ��s���ȏꍇ�� Integer.MAX_VALUE ��Ԃ��܂��B
	 *
	 * @return ClearedByType Value;
	 */
	public int getValue(){
		return clearedBy.value();
	}

	/**
	 * ClearedByType �𕶎���ŕԂ��܂��B�Ӗ��s���ȏꍇ�� "Not understand" ��Ԃ��܂��B
	 *
	 * @return ClearedByType String
	 */
	public String getString(){
		switch (clearedBy.value()){
			case None:
				return NONE;
			case Operator:
				return OPERATOR;
			case Timeout:
				return TIMEOUT;
			case Event:
				return EVENT;
			default:
				return NOT_UNDERSTAND;
		}
	}

	/**
	 * ClearedByType �𕶎���ŕԂ��܂��B�Ӗ��s���ȏꍇ�� "Not understand" ��Ԃ��܂��B
	 *
	 * @param clearedNum		ClearedByType
	 * @return ClearedByType String
	 */
	public static String getString(int clearedNum){
		switch (clearedNum){
			case None:
				return NONE;
			case Operator:
				return OPERATOR;
			case Timeout:
				return TIMEOUT;
			case Event:
				return EVENT;
			default:
				return NOT_UNDERSTAND;
		}
	}
}
