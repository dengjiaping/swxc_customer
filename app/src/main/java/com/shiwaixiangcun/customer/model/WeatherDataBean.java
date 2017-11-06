package com.shiwaixiangcun.customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class WeatherDataBean implements Serializable {

    /**
     * historyWeather : {"history":{"1":{"date":"2017-07-24","info":{"night":["0","晴","25","东风","微风","19:53"],"day":["0","晴","37","南风","微风","06:15"]}}}}
     * area : [["贵州","26"],["遵义","2602"],["赤水","101260208"]]
     * life : {"date":"2017-7-25","info":{"kongtiao":["长时间开启","您会感到非常热，长时间开启制冷空调可能会帮您找回舒适的感觉。"],"yundong":["较不宜","天气较好，但炎热，请注意适当减少运动时间并降低运动强度，户外运动请注意防晒。"],"ziwaixian":["很强","紫外线辐射极强，建议涂擦SPF20以上、PA++的防晒护肤品，尽量避免暴露于日光下。"],"ganmao":["少发","各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"wuran":["中","气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"],"chuanyi":["炎热","天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"]}}
     * realtime : {"mslp":"","wind":{"windspeed":"5.0","direct":"西南风","power":"2级"},"time":"13:00:00","pressure":"","weather":{"humidity":"50","img":"0","info":"晴","temperature":"36"},"feelslike_c":"37","dataUptime":"1500962641","date":"2017-07-25"}
     * trafficalert : []
     * weather : [{"date":"2017-07-25","info":{"night":["0","晴","26","南风","微风","19:53"],"day":["0","晴","38","南风","微风","06:16"]}},{"date":"2017-07-26","info":{"dawn":["0","晴","26","南风","微风","19:53"],"night":["0","晴","27","南风","微风","19:52"],"day":["0","晴","39","南风","微风","06:17"]}},{"date":"2017-07-27","info":{"dawn":["0","晴","27","南风","微风","19:52"],"night":["0","晴","26","南风","微风","19:52"],"day":["0","晴","39","南风","微风","06:17"]}},{"date":"2017-07-28","info":{"dawn":["0","晴","26","南风","微风","19:52"],"night":["0","晴","26","南风","微风","19:51"],"day":["0","晴","39","南风","微风","06:18"]}},{"date":"2017-07-29","info":{"dawn":["0","晴","26","南风","微风","19:51"],"night":["0","晴","26","西北风","微风","19:50"],"day":["0","晴","39","西北风","微风","06:18"]}},{"date":"2017-07-30","info":{"night":["4","雷阵雨","26","西北风","微风","19:30"],"day":["1","多云","37","西北风","微风","07:30"]}},{"date":"2017-07-31","info":{"night":["4","雷阵雨","26","西北风","微风","19:30"],"day":["1","多云","37","西北风","微风","07:30"]}},{"date":"2017-08-01","info":{"night":["4","雷阵雨","26","西北风","微风","19:30"],"day":["1","多云","36","西北风","微风","07:30"]}},{"date":"2017-08-02","info":{"night":["4","雷阵雨","26","西北风","微风","19:30"],"day":["4","雷阵雨","35","西北风","微风","07:30"]}},{"date":"2017-08-03","info":{"night":["4","雷阵雨","25","西北风","微风","19:30"],"day":["4","雷阵雨","35","西北风","微风","07:30"]}}]
     * pm25 : {"so2":3,"o3":79,"parent":"遵义,101260201","aqirank":{"total":388,"rank":97,"aqicitycode":"101260201"},"co":"0.6","level":1,"color":"#00e400","no2":7,"aqi":38,"quality":"优","pm10":36,"pm25":16,"advice":"今天的空气质量令人满意，各类人群可正常活动。","chief":"PM10","upDateTime":1500958800000}
     * hourly_forecast : [{"img":"0","hour":"15","temperature":"37","info":"晴"},{"img":"0","hour":"16","temperature":"36","info":"晴"},{"img":"0","hour":"17","temperature":"36","info":"晴"},{"img":"0","hour":"18","temperature":"35","info":"晴"},{"img":"0","hour":"19","temperature":"33","info":"晴"},{"img":"0","hour":"20","temperature":"32","info":"晴"},{"img":"0","hour":"21","temperature":"31","info":"晴"},{"img":"0","hour":"22","temperature":"30","info":"晴"},{"img":"0","hour":"23","temperature":"29","info":"晴"},{"img":"0","hour":"0","temperature":"29","info":"晴"},{"img":"0","hour":"1","temperature":"29","info":"晴"},{"img":"0","hour":"2","temperature":"28","info":"晴"},{"img":"0","hour":"3","temperature":"28","info":"晴"},{"img":"0","hour":"4","temperature":"27","info":"晴"},{"img":"0","hour":"5","temperature":"27","info":"晴"},{"img":"0","hour":"6","temperature":"27","info":"晴"},{"img":"0","hour":"7","temperature":"27","info":"晴"},{"img":"0","hour":"8","temperature":"29","info":"晴"},{"img":"0","hour":"9","temperature":"31","info":"晴"},{"img":"0","hour":"10","temperature":"33","info":"晴"},{"img":"0","hour":"11","temperature":"34","info":"晴"},{"img":"0","hour":"12","temperature":"36","info":"晴"},{"img":"0","hour":"13","temperature":"37","info":"晴"},{"img":"0","hour":"14","temperature":"38","info":"晴"}]
     */

    private HistoryWeatherBean historyWeather;
    private LifeBean life;
    private RealtimeBean realtime;
    private Pm25Bean pm25;
    private List<List<String>> area;
    private List<?> trafficalert;
    private List<WeatherBeanX> weather;
    private List<HourlyForecastBean> hourly_forecast;

    public HistoryWeatherBean getHistoryWeather() {
        return historyWeather;
    }

    public void setHistoryWeather(HistoryWeatherBean historyWeather) {
        this.historyWeather = historyWeather;
    }

    public LifeBean getLife() {
        return life;
    }

    public void setLife(LifeBean life) {
        this.life = life;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public Pm25Bean getPm25() {
        return pm25;
    }

    public void setPm25(Pm25Bean pm25) {
        this.pm25 = pm25;
    }

    public List<List<String>> getArea() {
        return area;
    }

    public void setArea(List<List<String>> area) {
        this.area = area;
    }

    public List<?> getTrafficalert() {
        return trafficalert;
    }

    public void setTrafficalert(List<?> trafficalert) {
        this.trafficalert = trafficalert;
    }

    public List<WeatherBeanX> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBeanX> weather) {
        this.weather = weather;
    }

    public List<HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public static class HistoryWeatherBean {
        /**
         * history : {"1":{"date":"2017-07-24","info":{"night":["0","晴","25","东风","微风","19:53"],"day":["0","晴","37","南风","微风","06:15"]}}}
         */

        private HistoryBean history;

        public HistoryBean getHistory() {
            return history;
        }

        public void setHistory(HistoryBean history) {
            this.history = history;
        }

        public static class HistoryBean {
            /**
             * 1 : {"date":"2017-07-24","info":{"night":["0","晴","25","东风","微风","19:53"],"day":["0","晴","37","南风","微风","06:15"]}}
             */

            @SerializedName("1")
            private _$1Bean _$1;

            public _$1Bean get_$1() {
                return _$1;
            }

            public void set_$1(_$1Bean _$1) {
                this._$1 = _$1;
            }

            public static class _$1Bean {
                /**
                 * date : 2017-07-24
                 * info : {"night":["0","晴","25","东风","微风","19:53"],"day":["0","晴","37","南风","微风","06:15"]}
                 */

                private String date;
                private InfoBean info;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public static class InfoBean {
                    private List<String> night;
                    private List<String> day;

                    public List<String> getNight() {
                        return night;
                    }

                    public void setNight(List<String> night) {
                        this.night = night;
                    }

                    public List<String> getDay() {
                        return day;
                    }

                    public void setDay(List<String> day) {
                        this.day = day;
                    }
                }
            }
        }
    }

    public static class LifeBean {
        /**
         * date : 2017-7-25
         * info : {"kongtiao":["长时间开启","您会感到非常热，长时间开启制冷空调可能会帮您找回舒适的感觉。"],"yundong":["较不宜","天气较好，但炎热，请注意适当减少运动时间并降低运动强度，户外运动请注意防晒。"],"ziwaixian":["很强","紫外线辐射极强，建议涂擦SPF20以上、PA++的防晒护肤品，尽量避免暴露于日光下。"],"ganmao":["少发","各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"wuran":["中","气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"],"chuanyi":["炎热","天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"]}
         */

        private String date;
        private InfoBeanX info;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public InfoBeanX getInfo() {
            return info;
        }

        public void setInfo(InfoBeanX info) {
            this.info = info;
        }

        public static class InfoBeanX {
            private List<String> kongtiao;
            private List<String> yundong;
            private List<String> ziwaixian;
            private List<String> ganmao;
            private List<String> xiche;
            private List<String> wuran;
            private List<String> chuanyi;

            public List<String> getKongtiao() {
                return kongtiao;
            }

            public void setKongtiao(List<String> kongtiao) {
                this.kongtiao = kongtiao;
            }

            public List<String> getYundong() {
                return yundong;
            }

            public void setYundong(List<String> yundong) {
                this.yundong = yundong;
            }

            public List<String> getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(List<String> ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public List<String> getGanmao() {
                return ganmao;
            }

            public void setGanmao(List<String> ganmao) {
                this.ganmao = ganmao;
            }

            public List<String> getXiche() {
                return xiche;
            }

            public void setXiche(List<String> xiche) {
                this.xiche = xiche;
            }

            public List<String> getWuran() {
                return wuran;
            }

            public void setWuran(List<String> wuran) {
                this.wuran = wuran;
            }

            public List<String> getChuanyi() {
                return chuanyi;
            }

            public void setChuanyi(List<String> chuanyi) {
                this.chuanyi = chuanyi;
            }
        }
    }

    public static class RealtimeBean {
        /**
         * mslp :
         * wind : {"windspeed":"5.0","direct":"西南风","power":"2级"}
         * time : 13:00:00
         * pressure :
         * weather : {"humidity":"50","img":"0","info":"晴","temperature":"36"}
         * feelslike_c : 37
         * dataUptime : 1500962641
         * date : 2017-07-25
         */

        private String mslp;
        private WindBean wind;
        private String time;
        private String pressure;
        private WeatherBean weather;
        private String feelslike_c;
        private String dataUptime;
        private String date;

        public String getMslp() {
            return mslp;
        }

        public void setMslp(String mslp) {
            this.mslp = mslp;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public WeatherBean getWeather() {
            return weather;
        }

        public void setWeather(WeatherBean weather) {
            this.weather = weather;
        }

        public String getFeelslike_c() {
            return feelslike_c;
        }

        public void setFeelslike_c(String feelslike_c) {
            this.feelslike_c = feelslike_c;
        }

        public String getDataUptime() {
            return dataUptime;
        }

        public void setDataUptime(String dataUptime) {
            this.dataUptime = dataUptime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public static class WindBean {
            /**
             * windspeed : 5.0
             * direct : 西南风
             * power : 2级
             */

            private String windspeed;
            private String direct;
            private String power;

            public String getWindspeed() {
                return windspeed;
            }

            public void setWindspeed(String windspeed) {
                this.windspeed = windspeed;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }
        }

        public static class WeatherBean {
            /**
             * humidity : 50
             * img : 0
             * info : 晴
             * temperature : 36
             */

            private String humidity;
            private String img;
            private String info;
            private String temperature;

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }

    public static class Pm25Bean {
        /**
         * so2 : 3
         * o3 : 79
         * parent : 遵义,101260201
         * aqirank : {"total":388,"rank":97,"aqicitycode":"101260201"}
         * co : 0.6
         * level : 1
         * color : #00e400
         * no2 : 7
         * aqi : 38
         * quality : 优
         * pm10 : 36
         * pm25 : 16
         * advice : 今天的空气质量令人满意，各类人群可正常活动。
         * chief : PM10
         * upDateTime : 1500958800000
         */

        private int so2;
        private int o3;
        private String parent;
        private AqirankBean aqirank;
        private String co;
        private int level;
        private String color;
        private int no2;
        private int aqi;
        private String quality;
        private int pm10;
        private int pm25;
        private String advice;
        private String chief;
        private long upDateTime;

        public int getSo2() {
            return so2;
        }

        public void setSo2(int so2) {
            this.so2 = so2;
        }

        public int getO3() {
            return o3;
        }

        public void setO3(int o3) {
            this.o3 = o3;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public AqirankBean getAqirank() {
            return aqirank;
        }

        public void setAqirank(AqirankBean aqirank) {
            this.aqirank = aqirank;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getNo2() {
            return no2;
        }

        public void setNo2(int no2) {
            this.no2 = no2;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public String getAdvice() {
            return advice;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }

        public String getChief() {
            return chief;
        }

        public void setChief(String chief) {
            this.chief = chief;
        }

        public long getUpDateTime() {
            return upDateTime;
        }

        public void setUpDateTime(long upDateTime) {
            this.upDateTime = upDateTime;
        }

        public static class AqirankBean {
            /**
             * total : 388
             * rank : 97
             * aqicitycode : 101260201
             */

            private int total;
            private int rank;
            private String aqicitycode;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getAqicitycode() {
                return aqicitycode;
            }

            public void setAqicitycode(String aqicitycode) {
                this.aqicitycode = aqicitycode;
            }
        }
    }

    public static class WeatherBeanX {
        /**
         * date : 2017-07-25
         * info : {"night":["0","晴","26","南风","微风","19:53"],"day":["0","晴","38","南风","微风","06:16"]}
         */

        private String date;
        private InfoBeanXX info;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public InfoBeanXX getInfo() {
            return info;
        }

        public void setInfo(InfoBeanXX info) {
            this.info = info;
        }

        public static class InfoBeanXX {
            private List<String> night;
            private List<String> day;

            public List<String> getNight() {
                return night;
            }

            public void setNight(List<String> night) {
                this.night = night;
            }

            public List<String> getDay() {
                return day;
            }

            public void setDay(List<String> day) {
                this.day = day;
            }
        }
    }

    public static class HourlyForecastBean {
        /**
         * img : 0
         * hour : 15
         * temperature : 37
         * info : 晴
         */

        private String img;
        private String hour;
        private String temperature;
        private String info;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
