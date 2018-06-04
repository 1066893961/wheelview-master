package xzh.com.addresswheel_master.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xzh.com.addresswheel_master.R;
import xzh.com.addresswheel_master.adapter.AreaWheelAdapter;
import xzh.com.addresswheel_master.adapter.CityWheelAdapter;
import xzh.com.addresswheel_master.adapter.ProvinceWheelAdapter;
import xzh.com.addresswheel_master.model.AddressModel;
import xzh.com.addresswheel_master.utils.Utils;
import xzh.com.addresswheel_master.view.listener.OnAddressChangeListener;
import xzh.com.addresswheel_master.view.wheelview.MyOnWheelChangedListener;
import xzh.com.addresswheel_master.view.wheelview.MyWheelView;


public class ChooseAddressWheel implements MyOnWheelChangedListener {

    @Bind(R.id.province_wheel)
    MyWheelView provinceWheel;
    @Bind(R.id.city_wheel)
    MyWheelView cityWheel;
    @Bind(R.id.district_wheel)
    MyWheelView districtWheel;

    private Activity context;
    private View parentView;
    private PopupWindow popupWindow = null;
    private WindowManager.LayoutParams layoutParams = null;
    private LayoutInflater layoutInflater = null;
    private AddressModel.ProvinceEntity provinceEntity = new AddressModel.ProvinceEntity();
    private AddressModel.ProvinceEntity.CityListBean cityEntity = new AddressModel.ProvinceEntity.CityListBean();
    private AddressModel.ProvinceEntity.CityListBean.AreaListBean areaEntity = new AddressModel.ProvinceEntity.CityListBean.AreaListBean();
    private List<AddressModel.ProvinceEntity> province = null;

    private OnAddressChangeListener onAddressChangeListener = null;

    public ChooseAddressWheel(Activity context) {
        this.context = context;
        init();
    }

    private void init() {
        layoutParams = context.getWindow().getAttributes();
        layoutInflater = context.getLayoutInflater();
        initView();
        initPopupWindow();
    }

    private void initView() {
        parentView = layoutInflater.inflate(R.layout.choose_city_layout, null);
        ButterKnife.bind(this, parentView);

        provinceWheel.setVisibleItems(7);
        cityWheel.setVisibleItems(7);
        districtWheel.setVisibleItems(7);

        provinceWheel.addChangingListener(this);
        cityWheel.addChangingListener(this);
        districtWheel.addChangingListener(this);
    }

    private void initPopupWindow() {
        popupWindow = new PopupWindow(parentView, WindowManager.LayoutParams.MATCH_PARENT, (int) (Utils.getScreenHeight(context) * (2.0 / 5)));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.anim_push_bottom);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                context.getWindow().setAttributes(layoutParams);
                popupWindow.dismiss();
            }
        });
    }

    private void bindData() {
        provinceWheel.setViewAdapter(new ProvinceWheelAdapter(context, province));
        updateCitiy();
        updateDistrict();
    }

    @Override
    public void onChanged(MyWheelView wheel, int oldValue, int newValue) {
        if (wheel == provinceWheel) {
            updateCitiy();//省份改变后城市和地区联动
        } else if (wheel == cityWheel) {
            updateDistrict();//城市改变后地区联动
        } else if (wheel == districtWheel) {
        }
    }

    private void updateCitiy() {
        int index = provinceWheel.getCurrentItem();
        List<AddressModel.ProvinceEntity.CityListBean> citys = province.get(index).getCityList();
        if (citys != null && citys.size() > 0) {
            cityWheel.setViewAdapter(new CityWheelAdapter(context, citys));
            cityWheel.setCurrentItem(0);
            updateDistrict();
        }
    }

    private void updateDistrict() {
        int provinceIndex = provinceWheel.getCurrentItem();
        List<AddressModel.ProvinceEntity.CityListBean> citys = province.get(provinceIndex).getCityList();
        int cityIndex = cityWheel.getCurrentItem();
        List<AddressModel.ProvinceEntity.CityListBean.AreaListBean> districts = citys.get(cityIndex).getAreaList();
        if (districts != null && districts.size() > 0) {
            districtWheel.setViewAdapter(new AreaWheelAdapter(context, districts));
            districtWheel.setCurrentItem(0);
        }

    }

    public void show(View v) {
        layoutParams.alpha = 0.6f;
        context.getWindow().setAttributes(layoutParams);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    public void setProvince(List<AddressModel.ProvinceEntity> province) {
        this.province = province;
        bindData();
    }

    public void defaultValue(String provinceStr, String city, String arae) {
        if (TextUtils.isEmpty(provinceStr)) {
            return;
        }
        for (int i = 0; i < province.size(); i++) {
            AddressModel.ProvinceEntity provinces = province.get(i);
            if (provinces != null && provinces.getProvinceName().equalsIgnoreCase(provinceStr)) {
                provinceWheel.setCurrentItem(i);
                if (TextUtils.isEmpty(city)) {
                    return;
                }
                List<AddressModel.ProvinceEntity.CityListBean> citys = provinces.getCityList();
                for (int j = 0; j < citys.size(); j++) {
                    AddressModel.ProvinceEntity.CityListBean cityEntity = citys.get(j);
                    if (cityEntity != null && cityEntity.getCityName().equalsIgnoreCase(city)) {
                        cityWheel.setViewAdapter(new CityWheelAdapter(context, citys));
                        cityWheel.setCurrentItem(j);
                        if (TextUtils.isEmpty(arae)) {
                            return;
                        }
                        List<AddressModel.ProvinceEntity.CityListBean.AreaListBean> areas = cityEntity.getAreaList();
                        for (int k = 0; k < areas.size(); k++) {
                            AddressModel.ProvinceEntity.CityListBean.AreaListBean areaEntity = areas.get(k);
                            if (areaEntity != null && areaEntity.getAreaName().equalsIgnoreCase(arae)) {
                                districtWheel.setViewAdapter(new AreaWheelAdapter(context, areas));
                                districtWheel.setCurrentItem(k);
                            }
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.confirm_button)
    public void confirm() {
        if (onAddressChangeListener != null) {
            int provinceIndex = provinceWheel.getCurrentItem();
            int cityIndex = cityWheel.getCurrentItem();
            int areaIndex = districtWheel.getCurrentItem();

            String provinceName = null, cityName = null, areaName = null;

            List<AddressModel.ProvinceEntity.CityListBean> citys = null;
            if (province != null && province.size() > provinceIndex) {
               provinceEntity = province.get(provinceIndex);
                citys = provinceEntity.getCityList();
                provinceName = provinceEntity.getProvinceName();
            }
            List<AddressModel.ProvinceEntity.CityListBean.AreaListBean> districts = null;
            if (citys != null && citys.size() > cityIndex) {
                cityEntity = citys.get(cityIndex);
                districts = cityEntity.getAreaList();
                cityName = cityEntity.getCityName();
            }

            if (districts != null && districts.size() > areaIndex) {
                 areaEntity = districts.get(areaIndex);
                areaName = areaEntity.getAreaName();
            }

            onAddressChangeListener.onAddressChange(provinceEntity, cityEntity, areaEntity);
        }
        cancel();
    }

    @OnClick(R.id.cancel_button)
    public void cancel() {
        popupWindow.dismiss();
    }

    public void setOnAddressChangeListener(OnAddressChangeListener onAddressChangeListener) {
        this.onAddressChangeListener = onAddressChangeListener;
    }
}