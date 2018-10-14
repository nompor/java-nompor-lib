package com.nompor.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.nompor.util.MultiTimeRecorder;
import com.nompor.util.ThreadUtil;
import com.nompor.util.TimeRecorder;

public final class BenchmarkWindow extends JFrame{
	private MultiTimeRecorder recorders;
	private boolean isAutoUpdate = true;
    private JTable table = new JTable();
    private JScrollPane scrollPane = new JScrollPane();
	private DefaultTableModel dtm;
	private final String unitString;

	private BenchmarkWindow(TimeUnit unit,int cacheBuffer, int recordingFrameCount){
		super("実行速度計測ツール");
		switch(unit) {
		case NANOSECONDS:unitString=" ns";break;
		case MICROSECONDS:unitString=" mcs";break;
		case MILLISECONDS:unitString=" ms";break;
		case SECONDS:unitString=" s";break;
		default:throw new IllegalArgumentException();
		}
		recorders = new MultiTimeRecorder(unit, cacheBuffer, recordingFrameCount) {
			public void onCreateTimeRecord(Object key, TimeRecorder recorder) {
	    		String[] cells = new String[dtm.getColumnCount()];
	    		cells[0] = key.toString();
	    		dtm.addRow(cells);
			}
			public void onReadyStartRecord(Object key, TimeRecorder recorder) {

			}
			public void onEndRecord(Object key, TimeRecorder recorder) {
				if ( isAutoUpdate ) updateView(key);
			}
		};
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public static BenchmarkWindow open(TimeUnit unit,int cacheBuffer, int recordingFrameCount, int width, int height) {
		BenchmarkWindow wnd = new BenchmarkWindow(unit, cacheBuffer, recordingFrameCount);
		wnd.setSize(width, height);
		wnd.setLocationRelativeTo(null);
		wnd.viewInit();
		wnd.setVisible(true);
		return wnd;
	}
	public static BenchmarkWindow open() {
		return open(TimeUnit.MILLISECONDS, 10, 1, 1000, 400);
	}
	public static BenchmarkWindow openAnimationWindow() {
		return open(TimeUnit.MILLISECONDS, 10, 60, 1000, 400);
	}
	private void viewInit(){
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		String[] cacheHeader = new String[recorders.getCacheBuffer()+2];
		cacheHeader[0] = "名前";
		cacheHeader[1] = "平均";
		for ( int i = 2;i < cacheHeader.length;i++ ) {
			cacheHeader[i] = ""+(i-1);
		}
		dtm = new DefaultTableModel(cacheHeader,0){
			private static final long serialVersionUID = -2585836092241257364L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        table.setModel(dtm);
        table.setDefaultRenderer(Object.class, new OriginalRenderer());
		scrollPane.getViewport().add( table );
	}
	public void startRecord(Object key){
		recorders.startRecord(key);
	}
	public void endRecord(Object key){
		recorders.endRecord(key);
	}
	public void updateView(Object k){
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		Set<Object> m = recorders.keySet();
		int i = 0;
		for ( Object key : m ) {
			if ( !key.equals(k) ) {
				i++;
				continue;
			}
			TimeRecorder tr = recorders.get(key);
			BigDecimal[] list = tr.getCacheList();
			for (int j = 0;j < list.length;j++) {
				DataCell dc = new DataCell();
				dc.d = list[j];
				dc.s = unitString;
				dtm.setValueAt(dc,i, j+2);
			}
			DataCell dc = new DataCell();
			dc.d = tr.getAverage();
			dc.s = unitString+"/"+tr.getAverageCount();
			dtm.setValueAt(dc, i, 1);
			break;
		}
	}

	public void startRecordForStackName(int stackNum){
		startRecord(ThreadUtil.getStackClassAndMethodName(stackNum));
	}
	public void endRecordForStackName(int stackNum){
		endRecord(ThreadUtil.getStackClassAndMethodName(stackNum));
	}

	public void startRecord(){
		startRecordForStackName(4);
	}
	public void endRecord(){
		endRecordForStackName(4);
	}

	private class OriginalRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 5936287067836363665L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        if ( value instanceof DataCell ) {
		        DataCell d = (DataCell) value;
		        long n = d.getData().longValue();
		        updateUI();
				if ( n >= 1000 ) {
					setForeground(Color.WHITE);
					setBackground(Color.red);
				} else if ( n >= 100 ) {
					setForeground(Color.BLACK);
					setBackground(Color.orange);
				} else if ( n >= 10 ) {
					setForeground(Color.BLACK);
					setBackground(Color.yellow);
				} else if ( n >= 1 ) {
					setForeground(Color.BLACK);
					setBackground(Color.cyan);
				} else {
					setForeground(Color.BLACK);
					setBackground(Color.white);
				}
		        setHorizontalAlignment(RIGHT);
	        } else {
				setBackground(Color.white);
		        setHorizontalAlignment(LEFT);
	        }
	        return this;
	    }
	}
	private class DataCell{
		BigDecimal d;
		String s;
		public BigDecimal getData(){
			return d;
		}
		public String toString(){
			return d.toPlainString()+s;
		}
	}
}
