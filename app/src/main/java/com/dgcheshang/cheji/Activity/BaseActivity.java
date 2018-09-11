package com.dgcheshang.cheji.Activity;

/*
 *  UVCCamera
 *  library and sample to access to UVC web camera on non-rooted Android device
 *
 * Copyright (c) 2014-2017 saki t_saki@serenegiant.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *  All files in the folder are under this Apache License, Version 2.0.
 *  Files in the libjpeg-turbo, libusb, libuvc, rapidjson folder
 *  may have a different license, see the respective files.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.dgcheshang.cheji.R;
import com.serenegiant.dialog.MessageDialogFragment;
import com.serenegiant.utils.BuildCheck;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.PermissionCheck;

/**
 * Created by saki on 2016/11/18.
 *
 */
public class BaseActivity extends Activity
	implements MessageDialogFragment.MessageDialogListener {

	private static boolean DEBUG = false;	// FIXME �g�P�r��false�˥��åȤ��뤳��
	private static final String TAG = BaseActivity.class.getSimpleName();

	/** UI�����Τ����Handler */
	private final Handler mUIHandler = new Handler(Looper.getMainLooper());
	private final Thread mUiThread = mUIHandler.getLooper().getThread();
	/** ��`���`����å��ϤǄI���뤿���Handler */
	private Handler mWorkerHandler;
	private long mWorkerThreadID = -1;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��`���`����åɤ�����
		if (mWorkerHandler == null) {
			mWorkerHandler = HandlerThreadHandler.createHandler(TAG);
			mWorkerThreadID = mWorkerHandler.getLooper().getThread().getId();
		}
	}

	@Override
	protected void onPause() {
		clearToast();
		super.onPause();
	}

	@Override
	protected synchronized void onDestroy() {
		// ��`���`����åɤ��Ɨ�
		if (mWorkerHandler != null) {
			try {
				mWorkerHandler.getLooper().quit();
			} catch (final Exception e) {
				//
			}
			mWorkerHandler = null;
		}
		super.onDestroy();
	}

//================================================================================
	/**
	 * UI����åɤ�Runnable��g�Ф��뤿��Υإ�ѩ`�᥽�å�
	 * @param task
	 * @param duration
	 */
	public final void runOnUiThread(final Runnable task, final long duration) {
		if (task == null) return;
		mUIHandler.removeCallbacks(task);
		if ((duration > 0) || Thread.currentThread() != mUiThread) {
			mUIHandler.postDelayed(task, duration);
		} else {
			try {
				task.run();
			} catch (final Exception e) {
				Log.w(TAG, e);
			}
		}
	}

	/**
	 * UI����å��Ϥ�ָ������Runnable���g�д������Ƥ���Ќg�д�����������
	 * @param task
	 */
	public final void removeFromUiThread(final Runnable task) {
		if (task == null) return;
		mUIHandler.removeCallbacks(task);
	}

	/**
	 * ��`���`����å��Ϥ�ָ������Runnable��g�Ф���
	 * δ�g�Ф�ͬ��Runnable������Х���󥻥뤵���(�ᤫ��ָ���������Τߌg�Ф����)
	 * @param task
	 * @param delayMillis
	 */
	protected final synchronized void queueEvent(final Runnable task, final long delayMillis) {
		if ((task == null) || (mWorkerHandler == null)) return;
		try {
			mWorkerHandler.removeCallbacks(task);
			if (delayMillis > 0) {
				mWorkerHandler.postDelayed(task, delayMillis);
			} else if (mWorkerThreadID == Thread.currentThread().getId()) {
				task.run();
			} else {
				mWorkerHandler.post(task);
			}
		} catch (final Exception e) {
			// ignore
		}
	}

	/**
	 * ָ������Runnable���`���`����å��Ϥǌg���趨�Ǥ���Х���󥻥뤹��
	 * @param task
	 */
	protected final synchronized void removeEvent(final Runnable task) {
		if (task == null) return;
		try {
			mWorkerHandler.removeCallbacks(task);
		} catch (final Exception e) {
			// ignore
		}
	}

//================================================================================
	private Toast mToast;
	/**
	 * Toast�ǥ�å��`�����ʾ
	 * @param msg
	 */
	protected void showToast(final int msg, final Object... args) {
		removeFromUiThread(mShowToastTask);
		mShowToastTask = new ShowToastTask(msg, args);
		runOnUiThread(mShowToastTask, 0);
	}

	/**
	 * Toast����ʾ����Ƥ���Х���󥻥뤹��
	 */
	protected void clearToast() {
		removeFromUiThread(mShowToastTask);
		mShowToastTask = null;
		try {
			if (mToast != null) {
				mToast.cancel();
				mToast = null;
			}
		} catch (final Exception e) {
			// ignore
		}
	}

	private ShowToastTask mShowToastTask;
	private final class ShowToastTask implements Runnable {
		final int msg;
		final Object args;
		private ShowToastTask(final int msg, final Object... args) {
			this.msg = msg;
			this.args = args;
		}

		@Override
		public void run() {
			try {
				if (mToast != null) {
					mToast.cancel();
					mToast = null;
				}
				if (args != null) {
					final String _msg = getString(msg, args);
					mToast = Toast.makeText(BaseActivity.this, _msg, Toast.LENGTH_SHORT);
				} else {
					mToast = Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT);
				}
				mToast.show();
			} catch (final Exception e) {
				// ignore
			}
		}
	}

