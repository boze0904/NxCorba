package NxCorba;

/**
 * �o�^����MO�ɑ΂��Ă̒��ۃN���X�ł��B<BR>
 * ���̃N���X���p������MO�̏�Ԃ��ς�������̃��\�b�h���������ĉ������B
 * 
 */
abstract public class ObjectHandler extends NxObjects_v1.NotificationHandlerPOA {

	/**
	 * MO�������������̏������`���܂��B
	 *
	 * @param object_in ��������MO
	 */
	abstract public void createNxObject(NxObjectWrapper object_in);

	/**
	 * MO���폜���ꂽ���̏������`���܂��B
	 *
	 * @object_in �폜���ꂽMO
	 */
	abstract public void deleteNxObject(String object_in);

	/**
	 * MO���X�V���ꂽ���̏������`���܂��B
	 *
	 * @param object_in �X�V���ꂽMO
	 * @param newAttributes_in �A�g���r���[�g
	 */
	abstract public void changeNxAttributeValue(String object_in, NxAttributeWrapper[] newAttributes_in);


	public void createObject(NxObjects_v1.NxObject object_in){
		createNxObject(new NxObjectWrapper(object_in));
	}

	public void deleteObject(String object_in){
		deleteNxObject(object_in);
	}

	public void changeAttributeValue(String object_in, NetExpert_v1.NxAttribute[] newAttributes_in){
		NxAttributeWrapper[] attr_in = new NxAttributeWrapper[newAttributes_in.length];
		for(int i = 0; i < newAttributes_in.length; i++){
			attr_in[i] = new NxAttributeWrapper(newAttributes_in[i]);
		}
		changeNxAttributeValue(object_in, attr_in);
	}

}
