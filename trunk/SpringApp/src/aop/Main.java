/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aop;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String args[]) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/aop/Config.xml");
        DataService ws = (DataService) ctx.getBean("dataService");

        Double high = ws.getHistoricalHigh(new GregorianCalendar(2004, 0, 1).getTime());
        System.out.println("High was: " + high);
        ws = new DataServiceImpl() {

            protected DataDao getDataDao() {
                return null;
            }
        };
    }
}

abstract class DataServiceImpl implements DataService {

    protected abstract DataDao getDataDao();

    public Double getHistoricalHigh(Date date) {
        DataData wd = getDataDao().find(date);
        if (wd != null) {
            return new Double(wd.getHigh());
        }
        return null;
    }
}

interface DataService {

    Double getHistoricalHigh(Date date);
}

class DataData {

    Date date;
    double low;
    double high;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }
}

interface DataDao {

    DataData find(Date date);

    DataData save(Date date);

    DataData update(Date date);
}

class StatefulDataDataDaoImpl implements DataDao {

    public DataData find(Date date) {

        DataData wd = new DataData();
        wd.setDate((Date) date.clone());
        wd.setLow(5);
        wd.setHigh(15);
        return wd;
    }

    public DataData save(Date date) {
        throw new UnsupportedOperationException("This class uses static data only");
    }

    public DataData update(Date date) {
        throw new UnsupportedOperationException("This class uses static data only");
    }
}
