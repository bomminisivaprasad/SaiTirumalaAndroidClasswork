package com.example.user.e_quiz.MyModel;

public class MyScore {
   private String mail;
  /*  String clang;
    String jlang;*/
  private String languageFundamentals;
    private String operatorsAssignments;
    private String declarationsAccessControl;
    private String assertions;
    private String exceptions;
    private String innerClasses;
    private String garbageCollections;
    private String javalangClass;
    private String flowControl;
    private String objectsandCollections;
    private String threads;
    private String overallQuestions;


    public MyScore(String mail, String languageFundamentals, String operatorsAssignments, String declarationsAccessControl, String assertions, String exceptions, String innerClasses, String garbageCollections, String javalangClass, String flowControl, String objectsandCollections, String threads, String overallQuestions) {
        this.mail = mail;
        this.languageFundamentals = languageFundamentals;
        this.operatorsAssignments = operatorsAssignments;
        this.declarationsAccessControl = declarationsAccessControl;
        this.assertions = assertions;
        this.exceptions = exceptions;
        this.innerClasses = innerClasses;
        this.garbageCollections = garbageCollections;
        this.javalangClass = javalangClass;
        this.flowControl = flowControl;
        this.objectsandCollections = objectsandCollections;
        this.threads = threads;
        this.overallQuestions = overallQuestions;
    }

    public String getLanguageFundamentals() {
        return languageFundamentals;
    }

    public void setLanguageFundamentals(String languageFundamentals) {
        this.languageFundamentals = languageFundamentals;
    }

    public String getOperatorsAssignments() {
        return operatorsAssignments;
    }

    public void setOperatorsAssignments(String operatorsAssignments) {
        this.operatorsAssignments = operatorsAssignments;
    }

    public String getDeclarationsAccessControl() {
        return declarationsAccessControl;
    }

    public void setDeclarationsAccessControl(String declarationsAccessControl) {
        this.declarationsAccessControl = declarationsAccessControl;
    }

    public String getAssertions() {
        return assertions;
    }

    public void setAssertions(String assertions) {
        this.assertions = assertions;
    }

    public String getExceptions() {
        return exceptions;
    }

    public void setExceptions(String exceptions) {
        this.exceptions = exceptions;
    }

    public String getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(String innerClasses) {
        this.innerClasses = innerClasses;
    }

    public String getGarbageCollections() {
        return garbageCollections;
    }

    public void setGarbageCollections(String garbageCollections) {
        this.garbageCollections = garbageCollections;
    }

    public String getJavalangClass() {
        return javalangClass;
    }

    public void setJavalangClass(String javalangClass) {
        this.javalangClass = javalangClass;
    }

    public String getFlowControl() {
        return flowControl;
    }

    public void setFlowControl(String flowControl) {
        this.flowControl = flowControl;
    }

    public String getObjectsandCollections() {
        return objectsandCollections;
    }

    public void setObjectsandCollections(String objectsandCollections) {
        this.objectsandCollections = objectsandCollections;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getOverallQuestions() {
        return overallQuestions;
    }

    public void setOverallQuestions(String overallQuestions) {
        this.overallQuestions = overallQuestions;
    }

    public MyScore() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

  /*  public String getClang() {
        return clang;
    }

    public void setClang(String clang) {
        this.clang = clang;
    }

    public String getJlang() {
        return jlang;
    }

    public void setJlang(String jlang) {
        this.jlang = jlang;
    }
*/
}

