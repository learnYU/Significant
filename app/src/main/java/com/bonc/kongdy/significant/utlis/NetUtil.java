package com.bonc.kongdy.significant.utlis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
/**
 * 判断是否有网
 * @ClassName: NetUtil 
 * @author leilei 
 * @date 2013-12-31 10:32:33 
 */
public class NetUtil {
	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;

	/**
	 * 
	 * @Title: getNetworkState 
	 * @param context
	 * @return int--0,没有网络; 1,wifi; 2,移动网络;   
	 * @date 2013-12-26 12:12:11
	 */
	public static int getNetworkState(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

//		connManager.getActiveNetworkInfo().getState()
		// Wifi

		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_WIFI;
		}

		// 3G
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_MOBILE;
		}
		return NETWORN_NONE;
	}
}

