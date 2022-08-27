package com.fastcampus.ch3.diCopy4;

import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component class Car{
    @Resource // @Autowired
    Engine engine; // 객체를 자동연결해준다. car.engine=engine으로 찾지않아도 된다
    @Autowired Door door; // @Resource

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}
@Component class SportsCar extends Car{}
@Component class Truck extends Car{}
@Component class Engine{}
@Component class Door{}
class AppContext{
    Map map ; // 객체 저장소
    
    AppContext(){
        map = new HashMap();
        doComponentScan();
        doAutowired();
        doResource();
    }

    private void doResource() {
        // map에 저장된 객체의 iv 중에 @Resource 붙어 있으면
        // map에서 iv 이름에 맞는 객체를 찾아서 연결(객체의 주소를 iv에 저장)
        try {
            // 맵에 값이 있는만큼 돈다
            for(Object bean : map.values()){
                for(Field fld : bean.getClass().getDeclaredFields()){
                    if(fld.getAnnotation(Resource.class) != null){ // byName
                        // 필드의 값을 바꾸어준다.
                        fld.set(bean, getBean(fld.getName())); // car.engine = obj
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAutowired(){
        // map에 저장된 객체의 iv 중에 @Autowired가 붙어 있으면
        // map에서 iv 타입에 맞는 객체를 찾아서 연결(객체의 주소를 iv에 저장)
        try {
            // 맵에 값이 있는만큼 돈다
            for(Object bean : map.values()){
                // 가지고온 값의 iv중에 오토와이드를 찾아서 가져와야한다.
                // bean이 가지고 있는 필드를 순회한다.
                for(Field fld : bean.getClass().getDeclaredFields()){
                    // 필드를 순회하다가 오토와이얼드 애너테이션을 가지고 있어 널이 아니라면
                    if(fld.getAnnotation(Autowired.class) != null){ // byType
                        // 필드의 값을 바꾸어준다.
                        fld.set(bean, getBean(fld.getType())); // car.engine = obj
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doComponentScan(){
        // 1. 패키지내의 클래스 목록을 가져온다.
        // 2. 반복문으로 클래스 하나씩 읽어와서 @Component이 붙어 있는지 확인
        // 3. @Component가 붙어있으면 객체를 생성해서 map에 저장
        try {
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");

            for(ClassPath.ClassInfo classInfo : set){
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if(component!=null){
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // by Name
    Object getBean(String key){
        return map.get(key);
    }

    // by Type
    Object getBean(Class clazz){
        for(Object obj : map.values()){
            if(clazz.isInstance(obj)){
                return obj;
            }
        }
        return null;
    }
}

public class Main4 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car)ac.getBean("car"); // byName 으로 객체를 검색
        Door door = (Door)ac.getBean(Door.class); // byType 으로 객체를 검색
        Engine engine = (Engine)ac.getBean("engine");

        // 수동으로 객체를 연결
//        car.engine = engine;
//        car.door = door;

        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }
}
// @Autowired 는 byType로 검색해서 알려준다.
// @Resource 는 byName으로 검색해서 알려준다.
//   - 만일 이름 생략시 첫글자를 소문자로 만들어서 생성한다.
//   - 지정도 가능하다 @Resource(name="engine2") Engine engine; 미지정시 이름은 engine