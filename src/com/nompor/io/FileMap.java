package com.nompor.io;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * キーオブジェクトのtoStringをキーにファイル入出力するクラス
 * Mapクラスなどと使用方法は似ているが、ファイルの入出力を行う為、全く処理内容が違います。
 * 手軽にファイル入出力を行いたい場合に利用できます。
 * 処理速度が求められる場面やマルチスレッド環境で使用するのはお勧めできません。
 * @author nompor
 *
 * @param <K>
 * @param <V>
 */
public class FileMap<K,V extends Serializable>{
	private Path workdir;
	public FileMap(Path workdir) throws IOException {
		this.workdir = workdir;
		if ( !Files.exists(workdir) ) {
			Files.createDirectories(workdir);
		}
	}
	public FileMap(File workdir) throws IOException {
		this(workdir.toPath());
	}
	public Path getWorkDirectory() {
		return workdir;
	}
	public void put(K key, V val) throws IOException {
		String keySt = key.toString();
		try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(workdir.resolve(Paths.get(keySt)), StandardOpenOption.CREATE_NEW))){
			stream.writeObject(val);
		}
	}
	@SuppressWarnings("unchecked")
	public V get(K key) throws ClassNotFoundException, IOException {
		String keySt = key.toString();
		Path tgt = workdir.resolve(Paths.get(keySt));
		if ( Files.exists(tgt) ) {
			try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(workdir.resolve(Paths.get(keySt))))){
				return (V) stream.readObject();
			}
		}
		return null;
	}

	public void remove(K key) throws IOException {
		String keySt = key.toString();
		Files.delete(workdir.resolve(Paths.get(keySt)));
	}

	public void clear() throws IOException {
		allDelete();
	}
	public boolean contains(K key) {
		String keySt = key.toString();
		return Files.exists(workdir.resolve(Paths.get(keySt)));
	}
	public int size() throws IOException {
		try(Stream<Path> s = Files.list(workdir)){
			return (int) s.count();
		}
	}
	@SuppressWarnings("unchecked")
	public ArrayList<V> toArray() throws IOException, ClassNotFoundException {
		ArrayList<V> arr = new ArrayList<>();
		try(DirectoryStream<Path> ds = Files.newDirectoryStream(workdir)){
			for ( Path p : ds ) {
				try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(p))){
					arr.add((V) stream.readObject());
				}
			}
		}
		return arr;
	}
	public ArrayList<String> toKeyArray() throws IOException, ClassNotFoundException {
		ArrayList<String> arr = new ArrayList<>();
		try(DirectoryStream<Path> ds = Files.newDirectoryStream(workdir)){
			for ( Path p : ds ) {
				arr.add(p.getFileName().toString());
			}
		}
		return arr;
	}
	private void allDelete() throws IOException {
		try(DirectoryStream<Path> ds = Files.newDirectoryStream(workdir)){
			for ( Path p : ds ) {
				Files.delete(p);
			}
		}
	}
}
