package fr.niavlys.dev.ci.players.grades;

public class Grade {

    private GradeType grade;

    public Grade(GradeType grade){
        this.grade = grade;
    }
    public GradeType getGrade() {
        return grade;
    }
    public void setGrade(GradeType grade) {
        this.grade = grade;
    }
    public void rankup(){
        GradeType next = this.grade.getNext(this.grade);
        if(next == null){
            return;
        }
        this.grade = next;
    }
    public void derank(){
        GradeType previous = this.grade.getPrevious(this.grade);
        if(previous == null){
            return;
        }
        this.grade = previous;
    }
    public boolean hasPerm(GradeType type){
        return this.grade.ordinal() >= type.ordinal();
    }
    public GradeType getNext(){
        return this.grade.getNext(this.grade);
    }
    public GradeType getPrevious(){
        return this.grade.getPrevious(this.grade);
    }
}
