package NxCorba;

/**
 * ターゲットを生成します。
 *
 * @version $Revision: 1.5 $ $Date: 2002/03/29 13:48:12 $
 * @author $Author: inagashima $
 */
public class NxTarget{

	/** リレーションタイプ ConnectUp */
	public static final String CONNECT_UP	= "ConnectUp";
	/** リレーションタイプ ConnectDown */
	public static final String CONNECT_DOWN	= "ConnectDown";
	/** リレーションタイプ ContaindIn */
	public static final String CONTAINED_IN = "ContainedIn";
	/** リレーションタイプ Contains */
	public static final String CONTAINS		= "Contains";
	/** リレーションタイプ ManagedBy */
	public static final String MANAGED_BY	= "ManagedBy";
	/** リレーションタイプ Manages */
	public static final String MANAGES		= "Manages";


	/** スコープタイプ ThisObjectOnly */
	public static final int THIS_OBJECT_ONLY									= 0;
	/** スコープタイプ DirectlyRelatedObjectsOnly */
	public static final int DIRECTLY_RELATED_OBJECTS_ONLY						= 1;
	/** スコープタイプ ObjectsRelatedAtLevel */
	public static final int OBJECTS_RELATED_AT_LEVEL							= 2;
	/** スコープタイプ ThisObjectAndRelatedObjectsToLevel */
	public static final int THIS_OBJECT_AND_RELATED_OBJECTS_TO_LEVEL			= 3;
	/** スコープタイプ AllRelatedObjects */
	public static final int ALL_RELATED_OBJECTS									= 4;


	/** ターゲット */
	private NetExpert_v1.Target target;

	/**
	 * NetExpert_v1.Targetからターゲットを生成します。
	 * @param target NetExpert_v1.Target
	 */
	protected NxTarget(NetExpert_v1.Target target){
		this.target = target;
	}

	/**
	 * 空のターゲットを生成します。
	 */
	public NxTarget(){
		
	}

	/**
	 * Object用のターゲットを生成します。
	 * @param baseId BaseObject
	 */
	public NxTarget(String baseId){
		target = new NetExpert_v1.Target();
		target.nxobjectId(EUCConv.encode(baseId));
	}

	/**
	 * Objects用のターゲットを生成します。
	 * @param baseId BaseObjects
	 */
	public NxTarget(String[] baseId){
		target = new NetExpert_v1.Target();
		String[] encodedId = new String[baseId.length];
		for (int i = 0; i < baseId.length; i++) {
			encodedId[i] = EUCConv.encode(baseId[i]);
		}
		target.nxobjectIds(encodedId);
	}

	/**
	 * リレーション用のターゲットを生成します。
	 * @param baseId BaseObject
	 * @param relateType リレーションタイプ
	 * @param scopeType スコープタイプ
	 * @param scopeLevel スコープレベル
	 * @param filter フィルタ
	 */
	public NxTarget(String baseId, String relateType, int scopeType, short scopeLevel, String filter){
		target = new NetExpert_v1.Target();
		NetExpert_v1.ObjectGroup group = new NetExpert_v1.ObjectGroup();
		group.nxobjectId = EUCConv.encode(baseId);
		group.relationship = EUCConv.encode(relateType);
		group.nxfilter = EUCConv.encode(filter);
		group.nxscope = new NetExpert_v1.Scope();

/*
		if(scopeType.equals("ThisObjectOnly")){
			group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectOnly;
		}else if(scopeType.equals("DirectlyRelatedObjectsOnly")){
			group.nxscope.type = NetExpert_v1.ScopeType.DirectlyRelatedObjectsOnly;
		}else if(scopeType.equals("ObjectsRelatedAtLevel")){
			group.nxscope.type = NetExpert_v1.ScopeType.ObjectsRelatedAtLevel;
		}else if(scopeType.equals("ThisObjectAndRelatedObjectsToLevel")){
			group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectAndRelatedObjectsToLevel;
		}else if(scopeType.equals("AllRelatedObjects")){
			group.nxscope.type = NetExpert_v1.ScopeType.AllRelatedObjects;
		}
*/

		switch(scopeType){
			case 0:
				group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectOnly;
				break;
			case 1:
				group.nxscope.type = NetExpert_v1.ScopeType.DirectlyRelatedObjectsOnly;
				break;
			case 2:
				group.nxscope.type = NetExpert_v1.ScopeType.ObjectsRelatedAtLevel;
				break;
			case 3:
				group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectAndRelatedObjectsToLevel;
				break;
			case 4:
				group.nxscope.type = NetExpert_v1.ScopeType.AllRelatedObjects;
				break;
			default:
				group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectOnly;
		}

//		group.nxscope.type = scopeType;
		group.nxscope.level = scopeLevel;
		target.nxobjectGroup(group);
	}

