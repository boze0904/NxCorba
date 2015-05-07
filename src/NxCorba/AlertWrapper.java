package NxCorba;

/**
 * NetExpertのAlertを定義したクラスです。<BR>
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
	 * 空のAlertWrapperを生成します。
	 */
	public AlertWrapper(){
		// 処理無し
	}

	/**
	 * NxAlerts_v1.Alertをカプセル化したオブジェクトを生成します。
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
	 * id を返します。
	 *
	 * @return id
	 */
	public String getId(){
		return id;
	}

	/**
	 * alertDef を返します。
	 *
	 * @return alertDef
	 */
	public String getAlertDef(){
		return alertDef;
	}

	/**
	 * objectId を返します。
	 *
	 * @return objectId
	 */
	public String getObjectId(){
		return objectId;
	}

	/**
	 * objectClass を返します。
	 *
	 * @return objectClass
	 */
	public String getObjectClass(){
		return objectClass;
	}

	/**
	 * manager を返します。
	 *
	 * @return manager
	 */
	public String getManager(){
		return manager;
	}

	/**
	 * managerClass を返します。
	 *
	 * @return managerClass
	 */
	public String getManagerClass(){
		return managerClass;
	}

	/**
	 * count を返します。
	 *
	 * @return count
	 */
	public int getCount(){
		return count;
	}

	/**
	 * ackOper を返します。
	 *
	 * @return ackOper
	 */
	public String getAckOper(){
		return ackOper;
	}

	/**
	 * currOper を返します。
	 *
	 * @return currOper
	 */
	public String getCurrOper(){
		return currOper;
	}

	/**
	 * AlertSeverityWrapper を返します。
	 *
	 * @return AlertSeverityWrapper
	 */
	public AlertSeverityWrapper getSeverity(){
		return nxseverity;
	}

	/**
	 * AlertStatusTypeWrapper を返します。
	 *
	 * @return AlertStatusTypeWrapper
	 */
	public AlertStatusTypeWrapper getStatusType(){
		return status;
	}

	/**
	 * ClearedByTypeWrapper を返します。
	 *
	 * @return ClearedByTypeWrapper
	 */
	public ClearedByTypeWrapper getClearedBy(){
		return clearedBy;
	}

	/**
	 * first を返します。
	 *
	 * @return first
	 */
	public int getFirst(){
		return first;
	}

	/**
	 * last を返します。
	 *
	 * @return last
	 */
	public int getLast(){
		return last;
	}

	/**
	 * clearedAt を返します。
	 *
	 * @return clearedAt
	 */
	public int getClearedAt(){
		return clearedAt;
	}

	/**
	 * ackedAt を返します。
	 *
	 * @return ackedAt
	 */
	public int getAckedAt(){
		return ackedAt;
	}

	/**
	 * text を返します。
	 *
	 * @return text
	 */
	public String getText(){
		return text;
	}

	/**
	 * tTicket を返します。
	 *
	 * @return tTicket
	 */
	public String getTTicket(){
		return tTicket;
	}

	/**
	 * NxAttributeWrapper の配列を返します。
	 *
	 * @return NxAttributeWrapper の配列
	 */
	public NxAttributeWrapper[] getNxAttributeWrapper(){
		return extProps;
	}
}
