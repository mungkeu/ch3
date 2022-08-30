//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component("engine") class Engine{} // <bean id="engine" class="com.fastcampus.ch3.Engine"/>과 같다.
//@Component class SuperEngine extends Engine{}
//@Component class TurboEngine extends Engine{}
//@Component class Door{}
//@Component
//class Car{
//    @Value("red")
//    String color;
//    @Value("100")
//    int oil;
//    @Autowired
//    @Qualifier("superEngine") // 여러개면 이렇게 지정할수도 있다.
//    Engine engine; // byType - @Autowired 는 타입으로 먼저 검색, 여러개면 이름으로 검색한다. - engine, superEngine, turboEngine
//    // 위의 2개를 쓴것과 비슷한 방법 Resorce(name="superEngine") // byName
//    @Autowired Door[] doors;  // byType
//
//    // 기본 생성자는 꼭 만들어주자.
//    public Car(){}
//    public Car(String color, int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    public void setDoors(Door[] doors) {
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
//public class SpringDiTest {
//
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config1.xml");
//        Car car = (Car) ac.getBean("car"); // byName 아래와 같다 즉 이름으로하든 타입으로하든 같다.
////        Car car2 = (Car) ac.getBean(Car.class); // byType
////        Car car3 = ac.getBean("car", Car.class); // 이름과 타입을 둘다 보내므로 형변환하지 않아도 된다.
//        //Engine engine = (Engine) ac.getBean("engine"); // byName
//        //Engine engine = (Engine) ac.getBean(Engine.class); // byType
////        Engine engine = (Engine) ac.getBean("superEngine"); // 같은 타입이 여러개라면 이름으로 찾는다.
////        Door door = (Door) ac.getBean("door");
//
//        // 아래의 car는 bean의 기본값이 싱글톤으로 다 같은 해시값을 가진다.
//        // 만일 다르게 하고싶다면 xml에서 스코프를 프로토타입으로 바꾸어준다.
//        System.out.println("car = " + car);
////        System.out.println("car2 = " + car2);
////        System.out.println("car3 = " + car3);
////        System.out.println("engine = " + engine);
////        System.out.println("door = " + door);
//
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        car.setDoors(new Door[]{ac.getBean("door", Door.class), ac.getBean("door", Door.class)});
////        위의 세터를 이용하지 않아도 프로퍼티에서 설정할수도 있다.
//        System.out.println("car = " + car);
//    }
//}
