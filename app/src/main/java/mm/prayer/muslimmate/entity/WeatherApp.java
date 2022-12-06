package mm.prayer.muslimmate.entity;

public class WeatherApp {
    public String dayName, temp, tempMini, tempMax, image, desc, humidity, windSpeed;

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
