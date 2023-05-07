public class Person6 {
    private Name name;
    private int age;

    public Person6(Name name, int age) {
        this.name = name;
        this.age = age;
    }

    public Name name() {
        return this.name;
    }

    public int age() {
        return this.age;
    }

    public class Name {
        public Name() {}
    }
}
