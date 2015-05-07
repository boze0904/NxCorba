package NxCorba;

/**
 * 登録したMOに対しての抽象クラスです。<BR>
 * このクラスを継承してMOの状態が変わった時のメソッドを実装して下さい。
 * 
 */
abstract public class ObjectHandler extends NxObjects_v1.NotificationHandlerPOA {

	/**
	 * MOが生成した時の処理を定義します。
	 *
	 * @param object_in 生成したMO
	 */
	abstract public void createNxObject(NxObjectWrapper object_in);

	/**
	 * MOが削除された時の処理を定義します。
	 *
	 * @object_in 削除されたMO
	 */
	abstract public void deleteNxObject(String object_in);

	/**
	 * MOが更新された時の処理を定義します。
	 *
	 * @param object_in 更新されたMO
	 * @param newAttributes_in アトリビュート
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
