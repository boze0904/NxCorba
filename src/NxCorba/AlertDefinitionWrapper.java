package NxCorba;

/**
 * NetExpert��AlertDefinition���`�����N���X�ł��B<BR>
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
	 * ���AlertDefinitionWrapper�𐶐����܂��B
	 */
	public AlertDefinitionWrapper(){
		// ��������
	}

	/**
	 * NxAlerts_v1.AlertDefinition���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
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
	 * Name��Ԃ��܂��B
	 *
	 * @return Name
	 */
	public String getName(){
		return name;
	}

	/**
	 * ClearTimeout ��Ԃ��܂��B
	 *
	 * @return ClearTimeout
	 */
	public int getClearTimeout(){
		return clearTimeout;
	}

	/**
	 * AlertSeverityWrapper ��Ԃ��܂��B
	 *
	 */
	public AlertSeverityWrapper getSeverity(){
		return nxseverity;
	}

	/**
	 * AlarmType ��Ԃ��܂��B
	 *
	 * @return AlarmType
	 */
	public String getAlarmType(){
		return alarmType;
	}

	/**
	 * ProblemType ��Ԃ��܂�
	 *
	 * @return ProblemType
	 */
	public String getProblemType(){
		return problemType;
	}

	/**
	 * Description ��Ԃ��܂��B
	 *
	 * @return Description
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * DetailedDescription ��Ԃ��܂��B
	 *
	 * @return DetailedDescription
	 */
	public String getDetailedDescription(){
		return detailedDescription;
	}

	/**
	 * Advice ��Ԃ��܂��B
	 *
	 * @return Advice
	 */
	public String getAdvice(){
		return advice;
	}
}
