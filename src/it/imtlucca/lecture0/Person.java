package it.imtlucca.lecture0;

public class Person {
    private String name;
    private int age;

    public Person(){
        this.name = "Unknown person";
        this.age = 0;
    }

    public String getName(){
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age){
        assert (age > -1 && age < 150);
        this.age = age;
    }

    public void setName(String name){
        this.name = name;
    }

    public static void main(String[] args){
        Person p1 = new Person();
        System.out.println(p1.getName());
        p1.setName("Peter Drabik");
        p1.setAge(28);
        System.out.println("Name: " + p1.getName() + "\nAge: " + p1.getAge());
    }
}
