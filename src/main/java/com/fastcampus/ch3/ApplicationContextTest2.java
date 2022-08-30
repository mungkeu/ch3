//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Properties;
//
//@Component
//@Scope("prototype")
//class Door {}
//@Component class Engine {}
//@Component class TurboEngine extends Engine {}
//@Component class SuperEngine extends Engine {}
//
//@Component
//class Car {
//    @Value("red") String color;
//    @Value("100") int oil;
//    //@Autowired
//    Engine engine;
//    //@Autowired
//    Door[] doors;
//
//    public Car(){}
//    //@Autowired 생성자는 생략해도 주입가능. 단 기본생성자가 있어 생성자가 2개라면 주입되지 않으므로 그냥 생략하지말자
//    @Autowired
//    public Car(@Value("red") String color, @Value("100") int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//
//@Component
//@PropertySource("setting.properties")
//class SysInfo{
//    @Value("#{systemProperties['user.timezone']}")
//    String timeZone;
//    @Value("#{systemEnvironment['PWD']}")
//    String currDir;
//    @Value("${autosaveDir}")
//    String autosaveDir;
//    @Value("${autosaveInterval}")
//    int autosaveInterval;
//    @Value("${autosave}")
//    boolean autosave;
//
//    @Override
//    public String toString() {
//        return "SysInfo{" +
//                "timeZone='" + timeZone + '\'' +
//                ", currDir='" + currDir + '\'' +
//                ", autosaveDir='" + autosaveDir + '\'' +
//                ", autosaveInterval=" + autosaveInterval +
//                ", autosave=" + autosave +
//                '}';
//    }
//}
//
//public class ApplicationContextTest2 {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config1.xml");
////      Car car = ac.getBean("car", Car.class); // 타입을 지정하면 형변환 안해도됨. 아래의 문장과 동일
//        Car car  = (Car) ac.getBean("car"); // 이름으로 빈 검색
//        System.out.println("car = " + car);
//
//        System.out.println("ac.getBean(SysInfo.class) = " + ac.getBean(SysInfo.class));
//
//        Properties properties = System.getProperties();
//        System.out.println("properties = " + properties);
//    }
//}