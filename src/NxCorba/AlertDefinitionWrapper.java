package NxCorba;

/**
 * NetExpertのAlertDefinitionを定義したクラスです。<BR>
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class AlertDefinitionWrapper{

	/** name */
	protected String name;
	/** clearTimeout */
	protected int clearTimeout;
	/** AlertSeverityWrapper */
	protected AlertSeverityWrapper nxseverity;
	/** alarmType */
	protected String alarmType;
	/** problemType */
	protected String problemType;
	/** description */
	protected String description;
	/** detailedDescription */
	protected String detailedDescription;
	/** advice */
	protected String advice;

	/**
	 * 空のAlertDefinitionWrapperを生成します。
	 */
	public AlertDefinitionWrapper(){
		// 処理無し
	}

	/**
	 * NxAlerts_v1.AlertDefinitionをカプセル化したオブジェクトを生成します。
	 * @param alertDefinition - NxAlerts_v1.AlertDefinition
	 */
	protected AlertDefinitionWrapper(NxAlerts_v1.AlertDefinition alertDefinition){

		this.name = EUCConv.decode(alertDefinition.name);
		this.clearTimeout = alertDefinition.clearTimeout;
		this.nxseverity = new AlertSeverityWrapper(alertDefinition.nxseverity);
		this.alarmType = EUCConv.decode(alertDefinition.alarmType);
		this.problemType = EUCConv.decode(alertDefinition.problemType);
		this.description = EUCConv.decode(alertDefinition.description);
		this.detailedDescription = EUCConv.decode(alertDefinition.detailedDescription);
		this.advice = EUCConv.decode(alertDefinition.advice);

	}

	/**
	 * Nameを返します。
	 *
	 * @return Name
	 */
	public String getName(){
		return name;
	}

	/**
	 * ClearTimeout を返します。
	 *
	 * @return ClearTimeout
	 */
	public int getClearTimeout(){
		return clearTimeout;
	}

	/**
	 * AlertSeverityWrapper を返します。
	 *
	 */
	public AlertSeverityWrapper getSeverity(){
		return nxseverity;
	}

	/**
	 * AlarmType を返します。
	 *
	 * @return AlarmType
	 */
	public String getAlarmType(){
		return alarmType;
	}

	/**
	 * ProblemType を返します
	 *
	 * @return ProblemType
	 */
	public String getProblemType(){
		return problemType;
	}

	/**
	 * Description を返します。
	 *
	 * @return Description
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * DetailedDescription を返します。
	 *
	 * @return DetailedDescription
	 */
	public String getDetailedDescription(){
		return detailedDescription;
	}

	/**
	 * Advice を返します。
	 *
	 * @return Advice
	 */
	public String getAdvice(){
		return advice;
	}
}
