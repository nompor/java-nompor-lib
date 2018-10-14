package com.nompor.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nompor.util.StringUtil;

/**
 * @author nompor
 * このクラスを利用するとCSV読み込み処理を利用することができます。
 * 引用符を指定することで、区切り文字を値に含めることもできます。
 *
 */
public class CSVReader implements Closeable{

	/**
	 * 列数をオリジナルの通りに取得するパラメータ
	 */
	public static final int READ_COLUMN_SIZE = -1;

	/**
	 * 列数を一行目に読み込んだ行に合わせるパラメータ
	 */
	public static final int FIRST_COLUMN_SIZE = -2;

	/**
	 * 列数を読み込み処理ごとの一行目に合わせるパラメータ
	 */
	public static final int READ_FIRST_COLUMN_SIZE = -3;

	/**
	 * 列数をヘッダに合わせるパラメータ
	 */
	public static final int HEADER_COLUMN_SIZE = -4;

	/**
	 * 列数一番大きい列数に合わせるパラメータ
	 */
	public static final int MAX_COLUMN_SIZE = -5;

	private final InputStreamFactory stream;
	private final String delimiter;
	private final String quote;
	private final String encode;
	private String lineFeed = null;
	private String[] fieldNameList;
	private boolean isHeader;
	private boolean isNonQuoteBlankToNull;
	private int maxRowCount = -1;
	private int maxRowDataCount = -1;
	private int maxColDataCount = -1;
	private int firstColDataCount = -1;
	private int rowCount = 0;
	private int rowDataCount = 0;
	private int bch = -1;
	private int readCharLen = -1;
	private int readCharCnt = -1;
	private char[] charBuffer = new char[2000];
	private BufferedReader br;
	private Pattern pattern;
	private LinkedList<String> tokenArray = new LinkedList<>();
	private ArrayList<StringBuilder> lineArray = new ArrayList<>();
	private IOException ioexception;


	/**
	 *
	 * @param stream 読み込みストリーム
	 * @param encode 文字エンコード
	 * @param delimiter 区切り文字
	 * @param quote 引用符
	 * @param isHeader trueなら1レコード目は読み飛ばします
	 * @param isNonQuoteBlankToNull 引用符なしの空文字をNULLにするかどうか
	 * @throws IOException
	 */
	public CSVReader(InputStreamFactory stream, String encode, String delimiter, String quote, boolean isHeader, boolean isNonQuoteBlankToNull) throws IOException {
		this.br = new BufferedReader(new InputStreamReader(stream.newInputStream(), encode));
		this.stream = stream;
		this.encode = encode;
		this.delimiter = delimiter;
		this.quote = quote;
		this.isHeader = isHeader;
		this.isNonQuoteBlankToNull = isNonQuoteBlankToNull;
		if ( delimiter == null || delimiter.equals("") ) throw new IllegalArgumentException("Delimiter IllegalArgument");
		String _deli = StringUtil.escapeRegex(delimiter);
		if ( quote == null || quote.equals("") ) {
			this.pattern = Pattern.compile(_deli+"|.*?(?="+_deli+")|(?<="+_deli+"){0,1}.*?$");
		} else {
			String _q1 = StringUtil.escapeRegex(quote);
			String dqReg = _deli+"|"+_q1;
			this.pattern = Pattern.compile(dqReg+"|.*?(?="+dqReg+")|(?<="+dqReg+"){0,1}.*?$");
		}
		if ( isHeader ) {
			String[] header = read();
			ArrayList<String> fieldNameArray = new ArrayList<>();
			if ( header != null ) {
				for ( String s : header ) {
					fieldNameArray.add(s);
				}
				fieldNameList = fieldNameArray.toArray(new String[fieldNameArray.size()]);
			}
		}
	}

	/**
	 *
	 * @param file 読み込みファイル
	 * @param encode 文字エンコード
	 * @param delimiter 区切り文字
	 * @param quote 引用符
	 * @param isHeader trueなら1レコード目は読み飛ばします
	 * @param isNonQuoteBlankToNull 引用符なしの空文字をNULLにするかどうか
	 * @throws IOException
	 */
	public CSVReader(File file, String encode, String delimiter, String quote, boolean isHeader, boolean isNonQuoteBlankToNull) throws IOException {
		this(() -> new FileInputStream(file), encode, delimiter, quote, isHeader, isNonQuoteBlankToNull);
	}

	public CSVReader(File file, String encode, String delimiter, String quote, boolean isHeader) throws IOException {
		this(file, encode, delimiter, quote, isHeader, true);
	}

	public CSVReader(File file, String encode) throws IOException {
		this(file,encode,",","\"",true);
	}

	public CSVReader(File file) throws IOException {
		this(file,Charset.defaultCharset().name());
	}

