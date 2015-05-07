package NxCorba;

/**
 * EUCとUnicodeの変換を行う。<BR>
 *
 * @version $Revision: 1.2 $ $Date: 2002/03/15 11:08:49 $
 * @author $Author: inagashima $
 */
public class EUCConv {

	/** OSの種別定数 */
	private static boolean forWindows = true;
	/** ～コードのUniCode */
	public static char wave = 0xff5e;  // '～'記号のUniCode(UNIXとPCではコードが違うため)

	/**
	 * OS種別を設定する。
	 * @param windows trueの場合、Windows falseの場合、Unix
	 */
	public static void setForWindows(boolean windows) {
		forWindows = windows;
		// '～'記号のUniCode値を設定する
		if (forWindows) {
			wave = (char)0xff5e;
		}
		else {
			wave = (char)0x301c;
		}
	}

	/**
	 * OS種別を取得する。
	 *
	 * @return trueの場合、Windows falseの場合、Unix
	 */
	public static boolean getForWindows() {
		return forWindows;
	}

	/**
	 * EUCからUnicodeに戻す。
	 *
	 * @param str EUC文字列
	 * @return Unicodeに変換された文字列
	 */
	public static String decode(String str){
		try{
			str =  new String(str.getBytes("8859_1"),"EUCJIS");
		}catch(Exception e){
			return null;
		}

		StringBuffer sb = new StringBuffer();
		char c;
		for(int i=0;i<str.length();i++){
			c = str.charAt(i);
			switch(c){
//				case 0x005c: c = 0xff3c; break;	//半角バックスラッシュ⇔全角バックスラッシュ
				case 0x301c: c = 0xff5e; break;
				case 0x2016: c = 0x2225; break;
				case 0x2212: c = 0xff0d; break;
				case 0x00a2: c = 0xffe0; break;
				case 0x00a3: c = 0xffe1; break;
				case 0x00ac: c = 0xffe2; break;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * EUC文字列に変換する。
	 * @param str EUCに変換するUnicode文字列
	 * @return EUCに変換された文字列
	 */
	public static String encode(String str){
		StringBuffer sb = new StringBuffer();
		char c;
		for(int i=0;i<str.length();i++){
			c = str.charAt(i);
			switch(c){
//				case 0xff3c: c = 0x005c; break;  // 半角バックスラッシュ⇔全角バックスラッシュ
				case 0xff5e: c = 0x301c; break;  // ～
				case 0x2225: c = 0x2016; break;  // ∥
				case 0xff0d: c = 0x2212; break;  // －
				case 0xffe0: c = 0x00a2; break;  // ￠
				case 0xffe1: c = 0x00a3; break;  // ￡
				case 0xffe2: c = 0x00ac; break;  // ￢
			}
			sb.append(c);
		}

		str = sb.toString();
		try{
			return new String(str.getBytes("EUCJIS"),"8859_1");
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * EUC文字列に変換してその長さを返す。
	 * @param str 計測する文字列
	 * @return 文字数
	 */
	public static int eucLength(String str){
		try{
			return encode(str).length();
		}catch(Exception e){
			return -1;
		}
	}

	public static void printCode(String str){
		for (int i=0;i<str.length();i++)
			System.out.println(Integer.toHexString((int)str.charAt(i))+"_");
	}

	public static void main(String[] argv){
		if (argv.length != 1){
			System.exit(0);
		}
		System.out.println(argv[0]);
		EUCConv.printCode(argv[0]);
		String enc = EUCConv.encode(argv[0]);
		EUCConv.printCode(enc);
		String dec = EUCConv.decode(enc);
		System.out.println(dec);
		EUCConv.printCode(dec);
//		char[] c = new char[1];
//		c[0] = 0x005c;
//		String s = new String(c);
//		System.out.println("Arg=" + s);
//		EUCConv.printCode(s);

//		String encArg = EUCConv.encode(s);
//		System.out.println("encArg=" + encArg);
//		EUCConv.printCode(encArg);

//		String decArg = EUCConv.decode(s);
//		System.out.println("decArg=" + decArg);
//		EUCConv.printCode(decArg);

//		String dec = EUCConv.decode(encArg);
//		System.out.println("dec=" + dec);
//		EUCConv.printCode(dec);
	}
}
