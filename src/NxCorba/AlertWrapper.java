package NxCorba;

/**
 * NetExpert��Alert���`�����N���X�ł��B<BR>
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class AlertWrapper{

	/** id */
    protected String id;
	/** alertDef */
    protected String alertDef;
	/** objectId */
    protected String objectId;
	/** objectClass */
    protected String objectClass;
	/** manager */
    protected String manager;
	/** managerClass */
    protected String managerClass;
	/** count */
    protected short count;
	/** ackOper */
    protected String ackOper;
	/** currOper */
    protected String currOper;
	/** AlertSeverityWrapper */
    protected AlertSeverityWrapper nxseverity;
	/** AlertStatusTypeWrapper */
    protected AlertStatusTypeWrapper status;
	/** ClearedByTypeWrapper */
    protected ClearedByTypeWrapper clearedBy;
	/** first */
    protected int first;
	/** last */
    protected int last;
	/** clearedAt */
    protected int clearedAt;
	/** ackedAt */
    protected int ackedAt;
	/** text */
    protected String text;
	/** tTicket */
    protected String tTicket;
	/** NxAttributeWrapper */
    protected NxAttributeWrapper[] extProps;


	/**
	 * ���AlertWrapper�𐶐����܂��B
	 */
	public AlertWrapper(){
		// ��������
	}

	/**
	 * NxAlerts_v1.Alert���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 * @param alert - NxAlerts_v1.Alert
	 */
	protected AlertWrapper(NxAlerts_v1.Alert alert){

        this.id = EUCConv.decode(alert.id);
        this.alertDef = EUCConv.decode(alert.alertDef);
        this.objectId = EUCConv.decode(alert.objectId);
        this.objectClass = EUCConv.decode(alert.objectClass);
        this.manager = EUCConv.decode(alert.manager);
        this.managerClass = EUCConv.decode(alert.managerClass);
        this.count = alert.count;
        this.ackOper = EUCConv.decode(alert.ackOper);
        this.currOper = EUCConv.decode(alert.currOper);
        this.nxseverity = new AlertSeverityWrapper(alert.nxseverity);
        this.status = new AlertStatusTypeWrapper(alert.status);
        this.clearedBy = new ClearedByTypeWrapper(alert.clearedBy);
        this.first = alert.first;
        this.last = alert.last;
        this.clearedAt = alert.clearedAt;
        this.ackedAt = alert.ackedAt;
        this.text = EUCConv.decode(alert.text);
        this.tTicket = EUCConv.decode(alert.tTicket);
        this.extProps = new NxAttributeWrapper[alert.extProps.length];
		for(int i = 0; i < alert.extProps.length; i++){
			extProps[i] = new NxAttributeWrapper(alert.extProps[i]);
		}
	}

	/**
	 * id ��Ԃ��܂��B
	 *
	 * @return id
	 */
	public String getId(){
		return id;
	}

	/**
	 * alertDef ��Ԃ��܂��B
	 *
	 * @return alertDef
	 */
	public String getAlertDef(){
		return alertDef;
	}

	/**
	 * objectId ��Ԃ��܂��B
	 *
	 * @return objectId
	 */
	public String getObjectId(){
		return objectId;
	}

	/**
	 * objectClass ��Ԃ��܂��B
	 *
	 * @return objectClass
	 */
	public String getObjectClass(){
		return objectClass;
	}

	/**
	 * manager ��Ԃ��܂��B
	 *
	 * @return manager
	 */
	public String getManager(){
		return manager;
	}

	/**
	 * managerClass ��Ԃ��܂��B
	 *
	 * @return managerClass
	 */
	public String getManagerClass(){
		return managerClass;
	}

	/**
	 * count ��Ԃ��܂��B
	 *
	 * @return count
	 */
	public int getCount(){
		return count;
	}

	/**
	 * ackOper ��Ԃ��܂��B
	 *
	 * @return ackOper
	 */
	public String getAckOper(){
		return ackOper;
	}

	/**
	 * currOper ��Ԃ��܂��B
	 *
	 * @return currOper
	 */
	public String getCurrOper(){
		return currOper;
	}

	/**
	 * AlertSeverityWrapper ��Ԃ��܂��B
	 *
	 * @return AlertSeverityWrapper
	 */
	public AlertSeverityWrapper getSeverity(){
		return nxseverity;
	}

	/**
	 * AlertStatusTypeWrapper ��Ԃ��܂��B
	 *
	 * @return AlertStatusTypeWrapper
	 */
	public AlertStatusTypeWrapper getStatusType(){
		return status;
	}

	/**
	 * ClearedByTypeWrapper ��Ԃ��܂��B
	 *
	 * @return ClearedByTypeWrapper
	 */
	public ClearedByTypeWrapper getClearedBy(){
		return clearedBy;
	}

	/**
	 * first ��Ԃ��܂��B
	 *
	 * @return first
	 */
	public int getFirst(){
		return first;
	}

	/**
	 * last ��Ԃ��܂��B
	 *
	 * @return last
	 */
	public int getLast(){
		return last;
	}

	/**
	 * clearedAt ��Ԃ��܂��B
	 *
	 * @return clearedAt
	 */
	public int getClearedAt(){
		return clearedAt;
	}

	/**
	 * ackedAt ��Ԃ��܂��B
	 *
	 * @return ackedAt
	 */
	public int getAckedAt(){
		return ackedAt;
	}

	/**
	 * text ��Ԃ��܂��B
	 *
	 * @return text
	 */
	public String getText(){
		return text;
	}

	/**
	 * tTicket ��Ԃ��܂��B
	 *
	 * @return tTicket
	 */
	public String getTTicket(){
		return tTicket;
	}

	/**
	 * NxAttributeWrapper �̔z���Ԃ��܂��B
	 *
	 * @return NxAttributeWrapper �̔z��
	 */
	public NxAttributeWrapper[] getNxAttributeWrapper(){
		return extProps;
	}
}