	/**
	 * ヘッダを保持するかを返します。
	 * @return
	 */
	public boolean isHeader(){
		return isHeader;
	}

	/**
	 * 引用符なしの空文字をNULLにするかどうかを返します。
	 * @return
	 */
	public boolean isNonQuoteBlankToNull(){
		return isNonQuoteBlankToNull;
	}

	/**
	 * 引用符を返します。
	 * @return
	 */
	public String getQuote(){
		return quote;
	}

	/**
	 * 区切り文字を返します。
	 * @return
	 */
	public String getDelimiter(){
		return delimiter;
	}

	/**
	 * 文字コードを返します。
	 * @return
	 */
	public String getEncode(){
		return encode;
	}

	/**
	 * このCSV解析器のクローズを実行します。
	 */
	@Override
	public void close() throws IOException {
		br.close();
	}

	/**
	 * このCSVのヘッダデータを返します。
	 * @return
	 */
	public String[] getHeader() {
		return fieldNameList;
	}

	/**
	 * ヘッダーありのCSVを読み込む場合に使用できます。
	 * CSV形式としての指定行を読み込み、ヘッダ文字列をキーとしてマッピングした、レコード配列を返します。
	 * @param limit 読み込むレコード数
	 * @return レコード配列
	 * @throws IOException
	 * @throws XCGLangException
	 */
	public ArrayList<LinkedHashMap<String, String>> readObject(int limit) throws IOException{
		ArrayList<LinkedHashMap<String, String>> dataArrayHash = new ArrayList<LinkedHashMap<String, String>>();
		for (  int i = 0;i < limit;i++  ) {
			LinkedHashMap<String, String> dataHash = readObject();
			if ( dataHash == null ) break;
			dataArrayHash.add(dataHash);
		}
		return dataArrayHash.size() > 0 ? dataArrayHash : null;
	}

	public LinkedHashMap<String, String> readObject() throws IOException{
		if ( !isHeader ) throw new IOException("ヘッダー読み込みを有効にしてください。");
		String[] list = read();
		if ( list == null ) return null;

		LinkedHashMap<String, String> dataHash = new LinkedHashMap<String, String>();
		for (int j=0; j<fieldNameList.length; j++) {
			String value = j < list.length ? list[j] : null;
			String head = fieldNameList[j];
			if ( head == null || head.equals("") ) continue;
			dataHash.put(head, value);
		}
		return dataHash;
	}

	/**
	 * CSV形式としてのlimitデータ行を読み込み、配列にして返します。
	 * arraySizeは項目の長さを表します。
	 * @return CSV形式としての1データ行を区切り文字で区切った文字配列
	 * @throws IOException
	 * @throws XCGLangException
	 */
	public String[][] read(int limit, int colSize) throws IOException{
		if ( limit > 0 ) {
			ArrayList<String[]> arr = new ArrayList<>();
			switch ( colSize ) {
				case READ_COLUMN_SIZE:
					{
						for ( int r = 0;r < limit;r++ ) {
							ArrayList<StringBuilder> colArray = _readLineToArray();
							if ( colArray == null ) break;
							String[] s = _toArray(colArray);
							arr.add(s);
						}
					}
					break;
				case HEADER_COLUMN_SIZE:
					{
						if ( !isHeader ) throw new IOException("ヘッダの読み込みを有効にしてください。");
						int size = fieldNameList.length;
						for ( int r = 0;r < limit;r++ ) {
							ArrayList<StringBuilder> colArray = _readLineToArray();
							if ( colArray == null ) break;
							_fitArray(colArray, size);
							String[] s = _toArray(colArray);
							arr.add(s);
						}
					}
					break;
				case FIRST_COLUMN_SIZE:
					{
						for ( int r = 0;r < limit;r++ ) {
							ArrayList<StringBuilder> colArray = _readLineToArray();
							if ( colArray == null ) break;
							int size = this.firstColDataCount;
							_fitArray(colArray, size);
							String[] s = _toArray(colArray);
							arr.add(s);
						}
					}
					break;
				case READ_FIRST_COLUMN_SIZE:
					{
						int size = -1;
						for ( int r = 0;r < limit;r++ ) {
							ArrayList<StringBuilder> colArray = _readLineToArray();
							if ( colArray == null ) break;
							if ( size == -1 ) size = colArray.size();
							_fitArray(colArray, size);
							String[] s = _toArray(colArray);
							arr.add(s);
						}
					}
					break;
				case MAX_COLUMN_SIZE:
					{
						if ( this.maxColDataCount == -1 ) this.loadInfo();
						int size = this.maxColDataCount;
						for ( int r = 0;r < limit;r++ ) {
							ArrayList<StringBuilder> colArray = _readLineToArray();
							if ( colArray == null ) break;
							_fitArray(colArray, size);
							String[] s = _toArray(colArray);
							arr.add(s);
						}
					}
					break;
				default:
					{
						for ( int r = 0;r < limit;r++ ) {
							ArrayList<StringBuilder> colArray = _readLineToArray();
							if ( colArray == null ) break;
							_fitArray(colArray, colSize);
							String[] s = _toArray(colArray);
							arr.add(s);
						}
					}
					break;
			}
			return arr.size() == 0 ? null : arr.toArray(new String[arr.size()][]);
		}
		return null;
	}

