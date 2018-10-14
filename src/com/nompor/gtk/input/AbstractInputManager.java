package com.nompor.gtk.input;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.nompor.gtk.file.GTKFileUtil;
import com.nompor.gtk.input.InputAdapter.Entry;

/**
 * 毎フレームユーザー入力処理を監視することで、入力を細かく判定するための機能を実装する抽象クラス

 * @param <K>
 */
public abstract class AbstractInputManager<K> implements Serializable {
	private HashMap<Object, InputAdapter<K>> registMap = new HashMap<>();
	private Map<K, InputInfo> keyMap = new HashMap<>();
	private boolean isUpdateAdapter = false;
	protected AbstractInputManager(Map<K, InputInfo> keyMap) {
		this.keyMap = keyMap;
	}
	/**
	 * 指定したコードでフラグセットを登録します。
	 * @param key
	 * @param keyCode
	 */
	public void regist(K key) {
		regist(key, key);
	}
	/**
	 * 指定したキーでコードのフラグセットを登録します。
	 * @param key
	 * @param keyCode
	 */
	public void regist(Object key, K code) {
		regist(key, new SingleInputAdapter<K>(code));
	}
	/**
	 * 指定したキーでコードをOR条件で判定するためのアダプターを登録します。
	 * @param key
	 * @param keyCode
	 */
	@SuppressWarnings("unchecked")
	public void orRegist(Object key, K... codes) {
		regist(key, new OrInputAdapter<K>(codes));
	}
	/**
	 * 指定したキーでコードをAND条件で判定するためのアダプターを登録します。
	 * @param key
	 * @param keyCode
	 */
	@SuppressWarnings("unchecked")
	public void andRegist(Object key, K... codes) {
		regist(key, new AndInputAdapter<K>(codes));
	}
	/**
	 * 指定したコードでキーフラグセットを登録します。
	 * @param key
	 * @param keyCode
	 */
	public void regist(Object key, InputAdapter<K> adapter) {
		adapter.forEach(entry -> {
			InputInfo info = keyMap.containsKey(entry.key) ? keyMap.get(entry.key) : new InputInfo();
			info.registedCount++;
			keyMap.put(entry.key, info);
			entry.info = info;
		});
		InputAdapter<K> oldAdapter = registMap.put(key, adapter);
		if ( oldAdapter != null ) oldAdapter.forEach(this::_removeForKeyMap);
	}
	/**
	 * 登録されたキーフラグセットを取得します。
	 * @return
	 */
	public HashMap<Object, InputAdapter<K>> getRegistMap(){
		return registMap;
	}
	/**
	 * 指定した名前に関連付けられたキーフラグセットを削除します。
	 * @param key
	 */
	public void remove(Object key) {
		InputAdapter<K> adapter = registMap.remove(key);
		adapter.forEach(this::_removeForKeyMap);
	}

	private void _removeForKeyMap(Entry<K> entry) {
		InputInfo info = keyMap.get(entry.key);
		info.registedCount--;
		if ( info.registedCount <= 0 ) {
			keyMap.remove(entry.key);
		}
	}

	/**
	 * キーが離された状態から押されたかどうか判定する
	 * @param key
	 * @return
	 */
	public boolean isPress(Object key) {
		return registMap.get(key).isPress();
	}
	/**
	 * キーが押された状態から離されたかどうか判定する
	 * @param key
	 * @return
	 */
	public boolean isRelease(Object key) {
		return registMap.get(key).isRelease();
	}
	/**
	 * キーが押し下げ状態かどうか判定する
	 * @param key
	 * @return
	 */
	public boolean isDown(Object key) {
		return registMap.get(key).isDown();
	}
	/**
	 * キーが離された状態かどうか判定する
	 * @param key
	 * @return
	 */
	public boolean isUp(Object key) {
		return registMap.get(key).isUp();
	}
	/**
	 * keyPressイベントが発生したらキーコードを渡します。
	 * @param keyCode
	 */
	public void pressed(K keyCode) {
		InputInfo info = keyMap.get(keyCode);
		if ( info != null ) {
			info.press();
		}
	}
	/**
	 * keyReleaseイベントが発生したらキーコードを渡します。
	 * @param keyCode
	 */
	public void released(K keyCode) {
		InputInfo info = keyMap.get(keyCode);
		if ( info != null ) {
			info.release();
		}
	}
	/**
	 * 毎フレーム呼び出すべき処理
	 */
	public void update() {
		for ( Map.Entry<K, InputInfo> e : keyMap.entrySet() ) {
			e.getValue().update();
		}
		if ( isUpdateAdapter ) {
			for ( Map.Entry<Object, InputAdapter<K>> e : registMap.entrySet() ) {
				e.getValue().update();
			}
		}
	}

	/**
	 * 全てのキーを削除します。
	 */
	public void clear() {
		registMap.clear();
		keyMap.clear();
	}

	/**
	 * 登録された入力の押下情報を初期化します。
	 */
	public void clearInput() {
		for ( InputAdapter<K> e : registMap.values() ) {
			e.forEach(e2 -> {
				InputInfo info = e2.info;
				info.clear();
			});
		}
	}

	/**
	 * アダプター更新メソッドを呼び出すかどうか
	 * @return
	 */
	public boolean isUpdateAdapter() {
		return isUpdateAdapter;
	}
	/**
	 * アダプター更新メソッドを呼び出すかどうか設定する
	 * @return
	 */
	public void setUpdateAdapter(boolean isUpdateAdapter) {
		this.isUpdateAdapter = isUpdateAdapter;
	}

	/**
	 * この入力オブジェクトの登録情報をファイルに保存します。
	 * @param file
	 */
	public void writeInputConfig(File file) {
		GTKFileUtil.writeObject(file, this);
	}

	/**
	 * ファイルが示す入力情報をオブジェクト化します。
	 * @param file
	 * @return
	 */
	public static AbstractInputManager<?> readInputConfig(File file) {
		return (AbstractInputManager<?>) GTKFileUtil.readObject(file);
	}
}
