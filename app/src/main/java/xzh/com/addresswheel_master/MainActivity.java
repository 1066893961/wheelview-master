package xzh.com.addresswheel_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xzh.com.addresswheel_master.model.AddressModel;
import xzh.com.addresswheel_master.utils.JsonUtil;
import xzh.com.addresswheel_master.utils.Utils;
import xzh.com.addresswheel_master.view.ChooseAddressWheel;
import xzh.com.addresswheel_master.view.listener.OnAddressChangeListener;

public class MainActivity extends AppCompatActivity implements OnAddressChangeListener {

    @Bind(R.id.choose_address)
    TextView chooseAddress;

    private ChooseAddressWheel chooseAddressWheel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initWheel();
        initData();
    }

    private void initWheel() {
        chooseAddressWheel = new ChooseAddressWheel(this);
        chooseAddressWheel.setOnAddressChangeListener(this);
    }

    private void initData() {
        String address = Utils.readAssert(this, "address.txt");
        AddressModel model = JsonUtil.parseJson(address, AddressModel.class);
        if (model != null) {
            List<AddressModel.ProvinceEntity> data = model.getProvince();

            List<AddressModel.ProvinceEntity.CityListBean> cityListBeanList = new ArrayList<>();
            List<AddressModel.ProvinceEntity.CityListBean.AreaListBean> areaListBeanList = new ArrayList<>();
            AddressModel.ProvinceEntity provinceEntity = new AddressModel.ProvinceEntity();
            AddressModel.ProvinceEntity.CityListBean cityListBean = new AddressModel.ProvinceEntity.CityListBean();
            AddressModel.ProvinceEntity.CityListBean.AreaListBean areaListBean = new AddressModel.ProvinceEntity.CityListBean.AreaListBean();
            provinceEntity.setProvinceName("全国");
            cityListBean.setCityName("全部");
            areaListBean.setAreaName("全部");
            areaListBeanList.add(0, areaListBean);
            cityListBeanList.add(0, cityListBean);
            cityListBean.setAreaList(areaListBeanList);
            provinceEntity.setCityList(cityListBeanList);
            data.add(0, provinceEntity);


            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getCityList().get(0).getCityName() != null) {
                    if (!data.get(i).getCityList().get(0).getCityName().equals("北京市") &&
                            !data.get(i).getCityList().get(0).getCityName().equals("上海市") &&
                            !data.get(i).getCityList().get(0).getCityName().equals("重庆市") &&
                            !data.get(i).getCityList().get(0).getCityName().equals("全部") &&
                            !data.get(i).getCityList().get(0).getCityName().equals("天津市")) {
                        List<AddressModel.ProvinceEntity.CityListBean> cityListBeanList1 = data.get(i).getCityList();
                        AddressModel.ProvinceEntity.CityListBean cityListBean1 = new AddressModel.ProvinceEntity.CityListBean();
                        List<AddressModel.ProvinceEntity.CityListBean.AreaListBean> areaListBeanList1 = new ArrayList<>();
                        AddressModel.ProvinceEntity.CityListBean.AreaListBean areaListBean1 = new AddressModel.ProvinceEntity.CityListBean.AreaListBean();
                        cityListBean1.setCityName("全部");
                        areaListBean1.setAreaName("全部");
                        areaListBeanList1.add(0, areaListBean1);
                        cityListBean1.setAreaList(areaListBeanList1);
                        cityListBeanList1.add(0, cityListBean1);

                    }
                }

                for (int j = 0; j < data.get(i).getCityList().size(); j++) {
                    List<AddressModel.ProvinceEntity.CityListBean.AreaListBean> areaListBeanList1 = data.get(i).getCityList().get(j).getAreaList();
                    AddressModel.ProvinceEntity.CityListBean.AreaListBean areaListBean1 = new AddressModel.ProvinceEntity.CityListBean.AreaListBean();
                    areaListBean1.setAreaName("全部");
                    if (areaListBeanList1 != null && areaListBeanList1.size() != 1) {
                        areaListBeanList1.add(0, areaListBean1);
                    }
                }

            }

            if (data == null) {
                return;
            }

//            chooseAddress.setText(data.provinceName + " " + data.cityName + " " + data.areaName);
            if (data != null) {
                chooseAddressWheel.setProvince(data);
//                chooseAddressWheel.defaultValue(data.get(0).getProvinceName(),
//                        data.get(0).getCityList().get(0).getCityName(),
//                        data.get(0).getCityList().get(0).getAreaList().get(0).getAreaName());
            }
        }

    }

    @OnClick(R.id.choose_address)
    public void addressClick(View view) {
//        Utils.hideKeyBoard(this);
        chooseAddressWheel.show(view);
    }

    @Override
    public void onAddressChange(AddressModel.ProvinceEntity province, AddressModel.ProvinceEntity.CityListBean city, AddressModel.ProvinceEntity.CityListBean.AreaListBean district) {
        chooseAddress.setText(province.getProvinceName() + " "+province.getGbCode()
                + city.getCityName() + " " +city.getGbCode()
                + district.getAreaName()+district.getGbCode());
    }
}