	/**
	 * CSV形式としてのlimitデータ行を読み込み、配列にして返します。
	 * @return CSV形式としての1データ行を区切り文字で区切った文字配列
	 * @throws IOException
	 * @throws XCGLangException
	 */
	public String[][] read(int limit) throws IOException{
		return read(limit, isHeader ? HEADER_COLUMN_SIZE : READ_COLUMN_SIZE);
	}

	/**
	 * CSV形式としての1データ行を読み込み、配列にして返します。
	 * @return CSV形式としての1データ行を区切り文字で区切った文字配列
	 * @throws IOException
	 * @throws XCGLangException
	 */
	public String[] read() throws IOException{
		ArrayList<StringBuilder> lineArray = _readLineToArray();
		if ( lineArray == null ) return null;
		String[] result = _toArray(lineArray);
		return result;
	}

	/**
	 * このCSVの詳細情報を読み込みます。
	 * @throws IOException
	 * @throws XCGLangException
	 */
	public void loadInfo() throws IOException{
		int maxRowCount = 0;
		int maxRowDataCount = 0;
		int maxColDataCount = 0;
		try(CSVReader reader = new CSVReader(stream, encode, delimiter, quote, isHeader, isNonQuoteBlankToNull)){
			ArrayList<?> arr = null;
			while((arr=reader._readLineToArray())!=null){
				maxColDataCount = Math.max(maxColDataCount, arr.size());
				arr.clear();
			}
			maxRowDataCount = reader.rowDataCount;
			maxRowCount = reader.rowCount;
			this.firstColDataCount = reader.firstColDataCount;
		}
		this.maxRowCount = maxRowCount;
		this.maxRowDataCount = maxRowDataCount;
		this.maxColDataCount = maxColDataCount;
	}

	/**
	 * このCSVファイルの行数を返します。
	 * このメソッドはloadInfoを呼び出した後に使用可能となります。
	 * @return
	 */
	public int getMaxRowCount(){
		if ( maxRowCount == -1 ) this._loadInfo();
		return maxRowCount;
	}

	/**
	 * このCSVファイルのデータセット数を返します。
	 * このメソッドはloadInfoを呼び出した後に使用可能となります。
	 * @return
	 */
	public int getMaxRowDataCount(){
		if ( maxRowDataCount == -1 ) this._loadInfo();
		return maxRowDataCount;
	}

	/**
	 * このCSVファイルの読み込み中の行数を返します。
	 * @return
	 */
	public int getRowCount(){
		return rowCount;
	}

	/**
	 * このCSVファイルの読み込みデータセット数を返します。
	 * @return
	 */
	public int getRowDataCount(){
		return rowDataCount;
	}

	/**
	 * このCSVファイルの最大項目数を返します。
	 * @return
	 */
	public int getMaxColDataCount(){
		if ( maxColDataCount == -1 ) this._loadInfo();
		return maxColDataCount;
	}

	/**
	 * このCSVファイルの一番最初のデータ数を返します。
	 * @return
	 */
	public int getFirstLineDataCount(){
		if ( firstColDataCount == -1 ) this._loadInfo();
		return firstColDataCount;
	}

	private void _loadInfo(){
		try{loadInfo();}catch(Exception e){}
	}

	private String[] _toArray(ArrayList<StringBuilder> colArray) throws IOException{
		String[] s = new String[colArray.size()];
		for ( int i = s.length - 1 ;i >= 0;i-- ) {
			StringBuilder sb = colArray.remove(i);
			if ( sb != null ) s[i] = sb.toString();
		}
		return s;
	}

	private void _fitArray(ArrayList<StringBuilder> colArray, int size) throws IOException{
		StringBuilder blank = isNonQuoteBlankToNull ? null : new StringBuilder();
		if ( colArray.size() > size ) {
			for ( int i = colArray.size() - 1;i >= size;i-- ) {
				colArray.remove(i);
			}
		} else if ( colArray.size() < size ) {
			for ( int i = colArray.size();i < size;i++ ) {
				colArray.add(blank);
			}
		}
	}

