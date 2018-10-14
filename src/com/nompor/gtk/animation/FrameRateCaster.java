package com.nompor.gtk.animation;

public class FrameRateCaster implements Animation{
    private long before;
    private int count;
    private float frameRate;
    private final int updateTimeMillis;
    public FrameRateCaster(int updateTimeMillis){
        this.updateTimeMillis = updateTimeMillis;
        before = System.currentTimeMillis();
    }

    public float getFrameRate(){
        return frameRate;
    }

    /**
     * フレームレート計算のために毎フレーム呼び出すべきメソッド
     * @return フレームレートの更新があればtrue
     */
	public boolean updateFrame() {
        long now = System.currentTimeMillis();
        count++;
        if(now - before >= updateTimeMillis){
            frameRate = (float)(count * 1000) / (float)(now - before);
            before = now;
            count = 0;
            return true;
        }
        return false;
	}

	@Override
	public void update() {
		updateFrame();
	}

	@Override
	public boolean isEnd() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
