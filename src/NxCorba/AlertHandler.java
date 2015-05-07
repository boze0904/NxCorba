package NxCorba;

/**
 * �o�^����Alert�ɑ΂��Ă̒��ۃN���X�ł��B<BR>
 * ���̃N���X���p������Alert�̏�Ԃ��ς�������̃��\�b�h���������ĉ������B
 * 
 */
abstract public class AlertHandler extends NxAlerts_v1.NotificationHandlerPOA {

	/**
	 * Alert�������������̏������`���܂��B
	 *
	 * @param alert_in ��������Alert
	 */
	abstract public void createNxAlert(AlertWrapper alert_in);

	/**
	 * Alert���폜���ꂽ���̏������`���܂��B
	 *
	 * @alert_in �폜���ꂽAlert
	 */
	abstract public void deleteNxAlert(String alert_in);

	/**
	 * Alert���X�V���ꂽ���̏������`���܂��B
	 *
	 * @param alert_in �X�V���ꂽAlert
	 * @param newAttributes_in �A�g���r���[�g
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