	private synchronized ArrayList<StringBuilder> _readLineToArray() throws IOException{
		if ( ioexception != null ) throw new IOException("前の解析でエラーが発生したため、これ以上解析できません。"+rowCount+"行目付近を確認してください。",ioexception);
		final String delimiter = this.delimiter;
		String nextLine = _readLine();
		if ( nextLine == null ) return null;

		// BOM付きUTF-8の時にBOMを取り除く
		boolean isUtf8 = ("utf8".equals(this.encode.toLowerCase()) || "utf-8".equals(this.encode.toLowerCase()) );
		if(isUtf8 && Integer.toHexString(nextLine.charAt(0)).equals("feff")) {
			nextLine = nextLine.substring(1);
		}

		rowCount++;
		rowDataCount++;
		_createTokenArray(nextLine);
		lineArray.add(_createColumnObject());
		while ( !tokenArray.isEmpty() ) {
			String token = tokenArray.removeFirst();
			if (token.equals(delimiter)) {
				lineArray.add(_createColumnObject());
			} else if (token.equals(quote)) {
				if ( lineArray.get(lineArray.size() - 1).length() > 0 ) {
					ioexception = new IOException("CSV構文解析中にエラーが発生しました。"+rowCount+"行目付近で引用符の前に不正な文字列が見つかりました。ヒント：「"+lineArray.get(lineArray.size() - 1)+"」");
					throw ioexception;
				}
				_parseString();
			} else {
				lineArray.get(lineArray.size() - 1).append(token);
			}
		}
		if ( firstColDataCount == -1 ) firstColDataCount = lineArray.size();
		return lineArray;
	}

	private void _parseString() throws IOException{
		while ( !tokenArray.isEmpty() ) {
			String token = tokenArray.removeFirst();
			if (token.equals(quote)) {
				if ( _isNextToken(null) || !_isNextToken(quote) ) {
					return;
				}
				tokenArray.removeFirst();
			}
			lineArray.get(lineArray.size() - 1).append(token);
		}
		if ( lineFeed != null ) {
			lineArray.get(lineArray.size() - 1).append(lineFeed);
		}
		String nextLine = _readLine();
		if ( nextLine != null ) {
			rowCount++;
			_createTokenArray(nextLine);
			_parseString();
			return;
		}
		ioexception = new IOException("CSV構文解析中にエラーが発生しました。"+rowCount+"行目付近の文字列で引用符が閉じられていない項目がある可能性があります。ヒント：「"+lineArray.get(lineArray.size() - 1)+"」");
		throw ioexception;
	}

	private void _createTokenArray(String line) throws IOException{
		Matcher matcher = pattern.matcher(line);


		while ( matcher.find() ) {
			/*
		      for (int i = 1 ; i <= matcher.groupCount(); i ++){
			    	 // matcher
			          System.out.println("[Group" + i + "] " + matcher.group(i));
			        }
			        */
			String s = matcher.group();
			tokenArray.addLast(s);
		}
		tokenArray.removeLast();
	}

	private boolean _isNextToken(String token) {
		return tokenArray.isEmpty() ? null == token : tokenArray.getFirst().equals(token);
	}

	private StringBuilder _createColumnObject() {
		if ( isNonQuoteBlankToNull && (_isNextToken(delimiter)||_isNextToken(null)) ) {
			return null;
		}
		return new StringBuilder();
	}

	private String _readLine() throws IOException{
		int ch = -1;
		int ch1 = -1;
		int ch2 = -1;
		StringBuilder sb = null;
		if ( bch != -1 ) {
			sb = new StringBuilder();
			sb.append((char)bch);
			bch = -1;
		}
		loop:
		while((ch=_readCh())!=-1){
			if ( sb == null ) sb = new StringBuilder();
			ch1 = ch;
            switch ((char) ch) {
            case '\r':
            case '\n':
                break loop;
            default:
                break;
            }
			sb.append((char) ch);
		}
		ch2 = _readCh();
		if (ch1=='\r'&&ch2=='\n') {
			lineFeed = "\r\n";
			bch = -1;
		} else {
			lineFeed = (ch1 == '\r' || ch1 == '\n') ? String.valueOf((char)ch1) : null;
			bch = ch2;
		}
		if ( sb != null ) {
			return sb.toString();
		}
		return null;
	}

	private int _readCh() throws IOException{
		if ( readCharLen == -1 ) {
			readCharLen = br.read(charBuffer);
			readCharCnt = 0;
		}
		if ( readCharCnt < readCharLen ) {
			char ch = charBuffer[readCharCnt++];
			if ( readCharCnt >= readCharLen ) readCharLen = -1;
			return ch;
		}
		return -1;
	}
/*
	public static void main(String[] args) throws Exception{
		try (CSVReader reader = new CSVReader(new File("C:/無題.txt"),"UTF-8",",","\"",false,true)){
			String[][] sList = null;
			int r = 0;
			while((sList = reader.read(1))!=null){
				System.out.print("[");
				for ( String s : sList[0] ) {
					System.out.print(s+"?");
				}
				System.out.println("]");
				r++;
			}
			System.out.println("END:→"+r);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
