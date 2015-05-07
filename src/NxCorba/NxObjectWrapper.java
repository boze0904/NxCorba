package NxCorba;

/**
 * NetExpertのMOを定義したクラスです。<BR>
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class NxObjectWrapper{

	/** Mo名 */
	protected String nameId;
	/** Class */
	protected String objectClass;
	/** 親クラス */
	protected String parent;
	/** manager */
	protected String manager;
	/** reportedAs */
	protected String reportedAs;
	/** アトリビュート */
	public NxAttributeWrapper[] attr;


	/**
	 * 空のNxObjectWrapperを生成します。
	 */
	public NxObjectWrapper(){
		// 処理無し
	}

	/**
	 * NxObjects_v1.NxObjectをカプセル化したオブジェクトを生成します。
	 * @param mo - NxObjects_v1.NxObject
	 */
	protected NxObjectWrapper(NxObjects_v1.NxObject mo){

		this.nameId = mo.nameId;
		this.objectClass = mo.objectClass;
		this.parent = mo.parent;
		this.manager = mo.manager;
		this.reportedAs = mo.reportedAs;

		// アトリビュートの生成
		attr = new NxAttributeWrapper[mo.attributes.length];
		for(int i = 0; i < mo.attributes.length; i++){
			attr[i] = new NxAttributeWrapper(mo.attributes[i]);
		}
	}


	/**
	 * MO名を返します。
	 * @return MO名
	 */
	public String getNameId(){
		return nameId;
	}

	/**
	 * Class名を返します。
	 * @return Class名
	 */
	public String getObjectClass(){
		return objectClass;
	}

	/**
	 * 親Class名を返します。
	 * @return 親Class名
	 */
	public String getParent(){
		return parent;
	}

	/**
	 * Manager名を返します。
	 * @return Manager名
	 */
	public String getManager(){
		return manager;
	}

	/**
	 * ReportedAs名を返します。
	 * @return ReportedAs名
	 */
	public String getReportedAs(){
		return reportedAs;
	}

	/**
	 * アトリビュートを返します。
	 *
	 * @return		NxAttributeWrapper
	 */
	public NxAttributeWrapper[] getNxAttributes(){
		return attr;
	}

	/**
	 * 必要なアトリビュートを返します。
	 * 呼び出し元で指定したObject型にキャストしてください。
	 * @param attrName アトリビュート名
	 * @return アトリビュートの値を返します。該当するアトリビュートが存在しない場合、空の場合nullを返します。
	 */
	public Object getNxAttribute(String attrName){
		for(int i = 0; i < attr.length; i++){
			if(attr[i].isNxAttribute(attrName)){
				return attr[i].getNxAttribute();
			}
		}
		return null;
	}


}
