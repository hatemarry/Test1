import java.util.*;
import java.lang.*;

public class CommonUtils {

// String replace.. 
public static String Replace( String cont, String src, String des ) {
	StringBuffer buffer = new StringBuffer(); 
	int src_len = src.length(); 
	int con_len = cont.length(); 
	
	for( int i=0; i<con_len; i++ ) {
		if( i > con_len-src_len ) {
			buffer.append( cont.substring(i) ); 
			break; 
		}
		
		String substr = cont.substring(i, i+src_len); 
		if( substr.equals(src) ) { 
			buffer.append(des); 
			i += (src_len-1); 
		} else {
			buffer.append( cont.substring(i, i+1) ); 
		}
	}
	
	return new String( buffer ); 
	
}

// src에서 delim으로 구분하여 Token을 반환하고, src를 나머지로 대치한다. 
public static String GetToken(StringBuffer srcBuf, String delim, boolean isTrim ) {
	int	nPos = -1;
	String retStr;
	String chkStr = new String(srcBuf); 
	
	if( chkStr.trim().equals("") || delim.equals("") )	{
		srcBuf = srcBuf.delete(0, srcBuf.length());
		if( isTrim ) return "";
		else return chkStr; 
	}

	nPos = srcBuf.toString().indexOf( delim );

	if( nPos == -1 ) {
		retStr = srcBuf.toString();
		srcBuf = srcBuf.delete(0, srcBuf.length());
	} else {
		retStr = srcBuf.substring( 0 , nPos );
		srcBuf = srcBuf.delete(0, nPos + delim.length() );
	}

	if( isTrim ) return retStr.trim();
	else return retStr; 
}

public static String GetToken(StringBuffer srcBuf, String delim) {
	return GetToken( srcBuf, delim, true ); 
}

// String을 대문자로 만든다. 상수문자 제외. 
public static String MakeQueryUpper( String src ) {
	String token = ""; 
	StringBuffer srcBuf = new StringBuffer( src.trim() ); 
	StringBuffer retBuf = new StringBuffer(); 
	boolean isLastComma = false; 
	if( src.trim().substring( src.trim().length() - 1 ).equals("\'") ) isLastComma = true; 
	
	boolean isIn = false; 
	while( !( token = GetToken(srcBuf, "\'", false)).equals("") ) {
		if( !isIn ) {
			retBuf.append( token.toUpperCase() ); 
		} else retBuf.append( token ); 
		
		if( srcBuf.toString().trim().equals("") ) { 
			if( isLastComma ) retBuf.append("\'"); 
			break; 
		} else retBuf.append("\'"); 
		isIn = !isIn; 
	}
	
	return retBuf.toString(); 
	
}

public static int CountChar(String source, char ch) {
	int lastPos = 0; 
	int count = 0; 
	for( int i=0; i<source.length(); ) {
		lastPos = source.indexOf(ch, lastPos+1); 
		if( lastPos >= 0 ) { 
			count++;
			i = lastPos; 
		} else break; 
	}
	return count; 
}

public static int countDelim(String cont, String delim ) {
	
	int delimLen = delim.length(); 
	int cnt = 0; 
	
	while( cont.length() > delimLen ) {
		int pos = cont.indexOf(delim); 
		if( pos >= 0 ) {
			cnt++; 
			cont = cont.substring(pos + delimLen); 
		} else return cnt; 
	}
	return cnt; 
	
}


}
