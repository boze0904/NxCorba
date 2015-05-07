package NxCorba;

/**
 * NetExpertのAttributeを定義したクラスです。<BR>
 * NetExpertの型変換を行うため、タイプにはNxAttributeConstantsインターフェースを参照して下さい。
 * @version $Revision: 1.4 $ $Date: 2002/06/10 06:01:12 $
 * @author $Author: inagashima $
 */
public class NxAttributeWrapper implements NxAttributeConstants{

	/** NetExpert_v1.NxAttribute */
	protected NetExpert_v1.NxAttribute attr;
	
	/** 値が不明な定数です。*/
	private final static String UNKNOWN = "UNKNOWN";

	/**
	 * 空のNxAttributeを生成します。
	 */
	public NxAttributeWrapper(){
		//処理無し
	}

	/**
	 * NetExpertのアトリビュートを生成します。<BR>
	 * attrメンバからアクセスできます。
	 * @param id アトリビュート名
	 * @param type アトリビュートタイプ
	 * @param value アトリビュートの値
	 */
	public NxAttributeWrapper(String id, String type, Object value){
		setNxAttribute(id, type, value);
	}

	/**
	 * NetExpertのアトリビュートを生成します。
	 * @param attribute NetExpert_v1.NxAttribute NetExpertアトリビュート
	 */
	protected NxAttributeWrapper(NetExpert_v1.NxAttribute attribute){
		attr = attribute;
	}

