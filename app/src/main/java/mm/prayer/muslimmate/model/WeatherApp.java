package mm.prayer.muslimmate.model;

public class WeatherApp {
    public String dayName;
    public String temp;
    public String tempMini;
    public String tempMax;
    public String image;
    public String desc;
    public String humidity;
    public String windSpeed;

    public WeatherApp(String dayName, String temp, String tempMini, String tempMax, String image, String desc, String humidity, String windSpeed) {
        this.dayName = dayName;
        this.temp = temp;
        this.tempMini = tempMini;
        this.tempMax = tempMax;
        this.image = image;
        this.desc = desc;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public WeatherApp(String dayName, String tempMini, String tempMax, String image) {
        this.dayName = dayName;
        this.tempMini = tempMini;
        this.tempMax = tempMax;
        this.image = image;
    }

}
