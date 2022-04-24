package WingsUtil;
/**
 * 入力された文字のHTMLタグを無効にする機能
 * */
public class WingsUtil {
	public static String htmlEscape(String strVal) {
		StringBuffer strResult = new StringBuffer();
		//一文字ずつ文字列を確認する
		for (int i = 0; i < strVal.length(); i++) {
			//case文に該当する文字列を無効化する
			switch (strVal.charAt(i)) {
			case '&':
				strResult.append("&amp");
				break;
			case '<':
				strResult.append("&lt");
				break;
			case '>':
				strResult.append("&gt");
				break;
			case '　':
			case ' ':
				break;
			default:
				strResult.append(strVal.charAt(i));
				break;
			}
		}
		return strResult.toString();
	}
}