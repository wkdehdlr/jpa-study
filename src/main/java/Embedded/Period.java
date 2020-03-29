package Embedded;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {
    private LocalDateTime stime;
    private LocalDateTime etime;

    public Period() {
    }

    public LocalDateTime getStime() {
        return stime;
    }

    public void setStime(LocalDateTime stime) {
        this.stime = stime;
    }

    public LocalDateTime getEtime() {
        return etime;
    }

    public void setEtime(LocalDateTime etime) {
        this.etime = etime;
    }
}