	/**
	 * 指定したアトリビュートかどうかを返します。
	 * @param id アトリビュート名
	 * @return 指定したアトリビュートならば、true そうでなければ、false
	 */
	public boolean isNxAttribute(String id){
		if(attr.id.equals(id)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * アトリビュートをセットします。<BR>
	 * @param id アトリビュート名
	 * @param type アトリビュートタイプ
	 * @param value アトリビュートの値
	 */
	public void setNxAttribute(String id, String type, Object value){
		
		attr = new NetExpert_v1.NxAttribute();
		attr.id = id;
		attr.value = new NetExpert_v1.AttributeValue();

		if(type.equals(NxAttributeConstants.BOOLEAN)){
			attr.type = NxAttributeConstants.BOOLEAN;
			attr.value.boolValue(((Boolean)value).booleanValue());
		}else if(type.equals(NxAttributeConstants.LONG)){
			attr.type = NxAttributeConstants.LONG;
			attr.value.longValue(((Number)value).intValue());
		}else if(type.equals(NxAttributeConstants.DOUBLE)){
			attr.type = NxAttributeConstants.DOUBLE;
			attr.value.doubleValue(((Number)value).doubleValue());
		}else if(type.equals(NxAttributeConstants.ENUM)){
			attr.type = NxAttributeConstants.ENUM;
			attr.value.enumValue(EUCConv.encode((String)value));
		}else if(type.equals(NxAttributeConstants.STRING)){
			attr.type = NxAttributeConstants.STRING;
			attr.value.stringValue(EUCConv.encode((String)value));
		}else if(type.equals(NxAttributeConstants.MO)){
			attr.type = NxAttributeConstants.MO;
			attr.value.moValue(EUCConv.encode((String)value));
		}else if(type.equals(NxAttributeConstants.SEQUENCE_OF_LONG)){
			attr.type = NxAttributeConstants.SEQUENCE_OF_LONG;
			long[] longsValue = (long[])value;
			NetExpert_v1.LongSetOfElement[] longs
					= new NetExpert_v1.LongSetOfElement[longsValue.length];
			for (int i = 0; i < longs.length; i++) {
				longs[i] = new NetExpert_v1.LongSetOfElement(i, (int)longsValue[i]);
			}
			attr.value.longsValue(longs);
		}else if(type.equals(NxAttributeConstants.SEQUENCE_OF_DOUBLE)){
			attr.type = NxAttributeConstants.SEQUENCE_OF_DOUBLE;
			double[] doublesValue = (double[])value;
			NetExpert_v1.DoubleSetOfElement[] doubles
					= new NetExpert_v1.DoubleSetOfElement[doublesValue.length];
			for (int i = 0; i < doubles.length; i++) {
				doubles[i] = new NetExpert_v1.DoubleSetOfElement(i, doublesValue[i]);
			}
			attr.value.doublesValue(doubles);
		}else if(type.equals(NxAttributeConstants.SEQUENCE_OF_ENUM)){
			attr.type = NxAttributeConstants.SEQUENCE_OF_ENUM;
			String[] enumsValue = (String[])value;
			NetExpert_v1.StringSetOfElement[] enums
					= new NetExpert_v1.StringSetOfElement[enumsValue.length];
			for (int i = 0; i < enums.length; i++) {
				enums[i] = new NetExpert_v1.StringSetOfElement(i, EUCConv.encode(enumsValue[i]));
			}
			attr.value.enumsValue(enums);
		}else if(type.equals(NxAttributeConstants.SEQUENCE_OF_STRING)){
			attr.type = NxAttributeConstants.SEQUENCE_OF_STRING;
			String[] stringsValue = (String[])value;
			NetExpert_v1.StringSetOfElement[] strings
					= new NetExpert_v1.StringSetOfElement[stringsValue.length];
			for (int i = 0; i < strings.length; i++) {
				strings[i] = new NetExpert_v1.StringSetOfElement(i, EUCConv.encode(stringsValue[i]));
			}
			attr.value.stringsValue(strings);
		}else if(type.equals(NxAttributeConstants.SEQUENCE_OF_MO)){
			attr.type = NxAttributeConstants.SEQUENCE_OF_MO;
			String[] moValue = (String[])value;
			NetExpert_v1.StringSetOfElement[] mos
					= new NetExpert_v1.StringSetOfElement[moValue.length];
			for (int i = 0; i < mos.length; i++) {
				mos[i] = new NetExpert_v1.StringSetOfElement(i, EUCConv.encode(moValue[i]));
			}
			attr.value.mosValue(mos);
		}else{
			attr.type = "UNKNOWN_TYPE";
		}
	}

	/**
	 * 推奨されていません。getNxAttribute()メソッドと使用して下さい。<p>
	 * 指定されたアトリビュート名の値を返します。<BR>
	 * 呼び出し元で指定したObject型にキャストしてください。
	 * @param attrName アトリビュート名
	 * @retrun アトリビュートの値を返します。該当するアトリビュートが存在しない場合、空の場合nullを返します。
	 */
	public Object getNxAttribute(String attrName){
		if(isNxAttribute(attrName)){
			return getNxAttribute();
		}
		return null;
	}

	/**
	 * アトリビュートの値を返します。<BR>
	 * 呼び出し元で指定したObject型にキャストしてください。
	 * @retrun アトリビュートの値を返します。該当するアトリビュートが存在しない場合、空の場合nullを返します。
	 */
	public Object getNxAttribute(){
		// NetExpert_v1.AttributeValue
		NetExpert_v1.AttributeValue value = attr.value;

		return getNxAttribute(value);
	}

	/**
	 * アトリビュートの値を返します。<BR>
	 * 呼び出し元で指定したObject型にキャストしてください。
	 * @retrun アトリビュートの値を返します。該当するアトリビュートが存在しない場合、空の場合nullを返します。
	 */
	public static Object getNxAttribute(NetExpert_v1.AttributeValue value){
		// NetExpert_v1.AttributeValue
		int type = value.discriminator().value();

		// _NoValue
		if(type == NetExpert_v1.AttributeType._NoValue){
			return null;
		}
		// _NullType
		else
		if(type == NetExpert_v1.AttributeType._NullType){
			return null;
		}
		// _AnyType
		else
		if(type == NetExpert_v1.AttributeType._AnyType){
			return value.anyValue();
		}
		// _BoolType
		else
		if(type == NetExpert_v1.AttributeType._BoolType){
			return new Boolean(value.boolValue());
		}
		// _LongType
		else
		if(type == NetExpert_v1.AttributeType._LongType){
			return new Long(new Integer(value.longValue()).longValue());
		}
		// _ULongType
		else
		if(type == NetExpert_v1.AttributeType._ULongType){
			return new Long(new Integer(value.uLongValue()).longValue());
		}
		// _DoubleType
		else
		if(type == NetExpert_v1.AttributeType._DoubleType){
			return new Double(value.doubleValue());
		}
		// _Int64Type
		else
		if(type == NetExpert_v1.AttributeType._Int64Type){
			return EUCConv.decode(value.int64Value());
		}
		// _UInt64Type
		else
		if(type == NetExpert_v1.AttributeType._UInt64Type){
			return EUCConv.decode(value.uint64Value());
		}
		// _EnumType
		else
		if(type == NetExpert_v1.AttributeType._EnumType){
			return EUCConv.decode(value.enumValue());
		}
		// _StringType
		else
		if(type == NetExpert_v1.AttributeType._StringType){
			return EUCConv.decode(value.stringValue());
		}
		// _OctetStrType
		else
		if(type == NetExpert_v1.AttributeType._OctetStrType){
			return value.octetStrValue();
		}
		// _MOType
		else
		if(type == NetExpert_v1.AttributeType._MOType){
			String m = value.moValue();
			if(m.equals(UNKNOWN)){
				m = "";
			}
			return EUCConv.decode(m);
		}
		// _NullsType
		else
		if(type == NetExpert_v1.AttributeType._NullsType){
			NetExpert_v1.LongSetOfElement[] element = value.nullsValue();
			long[] elementValue = new long[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = new Integer(element[i].value).longValue();
			}
			return elementValue;
		}
		// _BoolsType
		else
		if(type == NetExpert_v1.AttributeType._BoolsType){
			NetExpert_v1.BoolSetOfElement[] element = value.boolsValue();
			boolean[] elementValue = new boolean[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = element[i].value;
			}
			return elementValue;
		}
		// _LongsType
		else
		if(type == NetExpert_v1.AttributeType._LongsType){
			NetExpert_v1.LongSetOfElement[] element = value.longsValue();
			long[] elementValue = new long[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = new Integer(element[i].value).longValue();
			}
			return elementValue;
		}
		// _ULongsType
		else
		if(type == NetExpert_v1.AttributeType._ULongsType){
			NetExpert_v1.ULongSetOfElement[] element = value.uLongsValue();
			long[] elementValue = new long[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = new Integer(element[i].value).longValue();
			}
			return elementValue;
		}
		// _DoublesType
		else
		if(type == NetExpert_v1.AttributeType._DoublesType){
			NetExpert_v1.DoubleSetOfElement[] element = value.doublesValue();
			double[] elementValue = new double[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = element[i].value;
			}
			return elementValue;
		}
		// _Int64sType
		else
		if(type == NetExpert_v1.AttributeType._Int64sType){
			NetExpert_v1.StringSetOfElement[] element = value.int64sValue();
			String[] elementValue = new String[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = EUCConv.decode(element[i].value);
			}
			return elementValue;
		}
		// _UInt64sType
		else
		if(type == NetExpert_v1.AttributeType._UInt64sType){
			NetExpert_v1.StringSetOfElement[] element = value.uint64sValue();
			String[] elementValue = new String[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = EUCConv.decode(element[i].value);
			}
			return elementValue;
		}
		// _EnumsType
		else
		if(type == NetExpert_v1.AttributeType._EnumsType){
			NetExpert_v1.StringSetOfElement[] element = value.enumsValue();
			String[] elementValue = new String[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = EUCConv.decode(element[i].value);
			}
			return elementValue;
		}
		// _StringsType
		else
		if(type == NetExpert_v1.AttributeType._StringsType){
			NetExpert_v1.StringSetOfElement[] element = value.stringsValue();
			String[] elementValue = new String[element.length];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = EUCConv.decode(element[i].value);
			}
			return elementValue;
		}
		// _OctetStrsType
		else
		if(type == NetExpert_v1.AttributeType._OctetStrsType){
			NetExpert_v1.OctetStrSetOfElement[] element = value.octetStrsValue();
			byte[][] elementValue = new byte[element.length][];
			for(int i = 0; i < elementValue.length; i++){
				elementValue[i] = element[i].value;
			}
			return elementValue;
		}
		// _MOsType
		else
		if(type == NetExpert_v1.AttributeType._MOsType){
			NetExpert_v1.StringSetOfElement[] element = value.mosValue();
			String[] elementValue = new String[element.length];
			for(int i = 0; i < elementValue.length; i++){
				String m = element[i].value;
				if(m.equals(UNKNOWN)){
					m = "";
				}
				elementValue[i] = EUCConv.decode(m);
			}
			return elementValue;
		}

		// ここにくることはありえないが...
		return null;
	}

	/**
	 * アトリビュートの内容を表示する。
	 * @return 属性の文字列表現
	 */
	public String printNxAttribute(){
		StringBuffer sb = new StringBuffer(128);
		// IDと型
		sb.append(attr.id).append("(").append(attr.type).append(") = ");
		// NetExpert_v1.AttributeValue
		NetExpert_v1.AttributeValue value = attr.value;
		int type = value.discriminator().value();

		// _NoValue
		if(type == NetExpert_v1.AttributeType._NoValue){
			sb.append("");
		}
		// _NullType
		else
		if(type == NetExpert_v1.AttributeType._NullType){
			sb.append("");
		}
		// _AnyType
		else
		if(type == NetExpert_v1.AttributeType._AnyType){
			sb.append(value.anyValue());
		}
		// _BoolType
		else
		if(type == NetExpert_v1.AttributeType._BoolType){
			sb.append(value.boolValue());
		}
		// _LongType
		else
		if(type == NetExpert_v1.AttributeType._LongType){
			sb.append(value.longValue());
		}
		// _ULongType
		else
		if(type == NetExpert_v1.AttributeType._ULongType){
			sb.append(value.uLongValue());
		}
		// _DoubleType
		else
		if(type == NetExpert_v1.AttributeType._DoubleType){
			sb.append(value.doubleValue());
		}
		// _Int64Type
		else
		if(type == NetExpert_v1.AttributeType._Int64Type){
			sb.append(EUCConv.decode(value.int64Value()));
		}
		// _UInt64Type
		else
		if(type == NetExpert_v1.AttributeType._UInt64Type){
			sb.append(EUCConv.decode(value.uint64Value()));
		}
		// _EnumType
		else
		if(type == NetExpert_v1.AttributeType._EnumType){
			sb.append(EUCConv.decode(value.enumValue()));
		}
		// _StringType
		else
		if(type == NetExpert_v1.AttributeType._StringType){
			sb.append(EUCConv.decode(value.stringValue()));
		}
		// _OctetStrType
		else
		if(type == NetExpert_v1.AttributeType._OctetStrType){
			byte[] element = value.octetStrValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(element[i]);
			}
			sb.append("}");
		}
		// _MOType
		else
		if(type == NetExpert_v1.AttributeType._MOType){
			sb.append(EUCConv.decode(value.moValue()));
		}
		// _NullsType
		else
		if(type == NetExpert_v1.AttributeType._NullsType){
			NetExpert_v1.LongSetOfElement[] element = value.nullsValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(element[i].value);
			}
			sb.append("}");
		}
		// _BoolsType
		else
		if(type == NetExpert_v1.AttributeType._BoolsType){
			NetExpert_v1.BoolSetOfElement[] element = value.boolsValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(element[i].value);
			}
			sb.append("}");
		}
		// _LongsType
		else
		if(type == NetExpert_v1.AttributeType._LongsType){
			NetExpert_v1.LongSetOfElement[] element = value.longsValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(element[i].value);
			}
			sb.append("}");
		}
		// _ULongsType
		else
		if(type == NetExpert_v1.AttributeType._ULongsType){
			NetExpert_v1.ULongSetOfElement[] element = value.uLongsValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(element[i].value);
			}
			sb.append("}");
		}
		// _DoublesType
		else
		if(type == NetExpert_v1.AttributeType._DoublesType){
			NetExpert_v1.DoubleSetOfElement[] element = value.doublesValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(element[i].value);
			}
			sb.append("}");
		}
		// _Int64sType
		else
		if(type == NetExpert_v1.AttributeType._Int64sType){
			NetExpert_v1.StringSetOfElement[] element = value.int64sValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(EUCConv.decode(element[i].value));
			}
			sb.append("}");
		}
		// _UInt64sType
		else
		if(type == NetExpert_v1.AttributeType._UInt64sType){
			NetExpert_v1.StringSetOfElement[] element = value.uint64sValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(EUCConv.decode(element[i].value));
			}
			sb.append("}");
		}
		// _EnumsType
		else
		if(type == NetExpert_v1.AttributeType._EnumsType){
			NetExpert_v1.StringSetOfElement[] element = value.enumsValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(EUCConv.decode(element[i].value));
			}
			sb.append("}");
		}
		// _StringsType
		else
		if(type == NetExpert_v1.AttributeType._StringsType){
			NetExpert_v1.StringSetOfElement[] element = value.stringsValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(EUCConv.decode(element[i].value));
			}
			sb.append("}");
		}
		// _OctetStrsType
		else
		if(type == NetExpert_v1.AttributeType._OctetStrsType){
			NetExpert_v1.OctetStrSetOfElement[] element = value.octetStrsValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				byte[] b_element = element[i].value;
				for(int j = 0; j < b_element.length; j++){
					sb.append(b_element[j]);
					if(j != b_element.length - 1){
						sb.append(",");
					}
				}
			}
			sb.append("}");
		}
		// _MOsType
		else
		if(type == NetExpert_v1.AttributeType._MOsType){
			NetExpert_v1.StringSetOfElement[] element = value.mosValue();
			sb.append("{");
			for(int i = 0; i < element.length; i++){
				sb.append("[" + i + "]");
				sb.append(EUCConv.decode(element[i].value));
			}
			sb.append("}");
		}

		return sb.toString();
	}

}
