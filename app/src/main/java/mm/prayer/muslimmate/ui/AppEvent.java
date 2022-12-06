package mm.prayer.muslimmate.ui;

public class AppEvent {
    public String eventName , hejriDate ;
    public int icon ;

    public AppEvent(String eventName , String hejriDate)
    {
        this.eventName = eventName ;
        this.hejriDate = hejriDate ;
    }

    public AppEvent(String eventName , String hejriDate , int icon)
    {
        this.eventName = eventName ;
        this.hejriDate = hejriDate ;
        this.icon = icon ;
    }

}
