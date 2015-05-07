package NxCorba;

/**
 * NetExpert��MO���`�����N���X�ł��B<BR>
 *
 * @version $Revision: 1.3 $ $Date: 2002/05/22 04:01:36 $
 * @author $Author: tookubo $
 */
public class NxObjectWrapper{

	/** Mo�� */
	protected String nameId;
	/** Class */
	protected String objectClass;
	/** �e�N���X */
	protected String parent;
	/** manager */
	protected String manager;
	/** reportedAs */
	protected String reportedAs;
	/** �A�g���r���[�g */
	public NxAttributeWrapper[] attr;


	/**
	 * ���NxObjectWrapper�𐶐����܂��B
	 */
	public NxObjectWrapper(){
		// ��������
	}

	/**
	 * NxObjects_v1.NxObject���J�v�Z���������I�u�W�F�N�g�𐶐����܂��B
	 * @param mo - NxObjects_v1.NxObject
	 */
	protected NxObjectWrapper(NxObjects_v1.NxObject mo){

		this.nameId = mo.nameId;
		this.objectClass = mo.objectClass;
		this.parent = mo.parent;
		this.manager = mo.manager;
		this.reportedAs = mo.reportedAs;

		// �A�g���r���[�g�̐���
		attr = new NxAttributeWrapper[mo.attributes.length];
		for(int i = 0; i < mo.attributes.length; i++){
			attr[i] = new NxAttributeWrapper(mo.attributes[i]);
		}
	}


	/**
	 * MO����Ԃ��܂��B
	 * @return MO��
	 */
	public String getNameId(){
		return nameId;
	}

	/**
	 * Class����Ԃ��܂��B
	 * @return Class��
	 */
	public String getObjectClass(){
		return objectClass;
	}

	/**
	 * �eClass����Ԃ��܂��B
	 * @return �eClass��
	 */
	public String getParent(){
		return parent;
	}

	/**
	 * Manager����Ԃ��܂��B
	 * @return Manager��
	 */
	public String getManager(){
		return manager;
	}

	/**
	 * ReportedAs����Ԃ��܂��B
	 * @return ReportedAs��
	 */
	public String getReportedAs(){
		return reportedAs;
	}

	/**
	 * �A�g���r���[�g��Ԃ��܂��B
	 *
	 * @return		NxAttributeWrapper
	 */
	public NxAttributeWrapper[] getNxAttributes(){
		return attr;
	}

	/**
	 * �K�v�ȃA�g���r���[�g��Ԃ��܂��B
	 * �Ăяo�����Ŏw�肵��Object�^�ɃL���X�g���Ă��������B
	 * @param attrName �A�g���r���[�g��
	 * @return �A�g���r���[�g�̒l��Ԃ��܂��B�Y������A�g���r���[�g�����݂��Ȃ��ꍇ�A��̏ꍇnull��Ԃ��܂��B
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
