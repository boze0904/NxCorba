package NxCorba;

/**
 * NetExpertのNxAlerts_v1.AlertStatusTypeを定義したクラスです。<BR>
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
	 * NxAlerts_v1.AlertStatusTypeをカプセル化したオブジェクトを生成します。
	 * @param statusType - NxAlerts_v1.StatusType
	 */
	protected AlertStatusTypeWrapper(NxAlerts_v1.StatusType statusType){
		this.statusType = statusType;
	}

	/**
	 * StatusType を NxAlerts_v1.StatusType で返します。
	 *
	 * @return NxAlerts_v1.StatusType
	 */
	protected NxAlerts_v1.StatusType getStatusType(){
		return statusType;
	}

	/**
	 * StatusType を返します。意味不明な場合は Integer.MAX_VALUE を返します。
	 *
	 * @return StatusType Value;
	 */
	public int getValue(){
		return statusType.value();
	}

	/**
	 * StatusType を文字列で返します。意味不明な場合は "Not understand" を返します。
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
	 * StatusType を文字列で返します。意味不明な場合は "Not understand" を返します。
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