//================================================================================
	/**
	 * MessageDialogFragment��å��`��������������Υ��`��Хå��ꥹ�ʩ`
	 * @param dialog
	 * @param requestCode
	 * @param permissions
	 * @param result
	 */
	@SuppressLint("NewApi")
	@Override
	public void onMessageDialogResult(final MessageDialogFragment dialog, final int requestCode, final String[] permissions, final boolean result) {
		if (result) {
			// ��å��`������������OK��Ѻ���줿�r�ϥѩ`�ߥå����Ҫ�󤹤�
			if (BuildCheck.isMarshmallow()) {
				//requestPermissions(permissions, requestCode);
				return;
			}
		}
		// ��å��`�����������ǥ���󥻥뤵�줿�r��Android6�Ǥʤ��r����ǰ�ǥ����å�����#checkPermissionResult����ӳ���
		for (final String permission: permissions) {
			checkPermissionResult(requestCode, permission, PermissionCheck.hasPermission(this, permission));
		}
	}

	/**
	 * �ѩ`�ߥå����Ҫ��Y�����ܤ�ȡ�뤿��Υ᥽�å�
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
//	@Override
//	public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults);	// �Τ⤷�Ƥʤ�����һ�����ɤ�
//		final int n = Math.min(permissions.length, grantResults.length);
//		for (int i = 0; i < n; i++) {
//			checkPermissionResult(requestCode, permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
//		}
//	}

	/**
	 * �ѩ`�ߥå����Ҫ��νY��������å�
	 * �����Ǥϥѩ`�ߥå�����ȡ�äǤ��ʤ��ä��r��Toast�ǥ�å��`����ʾ�������
	 * @param requestCode
	 * @param permission
	 * @param result
	 */
	protected void checkPermissionResult(final int requestCode, final String permission, final boolean result) {
		// �ѩ`�ߥå���󤬤ʤ��Ȥ��ˤϥ�å��`�����ʾ����
		if (!result && (permission != null)) {
			if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
				showToast(R.string.permission_audio);
			}
			if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
				showToast(R.string.permission_ext_storage);
			}
			if (Manifest.permission.INTERNET.equals(permission)) {
				showToast(R.string.permission_network);
			}
		}
	}

	// �ӵĥѩ`�ߥå����Ҫ��r��Ҫ�󥳩`��
	protected static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 0x12345;
	protected static final int REQUEST_PERMISSION_AUDIO_RECORDING = 0x234567;
	protected static final int REQUEST_PERMISSION_NETWORK = 0x345678;
	protected static final int REQUEST_PERMISSION_CAMERA = 0x537642;

	/**
	 * �ⲿ���ȥ�`���ؤΕ����z�ߥѩ`�ߥå�����Ф뤫�ɤ���������å�
	 * �ʤ�����h�������������ʾ����
	 * @return true �ⲿ���ȥ�`���ؤΕ����z�ߥѩ`�ߥå�����Ф�
	 */
	protected boolean checkPermissionWriteExternalStorage() {
		if (!PermissionCheck.hasWriteExternalStorage(this)) {
			MessageDialogFragment.showDialog(this, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE,
				R.string.permission_title, R.string.permission_ext_storage_request,
				new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
			return false;
		}
		return true;
	}

	/**
	 * �h���Υѩ`�ߥå�����Ф뤫�ɤ���������å�
	 * �ʤ�����h�������������ʾ����
	 * @return true �h���Υѩ`�ߥå�����Ф�
	 */
	protected boolean checkPermissionAudio() {
		if (!PermissionCheck.hasAudio(this)) {
			MessageDialogFragment.showDialog(this, REQUEST_PERMISSION_AUDIO_RECORDING,
				R.string.permission_title, R.string.permission_audio_recording_request,
				new String[]{Manifest.permission.RECORD_AUDIO});
			return false;
		}
		return true;
	}

	/**
	 * �ͥåȥ�`�����������Υѩ`�ߥå�����Ф뤫�ɤ���������å�
	 * �ʤ�����h�������������ʾ����
	 * @return true �ͥåȥ�`�����������Υѩ`�ߥå�����Ф�
	 */
	protected boolean checkPermissionNetwork() {
		if (!PermissionCheck.hasNetwork(this)) {
			MessageDialogFragment.showDialog(this, REQUEST_PERMISSION_NETWORK,
				R.string.permission_title, R.string.permission_network_request,
				new String[]{Manifest.permission.INTERNET});
			return false;
		}
		return true;
	}

	/**
	 * ����饢�������Υѩ`�ߥå���󤬤��뤫�ɤ���������å�
	 * �ʤ�����h�������������ʾ����
	 * @return true ����饢�������Υѩ`�ߥå�����Ф�
	 */
	protected boolean checkPermissionCamera() {
		if (!PermissionCheck.hasCamera(this)) {
			MessageDialogFragment.showDialog(this, REQUEST_PERMISSION_CAMERA,
				R.string.permission_title, R.string.permission_camera_request,
				new String[]{Manifest.permission.CAMERA});
			return false;
		}
		return true;
	}

}