	/**
	 * Object用のターゲットをセットします。
	 * @param baseId BaseObject
	 */
	public void setTarget(String baseId){
		target = new NetExpert_v1.Target();
		target.nxobjectId(EUCConv.encode(baseId));
	}

	/**
	 * Objects用のターゲットをセットします。
	 * @param baseId BaseObjects
	 */
	public void setTarget(String[] baseId){
		target = new NetExpert_v1.Target();
		String[] encodedId = new String[baseId.length];
		for (int i = 0; i < baseId.length; i++) {
			encodedId[i] = EUCConv.encode(baseId[i]);
		}
		target.nxobjectIds(encodedId);
	}

	/**
	 * リレーション用のターゲットをセットします。
	 * @param baseId BaseObject
	 * @param relateType リレーションタイプ
	 * @param scopeType スコープタイプ
	 * @param scopeLevel スコープレベル
	 * @param filter フィルタ
	 */
	public void setTarget(String baseId, String relateType, int scopeType, short scopeLevel, String filter){
		target = new NetExpert_v1.Target();
		NetExpert_v1.ObjectGroup group = new NetExpert_v1.ObjectGroup();
		group.nxobjectId = EUCConv.encode(baseId);
		group.relationship = EUCConv.encode(relateType);
		group.nxfilter = EUCConv.encode(filter);
		group.nxscope = new NetExpert_v1.Scope();
		switch(scopeType){
			case 0:
				group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectOnly;
				break;
			case 1:
				group.nxscope.type = NetExpert_v1.ScopeType.DirectlyRelatedObjectsOnly;
				break;
			case 2:
				group.nxscope.type = NetExpert_v1.ScopeType.ObjectsRelatedAtLevel;
				break;
			case 3:
				group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectAndRelatedObjectsToLevel;
				break;
			case 4:
				group.nxscope.type = NetExpert_v1.ScopeType.AllRelatedObjects;
				break;
			default:
				group.nxscope.type = NetExpert_v1.ScopeType.ThisObjectOnly;
		}
//		group.nxscope.type = scopeType;
		group.nxscope.level = scopeLevel;
		target.nxobjectGroup(group);
	}

	/**
	 * ターゲットを返します。
	 * @return ターゲット
	 */
	public NetExpert_v1.Target getTarget(){
		return target;
	}

	/**
	 * 設定したターゲットの詳細を返します。
	 *
	 * @return ターゲットの詳細
	 */
	public String printTarget(){
		StringBuffer sb = new StringBuffer(128);
		switch (target.discriminator().value()){
			case NetExpert_v1.TargetType._NxObject:
				sb.append(target.nxobjectId());
				break;
			case NetExpert_v1.TargetType._NxObjects:
				for(int i = 0; i < target.nxobjectIds().length; i++){
					sb.append(target.nxobjectIds());
					sb.append(",");
				}
				break;
			case NetExpert_v1.TargetType._Relationship:
				sb.append(target.nxobjectGroup().nxobjectId);
				sb.append("(RelationShip = ").append(target.nxobjectGroup().relationship).append(")");
				sb.append("(ScopeType = ");
				switch (target.nxobjectGroup().nxscope.type.value()){
					case NetExpert_v1.ScopeType._ThisObjectOnly:
						sb.append("ThisObjectOnly").append(")");
						break;
					case NetExpert_v1.ScopeType._DirectlyRelatedObjectsOnly:
						sb.append("DirectlyRelatedObjectsOnly").append(")");
						break;
					case NetExpert_v1.ScopeType._ObjectsRelatedAtLevel:
						sb.append("ObjectsRelatedAtLevel").append(")");
						break;
					case NetExpert_v1.ScopeType._ThisObjectAndRelatedObjectsToLevel:
						sb.append("ThisObjectAndRelatedObjectsToLevel").append(")");
						break;
					case NetExpert_v1.ScopeType._AllRelatedObjects:
						sb.append("AllRelatedObjects").append(")");
						break;
					default:
						sb.append("UNKNOWN").append(")");
				}
				sb.append("(ScopeLevel = ").append((int)target.nxobjectGroup().nxscope.level).append(")");
				sb.append("(Filter = ").append(target.nxobjectGroup().nxfilter).append(")");
				break;
			default:
				sb.append("UNKNOWN");
		}
		return sb.toString();
	}
}
