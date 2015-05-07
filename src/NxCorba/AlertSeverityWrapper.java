package NxCorba;

/**
 * NetExpertのAlertSeverityを定義したクラスです。<BR>
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class AlertSeverityWrapper{

	/** NoSeverity */
    public static final int NoSeverity = 0;
	static final String NO_SEVERITY = "NoSeverity";
	/** Normal */
    public static final int Normal = 1;
	static final String NORMAL = "Normal";
	/** Indeterminate */
    public static final int Indeterminate = 2;
	static final String INDETERMINATE = "Indeterminate";
	/** Warning */
    public static final int Warning = 3;
	static final String WARNING = "Warning";
	/** Minor */
    public static final int Minor = 4;
	static final String MINOR = "Minor";
	/** Major */
    public static final int Major = 5;
	static final String MAJOR = "Major";
	/** Critical */
    public static final int Critical = 6;
	static final String CRITICAL = "Critical";
	/** AlertCleared */
    public static final int AlertCleared = 7;
	static final String ALERT_CLEARED = "AlertCleared";
	/** Unknown */
    public static final int Unknown = 8;
	static final String UNKNOWN = "Unknown";
	/** InvalidSeverity */
    public static final int InvalidSeverity = 9;
	static final String INVALID_SEVERITY = "InvalidSeverity";

	static final String NOT_UNDERSTAND = "Not understand";

	private NxAlerts_v1.Severity severity = null;


	/**
	 * NxAlerts_v1.Severityをカプセル化したオブジェクトを生成します。
	 * @param severity - NxAlerts_v1.Severity
	 */
	protected AlertSeverityWrapper(NxAlerts_v1.Severity severity){
		this.severity = severity;
	}

	/**
	 * Severity を NxAlerts_v1.Severity で返します。
	 *
	 * @return NxAlerts_v1.Severity
	 */
	protected NxAlerts_v1.Severity getSeverity(){
		return severity;
	}

	/**
	 * Severity を返します。意味不明な場合は Integer.MAX_VALUE を返します。
	 *
	 * @return Severity Value;
	 */
	public int getValue(){
		return severity.value();
	}

	/**
	 * Severity を文字列で返します。意味不明な場合は "Not understand" を返します。
	 *
	 * @return Severity String
	 */
	public String getString(){
		switch (severity.value()){
			case NoSeverity:
				return NO_SEVERITY;
			case Normal:
				return NORMAL;
			case Indeterminate:
				return INDETERMINATE;
			case Warning:
				return WARNING;
			case Minor:
				return MINOR;
			case Major:
				return MAJOR;
			case Critical:
				return CRITICAL;
			case AlertCleared:
				return ALERT_CLEARED;
			case Unknown:
				return UNKNOWN;
			case InvalidSeverity:
				return INVALID_SEVERITY;
			default:
				return NOT_UNDERSTAND;
		}
	}

	/**
	 * Severity を文字列で返します。意味不明な場合は "Not understand" を返します。
	 *
	 * @param severityNum		severity
	 * @return Severity String
	 */
	public static String getString(int severityNum){
		switch (severityNum){
			case NoSeverity:
				return NO_SEVERITY;
			case Normal:
				return NORMAL;
			case Indeterminate:
				return INDETERMINATE;
			case Warning:
				return WARNING;
			case Minor:
				return MINOR;
			case Major:
				return MAJOR;
			case Critical:
				return CRITICAL;
			case AlertCleared:
				return ALERT_CLEARED;
			case Unknown:
				return UNKNOWN;
			case InvalidSeverity:
				return INVALID_SEVERITY;
			default:
				return NOT_UNDERSTAND;
		}
	}
}
