package com.nompor.gtk.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.nompor.gtk.GTKException;
import com.nompor.io.CSVReader;
import com.nompor.io.CSVWriter;
import com.nompor.io.InputStreamFactory;

/**
 * セーブデータファイルの書き込みと読み込みのメソッド群を提供します。
 *
 */
public class GTKFileUtil {

	public static void writeText(File file, String text) {
		writeText(file, text, Charset.defaultCharset().name());
	}
	private static void mkdirs(File file) {
		mkdirs(file.toPath());
	}
	private static void mkdirs(Path file) {
		try {
			Files.createDirectories(file.getParent());
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static void writeText(File file, String text, String encode) {
		mkdirs(file);
		try {
			Files.write(file.toPath(), text.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			throw new GTKException(e);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static String readText(File file) {
		return readText(file, Charset.defaultCharset().name());
	}
	public static String readText(File file, String encode) {
		try {
			return new String(Files.readAllBytes(file.toPath()), encode);
		} catch (UnsupportedEncodingException e) {
			throw new GTKException(e);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static void writeCryptText(File file, String text, String key) {
		writeCryptText(file, text, Charset.defaultCharset().name(), key);
	}
	public static void writeCryptText(File file, String text, String encode, String key) {
		mkdirs(file);
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(getCipherOutputStream(file, key), encode))){
			bw.write(text);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (FileNotFoundException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static String readCryptText(File file, String encode, String key) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getCipherInputStream(file, key), encode))){
			char[] ch = new char[1024];
			StringBuilder sb = new StringBuilder();
			int len;
			while((len=br.read(ch))!=-1) {
				sb.append(ch, 0, len);
			}
			return sb.toString();
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (UnsupportedEncodingException e) {
			throw new GTKException(e);
		} catch (FileNotFoundException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static String readCryptText(File file, String key) {
		return readCryptText(file, Charset.defaultCharset().name(), key);
	}
	private static BufferedInputStream getCipherInputStream(File file, String key) throws FileNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		String algorithm = "AES";
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm));
		return new BufferedInputStream(new CipherInputStream(new FileInputStream(file), c));
	}
	private static BufferedOutputStream getCipherOutputStream(File file, String key) throws FileNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		String algorithm = "AES";
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm));
		return new BufferedOutputStream(new CipherOutputStream(new FileOutputStream(file), c));
	}
	public static void writeProperty(File file, Properties prop) {
		mkdirs(file);
		try (OutputStream out = Files.newOutputStream(file.toPath())){
			prop.store(out, "GTK_WRITE_PROPERTY");
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static Properties readProperty(File file) {
		try (InputStream in = Files.newInputStream(file.toPath())){
			Properties prop = new Properties();
			prop.load(in);
			return prop;
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static void writeProperty(File file, String encode, Properties prop) {
		mkdirs(file);
		try (BufferedWriter out = Files.newBufferedWriter(file.toPath(), Charset.forName(encode))){
			prop.store(out, "GTK_WRITE_PROPERTY");
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static Properties readProperty(File file, String encode) {
		try (BufferedReader in = Files.newBufferedReader(file.toPath(), Charset.forName(encode))){
			Properties prop = new Properties();
			prop.load(in);
			return prop;
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static void writeCryptProperty(File file, Properties prop, String key) {
		mkdirs(file);
		try (OutputStream out = getCipherOutputStream(file, key)){
			prop.store(out, "GTK_WRITE_PROPERTY");
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static Properties readCryptProperty(File file, String key) {
		try (InputStream in = getCipherInputStream(file, key)){
			Properties prop = new Properties();
			prop.load(in);
			return prop;
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static void writeCryptProperty(File file, String encode, Properties prop, String key) {
		mkdirs(file);
		try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(getCipherOutputStream(file, key), encode))){
			prop.store(out, "GTK_WRITE_PROPERTY");
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static Properties readCryptProperty(File file, String encode, String key) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(getCipherInputStream(file, key), encode)) ){
			Properties prop = new Properties();
			prop.load(in);
			return prop;
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static void writeObject(File file, Serializable obj) {
		mkdirs(file);
		try (OutputStream out = Files.newOutputStream(file.toPath())){
			writeObject(out, obj);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static Object readObject(File file) {
		try (InputStream in = Files.newInputStream(file.toPath())){
			return readObject(in);
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (ClassNotFoundException e) {
			throw new GTKException(e);
		}
	}
	public static void writeCryptObject(File file, Serializable obj, String key) {
		mkdirs(file);
		try (OutputStream out = getCipherOutputStream(file, key)){
			writeObject(out, obj);
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static Object readCryptObject(File file, String key) {

		try (InputStream in = getCipherInputStream(file, key)){
			return readObject(in);
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		} catch (ClassNotFoundException e) {
			throw new GTKException(e);
		}
	}

	private static void writeObject(OutputStream out, Serializable obj) throws IOException {
		try(ObjectOutputStream oos = new ObjectOutputStream(out)){
			oos.writeObject(obj);
		}
	}
	private static Object readObject(InputStream in) throws IOException, ClassNotFoundException {
		try(ObjectInputStream ois = new ObjectInputStream(in)){
			return ois.readObject();
		}
	}

	public static void writeBinary(File file, byte[] data) {
		writeBinary(file.toPath(), data);
	}

	public static void writeBinary(Path file, byte[] data) {
		mkdirs(file);
		try (OutputStream out = Files.newOutputStream(file)){
			out.write(data);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}

	/**
	 * 書き込み準備を自動で行い、データの書き込みを実行します。
	 * @param file
	 * @param data
	 */
	public static void writeBinaryBuffer(File file, ByteBuffer data) {
		writeBinaryBuffer(file.toPath(), data);
	}

	/**
	 * 書き込み準備を自動で行い、データの書き込みを実行します。
	 * @param file
	 * @param data
	 */
	public static void writeBinaryBuffer(Path file, ByteBuffer data) {
		mkdirs(file);
		data.flip();
		try (FileChannel c = FileChannel.open(file, StandardOpenOption.CREATE ,StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)){
			c.write(data);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static byte[] readBinary(File file) {
		return readBinary(file.toPath());
	}
	public static byte[] readBinary(Path file) {
		try (InputStream in = Files.newInputStream(file)){
			return in.readAllBytes();
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	/**
	 * データを読み込み、読み込み準備ができた状態でByteBufferを返します。
	 * @param file
	 * @return
	 */
	public static ByteBuffer readBinaryBuffer(File file) {
		return ByteBuffer.wrap(readBinary(file));
	}
	/**
	 * データを読み込み、読み込み準備ができた状態でByteBufferを返します。
	 * @param file
	 * @return
	 */
	public static ByteBuffer readBinaryBuffer(Path file) {
		return ByteBuffer.wrap(readBinary(file));
	}
	public static void writeCryptBinary(File file, byte[] data, String key) {
		mkdirs(file);
		try (OutputStream out = getCipherOutputStream(file, key)){
			out.write(data);
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static byte[] readCryptBinary(File file, String key) {
		try (InputStream in = getCipherInputStream(file, key)){
			return in.readAllBytes();
		} catch (IOException e) {
			throw new GTKException(e);
		} catch (InvalidKeyException e) {
			throw new GTKException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new GTKException(e);
		} catch (NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}

	public static void writeCSVListMap(File file, List<Map<String, String>> list) {
		mkdirs(file);
		try {
			_writeCSVListMap(Files.newOutputStream(file.toPath()), list);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}

	public static void writeCSV(File file, String[][] list) {
		mkdirs(file);
		try {
			_writeCSV(Files.newOutputStream(file.toPath()), list);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}

	private static void _writeCSV(OutputStream out, String[][] list) {
		try(CSVWriter w = new CSVWriter(out, Charset.defaultCharset().name(), ",", "\"")){
			for ( String[] s : list ) {
				w.write(s);
			}
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}

	private static void _writeCSVListMap(OutputStream out, List<Map<String, String>> list) {
		HashSet<String> header = new HashSet<>();
		list.forEach(e -> {
			e.forEach((k,v)->{
				header.add(k);
			});
		});
		try(CSVWriter w = new CSVWriter(out, Charset.defaultCharset().name(), ",", "\"", header.toArray(new String[header.size()]))){
			w.writeObject(list);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	private static List<Map<String, String>> _readCSVListMap(InputStreamFactory factory) {
		try (CSVReader r = new CSVReader(factory ,Charset.defaultCharset().name(), ",", "\"", true, true)){
			List<Map<String, String>> arr = new ArrayList<>();
			Map<String, String> map = null;
			while( (map = r.readObject()) != null ) {
				arr.add(map);
			}
			return arr;
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	private static String[][] _readCSV(InputStreamFactory factory) {
		try (CSVReader r = new CSVReader(factory ,Charset.defaultCharset().name(), ",", "\"", false, true)){
			ArrayList<String[]> arr = new ArrayList<>();
			String[] str = null;
			while( (str = r.read()) != null ) {
				arr.add(str);
			}
			return arr.toArray(new String[arr.size()][]);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}
	public static List<Map<String, String>> readCSVListMap(File file) {
		return _readCSVListMap(() -> Files.newInputStream(file.toPath()));
	}
	public static String[][] readCSV(File file) {
		return _readCSV(() -> Files.newInputStream(file.toPath()));
	}
	public static void writeCryptCSVListMap(File file, List<Map<String, String>> list, String key) {
		mkdirs(file);
		try {
			_writeCSVListMap(getCipherOutputStream(file, key), list);
		} catch (InvalidKeyException | FileNotFoundException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static List<Map<String, String>> readCryptCSVListMap(File file, String key) {
		return _readCSVListMap(() -> {
			try {
				return getCipherInputStream(file, key);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				throw new GTKException(e);
			}
		});
	}
	public static void writeCryptCSV(File file, String[][] list, String key) {
		mkdirs(file);
		try {
			_writeCSV(getCipherOutputStream(file, key), list);
		} catch (InvalidKeyException | FileNotFoundException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new GTKException(e);
		}
	}
	public static String[][] readCryptCSV(File file, String key) {
		return _readCSV(() -> {
			try {
				return getCipherInputStream(file, key);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				throw new GTKException(e);
			}
		});
	}
}
