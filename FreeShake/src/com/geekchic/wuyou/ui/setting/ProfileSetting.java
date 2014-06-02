/**
 * @Title: ProfileSetting.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 11, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.common.utils.FileUtils;
import com.geekchic.common.utils.ImageUtils;
import com.geekchic.common.utils.StringUtils;
import com.geekchic.constant.AppActionCode;
import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.framework.ui.dialog.BasicListDialog;
import com.geekchic.framework.ui.dialog.BasicListDialog.BounceMode;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.logic.profile.IProfileLogic;
import com.geekchic.wuyou.ui.dialog.PermissionDialog;
import com.geekchic.wuyou.ui.dialog.PhoneManagerDialog;
import com.widget.roundimageview.RoundedImageView;

/**
 * @ClassName: ProfileSetting
 * @Descritpion: 个人资料设置
 * @author evil
 * @date May 11, 2014
 */
public class ProfileSetting extends BaseTitleBarActivity implements
		OnClickListener {
	/**
	 * TAG
	 */
	private static final String TAG="ProfileSetting";
	/** 请求相册 */
	public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
	/** 请求相机 */
	public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
	/** 请求裁剪 */
	public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;
	/**
	 * 绑定旧号
	 */
	private View mPhoneManagerView;
	/**
	 * 设定权限
	 */
	private View mPermissionView;
	/**
	 * 编辑头像
	 */
	private View mAvatorEditView;
	/**
	 * 头像显示
	 */
	private RoundedImageView mAvatorImageView;
	/**
	 * 设置头像大小
	 */
	private final static int CROP = 64;
	/**
	 * 头像存储位置
	 */
	private final static String FILE_SAVEPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/wuyou/Portrait/";
	/**
	 * 原始Uri
	 */
	private Uri origUri;
	/**
	 * 剪裁后uri
	 */
	private Uri cropUri;
	/**
	 * 头像文件
	 */
	private File protraitFile;
	/**
	 * 头像路径
	 */
	private String protraitPath;
	/**
	 * 头像操作选项
	 */
	private BasicListDialog mAvatorBasicListDialog;
	/**
	 * 绑定邮箱
	 */
	private View mMailBindView;
	/**
	 * 绑定QQ
	 */
	private View mQQBindView;
	/**
	 * 邮箱显示
	 */
	private TextView mMailContentTextView;
	/**
	 * QQ号显示
	 */
	private TextView mQQContentTextView;
	/**
	 * 资源Logic
	 */
	private IProfileLogic mProfileLogic;
	/**
	 * 后退
	 */
	private OnClickListener mBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finishActivity();
		}
	};

	@Override
	public int getLayoutId() {
		return R.layout.profile_setting_layout;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.profile_setting);
		setLeftButton(R.drawable.icon_tab_metra_back_selector,
				mBackClickListener);
		setTitleBarBackground(R.color.blue);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		mPhoneManagerView = findViewById(R.id.set_bind_old_phone);
		mPermissionView=findViewById(R.id.set_contact_permission);
		mAvatorEditView=findViewById(R.id.profile_avatoredit_iv);
		mAvatorImageView=(RoundedImageView) findViewById(R.id.profile_avator_iv);
		mAvatorEditView.setOnClickListener(this);
		mPermissionView.setOnClickListener(this);
		mPhoneManagerView.setOnClickListener(this);
		
		mMailBindView=findViewById(R.id.set_bind_mail);
		mQQBindView=findViewById(R.id.set_bind_qq);
		mMailContentTextView=(TextView) findViewById(R.id.set_bind_mail_content);
		mQQContentTextView=(TextView) findViewById(R.id.set_bind_qq_content);
		mMailBindView.setOnClickListener(this);
		mQQBindView.setOnClickListener(this);
	}
    @Override
    protected void initLogics() {
    	super.initLogics();
    	mProfileLogic=(IProfileLogic) getLogic(IProfileLogic.class);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_bind_old_phone:
			PhoneManagerDialog phoneManagerDialog = new PhoneManagerDialog(this);
			phoneManagerDialog = phoneManagerDialog.create();
			phoneManagerDialog.show();
			break;
		case R.id.set_contact_permission:
			PermissionDialog permissionDialog=new PermissionDialog(this);
			permissionDialog=permissionDialog.create();
			permissionDialog.show();
			break;
		case R.id.profile_avatoredit_iv:
			showCameraChooseDialog();
			break;
		case R.id.set_bind_mail:
			EditText mailEditText=new EditText(this);
			new BasicDialog.Builder(this).setTitle("设置您的邮箱地址：").setContentView(mailEditText)
			.setNegativeButton("确认" , new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
            					
				}
			})
			.create().show();
			break;
		default:
			break;
		}
	}
	 /**
     * 拍照或者手机相册的选择框<BR>
     */
    private void showCameraChooseDialog()
    {
        String[] itemTitles = getResources().getStringArray(R.array.avator_dialog_title);
        mAvatorBasicListDialog = new BasicListDialog(this).setItems(itemTitles,
                null,
                null,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        switch (which)
                        {
                            case 0:
                            	startActionCamera();
                                break;
                            case 1:
                            	startImagePick();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setBounceMode(BounceMode.BOTTOM)
                .create();
        mAvatorBasicListDialog.resizeDialogSize(DisplayInfo.getScreenWidth(this),
                0);
    }
    /**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	private void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
				REQUEST_CODE_GETIMAGE_BYCROP);
	}

	/**
	 * 相机拍照
	 * 
	 * @param output
	 */
	private void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
		startActivityForResult(intent,
				REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	/**
	 * 拍照后裁剪
	 * 
	 * @param data
	 *            原始图片
	 * @param output
	 *            裁剪后图片
	 */
	private void startActionCrop(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", this.getUploadTempFile(data));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP);// 输出图片大小
		intent.putExtra("outputY", CROP);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		startActivityForResult(intent,
				REQUEST_CODE_GETIMAGE_BYSDCARD);
	}
	// 裁剪头像的绝对路径
	private Uri getUploadTempFile(Uri uri) {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			showShortToast("无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (StringUtils.isNullOrEmpty(thePath)) {
			thePath = ImageUtils.getAbsoluteImagePath(ProfileSetting.this, uri);
		}
		String ext = FileUtils.getFileFormat(thePath);
		ext = StringUtils.isNullOrEmpty(ext) ? "jpg" : ext;
		// 照片命名
		String cropFileName = "wuyou_crop_" + timeStamp + "." + ext;
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);

		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}
	// 拍照保存的绝对路径
	private Uri getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			showShortToast("无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// 照片命名
		String cropFileName = "geekchic_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		this.origUri = this.cropUri;
		return this.cropUri;
	}
    @Override
    protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case REQUEST_CODE_GETIMAGE_BYCAMERA:
			startActionCrop(origUri);// 拍照后裁剪
			break;
		case REQUEST_CODE_GETIMAGE_BYCROP:
			startActionCrop(data.getData());// 选图后裁剪
			break;
		case REQUEST_CODE_GETIMAGE_BYSDCARD:
			uploadNewPhoto();// 上传新照片
			Logger.d(TAG, protraitPath);
			break;
		}
	}

	private void uploadNewPhoto() {
			mProfileLogic.updateAvator(protraitPath);
			  Bitmap avatorBitmap = BitmapFactory.decodeFile(protraitPath);
              mAvatorImageView.setImageBitmap(avatorBitmap);
	}
	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		switch (msg.what) {
		case AppActionCode.ProfileCode.PROFILE_AVATOR_SUCCESS:
			String avatorId=(String) msg.obj;
			showShortToast("上传头像成功");
			mProfileLogic.setAvator(avatorId);
			break;
		case AppActionCode.ProfileCode.PROFILE_AVATORID_SUCCESS:
			showShortToast("设置ID成功");
		default:
			break;
		}
	}
}
