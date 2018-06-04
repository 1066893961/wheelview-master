package xzh.com.addresswheel_master.model;


import java.io.Serializable;
import java.util.List;

public class AddressModel implements Serializable{

    private List<ProvinceEntity> Province;

    public List<ProvinceEntity> getProvince() {
        return Province;
    }

    public void setProvince(List<ProvinceEntity> Province) {
        this.Province = Province;
    }

    public static class ProvinceEntity {
        /**
         * provinceName : 北京市
         * gbCode : 110000
         * cityList : [{"cityName":"北京市","gbCode":"110100","areaList":[{"areaName":"东城区","gbCode":"110101"},{"areaName":"西城区","gbCode":"110102"},{"areaName":"朝阳区","gbCode":"110105"},{"areaName":"丰台区","gbCode":"110106"},{"areaName":"石景山区","gbCode":"110107"},{"areaName":"海淀区","gbCode":"110108"},{"areaName":"门头沟区","gbCode":"110109"},{"areaName":"房山区","gbCode":"110111"},{"areaName":"通州区","gbCode":"110112"},{"areaName":"顺义区","gbCode":"110113"},{"areaName":"昌平区","gbCode":"110114"},{"areaName":"大兴区","gbCode":"110115"},{"areaName":"怀柔区","gbCode":"110116"},{"areaName":"平谷区","gbCode":"110117"},{"areaName":"密云区","gbCode":"110118"},{"areaName":"延庆区","gbCode":"110119"}]}]
         */

        private String provinceName;
        private String gbCode;
        private List<CityListBean> cityList;

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getGbCode() {
            return gbCode;
        }

        public void setGbCode(String gbCode) {
            this.gbCode = gbCode;
        }

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        public static class CityListBean {
            /**
             * cityName : 北京市
             * gbCode : 110100
             * areaList : [{"areaName":"东城区","gbCode":"110101"},{"areaName":"西城区","gbCode":"110102"},{"areaName":"朝阳区","gbCode":"110105"},{"areaName":"丰台区","gbCode":"110106"},{"areaName":"石景山区","gbCode":"110107"},{"areaName":"海淀区","gbCode":"110108"},{"areaName":"门头沟区","gbCode":"110109"},{"areaName":"房山区","gbCode":"110111"},{"areaName":"通州区","gbCode":"110112"},{"areaName":"顺义区","gbCode":"110113"},{"areaName":"昌平区","gbCode":"110114"},{"areaName":"大兴区","gbCode":"110115"},{"areaName":"怀柔区","gbCode":"110116"},{"areaName":"平谷区","gbCode":"110117"},{"areaName":"密云区","gbCode":"110118"},{"areaName":"延庆区","gbCode":"110119"}]
             */

            private String cityName;
            private String gbCode;
            private List<AreaListBean> areaList;

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getGbCode() {
                return gbCode;
            }

            public void setGbCode(String gbCode) {
                this.gbCode = gbCode;
            }

            public List<AreaListBean> getAreaList() {
                return areaList;
            }

            public void setAreaList(List<AreaListBean> areaList) {
                this.areaList = areaList;
            }

            public static class AreaListBean {
                /**
                 * areaName : 东城区
                 * gbCode : 110101
                 */

                private String areaName;
                private String gbCode;

                public String getAreaName() {
                    return areaName;
                }

                public void setAreaName(String areaName) {
                    this.areaName = areaName;
                }

                public String getGbCode() {
                    return gbCode;
                }

                public void setGbCode(String gbCode) {
                    this.gbCode = gbCode;
                }
            }
        }
    }
}
