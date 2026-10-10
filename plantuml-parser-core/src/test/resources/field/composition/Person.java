public class Person {

    //Composition Has-A Relationship
    private Job job;
    private Hobby hobby;

    public Person() {
        this.job = new Job();
        job.setSalary("1000");

        this.hobby = new Hobby();
        hobby.setHobbyName("Reading");
    }

    public String getThePersonSalary(){
        return job.getSalary();
    }

    public String getThePersonHobby(){
        return hobby.getHobbyName();
    }
}
