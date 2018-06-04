package xzh.com.addresswheel_master.view.listener;

import xzh.com.addresswheel_master.model.AddressModel;

public interface OnAddressChangeListener {
	void onAddressChange(AddressModel.ProvinceEntity province, AddressModel.ProvinceEntity.CityListBean city, AddressModel.ProvinceEntity.CityListBean.AreaListBean district);
}
