package NxCorba;

/**
 * 登録したAlertに対しての抽象クラスです。<BR>
 * このクラスを継承してAlertの状態が変わった時のメソッドを実装して下さい。
 * 
 */
abstract public class AlertHandler extends NxAlerts_v1.NotificationHandlerPOA {

	/**
	 * Alertが発生した時の処理を定義します。
	 *
	 * @param alert_in 発生したAlert
	 */
	abstract public void createNxAlert(AlertWrapper alert_in);

	/**
	 * Alertが削除された時の処理を定義します。
	 *
	 * @alert_in 削除されたAlert
	 */
	abstract public void deleteNxAlert(String alert_in);

	/**
	 * Alertが更新された時の処理を定義します。
	 *
	 * @param alert_in 更新されたAlert
	 * @param newAttributes_in アトリビュート
	 */
	abstract public void updateNxAlert(String alert_in, NxAttributeWrapper[] newAttributes_in);


	public void createAlert(NxAlerts_v1.Alert alert_in){
		createNxAlert(new AlertWrapper(alert_in));
	}

	public void deleteAlert(String alert_in){
		deleteNxAlert(alert_in);
	}

	public void updateAlert(String alert_in, NetExpert_v1.NxAttribute[] newAttributes_in){
		NxAttributeWrapper[] attr_in = new NxAttributeWrapper[newAttributes_in.length];
		for(int i = 0; i < newAttributes_in.length; i++){
			attr_in[i] = new NxAttributeWrapper(newAttributes_in[i]);
		}
		updateNxAlert(alert_in, attr_in);
	}

}
