package com.wangf.android.phone.ui;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.stevenhu.android.phone.ui.R;
import com.wangf.android.phone.bean.ADInfo;
import com.wangf.android.phone.utils.ViewFactory;
import com.wangf.cycleviewpager.lib.CycleViewPager;
import com.wangf.cycleviewpager.lib.CycleViewPager.ImageCycleViewListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 描述：主页
 *
 * @author 胡笃勇
 * @version 2015年5月8日 上午10:47:37
 */
public class MainActivity extends Activity {

	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;
	
	private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
			"http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
			"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
			"http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
			"http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
	
	
//	private String[] imageUrls = {
//			"http://upload.taihainet.com/2015/1206/1449401617228.jpg",
//			"http://imgsrc.baidu.com/baike/pic/item/c8177f3e6709c93d27ea5cbe9f3df8dcd100545a.jpg",
//			"http://pic24.nipic.com/20120921/11002873_131328759000_2.jpg",
//			"http://pic11.nipic.com/20101209/6386577_151355002650_2.jpg",
//			"http://ent.cnr.cn/list/20151110/W020151110250242612627.jpg",
//			"http://d.hiphotos.baidu.com/zhidao/pic/item/5ab5c9ea15ce36d31e4b3ad238f33a87e850b181.jpg"};
	
	
//	private int[] imageUrls = {
//			R.drawable.test1,
//			R.drawable.test2,
//			R.drawable.test3,
//			R.drawable.test4,
//			R.drawable.test5};
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_main);
		configImageLoader();
		initialize();
	}
	
	@SuppressLint("NewApi")
	private void initialize() {
		
		cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);
		
		for(int i = 0; i < imageUrls.length; i ++){
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
//			info.setDrawableId(imageUrls[i]);
			info.setContent("图片-->" + i );
			infos.add(info);
		}
		
		// 将最后一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
//		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getDrawableId()));
		for (int i = 0; i < infos.size(); i++) {
			views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
//			views.add(ViewFactory.getImageView(this, infos.get(i).getDrawableId()));
		}
		// 将第一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
//		views.add(ViewFactory.getImageView(this, infos.get(0).getDrawableId()));
		
		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);	

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, infos, mAdCycleViewListener);
		//设置轮播
		cycleViewPager.setWheel(true);

	    // 设置轮播时间，默认5000ms
		cycleViewPager.setTime(2000);
		//设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}
	
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				Toast.makeText(MainActivity.this,
						"position-->" + info.getContent(), Toast.LENGTH_SHORT)
						.show();
			}
			
		}

	};
	
	/**
	 * 配置ImageLoder
	 */
	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);		
	}
}
