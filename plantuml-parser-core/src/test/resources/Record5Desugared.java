class Person5 {
    private Name name;
    private int age;

    Person5(Name name, int age) {
        this.name = name;
        this.age = age;
    }

    public Name name() {
        return this.name;
    }

    public int age() {
        return this.age;
    }

    static class Name {
        private String personalName;
        private String familyName;

        Name(String personalName, String familyName) {
            this.personalName = personalName;
            this.familyName = familyName;
        }

        public String personalName() {
            return this.personalName;
        }

        public String familyName() {
            return this.familyName;
        }
    }
}
