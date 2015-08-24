import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;



public class Test {

	public static void main(String args[]) {
	//変更
//		String a = "JAVA JAVA";
//		System.out.println(a.replaceAll("(JAVA)", change("$1 a")));
		// shift_jisエンコード
		String b = "%83E%83B%83L%83y%83f%83B%83A";
		// utf8エンコード
		String c = "%E3%82%A6%E3%82%A3%E3%82%AD%E3%83%9A%E3%83%87%E3%82%A3%E3%82%A2";
/*		try {
			String inputTextByteenc = new String(b.getBytes("SJIS"), "SJIS");
			System.out.println(b.equals(inputTextByteenc));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}*/
		URLCodec codec = new URLCodec();
		//変更
		try {
			String b_decode = codec.decode(b, "UTF-8");
			String c_decode = codec.decode(c, "UTF-8");
			System.out.println(b_decode);
			System.out.println(c_decode);
				String b_inputTextByteenc = new String(b_decode.getBytes("SJIS"), "SJIS");
//				String b_inputTextByteenc = new String(b_decode.getBytes("UTF-8"), "UTF-8");
				System.out.println(b_decode.equals(b_inputTextByteenc));
				String c_inputTextByteenc = new String(c_decode.getBytes("SJIS"), "SJIS");
//				String c_inputTextByteenc = new String(c_decode.getBytes("UTF-8"), "UTF-8");
				System.out.println(c_decode.equals(c_inputTextByteenc));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (DecoderException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
