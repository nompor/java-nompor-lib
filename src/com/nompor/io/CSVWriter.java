package com.nompor.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nompor
 *
 */
public class CSVWriter implements Closeable{

	private BufferedWriter bw;
	private String delimiter;
	private String quote;
	private String[] header;
	private boolean isNewLine;

	public CSVWriter(OutputStream stream, String encode, String delimiter, String quote, String[] header) throws IOException {
		this.bw = new BufferedWriter(new OutputStreamWriter(stream, encode));
		this.delimiter = delimiter;
		this.quote = quote;
		this.header = header;
		if ( delimiter == null || delimiter.equals("") ) throw new IllegalArgumentException("Delimiter IllegalArgument");
		if ( header != null ) {
			write(header);
		}
	}

	public CSVWriter(OutputStream stream, String encode, String delimiter, String quote) throws IOException {
		this(stream, encode, delimiter, quote, null);
	}

	public CSVWriter(File file, String encode, String delimiter, String quote, String[] header) throws IOException {
		this(new FileOutputStream(file), encode, delimiter, quote, header);
	}

	public CSVWriter(File file, String encode, String delimiter, String quote) throws IOException {
		this(new FileOutputStream(file), encode, delimiter, quote);
	}

	public CSVWriter(File file, String encode) throws IOException {
		this(file,encode,",","\"");
	}

	public CSVWriter(File file) throws IOException {
		this(file,Charset.defaultCharset().name());
	}

	@Override
	public void close() throws IOException {
		bw.close();
	}

	public void writeLine(String str) throws IOException {
		if ( isNewLine ) bw.newLine();
		bw.write(str);
		isNewLine = true;
	}

	public void write(String[] list) throws IOException {
		StringBuilder sb = new StringBuilder();
		for ( int i = 0;i < list.length;i++ ) {
			if ( i != 0 ) sb.append(delimiter);
			String s = list[i];
			sb.append(_toQuoteString(s));
		}
		writeLine(sb.toString());
	}

	public void write(List<String> list) throws IOException {
		write(list.toArray(new String[list.size()]));
	}

	public void writeObject(List<Map<String, String>> data) throws IOException {
		for ( Map<String, String> map : data ) {
			writeObject(map);
		}
	}

	public void writeObject(Map<String, String> data) throws IOException {
		ArrayList<String> arr = new ArrayList<>();
		for ( String s : header ) {
			arr.add(data.get(s));
		}
		write(arr);
	}

	private String _toQuoteString(String target) {
		if ( target == null ) return "";
		if ( quote == null || "".equals(quote) ) return target;
		return quote+target.replace(quote, quote+quote)+quote;
	}
}
