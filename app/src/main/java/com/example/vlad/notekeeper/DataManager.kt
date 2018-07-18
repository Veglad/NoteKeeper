package com.example.vlad.notekeeper

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

    private fun initializeCourses(){
        var course = CourseInfo("andoid_intents", "Android programming with intents")
        courses.set(course.courseId, course)

        course = CourseInfo("android_async", "Android async programming and services")
        courses.set(course.courseId, course)

        course = CourseInfo("java_lang", "Java fundamentals: the Java language")
        courses.set(course.courseId, course)

        course = CourseInfo("java_core", "Java fundamentals: the core platform")
        courses.set(course.courseId, course)
    }

    private fun initializeNotes(){
        var courseList = courses.values.toList()
        var note = NoteInfo(courseList.get(0),"Hard course", "This course was so hard for me." +
                " But now I can use intents in my applications")
        notes.add(note)

        note = NoteInfo(courseList.get(2),"Important course for beginners", "If you want to develop Android applications" +
                "you probably should learn Java fundamentals")
        notes.add(note)

        note = NoteInfo(courseList.get(3),"I'm watching this course now", "I can't tell you nothing interesting because" +
                "I've just started this course, but it seems to be pretty useful")
        notes.add(note)
    }
}