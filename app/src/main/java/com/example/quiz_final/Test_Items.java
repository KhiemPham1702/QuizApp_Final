package com.example.quiz_final;

import com.google.firebase.firestore.FirebaseFirestore;

public class Test_Items {
        private String TestId,TestIdCat,TestCourse, TestName, TestTime, TestNumberQuestion,testCategoryBackground;

    public String getTestCategoryBackground() {
        return testCategoryBackground;
    }

    public void setTestCategoryBackground(String testCategoryBackground) {
        this.testCategoryBackground = testCategoryBackground;
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getTestIdCat() {
        return TestIdCat;
    }

    public void setTestIdCat(String testIdCat) {
        TestIdCat = testIdCat;
    }

    public String getTestCourse() {
        return TestCourse;
    }

    public void setTestCourse(String testCourse) {
        TestCourse = testCourse;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTestTime() {
        return TestTime;
    }

    public void setTestTime(String testTime) {
        TestTime = testTime;
    }

    public String getTestNumberQuestion() {
        return TestNumberQuestion;
    }

    public void setTestNumberQuestion(String testNumberQuestion) {
        TestNumberQuestion = testNumberQuestion;
    }

    public Test_Items(String testId, String testIdCat, String testCourse, String testName, String testTime, String testNumberQuestion) {
        TestId = testId;
        TestIdCat = testIdCat;
        TestCourse = testCourse;
        TestName = testName;
        TestTime = testTime;
        TestNumberQuestion = testNumberQuestion;
    }
    public Test_Items(){
    }
}