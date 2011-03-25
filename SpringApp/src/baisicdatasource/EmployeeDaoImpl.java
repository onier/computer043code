/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baisicdatasource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;

public class EmployeeDaoImpl extends JdbcDaoSupport {

    private LastNameAndAgeQuery lastNameAndAgeQuery;

    protected void initDao() throws Exception {
        super.initDao();
        getJdbcTemplate().setNativeJdbcExtractor(new SimpleNativeJdbcExtractor());
        lastNameAndAgeQuery = new LastNameAndAgeQuery(getDataSource());

    }

    public List getEmployeesForLastNameAndAge(String lastName, Integer age) {
        return lastNameAndAgeQuery.execute(new Object[]{lastName, age});
    }
}

class LastNameAndAgeQuery extends MappingSqlQuery {

    private static final String SQL_QUERY = "SELECT * FROM employee WHERE name_last = ? AND age = ?";

    public LastNameAndAgeQuery(DataSource ds) {
        super(ds, SQL_QUERY);
        declareParameter(new SqlParameter("name_last", Types.VARCHAR));
        declareParameter(new SqlParameter("age", Types.INTEGER));
        compile();
    }

    protected Object mapRow(ResultSet resultSet, int row) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.getName().setFirst(resultSet.getString("name_first"));
        employee.getName().setMiddle(resultSet.getString("name_middle"));
        employee.getName().setLast(resultSet.getString("name_last"));
        employee.getAddress().setLine1(resultSet.getString("address_line1"));
        employee.getAddress().setLine2(resultSet.getString("address_line2"));
        employee.getAddress().setCity(resultSet.getString("address_city"));
        employee.getAddress().setState(resultSet.getString("address_state"));
        employee.getAddress().setZip(resultSet.getString("address_zip"));
        employee.setAge(resultSet.getInt("age"));
        return employee;
    }
}

class Employee {

    private Integer id;
    private Name name = new Name();
    private Integer age;
    private Sex sex;
    private Address address = new Address();
    private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();

    public Employee() {
    }

    public Employee(String firstName, String lastName) {
        this.getName().setFirst(firstName);
        this.getName().setLast(lastName);
    }

    void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Name getName() {
        return name;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return Collections.unmodifiableList(phoneNumbers);
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    public void removePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.remove(phoneNumber);
    }

    public void removePhoneNumber(int index) {
        this.phoneNumbers.remove(index);
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}

abstract class Sex {

    public static final Sex MALE = new Male();
    public static final Sex FEMALE = new Female();

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return getClass().equals(o.getClass());
    }
}

class PhoneNumber {
}

class Address {

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine1() {
        return this.line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine2() {
        return this.line2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return this.zip;
    }
}

final class Male extends Sex {

    protected Male() {
    }
}

final class Female extends Sex {

    protected Female() {
    }
}

class Name {

    private String first;
    private String middle;
    private String last;

    public void setFirst(String first) {
        this.first = first;
    }

    public String getFirst() {
        return this.first;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getMiddle() {
        return this.middle;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLast() {
        return this.last;
    }
}

